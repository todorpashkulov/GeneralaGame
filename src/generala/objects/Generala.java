package generala.objects;

import generala.application.Main;
import generala.combinations.CombinationFinder;
import generala.enums.CombinationEnum;
import generala.utils.GeneralaUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;



public final class Generala {

    private static int playerCount;
    private static int roundCount;
    private GeneralaPrinter generalaPrinter = new GeneralaPrinter();
    private CombinationFinder combinationFinder = new CombinationFinder();

    public void playGenerala() {
        loadPropertiesFile();
        List<Player> players = GeneralaUtils.generatePlayerList(playerCount);
        CombinationEnum currentCombo;
        int oldPlayerScore;

        for (int i = 0; i < roundCount; i++) {
            GeneralaUtils.generateRandomDiceRollForEachPlayer(players);
            generalaPrinter.printRoundBanner(i + 1);

            for (Player p : players) {
                oldPlayerScore = p.getScore();
                //ADDING SCORE
                currentCombo = addScoreToPlayers(p);
                generalaPrinter.printRound(p, oldPlayerScore, currentCombo);
                if (currentCombo != null && currentCombo.equals(CombinationEnum.GENERALA)) {
                    generalaPrinter.printGeneralaWin(p, players);
                    return;
                }
            }
        }
        generalaPrinter.printNormalWin(players, false);

    }

    private CombinationEnum addScoreToPlayers(Player player) {
        EnumSet<CombinationEnum> playerRolledCombos = player.getRolledCombinations();
        int biggestScore = 0;
        CombinationEnum finalCombo = null;
        Map<CombinationEnum, Integer> comboMap = combinationFinder.findCombinationsInPlayerDiceRoll(player);
        //TODO:REMOVE
        //System.out.println(comboMap);
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

    private void loadPropertiesFile() {
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("generala.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            playerCount = Integer.valueOf(properties.getProperty("players"));
            roundCount = Integer.valueOf(properties.getProperty("rounds"));
            DiceRoll.setNumberOfDice(Integer.valueOf(properties.getProperty("numberOfDice")));
            DiceRoll.setNumberOfDiceSides(Integer.valueOf(properties.getProperty("numberOfDiceSides")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
