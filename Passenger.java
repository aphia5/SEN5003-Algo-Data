public class Passenger {
    private String passengerId;
    private String firstName;
    private String lastName;
    private String email;
    
    private static int idCounter = 1000;

    public Passenger(String firstName, String lastName, String email) {
        this.passengerId = "P" + (idCounter++);
        this.firstName = firstName.trim();
        this.lastName = lastName.trim();
        this.email = email.trim();
    }

    public String getPassengerId() { return passengerId; }
    public String getFullName() { return firstName + " " + lastName; }
    public String getEmail() { return email; }
    
    @Override
    public String toString() { return getFullName() + " (" + email + ")"; }
}
