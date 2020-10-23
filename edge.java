/*Author: Timothy Kemmis
Std no. c3329386
File: edge.java
Task: COMP2230 research assignment 1
Desc: a custom edge class to be compared and used in constructing MST's
*/

import java.text.DecimalFormat;

public class edge implements Comparable<edge>{
    private hotSpot[] conectedHotSpots;
    private double x1, y1, x2, y2, len;

    public edge(hotSpot A, hotSpot B){
        conectedHotSpots = new hotSpot[2];
        conectedHotSpots[0] = A;
        conectedHotSpots[1] = B;
        x1 = A.getX();
        x2 = B.getX();
        y1 = A.getY();
        y2 = B.getY();
        DecimalFormat df = new DecimalFormat("#.##");
        //Distance between hotSpot A and hotSpot B using pythagoras theorem. Answer is rounded to 2 DP
        len = Double.parseDouble(df.format(Math.sqrt(((x1-x2)*(x1-x2))+((y1-y2)*((y1-y2))))));
    }

    public double getLength() {
        return len;
    }

    public hotSpot getEnd1(){
        return conectedHotSpots[0];
    }

    public hotSpot getEnd2(){
        return conectedHotSpots[1];
    }

    @Override
    public int compareTo(edge o) {
        if (this.len==o.len)
            return 0;
        else if (this.len>o.len)
            return 1;

        return -1;
    }

    @Override
    public String toString() {
        return conectedHotSpots[0].getID() + "to" + conectedHotSpots[1].getID() + ". length = " + len;
    }
}
