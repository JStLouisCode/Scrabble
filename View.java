import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Locale;
class View {
    private CustomButton[][] buttons;
    private JPanel handPanel;
    private Game model;

    int clickedRow; // Since 'row' is accessible in this scope
    int clickedCol;

    boolean beforeStart = true;

    private Board board;
    private Tile selectedTile; // To store the selected tile from the hand

    public View(Game model) {
        this.model = model;

        buttons = new CustomButton[15][15];

        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                buttons[row][col] = new CustomButton();
                buttons[row][col].setPreferredSize(new
                        Dimension(25, 25));

                buttons[row][col].setEnabled(false);
                buttons[row][col].setRow(row);
                buttons[row][col].setCol(col);


                clickedRow = buttons[row][col].getRow();



                // Add the ActionListener directly to each button
                clickedRow = row; // Since 'row' is accessible in this scope
                clickedCol = col; // Since 'col' is accessible in this scope
                buttons[row][col].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (selectedTile != null) {
                            clickedRow = buttons
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

        handPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        handPanel.setPreferredSize(new Dimension(650, 50));
        updateHandPanel();

        JFrame frame = new JFrame();
        JPanel container = new JPanel(new GridLayout(15, 15, 0, 0));
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                container.add(buttons[row][col]);
            }
        }

        frame.add(handPanel, BorderLayout.SOUTH);
        frame.add(container, BorderLayout.NORTH);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public void updateHandPanel() {
        handPanel.removeAll();
        for (Tile tile : model.getCurrentPlayer().getHand()) {
            CustomButton tileButton = new CustomButton(String.valueOf(tile.getLetter()));
            tileButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CustomButton button = (CustomButton) e.getSource();

                    char tileLetter = button.getText().charAt(0);
                    selectedTile = new Tile(tileLetter); // Store the selected tile

                    if (beforeStart) {
                        enableButtons();
                    } else {
                        disableButtons();
                        if (clickedRow + 1 != 15 && clickedCol + 1 != 15) {
                            buttons[clickedRow + 1][clickedCol].setEnabled(true);
                            buttons[clickedRow][clickedCol + 1].setEnabled(true);
                        }
                    }
                    beforeStart = false;
                }
            });
            handPanel.add(tileButton);
        }
        handPanel.revalidate();
        handPanel.repaint();
    }


    public void updateView() {
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                Tile tile = model.board.getTile(row, col); // Get the tile from the model's board
                CustomButton button = buttons[row][col];

                if (tile != null && tile.getLetter() != ' ') { // Check if the tile exists and is not blank
                    button.setText(String.valueOf(tile.getLetter())); // Update button text with the tile's letter
                } else {
                    button.setText(""); // Clear the button if no tile is present or it's blank
                }
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

    public void disableButtons(){
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                buttons[row][col].setEnabled(false);
            }
        }

    }
}