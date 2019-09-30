package generala.objects;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class DiceRollTest {


    @Test
    @DisplayName("getEachSideDuplicates should returns TreeMap")
    void assertGetEachSideDuplicatesReturnsCorrectMapAtInitialization() {
        DiceRoll diceRoll = new DiceRoll();
        assertAll(
                () -> assertNotNull(diceRoll.getEachSideDuplicatesTreeMap()),
                () -> assertTrue(diceRoll.getEachSideDuplicatesTreeMap() instanceof TreeMap),
                () -> assertEquals(0, diceRoll.getEachSideDuplicatesTreeMap().size())
        );
    }

}