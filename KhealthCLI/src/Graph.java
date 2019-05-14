import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Graph {

    private int root;
    private HashMap<Integer, Vertex> vertices;
    private HashMap<Integer, HashMap<String, Integer>> edges;

    public Graph(int root, HashMap<Integer,Vertex> vertices, HashMap<Integer, HashMap<String,Integer>> edges){
        this.root = root;
        this.vertices = vertices;
        this.edges = edges;
    }

    public HashMap<Integer, HashMap<String,Integer>> getEdges() {
        return edges;
    }

    public HashMap<Integer, Vertex> getVertices() {
        return vertices;
    }

    public int getRoot() {
        return root;
    }

    public static void main(String[] args) {

        try (JsonReader reader = new JsonReader(new FileReader("src/graph.json"))) {

            Gson g = new Gson();

            Graph graph = g.fromJson(reader, Graph.class);

            Scanner scanner = new Scanner(System.in);

            int vertex = graph.getRoot();

            while(vertex != 0 && graph.getEdges().get(vertex).size() > 0) {

                System.out.println(graph.getVertices().get(vertex).getQuestion());

                if(graph.getVertices().get(vertex).isMulti()) {

                    System.out.println("(Please type all that apply from the following with commas (i.e: A,B,C:))");
                    System.out.println(graph.getVertices().get(vertex).getOptions());

                }

                String answer = scanner.nextLine();

                answer = String.join("", answer.split(","));

                vertex = graph.getEdges().get(vertex).getOrDefault(answer.trim(), 0);
            }

            System.out.println("Session is over! Thank you");

            scanner.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
