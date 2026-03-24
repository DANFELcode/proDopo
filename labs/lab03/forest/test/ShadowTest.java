package test;

import static org.junit.Assert.*;
import org.junit.Test;
import domain.*;
import java.awt.Color;

/**
 * ShadowTest
 * Proporciona las pruebas unitarias para asegurar el correcto comportamiento visual,
 * la propagacion en la fila y el movimiento circular de la clase Shadow.
 *
 * @author Daniel Felipe Sua y Juan David Munar
 */
public class ShadowTest {

    /**
     * Deberia: verificar que la sombra nazca con el color negro y forma redonda.
     */
    @Test
    public void shouldBeBlackAndRound() {
        Forest forest = new Forest();
        Shadow shadow = new Shadow(forest, 10, 10);
        
        assertEquals(Color.BLACK, shadow.getColor());
        assertEquals(Thing.ROUND, shadow.shape());
    }

    /**
     * Deberia: verificar que la sombra se esparza por toda la fila inicial al ser creada.
     */
    @Test
    public void shouldSpreadAcrossTheEntireRow() {
        Forest forest = new Forest();
        Shadow shadow = new Shadow(forest, 8, 5);
        
        int shadowCountInRow = 0;
        for (int c = 0; c < forest.getSize(); c++) {
            if (forest.getThing(8, c) != null && forest.getThing(8, c) instanceof Shadow) {
                shadowCountInRow++;
            }
        }
        
        assertEquals(forest.getSize(), shadowCountInRow);
    }

    /**
     * Deberia: verificar que la sombra se mueva de sur a norte y reaparezca al sur.
     */
    @Test
    public void shouldMoveSouthToNorthCircularly() {
        Forest forest = new Forest();
        Shadow shadow = new Shadow(forest, 0, 5);
        
        shadow.ticTac();
        
        assertTrue(forest.isEmpty(0, 5));
        assertFalse(forest.isEmpty(forest.getSize() - 1, 5));
    }
}
