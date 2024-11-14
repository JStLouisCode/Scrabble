
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

class View {
    private CustomButton[][] buttons;
    private JPanel handPanel;
    private Game model;

    private boolean isVertical;
    private JPanel directionPanel;
    private Set<CustomButton> selectedTileButtons = new HashSet<>();

    int clickedRow; // Since 'row' is accessible in this scope
    int clickedCol;

    boolean beforeStart = true;
    private Tile selectedTile; // To store the selected tile from the hand

    public View(Game model) {
        this.model = model;
        model.initializeTiles();
        model.initializePlayer();

        buttons = new CustomButton[15][15];

        // Initialize the grid of buttons
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                buttons[row][col] = new CustomButton();
                buttons[row][col].setPreferredSize(new Dimension(50, 25));
                buttons[row][col].setEnabled(false);
                buttons[row][col].setRow(row);
                buttons[row][col].setCol(col);

                buttons[row][col].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        CustomButton clickedButton = (CustomButton) e.getSource();
                        if (selectedTile != null) {
                            clickedRow = clickedButton.getRow();
                            clickedCol = clickedButton.getCol();

                            buttons[clickedRow][clickedCol].setText(String.valueOf(selectedTile.getLetter()));
                            model.board.setTile(clickedRow, clickedCol, selectedTile);
                            selectedTile = null;
                            updateView();
                            updateHandPanel();

                            disableButtons();
                        }
                    }
                });
            }
        }

        // Initialize hand panel
        handPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        handPanel.setPreferredSize(new Dimension(700, 50));
        updateHandPanel();

        // Initialize the direction buttons
        directionPanel = new JPanel(new GridLayout(2, 1));
        JButton verticalButton = new JButton("Vertical");
        JButton horizontalButton = new JButton("Horizontal");

        verticalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isVertical = true;
                verticalButton.setEnabled(false); // Disable after selecting vertical
                horizontalButton.setEnabled(false); // Disable horizontal as well
                updateEnabledTiles();
            }
        });

        horizontalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isVertical = false;
                horizontalButton.setEnabled(false); // Disable after selecting horizontal
                verticalButton.setEnabled(false); // Disable vertical as well
                updateEnabledTiles();
            }
        });

        directionPanel.add(verticalButton);
        directionPanel.add(horizontalButton);

        // Initialize Submit button
        JButton submit = new JButton("Submit");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "MESSAGE");
            }
        });

        // Create container panel for game board
        JPanel container = new JPanel(new GridLayout(15, 15, 0, 0));
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                container.add(buttons[row][col]);
            }
        }

        // Set up main frame
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(handPanel, BorderLayout.SOUTH);
        frame.add(container, BorderLayout.CENTER);
        frame.add(submit, BorderLayout.EAST);
        frame.add(directionPanel, BorderLayout.WEST);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        updateView();
    }

    public void updateHandPanel() {
        handPanel.removeAll();

        for (Tile tile : model.getCurrentPlayer().getHand()) {
            CustomButton tileButton = new CustomButton(String.valueOf(tile.getLetter()));
            tileButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CustomButton button = (CustomButton) e.getSource();
                    button.setEnabled(false);

                    selectedTile = new Tile(button.getText().charAt(0)); // Store the selected tile

                    if (beforeStart) {
                        enableButtons();
                    } else {
                        disableButtons();
                        updateEnabledTiles();
                    }
                    beforeStart = false;
                }
            });
            handPanel.add(tileButton);
        }
        handPanel.revalidate();
        handPanel.repaint();
    }

    public void updateEnabledTiles() {
        if (isVertical) {
            if (clickedRow + 1 < 15) {
                buttons[clickedRow + 1][clickedCol].setEnabled(true); // Enable tile below for vertical
            }
        } else { // Horizontal
            if (clickedCol + 1 < 15) {
                buttons[clickedRow][clickedCol + 1].setEnabled(true); // Enable tile to the right for horizontal
            }
        }
    }


    public void updateView() {
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                Tile tile = model.board.getTile(row, col);
                CustomButton button = buttons[row][col];
                button.setText(tile != null && tile.getLetter() != ' ' ? String.valueOf(tile.getLetter()) : "");
            }
        }
    }

    public void enableButtons() {
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                buttons[row][col].setEnabled(true);
            }
        }
    }

    public void disableButtons() {
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                buttons[row][col].setEnabled(false);
            }
        }
    }
}