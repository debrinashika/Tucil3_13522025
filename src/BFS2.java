package src;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class BFS2 {

    static Set<String> visited = new HashSet<>();

    // method untuk mencari path menggunakan algoritma BFS
    public static ArrayList<String> solveBFS2() {
        // hapus list visited setiap memulai baru
        visited.clear();

        // menyimpan start path 
        Node startNode = new Node(Input.startinput);
        Paths startPath = new Paths(startNode,new ArrayList<>(), 0);
        ArrayList<Node> childNode = new ArrayList<>();

        // melakukan iterasi selama belum ditemukan goal atau hingga tidak ada solusi
        Paths currentPath = startPath;
        Node currentNode = currentPath.getCurrNode();
        while (childNode.size()!=0) {

            // jika node saat ini adalah goal maka return hasil
            if (currentNode.getWord().equals(Input.targetinput)) {
                System.out.println("BFS FOUND!!!");
                System.out.println("End Node: " + currentNode.getWord());
                System.out.println("Total Cost: " + currentPath.getCost());
                System.out.println("length: " + currentPath.getpath().size());
                System.out.println("Path: " + currentPath.getpath());
                currentPath.getpath().add(currentNode.getWord());
                return currentPath.getpath();
            }

            // jika bukan, expand node yang belum dikunjungi
            if (!visited.contains(currentNode.getWord())) {
                visited.add(currentNode.getWord());
                currentNode.expandNode();
                for (Node neighbor : currentNode.getNeighbour()) {
                    if(!visited.contains(neighbor.getWord())){
                        childNode.add(neighbor);
                    }
                }
                
                // sort node
                Collections.sort(childNode, new Comparator<Node>() {
                    @Override
                    public int compare(Node node1, Node node2) {
                        return Integer.compare(node1.getcostBFS(), node2.getcostBFS());
                    }
                });


                // pindahkan currentnode ke node anak dengan cost terendah
                ArrayList<String> newRoute = new ArrayList<>(currentPath.getpath());
                newRoute.add(currentNode.getWord());
                Paths newPath = new Paths(childNode.get(0), newRoute,childNode.get(0).getcostBFS());
                currentPath = newPath;
                currentNode = newPath.getCurrNode();
                childNode.remove(0);
               
            }
        }
        return new ArrayList<String>();
    }
}
