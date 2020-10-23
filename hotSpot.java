/*Author: Timothy Kemmis
Std no. c3329386
File: hotSpot.java
Task: COMP2230 research assignment 1
Desc: a class used to store all of the data accosiated with each hotspot including the ID
*/

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
        return  "(" + x + ", " + y + ")";
    }
}
