package generala.generalahelpers;

import generala.enums.CombinationEnum;
import generala.objects.DiceRoll;

import java.util.Map;

import static generala.generalahelpers.Constants.*;


public final class Combinations {


    public boolean canAddFullHouse(int dieSide, Map<CombinationEnum, Integer> combinationTreeMap) {
        return combinationTreeMap.containsKey(CombinationEnum.TRIPLE)
                && (combinationTreeMap.get(CombinationEnum.TRIPLE) / TRIPLE_MULTIPLIER) != dieSide;
    }

    public void addFullHouse(int dieSide, Map<CombinationEnum, Integer> combinationTreeMap) {
        int tripleValue;
        int pairValue = combinationTreeMap.get(CombinationEnum.PAIR) / PAIR_MULTIPLIER;
        int biggestPair;
        int currentFullHouseValue;
        boolean hasFullHouse = combinationTreeMap.containsKey(CombinationEnum.FULL_HOUSE);
        int comboMapFullHouse = 0;

        if (canAddFullHouse(dieSide, combinationTreeMap)) {
            tripleValue = combinationTreeMap.get(CombinationEnum.TRIPLE) / TRIPLE_MULTIPLIER;

            if (pairValue > dieSide && pairValue != tripleValue) {
                biggestPair = pairValue;
            } else {
                biggestPair = dieSide;
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

    public boolean hasGenerala(int dieSideDuplicates) {
        return dieSideDuplicates == DiceRoll.getNumberOfDice();
    }

    public void addGenerala(int dieSide, Map<CombinationEnum, Integer> combinationTreeMap) {


        combinationTreeMap.put(CombinationEnum.GENERALA, dieSide * DiceRoll.getNumberOfDice());

    }

    public void addFourOfAKind(int dieSide, Map<CombinationEnum, Integer> comboMap) {
        if (!comboMap.containsKey(CombinationEnum.FOUR_OF_A_KIND)) {
            comboMap.put(CombinationEnum.FOUR_OF_A_KIND, dieSide * FOUR_MULTIPLIER);
        } else if (dieSide > comboMap.get(CombinationEnum.FOUR_OF_A_KIND) / FOUR_MULTIPLIER) {
            comboMap.put(CombinationEnum.FOUR_OF_A_KIND, dieSide * FOUR_MULTIPLIER);
        }

    }

    public void addTriple(int dieSide, Map<CombinationEnum, Integer> combinationTreeMap) {

        if (!combinationTreeMap.containsKey(CombinationEnum.TRIPLE)) {
            combinationTreeMap.put(CombinationEnum.TRIPLE, dieSide * TRIPLE_MULTIPLIER);
        } else if (dieSide > combinationTreeMap.get(CombinationEnum.TRIPLE) / TRIPLE_MULTIPLIER) {
            combinationTreeMap.put(CombinationEnum.TRIPLE, dieSide * TRIPLE_MULTIPLIER);
        }


    }

    public void addPair(int dieSide, Map<CombinationEnum, Integer> combinationTreeMap) {

        if (!combinationTreeMap.containsKey(CombinationEnum.PAIR)) {
            combinationTreeMap.put(CombinationEnum.PAIR, dieSide * PAIR_MULTIPLIER);
        } else if (dieSide > combinationTreeMap.get(CombinationEnum.PAIR) / PAIR_MULTIPLIER) {
            combinationTreeMap.put(CombinationEnum.PAIR, dieSide * PAIR_MULTIPLIER);
        }

    }

    private boolean canAddDoublePair(int dieSide, boolean containsPair, int pairValue) {
        return containsPair
                && pairValue / PAIR_MULTIPLIER != dieSide;
    }

    public void addDoublePair(int dieSide, Map<CombinationEnum, Integer> combinationTreeMap) {
        int doublePairComboMapValue = 0;
        int pairComboMapValue;

        if (canAddDoublePair(dieSide
                , combinationTreeMap.containsKey(CombinationEnum.PAIR)
                , combinationTreeMap.get(CombinationEnum.PAIR))) {

            pairComboMapValue = combinationTreeMap.get(CombinationEnum.PAIR) / PAIR_MULTIPLIER;

            if (combinationTreeMap.containsKey(CombinationEnum.DOUBLE_PAIR)) {

                doublePairComboMapValue = combinationTreeMap.get(CombinationEnum.DOUBLE_PAIR) / PAIR_MULTIPLIER;
            }

            if (pairComboMapValue + dieSide > doublePairComboMapValue) {

                combinationTreeMap.put(CombinationEnum.DOUBLE_PAIR, (pairComboMapValue + dieSide) * PAIR_MULTIPLIER);
            }
        }

    }

    public boolean canAddStraight(Map<CombinationEnum, Integer> combinationTreeMap) {
        return combinationTreeMap.containsKey(CombinationEnum.STRAIGHT);
    }

    public int[] addStraightIfPossible(int currentDieSide
            , int[] counter
            , Map<CombinationEnum, Integer> combinationTreeMap) {
        //Counter array size of 2: element 0=counter element 1= side for calculating straight score


        if (counter[0] == 0) {
            counter[0] += 1;
            counter[1] = currentDieSide;
            return counter;
        }
        if (currentDieSide == counter[1] - counter[0]) {
            counter[0] += 1;
        } else {
            counter[0] = 0;
        }


        if (counter[0] == DiceRoll.getStraightSize()) {
            combinationTreeMap.put(CombinationEnum.STRAIGHT, countStraight(counter[1]));
        }

        return counter;
    }

    public int countStraight(int dieSide) {
        int sum = 0;
        for (int i = dieSide, counter = 0; counter < DiceRoll.getStraightSize(); i--, counter++) {
            sum += i;
        }

        return sum;

    }


}
