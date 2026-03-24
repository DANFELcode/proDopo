package test;

import static org.junit.Assert.*;
import org.junit.Test;
import domain.*;
import java.awt.Color;

/**
 * MushroomTest
 * Proporciona las pruebas unitarias para asegurar el correcto comportamiento visual
 * y la mecánica de propagación del hongo.
 *
 * @author Daniel Felipe Sua y Juan David Munar
 */
public class MushroomTest {

    /**
     * Deberia: verificar que el hongo nazca con el color morado.
     */
    @Test
    public void shouldBePurple() {
        Forest forest = new Forest();
        Mushroom mushroom = new Mushroom(forest, 3, 3);

        assertEquals(new Color(128, 0, 128), mushroom.getColor());
    }

    /**
     * Deberia: verificar que la forma del hongo sea redonda.
     */
    @Test
    public void shouldBeRound() {
        Forest forest = new Forest();
        Mushroom mushroom = new Mushroom(forest, 4, 4);

        assertEquals(Thing.ROUND, mushroom.shape());
    }

    /**
     * Deberia: verificar que el hongo se propague a una celda vecina despues de 8 tictacs.
     */
    @Test
    public void shouldSpreadAfter8TicTacs() {
        Forest forest = new Forest();
        Mushroom mushroom = new Mushroom(forest, 5, 5);

        for (int i = 0; i < 8; i++) {
            mushroom.ticTac();
        }

        int mushroomCount = 0;
        for (int r = 4; r <= 6; r++) {
            for (int c = 4; c <= 6; c++) {
                if (forest.getThing(r, c) instanceof Mushroom) {
                    mushroomCount++;
                }
            }
        }

        assertEquals(2, mushroomCount);
    }
    
    /**
     * Deberia: verificar que el hongo es inmortal y permanece en su posicion inicial
     * incluso despues de una cantidad masiva de tictacs, ya que no hereda de LivingThing.
     */
    @Test
    public void shouldNeverDie() {
        Forest forest = new Forest();
        Mushroom mushroom = new Mushroom(forest, 10, 10);

        for (int i = 0; i < 1000; i++) {
            mushroom.ticTac();
        }

        assertEquals(mushroom, forest.getThing(10, 10));
    }
}
