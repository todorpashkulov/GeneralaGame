package generala;

import generala.objects.DiceRoll;
import generala.objects.Generala;
import generala.objects.Player;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

public final class GeneralaUtils {


    private GeneralaUtils() {
    }



    public static void generateDiceRoll(Player player) {
        DiceRoll diceRoll = player.getDiceRollObj();
        diceRoll.getDieSideDuplicatesMap().clear();

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
            addToDiceRollMap(diceRoll, tempRandomNum);

        }

        diceRoll.setDiceRollString(diceRollString.toString());
    }

    public static List<Player> generatePlayerList(int playerCount) {
        List<Player> players = new ArrayList<>();


        for (int i = 0; i < playerCount; i++) {
            players.add(new Player("Player " + (i + 1)));
        }

        return players;
    }

    //todo fix name
    public static void updatePlayerRandom(List<Player> players) {
        for (Player p : players) {
            generateDiceRoll(p);
        }
    }

    private static void addToDiceRollMap(DiceRoll diceRoll, int value) {
        Map<Integer, Integer> dieSideDuplicatesMap = diceRoll.getDieSideDuplicatesMap();
        if (dieSideDuplicatesMap.containsKey(value)) {
            dieSideDuplicatesMap.put(value, dieSideDuplicatesMap.get(value) + 1);
        } else {
            dieSideDuplicatesMap.put(value, 1);
        }
    }


}

