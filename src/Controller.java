package src;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class Controller implements ActionListener {
    private Game model;
    private View view;

    public Controller(Game model, View view) {
        this.model = model;
        this.view = view;

        view.getVerticalButton().addActionListener(e->verticleButton());
        view.getHorizontalButton().addActionListener(e->horizontalButton());
        view.getCurrentButton(view.getClickedRow(), view.getClickedCol()).addA
        view.getSubmit()
        view.getSkip()
        view.getTileButton().addActionListener(e->tileButton());


    }

    public static void main(String[] args) {

    }

    private void verticleButton(){
        view.setVertical(true);
        view.getVerticalButton().setEnabled(false); // Disable after selecting vertical
        view.getHorizontalButton().setEnabled(false); // Disable horizontal as well
        view.updateEnabledTiles();
    }
    private void horizontalButton(){
        view.setVertical(false);
        view.getVerticalButton().setEnabled(false); // Disable after selecting horizontal
        view.getVerticalButton().setEnabled(false); // Disable vertical as well
        view.updateEnabledTiles();
    }
    public void tileButton(ActionEvent e) {
        CustomButton button = (CustomButton) e.getSource();
        button.setEnabled(false);
        view.getSelectedTile() = new Tile(button.getText().charAt(0)); // Store the selected tile
        if (view.getBeforeStart()) {
            view.enableButtons();
        } else {
            view.disableButtons();
            view.updateEnabledTiles();
        }
        view.setBeforeStart(false);
    }
        public void ClickedBoard(ActionEvent e) {
            CustomButton clickedButton = (CustomButton) e.getSource();
            if (view.getSelectedTile() != null) {
                view.setClickedRow = clickedButton.getRow();
                clickedCol = clickedButton.getCol();
                buttons[clickedRow][clickedCol].setText(String.valueOf(selectedTile.getLetter()));
                inputWord = inputWord + selectedTile.getLetter();
                selectedTile = null;
                updateHandPanel();
                disableButtons();
            }
        }

    });


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

}
