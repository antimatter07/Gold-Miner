import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class GUI extends JFrame{
    public GUI() {
        super("Gold Miner");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //setSize(new Dimension(WIDTH, HEIGHT));
        makeStartPanel();
        add(startPanel);
       // setLocationRelativeTo(null); // opens at the center of screen
        pack();
        setResizable(false);
        setVisible(true);
    }

    public void setActionListeners (ActionListener listener) {
        enterButton.addActionListener(listener);
    }

    private void makeStartPanel () {
        startPanel = new JPanel();
        startPanel.setLayout(new BorderLayout());
        startPanel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        startPanel.setBackground(bgColor);

        // title
        JLabel welcome = new JLabel("Welcome to Gold Miner!");
        welcome.setFont(new Font ("Monospaced", Font.BOLD, 50));
        welcome.setPreferredSize(new Dimension(WIDTH, 120));
        welcome.setHorizontalAlignment(JLabel.CENTER);
        welcome.setVerticalAlignment(JLabel.CENTER);

        // image logo
        ImageIcon img = new ImageIcon(loadImage("images/miner_logo.png"));
        JLabel logo = new JLabel();
        logo.setIcon(img);
        logo.setHorizontalAlignment(JLabel.CENTER);

        // input (enter no. of squares)
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());
        inputPanel.setBackground(bgColor);
        JLabel enter = new JLabel(" > Enter number of squares [8-64]");
        enter.setFont(new Font ("Monospaced", Font.BOLD, 28));
        enter.setPreferredSize(new Dimension(WIDTH-logo.getWidth(), 190));
        enter.setVerticalAlignment(JLabel.BOTTOM);

        JPanel textPanel = new JPanel();
        textPanel.setBackground(bgColor);
        inputText = new JTextField();
        inputText.setText("");
        inputText.setColumns(10);
        inputText.setFont(new Font("Sanserif", Font.PLAIN, 20));
        textPanel.add(inputText);

        enterButton = new JButton("Enter");
        textPanel.add(enterButton);

        errorLabel = new JLabel();
        errorLabel.setFont(new Font ("Monospaced", Font.ITALIC, 16));
        errorLabel.setPreferredSize(new Dimension(WIDTH-logo.getWidth(), 320));
        errorLabel.setHorizontalAlignment(JLabel.CENTER);
        errorLabel.setVerticalAlignment(JLabel.NORTH);

        inputPanel.add(enter, BorderLayout.NORTH);
        inputPanel.add(textPanel, BorderLayout.CENTER);
        inputPanel.add(errorLabel, BorderLayout.SOUTH);

        startPanel.add(welcome, BorderLayout.NORTH);
        startPanel.add(logo, BorderLayout.WEST);
        startPanel.add(inputPanel, BorderLayout.CENTER);
        startPanel.setVisible(true);

    }

    public void makeBoardPanel (Board board) {
        boardPanel = new BoardPanel(board, WIDTH, HEIGHT);
        this.add(boardPanel);
    }

    private Image loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (Exception e){
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public String getInputText () {
        return inputText.getText();
    }

    public JPanel getBoardPanel () {
        return boardPanel;
    }

    public void displayError() {
        errorLabel.setText("Invalid input. Only enter integers between 8 and 64.");
    }

    private final int WIDTH = 1080;
    private final int HEIGHT = 720;

    private Color bgColor = new Color(208, 202, 181);

    private JPanel boardPanel;
    private JPanel startPanel;
    private JTextField inputText;
    private JButton enterButton;
    private JLabel errorLabel;
}
