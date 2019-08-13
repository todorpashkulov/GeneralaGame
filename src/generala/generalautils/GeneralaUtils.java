package generala.generalautils;

import generala.objects.DiceRoll;
import generala.objects.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public final class GeneralaUtils {

    private GeneralaUtils() {
    }

    public static void generateRandomDiceRoll(Player player) {
        DiceRoll diceRoll = player.getDiceRoll();
        diceRoll.getNumberOfSideDuplicatesTreeMap().clear();
        int numberOfDice = DiceRoll.getNumberOfDice();
        int numberOfDiceSides = DiceRoll.getNumberOfDiceSides();
        StringBuilder diceRollString = new StringBuilder(numberOfDice);
        int tempRandomNum;

        for (int i = 0; i < numberOfDice; i++) {
            if (i > 0) {
                diceRollString.append(",");
            }
            tempRandomNum = ThreadLocalRandom.current().nextInt(numberOfDiceSides) + 1;
            //Making string
            diceRollString.append(tempRandomNum);
            //Filling DiceRoll EnumMap
            addSideToDiceRollDuplicatesMap(diceRoll, tempRandomNum);

        }

        diceRoll.setDiceRoll(diceRollString.toString());
    }

    public static List<Player> generatePlayerList(int playerCount) {
        List<Player> players = new ArrayList<>();

        for (int i = 0; i < playerCount; i++) {
            players.add(new Player("Player " + (i + 1)));
        }

        return players;
    }

    public static void generateRandomDiceRollForEachPlayer(List<Player> players) {
        for (Player p : players) {
            generateRandomDiceRoll(p);
        }
    }

    private static void addSideToDiceRollDuplicatesMap(DiceRoll diceRoll, int value) {
        Map<Integer, Integer> dieSideDuplicatesMap = diceRoll.getNumberOfSideDuplicatesTreeMap();
        if (dieSideDuplicatesMap.containsKey(value)) {
            dieSideDuplicatesMap.put(value, dieSideDuplicatesMap.get(value) + 1);
        } else {
            dieSideDuplicatesMap.put(value, 1);
        }
    }

}

