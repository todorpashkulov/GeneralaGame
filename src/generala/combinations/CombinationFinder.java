package generala.combinations;

import generala.enums.CombinationEnum;
import generala.objects.DiceRoll;
import generala.objects.Player;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import static generala.constants.GeneralaConstants.*;

public final class CombinationFinder {
    private int straightCounter;
    private int dieSideForCalculatingStraightPoints;

    private int biggestFourOfAKind;
    private int biggestTriple;
    private int biggestPair;
    private int secondBiggestPair;


    public Map<CombinationEnum, Integer> findCombinationsInPlayerDiceRoll(Player player) {
        int currentSide;
        int currentSideValue;
        Map<CombinationEnum, Integer> combinationTreeMapReversed = new TreeMap<>(Collections.reverseOrder());
        Map<Integer, Integer> eachSideDuplicates = player.getDiceRoll().getEachSideDuplicatesTreeMapReversed();
        for (Map.Entry<Integer, Integer> currentDieSideEntrySet : eachSideDuplicates.entrySet()) {
            currentSide = currentDieSideEntrySet.getKey();
            currentSideValue = currentDieSideEntrySet.getValue();
            if (hasGenerala(currentDieSideEntrySet.getValue())) {
                addGenerala(currentSide, combinationTreeMapReversed);
                break;
            }
            if (hasAllCombinations(player.getRolledCombinations().size(), combinationTreeMapReversed.size())) {
                break;
            }
            //todo: ask for hasMethods and ask if u should make variables to save in the CombinationFinder.class
           if (currentDieSideEntrySet.getValue() >= FOUR_OF_A_KIND_SIZE) {
                addFourOfAKind(currentSide, combinationTreeMapReversed);
            }
            if (currentDieSideEntrySet.getValue() >= TRIPLE_SIZE) {
                addTriple(currentSide, combinationTreeMapReversed);
            }
            if (currentDieSideEntrySet.getValue() >= PAIR_SIZE) {
                addPair(currentSide, combinationTreeMapReversed);
                addDoublePair(currentSide, combinationTreeMapReversed);
                addFullHouse(currentSide, combinationTreeMapReversed);
            }




            if (!canAddStraight(combinationTreeMapReversed)) {
                addStraight(currentSide, combinationTreeMapReversed);
            }
        }
        //todo:Remove(only for Test)
        System.out.println(combinationTreeMapReversed);
        return combinationTreeMapReversed;
    }


    private boolean canAddFullHouse(int currentDieSide, Map<CombinationEnum, Integer> combinationTreeMap) {
        return combinationTreeMap.containsKey(CombinationEnum.TRIPLE)
                && (combinationTreeMap.get(CombinationEnum.TRIPLE) / TRIPLE_SIZE) != currentDieSide;
    }

