package generala.objects;

import generala.enums.CombinationEnum;
import generala.utils.CombinationFinder;

import java.util.EnumSet;
import java.util.Map;

public class Player {
    private String name;
    private DiceRoll diceRoll = new DiceRoll();
    private EnumSet<CombinationEnum> rolledCombinations = EnumSet.noneOf(CombinationEnum.class);
    private CombinationFinder combinationFinder = new CombinationFinder();

    private int score = 0;

    public Player() {
    }

    public Player(String name) {
        this.name = name;
    }

    public EnumSet<CombinationEnum> getRolledCombinations() {
        return rolledCombinations;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public DiceRoll getDiceRoll() {
        return diceRoll;
    }

    public void setDiceRoll(DiceRoll diceRoll) {
        this.diceRoll = diceRoll;
    }

    public CombinationEnum addScore() {
        Map<CombinationEnum, Integer> combinationTreeMap = combinationFinder.findCombinationsInPlayerDiceRoll(this);
        CombinationEnum biggestCombination = null;
        int biggestCombinationValue = 0;
        //todo:Remove(only for Test)
        System.out.println(combinationTreeMap);
        for (Map.Entry<CombinationEnum, Integer> currentCombinationMapEntry : combinationTreeMap.entrySet()) {
            if (currentCombinationMapEntry.getKey().equals(CombinationEnum.GENERALA)) {
                biggestCombination = CombinationEnum.GENERALA;
                biggestCombinationValue = currentCombinationMapEntry.getValue();
                break;
            } else if (currentCombinationMapEntry.getValue() > biggestCombinationValue) {
                biggestCombination = currentCombinationMapEntry.getKey();
                biggestCombinationValue = currentCombinationMapEntry.getValue();
            }
        }
        if (biggestCombination != null) {
            rolledCombinations.add(biggestCombination);
            score += biggestCombinationValue;
        }
        return biggestCombination;
    }
}
