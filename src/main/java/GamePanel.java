import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener {

    private Actor bird = new Actor(64,  192, 64, 64);
    private int score;

    private Color bgColor = new Color(64, 128, 192);
    private Color scoreColor = new Color(64, 255, 64);
    private Color actorsColor =  new Color(192, 192, 192);

    private Font mainFont = new Font("verdana", Font.PLAIN, 20);
    private Font scoreFont = new Font("Arial", Font.BOLD, 30);

    public GamePanel(Dimension dimension){
        this.setSize(dimension);
        this.setPreferredSize(dimension);

        //this.setBackground(bgColor);
        addKeyListener(this);
        this.setFocusable(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.setBackground(bgColor);

        g.setColor(scoreColor);
        g.setFont(scoreFont);
        g.drawString("SCORE: " + score, 8, 40);

        g.setColor(actorsColor);
        g.fillRect(bird.getPositionX(), bird.getPositionY(), bird.getWidth(), bird.getHeight());
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {

        // VERTICAL MOVEMENT
        if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
            System.out.println("W or UP Pressed");
            this.repaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
