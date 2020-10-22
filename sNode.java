public class sNode {
    private sNode parent;
    private hotSpot data;
    private int rank;

    public sNode(hotSpot h){
        parent = this;
        data = h;
        rank = 0;
    }

    public sNode(sNode parent, hotSpot h){
        this.parent = parent;
        data = h;
    }

    public int getDataID(){
        return data.getID();
    }

    public double getDataX(){
        return data.getX();
    }

    public double getDataY(){
        return data.getY();
    }

    public sNode getParent() {
        return parent;
    }

    public void setParent(sNode parent) {
        this.parent = parent;
    }

    public void setRank(int r){
        rank = r;
    }

    public int getRank(){
        return rank;
    }
}
