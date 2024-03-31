import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Test {
    public static void generateAndSaveGraph(int randomNum) throws IOException {
        Random random = new Random();
        File directory = new File("C:/Users/1/IdeaProjects/Floyd_Warshall/files");
        if (!directory.exists()) {
            directory.mkdirs();
        }
        for (int i = 0; i < randomNum; i++) {
            int numE = random.nextInt(9901) + 100;
            int numVertices = random.nextInt(numE+1);

            File file = new File(directory, "file" + i + ".txt");
            try(FileWriter writer = new FileWriter(file)) {

                writer.write(numVertices + " " + numE + "\n");

                for (int j = 0; j < numE; j++) {
                    int u = random.nextInt(numVertices);
                    int v = random.nextInt(numVertices);
                    while(u == v) {
                        v = random.nextInt(numVertices);
                    }
                    int weightOfEdge = random.nextInt(91) + 10;
                    writer.write(u + " " + v + " " + weightOfEdge+"\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
