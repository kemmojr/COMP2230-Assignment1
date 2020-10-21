import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class graph {
    /*Disjoint set methods:
    makeSet(i) - makes a new set containing the element i
    findSet(i) - finds the identifier element of the set that i is a part of
    union(i, j) - replaces the two separate sets of i and j with their union which usually involves making one the child of the root of another.*/

    private hotSpot[] allHotSpots;
    private edge[] allEdges;
    private ArrayList<ArrayList<edge>> spanningTrees;

    public graph(hotSpot[] hotSpots){
        int numVertices = hotSpots.length, edgeNum = 0;
        allHotSpots = new hotSpot[numVertices];
        System.arraycopy(hotSpots, 0, allHotSpots, 0, hotSpots.length);
        allEdges = new edge[(numVertices*(numVertices-1))/2];//Creates an array to hold all of the possible edges for the given number of vertices without edge repetitions. n(n-1)/2
        spanningTrees = new ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            for (int j = 1; j < numVertices; j++) {
                if (j>i){
                    allEdges[edgeNum] = new edge(allHotSpots[i], allHotSpots[j]);
                    edgeNum++;
                }
            }
        }
    }

    public void printEdgeLengths(){
        int numVertices = allHotSpots.length, edgeNum1 = 0, edgeNum2 = 0;
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (i>j){
                    System.out.print(String.format("%.02f",allEdges[edgeNum1].getLength()) + " ");
                    edgeNum1++;
                } else if (j>i){
                    System.out.print(String.format("%.02f",allEdges[edgeNum2].getLength()) + " ");
                    edgeNum2++;
                } else {
                    System.out.print("0.00 ");
                }
            }
            System.out.println();
        }
    }

    //Steps to kruskals algorithm
    //1. Sort all the edges in order. All the edges in this case are all the possible connections between all the vertexes
    //2. Pick the smallest edge. Check if it forms a cycle with the spanning tree formed so far. If cycle is not formed, include this edge. Else, discard it.
    //3. Repeat until there are V-1 edges (where V is the number of vertices in the given graph)
    public void kruskalMST(){
        Arrays.sort(allEdges);

    }
}
