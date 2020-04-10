package model;


public class Coordinates {
    private int x;
    private int y;
    private int z;

    public Coordinates(int x, int y) {
    	this.x = x;
    	this.y = y;
    	this.z = 0;
    }
    
    public Coordinates(int x, int y, int z) {
    	this.x = x;
    	this.y = y;
    	this.z = z;
    }

    public boolean equals(int x, int y) {
		return this.x == x && this.y == y; 
	}
    
	public boolean equals(int x, int y, int z) {
		return this.x == x && this.y == y && this.z == z; 
	}
	
	public String toString() {
		return "(" + x + "," + y + "," + z + ")";
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getZ() {
		return z;
	}

}
