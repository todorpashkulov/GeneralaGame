package generala.objects;

import generala.enums.CombinationEnum;
import generala.utils.CombinationFinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class PlayerTest {
    Map<CombinationEnum, Integer> combinationTreeMapReversed;
    private Player player;
    private CombinationFinder combinationFinder = mock(CombinationFinder.class);

    @BeforeEach
    void init() {
        combinationTreeMapReversed = new TreeMap<>(Collections.reverseOrder());
        combinationTreeMapReversed.put(CombinationEnum.PAIR, CombinationEnum.PAIR.getScoreConst());
        combinationTreeMapReversed.put(CombinationEnum.STRAIGHT, CombinationEnum.STRAIGHT.getScoreConst());
        combinationTreeMapReversed.put(CombinationEnum.TRIPLE, CombinationEnum.TRIPLE.getScoreConst());
    }

    @Test
    void assertGetRolledCombinationsReturnsEmptyEnumSetAtInitialization() {
        player = new Player();
        assertAll(
                () -> assertNotNull(player.getRolledCombinations()),
                () -> assertTrue(player.getRolledCombinations().isEmpty())
        );
    }

    @Test
    void assertAddScoreReturnsGeneralaCombinationCorrectly() {
        player = new Player(combinationFinder);
        combinationTreeMapReversed.put(CombinationEnum.GENERALA, CombinationEnum.GENERALA.getScoreConst());
        when(combinationFinder.findCombinations(player)).thenReturn(combinationTreeMapReversed);
        final CombinationEnum combinationEnum = player.addScore();
        assertSame(CombinationEnum.GENERALA, combinationEnum);
    }

    @Test
    void assertAddScoreReturnsBiggestCombinationCorrectly() {
        player = new Player(combinationFinder);
        when(combinationFinder.findCombinations(player)).thenReturn(combinationTreeMapReversed);
        final CombinationEnum combinationEnum = player.addScore();
        assertSame(CombinationEnum.STRAIGHT, combinationEnum);
    }

    @Test
    void assertAddScoreAddsCorrectly() {
        player = new Player(combinationFinder);
        player.setScore(50);
        when(combinationFinder.findCombinations(player)).thenReturn(combinationTreeMapReversed);
        player.addScore();
        assertEquals(80, player.getScore());

    }

}