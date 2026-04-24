package domain;

import java.io.IOException;
import java.util.*;


/**
 * Representa al juego Sokoban <br>
 * <b>(height, width, numElements, boxesAtDestination, colors, board)</b> <br>
 * <b>Inv:</b> height > 0 y width > 0 y numElements > 0 y 
 * numElements == (int)(height * width / 10) y 
 * boxesAtDestination >= 0 y boxesAtDestination <= numElements 
 */

public class Sokoban {
    private int height;
    private int width;    
    private int numElements;
    private int boxesAtDestination;
    private String[] colors;
    
    private char[][] board;


    /**
     * Crea un juego Sokoban con las dimensiones dadas
     * @param height Numero de filas
     * @param width Numero de columnas
     */
    public Sokoban(int height, int width){
        this.height = height;
        this.width = width;

        double area = height * width;
        this.numElements = (int) (area / 10);
        this.boxesAtDestination = 0;
        this.colors = new String[] {"cafe","naranja","rosa"};
        this.board = new char[height][width];       
        
    }

    /**
     * Genera las paredes internas, destinos y cajas de manera aleatoria, donde
     * el numero de cada una equivale al 10% del area del tablero.
     * Reglas de generacion:
     * 1. El jugador debe poder alcanzar todas las posiciones necesarias para empujar las cajas y las cajas deben poder llegar
     * a los destinos
     * 2. No permitir deadlocks estructurales, es decir cajas en esquinas inicialmente o paredes sin salida lateral, en general
     * en posiciones donde las cajas no se puedan mover    
     * 3. Distancia minima entre el jugador y las cajas al inicio = 2(el jugador no aparezca pegado a una caja al inicio porque podría
     * bloquearla sin querer contra una pared )
     */     
    public void generate(){
        
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                board[row][col] = 'e';
            }
        }

        for (int row = 0; row < height; row++) {
            board[row][0] = 'w';
            board[row][width-1] = 'w';
        }
        for (int col = 0; col < width; col++) {
            board[0][col] = 'w';
            board[height-1][col] = 'w';
        }

        Random rand = new Random();

        char[] elements = new char[] {'w','b','d'};
        for (char e:elements) {
            for (int i = 0; i < numElements; i++) {
                int randomRow, randomCol;
                do{
                    randomRow = rand.nextInt(board.length);
                    randomCol = rand.nextInt(board[0].length);
                } while ((board[randomRow][randomCol] != 'e')
                    || (e == 'b' && isStuck(randomRow, randomCol)) 
                    || (e == 'b' && isBoxesInAdjacency(randomRow, randomCol)) 
                );
                board[randomRow][randomCol] = e;
            }               
        }    

        int randomRow, randomCol;
        do{
            randomRow = rand.nextInt(board.length);
            randomCol = rand.nextInt(board[0].length);
        } while ((board[randomRow][randomCol] != 'e')
            || areBoxNeighbors(randomRow, randomCol));
        board[randomRow][randomCol] = 'p';

        if (!isConnected()){
            generate();
        } else{
            System.out.println("Tablero conectado");
        }      
                   
    }

    /**
     * Verifica si al colocar una caja en la posición dada se forma un bloque 2x2
     * con otras tres cajas adyacentes.
     * @param row Fila de la posicion a evaluar
     * @param col Columna de la posicion a evaluar
     * @return true si se forma un bloque 2x2 de cajas, false en caso contrario
     */ 
    private boolean isBoxesInAdjacency(int row, int col) {
        boolean badAdjacency = false;
        badAdjacency = badAdjacency || (board[row-1][col] == 'b' && board[row][col-1] == 'b' && board[row-1][col-1] == 'b');
        badAdjacency = badAdjacency || (board[row-1][col] == 'b' && board[row][col+1] == 'b' && board[row-1][col+1] == 'b');
        badAdjacency = badAdjacency || (board[row+1][col] == 'b' && board[row][col-1] == 'b' && board[row+1][col-1] == 'b');
        badAdjacency = badAdjacency || (board[row+1][col] == 'b' && board[row][col+1] == 'b' && board[row+1][col+1] == 'b');
        return badAdjacency;
    }

    /**
     * Verifica si existen cajas adyacentes a la posicion dada.
     * @param row Fila de la posicion a evaluar
     * @param col Columna de la posicion a evaluar
     * @return true si hay al menos una caja vecina, false en caso contrario
     */
    private boolean areBoxNeighbors(int row, int col) {
        boolean neighborVertical = board[row-1][col] == 'b' || board[row+1][col] == 'b';
        boolean neighborHorizontal = board[row][col-1] == 'b' || board[row][col+1] == 'b';
        return neighborVertical || neighborHorizontal;
    }

    /**
     * Determina si una posicion corresponde a un atascamiento de una caja con respecto 
     * a las paredes
     * @param row Fila de la posicion a evaluar
     * @param col Columna de la posicion a evaluar
     * @return true si la posicion esta bloqueada, false en caso contrario
     */
    private boolean isStuck(int row, int col) {
        boolean blockedVertical = board[row-1][col] == 'w' || board[row+1][col] == 'w';
        boolean blockedHorizontal = board[row][col-1] == 'w' || board[row][col+1] == 'w';
        return blockedVertical && blockedHorizontal;
    }

    /**
     * Verifica que todas las cajas tienen al menos una posicion desde la que el jugador
     * puede empujarlas a una celda no bloqueada.
     * @param visited Celdas alcanzables por el jugador
     * @return true si todas las cajas pueden ser empujadas, false en caso contrario
     */
    private boolean allBoxesCanBePushed(boolean[][] visited) {
        int[][] directions = {{-1,0},{1,0},{0,-1},{0,1}};
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (board[row][col] == 'b') {
                    boolean canPush = false;
                    for (int[] dir : directions) {
                        int pushRow = row - dir[0];
                        int pushCol = col - dir[1];
                        int destRow = row + dir[0];
                        int destCol = col + dir[1];
                        if (pushRow >= 0 && pushRow < height && pushCol >= 0 && pushCol < width
                            && destRow >= 0 && destRow < height && destCol >= 0 && destCol < width
                            && visited[pushRow][pushCol] && board[destRow][destCol] != 'w') {
                            canPush = true;
                        }
                    }
                    if (!canPush) return false;
                }
            }
        }
        return true;
    }

    /**
     * Verifica que todas las cajas y destinos son alcanzables sin atravesar paredes.
     * @return true si todas las cajas y destinos son alcanzables, false en caso contrario
     */
    private boolean isConnected() {
        int startRow = -1, startCol = -1;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (board[row][col] == 'p') {
                    startRow = row;
                    startCol = col;
                }
            }
        }

        boolean[][] visited = new boolean[height][width];
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startRow, startCol});
        visited[startRow][startCol] = true;

        int[][] directions = {{-1,0},{1,0},{0,-1},{0,1}};
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            for (int[] dir : directions) {
                int newRow = current[0] + dir[0];
                int newCol = current[1] + dir[1];
                if (newRow >= 0 && newRow < height && newCol >= 0 && newCol < width
                    && !visited[newRow][newCol] && board[newRow][newCol] != 'w'
                    && board[newRow][newCol] != 'b') {
                    visited[newRow][newCol] = true;
                    queue.add(new int[]{newRow, newCol});
                }
            }
        }

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (board[row][col] == 'd' && !visited[row][col]) return false;
            }
        }

        return allBoxesCanBePushed(visited);
    }

    /**
     * Mueve el jugador en una dirección posible
     * Si hay una caja en el camino la empuja a la siguiente celda vacia, si hay una pared
     * no permite que el jugador se mueva
     * @param direction direcciones validas: 'u'=arriba, 'd'=abajo, 'l'=izquierda, 'r'=derecha.
     */
    public void move(char direction){
        int[] pos = playerPosition();
        int row = pos[0];
        int col = pos[1];
        char leaving = (board[row][col] == 'P') ? 'd' : 'e';

        int nextRow = row, nextCol = col;
        if (direction == 'u') nextRow = row - 1;
        else if (direction == 'd') nextRow = row + 1;
        else if (direction == 'l') nextCol = col - 1;
        else if (direction == 'r') nextCol = col + 1;

        if (board[nextRow][nextCol] == 'w') return;

        if (board[nextRow][nextCol] == 'b' || board[nextRow][nextCol] == 'B') {
            char boxType = board[nextRow][nextCol];
            int afterRow = nextRow + (nextRow - row);
            int afterCol = nextCol + (nextCol - col);

            switch (board[afterRow][afterCol]) {
                case 'e':
                    board[row][col] = leaving;
                    board[afterRow][afterCol] = 'b';
                    
                    if (boxType == 'B') {
                        boxesAtDestination--; 
                        board[nextRow][nextCol] = 'P';
                    } else {
                        board[nextRow][nextCol] = 'p';
                    }
                    break;
                    
                case 'd':
                    board[row][col] = leaving;
                    board[afterRow][afterCol] = 'B';
                    
                    if (boxType == 'b') {
                        boxesAtDestination++;
                        board[nextRow][nextCol] = 'p';
                    } else {
                        board[nextRow][nextCol] = 'P'; 
                    }
                    break;
                    
                case 'w': case 'b': case 'B':
                    break;
            }
        } 
        else if (board[nextRow][nextCol] == 'd') {
            board[row][col] = leaving;
            board[nextRow][nextCol] = 'P';
        } 
        else {
            board[row][col] = leaving;
            board[nextRow][nextCol] = 'p';
        }
    }

    /**
     * Retorna la posicion del jugador en el tablero
     * @return arreglo con la fila y columna del jugador
     */
    public int[] playerPosition(){
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                if (board[i][j] == 'p' || board[i][j] == 'P'){
                    return new int[] {i, j};
                }
            }
        }
        return null;
    }

    /**
     * Modifica los colores de cajas y destinos.
     * @param colors Arreglo de 3 colores: [0]=cajas en el destino, [1]=cajas, 
     * [2]=destinos.
     */
    public void modifyColors(String[] colors){
        this.colors = colors;
    }

    /**
     * Modifica el tamaño del tablero y genera uno con las nuevas dimensiones
     * @param height El nuevo numero de filas
     * @param width El nuevo numero de columnas
     */
    public void modifySizeBoard(int height, int width){
        this.height = height;
        this.width = width;
        this.numElements = (int)(height * width / 10);
        this.board = new char[height][width];
        generate();
    }

    /**
     * Guarda un juego con su estado actual
     * @param fileName nombre del archivo para guardar
     * @throws IOException si no se puede guardar en el equipo
     */
    public void saveGame(String fileName) throws IOException{

    }

    /**
     * Abre un juego que ya habia sido guardado previamente
     * @param fileName nombre del archivo para abrir
     * @throws IOException si no se puede leer el archivo
     */
    public void openGame(String fileName) throws IOException{

    }

    /**
     * Retorna el estado actual del tablero.
     * @return Una matriz que representa la posición de todos los caracteres en el juego
     * 'e'=empty, 'w'=wall, 'p'=player, 'P'=player on destination, 
     * 'b'=box, 'B'=box at destination, 'd'=destination
     */
    public char[][] board(){
        return board;
    }

    /**
     * Retorna el numero de cajas que estan en su destino.
     * @return Numero de cajas en el destino
     */
    public int score(){
        return boxesAtDestination;
    }

    public static void main(String[] args) {
        Sokoban s = new Sokoban(9, 7);
        s.generate();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 7; col++) {
                System.out.print(s.board()[row][col] + " ");
            }
            System.out.println();
        }
        s.move('d');
        System.out.println();
        System.out.println();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 7; col++) {
                System.out.print(s.board()[row][col] + " ");
            }
            System.out.println();
        }
    }

}
