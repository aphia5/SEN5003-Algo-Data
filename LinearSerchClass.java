import java.util.ArrayList;

public class LinearSearchClass {

    public static String linearSearchFT(ArrayList<String> list, String FromTo, String From, String Date) {
        boolean found = false;
        for (int i = 0; i < list.size(); i++) {
            String val = list.get(i);
            if (val.startsWith(FromTo)) {
                System.out.println(val + " is available");
                found = true;
            }
        }
        if (!found){
            System.out.println(FromTo + " is not available");
            linearSearchF(list, From, Date);
        }
        return null;
    }

    public static String linearSearchF(ArrayList<String> list, String From, String Date) {
        boolean found = false;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).startsWith(From)) {
                System.out.println(list.get(i) + " is available");
                found = true;
            }
        }
        if (!found){
            System.out.println("No flights from " + From + " are available");
            linearSearchD(list, Date);
        }
        return null;
    }

    public static String linearSearchD(ArrayList<String> list, String Date) {
        boolean found = false;
        System.out.println("Available flights on " + Date + " are:");
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).endsWith(Date)) {
                System.out.println(list.get(i));
                found = true;
            }
        }
        if (!found){
            System.out.println("No flights satisfying user criteria are available");
        }
        return null;
    }
}
