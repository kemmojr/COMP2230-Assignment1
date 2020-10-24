/*Author: Timothy Kemmis
Std no. c3329386
File: subset.java
Task: COMP2230 research assignment 1
Desc: a class for holding all the main methods relating to disjoint sets. The implementation is from the lecture slides concerning disjoint sets.
    makeSet(i) is not included as what is done in makeset is done in the constructor for sNode (the node used in the disjoint set trees)
*/

public class disjointSets {//A class for dealing with disjoint sets


    public disjointSets(){
        //empty constructor as initialising the nodes for the first node is done in sNode
    }

    public sNode findSet(sNode element1){//finds the root of the tree that sNode belongs to
        sNode current = element1;//node to traverse up the tree to eventually become the root node
        while (current != current.getParent())//If the node's parent is itself then it is the root of the tree
            current = current.getParent();//otherwise continue to get the parent until the root is reached
        sNode root = current;//A variable that is the root of the tree
        sNode element2 = element1.getParent();//getting the parent of element1 so we can set element1 parent to root
        while (element2 != root) {//Sets all the nodes on the path to the root to be the child of the root, compressing the tree which ultimately lowers algorithm times
            element1.getParent().setParent(root);
            element1 = element2;
            element2 = element1.getParent();
        }
        return root;//Returns the root node that is the identifier for the set that element1 is in
    }

    public void mergeTrees(sNode nodeI, sNode nodeJ){//the inputs are the roots of two trees

        if (nodeI.getRank() < nodeJ.getRank())//makes the larger tree root the parent of the smaller tree when nodeI is smaller
            nodeI.setParent(nodeJ);
        else if (nodeI.getRank() > nodeJ.getRank())//makes the larger tree root the parent of the smaller tree when nodeJ is smaller
            nodeJ.setParent(nodeI);
        else {
            nodeI.setParent(nodeJ);//otherwise the second node becomes the parent and rank is incremented in the root node of the new tree
            nodeJ.setRank(nodeJ.getRank() + 1);
        }

    }

    public void union(sNode element1, sNode element2){//Takes the elements of two different sets and runs merge trees on the root nodes of each set
        mergeTrees(findSet(element1),findSet(element2));
    }

}
