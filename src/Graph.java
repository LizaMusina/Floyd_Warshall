import java.util.LinkedList;

public class Graph {
    private int vertices;
    private double[][] graphEdges;
    private LinkedList<Integer> adjacencyList[];

    private int countIterations;
    public Graph(int vertices) {
        this.vertices = vertices;
        adjacencyList = new LinkedList[vertices];
        for (int i = 0; i < vertices; i++) {
            adjacencyList[i] = new LinkedList<>();
        }
        graphEdges = new double[vertices][vertices];
    }
    public int getVertices() {
        return vertices;
    }
    public LinkedList<Integer>[] getAdjacencyList() {
        return adjacencyList;
    }

    public double[][] getGraphEdges() {
        return graphEdges;
    }
    public void setValue(int i, int j, double value) {
        graphEdges[i][j] = value;
    }

    public void addEdge(int source, int dest, double weight) {
        adjacencyList[source].addFirst(dest);
        graphEdges[source][dest] = weight;
    }

}
