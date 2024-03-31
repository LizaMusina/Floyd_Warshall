import java.io.*;
import java.util.Random;

import static java.lang.Double.POSITIVE_INFINITY;

public class Main {
    public static Graph readFilesFromDirectory(String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        Graph graph = null;
        if (files != null) {
            for (File file : files) {
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new FileReader(file));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                String line = reader.readLine();

                String[] splitLine = line.split(" ");
                int vertices = Integer.parseInt(splitLine[0]);
                int edges = Integer.parseInt(splitLine[1]);

                graph = new Graph(vertices);
                String newLine;

                while ((newLine = reader.readLine()) != null) {
                    String[] edg = newLine.split(" ");
                    int u = Integer.parseInt(edg[0]);
                    int v = Integer.parseInt(edg[1]);
                    double weight = Double.parseDouble(edg[2]);
                    graph.addEdge(u, v, weight);
                }
                reader.close();

                for (int i = 0; i < vertices; i++) {
                    graph.setValue(i, i, 0);
                    for (int j = 0; j < vertices; j++) {
                        if (graph.getGraphEdges()[i][j] == 0) {
                            graph.setValue(i, j, POSITIVE_INFINITY);
                        }
                    }
                }
            }
        }
        return graph;
    }
    public static void main(String[] args) throws IOException {
        String directoryPath = "C:/Users/1/IdeaProjects/Floyd_Warshall/files";
        File results = new File("C:/Users/1/IdeaProjects/Floyd_Warshall/src/finalResults.txt");

        Random random = new Random();
        int numOfCases = random.nextInt(51) + 50;

        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        int k = 0;

        FileWriter fileWriter = new FileWriter(results, true);

        if (files != null) {
            for (File file : files) {
                System.out.println(k);
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new FileReader(file));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                String line = reader.readLine();

                String[] splitLine = line.split(" ");
                int vertices = Integer.parseInt(splitLine[0]);
                int edges = Integer.parseInt(splitLine[1]);

                Graph graph = new Graph(vertices);
                String newLine;

                while ((newLine = reader.readLine()) != null) {
                    String[] edg = newLine.split(" ");
                    int u = Integer.parseInt(edg[0]);
                    int v = Integer.parseInt(edg[1]);
                    double weight = Double.parseDouble(edg[2]);
                    graph.addEdge(u, v, weight);
                }
                reader.close();

                for (int i = 0; i < vertices; i++) {
                    graph.setValue(i, i, 0);
                    for (int j = 0; j < vertices; j++) {
                        if (graph.getGraphEdges()[i][j] == 0) {
                            graph.setValue(i, j, POSITIVE_INFINITY);
                        }
                    }
                }

                long start = System.currentTimeMillis();
                FloydWarshallSolver solve = new FloydWarshallSolver(graph);
                solve.solve();
                long end = System.currentTimeMillis();
                long time = end - start;
                System.out.println(end - start);
                System.out.println(solve.getIterations());
                fileWriter.write(graph.getVertices() + " " + time + " " + solve.getIterations() + "\n");
                k += 1;
            }
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
