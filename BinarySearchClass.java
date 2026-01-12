import java.util.ArrayList;

public class BinarySearchClass {
    public static String binarySearchFTD(ArrayList<String> list, String FromToDate, String FromTo, String From, String Date) {
        int start = 0;
        int end = list.size() - 1;

        while (start <= end) {
            int mid = (start + end) / 2;
            String midVal = list.get(mid);

            if (FromToDate.compareTo(midVal) == 0) {
                System.out.println(midVal + " is available");
                return midVal;
            }
            if (FromToDate.compareTo(midVal) < 0) {
                end = mid - 1;
            }
            if (FromToDate.compareTo(midVal) > 0) {
                start = mid + 1;
            }
        }
        System.out.println(FromToDate + " is not available");
        LinearSearchClass.linearSearchFT(list, FromTo, From, Date);
        return null;
    }
}
