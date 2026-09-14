package bingo.game;

import java.util.ArrayList;
import java.util.Collections;

import bingo.service.BingoService;
import bingo.util.Color;
import bingo.util.Menu;

public class BingoCard {
	public static int[][] createCard(int size) {
		ArrayList<Integer> numbers = new ArrayList<>();

		for (int i = 1; i <= 99; i++) {
			numbers.add(i);
		}
		Collections.shuffle(numbers);

		int[][] card = new int[size][size];
		int index = 0;

		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				if (row == size / 2 && col == size / 2) {
					card[row][col] = 0;
				} else {
					card[row][col] = numbers.get(index++);
				}
			}
		}
		return card;
	}

	public static void showCard(int[][] card, int turn, int number) {
		int size = card.length;
		Menu.printTitle(size + " × " + size + " BINGO CARD");
		System.out.println("\n[ " + "TURN " + Color.ORANGE + turn + Color.RESET + " | DRAW " + Color.ORANGE + number
				+ Color.RESET + " ]");
		Menu.printLine();
		printCard(createHeader(size), size);
		printCard(createBorder(size), size);

		for (int row = 0; row < size; row++) {
			StringBuilder line = new StringBuilder();
			line.append(String.format("%2d |", row + 1));

			for (int col = 0; col < size; col++) {

				if (row == size / 2 && col == size / 2) {

					if (BingoService.isBingoCell(card, row, col)) {
						line.append(Color.RED + "FREE" + Color.RESET + "|");

					} else if (BingoService.isReachCell(card, row, col)) {
						line.append(Color.ORANGE + "FREE" + Color.RESET + "|");

					} else {
						line.append(Color.YELLOW + "FREE" + Color.RESET + "|");
					}

				} else if (card[row][col] == -1) {

					if (BingoService.isBingoCell(card, row, col)) {
						line.append(" " + Color.RED + "//" + Color.RESET + " |");

					} else if (BingoService.isReachCell(card, row, col)) {
						line.append(" " + Color.ORANGE + "//" + Color.RESET + " |");

					} else {
						line.append(" " + Color.YELLOW + "//" + Color.RESET + " |");
					}

				} else {
					line.append(String.format("%3d |", card[row][col]));
				}
			}

			printCard(line.toString(), size);
			printCard(createBorder(size), size);
		}
	}

	private static String createHeader(int size) {
		StringBuilder sb = new StringBuilder();
		sb.append("   ");
		for (int i = 0; i < size; i++) {
			sb.append(String.format("  %c  ", 'A' + i));
		}
		return sb.toString();
	}

	private static String createBorder(int size) {
		StringBuilder sb = new StringBuilder();
		sb.append("   ");
		for (int i = 0; i < size; i++) {
			sb.append("+----");
		}
		sb.append("+");
		return sb.toString();
	}

	private static void printCard(String text, int size) {
		final int width = 40;
		int cardWidth = createBorder(size).length();
		int padding = Math.max(0, (width - cardWidth) / 2);

		System.out.println(" ".repeat(padding) + text);
	}

	public static void openNumber(int[][] card, int number) {
		for (int row = 0; row < card.length; row++) {
			for (int col = 0; col < card[row].length; col++) {
				if (card[row][col] == number) {
					card[row][col] = -1;
				}
			}
		}
	}
}