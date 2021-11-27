import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Controller implements ActionListener {

    private Board board;
    private GUI gui;
    private BoardPanel boardPanel;

    private TryBFS bfs;
    private boolean findingPath = false;
    private Timer timer;
    private final int DELAY = 1000;

    private Node currentNode;
    private String solution;
    private int solutionLength;
    private int solutionCount = 0;

    Controller (GUI gui) {
        timer = new Timer(DELAY, this);
        this.gui = gui;
        gui.setActionListeners(this);
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        if (!findingPath){ // if board is not yet created
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
                    findingPath = true;

                    bfs = new TryBFS(board); // perform BFS
                    currentNode = bfs.BFS();
                    solution = currentNode.getActions();
                    solutionLength = solution.length();
                    timer.start();
                }
            }
        }
        else {
            updateBoard();
            boardPanel.repaint();
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
            timer.stop();
        }
    }
}
