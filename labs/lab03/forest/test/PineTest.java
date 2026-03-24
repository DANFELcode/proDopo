package test;

import static org.junit.Assert.*;
import org.junit.Test;
import domain.*;
import java.awt.Color;

/**
 * PineTest
 * Proporciona las pruebas unitarias para asegurar el correcto comportamiento
 * visual y de envejecimiento del pino.
 *
 * @author Daniel Felipe Sua y Juan David Munar
 */
public class PineTest {

    /**
     * Deberia: verificar que el pino nazca con el color verde oscuro.
     */
    @Test
    public void shouldBeDarkGreenInitially() {
        Forest forest = new Forest();
        Pine pine = new Pine(forest, 2, 2);
        
        assertEquals(new Color(0, 100, 0), pine.getColor());
    }

    /**
     * Deberia: verificar que el pino no cambie de color con el paso de los tictacs.
     */
    @Test
    public void shouldNotChangeColorAfterTicTacs() {
        Forest forest = new Forest();
        Pine pine = new Pine(forest, 2, 2);
        
        pine.ticTac();
        pine.ticTac();
        pine.ticTac();
        
        assertEquals(new Color(0, 100, 0), pine.getColor());
    }

    /**
     * Deberia: verificar que el pino envejezca y consuma energía como un árbol normal.
     */
    @Test
    public void shouldAgeAndConsumeEnergyLikeNormalTree() {
        Forest forest = new Forest();
        Pine pine = new Pine(forest, 2, 2);
        
        int initialEnergy = pine.getEnergy();
        
        for (int i = 0; i < 4; i++) {
            pine.ticTac();
        }
        
        assertTrue(pine.getEnergy() < initialEnergy);
    }

    
    /**
     * Deberia: verificar que el pino muera y sea removido del bosque cuando su energia se 
     * agota por completo.
     */
    @Test
    public void shouldDieWhenEnergyDepletes() {
        Forest forest = new Forest();
        Pine pine = new Pine(forest, 10, 8);
        
        for (int i = 0; i < 410; i++) {
            pine.ticTac();
        }
        
        assertNull(forest.getThing(10, 8));
    }
}
