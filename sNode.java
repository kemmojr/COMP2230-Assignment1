/*Author: Timothy Kemmis
Std no. c3329386
File: sNode.java
Task: COMP2230 research assignment 1
Desc: a custom node class to be used in disjoint set data structures. Each node corresponds to a hotspot (and has a hotspot as it's data).
    This can be utilised when checking if an edge creates a cycle as one can simply call findset() on both of the points associated with the edge and if they belong to
    different sets then they do not create a cycle.
*/

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
