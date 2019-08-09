package generala.objects;


import generala.enums.CombinationEnum;

import java.util.EnumSet;

public class Player {
    private String name;
    private DiceRoll diceRollObj = new DiceRoll();
    private EnumSet<CombinationEnum> rolledCombinations = EnumSet.noneOf(CombinationEnum.class);
    private int score = 0;

    public Player() {
    }

    public Player(String name) {
        this.name = name;
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

    public DiceRoll getDiceRollObj() {
        return diceRollObj;
    }

    public void setDiceRollObj(DiceRoll diceRollObj) {
        this.diceRollObj = diceRollObj;
    }
}
