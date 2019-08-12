package generala.objects;

import generala.generalahelpers.GeneralaUtils;
import generala.Main;
import generala.enums.CombinationEnum;
import generala.generalahelpers.Combinations;
import generala.generalahelpers.GeneralaPrinter;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;


public final class Generala {

    private int playerCount = 5;
    private int roundCount = 3;

    private GeneralaPrinter generalaPrinter = new GeneralaPrinter();
    private Combinations combinations=new Combinations();


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


    public void loadProperties() {

        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("generala.properties")) {
            Properties properties = new Properties();
            properties.load(input);

            playerCount = Integer.valueOf(properties.getProperty("players"));
            roundCount = Integer.valueOf(properties.getProperty("rounds"));
            DiceRoll.setNumberOfDice(Integer.valueOf(properties.getProperty("numberOfDice")));
            DiceRoll.setNumberOfDiceSides(Integer.valueOf(properties.getProperty("numberOfDiceSides")));
            DiceRoll.setStraightSize(Integer.valueOf(properties.getProperty("straightSize")));

        } catch (IOException e) {
            e.printStackTrace();
        }


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

            if (combinations.addGeneralaIfPossible(currentSide, dieSideEntry.getValue(), comboMap)
                    || comboMap.size() == (CombinationEnum.values().length - 1)
                    || player.getRolledCombinations().size() == CombinationEnum.values().length - 1) {
                break;
            }

            if (dieSideEntry.getValue() >= 4) {

                combinations.addFourOfAKindIfPossible(currentSide, comboMap);
            }
            if (dieSideEntry.getValue() >= 3) {
                combinations.addTripleIfPossible(currentSide, comboMap);
            }
            if (dieSideEntry.getValue() >= 2) {
                combinations.addPairIfPossible(currentSide, comboMap);
                combinations.addDoublePairIfPossible(currentSide, comboMap);
                combinations.addFullHouseIfPossible(currentSide, comboMap);
            }

            if (!comboMap.containsKey(CombinationEnum.STRAIGHT)) {
                straightCounter = combinations.addStraightIfPossible(currentSide, straightCounter, comboMap);
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
        loadProperties();
        List<Player> players = GeneralaUtils.generatePlayerList(playerCount);
        CombinationEnum currentCombo;
        int oldPlayerScore;

        for (int i = 0; i < roundCount; i++) {
            GeneralaUtils.generateRandomDiceRollForEachPlayer(players);
            generalaPrinter.printRoundBanner(i + 1);

            for (Player p : players) {
                oldPlayerScore = p.getScore();
                //ADDING SCORE
                currentCombo = addScore(p);

                generalaPrinter.printRound(p, oldPlayerScore, currentCombo);

                if (currentCombo != null && currentCombo.equals(CombinationEnum.GENERALA)) {
                    generalaPrinter.printGeneralaWin(p, players);
                    return;
                }
            }
        }
        generalaPrinter.printNormalWin(players, false);

    }






}
