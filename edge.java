import java.text.DecimalFormat;

public class edge {
    private int[] conectingVertexes;
    private double x1, y1, x2, y2, len;

    public edge(hotSpot A, hotSpot B){
        conectingVertexes = new int[]{A.getID(),B.getID()};
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

    public int[] getConectingVertexes() {
        return conectingVertexes;
    }
}
