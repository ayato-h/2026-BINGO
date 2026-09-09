package bingo;

import java.util.Scanner;

import bingo.game.Game;
import bingo.model.Player;
import bingo.util.Color;
import bingo.util.Input;
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
			int menu = Input.nextInt(scanner, 0, 3);
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
			int select = Input.nextInt(scanner, 0, 2);
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