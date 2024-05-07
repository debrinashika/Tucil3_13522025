package src;
import java.util.ArrayList;

public class Paths {
    
    private Node currNode;
    private ArrayList<String> path;
    private int totalcost;

    // konstruktor
    public Paths(Node node, ArrayList<String> path, int totalcost){
        this.currNode = node;
        this.path = path;
        this.totalcost = totalcost;
    }

    // method untuk mendapat current node
    public Node getCurrNode(){
        return currNode;
    }

    // method untuk mendapat path
    public ArrayList<String> getpath(){
        return path;
    }

    // method untuk mendapat cost
    public int getCost(){
        return totalcost;
    }

}
