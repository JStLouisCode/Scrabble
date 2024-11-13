import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Locale;
class View {
    private CustomButton[][] buttons;
    private JPanel handPanel;
    private Game model;
    private Word check;

    int clickedRow; // Since 'row' is accessible in this scope
    int clickedCol;
    private CustomButton[] selectedButtons;
    String inputWord = "";

    boolean beforeStart = true;

    private Board board;
    private Tile selectedTile; // To store the selected tile from the hand

    public View(Game model) {
        this.model = model;
        this.check = new Word();
        model.initializeTiles();
        model.initializePlayer();

        buttons = new CustomButton[15][15];


        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                buttons[row][col] = new CustomButton();
                buttons[row][col].setPreferredSize(new Dimension(40, 25));
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
                        CustomButton clickedButton = (CustomButton) e.getSource();
                        if (selectedTile != null) {
                            clickedRow = clickedButton.getRow();
                            clickedCol = clickedButton.getCol();

                            buttons[clickedRow][clickedCol].setText(String.valueOf(selectedTile.getLetter()));
                            inputWord = inputWord + selectedTile.getLetter();
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
        handPanel.setPreferredSize(new Dimension(700, 50));
        updateHandPanel();

        //Answer button
        JPanel answer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        answer.setPreferredSize(new Dimension(100,100));


        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton submit = new JButton("Submit");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (check.isWord(inputWord)){
                    //replace all used tiles

                    model.nextPlayer();
                    JOptionPane.showMessageDialog(frame,"submitted word: " + inputWord + " it is now " + model.getCurrentPlayer().getName() + "'s turn");
                    //replace hand with next players hand
                    updateHandPanel();
                    beforeStart = true;
                    inputWord = "";

                }else{
                    JOptionPane.showMessageDialog(frame,"submitted word: " + inputWord +" invalid word please try again");
                    //pickup all tiles placed

                }
            }
        });



        JPanel container = new JPanel(new GridLayout(15, 15, 0, 0));
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                container.add(buttons[row][col]);
            }
        }

        frame.add(handPanel, BorderLayout.SOUTH);
        frame.add(container, BorderLayout.NORTH);
        frame.add(submit,BorderLayout.EAST);
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

                    char tileLetter = button.getText().charAt(0);
                    selectedTile = new Tile(tileLetter); // Store the selected tile

                    if (beforeStart) {
                        enableButtons();
                    } else {
                        disableButtons();
                        if (clickedRow + 1 != 15 && clickedCol + 1 != 15) {
                            if(!buttons[clickedRow + 1][clickedCol].getText().isEmpty()){
                                inputWord = inputWord + buttons[clickedRow + 1][clickedCol].getText();
                                buttons[clickedRow + 2][clickedCol].setEnabled(true);
                            }
                            else {
                                buttons[clickedRow + 1][clickedCol].setEnabled(true);
                            }
                            if (!buttons[clickedRow][clickedCol + 1].getText().isEmpty()){
                                inputWord = inputWord + buttons[clickedRow][clickedCol+1].getText();
                                buttons[clickedRow][clickedCol + 2].setEnabled(true);
                            }
                            else {buttons[clickedRow][clickedCol + 1].setEnabled(true);}

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