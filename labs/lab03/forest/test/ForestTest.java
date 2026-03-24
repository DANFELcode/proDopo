package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import domain.*;
import java.awt.Color;

public class ForestTest {

    private Forest forest;

    @BeforeEach
    public void setUp() {
        forest = new Forest();
    }

    @AfterEach
    public void tearDown() {
        forest = null;
    }

    // =========================================================
    // TREE
    // =========================================================

    /**
     * Deberia: verificar que el árbol nazca con color rosado.
     */
    @Test
    public void treeShouldBePinkInitially() {
        Tree tree = new Tree(forest, 0, 0);
        assertEquals(Color.PINK, tree.getColor());
    }

    /**
     * Deberia: verificar el ciclo completo de colores del árbol.
     * tictac=1→GREEN, tictac=2→ORANGE, tictac=3→GRAY, tictac=4→PINK
     */
    @Test
    public void treeShouldCycleColors() {
        Tree tree = new Tree(forest, 0, 0);

        tree.ticTac();
        assertEquals(Color.GREEN, tree.getColor());

        tree.ticTac();
        assertEquals(Color.ORANGE, tree.getColor());

        tree.ticTac();
        assertEquals(Color.GRAY, tree.getColor());

        tree.ticTac();
        assertEquals(Color.PINK, tree.getColor());
    }

    /**
     * Deberia: verificar que el árbol consuma energía cada 4 tic-tacs.
     * step() se llama cuando tictac%4==3
     */
    @Test
    public void treeShouldConsumeEnergyEvery4TicTacs() {
        Tree tree = new Tree(forest, 0, 0);
        int initialEnergy = tree.getEnergy();

        for (int i = 0; i < 4; i++) {
            tree.ticTac();
        }

        assertEquals(initialEnergy - 1, tree.getEnergy());
    }

    /**
     * Deberia: verificar que el árbol muera al agotar su energía (~399 tic-tacs).
     */
    @Test
    public void treeShouldDieWhenEnergyDepletes() {
        Tree tree = new Tree(forest, 0, 0);

        for (int i = 0; i < 410; i++) {
            tree.ticTac();
        }

        assertNull(forest.getThing(0, 0));
    }

    /**
     * Deberia: verificar que el árbol tenga forma redonda.
     */
    @Test
    public void treeShouldBeRound() {
        Tree tree = new Tree(forest, 0, 0);
        assertEquals(Thing.ROUND, tree.shape());
    }

    // =========================================================
    // PINE
    // =========================================================

    /**
     * Deberia: verificar que el pino siempre sea verde oscuro sin importar
     * cuántos tic-tacs pasen.
     */
    @Test
    public void pineShouldAlwaysBeDarkGreen() {
        Pine pine = new Pine(forest, 0, 0);

        for (int i = 0; i < 20; i++) {
            pine.ticTac();
        }

        assertEquals(new Color(0, 100, 0), pine.getColor());
    }

    /**
     * Deberia: verificar que el pino muera igual que un árbol normal.
     */
    @Test
    public void pineShouldDieWhenEnergyDepletes() {
        Pine pine = new Pine(forest, 0, 0);

        for (int i = 0; i < 410; i++) {
            pine.ticTac();
        }

        assertNull(forest.getThing(0, 0));
    }

    /**
     * Deberia: verificar que el pino consuma energía como un árbol normal.
     */
    @Test
    public void pineShouldConsumeEnergyLikeTree() {
        Pine pine = new Pine(forest, 0, 0);
        int initialEnergy = pine.getEnergy();

        for (int i = 0; i < 4; i++) {
            pine.ticTac();
        }

        assertTrue(pine.getEnergy() < initialEnergy);
    }

    // =========================================================
    // SQUIRREL
    // =========================================================

    /**
     * Deberia: verificar que la ardilla nazca de color café.
     */
    @Test
    public void squirrelShouldBeBrownInitially() {
        Squirrel squirrel = new Squirrel(forest, 0, 0);
        assertEquals(new Color(139, 69, 19), squirrel.getColor());
    }

    /**
     * Deberia: verificar que la ardilla se vuelva más amarillenta con el tiempo.
     * El verde del color aumenta cada tictac%4==1
     */
    @Test
    public void squirrelShouldGetYellowerOverTime() {
        Squirrel squirrel = new Squirrel(forest, 0, 0);
        int initialGreen = squirrel.getColor().getGreen();

        squirrel.ticTac(); // tictac=1 → years++, color cambia

        assertTrue(squirrel.getColor().getGreen() > initialGreen);
    }

