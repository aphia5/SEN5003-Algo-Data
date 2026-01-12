import java.time.LocalDateTime;
import java.util.Comparator;

public class Reservation {
    private String reservationId;
    private Passenger passenger;
    private Flight flight;
    private String seatNumber;
    private Flight.FlightClass flightClass; // Added field to track class
    private double price;
    private LocalDateTime bookingTime;
    private boolean cancelled;
    
    private static int idCounter = 10000;

    public Reservation(Passenger p, Flight f, String seat, Flight.FlightClass fc) {
        this.reservationId = "R" + (idCounter++);
        this.passenger = p;
        this.flight = f;
        this.seatNumber = seat;
        this.flightClass = fc;
        this.price = fc.getBasePrice();
        this.bookingTime = LocalDateTime.now();
        this.cancelled = false;
    }

    public void setFlightClass(Flight.FlightClass flightClass) {
        this.flightClass = flightClass;
        this.price = flightClass.getBasePrice();
    }

    public boolean cancel() {
        if (!cancelled) {
            cancelled = true;
            flight.cancelSeat();
            return true;
        }
        return false;
    }

    // Getters
    public String getReservationId() { return reservationId; }
    public Passenger getPassenger() { return passenger; }
    public Flight getFlight() { return flight; }
    public String getSeatNumber() { return seatNumber; }
    public Flight.FlightClass getFlightClass() { return flightClass; } // Added Getter
    public double getPrice() { return price; }
    public LocalDateTime getBookingTime() { return bookingTime; }
    
    @Override
    public String toString() {
        String status = cancelled ? "CANCELLED" : "CONFIRMED";
        return String.format("[%s] %s on %s -> %s (%s) Seat: %s Price: $%.2f [%s]",
            reservationId, passenger.getFullName(), flight.getDepartureCity(), 
            flight.getDestinationCity(), flight.getDate(), seatNumber, price, status);
    }
    
    // Comparators
    public static class BookingTimeComparator implements Comparator<Reservation> {
        public int compare(Reservation r1, Reservation r2) {
            return r1.bookingTime.compareTo(r2.bookingTime);
        }
    }
    
    public static class PriceComparator implements Comparator<Reservation> {
        public int compare(Reservation r1, Reservation r2) {
            return Double.compare(r2.price, r1.price);
        }
    }
    
    public static class PassengerNameComparator implements Comparator<Reservation> {
        public int compare(Reservation r1, Reservation r2) {
            return r1.passenger.getFullName().compareToIgnoreCase(r2.passenger.getFullName());
        }
    }
}
