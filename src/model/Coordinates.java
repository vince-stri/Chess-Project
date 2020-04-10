package model;


public class Coordinates {
    private int x;

    private int y;

    private int z;

    public Coordinates(int x, int y, int z) {
    	this.x = x;
    	this.y = y;
    	this.z = z;
    }

	public boolean equals(int x, int y, int z) {
		return this.x == x && this.y == y && this.z == z; 
	}
	
	public String toString() {
		return "(" + x + "," + y + "," + z + ")";
	}

}
