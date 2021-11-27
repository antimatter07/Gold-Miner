import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
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
        currentMove = "STARTS";
        makeMinerIcons();
        drawBoard();
        makeInfoPanel();
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

    private void makeInfoPanel () {
        infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout());
        infoPanel.setPreferredSize(new Dimension(WIDTH-HEIGHT, HEIGHT));
        infoPanel.setBackground(infoColor);

        JPanel labels = new JPanel();
        labels.setLayout(new GridLayout(6,1));
        labels.setBackground(infoColor);
        minerPosLabel = new JLabel();
        formatText(minerPosLabel);
        minerDirectionLabel = new JLabel();
        formatText(minerDirectionLabel);
        moveLabel = new JLabel();
        formatText(moveLabel);
        moveCountLabel = new JLabel();
        formatText(moveCountLabel);
        rotateCountLabel = new JLabel();
        formatText(rotateCountLabel);
        pathCostLabel = new JLabel();
        formatText(pathCostLabel);
        updateInfoPanel();

        labels.add(minerPosLabel);
        labels.add(minerDirectionLabel);
        labels.add(moveLabel);
        labels.add(moveCountLabel);
        labels.add(rotateCountLabel);
        labels.add(pathCostLabel);
        infoPanel.add(labels, BorderLayout.NORTH);

        // add history of moves
        this.add(infoPanel);
    }

    private void formatText(JLabel msg) {
        msg.setFont (new Font("Monospaced", Font.BOLD, 22));
        msg.setBorder(javax.swing.BorderFactory.createEmptyBorder());
    }

    public void updateCosts (char move) {
        if (move == 'M') {
            currentMove = "MOVES";
            moveCount++;
        }
        else if (move == 'R') {
            currentMove = "ROTATES";
            rotateCount++;
        }
        pathCost++;
    }

    private void updateInfoPanel () {
        minerPosLabel.setText(" Miner is at [" + board.getMinerRow() + "," + board.getMinerCol() + "]");
        minerDirectionLabel.setText(" Miner is facing " + miner.getDirectionFront().toString());
        moveLabel.setText(" Miner " + currentMove);
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

    private void changeSize(String imgName, int dir, int w, int h){
        Image img = loadImage(imgName);
        if (img != null)
         img = img.getScaledInstance (w, h, Image.SCALE_DEFAULT);
        minerIcons[dir] = img;
    }

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

    private BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e){
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

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

    private int moveCount = 0;
    private int rotateCount = 0;
    private int pathCost = 0;
    private String currentMove;

    private Color lightBrown = new Color (150, 97, 61);
    private Color darkBrown = new Color (131, 80, 46);
    private Color beaconColor = new Color (78, 175,169);
    private Color goldColor = new Color(242, 195, 50);
    private Color infoColor = new Color(184, 223, 216);

    private JPanel boardGrid;
    private JPanel infoPanel;

    private JLabel currentLabel;
    private Image currentImage;

    private Image[] minerIcons;
    private JLabel[][] boardLabelArray;
    private JLabel minerPosLabel;
    private JLabel minerDirectionLabel;
    private JLabel moveLabel;
    private JLabel moveCountLabel;
    private JLabel rotateCountLabel;
    private JLabel pathCostLabel;
    

}