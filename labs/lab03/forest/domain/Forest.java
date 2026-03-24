package domain;

/**
 * Forest
 * Representa el bosque o tablero principal de la simulación. 
 * Gestiona una matriz bidimensional espacial donde interactúan los diferentes 
 * elementos (Thing) y controla el paso del tiempo global mediante el método ticTac.
 *
 * @author Daniel Felipe Sua y Juan David Munar
 */
public class Forest {
    static private int SIZE = 25;
    private Thing[][] places;
    
    /**
     * Crea un nuevo bosque vacío de tamaño SIZE x SIZE y lo inicializa
     * con los elementos predeterminados de la simulación.
     */
    public Forest() {
        places = new Thing[SIZE][SIZE];
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                places[r][c] = null;
            }
        }
        someThings();
    }

    /**
     * Obtiene el tamaño de un lado de la cuadrícula del bosque.
     * * @return el número de filas o columnas (SIZE)
     */
    public int getSize() {
        return SIZE;
    }

    /**
     * Obtiene el elemento ubicado en una posición específica del bosque.
     * * @param r la fila a consultar
     * @param c la columna a consultar
     * @return el objeto Thing en esa posición, o null si está vacía
     */
    public Thing getThing(int r, int c) {
        return places[r][c];
    }

    /**
     * Coloca un elemento en una posición específica del bosque.
     * * @param r la fila destino
     * @param c la columna destino
     * @param e el objeto Thing a ubicar en la matriz
     */
    public void setThing(int r, int c, Thing e) {
        places[r][c] = e;
    }

    /**
     * Inicializa el bosque con los elementos base 
     * para las pruebas de aceptación
     */
    public void someThings() {   
        Tree beard = new Tree(this, 10, 10);
        Tree soul = new Tree(this, 15, 15);
        
        Squirrel scrat = new Squirrel(this, 5, 5);
        Squirrel sandy = new Squirrel(this, 5, 3); 
        
        Shadow thief = new Shadow(this, 20, 10);
        Shadow lass = new Shadow(this, 15, 20);
        
        Pine munar = new Pine(this, 5, 19);
        Pine sua = new Pine(this, 3, 1);
        
        for (int i = 0; i < 400; i++) {
            munar.ticTac();
        }
        
        Mushroom juan = new Mushroom(this, 18, 20);
        Mushroom daniel = new Mushroom(this, 2, 1);
        
        for (int i = 0; i < 7; i++) {
            daniel.ticTac();
        }
    }
    
    /**
     * Cuenta cuántos vecinos inmediatos (adyacentes o diagonales) son
     * exactamente de la misma clase que el elemento en la posición dada.
     * * @param r la fila del elemento central
     * @param c la columna del elemento central
     * @return el número de vecinos idénticos
     */
    public int neighborsEquals(int r, int c) {
        int num = 0;
        if (inForest(r, c) && places[r][c] != null) {
            for (int dr = -1; dr < 2; dr++) {
                for (int dc = -1; dc < 2; dc++) {
                    if ((dr != 0 || dc != 0) && inForest(r + dr, c + dc) && 
                       (places[r + dr][c + dc] != null) &&  
                       (places[r][c].getClass() == places[r + dr][c + dc].getClass())) {
                        num++;
                    }
                }
            }
        }
        return num;
    }

    /**
     * Verifica si una celda específica está completamente vacía (null).
     * * @param r la fila a verificar
     * @param c la columna a verificar
     * @return true si la celda está dentro del bosque y no contiene ningún objeto
     */
    public boolean isEmpty(int r, int c) {
        return (inForest(r, c) && places[r][c] == null);
    }    
        
    /**
     * Verifica si unas coordenadas dadas se encuentran dentro de los límites de la matriz.
     * * @param r la fila a verificar
     * @param c la columna a verificar
     * @return true si las coordenadas son válidas dentro del tamaño del bosque
     */
    private boolean inForest(int r, int c) {
        return ((0 <= r) && (r < SIZE) && (0 <= c) && (c < SIZE));
    }
    
    /**
     * Avanza el tiempo global de la simulación un paso, delegando la acción
     * a cada uno de los elementos presentes en el tablero.
     */
    public void ticTac() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (places[r][c] != null) {
                    places[r][c].ticTac();
                }
            }
        }
    }
}