    public void addFullHouse(int currentDieSide, Map<CombinationEnum, Integer> combinationTreeMap) {
        int tripleValue;
        int pairValue = combinationTreeMap.get(CombinationEnum.PAIR) / PAIR_SIZE;
        int biggestPair;
        int currentFullHouseValue;
        boolean hasFullHouse = combinationTreeMap.containsKey(CombinationEnum.FULL_HOUSE);
        int combinationTreeMapFullHouse = 0;
        if (canAddFullHouse(currentDieSide, combinationTreeMap)) {
            tripleValue = combinationTreeMap.get(CombinationEnum.TRIPLE) / TRIPLE_SIZE;
            if (pairValue > currentDieSide && pairValue != tripleValue) {
                biggestPair = pairValue;
            } else {
                biggestPair = currentDieSide;
            }
            currentFullHouseValue = biggestPair * PAIR_SIZE + tripleValue * TRIPLE_SIZE;
            if (hasFullHouse) {
                combinationTreeMapFullHouse = combinationTreeMap.get(CombinationEnum.FULL_HOUSE);
            }
            if (currentFullHouseValue > combinationTreeMapFullHouse) {
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

    //TODO: ask about should you relay on sorting
    public void addFourOfAKind(int currentDieSide, Map<CombinationEnum, Integer> comboMap) {
        if (!comboMap.containsKey(CombinationEnum.FOUR_OF_A_KIND)) {
            comboMap.put(CombinationEnum.FOUR_OF_A_KIND, currentDieSide * FOUR_OF_A_KIND_SIZE);
        } else if (currentDieSide > comboMap.get(CombinationEnum.FOUR_OF_A_KIND) / FOUR_OF_A_KIND_SIZE) {
            comboMap.put(CombinationEnum.FOUR_OF_A_KIND, currentDieSide * FOUR_OF_A_KIND_SIZE);
        }

    }

    public void addTriple(int currentDieSide, Map<CombinationEnum, Integer> combinationTreeMap) {
        if (!combinationTreeMap.containsKey(CombinationEnum.TRIPLE)) {
            combinationTreeMap.put(CombinationEnum.TRIPLE, currentDieSide * TRIPLE_SIZE);
        } else if (currentDieSide > combinationTreeMap.get(CombinationEnum.TRIPLE) / TRIPLE_SIZE) {
            combinationTreeMap.put(CombinationEnum.TRIPLE, currentDieSide * TRIPLE_SIZE);
        }
    }

    public void addPair(int currentDieSide, Map<CombinationEnum, Integer> combinationTreeMap) {
        if (!combinationTreeMap.containsKey(CombinationEnum.PAIR)) {
            combinationTreeMap.put(CombinationEnum.PAIR, currentDieSide * PAIR_SIZE);
        } else if (currentDieSide > combinationTreeMap.get(CombinationEnum.PAIR) / PAIR_SIZE) {
            combinationTreeMap.put(CombinationEnum.PAIR, currentDieSide * PAIR_SIZE);
        }
    }

    private boolean canAddDoublePair(int currentDieSide, boolean containsPair, int pairValue) {
        return containsPair && pairValue / PAIR_SIZE != currentDieSide;
    }

    public void addDoublePair(int currentDieSide, Map<CombinationEnum, Integer> combinationTreeMap) {
        int doublePairComboMapValue = 0;
        int pairComboMapValue;

        if (canAddDoublePair(currentDieSide,
                combinationTreeMap.containsKey(CombinationEnum.PAIR),
                combinationTreeMap.get(CombinationEnum.PAIR))) {

            pairComboMapValue = combinationTreeMap.get(CombinationEnum.PAIR) / PAIR_SIZE;

            if (combinationTreeMap.containsKey(CombinationEnum.DOUBLE_PAIR)) {

                doublePairComboMapValue = combinationTreeMap.get(CombinationEnum.DOUBLE_PAIR) / PAIR_SIZE;
            }

            if (pairComboMapValue + currentDieSide > doublePairComboMapValue) {

                combinationTreeMap.put(CombinationEnum.DOUBLE_PAIR,
                        (pairComboMapValue + currentDieSide) * PAIR_SIZE);
            }
        }

    }

    public boolean canAddStraight(Map<CombinationEnum, Integer> combinationTreeMap) {
        return combinationTreeMap.containsKey(CombinationEnum.STRAIGHT);
    }

    //todo:change
    public boolean findStraight(int currentDieSide) {
        if (straightCounter == 0) {
            straightCounter += 1;
            dieSideForCalculatingStraightPoints = currentDieSide;
            return false;
        }
        if (currentDieSide == dieSideForCalculatingStraightPoints - straightCounter) {
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
            combinationTreeMap.put(CombinationEnum.STRAIGHT, countStraightPoints(dieSideForCalculatingStraightPoints));
            straightCounter = 0;
            dieSideForCalculatingStraightPoints = 0;
        }

    }

    private int countStraightPoints(int dieSide) {
        int sum = 0;
        for (int i = dieSide, counter = 0; counter < STRAIGHT_SIZE; i--, counter++) {
            sum += i;
        }
        return sum;

    }

    private boolean hasAllCombinations(int playerRolledCombinationCount, int combinationTreeMapSize) {
        return combinationTreeMapSize == (CombinationEnum.values().length - 1)
                || playerRolledCombinationCount == CombinationEnum.values().length - 1;
    }
}
