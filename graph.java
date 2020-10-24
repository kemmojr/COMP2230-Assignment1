/*Author: Timothy Kemmis
Std no. c3329386
File: graph.java
Task: COMP2230 research assignment 1
Desc: a graph class for running all the main computations involved in the program including generating the "weighted graph" of edge lengths and running
    kruskals algorithm using the functionality of disjoint sets
*/

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class graph {


    private hotSpot[] allHotSpots;//Array of all hotSpots
    private sNode[] allNodes;//Array of all the nodes which each hold a hotSpot. The nodes are also used in the disjoint sets
    private edge[] allEdges;//Array of all the possible edges (with no repetitions)
    private ArrayList<edge> addedEdges;//ArrayList to keep track of how many edges have been added to the MST(s)
    private disjointSets sets;//A reference class for calling all the disjoint set methods
    private int clustersToFind;

    public graph(hotSpot[] hotSpots, int numClusters){//Constructor that initialises all the information required for running kruskals and for creating the complete graph of all the edges
        clustersToFind = numClusters;
        int numVertices = hotSpots.length, edgeNum = 0;
        allHotSpots = new hotSpot[numVertices];
        allNodes = new sNode[numVertices];
        System.arraycopy(hotSpots, 0, allHotSpots, 0, hotSpots.length);
        for (int i = 0; i < allHotSpots.length; i++){
            allNodes[i] = new sNode(allHotSpots[i]);//creates a node for each hotSpot in an array where the index of it's node is it's ID-1
        }
        allEdges = new edge[(numVertices*(numVertices-1))/2];//Creates an array to hold all of the possible edges for the given number of vertices without edge repetitions. n(n-1)/2

        for (int i = 0; i < numVertices; i++) {
            for (int j = 1; j < numVertices; j++) {
                if (j>i){
                    allEdges[edgeNum] = new edge(allHotSpots[i], allHotSpots[j]);
                    //goes through all the vertices and creates all of the edges exactly once. i.e. only edge AB will be added and not BA
                    edgeNum++;
                }
            }
        }
        sets = new disjointSets();
    }

    public void printEdgeLengths(){//outputs the complete list of all edge lengths between all the hotspots aka the "weighted graph"
        int numVertices = allHotSpots.length, edgeNum1 = 0, edgeNum2 = 0;
        double[][] weightedGraph = new double[numVertices][numVertices];
        int count = 0;
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (j>i){
                    //calc = (numVertices*i)+j-i*j;
                    weightedGraph[i][j] = allEdges[count].getLength();
                    weightedGraph[j][i] = allEdges[count].getLength();
                    count++;
                    //System.out.print(String.format("%.02f",allEdges[calc].getLength()) + "\t");

                } else if (i==j){
                    weightedGraph[i][j] = 0.0;
                }
            }
        }

        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (i==j)
                    System.out.print("0\t\t");
                else
                    System.out.print(String.format("%.02f",weightedGraph[i][j]) + "\t");
            }
            System.out.println();
        }
    }

    //Steps to kruskals algorithm
    //1. Sort all the edges in order. All the edges in this case are all the possible connections between all the vertexes
    //2. Pick the smallest edge. Check if it forms a cycle with the spanning tree formed so far. If cycle is not formed, include this edge. Else, discard it.
    //3. Repeat until there are V-1 edges (where V is the number of vertices in the given graph).
    // This end condition is modified so that the number of edges left out will result in the specific number of clusters specified with the number of stations to place
    public void kruskalMST(){
        int numCheck = 1;//Counter for the index in the node array that we are currently checking
        ArrayList<Integer> clusterIDs = new ArrayList<>();//An arrayList for holding the ID's of all of the different sets (from findset(i))
        addedEdges = new ArrayList<>();//Arraylist for keeping track of how many edges have been added
        ArrayList<edge> interClusterEdges = new ArrayList<>();//ArrayList for holding the inter-clustering distances
        Arrays.sort(allEdges);//Sorts all the edges for kruskals as it is greedy and checks the edges from the smallest first
        addEdge(allEdges[0]);//Add the first edge to the MST
        //The main execution of kruskals algorithm to find the MST
        while (addedEdges.size()<allHotSpots.length-(1+(clustersToFind-1))){//kruskals continues until the number of different clusters for the stations have been found
            if (!makesCycle(allEdges[numCheck])){//if an edge does not make a cycle then add it to the MST
                addEdge(allEdges[numCheck]);//Adds the edge to the MST
            }
            numCheck++;//increment the index to check the next edge in the array
        }

        for (int i = 0; i < clustersToFind-1; i++) {//finds all of the edges between the clusters. All the inter-clustering distances
            for (edge e:allEdges){
                if (!makesCycle(e))
                    interClusterEdges.add(e);
                numCheck++;
            }
        }

        //get all of the hotspot nodes and work out all the different set ID's there are
        clusterIDs.add(sets.findSet(allNodes[0]).getDataID());
        for (sNode s:allNodes){
            Integer i = sets.findSet(s).getDataID();
            if (!clusterIDs.contains(i)){
                clusterIDs.add(i);
            }
        }
        //A double ArrayList with multiple arrayLists for each cluster. Each cluster ArrayList holds all the nodes belonging to that cluster
        ArrayList<ArrayList<sNode>> allClusterNodes = new ArrayList<>();

        for (int i = 0; i < clusterIDs.size(); i++) {//Sort all the nodes into their respective clusters
            ArrayList<sNode> singleCluster = new ArrayList<>();
            for (sNode s:allNodes){
                if (sets.findSet(s).getDataID()==clusterIDs.get(i)){
                    singleCluster.add(s);
                }
            }
            allClusterNodes.add(singleCluster);
        }
        ArrayList<hotSpot> stations = new ArrayList<>();//List of the locations of the fire stations
        ArrayList<ArrayList<Integer>> HSInStation = new ArrayList<>();//List for the hotspots that each station covers
        for (int i = 0; i < allClusterNodes.size(); i++){//For each cluster find the station location and the nodes that the station covers
            int j = i+1;
            stations.add(getAvgPoint(allClusterNodes.get(i),j));//find the average value for all the points in each hotspot. j is used in naming the hotspot correctly
            ArrayList<Integer> IDList = new ArrayList<>();
            for (sNode s:allClusterNodes.get(i)){
                IDList.add(s.getDataID());
            }
            HSInStation.add(IDList);
        }
        for (int i = 0; i < stations.size(); i++) {//Outputting the station locations and the hotSpots that each station covers
            System.out.println("Station " + stations.get(i).getID() + ":\nCoordinates: " + stations.get(i));
            System.out.print("Hotspots: {");
            for (int n = 0;n<HSInStation.get(i).size();n++){
                if (n==HSInStation.get(i).size()-1)
                    System.out.print(HSInStation.get(i).get(n) + "}\n\n");
                else
                    System.out.print(HSInStation.get(i).get(n) + ",");
            }
        }
        interClusterEdges.sort(edge::compareTo);//Sorts all the interClustering distances to find the shortest
        System.out.println("Inter-clustering distance: " + String.format("%.02f",interClusterEdges.get(0).getLength()));//Outputs the shortest inter-clustering distance

    }

    public void addEdge(edge adding){//Gets the nodes that correspond to each end of the edge and uonion's them, building the MST that kruskal's constructs
        addedEdges.add(adding);//Adds the edge to the list of added edges
        int indexN1 = adding.getEnd1().getID(), indexN2 = adding.getEnd2().getID();
        sNode end1 = allNodes[indexN1-1];
        sNode end2 = allNodes[indexN2-1];
        sets.union(end1,end2);
    }
    //Checks if an edge will create a cycle by checking if the ends belong to the same set. If there are in the same set then it means that they are already connected through
    // another path and so the edge will create a cycle
    public boolean makesCycle(edge checking){
        int indexN1 = checking.getEnd1().getID(), indexN2 = checking.getEnd2().getID();
        sNode end1 = allNodes[indexN1-1];
        sNode end2 = allNodes[indexN2-1];
        //If the two hotSpots are in the same set/tree then adding the edge with ends of those two hotspots will create a cycle
        if (sets.findSet(end1).getDataID()==sets.findSet(end2).getDataID())
            return true;
         else
            return false;

    }

    public hotSpot getAvgPoint(ArrayList<sNode> clusterNodes, int stationNum){//Get the average X and Y points of all the hotspots in a cluster
        double avgX = 0, avgY = 0, numNodes = clusterNodes.size();
        for (sNode s:clusterNodes){
            avgX += s.getDataX();
            avgY += s.getDataY();
        }
        DecimalFormat df = new DecimalFormat("#.##");
        avgX = Double.parseDouble(df.format(avgX/numNodes));//round the values to 2 DP
        avgY = Double.parseDouble(df.format(avgY/numNodes));
        return new hotSpot(stationNum, avgX, avgY);
    }
}
