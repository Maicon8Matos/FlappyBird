import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener {

    private Bird bird = new Bird(64,  192, 64, 64);
    private Pipe topPipe, bottomPipe;
    private int pipesWidth = 64, pipesHeight = 256, pipesDistance = 192;
    private int pipesScrollSpeed = -5, minScrollSpeed = -2, maxScrollSpeed = -10, currentScrollSpeed = -5;
    private int score;

    private Color bgColor = new Color(64, 128, 192);
    private Color scoreColor = new Color(64, 255, 64);
    private Color actorsColor =  new Color(192, 192, 192);
    private Color nemesisColor = new Color(192, 128, 64);

    private Font fpsFont = new Font("Verdana", Font.PLAIN, 24);
    private Font scoreFont = new Font("Arial", Font.BOLD, 30);

    public GamePanel(Dimension dimension){
        this.setSize(dimension);
        this.setPreferredSize(dimension);
        //this.setBackground(bgColor);
        addKeyListener(this);
        this.setFocusable(true);

        this.topPipe = new Pipe((int) dimension.getWidth(), 0, this.pipesWidth, this.pipesHeight);
        this.bottomPipe = new Pipe(this.topPipe.getPositionX(), this.topPipe.getYoffset() + this.pipesDistance, this.pipesWidth, this.pipesHeight);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.setBackground(bgColor);

        //  BIRD
        g.setColor(actorsColor);
        g.fillRect(bird.getPositionX(), bird.getPositionY(), bird.getWidth(), bird.getHeight());

        //  Draw TOP and BOTTOM Pipes
        g.setColor(nemesisColor);
        g.fillRect(topPipe.getPositionX(), topPipe.getPositionY(), topPipe.getWidth(), topPipe.getHeight());
        g.fillRect(bottomPipe.getPositionX(), bottomPipe.getPositionY(), bottomPipe.getWidth(), bottomPipe.getHeight());
        movePipes();

        //  SCORE
        g.setColor(scoreColor);
        g.setFont(scoreFont);
        g.drawString("SCORE: " + score, 8, 40);
    }

    public void movePipes(){
        if(this.topPipe.getPositionX() > -this.topPipe.getWidth()){
            this.topPipe.setPositionX(this.topPipe.getPositionX() + pipesScrollSpeed);
            this.bottomPipe.setPositionX(this.topPipe.getPositionX());
        }else {
            this.topPipe.initialPositionX();
            this.bottomPipe.setPositionX(this.topPipe.getPositionX());
            this.score++;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {

        // VERTICAL MOVEMENT
        if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
            this.bird.fly();
        }

        //  FAST FOWARD
        if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            this.pipesScrollSpeed = maxScrollSpeed;
        }
        //  SLOW FOWARD
        if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
            this.pipesScrollSpeed = minScrollSpeed;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //  RESET TO NORMAL SPEED from FAST FOWARD
        if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            this.pipesScrollSpeed = currentScrollSpeed;
        }
        //  RESET to NORMAL SPEED from SLOW FOWARD
        if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
            this.pipesScrollSpeed = currentScrollSpeed;
        }
    }
}
