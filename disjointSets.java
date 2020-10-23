/*Author: Timothy Kemmis
Std no. c3329386
File: subset.java
Task: COMP2230 research assignment 1
Desc: a class for holding all the main methods relating to disjoint sets. Some of the implementation is from the lecture slides concerning disjoint sets.
    makeSet(i) is not included as what is done in makeset is done in the constructor for sNode (the node used in the disjoint set trees)
*/

public class disjointSets {//A class for dealing with disjoint sets

    public disjointSets(){//makeSet
        //empty constructor as initialising the nodes for the first node is done in sNode
    }

    public sNode findSet(sNode element1){//finds the root of the tree that sNode belongs to
        sNode current = element1;
        while (current != current.getParent())
            current = current.getParent();
        sNode root = current;
        sNode element2 = element1.getParent();
        while (element2 != root) {
            element1.getParent().setParent(root);
            element1 = element2;
            element2 = element1.getParent();
        }
        return root;
    }

    public void mergeTrees(sNode nodeI, sNode nodeJ){//the inputs are the roots of two trees

        if (nodeI.getRank() < nodeJ.getRank())//makes the larger tree root the parent of the smaller tree
            nodeI.setParent(nodeJ);
        else if (nodeI.getRank() > nodeJ.getRank())
            nodeJ.setParent(nodeI);
        else {
            nodeI.setParent(nodeJ);//otherwise the second node becomes the parent and rank is incremented
            nodeJ.setRank(nodeJ.getRank() + 1);
        }

    }

    public void union(sNode element1, sNode element2){
        mergeTrees(findSet(element1),findSet(element2));
    }

}
