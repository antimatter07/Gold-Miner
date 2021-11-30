import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BoardPanel extends JPanel {
    BoardPanel(Board board, int width, int height) {
        super();
        setLayout(new FlowLayout(0,0,0));
        setSize(width, height);
        WIDTH = width;
        HEIGHT = height;
        this.board = board;
        miner = board.getMiner();
        N = board.getSize();
        squareSize = HEIGHT/N;
        minerIcons = new Image[4]; // N E S W
        boardLabelArray = new JLabel[N][N];
        currentMove = "is THINKING...";
        makeMinerIcons();
        drawBoard();
        makeDashPanel();
    }

    public void setActionListeners(ActionListener listener){
        randomButton.addActionListener(listener);
        smartButton.addActionListener(listener);
        slowButton.addActionListener(listener);
        fastButton.addActionListener(listener);
        goButton.addActionListener(listener);
    }

    public void removeActionListeners(ActionListener listener){
        randomButton.removeActionListener(listener);
        smartButton.removeActionListener(listener);
        slowButton.removeActionListener(listener);
        fastButton.removeActionListener(listener);
        goButton.removeActionListener(listener);
    }

    // creates the squares of the board
    private void drawBoard() {
        boardGrid = new JPanel();
        boardGrid.setLayout(new GridLayout(N, N));
        boardGrid.setBackground(Color.DARK_GRAY);
        boardGrid.setMinimumSize(new Dimension(N, N));
        boardGrid.setPreferredSize(new Dimension (HEIGHT, HEIGHT));

        for (int i = 0; i<N; i++) {
            for (int j = 0; j<N; j++){
                boardLabelArray[i][j] = new JLabel();
                boardLabelArray[i][j].setOpaque(true);
                if ((i%2 == 0 && j%2 == 0) || (i%2 != 0 && j%2 != 0))
                    boardLabelArray[i][j].setBackground(lightBrown);
                else boardLabelArray[i][j].setBackground(darkBrown);

                // add object symbols
                Unit unit = board.getUnit(i, j);
                UnitType unitType = unit.getUnitType();
                if (unitType != UnitType.EMPTY) {
                    boardLabelArray[i][j].setText(Character.toString(unit.getSymbol()));
                    boardLabelArray[i][j].setFont(new Font("Monospaced", Font.BOLD, squareSize));
                    boardLabelArray[i][j].setHorizontalAlignment(JLabel.CENTER);
                    boardLabelArray[i][j].setVerticalAlignment(JLabel.CENTER);
                    switch (unitType) {
                        case BEACON:
                            boardLabelArray[i][j].setBackground(beaconColor);
                            break;
                        case GOLD:
                            boardLabelArray[i][j].setBackground(goldColor);
                            break;
                        case PIT:
                            boardLabelArray[i][j].setBackground(Color.GRAY);
                            break;
                    }
                }
                boardGrid.add(boardLabelArray[i][j]);
            }
        }
        currentLabel = boardLabelArray[0][0];
        displayMiner();
        this.add(boardGrid);
    }

    // creates the dashboard
    private void makeDashPanel () {
        dashPanel = new JPanel();
        dashPanel.setLayout(new GridLayout(2,1));
        dashPanel.setPreferredSize(new Dimension(WIDTH-HEIGHT, HEIGHT));
        dashPanel.setBackground(infoColor);

        // infoPanel : displays counters and current position of miner
        JPanel labels = new JPanel();
        labels.setLayout(new GridLayout(7,1));
        labels.setBackground(infoColor);
        minerPosLabel = new JLabel();
        formatText(minerPosLabel);
        minerDirectionLabel = new JLabel();
        formatText(minerDirectionLabel);
        moveLabel = new JLabel();
        formatText(moveLabel);
        scanCountLabel = new JLabel();
        formatText(scanCountLabel);
        moveCountLabel = new JLabel();
        formatText(moveCountLabel);
        rotateCountLabel = new JLabel();
        formatText(rotateCountLabel);
        pathCostLabel = new JLabel();
        formatText(pathCostLabel);
        updateInfoPanel();

        labels.add(moveLabel);
        labels.add(minerPosLabel);
        labels.add(minerDirectionLabel);
        labels.add(scanCountLabel);
        labels.add(moveCountLabel);
        labels.add(rotateCountLabel);
        labels.add(pathCostLabel);

        // optionPanel : creates GUI that allows user to choose level of rational behavior
        // and speed of miner
        JPanel optionPanel = new JPanel();
        optionPanel.setLayout(new GridLayout(5,1));
        optionPanel.setPreferredSize(new Dimension(WIDTH-HEIGHT, HEIGHT/2));
        optionPanel.setBackground(optionColor);

        JLabel aiLabel = new JLabel("Select Miner AI Level:");
        formatText(aiLabel);
        aiLabel.setHorizontalAlignment(JLabel.CENTER);
        JPanel aiPanel = new JPanel();
        aiPanel.setBackground(optionColor);
        randomButton = new JButton("Random");
        aiPanel.add(randomButton);
        smartButton = new JButton("Smart");
        aiPanel.add(smartButton);

        JLabel speedLabel = new JLabel("Select Miner Speed:");
        speedLabel.setHorizontalAlignment(JLabel.CENTER);
        formatText(speedLabel);
        JPanel speedPanel = new JPanel();
        speedPanel.setBackground(optionColor);
        slowButton = new JButton("Slow");
        speedPanel.add(slowButton);
        fastButton = new JButton("Fast");
        speedPanel.add(fastButton);
        goButton = new JButton();
        goButton.setFont(new Font("Arial", Font.BOLD, 30));

        switchAI(true); //default AI is Random level
        switchSpeed(true); //default speed is Slow
        resetGo(); //reset Go button
        optionPanel.add(aiLabel);
        optionPanel.add(aiPanel);
        optionPanel.add(speedLabel);
        optionPanel.add(speedPanel);
        optionPanel.add(goButton);

        dashPanel.add(optionPanel);
        dashPanel.add(labels);
        this.add(dashPanel);
    }

    // formats the text GUI
    private void formatText(JLabel msg) {
        msg.setFont (new Font("Monospaced", Font.BOLD, 20));
        msg.setForeground(Color.BLACK);
        msg.setBorder(javax.swing.BorderFactory.createEmptyBorder());
    }

    // switch the active AI button
    public void switchAI (boolean isRandom) {
        if (isRandom) {
            randomButton.setBackground(Color.GREEN);
            smartButton.setBackground(Color.GRAY);
        }
        else {
            randomButton.setBackground(Color.GRAY);
            smartButton.setBackground(Color.GREEN);
        }
    }

    // switch the active speed button
    public void switchSpeed (boolean isSlow) {
        if (isSlow) {
            slowButton.setBackground(Color.GREEN);
            fastButton.setBackground(Color.GRAY);
        }
        else {
            slowButton.setBackground(Color.GRAY);
            fastButton.setBackground(Color.GREEN);
        }
    }

    // make start button green
    public void activateGo () {
        goButton.setBackground(Color.GREEN);
        goButton.setText("SEARCHING...");
    }

    // reset start button
    public void resetGo () {
        goButton.setBackground(Color.GRAY);
        goButton.setText("FIND GOLD");
    }

    // updates the GUI to reflect the current move of the Miner, as well as the number
    // of performed actions
    public void updateCosts (char move) {
        // get scanCount
        switch (move){
            case 'M':
                currentMove = "MOVES";
                moveCount++;
                break;
            case 'R':
                currentMove = "ROTATES";
                rotateCount++;
                break;
            case 'G':
                currentMove = "FOUND GOAL";
                break;

        }
        pathCost = moveCount + rotateCount;
    }

    // resets all the counts to 0
    public void resetCounts() {
        scanCount = 0;
        moveCount = 0;
        rotateCount = 0;
        pathCost = 0;
    }

    public void displayError() {
        moveLabel.setText(" Oops! Miner can't find GOLD...");
        moveLabel.setFont(new Font("Monospaced", Font.BOLD, 18));
        moveLabel.setForeground(new Color(130, 0,0));
    }

    public void updateScanCount (int count) {
        scanCount = count;
    }

    // updates the information on the dashboard
    private void updateInfoPanel () {
        formatText(moveLabel); //in case error was previously displayed
        moveLabel.setText(" Miner " + currentMove);
        scanCountLabel.setText(" Miner scanned "+ scanCount + " times.");
        minerPosLabel.setText(" Miner is at [" + board.getMinerRow() + "," + board.getMinerCol() + "]");
        minerDirectionLabel.setText(" Miner is facing " + miner.getDirectionFront().toString());
        moveCountLabel.setText(" Move Count: " + moveCount);
        rotateCountLabel.setText(" Rotate Count: " + rotateCount);
        pathCostLabel.setText(" Path Cost: " + pathCost);
    }

    // resizes miner icons to fit into the squares
    private void makeMinerIcons (){
        changeSize("images/miner_north.png", 0, squareSize, squareSize);
        changeSize("images/miner_east.png", 1, squareSize, squareSize);
        changeSize("images/miner_south.png", 2, squareSize, squareSize);
        changeSize("images/miner_west.png", 3, squareSize, squareSize);
    }

    // changes the size of the miner icons to fit into the squares
    private void changeSize(String imgName, int dir, int w, int h){
        Image img = loadImage(imgName);
        if (img != null)
         img = img.getScaledInstance (w, h, Image.SCALE_DEFAULT);
        minerIcons[dir] = img;
    }

    // adjusts the appearance of the GUI of the Miner to reflect its current state
    private void displayMiner () {
        int row = miner.getRow();
        int col = miner.getCol();
        DirectionType currentFront = miner.getDirectionFront();
        currentLabel = boardLabelArray[row][col];
        switch (currentFront) {
            case EAST:
                currentImage = minerIcons[1];
                break;
            case SOUTH:
                currentImage = minerIcons[2];
                break;
            case WEST:
                currentImage = minerIcons[3];
                break;
            case NORTH:
                currentImage = minerIcons[0];
                break;
        }
    }

    // checks if the image file exists
    private BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e){
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    // updates the gui when repaint() is called
    @Override
    public void paint (Graphics g) {
        super.paint(g);
        displayMiner();
        updateInfoPanel();
        g.drawImage(currentImage, currentLabel.getX(), currentLabel.getY(), null);
    }

    private Board board;
    private Miner miner;
    private final int N;
    private int squareSize;

    private final int WIDTH;
    private final int HEIGHT;

    private int scanCount = 0;
    private int moveCount = 0;
    private int rotateCount = 0;
    private int pathCost = 0;
    private String currentMove;

    private Color lightBrown = new Color (150, 97, 61);
    private Color darkBrown = new Color (131, 80, 46);
    private Color beaconColor = new Color (78, 175,169);
    private Color goldColor = new Color(242, 195, 50);
    private Color infoColor = new Color(184, 223, 216);
    private Color optionColor = new Color(170,170,170);

    private JPanel boardGrid;
    private JPanel dashPanel;

    private JLabel currentLabel;
    private Image currentImage;

    private Image[] minerIcons;
    private JLabel[][] boardLabelArray;
    private JLabel minerPosLabel;
    private JLabel minerDirectionLabel;
    private JLabel moveLabel;
    private JLabel scanCountLabel;
    private JLabel moveCountLabel;
    private JLabel rotateCountLabel;
    private JLabel pathCostLabel;

    private JButton randomButton;
    private JButton smartButton;
    private JButton slowButton;
    private JButton fastButton;
    private JButton goButton;
}