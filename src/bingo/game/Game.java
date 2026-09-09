package bingo.game;

import java.util.Scanner;

import bingo.Main;
import bingo.model.Player;
import bingo.service.BingoService;
import bingo.service.ScoreService;
import bingo.util.Color;
import bingo.util.Input;
import bingo.util.Menu;

public class Game {
	public static void showGameMenu(Scanner scanner, Player player) {
		while (true) {
			Menu.showGameMenu();
			int select = Input.nextInt(scanner, 0, 4);
			switch (select) {
			case 1:
				startGame(scanner, player, 3);
				break;
			case 2:
				startGame(scanner, player, 5);
				break;
			case 3:
				startGame(scanner, player, 7);
				break;
			case 4:
				Main.showSettings(scanner, player);
				break;
			case 0:
				return;
			}
		}
	}

	public static void startGame(Scanner scanner, Player player, int size) {
		if (player.getMode().equals("NORMAL")) {
			startNormal(scanner, player, size);
		} else if (player.getMode().equals("ENDLESS")) {
			startEndless(scanner, player, size);
		} else if (player.getMode().equals("CHALLENGE")) {
			startChallenge(scanner, player, size);
		}
	}

	public static void startNormal(Scanner scanner, Player player, int size) {
		int[][] card = BingoCard.createCard(size);
		NumberGenerator generator = new NumberGenerator();
		int turn = 0;
		while (true) {
			int number = generator.nextNumber();
			BingoCard.openNumber(card, number);
			turn++;
			System.out.println();
			BingoCard.showCard(card, turn, number);
			if (BingoService.isBingo(card)) {
				showNormalResult(scanner, player, size, turn);
				break;
			}
			System.out.print("\n" + Color.ORANGE + "[ENTER]" + Color.RESET + " 次へ");
			scanner.nextLine();
		}

	}

	public static void startEndless(Scanner scanner, Player player, int size) {
		int[][] card = BingoCard.createCard(size);
		NumberGenerator generator = new NumberGenerator();

		int turn = 0;
		int lastBingoTurn = 0;
		int bingoCount = 0;

		int totalBingo = size * 2 + 2;

		while (bingoCount < totalBingo) {
			int number = generator.nextNumber();
			BingoCard.openNumber(card, number);
			turn++;

			System.out.println();
			BingoCard.showCard(card, turn, number);

			int currentBingoCount = BingoService.countBingo(card);

			if (currentBingoCount > bingoCount) {
				int newBingoCount = currentBingoCount - bingoCount;
				int bingoTurn = turn - lastBingoTurn;

				bingoCount = currentBingoCount;
				lastBingoTurn = turn;

				System.out.println("[ " + Color.ORANGE + bingoTurn + Color.RESET + " TURN BINGO ]");
				Menu.printLine();
				System.out.println("TOTAL BINGO	: " + Color.ORANGE + bingoCount + Color.RESET);
				System.out.println("BINGO 		: " + Color.ORANGE + newBingoCount + Color.RESET);

				if (bingoCount < totalBingo) {
					System.out.print("\n" + Color.ORANGE + "[ENTER]" + Color.RESET + " 次のBINGOへ");
					scanner.nextLine();
				}
			} else {
				System.out.print("\n" + Color.ORANGE + "[ENTER]" + Color.RESET + " 次へ");
				scanner.nextLine();
			}
		}
		showEndlessResult(scanner, player, size, turn, bingoCount);
	}

	public static void startChallenge(Scanner scanner, Player player, int size) {
		int[][] card = BingoCard.createCard(size);
		NumberGenerator generator = new NumberGenerator();

		int turn = 0;
		int limitTurn = size * size;
		boolean isBingo = false;

		while (turn < limitTurn) {
			int number = generator.nextNumber();
			BingoCard.openNumber(card, number);
			turn++;
			System.out.println();
			BingoCard.showCard(card, turn, number);
			if (BingoService.isBingo(card)) {
				isBingo = true;
				break;
			}
			if (turn < limitTurn) {
				System.out.print("\n" + Color.ORANGE + "[ENTER]" + Color.RESET + " 次へ");
				scanner.nextLine();
			}
		}
		showChallengeResult(scanner, player, size, turn, isBingo);
	}

