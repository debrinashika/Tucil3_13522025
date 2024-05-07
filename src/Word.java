package src;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.Arrays;

public class Word {
    private static final Set<String> englishWords = new HashSet<>();

    static {
        // Load dictionary
        try (BufferedReader reader = new BufferedReader(new FileReader("src/dictionary.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // tambahkan kata pada string
                englishWords.add(line.trim().toLowerCase());
            }
        } catch (IOException e) {
            System.err.println("Error loading word list: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // method untuk memeriksa apakah sebuah kata merupakan bahasa inggris yang valid
    public static boolean isEnglishWord(String word) {
        return englishWords.contains(word.toLowerCase());
    }

    // mencari kata-kata tetangga dari sebuah node
    public static void searchNeighbour(Node node) {
        ArrayList<Character> alphabet = new ArrayList<>(Arrays.asList(
                    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 
                    'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 
                    'u', 'v', 'w', 'x', 'y', 'z'));

        // Iterasi melalui setiap karakter dalam kata
        for (int i = 0; i < node.getWord().length(); i++) {
            for (int j = 0; j < alphabet.size(); j++) {
                if(node.getWord().charAt(i)==Input.targetinput.charAt(i)){
                    continue;
                }
                StringBuilder sb = new StringBuilder(node.getWord());
                sb.setCharAt(i, alphabet.get(j));
                String newWord = sb.toString();
                // jika kata merupakan bahasa inggris yang valid
                if(isEnglishWord(newWord) && !newWord.equals(node.getWord())){
                    Node newNode = new Node(newWord);
                    node.addNode(newNode);
                }
            }
        }
    }

    // method untuk mendapat heuristic cost dengan menghitung jumlah kata yang berbeda
    public static int diffletter(String currWord){
        int diff = 0;
        for (int i = 0;i<currWord.length();i++){
            if(Input.targetinput.charAt(i)!=currWord.charAt(i)){
                diff += 1;
            }
            
        }
        return diff;
    }
}


