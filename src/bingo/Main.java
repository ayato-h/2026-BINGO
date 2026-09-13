package bingo;

import java.util.InputMismatchException;
import java.util.Scanner;

import bingo.game.Game;
import bingo.model.Player;
import bingo.util.Color;
import bingo.util.Menu;

public class Main {
	private static Player player;

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		setUpPlayer(scanner);
		Menu.showTitle();
		System.out.print(Color.ORANGE + "[ENTER]" + Color.RESET + " 開始");
		scanner.nextLine();
		while (true) {
			Menu.showMainMenu(player);
			int menu;
			while (true) {
				try {
					menu = scanner.nextInt();
					scanner.nextLine();
					if (0 <= menu && menu <= 3) {
						break;
					}
					System.out.print(Color.RED + "> 0～3の数字を入力してください。" + Color.RESET + "\n▶ ");
				} catch (InputMismatchException e) {
					scanner.nextLine();
					System.out.print(Color.RED + "> 数字を入力してください。" + Color.RESET + "\n▶ ");
					continue;
				}
			}
			switch (menu) {
			case 1:
				Game.showGameMenu(scanner, player);
				break;
			case 2:
				showSettings(scanner, player);
				break;
			case 3:
				Menu.showHowToPlay();
				System.out.print("\n" + Color.ORANGE + "[ENTER]" + Color.RESET + " 戻る");
				scanner.nextLine();
				break;
			case 0:
				Menu.exit();
				scanner.close();
				return;
			}
		}
	}

	private static void setUpPlayer(Scanner scanner) {
		Menu.printTitle("PLAYER SETUP");
		if (player == null) {
			System.out.print("プレイヤー名 ▶ " + Color.YELLOW);
			String name = scanner.nextLine();
			player = new Player(name);
			System.out.print(Color.RESET);
		}
	}

	public static void showSettings(Scanner scanner, Player player) {
		while (true) {
			Menu.showSettings(player);
			int select;
			while (true) {
				try {
					select = scanner.nextInt();
					scanner.nextLine();
					if (0 <= select && select <= 2) {
						break;
					}
					System.out.print(Color.RED + "> 0～2の数字を入力してください。" + Color.RESET + "\n▶ ");
				} catch (InputMismatchException e) {
					scanner.nextLine();
					System.out.print(Color.RED + "> 数字を入力してください。" + Color.RESET + "\n▶ ");
					continue;
				}
			}
			switch (select) {
			case 1:
				System.out.print("\n新しい名前 ▶ " + Color.YELLOW);
				String name = scanner.nextLine();
				player.setName(name);
				System.out.print(Color.RESET);
				break;
			case 2:
				Menu.showMode(player, scanner);
				break;
			case 3:
				System.out.println("文字色設定は現在制作中です。");
				break;
			case 0:
				return;
			}
		}
	}
}