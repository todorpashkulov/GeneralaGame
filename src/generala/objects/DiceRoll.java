package generala.objects;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class DiceRoll {

    private static int diceCount;
    private static int diceSidesCount;
    private String diceRoll;
    private Map<Integer, Integer> eachSideDuplicatesTreeMapReversed = new TreeMap<>(Comparator.reverseOrder());

    public DiceRoll() {
    }

    public static int getDiceCount() {
        return diceCount;
    }

    public static void setDiceCount(int diceCount) {
        DiceRoll.diceCount = diceCount;
    }

    public static int getDiceSidesCount() {
        return diceSidesCount;
    }

    public static void setDiceSidesCount(int diceSidesCount) {
        DiceRoll.diceSidesCount = diceSidesCount;
    }

    public void setDiceRoll(String diceRoll) {
        this.diceRoll = diceRoll;
    }

    public Map<Integer, Integer> getEachSideDuplicatesTreeMapReversed() {
        return eachSideDuplicatesTreeMapReversed;
    }

    @Override
    public String toString() {
        return "Dice roll: " + diceRoll;
    }
}
