package src;
import java.util.ArrayList;
import java.util.Scanner;

public class Input {
    
    Scanner sc = new Scanner(System.in);
    public static String startinput;
    public static String targetinput;

    public void askInput(){
        System.out.println("Welcome to World Ladder Solver :3");
        System.out.println("Please input the start and target word:");
        
 
        while(true){
            String start = checkWord("Start "); 
            String target = checkWord("Target"); 
            if(checkLength(start, target)){
                startinput = start;
                targetinput = target; 
                break;
            }
            System.out.println("Please input the same length of word");
        }
    }

    public String checkWord(String input){
        System.out.print(input + " Word: ");
        while(true){
            String word = sc.nextLine(); 
            if(Word.isEnglishWord(word)){
                return word;
            }
            System.out.println("Word is not valid! Please input another word");
            System.out.print(input + " Word: ");
        }
    }

    public boolean checkLength(String input1, String input2){
        return (input1.length() == input2.length());
    }

    public static void main(String[] args) {
        Input it = new Input();
        it.askInput();

        Runtime runtime = Runtime.getRuntime();
        long totalMemory = runtime.totalMemory();

        
        Node startNode = new Node(Input.startinput);

        startNode.expandNode();
        System.out.println("Choose Algorithm:");
        System.out.println("1. Uniform Cost Search");
        System.out.println("2. Greedy Best First Search");
        System.out.println("3. A*");
        Scanner sc = new Scanner(System.in);

        int num = 0; 
        while(true){
            System.out.print("Choose Number: ");
            num = sc.nextInt(); 
            if(num>0 && num<4){
                break;
            }
            System.out.println("Number is not valid! Please input number");
        }

        ArrayList<String> path = new ArrayList<String>();
        int visited = 0;
        long startTime = System.currentTimeMillis();

        if(num==1){
            path = UCS.solveUCS();
        } else if (num==2){
            path = BFS.solveBFS();
        } else if (num==3){
            path = Astar.solveAstar();
        }

        long endTime = System.currentTimeMillis();

        long freeMemory = runtime.freeMemory();
        long usedMemory = totalMemory - freeMemory;
        if(path.size()>0){
            System.out.println("Path: " + String.join(" -> ", path) + "\n");
        }
        else{
            System.out.println("No Solution");
        }
        System.out.println("Visited Nodes: " + visited);
        System.out.println("Execution Time (ms): " + (endTime - startTime));
        System.out.println("Total Memory: " + totalMemory / (1024 * 1024) + " MB");
        System.out.println("Free Memory: " + freeMemory / (1024 * 1024) + " MB");
        System.out.println("Used Memory: " + usedMemory / (1024 * 1024) + " MB");
    }
}
