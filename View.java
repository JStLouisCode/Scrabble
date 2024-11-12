import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Locale;
class View {
    private JButton[][] buttons;
    private JPanel handPanel;
    private Game model;
    private Tile selectedTile; // To store the selected tile from the hand

    public View(Game model) {
        this.model = model;

        buttons = new JButton[15][15];
        for (int row = 0; row < 15; row++) {
            for (int col = 0; col < 15; col++) {
                buttons[row][col] = new JButton();
                buttons[row][col].setPreferredSize(new
                        Dimension(25, 25));

                // Add the ActionListener directly to each button
                int clickedRow = row; // Since 'row' is accessible in this scope
                int clickedCol = col; // Since 'col' is accessible in this scope
                buttons[row][col].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (selectedTile != null) {
                            model.placeTile(selectedTile, clickedRow, clickedCol);
                            selectedTile = null;
                            //updateView();
                            updateHandPanel();
                        }
                    }
                });
            }
        }

        handPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        handPanel.setPreferredSize(new Dimension(600, 50));
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
    }

    public void updateHandPanel() {
        handPanel.removeAll();
        for (Tile tile : model.getCurrentPlayer().getHand()) {
            JButton tileButton = new JButton(String.valueOf(tile.getLetter()));
            tileButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton button = (JButton) e.getSource();

                    char tileLetter = button.getText().charAt(0);
                    selectedTile = new Tile(tileLetter); // Store the selected tile
                }
            });
            handPanel.add(tileButton);
        }
        handPanel.revalidate();
        handPanel.repaint();
    }
}