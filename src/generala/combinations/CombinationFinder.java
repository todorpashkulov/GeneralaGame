package generala.combinations;

import generala.enums.CombinationEnum;
import generala.objects.DiceRoll;
import generala.objects.Player;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Map;
import java.util.TreeMap;

import static generala.constants.GeneralaConstants.*;

//TODO: After you are done rename variables and methods
public final class CombinationFinder {
    private int straightCounter;
    private int dieSideForCalculatingStraightPoints;
    private int biggestFourOfAKind;
    private int biggestTriple;
    private int biggestPair;
    private int secondBiggestPair;

    //PUBLIC METHODS
    public Map<CombinationEnum, Integer> findCombinationsInPlayerDiceRoll(Player player) {
        int currentSideKey;
        int currentSideValue;
        Map<CombinationEnum, Integer> combinationTreeMapReversed = new TreeMap<>(Collections.reverseOrder());
        Map<Integer, Integer> eachSideDuplicatesMap = player.getDiceRoll().getEachSideDuplicatesTreeMapReversed();
        for (Map.Entry<Integer, Integer> currentDieSideEntrySet : eachSideDuplicatesMap.entrySet()) {
            currentSideKey = currentDieSideEntrySet.getKey();
            currentSideValue = currentDieSideEntrySet.getValue();
            if (hasGenerala(currentSideValue)) {
                addGenerala(currentSideKey, combinationTreeMapReversed);
                return combinationTreeMapReversed;
            }
            if (canBreakEarly(player.getRolledCombinations().size(), combinationTreeMapReversed.size())) {
                break;
            }
            //TODO:ask for this comparing parameter
            if (currentSideValue >= PAIR_SIZE) {
                findNonCompoundCombinations(currentSideKey, currentSideValue);
            }
        }
        findStraight(eachSideDuplicatesMap);
        callAddMethods(player, combinationTreeMapReversed);
        resetInstanceVariables();
        return combinationTreeMapReversed;
    }

    //HELPER METHODS
    private void findNonCompoundCombinations(int currentDieSide, int currentDieSideValue) {


        if (currentDieSideValue >= FOUR_OF_A_KIND_SIZE) {
            if (!hasBiggestFourOfAKind()) {
                setBiggestFourOfAKind(currentDieSide);
            }
        }
        if (currentDieSideValue >= TRIPLE_SIZE) {
            if (!hasBiggestTriple()) {
                setBiggestTriple(currentDieSide);
            }
        }
        if (currentDieSideValue >= PAIR_SIZE) {
            if (!hasBiggestPair()) {
                setBiggestPair(currentDieSide);
            } else if (!hasSecondBiggestPair()) {
                setSecondBiggestPair(currentDieSide);
            }
        }
    }

    private void callAddMethods(Player player, Map<CombinationEnum, Integer> combinationTreeMap) {
        EnumSet<CombinationEnum> playerRolledCombinations = player.getRolledCombinations();

        if (hasBiggestFourOfAKind()) {
            addFourOfAKind(playerRolledCombinations, combinationTreeMap);
        }
        if (hasBiggestTriple()) {
            addTriple(playerRolledCombinations, combinationTreeMap);
        }
        if (hasBiggestPair()) {
            addPair(playerRolledCombinations, combinationTreeMap);
        }
        if (hasSecondBiggestPair()) {
            addDoublePair(playerRolledCombinations, combinationTreeMap);
        }
        if (hasBiggestTriple() && hasBiggestPair()) {
            addFullHouse(playerRolledCombinations, combinationTreeMap);
        }
        if (hasStraight()) {
            addStraight(playerRolledCombinations, combinationTreeMap);
        }

    }
//todo:raname
    private void resetInstanceVariables() {
        biggestFourOfAKind = 0;
        biggestTriple = 0;
        biggestPair = 0;
        secondBiggestPair = 0;
        straightCounter = 0;
        dieSideForCalculatingStraightPoints = 0;
    }

    //TODO:use key set
    private void findStraight(Map<Integer, Integer> eachSideDuplicatesMap) {
        for (Map.Entry<Integer, Integer> currentDieSideEntrySet : eachSideDuplicatesMap.entrySet()) {
            if (straightCounter != STRAIGHT_SIZE) {
                increaseStraightCounter(currentDieSideEntrySet.getKey());
            } else
                break;
        }
    }

    //TODO:simplyfy
    private void increaseStraightCounter(int currentDieSide) {
        if (straightCounter == 0) {
            straightCounter += 1;
            dieSideForCalculatingStraightPoints = currentDieSide;
            return;
        }
        if (currentDieSide == dieSideForCalculatingStraightPoints - straightCounter) {
            straightCounter += 1;
        } else {
            straightCounter = 0;
        }
    }

    private int countStraightPoints(int dieSide) {
        int sum = 0;
        for (int i = dieSide, counter = 0; counter < STRAIGHT_SIZE; i--, counter++) {
            sum += i;
        }
        return sum;

    }

    //ADD METHODS
    private void addGenerala(int currentDieSide, Map<CombinationEnum, Integer> combinationTreeMap) {

        combinationTreeMap.put(CombinationEnum.GENERALA
                , (currentDieSide * DiceRoll.getNumberOfDice())
                        + CombinationEnum.GENERALA.getScoreConst());

    }

