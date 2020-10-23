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


    private hotSpot[] allHotSpots;
    private sNode[] allNodes;
    private edge[] allEdges;
    private ArrayList<edge> addedEdges;
    private disjointSets sets;
    private int clustersToFind;

    public graph(hotSpot[] hotSpots, int numClusters){
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
                    allEdges[edgeNum] = new edge(allHotSpots[i], allHotSpots[j]);//goes through all the vertices and creates all of the edges exactly once
                    edgeNum++;
                }
            }
        }
        sets = new disjointSets();
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
        int numCheck = 1;
        ArrayList<Integer> clusterIDs = new ArrayList<>();
        addedEdges = new ArrayList<>();
        ArrayList<edge> interClusterEdges = new ArrayList<>();
        Arrays.sort(allEdges);
        addEdge(allEdges[0]);
        while (addedEdges.size()<allHotSpots.length-(1+(clustersToFind-1))){
            if (!makesCycle(allEdges[numCheck])){
                addEdge(allEdges[numCheck]);
            }
            numCheck++;
        }

        for (int i = 0; i < clustersToFind-1; i++) {//finds all of the edges between the clusters
            for (edge e:allEdges){
                if (!makesCycle(e))
                    interClusterEdges.add(e);
                numCheck++;
            }
        }

        //get all of the hotspots and categorise them into groups for each hotspot. Then find the average value for all the points in each hotspot
        clusterIDs.add(sets.findSet(allNodes[0]).getDataID());
        for (sNode s:allNodes){
            Integer i = sets.findSet(s).getDataID();
            if (!clusterIDs.contains(i)){
                clusterIDs.add(i);
            }
        }

        ArrayList<ArrayList<sNode>> allClusterNodes = new ArrayList<>();

        for (int i = 0; i < clusterIDs.size(); i++) {
            ArrayList<sNode> singleCluster = new ArrayList<>();
            for (sNode s:allNodes){
                if (sets.findSet(s).getDataID()==clusterIDs.get(i)){
                    singleCluster.add(s);
                }
            }
            allClusterNodes.add(singleCluster);
        }
        ArrayList<hotSpot> stations = new ArrayList<>();
        ArrayList<ArrayList<Integer>> HSInStation = new ArrayList<>();
        for (int i = 0; i < allClusterNodes.size(); i++){
            int j = i+1;
            stations.add(getAvgPoint(allClusterNodes.get(i),j));
            ArrayList<Integer> IDList = new ArrayList<>();
            for (sNode s:allClusterNodes.get(i)){
                IDList.add(s.getDataID());
            }
            HSInStation.add(IDList);
        }
        for (int i = 0; i < stations.size(); i++) {
            System.out.println("Station " + stations.get(i).getID() + ":\nCoordinates: " + stations.get(i));
            System.out.print("Hotspots: {");
            for (int n = 0;n<HSInStation.get(i).size();n++){
                if (n==HSInStation.get(i).size()-1)
                    System.out.print(HSInStation.get(i).get(n) + "}\n\n");
                else
                    System.out.print(HSInStation.get(i).get(n) + ",");
            }
        }
        interClusterEdges.sort(edge::compareTo);
        System.out.println("Inter-clustering distance: " + String.format("%.02f",interClusterEdges.get(0).getLength()));//Outputs the shortest inter-clustering distance

    }

    public void addEdge(edge adding){
        addedEdges.add(adding);
        int indexN1 = adding.getEnd1().getID(), indexN2 = adding.getEnd2().getID();
        sNode end1 = allNodes[indexN1-1];
        sNode end2 = allNodes[indexN2-1];
        sets.union(end1,end2);
    }

    public boolean makesCycle(edge checking){
        int indexN1 = checking.getEnd1().getID(), indexN2 = checking.getEnd2().getID();
        sNode end1 = allNodes[indexN1-1];
        sNode end2 = allNodes[indexN2-1];
        if (sets.findSet(end1).getDataID()==sets.findSet(end2).getDataID())//If the two hotSpots are in the same set/tree then adding the edge with ends of those two hotspots will create a cycle
            return true;
         else
            return false;

    }

    public hotSpot getAvgPoint(ArrayList<sNode> clusterNodes, int stationNum){
        double avgX = 0, avgY = 0, numNodes = clusterNodes.size();
        for (sNode s:clusterNodes){
            avgX += s.getDataX();
            avgY += s.getDataY();
        }
        DecimalFormat df = new DecimalFormat("#.##");
        avgX = Double.parseDouble(df.format(avgX/numNodes));
        avgY = Double.parseDouble(df.format(avgY/numNodes));
        hotSpot station = new hotSpot(stationNum, avgX, avgY);
        return station;
    }
}
