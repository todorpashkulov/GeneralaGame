package generala.combinatios;

import generala.enums.CombinationEnum;
import generala.objects.DiceRoll;

import java.util.Map;

import static generala.constants.GeneralaConstants.*;

public final class Combinations {

    private int straightCounter;
    private int dieSideForCalculatingStraight;

    private boolean canAddFullHouse(int currentDieSide, Map<CombinationEnum, Integer> combinationTreeMap) {
        return combinationTreeMap.containsKey(CombinationEnum.TRIPLE)
                && (combinationTreeMap.get(CombinationEnum.TRIPLE) / TRIPLE_MULTIPLIER) != currentDieSide;
    }

    public void addFullHouse(int currentDieSide, Map<CombinationEnum, Integer> combinationTreeMap) {
        int tripleValue;
        int pairValue = combinationTreeMap.get(CombinationEnum.PAIR) / PAIR_MULTIPLIER;
        int biggestPair;
        int currentFullHouseValue;
        boolean hasFullHouse = combinationTreeMap.containsKey(CombinationEnum.FULL_HOUSE);
        int comboMapFullHouse = 0;

        if (canAddFullHouse(currentDieSide, combinationTreeMap)) {
            tripleValue = combinationTreeMap.get(CombinationEnum.TRIPLE) / TRIPLE_MULTIPLIER;

            if (pairValue > currentDieSide && pairValue != tripleValue) {
                biggestPair = pairValue;
            } else {
                biggestPair = currentDieSide;
            }
            currentFullHouseValue = biggestPair * PAIR_MULTIPLIER + tripleValue * TRIPLE_MULTIPLIER;

            if (hasFullHouse) {
                comboMapFullHouse = combinationTreeMap.get(CombinationEnum.FULL_HOUSE);
            }
            if (currentFullHouseValue > comboMapFullHouse) {
                combinationTreeMap.put(CombinationEnum.FULL_HOUSE, currentFullHouseValue);
            }
        }

    }

    public boolean hasGenerala(int currentDieSideDuplicates) {
        return currentDieSideDuplicates == DiceRoll.getNumberOfDice();
    }

    public void addGenerala(int currentDieSide, Map<CombinationEnum, Integer> combinationTreeMap) {

        combinationTreeMap.put(CombinationEnum.GENERALA, currentDieSide * DiceRoll.getNumberOfDice());

    }

    public void addFourOfAKind(int currentDieSide, Map<CombinationEnum, Integer> comboMap) {
        if (!comboMap.containsKey(CombinationEnum.FOUR_OF_A_KIND)) {
            comboMap.put(CombinationEnum.FOUR_OF_A_KIND, currentDieSide * FOUR_MULTIPLIER);
        } else if (currentDieSide > comboMap.get(CombinationEnum.FOUR_OF_A_KIND) / FOUR_MULTIPLIER) {
            comboMap.put(CombinationEnum.FOUR_OF_A_KIND, currentDieSide * FOUR_MULTIPLIER);
        }

    }

    public void addTriple(int currentDieSide, Map<CombinationEnum, Integer> combinationTreeMap) {
        if (!combinationTreeMap.containsKey(CombinationEnum.TRIPLE)) {
            combinationTreeMap.put(CombinationEnum.TRIPLE, currentDieSide * TRIPLE_MULTIPLIER);
        } else if (currentDieSide > combinationTreeMap.get(CombinationEnum.TRIPLE) / TRIPLE_MULTIPLIER) {
            combinationTreeMap.put(CombinationEnum.TRIPLE, currentDieSide * TRIPLE_MULTIPLIER);
        }
    }

    public void addPair(int currentDieSide, Map<CombinationEnum, Integer> combinationTreeMap) {

        if (!combinationTreeMap.containsKey(CombinationEnum.PAIR)) {
            combinationTreeMap.put(CombinationEnum.PAIR, currentDieSide * PAIR_MULTIPLIER);
        } else if (currentDieSide > combinationTreeMap.get(CombinationEnum.PAIR) / PAIR_MULTIPLIER) {
            combinationTreeMap.put(CombinationEnum.PAIR, currentDieSide * PAIR_MULTIPLIER);
        }

    }

    private boolean canAddDoublePair(int currentDieSide, boolean containsPair, int pairValue) {
        return containsPair && pairValue / PAIR_MULTIPLIER != currentDieSide;
    }

    public void addDoublePair(int currentDieSide, Map<CombinationEnum, Integer> combinationTreeMap) {
        int doublePairComboMapValue = 0;
        int pairComboMapValue;

        if (canAddDoublePair(currentDieSide,
                combinationTreeMap.containsKey(CombinationEnum.PAIR),
                combinationTreeMap.get(CombinationEnum.PAIR))) {

            pairComboMapValue = combinationTreeMap.get(CombinationEnum.PAIR) / PAIR_MULTIPLIER;

            if (combinationTreeMap.containsKey(CombinationEnum.DOUBLE_PAIR)) {

                doublePairComboMapValue = combinationTreeMap.get(CombinationEnum.DOUBLE_PAIR) / PAIR_MULTIPLIER;
            }

            if (pairComboMapValue + currentDieSide > doublePairComboMapValue) {

                combinationTreeMap.put(CombinationEnum.DOUBLE_PAIR,
                        (pairComboMapValue + currentDieSide) * PAIR_MULTIPLIER);
            }
        }

    }

    public boolean canAddStraight(Map<CombinationEnum, Integer> combinationTreeMap) {
        return combinationTreeMap.containsKey(CombinationEnum.STRAIGHT);
    }

    public boolean findStraight(int currentDieSide) {
        if (straightCounter == 0) {
            straightCounter += 1;
            dieSideForCalculatingStraight = currentDieSide;
            return false;
        }
        if (currentDieSide == dieSideForCalculatingStraight - straightCounter) {
            straightCounter += 1;
        } else {
            straightCounter = 0;
        }
        if (straightCounter == STRAIGHT_SIZE) {
            return true;
        }
        return false;

    }

    public void addStraight(int currentDieSide, Map<CombinationEnum, Integer> combinationTreeMap) {

        if (findStraight(currentDieSide)) {
            combinationTreeMap.put(CombinationEnum.STRAIGHT, countStraight(dieSideForCalculatingStraight));
            straightCounter = 0;
            dieSideForCalculatingStraight = 0;
        }

    }

    private int countStraight(int dieSide) {
        int sum = 0;
        for (int i = dieSide, counter = 0; counter < STRAIGHT_SIZE; i--, counter++) {
            sum += i;
        }
        return sum;

    }

}