    private void addFourOfAKind(EnumSet<CombinationEnum> playerRolledCombinations, Map<CombinationEnum, Integer> combinationTreeMap) {
        if (hasPlayerRolledCombination(playerRolledCombinations, CombinationEnum.FOUR_OF_A_KIND)) {
            return;
        }
        combinationTreeMap.put(CombinationEnum.FOUR_OF_A_KIND
                , (biggestFourOfAKind * FOUR_OF_A_KIND_SIZE)
                        + CombinationEnum.FOUR_OF_A_KIND.getScoreConst());


    }

    private void addTriple(EnumSet<CombinationEnum> playerRolledCombinations, Map<CombinationEnum, Integer> combinationTreeMap) {
        if (hasPlayerRolledCombination(playerRolledCombinations, CombinationEnum.TRIPLE)) {
            return;
        }
        combinationTreeMap.put(CombinationEnum.TRIPLE
                , (biggestTriple * TRIPLE_SIZE)
                        + CombinationEnum.TRIPLE.getScoreConst());
    }

    private void addPair(EnumSet<CombinationEnum> playerRolledCombinations, Map<CombinationEnum, Integer> combinationTreeMap) {
        if (hasPlayerRolledCombination(playerRolledCombinations, CombinationEnum.PAIR)) {
            return;
        }
        combinationTreeMap.put(CombinationEnum.PAIR
                , (biggestPair * PAIR_SIZE) + CombinationEnum.PAIR.getScoreConst());
    }

    private void addDoublePair(EnumSet<CombinationEnum> playerRolledCombinations, Map<CombinationEnum, Integer> combinationTreeMap) {
        if (hasPlayerRolledCombination(playerRolledCombinations, CombinationEnum.DOUBLE_PAIR)) {
            return;
        }
        combinationTreeMap.put(CombinationEnum.DOUBLE_PAIR
                , ((biggestPair + secondBiggestPair) * PAIR_SIZE)
                        + CombinationEnum.DOUBLE_PAIR.getScoreConst());

    }

    private void addFullHouse(EnumSet<CombinationEnum> playerRolledCombinations, Map<CombinationEnum, Integer> combinationTreeMap) {
        if (hasPlayerRolledCombination(playerRolledCombinations, CombinationEnum.FULL_HOUSE)) {
            return;
        }
        int pairToUseInCalculatingFullHouse;
        if (biggestPair != biggestTriple) {
            pairToUseInCalculatingFullHouse = biggestPair;
        } else if (hasSecondBiggestPair()) {
            pairToUseInCalculatingFullHouse = secondBiggestPair;
        } else {
            return;
        }
        combinationTreeMap.put(CombinationEnum.FULL_HOUSE
                , (pairToUseInCalculatingFullHouse * PAIR_SIZE)
                        + (biggestTriple * TRIPLE_SIZE)
                        + CombinationEnum.FULL_HOUSE.getScoreConst());
    }

    private void addStraight(EnumSet<CombinationEnum> playerRolledCombinations, Map<CombinationEnum, Integer> combinationTreeMap) {
        if (hasPlayerRolledCombination(playerRolledCombinations, CombinationEnum.STRAIGHT)) {
            return;
        }
        combinationTreeMap.put(CombinationEnum.STRAIGHT, countStraightPoints(dieSideForCalculatingStraightPoints));

    }

    //BOOLEAN METHODS
    private boolean hasGenerala(int currentDieSideDuplicates) {
        return currentDieSideDuplicates == DiceRoll.getNumberOfDice();
    }

    private boolean hasBiggestPair() {
        return biggestPair != 0;
    }

    private boolean hasSecondBiggestPair() {
        return secondBiggestPair != 0;
    }

    private boolean hasBiggestTriple() {
        return biggestTriple != 0;
    }

    private boolean hasBiggestFourOfAKind() {
        return biggestFourOfAKind != 0;
    }

    private boolean hasStraight() {
        return straightCounter == STRAIGHT_SIZE;
    }

    private boolean hasPlayerRolledCombination(EnumSet<CombinationEnum> rolledCombinations
            , CombinationEnum combinationEnum) {
        return rolledCombinations.contains(combinationEnum);
    }

    private boolean canBreakEarly(int playerRolledCombinationCount, int combinationTreeMapSize) {
        return combinationTreeMapSize == (CombinationEnum.values().length - 1)
                || playerRolledCombinationCount == CombinationEnum.values().length - 1
                || canBreakFindNonCompoundCombinations();
    }

    private boolean canBreakFindNonCompoundCombinations() {
        return biggestFourOfAKind != 0
                && biggestTriple != 0
                && biggestPair != 0
                && secondBiggestPair != 0;
    }

    //SETTERS
    private void setBiggestFourOfAKind(int biggestFourOfAKind) {
        this.biggestFourOfAKind = biggestFourOfAKind;
    }

    private void setBiggestTriple(int biggestTriple) {
        this.biggestTriple = biggestTriple;
    }

    private void setBiggestPair(int biggestPair) {
        this.biggestPair = biggestPair;
    }

    private void setSecondBiggestPair(int secondBiggestPair) {
        this.secondBiggestPair = secondBiggestPair;
    }

}
