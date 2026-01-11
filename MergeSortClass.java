import java.util.ArrayList;
import java.util.Arrays;

public class MergeSortClass {
    public static ArrayList<String> mergeSort(ArrayList<String> arraylist){

        //Make Variables
        int length = arraylist.size();
        int mid = arraylist.size()/2;

        //Check For Valid Length
        if(length <=1){
            return arraylist;
        }

        ArrayList<String> leftHalf = new ArrayList<>(arraylist.subList(0, mid)); //SOURCE ARRAY, START, END
        ArrayList<String> rightHalf = new ArrayList<>(arraylist.subList(mid, length)); //SOURCE ARRAY, START, END

        leftHalf = mergeSort(leftHalf);
        rightHalf = mergeSort(rightHalf);

        return merge(leftHalf, rightHalf);
    }

    public static ArrayList<String> merge(ArrayList<String> left, ArrayList<String> right){

        ArrayList<String> merged = new ArrayList<>();
        int i = 0, j = 0; //LEFT, RIGHT, MERGED ITERATOR

        int leftLength = left.size();
        int rightLength = right.size();

        while (i < leftLength && j < rightLength){
            if (left.get(i).compareTo(right.get(j)) <= 0){
                merged.add(left.get(i));
                i++;
            }
            else{
                merged.add(right.get(j));
                j++;
            }
        }

        while (i < leftLength){
            merged.add(left.get(i));
            i++;
        }

        while (j < rightLength){
            merged.add(right.get(j));
            j++;
        }

        return merged;
    }
}
