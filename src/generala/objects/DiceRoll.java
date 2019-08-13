package generala.objects;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class DiceRoll {
    //cant be final TODO: ask
    private static int numberOfDice;
    private static int numberOfDiceSides;
    private String diceRoll;
    private Map<Integer, Integer> numberOfSideDuplicatesTreeMap = new TreeMap<>(Comparator.reverseOrder());

    public DiceRoll() {
    }

    public static int getNumberOfDice() {
        return numberOfDice;
    }

    public static void setNumberOfDice(int numberOfDice) {
        DiceRoll.numberOfDice = numberOfDice;
    }

    public static int getNumberOfDiceSides() {
        return numberOfDiceSides;
    }

    public static void setNumberOfDiceSides(int numberOfDiceSides) {
        DiceRoll.numberOfDiceSides = numberOfDiceSides;
    }

    public void setDiceRoll(String diceRoll) {
        this.diceRoll = diceRoll;
    }

    public Map<Integer, Integer> getNumberOfSideDuplicatesTreeMap() {
        return numberOfSideDuplicatesTreeMap;
    }

    @Override
    public String toString() {
        return "Dice roll: " + diceRoll;
    }
}
