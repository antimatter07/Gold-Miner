import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class BoardPanel extends JPanel {
    BoardPanel(Board board, int width, int height) {
        super();
        setLayout(new FlowLayout(0,0,0));
        setSize(width, height);
        WIDTH = width;
        HEIGHT = height;
        this.board = board;
        N = board.getSize();
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
                JLabel square = new JLabel();
                square.setOpaque(true);
                if ((i%2 == 0 && j%2 == 0) || (i%2 != 0 && j%2 != 0))
                    square.setBackground(lightBrown);
                else square.setBackground(darkBrown);

                // add object symbols
                Unit unit = board.getUnit(i, j);
                UnitType unitType = unit.getUnitType();
                if (unitType != UnitType.EMPTY) {
                    square.setText(Character.toString(unit.getSymbol()));
                    square.setFont(new Font("Monospaced", Font.BOLD, HEIGHT/N));
                    square.setHorizontalAlignment(JLabel.CENTER);
                    square.setVerticalAlignment(JLabel.CENTER);
                    switch (unitType) {
                        case BEACON:
                            square.setBackground(beaconColor);
                            break;
                        case GOLD:
                            square.setBackground(goldColor);
                            break;
                        case PIT:
                            square.setBackground(Color.GRAY);
                            break;
                    }
                }
                boardGrid.add(square);
            }
        }
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

    private void updateInfoPanel () {
        minerPosLabel.setText("Miner is at [" + board.getMinerRow() + "," + board.getMinerCol() + "]");
        minerDirectionLabel.setText("Miner is facing " + board.getMinerFront());
        moveLabel.setText("Miner " /* + board.getMinerMove() */);
        moveCountLabel.setText("Move Count: "/* + board.getMoveCount */);
        rotateCountLabel.setText("Rotate Count: " /* board.getRotateCount */);
        pathCostLabel.setText("Path Cost: " /* board.getPathCost */);
    }

    // displays the miner
    @Override
    public void paint (Graphics g){
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        Font font = new Font(null, Font.BOLD, HEIGHT/N);
        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(Math.toRadians(180), 0, 0);
        Font rotatedFont = font.deriveFont(affineTransform);
        g2.setFont(rotatedFont);
        g2.drawString(Character.toString(board.getMinerSymbol()),board.getMinerRow(),board.getMinerCol());
        g2.dispose();
    }

    private Board board;
    private final int N;

    private final int WIDTH;
    private final int HEIGHT;

    private Color lightBrown = new Color (150, 97, 61);
    private Color darkBrown = new Color (131, 80, 46);
    private Color beaconColor = new Color (78, 175,169);
    private Color goldColor = new Color(242, 195, 50);
    private Color infoColor = new Color(184, 223, 216);

    private JPanel boardGrid;
    private JPanel infoPanel;

    private JLabel minerPosLabel;
    private JLabel minerDirectionLabel;
    private JLabel moveLabel;
    private JLabel moveCountLabel;
    private JLabel rotateCountLabel;
    private JLabel pathCostLabel;

}
