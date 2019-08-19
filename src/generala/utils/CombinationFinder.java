package generala.utils;

import generala.enums.CombinationEnum;
import generala.objects.DiceRoll;
import generala.objects.Player;

import java.util.*;

import static generala.constants.GeneralaConstants.*;

public final class CombinationFinder {

    private int sideForCalculatingStraightPoints;
    private int biggestFourOfAKind;
    private int biggestTriple;
    private int biggestPair;
    private int secondBiggestPair;

    //PUBLIC METHODS
    public Map<CombinationEnum, Integer> findCombinationsInPlayerDiceRoll(Player player) {
        Map<CombinationEnum, Integer> combinationTreeMapReversed = new TreeMap<>(Collections.reverseOrder());
        for (Map.Entry<Integer, Integer> currentSideEntrySet : player.getDiceRoll()
                .getEachSideDuplicatesTreeMapReversed()
                .entrySet()) {
            int currentSideKey = currentSideEntrySet.getKey();
            int currentSideValue = currentSideEntrySet.getValue();
            if (hasGenerala(currentSideValue)) {
                addGenerala(currentSideKey, combinationTreeMapReversed);
                return combinationTreeMapReversed;
            }
            if (canBreakEarly(player.getRolledCombinations().size(), combinationTreeMapReversed.size())) {
                break;
            }
            if (currentSideValue >= TWO_DUPLICATES) {
                findNonCompoundCombinations(currentSideKey, currentSideValue);
            }
        }
        callAddMethods(player, combinationTreeMapReversed);
        return combinationTreeMapReversed;
    }

