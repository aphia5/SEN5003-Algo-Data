import java.util.Scanner;

public class SeatBookingClass {

    public static void bookSeatInteractive(FlightReservationSystem system, Scanner scanner) {
        System.out.println("\n====== Book a Seat ======");
        System.out.print("Enter First Name: ");
        String fName = scanner.nextLine();
        System.out.print("Enter Last Name: ");
        String lName = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        Passenger p = new Passenger(fName, lName, email);
        
        System.out.print("Departure City: ");
        String dep = scanner.nextLine();
        System.out.print("Destination City: ");
        String dest = scanner.nextLine();
        System.out.print("Date (DD-MM-YYYY): ");
        String date = scanner.nextLine();
        
        var flights = system.searchFlightsWithFallback(dep, dest, date);
        if (flights.isEmpty()) {
            System.out.println("No flights found.");
            return;
        }
        
        System.out.println("\nSelect a flight:");
        for (int i = 0; i < flights.size(); i++) {
            System.out.println((i+1) + ". " + flights.get(i));
        }
        
        try {
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            if (idx < 0 || idx >= flights.size()) return;
            Flight selected = flights.get(idx);
            
            System.out.println("Class: 1. Economy 2. Business 3. First");
            int cls = Integer.parseInt(scanner.nextLine());
            Flight.FlightClass fc = (cls == 3) ? Flight.FlightClass.FIRST_CLASS : 
                                   (cls == 2) ? Flight.FlightClass.BUSINESS : Flight.FlightClass.ECONOMY;
            
            // Auto-generate seat (e.g., 1A)
            String seat = ((int)(Math.random() * 30) + 1) + "" + (char)('A' + (int)(Math.random() * 6));
            
            system.bookSeat(p, selected, seat, fc);
            
        } catch (Exception e) {
            System.out.println("Invalid input.");
        }
    }

    public static void cancelReservationInteractive(FlightReservationSystem system, Scanner scanner) {
        System.out.print("\nEnter Reservation ID: ");
        String id = scanner.nextLine();
        if (system.cancelReservation(id)) {
            System.out.println("Success.");
        } else {
            System.out.println("Failed or not found.");
        }
    }
    
    public static void viewReservation(FlightReservationSystem system, Scanner scanner) {
        System.out.print("\nEnter Reservation ID: ");
        Reservation r = system.searchReservationById(scanner.nextLine());
        if (r != null) System.out.println(r);
        else System.out.println("Not found.");
    }
    
    public static void upgradeSeat(FlightReservationSystem system, Scanner scanner) {
        System.out.println("\n====== Upgrade Seat Class ======");
        System.out.print("Enter Reservation ID: ");
        String id = scanner.nextLine();
        Reservation r = system.searchReservationById(id);
        
        if (r == null) {
            System.out.println("Reservation not found.");
            return;
        }
        
        System.out.println("Current Class: " + r.getFlightClass().getName() + " ($" + r.getPrice() + ")");
        System.out.println("Select New Class: 1. Economy 2. Business 3. First");
        
        try {
            int cls = Integer.parseInt(scanner.nextLine());
            Flight.FlightClass newClass = (cls == 3) ? Flight.FlightClass.FIRST_CLASS : 
                                         (cls == 2) ? Flight.FlightClass.BUSINESS : Flight.FlightClass.ECONOMY;
            
            double diff = newClass.getBasePrice() - r.getPrice();
            
            if (diff == 0) {
                System.out.println("You are already in this class.");
            } else {
                String action = diff > 0 ? "Upgrade cost" : "Downgrade refund";
                System.out.printf("%s: $%.2f. Confirm? (yes/no): ", action, Math.abs(diff));
                
                if (scanner.nextLine().equalsIgnoreCase("yes")) {
                    r.setFlightClass(newClass);
                    System.out.println("Class updated successfully to " + newClass.getName());
                } else {
                    System.out.println("Cancelled.");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }
}
