/**
 * AVLTree
 * <p>
 * An implementation of a AVL Tree with
 * distinct integer keys and info
 */

// Tamir Yaari - 304842990 - tamiryaari
// Shay Rozental - 313332181 - shayrozental

import java.util.ArrayList;

public class AVLTree {

    IAVLNode root;
    IAVLNode min;
    IAVLNode max;

    public static int counterBinary; // counts balancing operations
    public static int counterFinger; // counts edges traveled during finger search for measurements Q1
    public static int counterJoins; // counts number of join operations for measurements Q2
    public static ArrayList<Integer> joinCosts; // stores cost of each join operation for a specific split

    public AVLTree() {
        root = new AVLNode();
        min = null;
        max = null;

    }

    /**
     * public boolean empty()
     * <p>
     * returns true if and only if the tree is empty
     */
    public boolean empty() {
        return !root.isRealNode();

    }

    /**
     * public String search(int k)
     * <p>
     * returns the info of an item with key k if it exists in the tree
     * otherwise, returns null
     */
    public String search(int k) {
        if (empty()) { // tree is empty
            return null;
        }
        IAVLNode root = getRoot();
        IAVLNode node = ((AVLNode) root).searchNode(k); // perform binary search
        if (node == null) { // node not found
            return null;
        }
        return (node.getValue());
    }

    public String searchFinger(int k) {
        if (empty()) { // tree is empty
            return null;
        }
        IAVLNode root = max; // start from max node
        IAVLNode node = ((AVLNode) root).searchNodeFinger(k); // perform finger search
        if (node == null) { // node not found
            return null;
        }
        return (node.getValue());
    }

    /**
     * public int insert(int k, String i)
     * <p>
     * inserts an item with key k and info i to the AVL tree.
     * the tree must remain valid (keep its invariants).
     * returns the number of rebalancing operations, or 0 if no rebalancing operations were necessary.
     * promotion/rotation - counted as one rebalance operation, double-rotation is counted as 2.
     * returns -1 if an item with key k already exists in the tree.
     */

    public int insert(int k, String i) {
        return insert(k, i, false);
    }

