package generala.utils;

import generala.objects.DiceRoll;
import generala.objects.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public final class GeneralaUtils {

    private GeneralaUtils() {
    }

    public static List<Player> generatePlayerList(int playerCount) {
        List<Player> players = new ArrayList<>();

        for (int i = 0; i < playerCount; i++) {
            players.add(new Player("Player " + (i + 1)));
        }

        return players;
    }

    public static void generateRandomDiceRolls(List<Player> players) {
        for (Player p : players) {
            generateRandomDiceRoll(p);
        }
    }

    private static void generateRandomDiceRoll(Player player) {
        DiceRoll diceRoll = player.getDiceRoll();
        diceRoll.getEachSideDuplicatesTreeMap().clear();
        int numberOfDice = DiceRoll.getDiceCount();
        int numberOfDiceSides = DiceRoll.getDiceSidesCount();
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
            addCurrentSideToDiceRoll(diceRoll, tempRandomNum);
        }
        diceRoll.setDiceRoll(diceRollString.toString());
    }

    private static void addCurrentSideToDiceRoll(DiceRoll diceRoll, int value) {
        Map<Integer, Integer> dieSideDuplicatesMap = diceRoll.getEachSideDuplicatesTreeMap();
        if (dieSideDuplicatesMap.containsKey(value)) {
            dieSideDuplicatesMap.put(value, dieSideDuplicatesMap.get(value) + 1);
        } else {
            dieSideDuplicatesMap.put(value, 1);
        }
    }
}

