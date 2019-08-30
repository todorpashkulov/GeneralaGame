package generala.utils;

import generala.enums.CombinationEnum;
import generala.objects.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.List;

//TODO:try to make it more abstract
public final class GeneralaPrinter {
    private static final Logger LOGGER = LogManager.getLogger(GeneralaPrinter.class);


    public void printRoundSeparator(int currentRound) {
        LOGGER.info("<-------------------------------------------------->");
        LOGGER.info(">>>Round " + currentRound + System.lineSeparator());
    }

    public void printGeneralaWin(Player player, List<Player> playerList) {
        playerList.remove(player);
        LOGGER.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        LOGGER.info("WINNER IS ");
        LOGGER.info("GENERALA!!!! " + player.getName() + " GENERALA!!!!");
        LOGGER.info("WITH SCORE OF: " + player.getScore());
        LOGGER.info("CONGRATS" + System.lineSeparator());
        printNormalWin(playerList, true);
    }

    public void printNormalWin(List<Player> playerList, boolean hasGenerala) {
        playerList.sort(Comparator.comparing(Player::getScore).reversed());
        Player lastPlayer = playerList.get(0);
        LOGGER.info("<-------------------------------------------------------->");
        LOGGER.info("FINAL SCORES" + System.lineSeparator());
        for (int i = 0, positionCounter = hasGenerala ? 2 : 1; i < playerList.size(); i++) {
            Player player = playerList.get(i);
            if (lastPlayer.getScore() != player.getScore()) {
                positionCounter++;
            }
            LOGGER.info(positionCounter + ". " + player.getName() + " Score: " + player.getScore());
            lastPlayer = player;
        }
    }

    public void printRound(Player player, int oldScore, CombinationEnum currentCombo) {
        LOGGER.info(">" + player.getName());
        LOGGER.info("Current Score: " + oldScore);
        //TODO:REMOVE
        LOGGER.info(player.getDiceRoll().getEachSideDuplicatesTreeMapReversed());

        LOGGER.info(player.getDiceRoll().toString() +
                (currentCombo == null
                        ? " -> No Combos"
                        : " -> " + currentCombo.getLabel() + " ( " + (player.getScore() - oldScore) + " )"));
        LOGGER.info("New Score: " + player.getScore());
        LOGGER.info("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^" + System.lineSeparator());

    }

    public void printNewGame() {
        LOGGER.info("NEW GAME");

    }
}