    /**
     * Deberia: verificar que la ardilla muera después de 10 años (tictac=37).
     * years++ ocurre en tictac%4==1 → tictac=1,5,9,...,37 → 10 veces
     */
    @Test
    public void squirrelShouldDieAfter10Years() {
        Squirrel squirrel = new Squirrel(forest, 0, 0);

        for (int i = 0; i < 40; i++) {
            squirrel.ticTac();
        }

        boolean stillAlive = false;
        for (int r = 0; r < forest.getSize(); r++) {
            for (int c = 0; c < forest.getSize(); c++) {
                if (forest.getThing(r, c) == squirrel) {
                    stillAlive = true;
                }
            }
        }

        assertFalse(stillAlive);
    }

    // =========================================================
    // SHADOW
    // =========================================================

    /**
     * Deberia: verificar que la sombra sea negra y redonda.
     */
    @Test
    public void shadowShouldBeBlackAndRound() {
        Shadow shadow = new Shadow(forest, 0, 0);
        assertEquals(Color.BLACK, shadow.getColor());
        assertEquals(Thing.ROUND, shadow.shape());
    }

    /**
     * Deberia: verificar que la sombra llene toda su fila al crearse.
     * La fila 0 ya existe en el forest vacío.
     */
    @Test
    public void shadowShouldFillEntireRow() {
        // Usamos una fila vacía — fila 1 no tiene nada en someThings()
        Shadow shadow = new Shadow(forest, 1, 0);

        int count = 0;
        for (int c = 0; c < forest.getSize(); c++) {
            if (forest.getThing(1, c) instanceof Shadow) {
                count++;
            }
        }

        assertEquals(forest.getSize(), count);
    }

    /**
     * Deberia: verificar que la sombra se mueva de sur a norte.
     * Creada en fila 2, después de 1 ticTac debe estar en fila 1.
     */
    @Test
    public void shadowShouldMoveNorth() {
        Shadow shadow = new Shadow(forest, 2, 0);

        shadow.ticTac();

        assertNull(forest.getThing(2, 0));
        assertNotNull(forest.getThing(1, 0));
    }

    /**
     * Deberia: verificar que la sombra reaparezca al sur al llegar a la fila 0.
     */
    @Test
    public void shadowShouldWrapAroundCircularly() {
        Shadow shadow = new Shadow(forest, 0, 0);

        shadow.ticTac();

        assertNull(forest.getThing(0, 0));
        assertNotNull(forest.getThing(forest.getSize() - 1, 0));
    }

    // =========================================================
    // MUSHROOM
    // =========================================================

    /**
     * Deberia: verificar que el hongo sea morado y redondo.
     */
    @Test
    public void mushroomShouldBePurpleAndRound() {
        Mushroom mushroom = new Mushroom(forest, 0, 0);
        assertEquals(new Color(128, 0, 128), mushroom.getColor());
        assertEquals(Thing.ROUND, mushroom.shape());
    }

    /**
     * Deberia: verificar que el hongo se expanda después de 8 tic-tacs.
     */
    @Test
    public void mushroomShouldSpreadAfter8TicTacs() {
        Mushroom mushroom = new Mushroom(forest, 0, 0);

        for (int i = 0; i < 8; i++) {
            mushroom.ticTac();
        }

        int count = 0;
        for (int r = 0; r < forest.getSize(); r++) {
            for (int c = 0; c < forest.getSize(); c++) {
                if (forest.getThing(r, c) instanceof Mushroom) {
                    count++;
                }
            }
        }

        assertTrue(count >= 2);
    }

    /**
     * Deberia: verificar que el hongo nunca muera.
     */
    @Test
    public void mushroomShouldNeverDie() {
        Mushroom mushroom = new Mushroom(forest, 0, 0);

        for (int i = 0; i < 1000; i++) {
            mushroom.ticTac();
        }

        // El hongo original sigue existiendo en algún lugar del bosque
        boolean found = false;
        for (int r = 0; r < forest.getSize(); r++) {
            for (int c = 0; c < forest.getSize(); c++) {
                if (forest.getThing(r, c) == mushroom) {
                    found = true;
                }
            }
        }

        assertTrue(found);
    }

    // =========================================================
    // FOREST
    // =========================================================

    /**
     * Deberia: verificar que Forest.ticTac() llame a todos los objetos del bosque.
     * Comprobamos que un árbol cambie de color al hacer ticTac en el bosque.
     */
    @Test
    public void forestTicTacShouldUpdateAllThings() {
        Tree tree = new Tree(forest, 0, 0);

        forest.ticTac();

        assertNotEquals(Color.PINK, tree.getColor());
    }

    /**
     * Deberia: verificar que isEmpty retorne true en celdas vacías.
     */
    @Test
    public void forestIsEmptyShouldReturnTrueForEmptyCell() {
        assertTrue(forest.isEmpty(0, 0));
    }

    /**
     * Deberia: verificar que isEmpty retorne false cuando hay un objeto.
     */
    @Test
    public void forestIsEmptyShouldReturnFalseWhenOccupied() {
        new Tree(forest, 0, 0);
        assertFalse(forest.isEmpty(0, 0));
    }
}