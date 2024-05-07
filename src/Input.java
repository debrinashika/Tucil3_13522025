package src;
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
                System.out.println(start + target);
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

        System.out.println(Input.startinput);
        
        Node startNode = new Node(Input.startinput);

        startNode.expandNode();
        startNode.printNeighbour();
        BFS.solveBFS();
        UCS.solveUCS();
        Astar.solveAstar();
    }
}
