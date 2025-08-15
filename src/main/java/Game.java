import java.awt.Dimension;

public class Game {

    private final GamePanel   gamePanel;
    private final GameFrame gameFrame;
    private Dimension gameDimension = new Dimension(448, 640);

    public Game(){
        this.gamePanel = new GamePanel(gameDimension);
        this.gameFrame = new GameFrame("Flappy Bird (Clone)");

        this.gameFrame.add(gamePanel);
        this.gameFrame.pack();
        this.gameFrame.setLocationRelativeTo(null);
        this.gamePanel.requestFocus();
    }


}
