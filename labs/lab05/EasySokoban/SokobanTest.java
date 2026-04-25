package test;

import domain.Sokoban;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class SokobanTest {

    private Sokoban sokoban;

    @Before
    public void setUp() {
        sokoban = new Sokoban(9, 7);
        sokoban.generate();
    }

    @Test
    // Verifica que el tablero tiene exactamente las dimensiones dadas al constructor
    public void accordingMSBoardHasCorrectDimensions() {
        assertEquals(9, sokoban.board().length);
        assertEquals(7, sokoban.board()[0].length);
    }

    @Test
    // Verifica que todos los bordes del tablero estan ocupados por paredes externas
    public void accordingMSAllBorderCellsAreWalls() {
        char[][] board = sokoban.board();
        for (int i = 0; i < 9; i++) {
            assertEquals('w', board[i][0]);
            assertEquals('w', board[i][6]);
        }
        for (int j = 0; j < 7; j++) {
            assertEquals('w', board[0][j]);
            assertEquals('w', board[8][j]);
        }
    }

    @Test
    // Verifica que existe exactamente un jugador en el tablero tras generate
    public void accordingMSExactlyOnePlayerOnBoard() {
        char[][] board = sokoban.board();
        int count = 0;
        for (char[] row : board)
            for (char c : row)
                if (c == 'p' || c == 'P') count++;
        assertEquals(1, count);
    }

    @Test
    // Verifica que se generan exactamente 6 cajas, 6 destinos y 6 paredes internas (10% de 63)
    public void accordingMSSixElementsOfEachTypeGenerated() {
        char[][] board = sokoban.board();
        int boxes = 0, destinations = 0, walls = 0;
        for (int i = 1; i < 8; i++)
            for (int j = 1; j < 6; j++) {
                if (board[i][j] == 'b') boxes++;
                if (board[i][j] == 'd') destinations++;
                if (board[i][j] == 'w') walls++;
            }
        assertEquals(6, boxes);
        assertEquals(6, destinations);
        assertEquals(6, walls);
    }

    @Test
    // Verifica que ninguna caja queda en las esquinas internas donde no puede moverse
    public void accordingMSNoBoxesInInternalCorners() {
        char[][] board = sokoban.board();
        assertNotEquals('b', board[1][1]);
        assertNotEquals('b', board[1][5]);
        assertNotEquals('b', board[7][1]);
        assertNotEquals('b', board[7][5]);
    }

    @Test
    // Verifica que el jugador no aparece adyacente a ninguna caja al iniciar
    public void accordingMSPlayerNotAdjacentToBoxAtStart() {
        char[][] board = sokoban.board();
        int[] pos = sokoban.playerPosition();
        int r = pos[0], c = pos[1];
        assertNotEquals('b', board[r - 1][c]);
        assertNotEquals('b', board[r + 1][c]);
        assertNotEquals('b', board[r][c - 1]);
        assertNotEquals('b', board[r][c + 1]);
    }

    @Test
    // Verifica que moverse a una celda vacia retorna true y actualiza la posicion del jugador
    public void accordingMSMoveToEmptyCellReturnsTrueAndUpdatesPosition() {
        char[][] board = sokoban.board();
        int[] pos = sokoban.playerPosition();
        int r = pos[0], c = pos[1];

        if (board[r - 1][c] == 'e') {
            assertTrue(sokoban.move('u'));
            assertEquals(r - 1, sokoban.playerPosition()[0]);
        } else if (board[r + 1][c] == 'e') {
            assertTrue(sokoban.move('d'));
            assertEquals(r + 1, sokoban.playerPosition()[0]);
        } else if (board[r][c - 1] == 'e') {
            assertTrue(sokoban.move('l'));
            assertEquals(c - 1, sokoban.playerPosition()[1]);
        } else if (board[r][c + 1] == 'e') {
            assertTrue(sokoban.move('r'));
            assertEquals(c + 1, sokoban.playerPosition()[1]);
        }
    }

    @Test
    // Verifica que moverse hacia una pared retorna false sin modificar el score
    public void accordingMSMoveIntoWallReturnsFalseAndPreservesScore() {
        char[][] board = sokoban.board();
        int[] pos = sokoban.playerPosition();
        int r = pos[0], c = pos[1];
        int scoreBefore = sokoban.score();

        char dir = 0;
        if (board[r - 1][c] == 'w') dir = 'u';
        else if (board[r + 1][c] == 'w') dir = 'd';
        else if (board[r][c - 1] == 'w') dir = 'l';
        else if (board[r][c + 1] == 'w') dir = 'r';

        if (dir != 0) {
            assertFalse(sokoban.move(dir));
            assertEquals(scoreBefore, sokoban.score());
        }
    }

    @Test
    // Verifica que restart restaura la posicion original del jugador y resetea el score a 0
    public void accordingMSRestartRestoresPlayerPositionAndScore() {
        int[] initialPos = sokoban.playerPosition().clone();
        sokoban.move('u');
        sokoban.move('d');
        sokoban.move('l');
        sokoban.move('r');
        sokoban.restart();
        assertArrayEquals(initialPos, sokoban.playerPosition());
        assertEquals(0, sokoban.score());
    }

    @Test
    // Verifica que playerWin retorna false mientras no todas las cajas esten en destino
    public void accordingMSPlayerHasNotWonAtStart() {
        assertFalse(sokoban.playerWin());
        assertEquals(0, sokoban.score());
    }

    @Test
    // Verifica que modifySizeBoard cambia dimensiones, genera nuevo tablero y resetea score
    public void accordingMSModifySizeBoardUpdatesDimensionsAndResetsScore() {
        sokoban.modifySizeBoard(10, 8);
        assertEquals(10, sokoban.board().length);
        assertEquals(8, sokoban.board()[0].length);
        assertEquals(0, sokoban.score());
        assertNotNull(sokoban.playerPosition());
    }
}