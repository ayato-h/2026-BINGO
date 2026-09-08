package bingo.model;

public class Player {
	private String name;
	private String mode;

	private int bestTurn3x3;
	private int bestTurn5x5;
	private int bestTurn7x7;
	private int endlessTurn;

	public Player(String name) {
		this.name = name;
		//	this.mode = "NORMAL";
		this.mode = "ENDLESS";

		bestTurn3x3 = 0;
		bestTurn5x5 = 0;
		bestTurn7x7 = 0;
		endlessTurn = 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public int getBestTurn3x3() {
		return bestTurn3x3;
	}

	public void setBestTurn3x3(int bestTurn3x3) {
		this.bestTurn3x3 = bestTurn3x3;
	}

	public int getBestTurn5x5() {
		return bestTurn5x5;
	}

	public void setBestTurn5x5(int bestTurn5x5) {
		this.bestTurn5x5 = bestTurn5x5;
	}

	public int getBestTurn7x7() {
		return bestTurn7x7;
	}

	public void setBestTurn7x7(int bestTurn7x7) {
		this.bestTurn7x7 = bestTurn7x7;
	}

	public int getEndlessTurn() {
		return endlessTurn;
	}

	public void setEndlessTurn(int endlessTurn) {
		this.endlessTurn = endlessTurn;
	}
}
