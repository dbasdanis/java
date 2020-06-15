/* Antoniou Theodoros 2208
 * Basdanis Dionisis 2166
 * CE325 - HW3  Sudoku
 */
package ce325.hw3;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.border.Border;

public class Sudoku extends JFrame implements ActionListener{
    public static final int WIDTH = 420;
    public static final int HEIGHT = 520;
    URL pageURL;
    JTextField click;
    int clickRow,clickCol;
    int correctFields = 0;
    JTextField[][] array;
    JButton one, two, three, four, five, six, seven, eight, nine, eraser, undo, solution;
    JCheckBox verify;
    Color initialColor;
    ArrayList<String> undoArray = new ArrayList<>();
    boolean verify_bool = false;
    
    String[][] solutionArray = new String[9][9];
    int[][] initialArray = new int[9][9];
    
    public static void main(String args[]){
        Sudoku gui = new Sudoku();
        gui.setVisible(true);
    }
    
    public Sudoku(){
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JMenu newGame = new JMenu("New Game");
        JMenuItem easy = new JMenuItem("Easy");
        easy.addActionListener(this);
        newGame.add(easy);
        JMenuItem intMed = new JMenuItem("Intermediate");
        intMed.addActionListener(this);
        newGame.add(intMed);
        JMenuItem expert = new JMenuItem("Expert");
        expert.addActionListener(this);
        newGame.add(expert);
        
        JMenuBar bar = new JMenuBar();
        bar.add(newGame);
        setJMenuBar(bar);
        
        Border empty = BorderFactory.createEmptyBorder(0,70,70,70);
        JPanel up = new JPanel();
        up.setBorder(empty);
        add(up,BorderLayout.NORTH);
        
        array = new JTextField[9][9];
        JPanel []p2 = new JPanel[9];
        JPanel p1 =  new JPanel(new GridLayout(3,3));
        for (int i=0; i<9; i++){
            p2[i] = new JPanel(new GridLayout(3,3));
        }
        int k=-1;
        int tmp=0;
        for(int m=0;m<3;m++){
            for(int i=(0+tmp); i<(3+tmp); i++){
                for(int j=0; j<9; j++){
                    array[i][j] = new JTextField(1);
                    array[i][j].setBackground(Color.WHITE);
                    array[i][j].setEditable(false);
                    array[i][j].addMouseListener(new MouseAdapter(){
                        public void mouseClicked(MouseEvent e){
                            
                            click = (JTextField) e.getComponent();
                            initialColor = click.getBackground(); 
                            
                            for(int i=0;i<9;i++){
                                for(int j=0;j<9;j++){
                                    
                                    if(e.getSource().equals(array[i][j])){
                                        clickRow = i;
                                        clickCol = j;
                                    }                                      
                                    
                                    if(click.getText().equals(array[i][j].getText()) && !array[i][j].getText().equals("") ){
                                        array[i][j].setBackground(new Color(255,255,200));
                                    }
                                    else if(initialArray[i][j] == 1){
                                        array[i][j].setBackground(Color.GRAY);
                                    }
                                    else if(!array[i][j].getBackground().equals(Color.BLUE))
                                        array[i][j].setBackground(Color.WHITE);     
                                }
                            }
                        }
                    });
                    if(j%3==0){
                        k++;
                        if(k==(3+tmp)){
                            k=tmp;
                        }
                    }
                    p2[k].add(array[i][j]);
                }
            }
            tmp = tmp + 3;
        }
        
        for(int i=0; i<9; i++ ){
            p1.add(p2[i]);
        }
        
        add(p1,BorderLayout.CENTER);
        
        
        one = new JButton("1");
        one.addActionListener(this);
        one.setActionCommand("1");
        two = new JButton("2");
        two.addActionListener(this);
        two.setActionCommand("2");
        three = new JButton("3");
        three.addActionListener(this);
        three.setActionCommand("3");
        four = new JButton("4");
        four.addActionListener(this);
        four.setActionCommand("4");
        five = new JButton("5");
        five.addActionListener(this);
        five.setActionCommand("5");
        six = new JButton("6");
        six.addActionListener(this);
        six.setActionCommand("6");
        seven = new JButton("7");
        seven.addActionListener(this);
        seven.setActionCommand("7");
        eight = new JButton("8");
        eight.addActionListener(this);
        eight.setActionCommand("8");
        nine = new JButton("9");
        nine.addActionListener(this);
        nine.setActionCommand("9");
        eraser = new JButton();
        eraser.addActionListener(this);
        undo = new JButton();
        undo.addActionListener(this);
        solution = new JButton();
        solution.addActionListener(this);
        try {
            Image img = ImageIO.read(getClass().getResource("eraser.png"));
            eraser.setIcon(new ImageIcon(img));
            Image img1 = ImageIO.read(getClass().getResource("undo.png"));
            undo.setIcon(new ImageIcon(img1));
            Image img2 = ImageIO.read(getClass().getResource("cube.png"));
            solution.setIcon(new ImageIcon(img2));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        verify = new JCheckBox("Verify against solution");
        verify.addActionListener(this);
        verify.setActionCommand("verify");
       
        
       
        JPanel panelButton1 = new JPanel();
        panelButton1.add(one);
        panelButton1.add(two);
        panelButton1.add(three);
        panelButton1.add(four);
        panelButton1.add(five);
        panelButton1.add(six);
        panelButton1.add(seven);
        panelButton1.add(eight);
        
        JPanel panelButton2 = new JPanel();
        panelButton2.add(nine);
        panelButton2.add(eraser);
        panelButton2.add(undo);
        panelButton2.add(verify);
        panelButton2.add(solution);
        
        
        JPanel panelButton = new JPanel(new GridLayout(3,1));
        Border empty2 = BorderFactory.createEmptyBorder(0,5,5,5);
        JPanel down = new JPanel();
        down.setBorder(empty2);
        panelButton.add(down);
        panelButton.add(panelButton1);
        panelButton.add(panelButton2);
        
        add(panelButton,BorderLayout.SOUTH);
        
    }
    
    public void actionPerformed(ActionEvent e){
        String inString;
        String rem;
        String pos;
        
        if(e.getActionCommand().equals("Easy") || e.getActionCommand().equals("Intermediate") || e.getActionCommand().equals("Expert")){
        
            try{
                pageURL = new URL("http://gthanos.inf.uth.gr/~gthanos/sudoku/exec.php?difficulty="+e.getActionCommand().toLowerCase());
                URLConnection con = pageURL.openConnection();
                BufferedReader urlread = new BufferedReader(new InputStreamReader(con.getInputStream())); 
                    
                for(int i=0;i<9;i++){
                    inString = urlread.readLine();
                    //System.out.println(inString);
                    for(int j=0;j<9;j++){
                        array[i][j].setText("");
                        array[i][j].setBackground(Color.WHITE);
                        char text = inString.charAt(j);
                        if(String.valueOf(text).equals(".")){
                            initialArray[i][j] = 0;
                            solutionArray[i][j] = ".";
                            continue;
                        }
                        correctFields++;
                        array[i][j].setText(String.valueOf(text));
                        array[i][j].setBackground(Color.GRAY);
                        solutionArray[i][j] = String.valueOf(text);
                        initialArray[i][j] = 1;
                        
                    }
                }
                one.setEnabled(true);
                two.setEnabled(true);
                three.setEnabled(true);
                four.setEnabled(true);
                five.setEnabled(true);
                six.setEnabled(true);
                seven.setEnabled(true);
                eight.setEnabled(true);
                nine.setEnabled(true);
                eraser.setEnabled(true);
                undo.setEnabled(true);
                verify.setEnabled(true);
                solution.setEnabled(true);
                solveSudoku();
//                for(int i=0;i<9;i++){
//                    for(int j=0;j<9;j++){
//                        System.out.print(solutionArray[i][j]+" ");
//                    }
//                    System.out.println();
//                }
                
            }catch(MalformedURLException ex){
            } catch (IOException ex) {
                Logger.getLogger(Sudoku.class.getName()).log(Level.SEVERE, null, ex);
            }    
        }
        else if(e.getSource() == one || e.getSource() == two ||e.getSource() == three ||e.getSource() == four ||e.getSource() == five ||e.getSource() == six ||e.getSource() == seven ||e.getSource() == eight ||e.getSource() == nine){
            boolean problem = false;
            
            for(int i=0; i<9; i++){
                if(i == clickCol)
                    continue;
                if(array[clickRow][i].getText().equals(e.getActionCommand())){
                    array[clickRow][i].setBackground(Color.RED);
                    problem = true;
                    break;
                }
            }
            
            if(problem == false){
                for(int i=0; i<9; i++){
                    if(i == clickRow)
                        continue;
                    if(array[i][clickCol].getText().equals(e.getActionCommand())){
                        array[i][clickCol].setBackground(Color.RED);
                        problem = true;
                        break;
                    }
                }   
            }
            
            if(problem == false){
                int r = clickRow - clickRow%3;
                int c = clickCol - clickCol%3;
        
                for(int i = r ; i< r+3 ; i++){
                    for(int j = c; j < c+3 ; j++){ 
                        if(array[i][j].getText().equals(e.getActionCommand())){
                            array[i][j].setBackground(Color.RED);
                            problem = true;
                            break;
                        }
                    }
                }  
            }
                                    
            if(click.getText().equals("") && problem == false){
                pos = String.format("%d%d",clickRow,clickCol);
                undoArray.add(pos);
                if(e.getActionCommand().equals(String.valueOf(solutionArray[clickRow][clickCol])) && !array[clickRow][clickCol].getText().equals(String.valueOf(solutionArray[clickRow][clickCol])))
                    correctFields++;
                click.setText(e.getActionCommand());
                if(verify_bool == true){
                    if(e.getActionCommand().equals(String.valueOf(solutionArray[clickRow][clickCol])))
                            array[clickRow][clickCol].setBackground(Color.WHITE);
                    else
                        array[clickRow][clickCol].setBackground(Color.BLUE);
                }
                
                if(correctFields == 81){
                    one.setEnabled(false);
                    two.setEnabled(false);
                    three.setEnabled(false);
                    four.setEnabled(false);
                    five.setEnabled(false);
                    six.setEnabled(false);
                    seven.setEnabled(false);
                    eight.setEnabled(false);
                    nine.setEnabled(false);
                    eraser.setEnabled(false);
                    undo.setEnabled(false);
                    verify.setEnabled(false);
                    solution.setEnabled(false);
                    JOptionPane.showMessageDialog(null,"\t\tCongratulations!!!\n You are the best!!!");
                }
                    
            }
        }
        else if(e.getSource() == eraser){
            if(click.getText().equals(String.valueOf(solutionArray[clickRow][clickCol])) && initialColor.equals(Color.GRAY) == false)
                correctFields--;
            if(initialColor.equals(Color.GRAY) == false){
                click.setText("");
                click.setBackground(Color.WHITE);
            }           
        }
        else if(e.getSource() == undo){
            rem = undoArray.get(undoArray.size()-1);
            undoArray.remove(undoArray.size()-1);
            System.out.println("rem : %s"+rem);
            char row = rem.charAt(0);
            char col = rem.charAt(1);
            
            array[Character.getNumericValue(row)][Character.getNumericValue(col)].setText("");
        }
        else if(e.getActionCommand().equals("verify")){
            if(verify_bool == true){
                verify_bool = false;
                for(int i=0; i<9; i++){
                    for(int j =0; j<9; j++){
                        if(array[i][j].getBackground() == Color.BLUE)
                            array[i][j].setBackground(Color.WHITE);
                    }
                }
            }
            else{
                verify_bool = true;
                for(int i=0; i<9; i++){
                    for(int j =0; j<9; j++){
                        if(!array[i][j].getText().equals("")  && !array[i][j].getText().equals(String.valueOf(solutionArray[i][j])))
                            array[i][j].setBackground(Color.BLUE);
                    }
                }
            }
        }  
        else if(e.getSource() == solution){
            for(int i=0; i<9; i++){
                for(int j =0; j<9; j++){
                    array[i][j].setText(String.valueOf(solutionArray[i][j]));
                }
            }
            one.setEnabled(false);
            two.setEnabled(false);
            three.setEnabled(false);
            four.setEnabled(false);
            five.setEnabled(false);
            six.setEnabled(false);
            seven.setEnabled(false);
            eight.setEnabled(false);
            nine.setEnabled(false);
            eraser.setEnabled(false);
            undo.setEnabled(false);
            verify.setEnabled(false);
            solution.setEnabled(false);
        }
    }
    
    private boolean containsInRow(int row,int number){
        for(int i=0;i<9;i++){
            if(solutionArray[row][i].equals(String.valueOf(number))){
                return true;
            }
        }
        return false;
    }
  
    private boolean containsInCol(int col,int number){
        for(int i=0;i<9;i++){
            if(solutionArray[i][col].equals(String.valueOf(number))){
                return true;
            }
        }
        return false;
    }
  
  
    private boolean containsInBox(int row, int col,int number){
        int r = row - row%3;
        int c = col - col%3;
        
        for(int i = r ; i< r+3 ; i++){
            for(int j = c; j < c+3 ; j++){ 
                if(solutionArray[i][j].equals(String.valueOf(number))){
                    return true;
                }
            }

        }
        return false;
    }
    
    private boolean isAllowed(int row, int col,int number){
        return !(containsInRow(row, number) || containsInCol(col, number) || containsInBox(row, col, number));
    }
    public boolean solveSudoku(){
        for(int row=0;row<9;row++){
            for(int col=0;col<9;col++){
                if(solutionArray[row][col].equals(".")){
                    for(int number=1;number<=9;number++){
                        if(isAllowed(row, col, number)){
                            solutionArray[row][col] = String.valueOf(number);
                            if(solveSudoku()){
                                return true;
                            }
                            else{
                                solutionArray[row][col] = ".";
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }
}


