package model;

import java.io.Serializable;


/**
 * A class giving coordinates in a discrete 3D space environment 
 * @version 1.0
 * @author Hugo BONNET
 */

@SuppressWarnings("serial")
public class Coordinates implements Serializable {
	/**
	 * Value corresponding to each axes. 
	 */
    private int x;
    private int y;
    private int z;

    /**
     * Constructor for a 2D Coordinates 
     * @param x x-axis
     * @param y y-axis
     */
    public Coordinates(int x, int y) {
    	this.x = x;
    	this.y = y;
    	this.z = 0;
    }
    
    /**
     * Constructor for a 3D Coordinates 
     * @param x x-axis
     * @param y y-axis
     * @param z z-axis
     */
    public Coordinates(int x, int y, int z) {
    	this.x = x;
    	this.y = y;
    	this.z = z;
    }

    /**
     * Override of the equals method.
     * Check the equality between two 2D coordinates
     * @param x x-axis to compare
     * @param y y-axis to compare
     * @return true or false according to the equality
     */
    public boolean equals(int x, int y) {
		return this.x == x && this.y == y; 
	}
    
    /**
     * Override of the equals method.
     * Check the equality between two 3D coordinates
     * @param x x-axis to compare
     * @param y y-axis to compare
     * @param z z-axis to compare
     * @return true or false according to the equality
     */
	public boolean equals(int x, int y, int z) {
		return this.x == x && this.y == y && this.z == z; 
	}
	
	/**
     * Override of the equals method.
     * Check the equality between two complete Coordinates classes
     * @param coords coordinates to compare
     * @return true or false according to the equality
     */
	public boolean equals(Coordinates coords) {
		return this.x == coords.getX() && this.y == coords.getY() && this.z == coords.getZ();
	}

	/**
	 * Override of the toString method.
	 * Return parentheses axes;
	 * @return the string describing Coordinates
	 */
	public String toString() {
		return "(" + x + "," + y + "," + z + ")";
	}
	
	/**
	 * Getter of the x-axis
	 * @return x-axis
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Getter of the y-axis
	 * @return y-axis
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Getter of the z-axis
	 * @return z-axis
	 */
	public int getZ() {
		return z;
	}

}
