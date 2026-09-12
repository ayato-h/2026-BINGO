package bingo.service;

import bingo.model.Player;

public class ScoreService {
	public static boolean updateBestTurn(Player player, int size, int turn) {
		switch (size) {
		case 3:
			if (player.getBestTurn3x3() == 0 || turn < player.getBestTurn3x3()) {
				player.setBestTurn3x3(turn);
				return true;
			}
			break;
		case 5:
			if (player.getBestTurn5x5() == 0 || turn < player.getBestTurn5x5()) {
				player.setBestTurn5x5(turn);
				return true;
			}
			break;
		case 7:
			if (player.getBestTurn7x7() == 0 || turn < player.getBestTurn7x7()) {
				player.setBestTurn7x7(turn);
				return true;
			}
			break;
		}
		return false;
	}

	public static int calculateScore(int size, int turn) {
		int baseScore;
		switch (size) {
		case 3:
			baseScore = 1000;
			break;
		case 5:
			baseScore = 2000;
			break;
		case 7:
			baseScore = 3000;
			break;
		default:
			baseScore = 0;
		}

		int score = baseScore - (turn - 1) * 50;
		return Math.max(score, 100);

	}
}
