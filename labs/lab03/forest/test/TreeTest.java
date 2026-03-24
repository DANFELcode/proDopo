package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import domain.*;
import java.awt.Color;

public class TreeTest {

    private Forest forest;
    private Tree tree;

    @BeforeEach
    public void setUp() {
        forest = new Forest();
        tree = new Tree(forest, 0, 0);
    }

    @AfterEach
    public void tearDown() {
        forest = null;
        tree = null;
    }

    /**
     * Deberia: verificar que el árbol nazca con el color rosado inicial.
     */
    @Test
    public void shouldBePinkInitially() {
        assertEquals(Color.PINK, tree.getColor());
    }

    /**
     * Deberia: verificar que el árbol cambie de color después de un tic-tac.
     */
    @Test
    public void shouldChangeColorAfterTicTac() {
        tree.ticTac();
        assertEquals(Color.GREEN, tree.getColor());
    }

    /**
     * Deberia: verificar que el árbol muera cuando se agota su energía.
     */
    @Test
    public void shouldDieWhenEnergyDepletes() {
        for (int i = 0; i < 410; i++) {
            tree.ticTac();
        }
        assertNull(forest.getThing(0, 0));
    }
}