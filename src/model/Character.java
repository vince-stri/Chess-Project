package model;

import java.util.ArrayList;
import java.util.List;

public class Character {
    private int healthPoints;

    private String name;

    private CharacterType characterType;

    private Coordinates[] moves;

    private int damagePoints;

    private int maxHP;

    private int armor;

    private int maxArmor;

    public Cell cell;

    public List<Treasure> treasures = new ArrayList<Treasure> ();

    public Army army;

    public List<Coordinates> coordinates = new ArrayList<Coordinates> ();

    public int goTo(Cell destination) {
    	return 0;
    }

    public void addTreasure(Treasure reward) {
    }

    public boolean strike(Character striked) {
    	return false;
    }

    public boolean takeDamages(int damages) {
    	return false;
    }

    public Character(int healthPoints, String name, CharacterType charType, Cell cell) {
    }

    public boolean isDead() {
    	return false;
    }

    public void addHP(int hp) {
    }

    public void addArmor(int armor) {
    }

    public void addDamage(int damages) {
    }

    public void kill() {
    }

    public void heal() {
    }

}
