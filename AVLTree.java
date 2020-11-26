/**
 * AVLTree
 * <p>
 * An implementation of a AVL Tree with
 * distinct integer keys and info
 */

import java.util.ArrayList;
import java.util.List;

public class AVLTree {

    public IAVLNode root;
    public IAVLNode min;

    public AVLTree() {
        root = null;
        min = null;
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
            return 42;
        }
        if (node.getKey() < min.getKey()) {
            min = node;
        }
        IAVLNode root = getRoot();
        while (node.getParent() == null) {
            if (node.getKey() < root.getKey()) {
//                if (root.getLeft() == null) {
                if (!root.getLeft().isRealNode()) {
                    root.setLeft(node);
                    node.setParent(root);
                    ((AVLNode) node).setHeightAfterInsert();
                    return 42;
                }
                root = root.getLeft();
            } else {
//                if (root.getRight() == null) {
                if (!root.getRight().isRealNode()) {

                    root.setRight(node);
                    node.setParent(root);
                    ((AVLNode) node).setHeightAfterInsert();
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
        if (search(k) == null) {
            return -1;
        }
        IAVLNode nodeToDelete = ((AVLNode) root).searchNode(k);
        if (!nodeToDelete.getLeft().isRealNode() && !nodeToDelete.getRight().isRealNode()) { //nodeToDelete is an internal leaf
            IAVLNode externalNode = new AVLNode();
            if (nodeToDelete.getKey() == nodeToDelete.getParent().getLeft().getKey()) { // nodeToDelete is a left child
                nodeToDelete.getParent().setLeft(externalNode);
                if (!nodeToDelete.getParent().getRight().isRealNode()) { // parent is now internal leaf
                    ((AVLNode) nodeToDelete.getParent()).setHeightAfterDelete();
                    nodeToDelete.setParent(null);
                }
            } else { //nodeToDelete is a right child
                nodeToDelete.getParent().setRight(externalNode);
                if (!nodeToDelete.getParent().getLeft().isRealNode()) { // parent is now internal leaf
                    ((AVLNode) nodeToDelete.getParent()).setHeightAfterDelete();
                    nodeToDelete.setParent(null);
                }
            }
        }

        if (min == null) { /// this will be set after deletion was done
            min = getNewMin();
        }
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
    public AVLTree[] split(int x) {
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
    public int join(IAVLNode x, AVLTree t) {
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

        public AVLNode() {
        } //for virtual nodes

        public AVLNode(int k, String i) { // for regular nodes
            key = k;
            info = i;
            left = new AVLNode();
            right = new AVLNode();

        }

        public int getKey() {
            return key;
        }

        public void setKey(int k) {
            key = k;
        }

        public void setInfo(String st) {
            info = st;
        }

        public String getValue() {
            return info;
        }

        public void setLeft(IAVLNode node) {
            left = (AVLNode) node;
        }

        public IAVLNode getLeft() {
            return left;
        }

        public void setRight(IAVLNode node) {
            right = (AVLNode) node;
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

        public void setHeightAfterInsert() {
            setHeight(0);
            IAVLNode node = getParent();
            if (!node.getRight().isRealNode() || !node.getLeft().isRealNode()) {
                while (node != null) {
                    node.setHeight(node.getHeight() + 1);
                    node = node.getParent();
                }
            }
        }

        public void setHeightAfterDelete() {
            setHeight(getHeight() - 1);
            IAVLNode node = getParent();
            while (node != null) {
                node.setHeight(Math.max(node.getLeft().getHeight(), node.getRight().getHeight()) + 1);
                node = node.getParent();
            }
        }

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

        public int sizeNode() {
            if (!isRealNode()) {
                return 0;
            } else {
                return 1 + left.sizeNode() + right.sizeNode();
            }
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
        AVLTree tree = new AVLTree();
        tree.insert(10, "ten");
        tree.insert(3, "three");
        tree.insert(18, "eighteen");
        tree.insert(1, "one");
        tree.insert(6, "six");
        tree.insert(8, "eight");

        System.out.println("root = " + tree.getRoot().getKey());
        System.out.println("root height = " + tree.getRoot().getHeight());
        System.out.println("root.left = " + tree.getRoot().getLeft().getKey());
        System.out.println("root.left height = " + tree.getRoot().getLeft().getHeight());
        System.out.println("root.right = " + tree.getRoot().getRight().getKey());
        System.out.println("root.right height = " + tree.getRoot().getRight().getHeight());
        System.out.println("root.left.left = " + tree.getRoot().getLeft().getLeft().getKey());
        System.out.println("root.left.left height = " + tree.getRoot().getLeft().getLeft().getHeight());
        IAVLNode node3 = ((AVLNode)tree.getRoot()).searchNode(3);
//        System.out.println("successor of 3 is "+((AVLNode)node3).Successor().getValue());
        tree.delete(18);
        tree.delete(8);
        tree.delete(1);
        tree.delete(6);
        System.out.println("root = " + tree.getRoot().getKey());
        System.out.println("root height = " + tree.getRoot().getHeight());
        System.out.println("root.left = " + tree.getRoot().getLeft().getKey());
        System.out.println("root.left height = " + tree.getRoot().getLeft().getHeight());
        System.out.println("root.right = " + tree.getRoot().getRight().getKey());
        System.out.println("root.right height = " + tree.getRoot().getRight().getHeight());
//        System.out.println(tree.search(18));
        System.out.println("successor of 3 is "+((AVLNode)node3).Successor().getValue());




    }

}






