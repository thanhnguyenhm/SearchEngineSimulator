package pa2;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BucketSort {
    public static void sort(int[] A) {
        List[] B = new List[A.length];
        int n = A.length;

        for (int i = 0; i < n; i++)
            B[i] = new ArrayList();

        for (int i = 0; i < n; i++) {
            B[(int) (n * A[i])].add(A[i]);
        }

        for (int i = 0; i < n; i++) {
            InsertionSort.sort(B[i]);
        }
    }

    public static void sortString(ArrayList<String> A) {
        ArrayList<ArrayList<String>> B = new ArrayList<ArrayList<String>>();
        int n = A.size();
        for (int i = 0; i < 27; i++) {
            B.add(new ArrayList<String>());
        }
//        for (int i = 0; i < n; i++) {
//            B[n * Integer.parseInt(A[i])] = null;
//        }


    }
}

//    LinkedList<String>[] B = new LinkedList[26];
//    int n = A.length;
//        for (int i = 0; i < n; i++) {
//        B[i] = new LinkedList<String>();
//        }
//        for (int i = 0; i < n; i++) {
//        B[n * Integer.parseInt(A[i])] =
//        }
