package src;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GUI extends JFrame {
    private JTextField startWordField, endWordField;
    private JComboBox<String> algorithmComboBox;
    private JButton findPathButton;
    private JTextArea resultArea;

    public GUI() {
        setTitle("Word Ladder Solver");
        setSize(500, 450); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false); 
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(255, 220, 220)); 

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(255, 105, 180)); 
        JLabel titleLabel = new JLabel("Word Ladder Solver");
        titleLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        add(titlePanel, BorderLayout.NORTH);

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 1, 30, 1));
        inputPanel.setBorder(new EmptyBorder(20, 80, 10, 80));
        inputPanel.setBackground(new Color(255, 240, 240)); 

        JLabel startLabel = new JLabel("Start Word:");
        startLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16)); 
        inputPanel.add(startLabel);

        startWordField = new JTextField();
        startWordField.setPreferredSize(new Dimension(500, 100)); 
        inputPanel.add(startWordField);

        JLabel endLabel = new JLabel("End Word:");
        endLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        inputPanel.add(endLabel);

        endWordField = new JTextField();
        endWordField.setPreferredSize(new Dimension(500, 100)); 
        inputPanel.add(endWordField);

        JLabel algo = new JLabel("Algorithm :");
        algo.setFont(new Font("Comic Sans MS", Font.BOLD, 16)); 
        inputPanel.add(algo);

        String[] algorithms = {"UCS", "Greedy Best First Search", "A*"};
        algorithmComboBox = new JComboBox<>(algorithms);
        algorithmComboBox.setFont(new Font("Comic Sans MS", Font.PLAIN, 14)); 
        algorithmComboBox.setBackground(new Color(255, 182, 193)); 
        algorithmComboBox.setPreferredSize(new Dimension(500, 30));
        inputPanel.add(algorithmComboBox);

        add(inputPanel, BorderLayout.CENTER);

        // Result Panel
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BorderLayout());
        resultPanel.setBorder(new EmptyBorder(10, 50, 20, 40));
        resultPanel.setBackground(new Color(255, 240, 240)); 

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Comic Sans MS", Font.PLAIN, 14)); 
        resultArea.setBackground(new Color(255, 255, 255)); 
        resultArea.setWrapStyleWord(true);
        resultArea.setLineWrap(true);

        
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setPreferredSize(new Dimension(140, 140)); 

        resultPanel.add(scrollPane, BorderLayout.CENTER);

        // Find Path Button
        findPathButton = new JButton("Find Path");
        findPathButton.setFont(new Font("Comic Sans MS", Font.BOLD, 18)); 
        findPathButton.setForeground(Color.WHITE);
        findPathButton.setBackground(new Color(255, 105, 180)); 
        findPathButton.setFocusPainted(false);
        findPathButton.setBorderPainted(false);
        findPathButton.setOpaque(true);
        findPathButton.setPreferredSize(new Dimension(200, 50));
        findPathButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                findPath();
            }
        });

        // Add hover effect on button
        findPathButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                findPathButton.setBackground(new Color(255, 182, 193)); 
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                findPathButton.setBackground(new Color(255, 105, 180)); 
            }
        });

        resultPanel.add(findPathButton, BorderLayout.SOUTH);

        add(resultPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null); 
        setVisible(true);
    }

    // method untuk mencari path berdasarkan masukan user
    private void findPath() {
        String startWord = startWordField.getText().trim();
        String endWord = endWordField.getText().trim();
        String algorithm = (String) algorithmComboBox.getSelectedItem();

        if (!Word.isEnglishWord(startWord) || !Word.isEnglishWord(endWord)) {
            JOptionPane.showMessageDialog(this, "Please enter valid English words.");
            return;
        }
    
        if (startWord.length() != endWord.length()) {
            JOptionPane.showMessageDialog(this, "Start and end words must have the same length.");
            return;
        }

        Input.startinput = startWord.toLowerCase();
        Input.targetinput = endWord.toLowerCase();

        ArrayList<String> path = new ArrayList<String>();
        int visited = 0;
        long startTime = System.currentTimeMillis();

        if(algorithm=="UCS"){
            path = UCS.solveUCS();
            visited = UCS.visited.size();
        }else if(algorithm=="Greedy Best First Search"){
            path = BFS.solveBFS();
            visited = BFS.visited.size();
        }else{
            path = Astar.solveAstar();
            visited = Astar.visited.size();
        }

        long endTime = System.currentTimeMillis();

        if(path.size()>0){
            resultArea.setText("Path: " + String.join(" -> ", path) + "\n" +
            "Visited Nodes: " + visited + "\n"+
            "Execution Time (ms): " + (endTime - startTime));
        } else{
            if(algorithm=="Greedy Best First Search"){
                resultArea.setText("No Solution\n" +
                "BFS Stuck in local minima\n" +
                "Visited Nodes: " + visited + "\n"+
                "Execution Time (ms): " + (endTime - startTime));
            }else{
                resultArea.setText("No Solution\n" +
                "Visited Nodes: " + visited + "\n"+
                "Execution Time (ms): " + (endTime - startTime));
            }
        }
       
    }

    public static void main(String[] args) {
        // Use invokeLater to safely create GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Set Nimbus look and feel for modern UI (optional)
                    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new GUI();
            }
        });
    }
}
