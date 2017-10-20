package student;

public class Hero extends Character {
	
	private int numberLives;
	private int numberVictories;
	
	public Hero(String name, int strength, int agility, int intelligence,
			String taunt, String imgLoc, int numberLives) {
		super(name, strength, agility, intelligence, taunt, imgLoc);
		this.numberLives = numberLives;
		numberVictories = 0;
	}
	
	/**
	 * Increments the strength ability by 1 point
	 */
	public void incrementStrength() {
		strength++;
	}
	
	/**
	 * Increments the agility ability by 1 point
	 */
	public void incrementAgility() {
		agility++;
	}
	
	/**
	 * Increments the intelligence ability by 1 point
	 */
	public void incrementIntelligence() {
		intelligence++;
	}
	
	/**
	 * @return The current number of lives for this hero
	 */
	public int getNumberLives() {
		return numberLives;
	}
	
	/**
	 * Decrements the number of lives by 1 life
	 */
	public void decrementLives() {
		numberLives--;
	}
	
	/**
	 * Increments the number of victories for this hero by 1
	 */
	public void incrementVictories() {
		numberVictories++;
	}
	
	/**
	 * @return The number of victories obtained by this hero in the game
	 */
	public int getNumberOfVictories() {
		return numberVictories;
	}
}
