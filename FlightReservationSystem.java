import java.io.*;
import java.util.*;

public class FlightReservationSystem {
    
    // HashMaps for O(1) lookup
    private HashMap<String, Flight> flightsById;
    private HashMap<String, ArrayList<Flight>> flightsByRoute; // Key: "Dep-Dest-Date"
    private HashMap<String, ArrayList<Flight>> flightsByDeparture;
    private HashMap<String, ArrayList<Flight>> flightsByDate;
    private HashMap<String, Passenger> passengersById;
    private HashMap<String, Reservation> reservationsById;
    private HashMap<String, Reservation> reservationsByCompositeKey; // Prevent duplicate bookings

    // ArrayLists for iteration/sorting
    private ArrayList<Flight> allFlights;
    private ArrayList<Passenger> allPassengers;
    private ArrayList<Reservation> allReservations;
    
    private int flightIdCounter = 100;

    public FlightReservationSystem() {
        flightsById = new HashMap<>();
        flightsByRoute = new HashMap<>();
        flightsByDeparture = new HashMap<>();
        flightsByDate = new HashMap<>();
        passengersById = new HashMap<>();
        reservationsById = new HashMap<>();
        reservationsByCompositeKey = new HashMap<>();
        allFlights = new ArrayList<>();
        allPassengers = new ArrayList<>();
        allReservations = new ArrayList<>();
    }
    
    // --- FLIGHT OPERATIONS ---
    public void addFlight(Flight flight) {
        flightsById.put(flight.getFlightId(), flight);
        
        String routeKey = flight.getDepartureCity() + "-" + flight.getDestinationCity() + "-" + flight.getDate();
        flightsByRoute.computeIfAbsent(routeKey, k -> new ArrayList<>()).add(flight);
        flightsByDeparture.computeIfAbsent(flight.getDepartureCity(), k -> new ArrayList<>()).add(flight);
        flightsByDate.computeIfAbsent(flight.getDate(), k -> new ArrayList<>()).add(flight);
        
        allFlights.add(flight);
    }
    
    public ArrayList<Flight> searchFlightsWithFallback(String departure, String destination, String date) {
        // Level 1: Exact route match
        String exactKey = departure.trim() + "-" + destination.trim() + "-" + date.trim();
        if (flightsByRoute.containsKey(exactKey)) return flightsByRoute.get(exactKey);
        
        System.out.println("No exact match. Searching alternative dates...");

        // Level 2: Route match, different date
        ArrayList<Flight> departureFlights = flightsByDeparture.getOrDefault(departure.trim(), new ArrayList<>());
        ArrayList<Flight> results = new ArrayList<>();
        for (Flight f : departureFlights) {
            if (f.getDestinationCity().equalsIgnoreCase(destination)) results.add(f);
        }
        if (!results.isEmpty()) return results;
        
        // Level 3: Departure match only
        if (!departureFlights.isEmpty()) return departureFlights;

        // Level 4: Date match only
        return flightsByDate.getOrDefault(date.trim(), new ArrayList<>());
    }
    
    public ArrayList<Flight> getAllFlightsSorted(Comparator<Flight> comparator) {
        ArrayList<Flight> sorted = new ArrayList<>(allFlights);
        sorted.sort(comparator);
        return sorted;
    }

    // --- BOOKING OPERATIONS ---
    public Reservation bookSeat(Passenger passenger, Flight flight, String seatNumber, Flight.FlightClass flightClass) {
        String compositeKey = passenger.getPassengerId() + "-" + flight.getFlightId();
        if (reservationsByCompositeKey.containsKey(compositeKey)) {
            System.out.println("Error: Passenger already booked on this flight.");
            return null;
        }
        if (!flight.bookSeat()) {
            System.out.println("Error: Flight full.");
            return null;
        }
        
        Reservation res = new Reservation(passenger, flight, seatNumber, flightClass);
        reservationsById.put(res.getReservationId(), res);
        reservationsByCompositeKey.put(compositeKey, res);
        allReservations.add(res);
        
        if (!passengersById.containsKey(passenger.getPassengerId())) {
            passengersById.put(passenger.getPassengerId(), passenger);
            allPassengers.add(passenger);
        }
        System.out.println("Booking successful! " + res);
        return res;
    }
    
    public boolean cancelReservation(String reservationId) {
        Reservation res = reservationsById.get(reservationId);
        if (res != null && res.cancel()) {
            reservationsByCompositeKey.remove(res.getPassenger().getPassengerId() + "-" + res.getFlight().getFlightId());
            System.out.println("Reservation cancelled.");
            return true;
        }
        return false;
    }

    public Reservation searchReservationById(String id) { return reservationsById.get(id); }
    public ArrayList<Reservation> getAllReservations() { return allReservations; }
    
    public ArrayList<Reservation> getAllReservationsSorted(Comparator<Reservation> comparator) {
        ArrayList<Reservation> sorted = new ArrayList<>(allReservations);
        sorted.sort(comparator);
        return sorted;
    }

    public void loadFlightsFromCSV(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    // Generate ID and load
                    addFlight(new Flight("F" + (flightIdCounter++), parts[0], parts[1], parts[2], 150));
                }
            }
            System.out.println("System loaded " + allFlights.size() + " flights.");
        } catch (IOException e) {
            System.err.println("Error loading flights: " + e.getMessage());
        }
    }
    
    public void printStatistics() {
        System.out.println("\n====== System Statistics ======");
        System.out.println("Total Flights: " + allFlights.size());
        System.out.println("Total Reservations: " + allReservations.size());
        System.out.println("Unique Routes: " + flightsByRoute.size());
        System.out.println("===============================");
    }
}
