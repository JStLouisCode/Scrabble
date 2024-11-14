import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Locale;
class View {
    private JPanel scorepanel;
    private CustomButton[][] buttons;
    private JPanel handPanel;
    private Game model;
    private Word check;
    private JFrame frame;
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
        scorepanel = new JPanel(new GridLayout(4, 1)); // 4 rows, 1 column, 5px vertical gap

        // Create and add text fields to the panel
        for (int i = 0; i < 4; i++) {
            JTextField textField = new JTextField("Player " + i + ": "+ model.player[i].getPoints());
            scorepanel.add(textField);
        }


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

                            selectedTile = null;

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


        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton submit = new JButton("Submit");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (check.isWord(inputWord.toLowerCase())){
                    //replace all used tiles
                    model.addPoints(inputWord, model.getCurrentPlayer());
                    model.nextPlayer();
                    JOptionPane.showMessageDialog(frame,"submitted word: " + inputWord + " it is now " + model.getCurrentPlayer().getName() + "'s turn, they have " + model.getCurrentPlayer().getPoints() + " points");
                    //replace hand with next players hand
                    updateHandPanel();
                    beforeStart = true;
                    inputWord = "";


                }else{
                    JOptionPane.showMessageDialog(frame,"submitted word: " + inputWord +" invalid word please try again");
                    //pickup all tiles placed
                    inputWord = "";
                }
                updateView();
            }
        });

        JPanel skipPannel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        skipPannel.setPreferredSize(new Dimension(100,100));
        JButton skip = new JButton("skip");
        skip.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                model.addPoints(inputWord, model.getCurrentPlayer());
                model.nextPlayer();
                JOptionPane.showMessageDialog(frame,"skipping turn, it is now " + model.getCurrentPlayer().getName() + "'s turn, they have " + model.getCurrentPlayer().getPoints() + " points");
                //replace hand with next players hand
                updateHandPanel();
                beforeStart = true;
                inputWord = "";


                updateView();
            }
        });

        JPanel container = new JPanel(new GridLayout(15, 15, 0, 0));
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                container.add(buttons[row][col]);
            }
        }
        frame.add(scorepanel, BorderLayout.WEST);
        frame.add(handPanel, BorderLayout.SOUTH);
        frame.add(container, BorderLayout.NORTH);
        frame.add(submit);
        frame.add(skip,BorderLayout.EAST);
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
                Tile tile = model.board.getTile(row, col);
                if (tile != null && tile.getLetter() != ' ') {
                    buttons[row][col].setText(String.valueOf(tile.getLetter()));
                } else {
                    buttons[row][col].setText("");
                }
            }
        }
        scorepanel.removeAll();
        for (int i = 0; i < 4; i++) {
            JTextField textField = new JTextField("Player " + i + ": "+ model.player[i].getName());
            scorepanel.add(textField);
        }
        frame.revalidate();
        frame.repaint();
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