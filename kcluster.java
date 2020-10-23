/*Author: Timothy Kemmis
Std no. c3329386
File: kcluster.java
Task: COMP2230 research assignment 1
Program description: A program that takes an input file containing all of the data on a certain number of points (hotspots) and then calculates the MST or multiple MST's
    of all of the points and then selects the average positions for each cluster and "places" a temporary fire station there. The number of stations placed depends on the
    input argument denoting how many station locations to find. The goal of the program is to place the stations to cover multiple hotspots in a cluster while maximising the
    inter-cluster distance between the clusters (denoted by the closest distance of two points that belong to different clusters). This is sort of optimised automatically
    with kruskals algorithm as it is greedy and always chooses the edges that are the shortest without creating a cycle. Meaning that if kruskals doesn't find the complete
    MST but instead adds numberOfHotSpots-(1+(clustersToFind-1)) the remaining edges that have not been added to the MST will inherently be the longest.
*/

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class kcluster {

    public static void main(String args[]){
        hotSpot[] HSList = null;
        int numHotSpots = 0, numFireStations = 0;
        try {//try/catch loop that reads from thee input file and initialises all of the hotspots and data used in the rest of the program when everything is done correctly.
            //If there is a common error with file reading/initialisation then it will be detected and an error message will be outputted and the program terminated
            Scanner reader = new Scanner(new FileInputStream(args[0]));
            Scanner testReader = new Scanner(new FileInputStream(args[0]));
            String[] splitInput;
            while (testReader.hasNext()){//Loop to calculate the number of hotspots to create the correct array size
                testReader.next();
                numHotSpots++;
            }
            HSList = new hotSpot[numHotSpots];
            for (int i = 0; i < numHotSpots; i++){
                splitInput = reader.next().split(",");
                //takes the correctly formatted input file and parses the string into all of the data to create a hotspot object
                HSList[i] = new hotSpot(Integer.parseInt(splitInput[0]), Double.parseDouble(splitInput[1]), Double.parseDouble(splitInput[2]));
            }
            if (args.length<2){//Error detection for if there is no number of temp fire-stations
                System.err.print("ERROR: java.lang.ArrayIndexOutOfBoundsException\nNo number for amount of temporary fire stations. Program should be run with the syntax:\n" +
                        "'java kcluster ./input.txt 2'. You are missing the integer '2'. \nThis is the number of temporary fire-station locations to be found in the graph and " +
                        "can be any integer greater than 0.");
                System.exit(1);
            }
            numFireStations = Integer.parseInt(args[1]);
            if (numFireStations<1){//Error detection for if there is no number of temp fire-stations
                System.err.println("ERROR: Negative or zero number of fire-stations ("+numFireStations+")\nThe number of temporary fire-stations must be an integer greater than 0.\n" +
                        "E.g. java kcluster ./input.txt '2' \nwhere 2 is the positive integer denoting the number of temporary fire stations to be placed");
                System.exit(1);
            }

            if (numHotSpots==0){//Error detection for if there is an empty input file
                System.err.println("ERROR: There were no hotspots found in the input file\nThis is most likely this is because the input file specified is empty. " +
                        "A correctly formatted file should have multiple hotspots with one row per hotspot\nHotspot row should be formatted in form: " +
                        "'int ID, double x-coordinate, double y-coordinate' \nE.g.'1,1.0,1.0'");
                System.exit(1);
            }
        } catch (FileNotFoundException e) {//Error detection for if no input file was specified
            System.err.println("ERROR: "+ e + "\nThe input file could not be found. This is either because it was not specified as an input parameter or because you have entered the " +
                    "parameters in the wrong order\nCorrect syntax for running the program is 'java kcluster ./input.txt 2' " +
                    "where ./input.txt is the relative path for the input file and 2 is the number of fire stations being requested");
            System.exit(1);

        } catch (ArrayIndexOutOfBoundsException e){//Error detection for if the input file is empty
            System.err.println("ERROR: "+e+"\nIncorrectly formatted input file. Not enough columns in one or multiple of the rows.\n" +
                    "Row should be formatted in form: 'int ID, double x-coordinate, double y-coordinate' \nE.g.'1,1.0,1.0'");
            System.exit(1);
        } catch (NumberFormatException e){//Error detection for if the numbers are not the correct types in the input file or the int for number of stations
            System.err.println("ERROR: " + e + "\nIncorrectly formatted input number types. Incorrect data types for one or multiple of the number inputs, either in the input file or the number of stations.\n" +
                    "Input file numbers should match the following: 'int ID, double x-coordinate, double y-coordinate' \nE.g.'1,1.0,1.0'\n" +
                    "And the number of stations should be an int (no decimal places)\nE.g. java kcluster ./input.txt '2'");
            System.exit(1);
        } catch (Exception e){//Error detection for all other exceptions
            System.err.println("ERROR: "+e+"\nThere was an error in initialisation and the program could not begin successfully");
            System.exit(1);
        }

        graph graph = new graph(HSList, numFireStations);//A graph object for simulating and computing kruskals algorithm

        System.out.println("Hello and welcome to Kruskal’s Clustering!\nThe weighted graph of hotspots:\n");
        graph.printEdgeLengths();//Outputs all the connection lengths between all hotSpots aka the "weighted graph" as shown in the spec

        System.out.println("\nThere are "+ numHotSpots +" hotspots.");
        System.out.println("You have requested "+ numFireStations + " temporary fire stations.");
        //Computes kruskals algorithm using disjoint sets as the data structure. Then sorts and outputs the statistics i.e. where all the stations were placed which hotspots they cover etc.
        graph.kruskalMST();
        System.out.println("\nThank you for using Kruskal's Clustering. Bye");

    }
}