    //HELPER METHODS
    private void findNonCompoundCombinations(int currentSide, int currentSideDuplicates) {
        if (currentSideDuplicates >= FOUR_OF_A_KIND_SIZE) {
            if (!hasBiggestFourOfAKind()) {
                setBiggestFourOfAKind(currentSide);
            }
        }
        if (currentSideDuplicates >= TRIPLE_SIZE) {
            if (!hasBiggestTriple()) {
                setBiggestTriple(currentSide);
            }
        }
        if (currentSideDuplicates >= PAIR_SIZE) {
            if (!hasBiggestPair()) {
                setBiggestPair(currentSide);
            } else if (!hasSecondBiggestPair()) {
                setSecondBiggestPair(currentSide);
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
        if (hasStraight(player.getDiceRoll().getEachSideDuplicatesTreeMapReversed().keySet())) {
            addStraight(playerRolledCombinations, combinationTreeMap);
        }
        resetInstanceVariablesForCombinationCreating();
    }

    private void resetInstanceVariablesForCombinationCreating() {
        biggestFourOfAKind = 0;
        biggestTriple = 0;
        biggestPair = 0;
        secondBiggestPair = 0;
        sideForCalculatingStraightPoints = 0;
    }

    private int countStraightScore(int sideForCalculatingStraightPoints) {
        int sum = 0;
        for (int i = sideForCalculatingStraightPoints, counter = 0; counter < STRAIGHT_SIZE; i++, counter++) {
            sum += i;
        }
        return sum + CombinationEnum.STRAIGHT.getScoreConst();
    }

    //ADD METHODS
    private void addGenerala(int currentDieSide, Map<CombinationEnum, Integer> combinationTreeMap) {
        combinationTreeMap.put(CombinationEnum.GENERALA,
                (currentDieSide * DiceRoll.getDiceCount()) + CombinationEnum.GENERALA.getScoreConst());

    }

    private void addFourOfAKind(EnumSet<CombinationEnum> playerRolledCombinations,
                                Map<CombinationEnum, Integer> combinationTreeMap) {
        if (hasPlayerRolledCombination(playerRolledCombinations, CombinationEnum.FOUR_OF_A_KIND)) {
            return;
        }
        combinationTreeMap.put(CombinationEnum.FOUR_OF_A_KIND,
                (biggestFourOfAKind * FOUR_OF_A_KIND_SIZE) + CombinationEnum.FOUR_OF_A_KIND.getScoreConst());

    }

    private void addTriple(EnumSet<CombinationEnum> playerRolledCombinations,
                           Map<CombinationEnum, Integer> combinationTreeMap) {
        if (hasPlayerRolledCombination(playerRolledCombinations, CombinationEnum.TRIPLE)) {
            return;
        }
        combinationTreeMap.put(CombinationEnum.TRIPLE,
                (biggestTriple * TRIPLE_SIZE) + CombinationEnum.TRIPLE.getScoreConst());
    }

    private void addPair(EnumSet<CombinationEnum> playerRolledCombinations,
                         Map<CombinationEnum, Integer> combinationTreeMap) {
        if (hasPlayerRolledCombination(playerRolledCombinations, CombinationEnum.PAIR)) {
            return;
        }
        combinationTreeMap.put(CombinationEnum.PAIR, (biggestPair * PAIR_SIZE) + CombinationEnum.PAIR.getScoreConst());
    }

    private void addDoublePair(EnumSet<CombinationEnum> playerRolledCombinations,
                               Map<CombinationEnum, Integer> combinationTreeMap) {
        if (hasPlayerRolledCombination(playerRolledCombinations, CombinationEnum.DOUBLE_PAIR)) {
            return;
        }
        combinationTreeMap.put(CombinationEnum.DOUBLE_PAIR,
                ((biggestPair + secondBiggestPair) * PAIR_SIZE)
                        + CombinationEnum.DOUBLE_PAIR.getScoreConst());

    }

    private void addFullHouse(EnumSet<CombinationEnum> playerRolledCombinations,
                              Map<CombinationEnum, Integer> combinationTreeMap) {
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
        combinationTreeMap.put(CombinationEnum.FULL_HOUSE,
                (pairToUseInCalculatingFullHouse * PAIR_SIZE) + (biggestTriple * TRIPLE_SIZE)
                        + CombinationEnum.FULL_HOUSE.getScoreConst());
    }

    private void addStraight(EnumSet<CombinationEnum> playerRolledCombinations,
                             Map<CombinationEnum, Integer> combinationTreeMap) {
        if (hasPlayerRolledCombination(playerRolledCombinations, CombinationEnum.STRAIGHT)) {
            return;
        }
        combinationTreeMap.put(CombinationEnum.STRAIGHT, countStraightScore(sideForCalculatingStraightPoints));
    }

    //BOOLEAN METHODS
    private boolean hasGenerala(int currentDieSideDuplicates) {
        return currentDieSideDuplicates == DiceRoll.getDiceCount();
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

    private boolean hasPlayerRolledCombination(EnumSet<CombinationEnum> rolledCombinations,
                                               CombinationEnum combinationEnum) {
        return rolledCombinations.contains(combinationEnum);
    }

    private boolean hasStraight(Set<Integer> rolledSidesSet) {
        List<Integer> rolledSidesList = new ArrayList<>(rolledSidesSet);
        for (int i = 0, counter = 0; i < rolledSidesList.size() - 1; i++) {
            if (rolledSidesList.get(i) - 1 != rolledSidesList.get(i + 1)) {
                counter = 0;
            } else
                counter++;
            if (counter == STRAIGHT_SIZE - 1) {
                sideForCalculatingStraightPoints = rolledSidesList.get(i + 1);
                return true;
            }
        }
        return false;
    }

    private boolean canBreakEarly(int playerRolledCombinationCount, int combinationTreeMapSize) {
        return combinationTreeMapSize == (CombinationEnum.values().length - 1) ||
                playerRolledCombinationCount == CombinationEnum.values().length - 1 ||
                canBreakFindNonCompoundCombinations();
    }

    private boolean canBreakFindNonCompoundCombinations() {
        return biggestFourOfAKind != 0 && biggestTriple != 0 && biggestPair != 0 && secondBiggestPair != 0;
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
