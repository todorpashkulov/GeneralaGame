package generala.objects;

import generala.application.Application;
import generala.enums.CombinationEnum;
import generala.utils.GeneralaPrinter;
import generala.utils.GeneralaUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public final class Generala {

    private static int playerCount;
    private static int roundCount;
    private GeneralaPrinter generalaPrinter = new GeneralaPrinter();

    public void playGenerala() {
        loadPropertiesFile();
        List<Player> players = GeneralaUtils.generatePlayerList(playerCount);
        CombinationEnum currentPlayerBiggestCombination;
        int oldPlayerScore;
        generalaPrinter.printNewGame();
        for (int i = 0; i < roundCount; i++) {
            GeneralaUtils.generateRandomDiceRollForEachPlayer(players);
            generalaPrinter.printRoundSeparator(i + 1);
            for (Player p : players) {
                oldPlayerScore = p.getScore();
                currentPlayerBiggestCombination = p.addScore();
                generalaPrinter.printRound(p, oldPlayerScore, currentPlayerBiggestCombination);
                if (currentPlayerBiggestCombination != null &&
                        currentPlayerBiggestCombination.equals(CombinationEnum.GENERALA)) {
                    generalaPrinter.printGeneralaWin(p, players);
                    return;
                }
            }
        }
        generalaPrinter.printNormalWin(players, false);

    }



    private void loadPropertiesFile() {
        try (InputStream input = Application.class.getClassLoader().getResourceAsStream("generala.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            playerCount = Integer.valueOf(properties.getProperty("players"));
            roundCount = Integer.valueOf(properties.getProperty("rounds"));
            DiceRoll.setDiceCount(Integer.valueOf(properties.getProperty("dice")));
            DiceRoll.setDiceSidesCount(Integer.valueOf(properties.getProperty("diceSides")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
