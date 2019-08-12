package generala.objects;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class DiceRoll {
    //todo fix final
    private static int numberOfDice = 5;
    private static int numberOfDiceSides = 6;
    private static int straightSize = 5;
    //todo toString change name
    private String diceRollString;
    //todo fix name
    private Map<Integer, Integer> dieSideDuplicatesMap = new TreeMap<>(Comparator.reverseOrder());

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

    public static int getStraightSize() {
        return straightSize;
    }

    public static void setStraightSize(int straightSize) {
        DiceRoll.straightSize = straightSize;
    }

    public String getDiceRollString() {
        return diceRollString;
    }

    public void setDiceRollString(String diceRollString) {
        this.diceRollString = diceRollString;
    }

    public Map<Integer, Integer> getDieSideDuplicatesMap() {
        return dieSideDuplicatesMap;
    }

    public void setDieSideDuplicatesMap(Map<Integer, Integer> dieSideDuplicatesMap) {
        this.dieSideDuplicatesMap = dieSideDuplicatesMap;
    }
}
