import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class measurements_Q1 {
    private static long insertionSortCounter;

    public static long insertionSort(Integer[] arr) {
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
        long res = insertionSortCounter;
        insertionSortCounter = 0;
        return res;
    }

    public static Integer[] buildRandomArray(int size) {
        Integer[] arrInteger = new Integer[size];

        for (int i = 1; i < arrInteger.length + 1; i++) {
            arrInteger[i - 1] = i;
        }
        List<Integer> intList = Arrays.asList(arrInteger);
        Collections.shuffle(intList);
        intList.toArray(arrInteger);
        return arrInteger;
    }

    public static Integer[] buildDescArray(int size) {
        Integer[] arrInteger = new Integer[size];
        for (int i = 0; i < arrInteger.length; i++) {
            arrInteger[i] = arrInteger.length - i;
        }
        return arrInteger;
    }


    public static int AVLInsertionCosts(Integer[] arr) {
        AVLTree tree = new AVLTree();
//        AVLTree tree = new printableTree();
        int[] searchCosts = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            int cost = tree.insert(arr[i], String.valueOf(arr[i]), true);
//            ((printableTree) tree).printTree();
//            System.out.println("----------------");
            searchCosts[i] = cost;
        }
//        System.out.println(Arrays.toString(searchCosts));
        return Arrays.stream(searchCosts).sum();
    }

    public static void main(String[] args) {
        Integer[] randArr = buildRandomArray(100000);
        Integer[] randArrCopy = randArr.clone();
        long insertionSortCost = insertionSort(randArr);
        int AVLCost = AVLInsertionCosts(randArrCopy);
        System.out.println("insertionSort Cost = " + insertionSortCost);
        System.out.println("AVL Cost = " + AVLCost);

    }
}
