package src;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Locale;
class View {


    private JPanel directionPanel;



    private boolean isVertical;
    private char direction = 'H';
    private CustomButton[][] buttons;

    public CustomButton[][] getButtons() {
        return buttons;
    }

    public JPanel getHandPanel() {
        return handPanel;
    }

    public String getInputWord() {
        return inputWord;
    }

    public Tile getSelectedTile() {
        return selectedTile;
    }

    private JPanel handPanel;
    private Game model;
    private Word check;
    private JFrame frame;



    int clickedRow; // Since 'row' is accessible in this scope
    int clickedCol;
    private CustomButton[] selectedButtons;
    String inputWord = "";

    private boolean beforeStart = true;

    private Board board;
    private Tile selectedTile; // To store the selected tile from the hand

    private JButton verticalButton;
    private JButton horizontalButton;
    private CustomButton tileButton;

    public int getClickedRow() {
        return clickedRow;
    }

    public int getClickedCol() {
        return clickedCol;
    }
    public void setClickedRow(int row){
        clickedRow = row;
    }
    public void setClickedCol(int col){
        clickedCol = col;
    }
    public JButton getVerticalButton() {
        return verticalButton;
    }

    public JButton getHorizontalButton() {
        return horizontalButton;
    }
    public void setVertical(boolean vertical) {
        isVertical = vertical;
    }
    public boolean getBeforeStart(){
        return beforeStart;
    }
    public void setBeforeStart(boolean input){
        beforeStart = input;
    }

    /**
     * Constructor for the View class.
     * Initializes the game model, sets up the UI components, and displays the main game window.
     *
     * @param model The Game object representing the game model.
     */


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
        verticalButton = new JButton("Vertical");
        horizontalButton = new JButton("Horizontal");

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

                    JOptionPane.showMessageDialog(frame,"submitted word: " + inputWord + " it is now " + model.getCurrentPlayer().getName() + "'s turn, they have " + model.getCurrentPlayer().getPoints() + " points");
                    //replace hand with next players hand
                    model.play(inputWord, direction, clickedRow, clickedCol);
                    model.placeWord(inputWord,  clickedRow, clickedCol, direction,model.getCurrentPlayer());

                    updateHandPanel();

                }else{
                    JOptionPane.showMessageDialog(frame,"tried to submitted word: " + inputWord +" invalid word please try again");
                    //pickup all tiles placed
                    updateView();
                }

                horizontalButton.setEnabled(true);
                verticalButton.setEnabled(true);
                if(isVertical){
                    direction = 'V';
                }else{
                    direction = 'H';
                }
                System.out.println(inputWord);

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

                horizontalButton.setEnabled(true);
                verticalButton.setEnabled(true);
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

    public CustomButton getTileButton() {
        return tileButton;
    }

    /**
     * Updates the hand panel with the current player's tiles.
     */
    public void updateHandPanel() {
        handPanel.removeAll();

        for (Tile tile : model.getCurrentPlayer().getHand()) {
            tileButton = new CustomButton(String.valueOf(tile.getLetter()));

            handPanel.add(tileButton);
        }
        handPanel.revalidate();
        handPanel.repaint();
    }

    /**
     * Enables tiles for the next placement based on the selected direction.
     */
    public void updateEnabledTiles() {
        if (isVertical) {
            if (clickedRow + 1 < 15) {

                if(!buttons[clickedRow + 1][clickedCol].getText().isEmpty()){
                    buttons[clickedRow + 2][clickedCol].setEnabled(true);
                    inputWord = inputWord + buttons[clickedRow+1][clickedCol].getText();

                }
                else {buttons[clickedRow + 1][clickedCol].setEnabled(true);} // Enable tile below for vertical
            }
        } else { // Horizontal
            if (clickedCol + 1 < 15) {
                if (!buttons[clickedRow][clickedCol + 1].getText().isEmpty()) {
                    buttons[clickedRow][clickedCol + 2].setEnabled(true);
                    inputWord = inputWord + buttons[clickedRow][clickedCol + 1].getText();
                } else {
                    buttons[clickedRow][clickedCol + 1].setEnabled(true); // Enable tile to the right for horizontal}

                }
            }

        }
    }

    /**
     * Updates the board view with the current state of the tiles.
     */
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


    /**
     * Enables all buttons on the board for tile placement.
     */
    public void enableButtons() {
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                buttons[row][col].setEnabled(true);
            }
        }

    }
    /**
     * Disables all buttons on the board after a tile is placed.
     */
    public void disableButtons(){
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                buttons[row][col].setEnabled(false);
            }
        }

    }
}
