package src;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BFS {

    static Set<String> visited = new HashSet<>();

    // overide kelas comparator untuk mengurutkan array berdasarkan cost path
    public static class PathsComparator implements Comparator<Paths> {
        @Override
        public int compare(Paths p1, Paths p2) {
            return Integer.compare(p1.getCost(), p2.getCost());
        }
    }

    // method untuk mencari path menggunakan algoritma BFS
    public static ArrayList<String> solveBFS() {

        // hapus list visited setiap memulai baru
        visited.clear();

        // Executor agar program bisa berjalan secara pararel
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()); 

        // menyiapkan priority queue untuk menyimpan node yang sudah diexpand
        PriorityQueue<Paths> pq = new PriorityQueue<>(new PathsComparator());
        CompletionService<Void> completionService = new ExecutorCompletionService<>(executor);
        Map<String, Integer> minCostToNode = new HashMap<>();

        // menyimpan start path ke priority queue
        Node startNode = new Node(Input.startinput);
        Paths startPath = new Paths(startNode,new ArrayList<>(), 0);
        pq.add(startPath);
        minCostToNode.put(startNode.getWord(), 0);

        // melakukan iterasi selama prioqueue belum kosong
        while (!pq.isEmpty()) {

            // dequque prioqueue dan jadikan currentpath
            Paths currentPath = pq.poll();
            Node currentNode = currentPath.getCurrNode();

            // jika node saat ini adalah goal maka return hasil
            if (currentNode.getWord().equals(Input.targetinput)) {
                System.out.println("Astar FOUND!!!");
                System.out.println("End Node: " + currentNode.getWord());
                System.out.println("Total Cost: " + currentPath.getCost());
                System.out.println("length: " + currentPath.getpath().size());
                System.out.println("Path: " + currentPath.getpath());
                currentPath.getpath().add(currentNode.getWord());
                executor.shutdown(); 
                return currentPath.getpath();
            }

            // jika bukan, expand node yang belum dikunjungi
            if (!visited.contains(currentNode.getWord())) {
                visited.add(currentNode.getWord());

                currentNode.expandNode();
                List<Callable<Void>> tasks = new ArrayList<>();
                // tambahkan node tetangga yang belum pernah dikunjungi pada prioqueue
                for (Node neighbor : currentNode.getNeighbour()) {
                    tasks.add(() -> {
                        // hitung heuristic cost yang ada pada masing masing node 
                        int newCost = neighbor.getcostBFS();
                        synchronized (minCostToNode) {
                            if (!minCostToNode.containsKey(neighbor.getWord()) || newCost < minCostToNode.get(neighbor.getWord())) {
                                minCostToNode.put(neighbor.getWord(), newCost);
                                ArrayList<String> newRoute = new ArrayList<>(currentPath.getpath());
                                newRoute.add(currentNode.getWord());
                                Paths newPath = new Paths(neighbor, newRoute,newCost);
                                pq.add(newPath); // tambah path ke prioqueue
                            }
                        }
                        return null;
                    });
                }

                for (Callable<Void> task : tasks) {
                    completionService.submit(task);
                }

                // tunggu semua task selesai sebelum lanjut ke iterasi selanjutnya
                try {
                    for (int i = 0; i < tasks.size(); i++) {
                        completionService.take().get();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        executor.shutdown();
        return new ArrayList<String>();
    }
}
