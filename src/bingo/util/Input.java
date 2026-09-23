package bingo.util;

import java.util.Scanner;

public class Input {
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

	public static boolean exit(Scanner scanner) {
		String input = scanner.nextLine();

		if (input.equalsIgnoreCase("exit")) {
			return true;
		}

		return false;
	}
}