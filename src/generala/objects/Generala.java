package generala.objects;

import generala.application.Main;
import generala.combinations.CombinationFinder;
import generala.enums.CombinationEnum;
import generala.utils.GeneralaUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public final class Generala {

    private static int playerCount;
    private static int roundCount;
    private GeneralaPrinter generalaPrinter = new GeneralaPrinter();
    private CombinationFinder combinationFinder = new CombinationFinder();

    public void playGenerala() {
        loadPropertiesFile();
        List<Player> players = GeneralaUtils.generatePlayerList(playerCount);
        CombinationEnum currentPlayerBiggestCombination;
        int oldPlayerScore;

        for (int i = 0; i < roundCount; i++) {
            GeneralaUtils.generateRandomDiceRollForEachPlayer(players);
            generalaPrinter.printRoundSeparator(i + 1);
            for (Player p : players) {
                oldPlayerScore = p.getScore();
                currentPlayerBiggestCombination = addScoreToPlayer(p);
                generalaPrinter.printRound(p, oldPlayerScore, currentPlayerBiggestCombination);
                if (currentPlayerBiggestCombination != null
                        && currentPlayerBiggestCombination.equals(CombinationEnum.GENERALA)) {
                    generalaPrinter.printGeneralaWin(p, players);
                    return;
                }
            }
        }
        generalaPrinter.printNormalWin(players, false);

    }

    private CombinationEnum addScoreToPlayer(Player player) {
        Map<CombinationEnum, Integer> combinationTreeMap = combinationFinder
                .findCombinationsInPlayerDiceRoll(player);
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
            player.getRolledCombinations().add(biggestCombination);
            player.addScore(biggestCombinationValue);
        }
        return biggestCombination;
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
   /* private CombinationEnum addScoreToPlayers(Player player) {
        EnumSet<CombinationEnum> playerRolledCombos = player.getRolledCombinations();
        int biggestScore = 0;
        CombinationEnum finalCombo = null;
        Map<CombinationEnum, Integer> comboMap = combinationFinder.findCombinationsInPlayerDiceRoll(player);

        for (Map.Entry<CombinationEnum, Integer> comboMapEntrySet : comboMap.entrySet()) {
            if (comboMapEntrySet.getKey().equals(CombinationEnum.GENERALA)) {
                biggestScore = comboMapEntrySet.getValue();
                finalCombo = CombinationEnum.GENERALA;
                break;
            }
            if (playerRolledCombos.contains(comboMapEntrySet.getKey())) {
                continue;
            }
            int tempScore = comboMapEntrySet.getValue();
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

    }*/