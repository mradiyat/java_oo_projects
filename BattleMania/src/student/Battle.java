	package student;

import java.util.Random;

public class Battle implements Comparable<Battle> {
	
	public static final int HERO_WON = 0;
	public static final int ENEMY_WON = 1;
	
	private Hero hero;
	private Enemy enemy;
	
	private static Random rng = new Random(123456789);
	
	public Battle(Hero hero, Enemy enemy) {
		this.hero = hero;
		this.enemy = enemy;
	}
	
	/**
	 * Calculates the outcome of fighting the hero with the enemy. Based on
	 * generating a random double using <i>rng</i>, the hero wins if the 
	 * generated double is greater than the probability of the hero winning, 
	 * and the enemy wins otherwise.
	 * @return <i>HERO_WON</i> if the hero wins the simulated battle, and
	 * <i>ENEMY_WON</i> if the enemy wins
	 */
	public int getOutcome() {
		if (hero == null || enemy == null) {
			return -1;
		}
		double outcomeProb = getProbHeroWinning();
		double actualOutcome= rng.nextDouble();
		return (actualOutcome < outcomeProb ? HERO_WON : ENEMY_WON);
	}
	
	/**
	 * Calculates the probability of the hero winning using the formula given
	 * in the writeup.
	 * @return The probability that the hero would win against the enemy
	 */
	public double getProbHeroWinning() {
		if (hero == null || enemy == null) {
			return 0;
		}
		double heroStr = hero.getStrength();
		double heroInt = hero.getIntelligence();
		double heroAgl = hero.getAgility();
		double enemyStr = enemy.getStrength();
		double enemyAgl = enemy.getAgility();
		double enemyInt = enemy.getIntelligence();
		
		return ((heroStr / (heroStr + enemyStr)) + 
				(heroAgl / (heroAgl + enemyAgl)) + 
				(heroInt / (heroInt + enemyInt))) / 3;
	}
	
	/**
	 * Calculates and returns the number of points that will be awarded to
	 * the hero if the hero wins.
	 * @return The number of points to be allocated to the hero if winning
	 */
	public int getPointsHeroWin() {
		return (int) (10 / getProbHeroWinning());
	}
	
	/**
	 * @return The hero in this battle
	 */
	public Hero getHero() {
		return hero;
	}
	
	/**
	 * @return The enemy in this battle
	 */
	public Enemy getEnemy() {
		return enemy;
	}

	/**
	 * Compares this battle to a different battle. Returns -1 if the hero is
	 * more likely to win in this battle object compared to 
	 * <i>secondBattle</i>, 0 if the probabilities are the same, and 1 if the
	 * hero is more likely to win in <i>secondBattle</i> compared to this 
	 * battle
	 */
	@Override
	public int compareTo(Battle secondBattle) {
		if (secondBattle == null) {
			return 0;
		}
		if (secondBattle.getProbHeroWinning() == getProbHeroWinning()) {
			return 0;
		}
		if (getProbHeroWinning() > secondBattle.getProbHeroWinning()) {
			return -1;
		}
		else {
			return 1;
		}
	}
	
}
