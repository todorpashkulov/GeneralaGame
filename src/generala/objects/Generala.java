package generala.objects;

import generala.GeneralaUtils;
import generala.enums.CombinationEnum;

import java.util.*;


public final class Generala {

    private static Generala instance;
    private int playerCount = 5;
    private int roundCount = 3;

    private final int PAIR_MULTIPLIER = 2;
    private final int TRIPLE_MULTIPLIER = 3;
    private final int FOUR_MULTIPLIER = 4;

    private Generala() {

    }

    public static Generala getInstance() {

        if (instance == null) {
            instance = new Generala();
        }

        return instance;
    }


    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public int getRoundCount() {
        return roundCount;
    }

    public void setRoundCount(int roundCount) {
        this.roundCount = roundCount;
    }


    private Map<CombinationEnum, Integer> findCombos(Player player) {
        Map<CombinationEnum, Integer> comboMap = new TreeMap<>(Collections.reverseOrder());

        Map<Integer, Integer> dieSideDuplicatesMap = player
                .getDiceRollObj()
                .getDieSideDuplicatesMap();

        int currentSide;
        int[] straightCounter = {0, 0};


        for (Map.Entry<Integer, Integer> dieSideEntry : dieSideDuplicatesMap.entrySet()) {
            currentSide = dieSideEntry.getKey();

            if (addGeneralaIfPossible(currentSide, dieSideEntry.getValue(), comboMap)
                    || comboMap.size() == (CombinationEnum.values().length - 1)
                    || player.getRolledCombinations().size() == CombinationEnum.values().length - 1) {
                break;
            }

            if (dieSideEntry.getValue() >= 4) {

                addFourOfAKindIfPossible(currentSide, comboMap);
            }
            if (dieSideEntry.getValue() >= 3) {
                addTripleIfPossible(currentSide, comboMap);
            }
            if (dieSideEntry.getValue() >= 2) {
                addPairIfPossible(currentSide, comboMap);
                addDoublePairIfPossible(currentSide, comboMap);
                addFullHouseIfPossible(currentSide, comboMap);
            }

            if (!comboMap.containsKey(CombinationEnum.STRAIGHT)) {
                straightCounter = addStraightIfPossible(currentSide, straightCounter, comboMap);
            }
        }
//todo:remove
        //System.out.println(comboMap);

        return comboMap;
    }

    private CombinationEnum addScore(Player player) {
        EnumSet<CombinationEnum> playerRolledCombos = player.getRolledCombinations();
        int biggestScore = 0;
        CombinationEnum finalCombo = null;
        Map<CombinationEnum, Integer> comboMap = findCombos(player);

        //TODO:REMOVE
        // System.out.println(comboMap);

        for (Map.Entry<CombinationEnum, Integer> comboMapEntrySet : comboMap.entrySet()) {
            if (comboMapEntrySet.getKey().equals(CombinationEnum.GENERALA)) {
                biggestScore = comboMapEntrySet.getValue() + CombinationEnum.GENERALA.getScoreConst();
                finalCombo = CombinationEnum.GENERALA;
                break;
            }
            if (playerRolledCombos.contains(comboMapEntrySet.getKey())) {
                continue;
            }
            int tempScore = comboMapEntrySet.getValue() + comboMapEntrySet.getKey().getScoreConst();
            if (tempScore > biggestScore) {
                biggestScore = tempScore;
                finalCombo = comboMapEntrySet.getKey();
            }
        }
        if (finalCombo != null) {
            playerRolledCombos.add(finalCombo);
        }
        player.setRolledCombinations(playerRolledCombos);
        player.setScore(player.getScore() + biggestScore);

        return finalCombo;

    }

    public void playGenerala() {
        GeneralaUtils.loadProperties();
        List<Player> players = GeneralaUtils.generatePlayerList(playerCount);
        CombinationEnum currentCombo;
        int oldPlayerScore;

        for (int i = 0; i < roundCount; i++) {
            GeneralaUtils.updatePlayerRandom(players);
            printRoundBanner(i + 1);

            for (Player p : players) {
                oldPlayerScore = p.getScore();
                //ADDING SCORE
                currentCombo = addScore(p);

                printRound(p, oldPlayerScore, currentCombo);

                if (currentCombo != null && currentCombo.equals(CombinationEnum.GENERALA)) {
                    printGeneralaWin(p, players);
                    return;
                }
            }
        }
        printNormalWin(players, false);

    }


    //PRINT METHODS

