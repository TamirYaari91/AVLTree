import java.util.Arrays;
import java.util.Collections;
import java.util.List;



public class measurements {
    private static int insertionSortCounter;

    public static int insertionSort(Integer[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            int key = arr[i];
            int j = i - 1;

            /* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j = j - 1;
                insertionSortCounter++;
            }
            arr[j + 1] = key;
        }
        int res = insertionSortCounter;
        insertionSortCounter = 0;
        return res;
    }

    public static Integer[] buildRandomArray(int size) {
        Integer[] arrInteger = new Integer[size*10000];
        for (int i = 0; i < arrInteger.length; i++) {
            arrInteger[i] = i;
        }
        List<Integer> intList = Arrays.asList(arrInteger);
        Collections.shuffle(intList);
        intList.toArray(arrInteger);
        System.out.println(Arrays.toString(arrInteger));
        return arrInteger;
    }

    public static int AVLInsertionCosts (Integer[] arr) {
        AVLTree tree = new AVLTree();
        int[] searchCosts = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            int cost = tree.insert(i,String.valueOf(i));
            searchCosts[i] = cost;
        }
        System.out.println(Arrays.toString(searchCosts));
        return Arrays.stream(searchCosts).sum();
    }

    public static void main(String[] args) {
        Integer[] randArr = buildRandomArray(1);
        int insertionSortCost = insertionSort(randArr);
        int AVLCost = AVLInsertionCosts(randArr);
        System.out.println("insertionSort Cost = "+insertionSortCost);
        System.out.println("AVL Cost = "+AVLCost);

    }
}
