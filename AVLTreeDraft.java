
/**
 * AVLTree
 * <p>
 * An implementation of a AVL Tree with
 * distinct integer keys and info
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AVLTreeDraft {

    IAVLNode root;
    IAVLNode min;
    int size;

    public AVLTreeDraft() {
        root = null;
        min = null;
        size = 0;
    }

    /**
     * public boolean empty()
     * <p>
     * returns true if and only if the tree is empty
     */
    public boolean empty() {
        return root == null;// to be replaced by student code
    }

    /**
     * public String search(int k)
     * <p>
     * returns the info of an item with key k if it exists in the tree
     * otherwise, returns null
     */
    public String search(int k) {
        if (empty()) {
            return null;
        }
        IAVLNode root = getRoot();
        return ((AVLNode) root).searchNode(k).getValue();
    }

    /**
     * public int insert(int k, String i)
     * <p>
     * inserts an item with key k and info i to the AVL tree.
     * the tree must remain valid (keep its invariants).
     * returns the number of rebalancing operations, or 0 if no rebalancing operations were necessary.
     * promotion/rotation - counted as one rebalnce operation, double-rotation is counted as 2.
     * returns -1 if an item with key k already exists in the tree.
     */
    public int insert(int k, String i) {
        IAVLNode node = new AVLNode(k, i);
        if (root == null) {
            root = node;
            min = node;
            root.setHeight(0);
            return 0;
        }
        if (node.getKey() < min.getKey()) {
            min = node;
        }
        IAVLNode root = getRoot();
        while (node.getParent() == null) {
            if (node.getKey() < root.getKey()) {
                if (!root.getLeft().isRealNode()) {
                    root.setLeft(node);
                    ((AVLNode) node).rebalancing();
                    return 42;
                }
                root = root.getLeft();
            } else {
                if (!root.getRight().isRealNode()) {
                    root.setRight(node);
                    ((AVLNode) node).rebalancing();
                    return 42;
                }
                root = root.getRight();
            }
        }
        return 42;    // to be replaced by student code
    }

    /**
     * public int delete(int k)
     * <p>
     * deletes an item with key k from the binary tree, if it is there;
     * the tree must remain valid (keep its invariants).
     * returns the number of rebalancing operations, or 0 if no rebalancing operations were needed.
     * demotion/rotation - counted as one rebalnce operation, double-rotation is counted as 2.
     * returns -1 if an item with key k was not found in the tree.
     */
    public int delete(int k) {
        return 42;    // to be replaced by student code
    }

    /**
     * public String min()
     * <p>
     * Returns the info of the item with the smallest key in the tree,
     * or null if the tree is empty
     */