    private void printRoundBanner(int currentRound) {
        System.out.println("<-------------------------------------------------->");
        System.out.println();
        System.out.println(">>>Round " + currentRound);
        System.out.println();

    }

    private void printGeneralaWin(Player player, List<Player> playerList) {
        playerList.remove(player);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("WINNER IS ");
        System.out.println("GENERALA!!!! " + player.getName() + " GENERALA!!!!");
        System.out.println("WITH SCORE OF: " + player.getScore());
        System.out.println("CONGRATS");
        System.out.println();

        printNormalWin(playerList, true);


    }

    private void printNormalWin(List<Player> playerList, boolean hasGenerala) {
        playerList.sort(Comparator.comparing(Player::getScore).reversed());
        Player lastPlayer = playerList.get(0);
        System.out.println();
        System.out.println("<-------------------------------------------------------->");
        System.out.println();
        System.out.println("FINAL SCORES");


        for (int i = 0, positionCounter = hasGenerala ? 2 : 1; i < playerList.size(); i++) {
            Player player = playerList.get(i);

            if (lastPlayer.getScore() != player.getScore()) {
                positionCounter++;
            }

            System.out.println(positionCounter + ". " + player.getName() + " Score: " + player.getScore());


            lastPlayer = player;


        }


    }

    private void printRound(Player player, int oldScore, CombinationEnum currentCombo) {

        System.out.println(">" + player.getName());
        System.out.println("Current Score: " + oldScore);
        System.out.print("Dice roll: " + player.getDiceRollObj().getDiceRollString());
        //TODO:REMOVE
        //System.out.println(currentPlayer.getDiceRollObj().getDieSideDuplicatesMap());
        System.out.print(currentCombo == null
                ? " -> No Combos"
                : " -> " + currentCombo.getLabel() + " ( " + (player.getScore() - oldScore) + " )");
        System.out.println();
        System.out.println("New Score: " + player.getScore());
        System.out.println();
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        System.out.println();


    }

    //COMBINATION FINDER METHODS

    private void addFullHouseIfPossible(int dieSide, Map<CombinationEnum, Integer> comboMap) {
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

    private boolean addGeneralaIfPossible(int dieSide, int dieSideDuplicates, Map<CombinationEnum, Integer> comboMap) {
        if (dieSideDuplicates != DiceRoll.getNumberOfDice()) {
            return false;
        }
        comboMap.put(CombinationEnum.GENERALA, dieSide * DiceRoll.getNumberOfDice());
        return true;
    }

    private void addFourOfAKindIfPossible(int dieSide, Map<CombinationEnum, Integer> comboMap) {
        if (!comboMap.containsKey(CombinationEnum.FOUR_OF_A_KIND)) {
            comboMap.put(CombinationEnum.FOUR_OF_A_KIND, dieSide * FOUR_MULTIPLIER);
        } else if (dieSide > comboMap.get(CombinationEnum.FOUR_OF_A_KIND) / FOUR_MULTIPLIER) {
            comboMap.put(CombinationEnum.FOUR_OF_A_KIND, dieSide * FOUR_MULTIPLIER);
        }

    }

    private void addTripleIfPossible(int dieSide, Map<CombinationEnum, Integer> comboMap) {

        if (!comboMap.containsKey(CombinationEnum.TRIPLE)) {
            comboMap.put(CombinationEnum.TRIPLE, dieSide * TRIPLE_MULTIPLIER);
        } else if (dieSide > comboMap.get(CombinationEnum.TRIPLE) / TRIPLE_MULTIPLIER) {
            comboMap.put(CombinationEnum.TRIPLE, dieSide * TRIPLE_MULTIPLIER);
        }


    }

    private void addPairIfPossible(int dieSide, Map<CombinationEnum, Integer> comboMap) {

        if (!comboMap.containsKey(CombinationEnum.PAIR)) {
            comboMap.put(CombinationEnum.PAIR, dieSide * PAIR_MULTIPLIER);
        } else if (dieSide > comboMap.get(CombinationEnum.PAIR) / PAIR_MULTIPLIER) {
            comboMap.put(CombinationEnum.PAIR, dieSide * PAIR_MULTIPLIER);
        }

    }

    private void addDoublePairIfPossible(int dieSide, Map<CombinationEnum, Integer> comboMap) {
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

    private int[] addStraightIfPossible(int currentDieSide
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

    private int countStraight(int dieSide) {
        int sum = 0;
        for (int i = dieSide, counter = 0; counter < DiceRoll.getStraightSize(); i--, counter++) {
            sum += i;
        }

        return sum;

    }


}
