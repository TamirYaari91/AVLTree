import java.util.*;

public class measurements_Q2 {

    public static int[] buildRandomArrayUnbounded(int size) {
        int[] arrInteger = new int[size];
        Random rd = new Random(); // creating Random object

        for (int i = 0; i < arrInteger.length; i++) {
//            arrInteger[i] = rd.nextInt(Integer.MAX_VALUE);
            arrInteger[i] = rd.nextInt(100);
        }
        return arrInteger;
    }

    public static int getRandomElementFromArray(int[] arr) {
        int rnd = new Random().nextInt(arr.length);
        return arr[rnd];
    }

    public static void main(String[] args) {
        AVLTree tree = new printableTree();
        int[] randArr = buildRandomArrayUnbounded(10);
        for (int num: randArr) {
            tree.insert(num,String.valueOf(num));
        }
        ((printableTree) tree).printTree();
        System.out.println("---------");
        int splitVal = getRandomElementFromArray(randArr);
        System.out.println("split by = "+splitVal);
        System.out.println("---------");
        AVLTree[] split = tree.split(splitVal);
        AVLTree treeSplit0 = new printableTree();
        AVLTree treeSplit1 = new printableTree();
        treeSplit0.root = split[0].getRoot();
        treeSplit0.min = split[0].min;
        treeSplit0.max = split[0].max;
        treeSplit1.root = split[1].getRoot();
        treeSplit1.min = split[1].min;
        treeSplit1.max = split[1].max;
        ((printableTree) treeSplit0).printTree();
        System.out.println("---------");
        ((printableTree) treeSplit1).printTree();
        System.out.println("---------");
//        System.out.println("joinCosts = "+ AVLTree.joinCosts.toString());


    }


}