//    public String min() {
//        if (empty()) {
//            return null;
//        }
//        IAVLNode root = getRoot();
//        IAVLNode minNode = ((AVLNode) root).nodeMin();
//        return minNode.getValue(); // to be replaced by student code
//    }
    public String min() {
        if (min == null) {
            return null;
        }
        return min.getValue();
    }

    public IAVLNode getNewMin() {
        IAVLNode root = getRoot();
        IAVLNode minNode = ((AVLNode) root).nodeMin();
        return minNode;
    }

    /**
     * public String max()
     * <p>
     * Returns the info of the item with the largest key in the tree,
     * or null if the tree is empty
     */
    public String max() {
        if (empty()) {
            return null;
        }
        IAVLNode root = getRoot();
        IAVLNode maxNode = ((AVLNode) root).nodeMax();
        return maxNode.getValue(); // to be replaced by student code
    }

    /**
     * public int[] keysToArray()
     * <p>
     * Returns a sorted array which contains all keys in the tree,
     * or an empty array if the tree is empty.
     */
    public int[] keysToArray() {
        int[] arr = new int[size()]; // to be replaced by student code
        if (arr.length == 0) {
            return arr;
        }
        IAVLNode root = getRoot();
        IAVLNode minNode = ((AVLNode) root).nodeMin();
        arr[0] = minNode.getKey();
        for (int i = 1; i < arr.length; i++) {
            minNode = ((AVLNode) minNode).Successor();
            arr[i] = minNode.getKey();
        }
        return arr;              // to be replaced by student code
    }

    /**
     * public String[] infoToArray()
     * <p>
     * Returns an array which contains all info in the tree,
     * sorted by their respective keys,
     * or an empty array if the tree is empty.
     */
    public String[] infoToArray() {
        String[] arr = new String[size()]; // to be replaced by student code
        if (arr.length == 0) {
            return arr;
        }
        IAVLNode root = getRoot();
        IAVLNode minNode = ((AVLNode) root).nodeMin();
        arr[0] = minNode.getValue();
        for (int i = 1; i < arr.length; i++) {
            minNode = ((AVLNode) minNode).Successor();
            arr[i] = minNode.getValue();
        }
        return arr;
    }

    /**
     * public int size()
     * <p>
     * Returns the number of nodes in the tree.
     * <p>
     * precondition: none
     * postcondition: none
     */
    public int size() {
        IAVLNode root = getRoot();
        return ((AVLNode) root).sizeNode(); // to be replaced by student code
        // return 1 + ((AVLNode) root).left.size + ((AVLNode) root).right.size; // to be replaced by student code
    }

    /**
     * public int getRoot()
     * <p>
     * Returns the root AVL node, or null if the tree is empty
     * <p>
     * precondition: none
     * postcondition: none
     */
    public IAVLNode getRoot() {
        if (empty()) {
            return null;
        }
        return root;
    }

    /**
     * public string split(int x)
     * <p>
     * splits the tree into 2 trees according to the key x.
     * Returns an array [t1, t2] with two AVL trees. keys(t1) < x < keys(t2).
     * precondition: search(x) != null (i.e. you can also assume that the tree is not empty)
     * postcondition: none
     */
    public AVLTreeDraft[] split(int x) {
        return null;
    }

    /**
     * public join(IAVLNode x, AVLTree t)
     * <p>
     * joins t and x with the tree.
     * Returns the complexity of the operation (|tree.rank - t.rank| + 1).
     * precondition: keys(x,t) < keys() or keys(x,t) > keys(). t/tree might be empty (rank = -1).
     * postcondition: none
     */
    public int join(IAVLNode x, AVLTreeDraft t) {
        return 0;
    }


    /**
     * public interface IAVLNode
     * ! Do not delete or modify this - otherwise all tests will fail !
     */
    public interface IAVLNode {
        public int getKey(); //returns node's key (for virtual node return -1)

        public String getValue(); //returns node's value [info] (for virtual node return null)

        public void setLeft(IAVLNode node); //sets left child

        public IAVLNode getLeft(); //returns left child (if there is no left child return null)

        public void setRight(IAVLNode node); //sets right child

        public IAVLNode getRight(); //returns right child (if there is no right child return null)

        public void setParent(IAVLNode node); //sets parent

        public IAVLNode getParent(); //returns the parent (if there is no parent return null)

        public boolean isRealNode(); // Returns True if this is a non-virtual AVL node

        public void setHeight(int height); // sets the height of the node

        public int getHeight(); // Returns the height of the node (-1 for virtual nodes)
    }

    /**
     * public class AVLNode
     * <p>
     * If you wish to implement classes other than AVLTree
     * (for example AVLNode), do it in this file, not in
     * another file.
     * This class can and must be modified.
     * (It must implement IAVLNode)
     */
    public class AVLNode implements IAVLNode {

        String info;
        int key = -1;
        AVLNode left = null;
        AVLNode right = null;
        AVLNode parent = null;
        int height = -1;
        int size = 0;

        public AVLNode() {
        } //for virtual nodes

        public AVLNode(int k, String i) { // for regular nodes
            key = k;
            info = i;
            left = new AVLNode();
            right = new AVLNode();
            size = 1;
            height = 0;

        }

        public int getKey() {
            return key;
        }

        public String getValue() {
            return info;
        }

        public void setLeft(IAVLNode node) {
            left = (AVLNode) node;
            left.setParent(this);
            //           size += left.size;
//            int heightLeft = left.getHeight();
//            int heightRight = right.getHeight();
//            setHeight(Math.max(heightLeft, heightRight) + 1);

        }

        public void rotateRight() {
//            if ((getParent() != null) && (getParent().getParent() != null)) {}
            IAVLNode parent = getParent();
            IAVLNode grandpa = getParent().getParent();
            IAVLNode rightChild = getRight();

            setRight(parent);
            if (getRoot().getKey() == parent.getKey()) {
                root = this;
            } else {
                setParent(grandpa);
                if (parent.getKey() == grandpa.getLeft().getKey()) { // if parent is left child
                    grandpa.setLeft(this);
                } else { // parent is a right child
                    grandpa.setRight(this);
                }
            }
            parent.setLeft(rightChild);

        }

        public void rotateLeft() {
//            if ((getParent() != null) && (getParent().getParent() != null)) {}
            IAVLNode parent = getParent();
            IAVLNode grandpa = getParent().getParent();
            IAVLNode leftChild = getLeft();

            setLeft(parent);
            if (getRoot().getKey() == parent.getKey()) {
                root = this;
            } else {
                setParent(grandpa);
                if (parent.getKey() == grandpa.getLeft().getKey()) { // if parent is left child
                    grandpa.setLeft(this);
                } else { // parent is a right child
                    grandpa.setRight(this);
                }
            }
            parent.setRight(leftChild);

        }
        public void demote() {
            int rank = getHeight();
            setHeight(rank-1);
        }
        public void promote() {
            int rank = getSize();
            setSize(rank+1);
        }
        public void demoteSize() {
            int rank = getSize();
            setSize(rank-1);
        }
        public void promoteSize() {
            int rank = getHeight();
            setHeight(rank+1);
        }
        public void rotateLeftRight() {
            rotateLeft();
            rotateRight();
        }

        public void rotateRightLeft() {
            rotateRight();
            rotateLeft();
        }

        //        public int whatCase() {
//            if (getRoot() == this) {
//                return 23;
//            }
//            IAVLNode parent = getParent();
//            IAVLNode sibling;
//            if (parent.getLeft() == this) {
//                sibling = getParent().getRight();
//            }
//            else {
//                sibling = getParent().getLeft();
//            }
////            System.out.println("parent - sibling" + (parent.getHeight() - sibling.getHeight()));
//////            System.out.println("parent" + parent.getHeight());
//////            System.out.println("this"+this.getHeight());
////            System.out.println("parent - this" +(parent.getHeight() - this.getHeight()));
//            if ((parent.getHeight() - this.getHeight() == 1)
//                    && (parent.getHeight() - sibling.getHeight() == 1 )) {
//                return 0;
//            }
//            if ((parent.getHeight() - this.getHeight() == 0)
//                    && (parent.getHeight() - sibling.getHeight() == 1 )) {
//                return 1;
//            }
//            IAVLNode childLeft = getLeft();
//            IAVLNode childRight = getRight();
//            if ((parent.getHeight() - this.getHeight() == 0)
//                    && (parent.getHeight() - sibling.getHeight() == 2 )) {
//                if ((this.getHeight() - childLeft.getHeight() == 1)
//                        && (this.getHeight() - childRight.getHeight() == 2)) {
//                    return 2;
//                }
//                if ((this.getHeight() - childLeft.getHeight() == 2)
//                        && (this.getHeight() - childRight.getHeight() == 1)) {
//                    return 3;
//                }
//            }
//            return 554;
//        }
        public int whatCase() {
            if (getRoot() == this) {
                return 23;
            }
            IAVLNode parent = getParent();
            IAVLNode sibling;
            if (parent.getLeft() == this) {
                sibling = getParent().getRight();
                if ((parent.getHeight() - this.getHeight() == 1)
                        && (parent.getHeight() - sibling.getHeight() == 1 )) {
                    return 0;
                }
                if ((parent.getHeight() - this.getHeight() == 0)
                        && (parent.getHeight() - sibling.getHeight() == 1 )) {
                    return 1;
                }
                IAVLNode childLeft = getLeft();
                IAVLNode childRight = getRight();
                if ((parent.getHeight() - this.getHeight() == 0)
                        && (parent.getHeight() - sibling.getHeight() == 2 )) {
                    if ((this.getHeight() - childLeft.getHeight() == 1)
                            && (this.getHeight() - childRight.getHeight() == 2)) {
                        return 2;
                    }
                    if ((this.getHeight() - childLeft.getHeight() == 2)
                            && (this.getHeight() - childRight.getHeight() == 1)) {
                        return 3;
                    }
                }
            }
            else {
                sibling = getParent().getLeft();
                if ((parent.getHeight() - this.getHeight() == 1)
                        && (parent.getHeight() - sibling.getHeight() == 1 )) {
                    return 0;
                }
                if ((parent.getHeight() - this.getHeight() == 0)
                        && (parent.getHeight() - sibling.getHeight() == 1 )) {
                    return 1;
                }
                IAVLNode childLeft = getLeft();
                IAVLNode childRight = getRight();
                if ((parent.getHeight() - this.getHeight() == 0)
                        && (parent.getHeight() - sibling.getHeight() == 2 )) {
                    if ((this.getHeight() - childLeft.getHeight() == 1)
                            && (this.getHeight() - childRight.getHeight() == 2)) {
                        return 3;
                    }
                    if ((this.getHeight() - childLeft.getHeight() == 2)
                            && (this.getHeight() - childRight.getHeight() == 1)) {
                        return 2;
                    }
                }
            }
            return 554;
        }

        public void rebalancing() {
            IAVLNode node = this;
            if ((((AVLNode) node).whatCase() == 0) || ((getParent() != null) && (getParent().getParent() != null))) {}
            while ((((AVLNode) node).whatCase() == 1) && (node != root)) {
                node = node.getParent();
                ((AVLNode) node).setHeightAfterInsert();
                ((AVLNode) node).setSizeAfterInsert();
            }
            if ((((AVLNode) node).whatCase() == 2) && (getParent().getLeft() == this)) {
                System.out.println("case 2 L");
                ((AVLNode) node).rotateLeft();
                ((AVLNode) node.getLeft()).demote();
                ((AVLNode) node.getLeft()).demoteSize();
            }
            if ((((AVLNode) node).whatCase() == 2) && (getParent().getRight() == this)) {
                System.out.println("case 2 R");
                ((AVLNode) node).rotateRight();
                ((AVLNode) node.getRight()).demote();
                ((AVLNode) node.getRight()).demoteSize();
            }
            if ((((AVLNode) node).whatCase() == 3) && (getParent().getLeft() == this)) {
                System.out.println("case 3 LR");
                ((AVLNode) node.getRight()).rotateLeftRight();
                ((AVLNode) node).demote();
                ((AVLNode) node).demoteSize();
                ((AVLNode) node.getParent()).promote();
                ((AVLNode) node.getParent()).promoteSize();
                ((AVLNode) node.getParent().getRight()).demote();
                ((AVLNode) node.getParent().getRight()).demoteSize();

            }
            if ((((AVLNode) node).whatCase() == 3) && (getParent().getRight() == this)) {
                System.out.println("case 3 RL");
                ((AVLNode) node.getLeft()).rotateRightLeft();
                ((AVLNode) node).demote();
                ((AVLNode) node).demoteSize();
                ((AVLNode) node.getParent()).promote();
                ((AVLNode) node.getParent()).promoteSize();
                ((AVLNode) node.getParent().getLeft()).demote();
                ((AVLNode) node.getParent().getLeft()).demoteSize();
            }
        }


        public IAVLNode getLeft() {
            return left;
        }

        public void setRight(IAVLNode node) {
            right = (AVLNode) node;
            right.setParent(this);
//            size += right.size;
//            int heightLeft = left.getHeight();
//            int heightRight = right.getHeight();
//            setHeight(Math.max(heightLeft, heightRight) + 1);
        }

        public void setHeightAfterInsert() {
            int heightLeft = left.getHeight();
            int heightRight = right.getHeight();
            setHeight(Math.max(heightLeft, heightRight) + 1);
        }
        public void setSizeAfterInsert() {
            int sizeLeft = left.getSize();
            int sizeRight = right.getSize();
            setSize(sizeLeft + sizeRight + 1);
        }


        public IAVLNode getRight() {
            return right;
        }

        public void setParent(IAVLNode node) {
            parent = (AVLNode) node;
        }

        public IAVLNode getParent() {
            return parent;
        }

        // Returns True if this is a non-virtual AVL node
        public boolean isRealNode() {
            return key != -1;
        }

        public void setHeight(int h) {
            height = h;
        }
        public void setSize(int s) {
            size = s;
        }
        public int getSize() {
            return size;
        }

//        public void setHeightAfterInsert() {
//            IAVLNode node = getParent();
//            if (!getRight().isRealNode() || !node.getLeft().isRealNode()) {
//                while (node != null) {
//                    node.setHeight(Math.max(node.getLeft().getHeight(),node.getRight().getHeight()) + 1);
//                    node = node.getParent();
//                }
//            }
//        }

        public int getHeight() {
            return height;
        }

        public AVLNode nodeMin() {
            AVLNode node = left;
            if (!left.isRealNode()) { //added this
                return this;
            }
            while (node.left.isRealNode()) {
                node = node.left;
            }
            return node;
        }

        public AVLNode nodeMax() {
            AVLNode node = right;
            while (right.isRealNode()) {
                node = node.right;
            }
            return node;
        }

        public AVLNode Successor() {
            AVLNode node = right;
            if (node.isRealNode()) {
                return node.nodeMin();
            }
            AVLNode x = this;
            AVLNode y = x.parent;
            while ((y != null) && (x.key == y.right.key)) { //we used equal on keys and not nodes!!
                x = y;
                y = x.parent;
            }
            return y;
        }

        public AVLNode Predecessor() {
            AVLNode node = left;
            if (node.isRealNode()) {
                return node.nodeMax();
            }
            AVLNode x = this;
            AVLNode y = x.parent;
            while ((y != null) && (x.key == y.key)) { //we used equal on keys and not nodes!!
                x = y;
                y = x.parent;
            }
            return y;
        }

//        public int sizeNode() {
//            if (!isRealNode()) {
//                return 0;
//            } else {
//                return 1 + left.sizeNode() + right.sizeNode();
//            }
//        }

        public int sizeNode() {
            return size;
        }

        public AVLNode searchNode(int k) {
            if (!isRealNode()) {
                return this;
            }
            if (key == k) {
                return this;
            } else {
                if (key > k) {
                    return left.searchNode(k);
                } else {
                    return right.searchNode(k);
                }
            }
        }
    }

    public static void main(String[] args) {
        AVLTreeDraft tree = new AVLTreeDraft();
//        tree.insert(1, "one");
//        tree.insert(2, "two");
//        tree.insert(3, "three");
//        int[] keys = tree.keysToArray();
//        System.out.println(Arrays.toString(keys));
//        System.out.println("root = " + tree.getRoot().getKey() + " ");
//        System.out.println("root.left = " + tree.getRoot().getLeft().getKey());
//        System.out.println("root.right = " + tree.getRoot().getRight().getKey());

        tree.insert(15, "fifteen");
        tree.insert(17, "seventeen");
        tree.insert(10, "ten");
        tree.insert(8, "eight");
        tree.insert(12, "twelve");
        tree.insert(9, "nine");
        //tree.insert(11, "eleven");
        //tree.insert(13, "thirteen");

//        tree.insert(10, "fifteen");
//        tree.insert(9, "seventeen");
//        tree.insert(17, "ten");
//        tree.insert(13, "eight");
//        tree.insert(19, "twelve");
//        //tree.insert(9, "nine");
//        tree.insert(18, "eleven");
//        //tree.insert(13, "thirteen");
//
        int[] keys = tree.keysToArray();
        System.out.println(Arrays.toString(keys));
        System.out.println("root = " + tree.getRoot().getKey()+" height= "+tree.getRoot().getHeight());
        System.out.println("root.left = " + tree.getRoot().getLeft().getKey()+" height= "+tree.getRoot().getLeft().getHeight());
        System.out.println("root.right = " + tree.getRoot().getRight().getKey()+" height= "+tree.getRoot().getRight().getHeight());
        System.out.println("root.left.left = " + tree.getRoot().getLeft().getLeft().getKey()+" height= "+tree.getRoot().getLeft().getLeft().getHeight());
        System.out.println("root.left.right = " + tree.getRoot().getLeft().getRight().getKey()+" height= "+tree.getRoot().getLeft().getRight().getHeight());
        System.out.println("root.right.right = " + tree.getRoot().getRight().getRight().getKey()+" height= "+tree.getRoot().getRight().getRight().getHeight());
        System.out.println("root.right.left = " + tree.getRoot().getRight().getLeft().getKey()+" height= "+tree.getRoot().getRight().getLeft().getHeight());

//        node12.rotateLeftRight();
//        int[] keys_after_rotate = tree.keysToArray();
//        System.out.println(Arrays.toString(keys_after_rotate));
//        System.out.println("root = " + tree.getRoot().getKey());
//        System.out.println("root.left = " + tree.getRoot().getLeft().getKey());
//        System.out.println("root.right = " + tree.getRoot().getRight().getKey());
//        System.out.println("root.left.left = " + tree.getRoot().getLeft().getLeft().getKey());
//        System.out.println("root.left.right = " + tree.getRoot().getLeft().getRight().getKey());



////        tree.insert(50, "fifty");
//        tree.insert(10, "ten");
//        tree.insert(7, "seven");
//        tree.insert(15, "fifteen");
//        tree.insert(23, "twenty-three");
//        tree.insert(39, "thirty-nine");
//        tree.insert(12, "twelve");
//        tree.insert(13, "thirteen");
//
//
//        int[] keys = tree.keysToArray();
//        System.out.println(Arrays.toString(keys));
//        AVLNode node15 = ((AVLNode) tree.getRoot()).searchNode(15);
//        System.out.println("root = " + tree.getRoot().getKey());
//        System.out.println("root.left = " + tree.getRoot().getLeft().getKey());
//        System.out.println("root.right = " + tree.getRoot().getRight().getKey());
//        System.out.println("root.right.left = " + tree.getRoot().getRight().getLeft().getKey());
//        System.out.println("root.right.right = " + tree.getRoot().getRight().getRight().getKey());
//        node15.rotateLeft();
//        int[] keys_after_rotate = tree.keysToArray();
//        System.out.println(Arrays.toString(keys_after_rotate));
//        System.out.println("root = " + tree.getRoot().getKey());
//        System.out.println("root.left = " + tree.getRoot().getLeft().getKey());
//        System.out.println("root.right = " + tree.getRoot().getRight().getKey());
//        System.out.println("root.right.left = " + tree.getRoot().getRight().getLeft().getKey());
//        System.out.println("root.right.right = " + tree.getRoot().getRight().getRight().getKey());
//
//
//
////        tree.insert(4, "four");
////        tree.insert(8, "eight");
////        tree.insert(1, "one");
////        tree.insert(6, "six");
////        tree.insert(2,"two");
//        System.out.println("root = " + tree.getRoot().getKey());
//        System.out.println("root.left = " + tree.getRoot().getLeft().getKey());
//        System.out.println("root.right = " + tree.getRoot().getRight().getKey());
////        System.out.println("root.left.left = " + tree.getRoot().getLeft().getLeft().getKey());
////        System.out.println("root.left.right = " + tree.getRoot().getLeft().getRight().getKey());
//        System.out.println("root.right.left = " + tree.getRoot().getRight().getLeft().getKey());
//        System.out.println("root.right.right = " + tree.getRoot().getRight().getRight().getKey());
////        System.out.println("root.left.left.right = " + tree.getRoot().getLeft().getLeft().getRight().getKey());
////        System.out.println("root.left.right.left = " + tree.getRoot().getLeft().getRight().getLeft().getKey());
////        System.out.println("root.left.right.right = " + tree.getRoot().getLeft().getRight().getRight().getKey());
////        System.out.println("root.right.left = " + tree.getRoot().getRight().getLeft().getKey());
////        System.out.println("root.right.right = " + tree.getRoot().getRight().getRight().getKey());
////        IAVLNode node15 = ((AVLNode) tree.getRoot()).searchNode(15);
////        ((AVLNode) node15).rotateLeft();
////        System.out.println("after rotate:" + System.lineSeparator());
////        System.out.println("root = " + tree.getRoot().getKey());
////        System.out.println("root.left = " + tree.getRoot().getLeft().getKey());
////        System.out.println("root.right = " + tree.getRoot().getRight().getKey());
////        System.out.println("root.left.left = " + tree.getRoot().getLeft().getLeft().getKey());
////        System.out.println("root.left.right = " + tree.getRoot().getLeft().getRight().getKey());
////        System.out.println("root.right.left = " + tree.getRoot().getRight().getLeft().getKey());
////        System.out.println("root.right.right = " + tree.getRoot().getRight().getRight().getKey());
////        System.out.println("root.right.left = " + tree.getRoot().getRight().getLeft().getKey());
////        System.out.println("root.left.right.right = " + tree.getRoot().getLeft().getRight().getRight().getKey());
////        System.out.println("root.right.right = " + tree.getRoot().getRight().getRight().getKey());
//
//
////        IAVLNode node3 = ((AVLNode) tree.getRoot()).searchNode(3);
////        System.out.println("successor of 3 is " + ((AVLNode) node3).Successor().getValue());
////        System.out.println(tree.search(18));
////        System.out.println("successor of 3 is " + ((AVLNode) node3).Successor().getValue());
//

    }

}
