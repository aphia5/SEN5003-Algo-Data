import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
//CSV for to and from flights
//CSV for passenger
//Seoul, London, 13-11-2026

public class Main {
    public static void main(String[] args) {

        // Adds Flights from CSV to ArrayList
        String file = "src/Flights_CSV(Sheet1).csv";
        ArrayList<String> flights = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            String line = "";
            while ((line = reader.readLine()) != null){
                flights.add(line);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        //Sort Flights
        ArrayList<String> list = new ArrayList<>(flights);
        ArrayList<String> sorted = MergeSortClass.mergeSort(list);

        System.out.println("Sorted");
        for (String flight : sorted) {
            System.out.println(flight);
        }

        //Search Algorithms in Week 5
        //Chosen Search Method: Binary Search
        // Data Input
        Scanner input = new Scanner(System.in);

        System.out.println("Enter From City");
        String from = input.nextLine();

        System.out.println("Enter To City");
        String to = input.nextLine();

        System.out.println("Enter Date In DD-MM-YYYY Format");
        String date = input.nextLine();



        String FromTo = from+", "+to;
        String FromToDate = from+", "+to+", "+date;
        BinarySearchClass.binarySearchFTD(sorted, FromToDate, FromTo, from, date);

        //from to date in binary
        // to ft in linear
        // to f in linear
        // to d in linear
    }
}
