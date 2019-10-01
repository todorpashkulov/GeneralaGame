package generala.utils;

import generala.enums.CombinationEnum;
import generala.objects.DiceRoll;
import generala.objects.Player;
import org.junit.jupiter.api.*;

import java.util.Map;

import static generala.enums.CombinationEnum.*;
import static org.junit.jupiter.api.Assertions.*;


class CombinationFinderTest {
    private static int diceCountPreviousValue = DiceRoll.getDiceCount();
    private CombinationFinder combinationFinder = new CombinationFinder();
    private Player player = new Player();
    private DiceRoll diceRoll = new DiceRoll();
    private Map<CombinationEnum, Integer> combinationTreeMap;

    @AfterAll
    static void cleanUp() {
        DiceRoll.setDiceCount(diceCountPreviousValue);
    }

    @Nested
    @DisplayName("Assertions for combinations without generala")
    class CombinationAssertionsWithoutGenerala {

        @BeforeEach
        void init() {
            diceRoll.getEachSideDuplicatesTreeMap().put(9, 3);
            diceRoll.getEachSideDuplicatesTreeMap().put(8, 1);
            diceRoll.getEachSideDuplicatesTreeMap().put(7, 4);
            diceRoll.getEachSideDuplicatesTreeMap().put(5, 4);
            diceRoll.getEachSideDuplicatesTreeMap().put(4, 3);
            diceRoll.getEachSideDuplicatesTreeMap().put(3, 2);
            diceRoll.getEachSideDuplicatesTreeMap().put(2, 2);
            diceRoll.getEachSideDuplicatesTreeMap().put(1, 4);
            player.setDiceRoll(diceRoll);
            combinationTreeMap = combinationFinder.findCombinations(player);
        }

        @Test
        void assertFindCombinationsFindsAllCombinations() {
            System.out.println(DiceRoll.getDiceCount());
            assertAll(
                    () -> assertTrue(combinationTreeMap.containsKey(TRIPLE)),
                    () -> assertTrue(combinationTreeMap.containsKey(PAIR)),
                    () -> assertTrue(combinationTreeMap.containsKey(FOUR_OF_A_KIND)),
                    () -> assertTrue(combinationTreeMap.containsKey(STRAIGHT)),
                    () -> assertTrue(combinationTreeMap.containsKey(DOUBLE_PAIR)),
                    () -> assertTrue(combinationTreeMap.containsKey(FULL_HOUSE))
            );
        }

        @Test
        void assertFindCombinationsAddsCorrectScoreForCombinations() {

            assertAll(
                    () -> assertEquals(9 * 3 + TRIPLE.getScoreConst(),
                            combinationTreeMap.get(TRIPLE)),
                    () -> assertEquals(9 * 2 + PAIR.getScoreConst(),
                            combinationTreeMap.get(PAIR)),
                    () -> assertEquals(7 * 4 + FOUR_OF_A_KIND.getScoreConst(),
                            combinationTreeMap.get(FOUR_OF_A_KIND)),
                    () -> assertEquals(15 + STRAIGHT.getScoreConst(),
                            combinationTreeMap.get(STRAIGHT)),
                    () -> assertEquals((7 + 9) * 2 + DOUBLE_PAIR.getScoreConst(),
                            combinationTreeMap.get(DOUBLE_PAIR)),
                    () -> assertEquals(9 * 3 + 2 * 7 + FULL_HOUSE.getScoreConst(),
                            combinationTreeMap.get(FULL_HOUSE))
            );
        }
    }

    @Nested
    @DisplayName("Assertions for generala combination")
    class GeneralaAssertions {
        @BeforeEach
        void init() {
            DiceRoll.setDiceCount(9);
            DiceRoll diceRoll = new DiceRoll();
            diceRoll.getEachSideDuplicatesTreeMap().put(9, 9);
            player.setDiceRoll(diceRoll);

        }

        @Test
        void assertFindCombinationsDetectsGeneralaCombination() {
            assertTrue(combinationFinder.findCombinations(player).containsKey(GENERALA));
        }

        @Test
        void assertFindCombinationsCalculatesGeneralaCombinationRight() {
            assertEquals(9 * 9 + GENERALA.getScoreConst(),
                    combinationFinder.findCombinations(player).get(GENERALA));

        }
    }

}