public class Pipe extends Actor{

    private final int initialPositionX, initialPositionY;


    public Pipe(int positionX, int positionY, int width, int height){
        super(positionX, positionY, width, height);

        this.initialPositionX = positionX;
        this.initialPositionY = positionY;
    }


    public void initialPositionX() {
        this.setPositionX(initialPositionX);
    }

    public void initialPositionY() {
        this.setPositionY(this.initialPositionY);
    }
}
