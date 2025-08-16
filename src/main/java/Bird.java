public class Bird extends Actor{

    public boolean isAlive = true;
    private String status =  "Flying!";

    public final int MAX_SPEED_Y = 20;
    private int fallSpeed = 1;
    private int gravity = 1;
    private int flyStrength = 16;


    public Bird(int positionX, int positionY, int width, int height){
        super(positionX, positionY, width, height);
    }


    public void fly(){
        //this.setPositionY(this.getPositionY() + flyStrength);
        this.fallSpeed = -flyStrength;
    }
    public void fall(){

        //  Update PositionY
        this.setPositionY(this.getPositionY() + this.fallSpeed);

        //  MAKE SURE The FallingSpeed is LIMITED to MAS_SPED_Y
        if (this.fallSpeed < this.MAX_SPEED_Y){
            this.fallSpeed += this.gravity;
        }

    }
    public void die(String cause){
        this.isAlive = false;
        this.status = cause;
    }
    public String getStatus(){
        return this.status;
    }
    public void restart(){
        this.setPositionY(192);
        this.fallSpeed = -1;
        this.isAlive = true;
    }


    public int getGravity() {
        return gravity;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public int getFlyStrength() {
        return flyStrength;
    }

    public void setFlyStrength(int flyStrength) {
        this.flyStrength = flyStrength;
    }
}
