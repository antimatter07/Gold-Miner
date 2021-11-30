import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Controller implements ActionListener {

    private Board board;
    private GUI gui;
    private BoardPanel boardPanel;

    private OPBFS bfs;
    private boolean inBoard = false;
    private boolean findingPath = false;
    private Timer timer;
    private int fastDelay = 250;
    private int slowDelay = 1000;

    // default options
    private boolean isRandom = true;
    private boolean isSlow = true;

    private Node solutionNode;
    private String solution; // String of all moves made by Miner
    private int solutionLength; // total number of actions done by Miner
    private int solutionCount = 0; // determines current action displayed on the GUI
    
    

    Controller (GUI gui) {
        this.gui = gui;
        gui.setActionListeners(this);
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        if (!inBoard){ // if board is not yet created
            if (e.getActionCommand().equals("Enter")) {
                String input = gui.getInputText();
                if (checkInput(input)){ //if N is valid, display board
                    int intInput = Integer.parseInt(input);
                    board = new Board(intInput);
                    gui.getContentPane().removeAll();
                    gui.makeBoardPanel(board);
                    gui.repaint();
                    gui.revalidate();
                    boardPanel = gui.getBoardPanel();
                    boardPanel.setActionListeners(this);
                    inBoard = true;
                }
            }
        }
        else { // user is seeing the board
            if (!findingPath) { //user has not yet prompted Miner to find path
                if (e.getActionCommand().equals("Random")){
                    isRandom = true;
                }
                else if (e.getActionCommand().equals("Smart")){
                    isRandom = false;
                }
                else if (e.getActionCommand().equals("Slow")){
                    isSlow = true;
                }
                else if (e.getActionCommand().equals("Fast")){
                    isSlow = false;
                }
                else if (e.getActionCommand().equals("FIND GOLD")){
                    boardPanel.activateGo();
                    findingPath = true;
                    solutionNode = getSolution();
                    createTimer();
                    timer.start();
                }
                boardPanel.switchAI(isRandom);
                boardPanel.switchSpeed(isSlow);
            }
            else { // Miner is currently finding path
                if (solutionNode != null) {// Miner found path using chosen AI level
                    updateBoard();
                    boardPanel.repaint();
                }
                else { // Miner has NOT found a path
                    boardPanel.displayError();
                    boardPanel.resetGo();
                    findingPath = false;
                    timer.stop();
                }
            }
        }
    }

    // checks if the input value is between 8 and 64
    private boolean checkInput (String input) {
        try {
            int intValue = Integer.parseInt(input);
            if (intValue>=8 && intValue<=64)
                return true;
        } catch (NumberFormatException e) {
            gui.displayError();
            return false;
        }
        gui.displayError();
        return false;
    }

    private void updateBoard() {
        char currentMove = solution.charAt(solutionCount);
        boardPanel.updateCosts(currentMove);
        if (currentMove == 'R')
            board.getMiner().rotate();
        else if (currentMove == 'M')
            board.getMiner().move(board);
        
        solutionCount++;
        if (solutionCount == solutionLength) {
            boardPanel.updateCosts('G'); //Goal is Found
            boardPanel.resetGo(); // allow user to try another AI level
            findingPath = false;
            timer.stop();
        }
    }

    private void createTimer () {
        if (isSlow)
            timer = new Timer(slowDelay, this);
        else timer = new Timer(fastDelay, this);
    }

    private Node getSolution () {
        if (isRandom)
            return tryRandom();
        return null;
    }

    // checks if BFS can successfully find a path
    private Node tryRandom () {
        try {
            bfs = new OPBFS(board); // perform BFS
            solutionNode = bfs.BFS();
            solution = solutionNode.getActions();
            solutionLength = solution.length();
            return solutionNode;

        } catch (Exception e) {
            boardPanel.displayError();
            return null;
        }
    }
}
