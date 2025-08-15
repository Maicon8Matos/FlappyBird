import java.awt.Dimension;

public class Game implements Runnable{

    private Thread thread;

    private final GamePanel   gamePanel;
    private final GameFrame gameFrame;
    private Dimension gameDimension = new Dimension(448, 640);
    private final int FPS = 60;

    public Game(){
        this.gamePanel = new GamePanel(gameDimension);
        this.gameFrame = new GameFrame("Flappy Bird (Clone)");

        this.gameFrame.add(gamePanel);
        this.gameFrame.pack();
        this.gameFrame.setLocationRelativeTo(null);
        this.gamePanel.requestFocus();

        this.thread = new Thread(this);
        this.thread.start();

    }

    public void run() {

        long lastCheck = System.currentTimeMillis();

        while (true) {
            long timeNow = System.currentTimeMillis();
            if (timeNow - lastCheck >= 1_000 / this.FPS) {
                lastCheck = timeNow;
                this.gamePanel.repaint();
            }
        }
    }
}
