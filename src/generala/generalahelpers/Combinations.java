package generala.generalahelpers;

import generala.enums.CombinationEnum;
import generala.objects.DiceRoll;

import java.util.Map;

import static generala.generalahelpers.Constants.*;


public final class Combinations {

    public void addFullHouseIfPossible(int dieSide, Map<CombinationEnum, Integer> comboMap) {
        int tripleValue;
        int pairValue = comboMap.get(CombinationEnum.PAIR) / PAIR_MULTIPLIER;
        int biggestPair;
        int currentFullHouseValue;
        boolean hasFullHouse = comboMap.containsKey(CombinationEnum.FULL_HOUSE);
        int comboMapFullHouse = 0;

        if (comboMap.containsKey(CombinationEnum.TRIPLE)
                && (tripleValue = comboMap.get(CombinationEnum.TRIPLE) / TRIPLE_MULTIPLIER) != dieSide) {
            if (pairValue > dieSide && pairValue != tripleValue) {
                biggestPair = pairValue;
            } else {
                biggestPair = dieSide;
            }
            currentFullHouseValue = biggestPair * PAIR_MULTIPLIER + tripleValue * TRIPLE_MULTIPLIER;

            if (hasFullHouse) {
                comboMapFullHouse = comboMap.get(CombinationEnum.FULL_HOUSE);
            }
            if (currentFullHouseValue > comboMapFullHouse) {
                comboMap.put(CombinationEnum.FULL_HOUSE, currentFullHouseValue);
            }


        }
    }

    public boolean addGeneralaIfPossible(int dieSide, int dieSideDuplicates, Map<CombinationEnum, Integer> comboMap) {
        if (dieSideDuplicates != DiceRoll.getNumberOfDice()) {
            return false;
        }
        comboMap.put(CombinationEnum.GENERALA, dieSide * DiceRoll.getNumberOfDice());
        return true;
    }

    public void addFourOfAKindIfPossible(int dieSide, Map<CombinationEnum, Integer> comboMap) {
        if (!comboMap.containsKey(CombinationEnum.FOUR_OF_A_KIND)) {
            comboMap.put(CombinationEnum.FOUR_OF_A_KIND, dieSide * FOUR_MULTIPLIER);
        } else if (dieSide > comboMap.get(CombinationEnum.FOUR_OF_A_KIND) / FOUR_MULTIPLIER) {
            comboMap.put(CombinationEnum.FOUR_OF_A_KIND, dieSide * FOUR_MULTIPLIER);
        }

    }

    public void addTripleIfPossible(int dieSide, Map<CombinationEnum, Integer> comboMap) {

        if (!comboMap.containsKey(CombinationEnum.TRIPLE)) {
            comboMap.put(CombinationEnum.TRIPLE, dieSide * TRIPLE_MULTIPLIER);
        } else if (dieSide > comboMap.get(CombinationEnum.TRIPLE) / TRIPLE_MULTIPLIER) {
            comboMap.put(CombinationEnum.TRIPLE, dieSide * TRIPLE_MULTIPLIER);
        }


    }

    public void addPairIfPossible(int dieSide, Map<CombinationEnum, Integer> comboMap) {

        if (!comboMap.containsKey(CombinationEnum.PAIR)) {
            comboMap.put(CombinationEnum.PAIR, dieSide * PAIR_MULTIPLIER);
        } else if (dieSide > comboMap.get(CombinationEnum.PAIR) / PAIR_MULTIPLIER) {
            comboMap.put(CombinationEnum.PAIR, dieSide * PAIR_MULTIPLIER);
        }

    }

    public void addDoublePairIfPossible(int dieSide, Map<CombinationEnum, Integer> comboMap) {
        int doublePairComboMapValue = 0;
        int pairComboMapValue = 0;
        if (comboMap.containsKey(CombinationEnum.DOUBLE_PAIR)) {

            doublePairComboMapValue = comboMap.get(CombinationEnum.DOUBLE_PAIR) / PAIR_MULTIPLIER;
        }

        if (comboMap.containsKey(CombinationEnum.PAIR)
                && comboMap.get(CombinationEnum.PAIR) / PAIR_MULTIPLIER != dieSide) {

            pairComboMapValue = comboMap.get(CombinationEnum.PAIR) / PAIR_MULTIPLIER;

            if (pairComboMapValue + dieSide > doublePairComboMapValue) {

                comboMap.put(CombinationEnum.DOUBLE_PAIR, (pairComboMapValue + dieSide) * PAIR_MULTIPLIER);
            }
        }

    }

    public int[] addStraightIfPossible(int currentDieSide
            , int[] counter
            , Map<CombinationEnum, Integer> comboMap) {
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
            comboMap.put(CombinationEnum.STRAIGHT, countStraight(counter[1]));
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
