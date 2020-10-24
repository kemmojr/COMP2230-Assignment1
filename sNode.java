/*Author: Timothy Kemmis
Std no. c3329386
File: sNode.java
Task: COMP2230 research assignment 1
Desc: a custom node class to be used in disjoint set data structures. Each node corresponds to a hotspot (and has a hotspot as it's data).
    This can be utilised when checking if an edge creates a cycle as one can simply call findset() on both of the points associated with the edge and if they belong to
    different sets then they do not create a cycle.
*/

public class sNode {//Node class to be used as part of the tree structures in disjoint sets
    private sNode parent;//The parent node of this node
    private hotSpot data;//A hotspot point that is the data of the node
    private int rank;//A metric used to denote the upper bound on the trees height

    public sNode(hotSpot h){//makeSet
        parent = this;//In a new node the parent is set to itself as it is the root of the tree
        data = h;
        rank = 0;
    }

    public sNode(sNode parent, hotSpot h){
        this.parent = parent;
        data = h;
    }

    //Getters
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

    //Setters
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
