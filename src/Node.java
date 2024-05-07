package src;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Node {
    private String word;
    private ArrayList<Node> neighbour;
    private int costUCS;
    private int costBFS;
    private boolean visited;

    // konstruktor Node
    public Node(String word){
        this.word = word;
        this.costBFS = 0;
        this.costUCS = 1;
        this.visited = false;
        this.neighbour = new ArrayList<>();
    }

    // method untuk mendapat nilai word
    public String getWord(){
        return this.word;
    }

    // method untuk mendapat list tetangga node
    public ArrayList<Node> getNeighbour(){
        return this.neighbour;
    }

    // method untuk mendapat cost ucs
    public int getcostUCS(){
        return this.costUCS;
    }

    // method untuk mendapat cost bfs
    public int getcostBFS(){
        setCostBFS();
        return this.costBFS;
    }

    // method untuk mencari node tetangga dengan cost optimal
    public Node getOptimalNeighbour(){
        Node opt = null;
        int min = 100;
        for (Node i : neighbour){
            if(i.getcostBFS()<min){
                opt = i;
            }
        }
        return opt;
    }


    // method untuk mengeset nilai cost heuristic bfs
    public void setCostBFS(){
        int cost = Word.diffletter(this.getWord());
        this.costBFS = cost;
    }

    // method untuk mengeset node telah dikunjungi
    public void setVisited(){
        this.visited = true;
    }

    // method untuk mengetahui apakah node telah dikunjungi
    public boolean isVisited(){
        return visited;
    }

    // method untuk mencetak semua tetangga node
    public void printNeighbour(){
        System.out.println("neighbour!!");
        for (Node i : neighbour){
            System.out.println(i.getWord());
        }
    }
    
    // method untuk mengexpand node
    public void expandNode(){
        Word.searchNeighbour(this);
    }

    // method untuk menambah node tetangga
    public void addNode(Node node){
        neighbour.add(node);
        // Sort secara alphabet
        Collections.sort(this.neighbour, new Comparator<Node>() {
            @Override
            public int compare(Node node1, Node node2) {
                return node1.getWord().compareTo(node2.getWord());
            }
        });
    }
    

}
