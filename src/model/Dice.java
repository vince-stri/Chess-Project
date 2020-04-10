package model;

import java.util.Random;

public class Dice {
	private Random r = new Random();
	private int min;
	private int max;

	public Dice() {
		this.min = 1;
		this.max = 6;
	}
	
	public Dice(int max) {
		this.max = max;
	}
	
	public Dice(int min, int max) {
		this.min = min;
		this.max = max;
	}
	
	public int roll() {
		return r.nextInt(this.max - this.min+ 1) + this.min;
	}
	
    public int roll(int min, int max) {
    	return r.nextInt(max - min + 1) + min;
    }

}
