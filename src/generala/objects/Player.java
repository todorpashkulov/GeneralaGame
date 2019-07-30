package generala.objects;


import generala.CombinationEnum;

import java.util.EnumSet;

public class Player {
    private String name;
    private String diceRoll;
    private EnumSet<CombinationEnum> rolledCombinations = EnumSet.noneOf(CombinationEnum.class);
    private int score;

    public Player() {
    }

    public Player(String name) {
        this.name = name;
    }

    public String getDiceRoll() {
        return diceRoll;
    }

    public void setDiceRoll(String diceRoll) {
        this.diceRoll = diceRoll;
    }

    public EnumSet<CombinationEnum> getRolledCombinations() {
        return rolledCombinations;
    }

    public void setRolledCombinations(EnumSet<CombinationEnum> rolledCombinations) {
        this.rolledCombinations = rolledCombinations;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
