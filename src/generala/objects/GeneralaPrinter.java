package generala.objects;

import generala.enums.CombinationEnum;

import java.util.Comparator;
import java.util.List;

public final class GeneralaPrinter {

    public void printRoundBanner(int currentRound) {
        System.out.println("<-------------------------------------------------->");
        System.out.println();
        System.out.println(">>>Round " + currentRound);
        System.out.println();

    }

    public void printGeneralaWin(Player player, List<Player> playerList) {
        playerList.remove(player);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("WINNER IS ");
        System.out.println("GENERALA!!!! " + player.getName() + " GENERALA!!!!");
        System.out.println("WITH SCORE OF: " + player.getScore());
        System.out.println("CONGRATS");
        System.out.println();

        printNormalWin(playerList, true);

    }

    public void printNormalWin(List<Player> playerList, boolean hasGenerala) {
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

    public void printRound(Player player, int oldScore, CombinationEnum currentCombo) {

        System.out.println(">" + player.getName());
        System.out.println("Current Score: " + oldScore);
        System.out.print(player.getDiceRoll());

        //TODO:REMOVE
        System.out.println(player.getDiceRoll().getNumberOfSideDuplicatesTreeMap());

        System.out.print(currentCombo == null ?
                " -> No Combos" :
                " -> " + currentCombo.getLabel() + " ( " + (player.getScore() - oldScore) + " )");
        System.out.println();
        System.out.println("New Score: " + player.getScore());
        System.out.println();
        System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        System.out.println();

    }

}