	public static void showNormalResult(Scanner scanner, Player player, int size, int turn) {
		boolean isBest = ScoreService.updateBestTurn(player, size, turn);
		int score = ScoreService.calculateScore(size, turn);
		System.out.print("\n" + Color.ORANGE + "[ENTER]" + Color.RESET + " 結果を見る");
		Input.waitEnter(scanner);
		Menu.printTitle("GAME CLEAR");
		System.out.println("\n┌─ " + Color.ORANGE + "RESULT" + Color.RESET + " ─────────────────────────────┐");
		System.out.println("│ PLAYER	: " + Color.ORANGE + player.getName() + Color.RESET);
		System.out.println("│ SCORE		: " + Color.ORANGE + score + Color.RESET);
		if (isBest) {
			System.out.println("│ BEST TURN	: " + Color.ORANGE + turn + Color.RESET + " (" + Color.ORANGE
					+ " NEW " + Color.RESET + ")");
		} else {
			switch (size) {
			case 3:
				System.out.println("│ BEST TURN	: " + Color.ORANGE + player.getBestTurn3x3() + Color.RESET);
				break;
			case 5:
				System.out.println("│ BEST TURN	: " + Color.ORANGE + player.getBestTurn5x5() + Color.RESET);
				break;
			case 7:
				System.out.println("│ BEST TURN	: " + Color.ORANGE + player.getBestTurn7x7() + Color.RESET);
				break;
			}
		}
		System.out.println("│ TURN		: " + Color.ORANGE + turn + Color.RESET + "");
		System.out.println("└──────────────────────────────────────┘");
		System.out.print(Color.ORANGE + "\n[ENTER]" + Color.RESET + " 終了");
		scanner.nextLine();
	}

	public static void showEndlessResult(Scanner scanner, Player player, int size, int turn, int bingoCount) {
		boolean isBest = player.getEndlessTurn() == 0 || turn < player.getEndlessTurn();
		if (isBest) {
			player.setEndlessTurn(turn);
		}
		System.out.print("\n" + Color.ORANGE + "[ENTER]" + Color.RESET + " 結果を見る");
		Input.waitEnter(scanner);
		Menu.printTitle("GAME CLEAR");
		System.out.println("\n┌─ " + Color.ORANGE + "RESULT" + Color.RESET + " ─────────────────────────────┐");
		System.out.println("│ PLAYER	: " + Color.ORANGE + player.getName() + Color.RESET);
		System.out.println("│ TOTAL BINGO	: " + Color.ORANGE + bingoCount + Color.RESET);
		if (isBest) {
			System.out.println("│ BEST TURN	: " + Color.ORANGE + turn + Color.RESET + " (" + Color.ORANGE
					+ "NEW" + Color.RESET + ")");
		} else {
			System.out.println("│ BEST TURN	: " + Color.ORANGE + player.getEndlessTurn() + Color.RESET);
		}
		System.out.println("│ TURN		: " + Color.ORANGE + turn + Color.RESET + "");
		System.out.println("└──────────────────────────────────────┘");
		System.out.print(Color.ORANGE + "\n[ENTER]" + Color.RESET + " 終了");
		scanner.nextLine();
	}

	public static void showChallengeResult(Scanner scanner, Player player, int size, int turn, boolean isBingo) {
		int limitTurn = size * size;
		int remainingTurn = limitTurn - turn;
		System.out.print("\n" + Color.ORANGE + "[ENTER]" + Color.RESET + " 結果を見る");
		Input.waitEnter(scanner);
		if (isBingo) {
			Menu.printTitle("CHALLENGE CLEAR");
		} else {
			Menu.printTitle("CHALLENGE FAILED");
		}
		System.out.println("\n┌─ " + Color.ORANGE + "RESULT" + Color.RESET + " ─────────────────────────────┐");
		System.out.println("│ PLAYER	: " + Color.ORANGE + player.getName() + Color.RESET);
		System.out.println("│ LIMIT TURN	: " + Color.ORANGE + limitTurn + Color.RESET);
		System.out.println("│ TURN		: " + Color.ORANGE + turn + Color.RESET);
		if (isBingo) {
			System.out.println("│ REMAINING	: " + Color.ORANGE + remainingTurn + Color.RESET);
			System.out.println("│ RESULT	: " + Color.ORANGE + "CLEAR" + Color.RESET);
			player.setChallengeCleared(true);
		} else {
			System.out.println("│ RESULT	: " + Color.ORANGE + "FAILED" + Color.RESET);
		}
		System.out.println("└──────────────────────────────────────┘");
		System.out.print(Color.ORANGE + "\n[ENTER]" + Color.RESET + " 終了");
		scanner.nextLine();

	}
}
