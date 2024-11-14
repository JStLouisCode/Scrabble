import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Locale;
class View {

    private JPanel directionPanel;
    private boolean isVertical;
    private char direction = 'H';
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

                if (check.isWord(inputWord.toLowerCase()) && inputWord.length() > 1){
                    //replace all used tiles
                    model.addPoints(inputWord, model.getCurrentPlayer());
                    model.nextPlayer();
                    JOptionPane.showMessageDialog(frame,"submitted word: " + inputWord + " it is now " + model.getCurrentPlayer().getName() + "'s turn, they have " + model.getCurrentPlayer().getPoints() + " points");
                    //replace hand with next players hand
                    updateHandPanel();



                }else{
                    JOptionPane.showMessageDialog(frame,"submitted word: " + inputWord +" invalid word please try again");
                    //pickup all tiles placed
                    updateView();
                }
                model.play(inputWord, direction, clickedRow, clickedCol);
                beforeStart = true;
                inputWord = "";
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


        frame.add(handPanel, BorderLayout.SOUTH);
        frame.add(directionPanel, BorderLayout.WEST);
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
                if (tile != null && tile.getLetter() != ' ') {
                    buttons[row][col].setText(String.valueOf(tile.getLetter()));
                } else {
                    buttons[row][col].setText("");
                }
            }
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