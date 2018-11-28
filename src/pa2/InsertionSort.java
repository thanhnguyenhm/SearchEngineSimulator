package pa2;

import java.util.List;

public class InsertionSort {
    public static int sort(List arr) {
        int i, j, key;
        int count = 0;

        for(j = 1; j < arr.size(); j++) {
            key = (Integer) arr.get(j);
            i = j - 1;
            while(i >= 0 && (Integer) arr.get(i) > key) {
                arr.set(i + 1, arr.get(i));
                i = i - 1;
                count++;
            }
            arr.set(i + 1, key);
        }
        return count;
    }
}
