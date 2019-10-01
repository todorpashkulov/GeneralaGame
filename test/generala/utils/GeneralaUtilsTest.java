package generala.utils;

import generala.objects.DiceRoll;
import generala.objects.Player;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class GeneralaUtilsTest {
    private static int oldDiceCount;
    private static int oldDiceSides;

    @BeforeAll
    static void init() {
        oldDiceCount = DiceRoll.getDiceCount();
        oldDiceSides = DiceRoll.getDiceSidesCount();
        DiceRoll.setDiceCount(5);
        DiceRoll.setDiceSidesCount(6);
    }

    @AfterAll
    static void cleanUp() {
        DiceRoll.setDiceCount(oldDiceCount);
        DiceRoll.setDiceSidesCount(oldDiceSides);
    }

    @Test
    void assertGeneratePlayerListGeneratesListWithCorrectSize() {
        assertEquals(5, GeneralaUtils.generatePlayerList(5).size());
    }

    @Test
    void assertGenerateRandomDiceRollsFillsAllPlayersDiceRolls() {
        List<Player> playerList = new ArrayList<>();
        playerList.add(new Player());
        playerList.add(new Player());
        playerList.add(new Player());
        GeneralaUtils.generateRandomDiceRolls(playerList);
        assertFalse(playerList.get(0).getDiceRoll().getEachSideDuplicatesTreeMap().isEmpty());
        assertFalse(playerList.get(1).getDiceRoll().getEachSideDuplicatesTreeMap().isEmpty());
        assertFalse(playerList.get(2).getDiceRoll().getEachSideDuplicatesTreeMap().isEmpty());
    }

}