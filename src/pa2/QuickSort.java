package pa2;

public class QuickSort {
    /**
     * Quick Sort method: to sort the array in ascending order by recursive calls
     * @param arr: the array of integers need to be sorted
     * @param p: first element of the array
     * @param r: last element of the array
     */
    public static void quickSort(int[] arr, int p, int r) {

        if (p < r) {
            int q = partition(arr, p , r);
            quickSort(arr, p, q - 1);
            quickSort(arr, q + 1, r);
        }
    }

    /**
     * Partition method: to divide the array into three groups: less than pivot, pivot and greater than pivot
     * @param arr: the array of integers need to be sorted
     * @param p: first element in the array
     * @param r: last element in the array
     * @return the position of the pivot in which the array is divided by half
     */
    private static int partition(int[] arr, int p, int r) {
        int x = arr[r];
        int i = p - 1;
        int temp;

        for (int j = p; j <= r - 1; j++) {
            if (arr[j] <= x) {
                i++;
                temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        temp = arr[i + 1];
        arr[i + 1] = arr[r];
        arr[r] = temp;

        return (i + 1);
    }

    /**
     * Quick Sort method: to sort the array in descending order by recursive calls
     * @param arr: the array of integers need to be sorted
     * @param p: first element of the array
     * @param r: last element of the array
     */
    public static void quickSortReverse(int[] arr, int p, int r) {

        if (p < r) {
            int q = partitionReverse(arr, p , r);
            quickSortReverse(arr, p, q - 1);
            quickSortReverse(arr, q + 1, r);
        }
    }

    /**
     * Partition method: to divide the array into three groups: greater than pivot, pivot and less than pivot
     * @param arr: the array of integers need to be sorted
     * @param p: first element in the array
     * @param r: last element in the array
     * @return the position of the pivot in which the array is divided by half
     */
    private static int partitionReverse(int[] arr, int p, int r) {
        int x = arr[r];
        int i = p - 1;
        int temp;

        for (int j = p; j <= r - 1; j++) {
            if (arr[j] >= x) {
                i++;
                temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        temp = arr[i + 1];
        arr[i + 1] = arr[r];
        arr[r] = temp;

        return (i + 1);
    }
}
