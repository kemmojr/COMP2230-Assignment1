
public class subset {//A class for dealing with disjoint sets
    private sNode node;
    //private int rank;
    /*Disjoint set methods:
    makeSet(i) - makes a new set containing the element i
    findSet(i) - finds the identifier element of the set that i is a part of
    union(i, j) - replaces the two separate sets of i and j with their union which usually involves making one the child of the root of another.*/
    public subset(){//makeSet
        //empty constructor as initialising the nodes for the first node is done in sNode
    }

    public void makeSet(sNode n){
        node = n;
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
