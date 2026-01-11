import java.util.ArrayList;

public class LinearSerchClass {

    public static String linearSearchFT(ArrayList<String> list, String FromTo, String From, String Date) {
        boolean found = false;
        for (int i = 0; i < list.size(); i++) {
            String val = list.get(i);
            if (val.startsWith(FromTo) == true) {
                System.out.println(val + " is avaliable");
                found = true;
            }
        }
        if (found == false){
            System.out.println(FromTo+ " is not avalible");
            linearSearchF(list, From, Date);
        }
        return null;
    }

    public static String linearSearchF(ArrayList<String> list, String From, String Date) {
        boolean found1 = false;
        for (int i = 0; i < list.size(); i++) {
            String val = list.get(i);
            if (val.startsWith(From) == true) {
                System.out.println(val + " is avaliable");
                found1 = true;
            }
        }
        if (found1 == false){
            System.out.println("No flights from " + From + " are avaliable");
            linearSearchD(list, Date);
        }
        return null;
    }

    public static String linearSearchD(ArrayList<String> list, String Date) {
        boolean found2 = false;
        System.out.println("Avalible flights on " + Date + " are:");
        for (int i = 0; i < list.size(); i++) {
            String val = list.get(i);
            if (val.endsWith(Date) == true) {
                System.out.println(val);
                found2 = true;
            }
        }
        if (found2 == false){
            System.out.println("No flights satisfying user critera are avaliable");
        }
        return null;
    }

}
