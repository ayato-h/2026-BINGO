package bingo.service;

public class BingoService {
	public static boolean isBingo(int[][] card) {
		int size = card.length;
		for (int row = 0; row < size; row++) {
			if (isBingoRow(card, row)) {
				return true;
			}
		}

		for (int col = 0; col < size; col++) {
			if (isBingoColumn(card, col)) {
				return true;
			}
		}

		if (isBingoDiagonalLeft(card)) {
			return true;
		}

		if (isBingoDiagonalRight(card)) {
			return true;
		}

		return false;
	}

	public static int countBingo(int[][] card) {
		int size = card.length;
		int count = 0;
		for (int row = 0; row < size; row++) {
			if (isBingoRow(card, row)) {
				count++;
			}
		}
		for (int col = 0; col < size; col++) {
			if (isBingoColumn(card, col)) {
				count++;
			}
		}
		if (isBingoDiagonalLeft(card)) {
			count++;
		}
		if (isBingoDiagonalRight(card)) {
			count++;
		}
		return count;
	}

	public static boolean isBingoCell(int[][] card, int row, int col) {
		int size = card.length;
		if (isBingoRow(card, row)) {
			return true;
		}
		if (isBingoColumn(card, col)) {
			return true;
		}
		if (row == col && isBingoDiagonalLeft(card)) {
			return true;
		}
		if (row + col == size - 1 && isBingoDiagonalRight(card)) {
			return true;
		}
		return false;
	}

	private static boolean isBingoRow(int[][] card, int row) {
		for (int col = 0; col < card.length; col++) {
			if (!isOpen(card, row, col)) {
				return false;
			}
		}
		return true;
	}

	private static boolean isBingoColumn(int[][] card, int col) {
		for (int row = 0; row < card.length; row++) {
			if (!isOpen(card, row, col)) {
				return false;
			}
		}
		return true;
	}

	private static boolean isBingoDiagonalLeft(int[][] card) {
		for (int i = 0; i < card.length; i++) {
			if (!isOpen(card, i, i)) {
				return false;
			}
		}
		return true;
	}

	private static boolean isBingoDiagonalRight(int[][] card) {
		for (int i = 0; i < card.length; i++) {
			if (!isOpen(card, i, card.length - 1 - i)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isReachCell(int[][] card, int row, int col) {
		int size = card.length;

		int openCount = 0;
		for (int c = 0; c < size; c++) {
			if (isOpen(card, row, c)) {
				openCount++;
			}
		}
		if (openCount == size - 1) {
			return true;
		}

		openCount = 0;
		for (int r = 0; r < size; r++) {
			if (isOpen(card, r, col)) {
				openCount++;
			}
		}
		if (openCount == size - 1) {
			return true;
		}

		if (row == col) {
			openCount = 0;
			for (int i = 0; i < size; i++) {
				if (isOpen(card, i, i)) {
					openCount++;
				}
			}
			if (openCount == size - 1) {
				return true;
			}
		}

		if (row + col == size - 1) {
			openCount = 0;
			for (int i = 0; i < size; i++) {
				if (isOpen(card, i, size - 1 - i)) {
					openCount++;
				}
			}
			if (openCount == size - 1) {
				return true;
			}
		}

		return false;
	}

	private static boolean isOpen(int[][] card, int row, int col) {
		int size = card.length;
		if (size % 2 == 1
				&& row == size / 2
				&& col == size / 2) {
			return true;
		}
		return card[row][col] == -1;
	}
}