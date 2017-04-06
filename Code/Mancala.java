import java.util.Scanner;

public class Mancala {
	int[] board = { 4, 4, 4, 4, 4, 4, 0, 4, 4, 4, 4, 4, 4, 0 };
	MancalaAI AI;
	boolean playerTurn = true;
	Scanner console = new Scanner(System.in);

	public Mancala(MancalaAI mancalaAI) {
		this.AI = mancalaAI;
	}

	public void displayBoard(int [] board) {
		System.out.print("         0   1   2   3   4   5");
		for (int i = 0; i < 2; i++) {
			System.out.print("\n   ");
			if (i != 0) {
				System.out.print("|");
				if (board[6] / 10 == 0) {
					System.out.print(" ");
				}
				System.out.print(board[6] + " ");
			}
			for (int k = 0; k < 33 + (i * (-8)); k++)
				System.out.print("-");
			if (i != 0) {
				if (board[13] / 10 == 0) {
					System.out.print(" ");
				}
				System.out.print("" + board[13] + " ");

				System.out.print("|");
			}
			System.out.print("\n");
			if (i == 0)
				System.out.print("a");
			else
				System.out.print("b");
			if (i == 0) {
				System.out.print("  |   | " + board[5] + " |");
				for (int j = 4; j >= 0; j--) {
					System.out.print(" " + board[j] + " |");
				}
			} else {
				System.out.print("  |   | " + board[7] + " |");
				for (int j = 1; j < 6; j++) {
					System.out.print(" " + board[i * 7 + j] + " |");
				}
			}
			System.out.print("   |");
		}
		System.out.print("\n   ");
		for (int k = 0; k < 33; k++)
			System.out.print("-");
		System.out.println();
	}

	public void run() {
		String move = null;
		while (!this.isGameOver()) {
			this.displayBoard(board);
			if (playerTurn)
				move = promptPlayer();
			else
				move = promptAI();
			playerTurn = makeMove(move, playerTurn);
		}
		this.displayBoard(board);
		// TODO gameover message needed

	}

	private boolean makeMove(String move, boolean playerTurn) {
		this.printmove(move, playerTurn);
		int piecesInHand;
		int pos;
		if (move.charAt(0) == 'a') {
			piecesInHand = board[Math.abs((move.charAt(1) - '0') - 5)];
			pos = Math.abs((move.charAt(1) - '0') - 5);
			board[Math.abs((move.charAt(1) - '0') - 5)] = 0;
		} else {
			piecesInHand = board[(move.charAt(1) - '0') + 7];
			pos = (move.charAt(1) - '0') + 7;
			board[(move.charAt(1) - '0') + 7] = 0;
		}

		while (piecesInHand > 0) {
			pos = (pos + 1) % board.length;
			board[pos]++;
			piecesInHand--;
		}
		if ((pos == 6 && move.charAt(0) == 'a') || (pos == 13 && move.charAt(0) == 'b'))
			return playerTurn;
		return !playerTurn;
	}

	private void printmove(String move, boolean playerTurn) {
		if (playerTurn) {
			System.out.println("You moved pieces from " + move.substring(0, 2));
		} else {
			System.out.println("The AI moved pieces from " + move.substring(0, 2));
		}

	}

	private String promptAI() {
		console.nextLine();
		return AI.nextmove(board);

	}

	private String promptPlayer() {
		String input;
		boolean invalid;
		do {
			System.out.print("What move would you like to make? ");
			input = console.next();
			invalid = invalid(input);
			if (invalid) {
				System.out.println("INVALID INPUT\n");
			}
		} while (invalid);
		return input.substring(0, 2);

	}

	private boolean invalid(String input) {
		boolean inrange = input.matches("[a-b][0-5]");
		if (!inrange)
			return true;
		if (input.charAt(0) == 'b')
			return true;
		if (board[Math.abs((input.charAt(1) - '0')-5)] == 0)
			return true;
		return false;
	}

	private boolean isGameOver() {
		return (sumOfSubArray(0, 6) == 0 || sumOfSubArray(7, 13) == 0);
	}

	private int sumOfSubArray(int start, int end) {
		int count = 0;
		for (int i = start; i < end; i++)
			count += board[i];
		return count;
	}
}
