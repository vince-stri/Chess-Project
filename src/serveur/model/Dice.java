package serveur.model;

import java.util.Random;

/**
 * A role-play class to generate random numbers
 * @version 1.0
 * @author Hugo Bonnet
 */
public class Dice {
	
	/**
	 * The random class to generate random numbers 
	 */
	private Random r = new Random();
	
	/**
	 * The minimal value of the dice
	 */
	private int min;
	
	/**
	 * The maximal value of the dice
	 */
	private int max;

	/**
	 * Constructor of a basic dice
	 */
	public Dice() {
		this.min = 1;
		this.max = 6;
	}
	
	/**
	 * Constructor of a dice with a custom maximal value
	 * @param max the maximal value
	 */
	public Dice(int max) {
		this.max = max;
	}
	
	/**
	 * Constructor of a customizable dice
	 * @param min the minimal value of the dice
	 * @param max the maximal value of the dice
	 */
	public Dice(int min, int max) {
		this.min = min;
		this.max = max;
	}
	
	/**
	 * Generate a random number
	 * @return the random number
	 */
	public int roll() {
		return r.nextInt(this.max - this.min+ 1) + this.min;
	}
	
	/**
	 * Generate an entire random value 
	 * @param min the minimal value of the random number
	 * @param max the maximal value of the random number
	 * @return the random number
	 */
    public int roll(int min, int max) {
    	return r.nextInt(max - min + 1) + min;
    }

}
