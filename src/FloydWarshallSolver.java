import java.util.ArrayList;
import java.util.List;

import static java.lang.Double.NEGATIVE_INFINITY;
import static java.lang.Double.POSITIVE_INFINITY;

public class FloydWarshallSolver {
    private final int n;
    private long iterations;
    private Graph graph;
    private double[][] shortestPathTable; // двумерный массив с кратчайшими расстояниями между всеми вершинами
    private Integer[][] next; // вершины, через которые мы проходим на кратчайшем пути
    private static final int REACHES_NEGATIVE_CYCLE = -1;

    public FloydWarshallSolver(Graph graph) {
        double[][] matrix = graph.getGraphEdges();
        n = matrix.length;
        shortestPathTable = new double[n][n];
        next = new Integer[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] != POSITIVE_INFINITY) next[i][j] = j; // задаем матрицу-преемников. Изначально на пути из i в j преемником i будет вершина j
                shortestPathTable[i][j] = matrix[i][j]; // значения исходной матрицы копируем в массив с кратчайшими расстояниями
            }
        }
    }

    public double[][] getApspMatrix() {
        solve();
        return shortestPathTable;
    }
    public Integer[][] getNext() {
        return next;
    }

    public void solve() {
        iterations = 0;

        for (int k = 0; k < n; k++) { // выбираем промежуточную вершину, которую будем рассматривать в кратчайшем пути
            for (int i = 0; i < n; i++) { // проходимся по всем парам вершин в графе;
                for (int j = 0; j < n; j++) {
                    iterations += 1;
                    if (shortestPathTable[i][k] + shortestPathTable[k][j] < shortestPathTable[i][j]) {
                        // если расстояние меду вершинами i j при проходе через промежуточную вершину k меньше,
                        // чем расстояние между вершинами i j напрямую, то перезаписываем значение
                        shortestPathTable[i][j] = shortestPathTable[i][k] + shortestPathTable[k][j];
                        next[i][j] = next[i][k]; // теперь путь из i j ведёт через того же преемника, что и путь из i в k
                    }
                }
            }
        }

        // Вычисляем циклы отрицательного веса
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    iterations += 1;
                    if (shortestPathTable[i][k] + shortestPathTable[k][j] < shortestPathTable[i][j]) {
                        // если путь уменьшился после работы всего алгоритма, то это значит, что на пути из
                        // вершины i в вершину j был встречен цикл отрицательного веса
                        shortestPathTable[i][j] = NEGATIVE_INFINITY; // значение пути задаем как - бесконечность
                        next[i][j] = REACHES_NEGATIVE_CYCLE; // помечаем, что преемник из вершины i в вершину j
                        // будет -1, это значит, что был встречен цикл отрицательного веса
                    }
                }
            }
        }
    }
    public long getIterations() {
        return iterations;
    }

    //  Если «начало» и «конец» не связаны, возвращаем пустой массив. Если кратчайший путь от «начала» до «конца»
    //   достижим посредством отрицательного цикла, возвращаем null
    public List<Integer> reconstructShortestPath(int start, int end) {
        solve();
        List<Integer> path = new ArrayList<>(); // создаем список вершин, через которые пройдёт путь из start в end
        if (shortestPathTable[start][end] == POSITIVE_INFINITY) return path;
        int at = start;
        while (at != end) {
            if (at == REACHES_NEGATIVE_CYCLE) return null;
            path.add(at);
            at = next[at][end]; // проходимся по всем преемникам из вершины start в вершину end
        }
        if (next[at][end] == REACHES_NEGATIVE_CYCLE) return null;
        path.add(end);
        return path;
    }
}
