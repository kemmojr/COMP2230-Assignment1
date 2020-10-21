public class hotSpot {
    private int ID;
    private double x, y;

    //Constructor that initialises the hotspot and all of it's attributes
    public hotSpot(int id, double xVal, double yVal){
        ID = id;
        x = xVal;
        y = yVal;
    }

    public hotSpot(hotSpot A){//copy constructor
        this.ID = A.ID;
        this.x = A.x;
        this.y = A.y;
    }

    //Getters
    public int getID(){
        return ID;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    @Override
    public String toString() {
        return ID + ", " + x + ", " + y;
    }
}
