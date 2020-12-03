
/**
 * AVLTree
 * <p>
 * An implementation of a AVL Tree with
 * distinct integer keys and info
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AVLTree {

    IAVLNode root;
    IAVLNode min;
    IAVLNode max;
    int size;

    public AVLTree() {
        root = null;
        min = null;
        max = null;
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
//        System.out.println("inserting node = "+k);
        IAVLNode node = new AVLNode(k, i);
        if (root == null) {
            root = node;
            min = node;
            max = node;
            root.setHeight(0);
            return 0;
        }
        if (node.getKey() < min.getKey()) {
            min = node;
        }
        if (node.getKey() > max.getKey()) {
            max = node;
        }
        IAVLNode root = getRoot();
        while (node.getParent() == null) {
            if (node.getKey() < root.getKey()) {
                if (!root.getLeft().isRealNode()) {
                    root.setLeft(node);
                    ((AVLNode) node).rebalancingInsert();
                    return 42;
                }
                root = root.getLeft();
            } else {
                if (!root.getRight().isRealNode()) {
                    root.setRight(node);
                    ((AVLNode) node).rebalancingInsert();
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
        IAVLNode node = this.root;
        IAVLNode toDelete = ((AVLNode) node).searchNode(k);
        if (toDelete == null) {
            return -1;
        }
        if (size() == 1) {
            root = null;
            min = null;
            max = null;
            size = 0;
            return 2312;
        }
        if (min.getKey() == k) {
            min = ((AVLNode) min).Successor();
        }
        if (max.getKey() == k) {
            max = ((AVLNode) max).Predecessor();
        }
        IAVLNode parent = toDelete.getParent();
        if (toDelete.getHeight() == 0) { //deleting a leaf
            if (parent.getLeft() == toDelete) {
                parent.setLeft(new AVLNode());
            } else {
                parent.setRight(new AVLNode());
            }
            ((AVLNode) parent).rebalancingDelete();
            return 232;
        }
        if (((AVLNode) toDelete).getSize() == 2) { //deleting a unary node
            ((AVLNode) toDelete).deleteUnary();
            ((AVLNode) parent).rebalancingDelete();
            return 2322;
        }
        IAVLNode successor = ((AVLNode) toDelete).Successor();
        ((AVLNode) successor).deleteUnary();
        successor.setLeft(toDelete.getLeft());
        successor.setRight(toDelete.getRight());
        if (toDelete == root) {
            root = successor;
        } else {
            successor.setParent(toDelete.getParent());
        }
        if (toDelete == toDelete.getParent().getLeft()) {
            toDelete.getParent().setLeft(successor);
        } else {
            toDelete.getParent().setRight(successor);
        }
        ((AVLNode) parent).rebalancingDelete();
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
        }

        public void rotateRight() {
//            if ((getParent() != null) && (getParent().getParent() != null)) {}
            IAVLNode parent = getParent();
            IAVLNode grandpa = getParent().getParent();
            IAVLNode rightChild = getRight();

            setRight(parent);
            if (getRoot().getKey() == parent.getKey()) {
                root = this;
                setParent(grandpa);
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
                setParent(grandpa);
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
            setHeight(rank - 1);
        }

        public void promote() {
            int rank = getHeight();
            setHeight(rank + 1);
        }

        public void rotateLeftRight() {
            rotateLeft();
            rotateRight();
        }

        public void rotateRightLeft() {
            rotateRight();
            rotateLeft();
        }

        public String whatCaseDelete() { // whatCaseDelete is applied on parent node
            IAVLNode leftChild = this.getLeft();
            IAVLNode rightChild = this.getRight();

            if ((this.getHeight() - leftChild.getHeight() == 2 && this.getHeight() - rightChild.getHeight() == 1) ||
                    (this.getHeight() - rightChild.getHeight() == 2 && this.getHeight() - leftChild.getHeight() == 1)) {
                return "nothing";
            }
            if (this.getHeight() - leftChild.getHeight() == 2 && this.getHeight() - rightChild.getHeight() == 2) {
                return "case 1";
            }
            if (this.getHeight() - leftChild.getHeight() == 3 && this.getHeight() - rightChild.getHeight() == 1) {
                if (rightChild.getHeight() - rightChild.getLeft().getHeight() == 1 &&
                        rightChild.getHeight() - rightChild.getRight().getHeight() == 1) {
                    return "case 2 rotateLeft";
                }
                if (rightChild.getHeight() - rightChild.getLeft().getHeight() == 2 &&
                        rightChild.getHeight() - rightChild.getRight().getHeight() == 1) {
                    return "case 3 rotateLeft";
                }
                if (rightChild.getHeight() - rightChild.getLeft().getHeight() == 1 &&
                        rightChild.getHeight() - rightChild.getRight().getHeight() == 2) {
                    return "case 4 rotateRightLeft";
                }
            }

            if (this.getHeight() - leftChild.getHeight() == 1 && this.getHeight() - rightChild.getHeight() == 3) {
                if (leftChild.getHeight() - leftChild.getLeft().getHeight() == 1 &&
                        leftChild.getHeight() - leftChild.getRight().getHeight() == 1) {
                    return "case 2 rotateRight";
                }
                if (leftChild.getHeight() - leftChild.getLeft().getHeight() == 1 &&
                        leftChild.getHeight() - leftChild.getRight().getHeight() == 2) {
                    return "case 3 rotateRight";
                }
                if (leftChild.getHeight() - leftChild.getLeft().getHeight() == 2 &&
                        leftChild.getHeight() - leftChild.getRight().getHeight() == 1) {
                    return "case 4 rotateLeftRight";
                }
            }
            return "";
        }

        public void rebalancingDelete() {
            IAVLNode node = this;
            while (!(((AVLNode) node).whatCaseDelete().equals("nothing") ||
                    ((AVLNode) node).whatCaseDelete().equals("case 2 rotateRight") ||
                    ((AVLNode) node).whatCaseDelete().equals("case 2 rotateLeft"))) {
                if (((AVLNode) node).whatCaseDelete().equals("case 1")) {
                    ((AVLNode) node).demote();
                    ((AVLNode) node).updateSize();
                    node = node.getParent();
                    continue;
                }
                if (((AVLNode) node).whatCaseDelete().equals("case 3 rotateLeft")) {
                    ((AVLNode) node.getRight()).rotateLeft();
                    ((AVLNode) node).demote();
                    ((AVLNode) node).demote();
                    ((AVLNode) node).updateSize();
                    ((AVLNode) node.getParent()).updateSize();
                    node = node.getParent();
                    node = node.getParent();
                    continue;
                }
                if (((AVLNode) node).whatCaseDelete().equals("case 3 rotateRight")) {
                    ((AVLNode) node.getLeft()).rotateRight();
                    ((AVLNode) node).demote();
                    ((AVLNode) node).demote();
                    ((AVLNode) node).updateSize();
                    ((AVLNode) node.getParent()).updateSize();
                    node = node.getParent();
                    node = node.getParent();
                    continue;
                }
                if (((AVLNode) node).whatCaseDelete().equals("case 4 rotateRightLeft")) {
                    ((AVLNode) node.getRight().getLeft()).rotateRightLeft();
                    ((AVLNode) node).demote();
                    ((AVLNode) node).demote();
                    ((AVLNode) node.getParent()).promote();
                    ((AVLNode) node.getParent().getRight()).demote();
                    ((AVLNode) node).updateSize();
                    ((AVLNode) node.getParent().getRight()).updateSize();
                    ((AVLNode) node.getParent()).updateSize();
                    node = node.getParent();
                    node = node.getParent();
                    continue;
                }
                if (((AVLNode) node).whatCaseDelete().equals("case 4 rotateLeftRight")) {
                    ((AVLNode) node.getLeft().getRight()).rotateLeftRight();
                    ((AVLNode) node).demote();
                    ((AVLNode) node).demote();
                    ((AVLNode) node.getParent()).promote();
                    ((AVLNode) node.getParent().getLeft()).demote();
                    ((AVLNode) node).updateSize();
                    ((AVLNode) node.getParent().getLeft()).updateSize();
                    ((AVLNode) node.getParent()).updateSize();
                    node = node.getParent();
                    node = node.getParent();
                    continue;
                }
//                node = node.getParent();
                break;
            }
            if (((AVLNode) node).whatCaseDelete().equals("case 2 rotateLeft")) {
                ((AVLNode) node.getRight()).rotateLeft();
                ((AVLNode) node).demote();
                ((AVLNode) node.getParent()).promote();
                ((AVLNode) node).updateSize();
                ((AVLNode) node.getParent()).updateSize();
            }
            if (((AVLNode) node).whatCaseDelete().equals("case 2 rotateRight")) {
                ((AVLNode) node.getLeft()).rotateRight();
                ((AVLNode) node).demote();
                ((AVLNode) node.getParent()).promote();
                ((AVLNode) node).updateSize();
                ((AVLNode) node.getParent()).updateSize();
            }
            ((AVLNode) node).updateSize();

        }


        /**
         * 21 - case 2 rotateleft
         * 22 - case 2 rotateRight
         * 31 - case 3 rotateleftright
         * 32 - case 3 rotaterightleft
         *
         * @return
         */
        public int whatCaseInsert() {
            if (getRoot() == this) {
                return 23;
            }
            IAVLNode parent = getParent();
            IAVLNode sibling;
            if (parent.getLeft() == this) {
                sibling = getParent().getRight();
                if (((parent.getHeight() - this.getHeight() == 1)
                        && (parent.getHeight() - sibling.getHeight() == 1)) ||
                        ((parent.getHeight() - this.getHeight() == 1)  && (parent.getHeight() - sibling.getHeight() == 2))
                        || ((parent.getHeight() - this.getHeight() == 2)  && (parent.getHeight() - sibling.getHeight() == 1)))  {
//                    System.out.println("0");
                    return 0;
                }
                if ((parent.getHeight() - this.getHeight() == 0)
                        && (parent.getHeight() - sibling.getHeight() == 1)) {
//                    System.out.println("1");
                    return 1;
                }
                IAVLNode childLeft = getLeft();
                IAVLNode childRight = getRight();
                if ((parent.getHeight() - this.getHeight() == 0)
                        && (parent.getHeight() - sibling.getHeight() == 2)) {
                    if ((this.getHeight() - childLeft.getHeight() == 1)
                            && (this.getHeight() - childRight.getHeight() == 2)) {
//                        System.out.println("22");
                        return 22;
                    }
                    if ((this.getHeight() - childLeft.getHeight() == 2)
                            && (this.getHeight() - childRight.getHeight() == 1)) {
//                        System.out.println("31");
                        return 31;
                    }
                }
            } else {
                sibling = getParent().getLeft();
                if (((parent.getHeight() - this.getHeight() == 1)
                        && (parent.getHeight() - sibling.getHeight() == 1)) ||
                        ((parent.getHeight() - this.getHeight() == 1)  && (parent.getHeight() - sibling.getHeight() == 2)) ||
                        ((parent.getHeight() - this.getHeight() == 2)  && (parent.getHeight() - sibling.getHeight() == 1)))  {
//                    System.out.println("00");
                    return 0;
                }
                if ((parent.getHeight() - this.getHeight() == 0)
                        && (parent.getHeight() - sibling.getHeight() == 1)) {
//                    System.out.println("11");
                    return 1;
                }
                IAVLNode childLeft = getLeft();
                IAVLNode childRight = getRight();
                if ((parent.getHeight() - this.getHeight() == 0)
                        && (parent.getHeight() - sibling.getHeight() == 2)) {
                    if ((this.getHeight() - childLeft.getHeight() == 1)
                            && (this.getHeight() - childRight.getHeight() == 2)) {
//                        System.out.println("32");
                        return 32;
                    }
                    if ((this.getHeight() - childLeft.getHeight() == 2)
                            && (this.getHeight() - childRight.getHeight() == 1)) {
//                        System.out.println("21");
                        return 21;
                    }
                }
            }
//            System.out.println("end! key = " + getKey());
            return 554;
        }

        public void rebalancingInsert() {
            IAVLNode node = this;
//            System.out.println(node.getKey());
            while ((((AVLNode) node).whatCaseInsert() == 0) && (node != root)) {
//                System.out.println("case 0");
                node = node.getParent();
                ((AVLNode) node).updateSize();
            }

            ((AVLNode) node).updateSize();
            while ((((AVLNode) node).whatCaseInsert() == 1) && (node != root)) {
//                System.out.println("whatCaseInsert == 1, key = "+node.getKey());
                node = node.getParent();
                ((AVLNode) node).setHeightAfterInsert();
                ((AVLNode) node).updateSize();
            }

//            ((AVLNode) node).updateSize();
            if (((AVLNode) node).whatCaseInsert() == 22) {
//                System.out.println("case 2 R");
                ((AVLNode) node).rotateRight();
                ((AVLNode) node.getRight()).demote();
                ((AVLNode) node.getRight()).updateSize();
                ((AVLNode) node).updateSize();
            }
            if (((AVLNode) node).whatCaseInsert() == 21) {
//                System.out.println("case 2 L");
                ((AVLNode) node).rotateLeft();
                ((AVLNode) node.getLeft()).demote();
                ((AVLNode) node.getLeft()).updateSize();
                ((AVLNode) node).updateSize();
            }
            if (((AVLNode) node).whatCaseInsert() == 31) {
//                System.out.println("case 3 LR");
                ((AVLNode) node.getRight()).rotateLeftRight();
                ((AVLNode) node).demote();
                ((AVLNode) node).updateSize();
                ((AVLNode) node.getParent().getRight()).demote();
                ((AVLNode) node.getParent().getRight()).updateSize();
                ((AVLNode) node.getParent()).promote();
                ((AVLNode) node.getParent()).updateSize();

            }
            if (((AVLNode) node).whatCaseInsert() == 32) {
//                System.out.println("case 3 RL");
                ((AVLNode) node.getLeft()).rotateRightLeft();
                ((AVLNode) node).demote();
                ((AVLNode) node).updateSize();
                ((AVLNode) node.getParent().getLeft()).demote();
                ((AVLNode) node.getParent().getLeft()).updateSize();
                ((AVLNode) node.getParent()).promote();
                ((AVLNode) node.getParent()).updateSize();
            }
        }

        public int deleteUnary() {
            if (this == root) {
                if (this.getLeft().getHeight() == 0) {
                    root = this.getLeft();
                } else {
                    root = this.getRight();
                }
                return 0;
            }
            IAVLNode parent = this.getParent();
            if (parent.getLeft() == this) {
                if (this.getLeft().getHeight() == 0) {
                    parent.setLeft(this.getLeft());
                } else {
                    parent.setLeft(this.getRight());
                }
            } else {
                if (this.getLeft().getHeight() == 0) {
                    parent.setRight(this.getLeft());
                } else {
                    parent.setRight(this.getRight());
                }
            }
            return 44;
        }


        public IAVLNode getLeft() {
            return left;
        }

        public void setRight(IAVLNode node) {
            right = (AVLNode) node;
            right.setParent(this);
        }

        public void setHeightAfterInsert() {
            int heightLeft = left.getHeight();
            int heightRight = right.getHeight();
            setHeight(Math.max(heightLeft, heightRight) + 1);
        }

        public void updateSize() {
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
        AVLTree tree = new AVLTree();
//        tree.insert(1, "one");
//        tree.insert(2, "two");
//        tree.insert(3, "three");
//        tree.delete(2);
//        int[] keys = tree.keysToArray();
//        System.out.println(Arrays.toString(keys));
//        System.out.println("root = " + tree.getRoot().getKey() + " ");
//        System.out.println("root.left = " + tree.getRoot().getLeft().getKey());
//        System.out.println("root.right = " + tree.getRoot().getRight().getKey());


        tree.insert(8,"");
        tree.insert(24,"");
        tree.insert(4,"");
        tree.insert(7,"");
        tree.insert(36,"");
        tree.insert(16,"");
        tree.insert(2,"");
        tree.insert(1,"");
        tree.insert(5,"");
        tree.insert(13,"");
        tree.insert(35,"");
//        ((AVLNode)tree.getRoot()).updateSize();
//        tree.insert(4,"");
//        tree.insert(9,"");
//
//        int[] keys = tree.keysToArray();
//        System.out.println(Arrays.toString(keys));
        System.out.println("root = " + tree.getRoot().getKey() + " size= " +
                ((AVLNode) tree.getRoot()).getSize() + " height = " + tree.getRoot().getHeight());
        System.out.println("root.left = " + tree.getRoot().getLeft().getKey() + " size= " + ((AVLNode) tree.getRoot().getLeft()).getSize()
                + " height = " + tree.getRoot().getLeft().getHeight());
        System.out.println("root.right = " + tree.getRoot().getRight().getKey() + " size= " + ((AVLNode) tree.getRoot().getRight()).getSize()
                + " height = " + tree.getRoot().getRight().getHeight());
        System.out.println("root.left.left = " + tree.getRoot().getLeft().getLeft().getKey() + " size= " + ((AVLNode) tree.getRoot().getLeft().getLeft()).getSize()+
                " height = "
                + tree.getRoot().getLeft().getLeft().getHeight());
        System.out.println("root.left.right = " + tree.getRoot().getLeft().getRight().getKey() + " size= " + ((AVLNode) tree.getRoot().getLeft().getRight()).getSize()+ "" +
                " height = "
                + tree.getRoot().getLeft().getRight().getHeight());
//        System.out.println(tree.getRoot().getParent());
//        System.out.println("root.right.right = " + tree.getRoot().getRight().getRight().getKey() + " size= " + ((AVLNode) tree.getRoot().getRight().getRight()).getSize());
//        System.out.println("root.right.left = " + tree.getRoot().getRight().getLeft().getKey() + " size= " + ((AVLNode) tree.getRoot().getRight().getLeft()).getSize());

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
