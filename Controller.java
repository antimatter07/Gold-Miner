import javax.swing.*;
import java.awt.event.*;

public class Controller implements ActionListener {

    private Board board;
    private GUI gui;
    private JPanel boardPanel;

    Controller (GUI gui) {
        this.gui = gui;
        gui.setActionListeners(this);
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        if (e.getActionCommand().equals("Enter")) {
            String input = gui.getInputText();
            if (checkInput(input) == true){ //if N is valid, display board
                int intInput = Integer.parseInt(input);
                board = new Board(intInput);
                gui.getContentPane().removeAll();
                gui.makeBoardPanel(board);
                gui.repaint();
                gui.revalidate();

                boardPanel = gui.getBoardPanel();
            }
        }
    }

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
}
