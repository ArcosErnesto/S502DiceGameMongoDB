package cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.utils;

import cat.itacademy.barcelonactiva.arcos.ernesto.s05.t02.n01.model.utils.DiceRoll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiceRollUnitTest {
    @Test
    public void testRoll() {
        DiceRoll diceRoll = new DiceRoll();

        diceRoll.roll();

        int dice1Value = diceRoll.getDice1();
        int dice2Value = diceRoll.getDice2();

        assertTrue(dice1Value >= 1 && dice1Value <= 6);
        assertTrue(dice2Value >= 1 && dice2Value <= 6);
    }

}
