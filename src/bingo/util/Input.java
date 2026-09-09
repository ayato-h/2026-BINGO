package bingo.util;

import java.util.Scanner;

public class Input {
	public static int nextInt(Scanner scanner) {
		while (!scanner.hasNextInt()) {
			System.out.print(Color.RED + "> 数字を入力してください。" + Color.RESET + "\n▶ ");
			scanner.nextLine();
		}
		int value = scanner.nextInt();
		scanner.nextLine();
		return value;
	}

	public static int nextInt(Scanner scanner, int min, int max) {
		while (true) {
			int value = nextInt(scanner);
			if (value >= min && value <= max) {
				return value;
			}
			System.out.print(Color.RED + "> " + min + "～" + max + "の数字を入力してください。" + Color.RESET + "\n▶ ");
		}
	}

	public static void waitEnter(Scanner scanner) {
		long start = System.currentTimeMillis();
		while (true) {
			if (scanner.hasNextLine()) {
				scanner.nextLine();

				if (System.currentTimeMillis() - start >= 500) {
					break;
				}
			}
		}
	}
}