    public int insert(int k, String i, boolean fingerSearch) {
        if (fingerSearch) {
            if (searchFinger(k) != null) {
                return -1; // node k already in tree
            }
        } else {
            if (search(k) != null) {
                return -1; // node k already in tree
            }
        }
        IAVLNode node = new AVLNode(k, i);
        if (!root.isRealNode()) { // node k is inserted to an empty tree
            root = node;
            min = node;
            max = node;
            root.setHeight(0);
            return 0;
        }
        if (node.getKey() < min.getKey()) { // node k is the new minimum
            min = node;
        }
        if (node.getKey() > max.getKey()) { // node k is the new maximum
            max = node;
        }
        IAVLNode root = getRoot();
        while (node.getParent() == null) { // performs binary search in order to determine where to place node
            if (node.getKey() < root.getKey()) {
                if (!root.getLeft().isRealNode()) {
                    root.setLeft(node);
                    node.rebalancingInsert(); // after node is entered, relevant rebalancing function is called
                    if (fingerSearch) {
                        int res = counterFinger; // counter is nullified to be ready for next operation
                        counterFinger = 0;
                        return res;
                    }
                    int res = counterBinary;
                    counterBinary = 0; // counter is nullified to be ready for next operation
                    return res;
                }
                root = root.getLeft();
            } else {
                if (!root.getRight().isRealNode()) {
                    root.setRight(node);
                    node.rebalancingInsert();
                    if (fingerSearch) {
                        int res = counterFinger;
                        counterFinger = 0;
                        return res;
                    }
                    int res = counterBinary;
                    counterBinary = 0;
                    return res;
                }
                root = root.getRight();
            }
        }
        if (fingerSearch) {
            int res = counterFinger;
            counterFinger = 0;
            return res;
        }
        int res = counterBinary;
        counterBinary = 0;
        return res;
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
        IAVLNode toDelete = ((AVLNode) node).searchNode(k); // finds node to be deleted using binary search
        if (toDelete == null) { // node to be deleted wasn't found
            return -1;
        }
        if (size() == 1) { // tree is consisted of 1 node only - creates a new empty tree instead
            root = new AVLNode();
            min = null;
            max = null;
            return 0;
        }
        if (min.getKey() == k) { // deleting the min node - new min is the successor of the current min
            min = min.Successor();
        }
        if (max.getKey() == k) { // deleting the max node - new max is the predecessor of the current max
            max = max.Predecessor();
        }
        IAVLNode parent = toDelete.getParent();
        if (toDelete.getHeight() == 0) { // deleting a leaf
            if (parent.getLeft() == toDelete) { // leaf is a left child
                parent.setLeft(new AVLNode());
            } else { // leaf is a right child
                parent.setRight(new AVLNode());
            }
            parent.rebalancingDelete(); // after node is deleted, relevant rebalancing function is called
            int res = counterBinary;
            counterBinary = 0;
            return res;
        }
        if (toDelete.getSize() == 2) { // deleting an unary node
            toDelete.deleteUnary();
            if (parent != null) { // false -> tree is consisted of 2 nodes, no need of additional rebalancing
                parent.rebalancingDelete();
            }
            int res = counterBinary;
            counterBinary = 0; // counter is nullified to be ready for next operation
            return res;
        } // node to be deleted has 2 sons
        IAVLNode successor = toDelete.Successor(); // successor is guaranteed to be an unary node
        IAVLNode successorParent = toDelete.Successor().getParent();
        successor.deleteUnary(); // removes successor from tree in order to place it instead of deleted node
        successor.setLeft(toDelete.getLeft());
        successor.setRight(toDelete.getRight()); // set successor's children to be the deleted node's children
        node = successorParent;
        while (node != successor.getLeft() && node != successor.getRight() && node != null) {
            node.updateSize(); // going up the tree updating size, starting from previous parent of successor, up to successor
            node = node.getParent();
        }
        successor.getLeft().updateSize();
        successor.getRight().updateSize();
        ((AVLNode) successor).setHeightAfterInsert(); // update height of successor
        successor.updateSize();
        if (toDelete == root) { // if the deleted node was the root node, set successor to be the root
            root = successor;
            root.setParent(null);
        } else {
            successor.setParent(toDelete.getParent()); // establishes connection between successor and deleted node's parent so deleted node is removed from tree
            if (toDelete == toDelete.getParent().getLeft()) {
                toDelete.getParent().setLeft(successor);
            } else {
                toDelete.getParent().setRight(successor);
            }
        }
        if (toDelete.getParent() != null) {
            if (successorParent == toDelete) {
                successor.setParent(toDelete.getParent());
                ((AVLNode) successor).setHeightAfterInsert();
                successor.rebalancingDelete();
            } else {
                successorParent.rebalancingDelete(); // after node is deleted, relevant rebalancing function is called
            }
        } else {
            successor.getRight().rebalancingDelete(); // if deleted node was root, rebalancing starts from right child
        }
        int res = counterBinary;
        counterBinary = 0;
        return res;
    }

    /**
     * public String min()
     * <p>
     * Returns the info of the item with the smallest key in the tree,
     * or null if the tree is empty
     */

    public String min() { // gets value of node with min pointer
        if (min == null) {
            return null;
        }
        return min.getValue();
    }

    /**
     * public String max()
     * <p>
     * Returns the info of the item with the largest key in the tree,
     * or null if the tree is empty
     */

    public String max() { // gets value of node with max pointer
        if (max == null) {
            return null;
        }
        return max.getValue();
    }


    /**
     * public int[] keysToArray()
     * <p>
     * Returns a sorted array which contains all keys in the tree,
     * or an empty array if the tree is empty.
     */

