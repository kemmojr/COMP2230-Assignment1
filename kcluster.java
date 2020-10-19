import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class kcluster {

    public void kruskalALG(hotSpot[] allHotSpots, edge[] allEdges){
        //Steps to kruskals algorithm
        //1. Sort all the edges in order. All the edges in this case are all the possible connections between all the vertexes
        //2. Pick the smallest edge. Check if it forms a cycle with the spanning tree formed so far. If cycle is not formed, include this edge. Else, discard it.
        //3. Repeat until there are V-1 edges (where V is the number of vertices in the given graph)


    }

    public static void main(String args[]){
        hotSpot[] HSList = null;
        int numHotSpots = 0, numFireStations = 0;
        try {
            Scanner reader = new Scanner(new FileInputStream(args[0]));
            Scanner testReader = new Scanner(new FileInputStream(args[0]));
            String[] splitInput;
            while (testReader.hasNext()){
                testReader.next();
                numHotSpots++;
            }
            HSList = new hotSpot[numHotSpots];
            for (int i = 0; i < numHotSpots; i++){
                splitInput = reader.next().split(",");
                HSList[i] = new hotSpot(Integer.parseInt(splitInput[0]), Double.parseDouble(splitInput[1]), Double.parseDouble(splitInput[2]));
            }

            if (numHotSpots==0){
                System.err.println("ERROR: There were no hotspots found in the input file\nThis is most likely this is because the input file specified is empty. A correctly formatted file should have multiple hotspots with one row per hotspot\nHotspot row should be formatted in form: 'int ID, double x-coordinate, double y-coordinate' \nE.g.'1,1.0,1.0'");
                System.exit(1);
            }
        } catch (FileNotFoundException e) {
            System.err.println("ERROR: "+ e + "\nThe input file could not be found. This is either because it was not specified as an input parameter or because you have entered the parameters in the wrong order\nCorrect syntax for running the program is 'java kcluster ./input.txt 2' where ./input.txt is the relative path for the input file and 2 is the number of fire stations being requested");
            System.exit(1);
            //e.printStackTrace();

        } catch (ArrayIndexOutOfBoundsException e){
            System.err.println("ERROR: java.lang.ArrayIndexOutOfBoundsException\nIncorrectly formatted input file. Not enough columns in one or multiple of the rows.\nRow should be formatted in form: 'int ID, double x-coordinate, double y-coordinate' \nE.g.'1,1.0,1.0'");
            System.exit(1);
        } catch (NumberFormatException e){
            System.err.println("ERROR: " + e + "\nIncorrectly formatted input file. Incorrect data types for ID integers in the input file.\nInput file numbers should match the following: 'int ID, double x-coordinate, double y-coordinate' \nE.g.'1,1.0,1.0'");
            System.exit(1);
        }

//        System.out.println("Hot spots: ");
        double[][] graph = new double[numHotSpots][numHotSpots];//weighted graph to print as in the spec
        edge[] edges = new edge[(5*5)-5];//An array of all the edges
        DecimalFormat df = new DecimalFormat("#.##");
        int edgeNum = 0;

        for (int i = 0; i < numHotSpots; i++) {//creates the weighted graph of the distances between all the points
            for (int j = 0; j < numHotSpots; j++) {
                if (i!=j){
                    edges[edgeNum] = new edge(HSList[i],HSList[j]);
                    graph[i][j] = edges[edgeNum].getLength();
                    edgeNum++;
                } else {
                    graph[i][j] = 0.0;
                }


            }
        }

        System.out.println("Hello and welcome to Kruskal’s Clustering!\nThe weighted graph of hotspots:\n");
        for (double[] aD: graph){
            for (double d: aD){
                System.out.print(String.format("%.02f",d) + " ");
            }
            System.out.println();
        }
        System.out.println("\nThere are "+ numHotSpots +" hotspots.");
        System.out.println("You have requested "+ args[1] + " temporary fire stations.");


    }
}
