import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) {
        System.out.println("====== EDD Airlines Flight Reservation System ======\n");

        // 1. Setup Legacy Data
        String file = "src/Flights_CSV(Sheet1).csv";
        ArrayList<String> flights = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                flights.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Sort using Group's Merge Sort
        ArrayList<String> sorted = MergeSortClass.mergeSort(flights);

        // 2. Setup New System (HashMap & OOP Implementation)
        FlightReservationSystem frs = new FlightReservationSystem();
        frs.loadFlightsFromCSV(file);
        
        // 3. Interactive Menu
        try (Scanner input = new Scanner(System.in)) {
            boolean running = true;
            while (running) {
                printMenu();
                System.out.print("Enter choice: ");
                String choice = input.nextLine();
                
                switch (choice) {
                    case "1":
                        searchFlightOriginal(sorted, input);
                        break;
                    case "2":
                        searchFlightHashMap(frs, input);
                        break;
                    case "3":
                        SeatBookingClass.bookSeatInteractive(frs, input);
                        break;
                    case "4":
                        SeatBookingClass.cancelReservationInteractive(frs, input);
                        break;
                    case "5":
                        SeatBookingClass.viewReservation(frs, input);
                        break;
                    case "6":
                        SeatBookingClass.upgradeSeat(frs, input);
                        break;
                    case "7":
                        viewFlightsSorted(frs, input);
                        break;
                    case "8":
                        viewReservationsSorted(frs, input);
                        break;
                    case "9":
                        frs.printStatistics();
                        break;
                    case "0":
                        running = false;
                        System.out.println("Thank you for using EDD Airlines. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        }
    }
    
    private static void printMenu() {
        System.out.println("\n========== MAIN MENU ==========");
        System.out.println("1. Search Flights (Binary/Linear Search)");
        System.out.println("2. Search Flights (HashMap - O(1) Lookup)");
        System.out.println("3. Book a Seat");
        System.out.println("4. Cancel Reservation");
        System.out.println("5. View Reservation");
        System.out.println("6. Upgrade Seat Class");
        System.out.println("7. View Flights (Sorted)");
        System.out.println("8. View Reservations (Sorted)");
        System.out.println("9. System Statistics");
        System.out.println("0. Exit");
        System.out.println("================================");
    }
    
    private static void searchFlightOriginal(ArrayList<String> sorted, Scanner input) {
        System.out.println("\n====== Search Flights (Binary/Linear) ======");
        System.out.print("Enter From City: ");
        String from = input.nextLine();
        System.out.print("Enter To City: ");
        String to = input.nextLine();
        System.out.print("Enter Date (DD-MM-YYYY): ");
        String date = input.nextLine();
        
        String fromTo = from + ", " + to;
        String fromToDate = from + ", " + to + ", " + date;
        BinarySearchClass.binarySearchFTD(sorted, fromToDate, fromTo, from, date);
    }
    
    private static void searchFlightHashMap(FlightReservationSystem frs, Scanner input) {
        System.out.println("\n====== Search Flights (HashMap O(1)) ======");
        System.out.print("Enter From City: ");
        String from = input.nextLine();
        System.out.print("Enter To City: ");
        String to = input.nextLine();
        System.out.print("Enter Date (DD-MM-YYYY): ");
        String date = input.nextLine();
        
        var results = frs.searchFlightsWithFallback(from, to, date);
        if (results.isEmpty()) {
            System.out.println("No flights found matching your criteria.");
        } else {
            System.out.println("\nFound " + results.size() + " flight(s):");
            for (Flight flight : results) {
                System.out.println("  - " + flight);
            }
        }
    }
    
    private static void viewFlightsSorted(FlightReservationSystem frs, Scanner input) {
        System.out.println("\n====== View Flights (Sorted) ======");
        System.out.println("Sort by: 1. Departure 2. Destination 3. Date 4. Composite (All) 5. Availability");
        System.out.print("Enter choice: ");
        
        try {
            int choice = Integer.parseInt(input.nextLine());
            ArrayList<Flight> sorted;
            switch (choice) {
                case 1: sorted = frs.getAllFlightsSorted(new Flight.DepartureCityComparator()); break;
                case 2: sorted = frs.getAllFlightsSorted(new Flight.DestinationCityComparator()); break;
                case 3: sorted = frs.getAllFlightsSorted(new Flight.DateComparator()); break;
                case 4: sorted = frs.getAllFlightsSorted(new Flight.CompositeDDDComparator()); break;
                case 5: sorted = frs.getAllFlightsSorted(new Flight.AvailableSeatsComparator()); break;
                default: System.out.println("Invalid choice."); return;
            }
            // Display first 20 results to avoid console spam
            int count = 0;
            for (Flight flight : sorted) {
                System.out.println("  " + flight);
                if (++count >= 20) {
                    System.out.println("  ... and " + (sorted.size() - 20) + " more");
                    break;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }
    
    private static void viewReservationsSorted(FlightReservationSystem frs, Scanner input) {
        System.out.println("\n====== View Reservations (Sorted) ======");
        if (frs.getAllReservations().isEmpty()) {
            System.out.println("No reservations in the system.");
            return;
        }
        System.out.println("Sort by: 1. Booking Time 2. Price (High-Low) 3. Passenger Name");
        System.out.print("Enter choice: ");
        try {
            int choice = Integer.parseInt(input.nextLine());
            ArrayList<Reservation> sorted;
            switch (choice) {
                case 1: sorted = frs.getAllReservationsSorted(new Reservation.BookingTimeComparator()); break;
                case 2: sorted = frs.getAllReservationsSorted(new Reservation.PriceComparator()); break;
                case 3: sorted = frs.getAllReservationsSorted(new Reservation.PassengerNameComparator()); break;
                default: System.out.println("Invalid choice."); return;
            }
            for (Reservation res : sorted) System.out.println("  " + res);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }
}
