/**
 * Genera las paredes internas, destinos y cajas de manera aleatoria, donde
 * el numero de cada una equivale al 10% del area del tablero.
 */
public void generate(){

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
public void modifyColors(string[] colors){

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
 * Abre un juego que ya había sido guardado previamente
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
    return null;
}

/**
 * Retorna el numero de cajas que estan en su destino.
 * @return Numero de cajas en el destino
 */
public int score(){
    return 0;
}
