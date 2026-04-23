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
     * 3. Las cajas no pueden estar en sus destinos al inicio     
     * 4. Distancia minima entre el jugador y las cajas al inicio = 2(el jugador no aparezca pegado a una caja al inicio porque podría
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
                } while (board[randomRow][randomCol] != 'e');
                board[randomRow][randomCol] = e;
            }               
        }


        int randomRow, randomCol;
        do{
            randomRow = rand.nextInt(board.length);
            randomCol = rand.nextInt(board[0].length);
        } while (board[randomRow][randomCol] != 'e');
        board[randomRow][randomCol] = 'p';

    
        // Regla 1
        if (!isConnected()){
            generate();
        } else{
            System.out.println("Tablero conectado");
        }

        
                   
    }
 
    
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
                    && !visited[newRow][newCol] && board[newRow][newCol] != 'w') {
                    visited[newRow][newCol] = true;
                    queue.add(new int[]{newRow, newCol});
                }
            }
        }

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if ((board[row][col] == 'b' || board[row][col] == 'd') && !visited[row][col]) {
                    return false;
                }
            }
        }
        return true;
        
    }

    /**
     * Mueve el jugador en una dirección posible
     * Si hay una caja en el camino la empuja a la siguiente celda vacia, si hay una pared
     * no permite que el jugador se mueva
     * @param direction direcciones validas: 'U'=arriba, 'D'=abajo, 'L'=izquierda, 'R'=derecha.
     */
    public void move(char direction){

    }

    /**
     * Modifica los colores de cajas y destinos.
     * @param colors Arreglo de 3 colores: [0]=cajas en el destino, [1]=cajas, 
     * [2]=destinos.
     */
    public void modifyColors(String[] colors){

    }

    /**
     * Modifica el tamaño del tablero y genera uno con las nuevas dimensiones
     * @param height El nuevo numero de filas
     * @param width El nuevo numero de columnas
     */
    public void modifySizeBoard(int height, int width){

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
     * 'e' = empty, 'w' = wall, 'p' = player, 'b' = box, 'd' = destination, 'B' = box at destination
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
    }

}
