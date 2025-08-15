public class Bird extends Actor{

    private int flyStrength = -64;

    public Bird(int positionX, int positionY, int width, int height){
        super(positionX, positionY, width, height);
    }

    public void fly(){
        //this.setPositionY(this.getPositionY() + flyStrength);
    }

}
