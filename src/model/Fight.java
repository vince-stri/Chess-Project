package model;

import java.util.ArrayList;
import java.util.List;

public class Fight {
    private FightState fightState;

    public Treasure treasure;

    public List<Character> fighters = new ArrayList<Character> ();

    public Dice dice;

    public boolean startFight(Character challenger, Character challenged) {
    	return false;
    }

}