    public int[] keysToArray() { // performs in-order scan of the tree, adding each node's key to an array
        int[] arr = new int[size()]; // array size is the size of the tree which is the size of the root
        if (arr.length == 0) {
            return arr;
        }
        IAVLNode minNode = min;
        arr[0] = minNode.getKey();
        for (int i = 1; i < arr.length; i++) { // scan starts with min node
            minNode = minNode.Successor(); // afterwards, n-1 successor operations done
            arr[i] = minNode.getKey();
        }
        return arr;
    }

    /**
     * public String[] infoToArray()
     * <p>
     * Returns an array which contains all info in the tree,
     * sorted by their respective keys,
     * or an empty array if the tree is empty.
     */
    public String[] infoToArray() { // performs in-order scan of the tree, adding each node's value to an array
        String[] arr = new String[size()]; // array size is the size of the tree which is the size of the root
        if (arr.length == 0) {
            return arr;
        }
        IAVLNode minNode = min; // scan starts with min node
        arr[0] = minNode.getValue();
        for (int i = 1; i < arr.length; i++) {
            minNode = minNode.Successor(); // afterwards, n-1 successor operations done
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
    public int size() { // returns size of root
        return getRoot().getSize();
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
        counterJoins = 0;
        joinCosts = new ArrayList<>();
        IAVLNode node = ((AVLNode) getRoot()).searchNode(x); // initializing pointers and empty trees to be filled
        IAVLNode nextCurr = this.getRoot();
        AVLTree leftTree = new AVLTree();
        AVLTree rightTree = new AVLTree();
        AVLTree toJoin = new AVLTree();

        IAVLNode successor = node.Successor(); // finds successor, predecessor, min and max to be set as pointers later
        IAVLNode predecessor = node.Predecessor();
        IAVLNode minNode = min;
        IAVLNode maxNode = max;


        if (node.getLeft().isRealNode()) { // adds left subtree and right subtree split node to the initialized trees
            leftTree.fillTree(node.getLeft());
        }
        if (node.getRight().isRealNode()) {
            rightTree.fillTree(node.getRight());
        }

        IAVLNode prev = node;
        IAVLNode curr = node.getParent();

        while (curr != null) {
            nextCurr = curr.getParent();

            if (curr.getRight() == prev) { // node is a right child

                toJoin.fillTree(curr.getLeft());
                curr.setRight(new AVLNode());
                curr.setLeft(new AVLNode());
                curr.setParent(null);

                leftTree.join(curr, toJoin); // node is a right child. therefore its sibling is joined with leftTree
                leftTree.fillTree(leftTree.getRoot());

            } else { // node is left child
                toJoin.fillTree(curr.getRight());
                curr.setRight(new AVLNode());
                curr.setLeft(new AVLNode());
                curr.setParent(null);

                rightTree.join(curr, toJoin); // node is a left child. therefore its sibling is joined with rightTree
                rightTree.fillTree(rightTree.getRoot());

            }
            prev = curr; // go up one level in tree
            curr = nextCurr;

        }

        leftTree.min = minNode; // after all join operations were completed, the pointers from the original tree are used
        leftTree.max = predecessor;
        rightTree.min = successor;
        rightTree.max = maxNode;

        return new AVLTree[]{leftTree, rightTree};
    }

    private void fillTree(IAVLNode node) { // fills tree with root
        this.root = node;
        this.root.setParent(null);
        this.max = node; // only set for initializing purposes - changed in split function
        this.min = node; // only set for initializing purposes - changed in split function
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
        int treeRank = getRoot().getHeight();
        int otherRank = t.getRoot().getHeight();
        int complexity = Math.abs(this.getRoot().getHeight() - t.getRoot().getHeight()) + 1; // computes rank diff to be returned
        if (treeRank < otherRank) {
            if (treeRank == -1) { // this tree is empty
                t.insert(x.getKey(), x.getValue());
                min = t.min;
                max = t.max;
            } else { // trees joined according to keys
                if (t.getRoot().getKey() < x.getKey()) {
                    this.joinRight(x, t);
                    min = t.min;
                } else {
                    this.joinLeft(x, t);
                    max = t.max;
                }
            }
            IAVLNode tRoot = t.getRoot();
            while (tRoot.getParent() != null) {
                tRoot = tRoot.getParent();
            }
            root = tRoot; // joined tree is the other tree, so this tree is set to be joined tree
        } else if (treeRank > otherRank) {
            if (otherRank == -1) { // other tree is empty
                this.insert(x.getKey(), x.getValue());
            } else { // trees joined according to keys
                if (t.getRoot().getKey() < x.getKey()) {
                    t.joinLeft(x, this);
                    min = t.min;
                } else {
                    t.joinRight(x, this);
                    max = t.max;
                }
            }
        } else {
            if (!t.getRoot().isRealNode() && !this.getRoot().isRealNode()) {
                min = x;
                max = x;
            }
            if (t.getRoot().getKey() < x.getKey()) {
                x.setLeft(t.getRoot());
                x.setRight(this.getRoot());
                min = t.min;
            } else {
                x.setRight(t.getRoot());
                x.setLeft(this.getRoot());
                max = t.max;
            }
            root = x;
            getRoot().updateSize();
            ((AVLNode) getRoot()).setHeightAfterInsert();
        }
        counterJoins++;
        joinCosts.add(complexity);
        return complexity;
    }

    private void joinLeft(IAVLNode x, AVLTree t) {
        int thisRank = getRoot().getHeight();
        IAVLNode b = t.getRoot();
        while (b.getHeight() > thisRank) {
            if (b.isRealNode() && b.getLeft().getParent() != b) {
                b.getLeft().setParent(b);
            }
            b = b.getLeft();
        } // b is now a node with close to equal rank of this tree
        IAVLNode c = b.getParent();
        x.setLeft(this.getRoot());
        x.setRight(b);
        x.setParent(c);
        c.setLeft(x);
        ((AVLNode) x).setHeightAfterInsert();
        x.updateSize();
        x.rebalancingInsert(); // rebalancingInsert includes join-relevant cases as well
    }

    private void joinRight(IAVLNode x, AVLTree t) {
        int thisRank = getRoot().getHeight();
        IAVLNode a = t.getRoot();
        while (a.getHeight() > thisRank) {
            if (a.isRealNode() && a.getRight().getParent() != a) {
                a.getRight().setParent(a);
            }
            a = a.getRight();
        } // a is now a node with close to equal rank of this tree
        IAVLNode d = a.getParent();
        x.setRight(this.getRoot());
        x.setLeft(a);
        x.setParent(d);
        d.setRight(x);
        ((AVLNode) x).setHeightAfterInsert();
        x.updateSize();
        x.rebalancingInsert(); // rebalancingInsert includes join-relevant cases as well
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

        public int getSize(); // Returns the size of the node (0 for virtual nodes)

        public void updateSize(); // updates the size of a node according to its sons

        public void deleteUnary(); // deletes the node in case the node in an unary node

        public IAVLNode Successor(); // finds the successor of the node and returns it

        public IAVLNode Predecessor(); // finds the predecessor of the node and returns it

        public void rebalancingDelete(); // performs rebalancing operations after a node was deleted

        public void rebalancingInsert(); // performs rebalancing operations after a node was inserted

        public void promote(); // promotes a node - increases its height by 1

        public void demote(); // demotes a node - reduces its height by 1

        public void rotateRight(); // performs a right rotation on a node

        public void rotateLeft(); // performs a left rotation on a node

        public void rotateLeftRight(); // performs a left-right rotation on a node

        public void rotateRightLeft(); // performs a right-left rotation on a node

        public int whatCaseInsert(); // classifies the rebalancing case after a node was inserted

        public String whatCaseDelete(); // classifies the rebalancing case after a node was deleted

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

        String info = null;
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
            IAVLNode parent = getParent();
            IAVLNode grandpa = getParent().getParent();
            IAVLNode rightChild = getRight();

            setRight(parent);
            if (getRoot().getKey() == parent.getKey() || grandpa == null) {
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
            counterBinary++;
        }

        public void rotateLeft() {
            IAVLNode parent = getParent();
            IAVLNode grandpa = getParent().getParent();
            IAVLNode leftChild = getLeft();

            setLeft(parent);
            if (getRoot().getKey() == parent.getKey() || grandpa == null) { // parent is root
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
            counterBinary++;
        }

        public void demote() {
            int rank = getHeight();
            setHeight(rank - 1);
            counterBinary++;
        }

        public void promote() {
            int rank = getHeight();
            setHeight(rank + 1);
            counterBinary++;
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
                return "nothing"; // rebalancingDelete uses the different int return values to classify
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
            return ""; // this is never reached
        }

        public void rebalancingDelete() {
            IAVLNode node = this;
            while (!(node.whatCaseDelete().equals("nothing") ||
                    node.whatCaseDelete().equals("case 2 rotateRight") || // terminal operation
                    node.whatCaseDelete().equals("case 2 rotateLeft"))) { // terminal operation
                if (node.whatCaseDelete().equals("case 1")) {
                    node.demote();
                    node.updateSize();
                    node = node.getParent();
                    if (node == null) {
                        break;
                    }
                    continue;
                }
                if (node.whatCaseDelete().equals("case 3 rotateLeft")) {
                    node.getRight().rotateLeft();
                    node.demote();
                    node.demote();
                    node.updateSize();
                    node.getParent().updateSize();
                    node = node.getParent();
                    node = node.getParent();
                    if (node == null) {
                        break;
                    }
                    continue;
                }
                if (node.whatCaseDelete().equals("case 3 rotateRight")) {
                    node.getLeft().rotateRight();
                    node.demote();
                    node.demote();
                    node.updateSize();
                    node.getParent().updateSize();
                    node = node.getParent();
                    node = node.getParent();
                    if (node == null) {
                        break;
                    }
                    continue;
                }
                if (node.whatCaseDelete().equals("case 4 rotateRightLeft")) {
                    node.getRight().getLeft().rotateRightLeft();
                    node.demote();
                    node.demote();
                    node.getParent().promote();
                    node.getParent().getRight().demote();
                    node.updateSize();
                    node.getParent().getRight().updateSize();
                    node.getParent().updateSize();
                    node = node.getParent();
                    node = node.getParent();
                    if (node == null) {
                        break;
                    }
                    continue;
                }
                if (node.whatCaseDelete().equals("case 4 rotateLeftRight")) {
                    node.getLeft().getRight().rotateLeftRight();
                    node.demote();
                    node.demote();
                    node.getParent().promote();
                    node.getParent().getLeft().demote();
                    node.updateSize();
                    node.getParent().getLeft().updateSize();
                    node.getParent().updateSize();
                    node = node.getParent();
                    node = node.getParent();
                    if (node == null) {
                        break;
                    }
                    continue;
                }
                break;
            }
            if (node != null) {
                if (node.whatCaseDelete().equals("case 2 rotateLeft")) {
                    node.getRight().rotateLeft();
                    node.demote();
                    node.getParent().promote();
                    node.updateSize();
                    node.getParent().updateSize();
                }
                if (node.whatCaseDelete().equals("case 2 rotateRight")) {
                    node.getLeft().rotateRight();
                    node.demote();
                    node.getParent().promote();
                    node.updateSize();
                    node.getParent().updateSize();
                }
                if (node.whatCaseDelete().equals("nothing")) {
                    while (node.getParent() != null) {
                        node.updateSize();
                        node = node.getParent();
                    }
                }
            }
            if (node != null) {
                node.updateSize();
            }

        }


        /**
         * 21 - case 2 rotateleft
         * 22 - case 2 rotateRight
         * 31 - case 3 rotateleftright
         * 32 - case 3 rotaterightleft
         */
        public int whatCaseInsert() {
            if (getParent() == null) {
                return 23;
            }
            IAVLNode parent = getParent();
            IAVLNode sibling;
            if (parent.getLeft() == this) {
                sibling = getParent().getRight();
                if (((parent.getHeight() - this.getHeight() == 1)
                        && (parent.getHeight() - sibling.getHeight() == 1)) ||
                        ((parent.getHeight() - this.getHeight() == 1) && (parent.getHeight() - sibling.getHeight() == 2))
                        || ((parent.getHeight() - this.getHeight() == 2) && (parent.getHeight() - sibling.getHeight() == 1))) {
                    return 0; // rebalancingInsert uses the different int return values to classify
                }
                if ((parent.getHeight() - this.getHeight() == 0)
                        && (parent.getHeight() - sibling.getHeight() == 1)) {
                    return 1;
                }
                IAVLNode childLeft = getLeft();
                IAVLNode childRight = getRight();
                if ((parent.getHeight() - this.getHeight() == 0)
                        && (parent.getHeight() - sibling.getHeight() == 2)) {
                    if ((this.getHeight() - childLeft.getHeight() == 1)
                            && (this.getHeight() - childRight.getHeight() == 2)) {
                        return 22;
                    }
                    if ((this.getHeight() - childLeft.getHeight() == 2)
                            && (this.getHeight() - childRight.getHeight() == 1)) {
                        return 31;
                    }
                    if ((this.getHeight() - childLeft.getHeight() == 1)
                            && (this.getHeight() - childRight.getHeight() == 1)) {
                        return 41; // joining rank(T1) < rank(T2)
                    }
                }
            } else {
                sibling = getParent().getLeft();
                if (((parent.getHeight() - this.getHeight() == 1)
                        && (parent.getHeight() - sibling.getHeight() == 1)) ||
                        ((parent.getHeight() - this.getHeight() == 1) && (parent.getHeight() - sibling.getHeight() == 2)) ||
                        ((parent.getHeight() - this.getHeight() == 2) && (parent.getHeight() - sibling.getHeight() == 1))) {
                    return 0;
                }
                if ((parent.getHeight() - this.getHeight() == 0)
                        && (parent.getHeight() - sibling.getHeight() == 1)) {
                    return 1;
                }
                IAVLNode childLeft = getLeft();
                IAVLNode childRight = getRight();
                if ((parent.getHeight() - this.getHeight() == 0)
                        && (parent.getHeight() - sibling.getHeight() == 2)) {

                    if ((this.getHeight() - childLeft.getHeight() == 1)
                            && (this.getHeight() - childRight.getHeight() == 2)) {
                        return 32;
                    }
                    if ((this.getHeight() - childLeft.getHeight() == 2)
                            && (this.getHeight() - childRight.getHeight() == 1)) {
                        return 21;
                    }
                    if ((this.getHeight() - childLeft.getHeight() == 1)
                            && (this.getHeight() - childRight.getHeight() == 1)) {
                        return 42; // joining rank(T1) > rank(T2)
                    }
                }
            }
            return Integer.MAX_VALUE; // this is never reached
        }

        public void rebalancingInsert() { // performs operations according to different cases
            IAVLNode node = this;
            while (true) {
                while ((node.whatCaseInsert() == 0) && (node != getRoot())) {
                    node = node.getParent();
                    node.updateSize();
                }
                node.updateSize();

                while ((node.whatCaseInsert() == 1) && (node != getRoot())) {
                    node = node.getParent();
                    node.promote();
                    node.updateSize();
                }

                if (node.whatCaseInsert() == 22) {
                    node.rotateRight();
                    node.getRight().demote();
                    node.getRight().updateSize();
                    node.updateSize();
                }
                if (node.whatCaseInsert() == 21) {
                    node.rotateLeft();
                    node.getLeft().demote();
                    node.getLeft().updateSize();
                    node.updateSize();
                }
                if (node.whatCaseInsert() == 31) {
                    node.getRight().rotateLeftRight();
                    node.demote();
                    node.updateSize();
                    node.getParent().getRight().demote();
                    node.getParent().getRight().updateSize();
                    node.getParent().promote();
                    node.getParent().updateSize();

                }
                if (node.whatCaseInsert() == 32) {
                    node.getLeft().rotateRightLeft();
                    node.demote();
                    node.updateSize();
                    node.getParent().getLeft().demote();
                    node.getParent().getLeft().updateSize();
                    node.getParent().promote();
                    node.getParent().updateSize();
                }
                if (node.whatCaseInsert() == 41) {
                    node.rotateRight();
                    node.promote();
                    node.updateSize();
                }

                if (node.whatCaseInsert() == 42) {
                    node.rotateLeft();
                    node.promote();
                    node.updateSize();
                }

                if (node.getParent() == getRoot()) {
                    node.getParent().updateSize();
                }
                if (node.getParent() == null) {
                    break;
                }
                if (node == getRoot() && node.getParent() != null) {
                    node = node.getParent();
                    node.updateSize();


                }
            }
        }

        public void deleteUnary() {
            if (this == root) {
                if (this.getLeft().getHeight() == 0) {
                    root = this.getLeft();
                } else {
                    root = this.getRight();
                }
                return;
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
            counterBinary++;
        }

        public void updateSize() {
            if (!isRealNode()) {
                setSize(0);
                return;
            }
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

        public AVLNode nodeMin() { // finds minimum node in subtree for which the "this" node is the root
            AVLNode node = left;
            if (!left.isRealNode()) {
                return this;
            }
            while (node.left.isRealNode()) {
                node = node.left;
            }
            return node;
        }

        public AVLNode nodeMax() { // finds maximum node in subtree for which the "this" node is the root
            AVLNode node = right;
            if (!right.isRealNode()) {
                return this;
            }
            while (node.right.isRealNode()) {
                node = node.right;
            }
            return node;
        }

        public IAVLNode Successor() {
            AVLNode node = right;
            if (node.isRealNode()) {
                return node.nodeMin();
            }
            AVLNode x = this;
            AVLNode y = x.parent;
            while ((y != null) && (x.key == y.right.key)) {
                x = y;
                y = x.parent;
            }
            return y;
        }

        public IAVLNode Predecessor() {
            AVLNode node = left;
            if (node.isRealNode()) {
                return node.nodeMax();
            }
            AVLNode x = this;
            AVLNode y = x.parent;
            while ((y != null) && (x.key == y.key)) {
                x = y;
                y = x.parent;
            }
            return y;
        }

        public AVLNode searchNode(int k) { // standard binary search
            if (!isRealNode()) {
                counterFinger--;
                return null;
            }
            if (key == k) {
                return this;
            } else {
                if (key > k) {
                    counterFinger++;
                    return left.searchNode(k);
                } else {
                    counterFinger++;
                    return right.searchNode(k);
                }
            }
        }

        public AVLNode searchNodeFinger(int k) { // search starting with finger pointing max node
            IAVLNode node = max;
            if (k > max.getKey() || max == null) {
                return null;
            }
            if (k == max.getKey()) {
                return this;
            }
            while (k < node.getKey() && node.getParent() != null) {
                node = node.getParent();
                counterFinger++;
            }
            return ((AVLNode) node).searchNode(k);

        }

        public int maxNodeInLeftTree() { // used for measurements Q2
            IAVLNode node = getLeft();
            while (node.getRight().isRealNode()) {
                node = node.getRight();
            }
            return node.getKey();
        }

    }
}


