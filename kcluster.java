import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class kcluster {

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

        graph graph = new graph(HSList);

        System.out.println("Hello and welcome to Kruskal’s Clustering!\nThe weighted graph of hotspots:\n");
        graph.printEdgeLengths();//Outputs all the connection lengths between all hotSpots

        System.out.println("\nThere are "+ numHotSpots +" hotspots.");
        System.out.println("You have requested "+ args[1] + " temporary fire stations.");
        graph.kruskalMST();

    }
}
