package test;
import static org.junit.Assert.*;
import org.junit.Test;
import domain.*;
import java.awt.Color;

/**
 * SquirrelTest - Pruebas de unidad para Squirrel.
 * Todas las pruebas se ejecutan en modo invisible.
 * @author Daniel Felipe Sua y Juan David Munar
 */

public class SquirrelTest {

    /**
     * Deberia: verificar que la ardilla nazca con el color café inicial correcto.
     */
    @Test
    public void shouldBeBrownInitially() {
        Forest forest = new Forest();
        Squirrel squirrel = new Squirrel(forest, 5, 5);
        
        // Verificamos que el color al nacer sea el café (139, 69, 19)
        assertEquals(new Color(139, 69, 19), squirrel.getColor());
    }

    /**
     * Deberia: verificar que la ardilla muera y desaparezca del bosque al cumplir 10 años (40 tic-tacs).
     */
    @Test
    public void shouldDieAfter10Years() {
        Forest forest = new Forest();
        Squirrel squirrel = new Squirrel(forest, 5, 5);
        
        for (int i = 0; i < 40; i++) {
            squirrel.ticTac();
        }
        
        boolean isAliveInForest = false;
        for (int r = 0; r < forest.getSize(); r++) {
            for (int c = 0; c < forest.getSize(); c++) {
                if (forest.getThing(r, c) == squirrel) {
                    isAliveInForest = true;
                }
            }
        }
        
        assertFalse("La ardilla debería haber muerto y desaparecido del bosque", isAliveInForest);
    }
}
