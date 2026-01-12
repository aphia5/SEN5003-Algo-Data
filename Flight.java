import java.util.Comparator;

public class Flight {
    private String flightId;
    private String departureCity;
    private String destinationCity;
    private String date;
    private int totalSeats;
    private int availableSeats;
    public enum FlightClass {
        ECONOMY("Economy", 100.0),
        BUSINESS("Business", 250.0),
        FIRST_CLASS("First Class", 500.0);
        
        private final String name;
        private final double basePrice;
        FlightClass(String name, double basePrice) { this.name = name; this.basePrice = basePrice; }
        public String getName() { return name; }
        public double getBasePrice() { return basePrice; }
    }

    public Flight(String flightId, String departure, String destination, String date, int totalSeats) {
        this.flightId = flightId;
        this.departureCity = departure.trim();
        this.destinationCity = destination.trim();
        this.date = date.trim();
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
    }

    public boolean bookSeat() {
        if (availableSeats > 0) {
            availableSeats--;
            return true;
        }
        return false;
    }
    
    public void cancelSeat() {
        if (availableSeats < totalSeats) availableSeats++;
    }

    // Getters
    public String getFlightId() { return flightId; }
    public String getDepartureCity() { return departureCity; }
    public String getDestinationCity() { return destinationCity; }
    public String getDate() { return date; }
    public int getAvailableSeats() { return availableSeats; }
    
    // Helper to format date for comparison (DD-MM-YYYY -> YYYY-MM-DD)
    public String getComparableDate() {
        String[] parts = date.split("-");
        return (parts.length == 3) ? parts[2] + "-" + parts[1] + "-" + parts[0] : date;
    }

    @Override
    public String toString() {
        return String.format("%s: %s -> %s on %s (Seats: %d/%d)", 
            flightId, departureCity, destinationCity, date, availableSeats, totalSeats);
    }

    // --- COMPARATORS (Assignment Requirement) ---
    
    public static class DepartureCityComparator implements Comparator<Flight> {
        public int compare(Flight f1, Flight f2) {
            return f1.departureCity.compareToIgnoreCase(f2.departureCity);
        }
    }
    
    public static class DestinationCityComparator implements Comparator<Flight> {
        public int compare(Flight f1, Flight f2) {
            return f1.destinationCity.compareToIgnoreCase(f2.destinationCity);
        }
    }
    
    public static class DateComparator implements Comparator<Flight> {
        public int compare(Flight f1, Flight f2) {
            return f1.getComparableDate().compareTo(f2.getComparableDate());
        }
    }
    
    // Composite Comparator: Departure -> Destination -> Date
    public static class CompositeDDDComparator implements Comparator<Flight> {
        public int compare(Flight f1, Flight f2) {
            int res = f1.departureCity.compareToIgnoreCase(f2.departureCity);
            if (res != 0) return res;
            res = f1.destinationCity.compareToIgnoreCase(f2.destinationCity);
            if (res != 0) return res;
            return f1.getComparableDate().compareTo(f2.getComparableDate());
        }
    }
    
    public static class AvailableSeatsComparator implements Comparator<Flight> {
        public int compare(Flight f1, Flight f2) {
            return Integer.compare(f2.availableSeats, f1.availableSeats);
        }
    }
}
