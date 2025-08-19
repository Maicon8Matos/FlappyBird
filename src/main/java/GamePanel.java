import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.Random;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements KeyListener {

    private boolean gameIsPaused = true;

    private Bird bird = new Bird(64,  192, 64, 48);
    private Pipe topPipe, bottomPipe;
    private int pipesWidth = 64, pipesHeight = 256, pipesDistance = 192;
    private int pipesScrollSpeed = -5, initialScrollSpeed = -5, minScrollSpeed = -2, maxScrollSpeed = -10;
    private Random random = new Random();

    private int score;

    private Color bgColor = new Color(64, 128, 192);
    private Color scoreColor = new Color(64, 255, 64);
    private Color actorsColor =  new Color(192, 192, 192);
    private Color nemesisColor = new Color(192, 128, 64);
    private Color gameOverColor = new Color(192, 32, 32);

    private Font fpsFont = new Font("Verdana", Font.PLAIN, 24);
    private Font gameOverFont = new Font("Arial", Font.BOLD, 40);
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
        //birdFall();

        //  Draw TOP and BOTTOM Pipes
        g.setColor(nemesisColor);
        g.fillRect(topPipe.getPositionX(), topPipe.getPositionY() + 8, topPipe.getWidth(), topPipe.getHeight());
        g.fillRect(bottomPipe.getPositionX(), bottomPipe.getPositionY(), bottomPipe.getWidth(), bottomPipe.getHeight());
        //movePipes();

        //  SCORE
        g.setColor(scoreColor);
        g.setFont(scoreFont);
        g.drawString("SCORE: " + score, 8, 40);

        //  GAME NOT PAUSED
        if(!this.gameIsPaused){
            birdFall();
            movePipes();
        }

        //  Start CONTROLS TIP Message
        if (this.gameIsPaused && this.bird.isAlive){
            g.setColor(actorsColor);
            g.drawString("Press W or Up-Arrow to Fly!", 24, 96);
        }

        //  GAME OVER - TopPipe OR BottomPipe
        if(this.colliding(this.bird, this.topPipe) || this.colliding(this.bird, this.bottomPipe)){
            this.bird.die("Piped!");
            this.gameIsPaused = true;
        }

        if(!this.bird.isAlive){
            //  DEATH Cause MESSAGE
            g.setColor(gameOverColor);
            g.setFont(gameOverFont);
            g.drawString("YOU DIED!", 128, this.getHeight() / 2 + 16);
            g.drawString("(" + this.bird.getStatus() + ")", 128, this.getHeight() / 2 + 64);

            //  RESTART Keys MESSAGE
            g.setColor(scoreColor);
            g.setFont(this.scoreFont);
            g.drawString("Press R or ESC to Restart!", 24, this.getWidth() / 2 - 16);
        }
    }
    //  COLLISION CHECK
    private boolean colliding(Actor one, Actor two){
        if(one.getXoffset() >= two.getPositionX() && two.getXoffset() > one.getPositionX()){
            if(one.getYoffset() >= two.getPositionY() && two.getYoffset() > one.getPositionY()){
                return true;
            }
        }
        return false;
    }

    public void birdFall(){

        //  CHECK if THe BIRD is On Screen
        if(this.bird.getPositionY() < this.getHeight() - this.bird.getHeight()){
            //  Update BIRD PositionY
            this.bird.fall();
        }

        //GAME OVER - Floor Crash
        if(this.bird.getPositionY() > this.getHeight() - this.bird.getHeight()){
            this.bird.setPositionY(this.getHeight() - this.bird.getHeight());

            if (this.bird.isAlive){
                this.bird.die("Floor Crash!");
                this.gameIsPaused = true;
            }
        }
        //  GAME OVER - Fly Too High
        if(this.bird.getPositionY() < 0){
            this.bird.setPositionY(0);
            this.bird.die("Lightning!");
            this.gameIsPaused = true;
        }

    }

    public void movePipes(){
        if(this.topPipe.getPositionX() > -this.topPipe.getWidth()){
            this.topPipe.setPositionX(this.topPipe.getPositionX() + pipesScrollSpeed);
            this.bottomPipe.setPositionX(this.topPipe.getPositionX());
        }else {
            this.topPipe.setHeight(this.random.nextInt(1, 6) * 64);
            this.topPipe.initialPositionX();
            this.bottomPipe.setPositionX(this.topPipe.getPositionX());
            this.bottomPipe.setPositionY(this.topPipe.getYoffset() + this.pipesDistance);
            this.bottomPipe.setHeight(this.getHeight() - this.bottomPipe.getPositionY()  - 8);
            this.score++;
        }
    }

    public void gameStart(){
        this.score = 0;
        this.gameIsPaused = false;
        this.bird.restart();
        this.topPipe.initialPositionX();
        this.bottomPipe.initialPositionX();
    }
    public void gameRestart(){
        this.score = 0;
        this.bird.restart();
        this.topPipe.initialPositionX();
        this.topPipe.initialPositionY();
        this.bottomPipe.initialPositionX();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {

        // VERTICAL MOVEMENT
        if(e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
            if(this.bird.isAlive) {
                this.bird.fly();
                if(this.gameIsPaused){
                    this.gameStart();
                }
            }
        }

        //  FAST FOWARD
        if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            this.pipesScrollSpeed = maxScrollSpeed;
        }
        //  SLOW FOWARD
        if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
            this.pipesScrollSpeed = minScrollSpeed;
        }

        //  RESET Bird to Origin PositionY
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE || e.getKeyCode() == KeyEvent.VK_R) {
            if (!this.bird.isAlive){
                this.gameRestart();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //  RESET TO NORMAL SPEED from FAST FOWARD
        if(e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            this.pipesScrollSpeed = initialScrollSpeed;
        }
        //  RESET to NORMAL SPEED from SLOW FOWARD
        if(e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
            this.pipesScrollSpeed = initialScrollSpeed;
        }
    }
}
