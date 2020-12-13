import java.awt.print.Printable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class printableTree extends AVLTree {
    //you should implement IAVLnode
    // you should write insert(k, info)

    public void printTree() {
        IAVLNode root = this.getRoot();
        List<List<String>> lines = new ArrayList<List<String>>();

        List<IAVLNode> level = new ArrayList<IAVLNode>();
        List<IAVLNode> next = new ArrayList<IAVLNode>();

        level.add(root);
        int nn = 1;

        int widest = 0;

        while (nn != 0) {
            List<String> line = new ArrayList<String>();

            nn = 0;

            for (IAVLNode n : level) {
                if (n == null) {
                    line.add(null);

                    next.add(null);
                    next.add(null);
                } else {
                    String aa = getText(n);
                    line.add(aa);
                    if (aa.length() > widest) widest = aa.length();

                    next.add(n.getLeft());
                    next.add(n.getRight());

                    if (n.getLeft() != null) nn++;
                    if (n.getRight() != null) nn++;
                }
            }

            if (widest % 8 == 1) widest++;

            lines.add(line);

            List<IAVLNode> tmp = level;
            level = next;
            next = tmp;
            next.clear();
        }

        int perpiece = lines.get(lines.size() - 2).size() * (widest);
        for (int i = 0; i < lines.size(); i++) {
            List<String> line = lines.get(i);
            int hpw = (int) Math.floor(perpiece / 2f) - 1;

            if (i > 0) {
                for (int j = 0; j < line.size(); j++) {

                    // split node
                    char c = ' ';
                    if (j % 2 == 1) {
                        if (line.get(j - 1) != null) {
                            c = (line.get(j) != null) ? '┴' : '┘';
                        } else {
                            if (j < line.size() && line.get(j) != null) c = '└';
                        }
                    }
                    System.out.print(c);

                    // lines and spaces
                    if (line.get(j) == null) {
                        for (int k = 0; k < perpiece - 1; k++) {
                            System.out.print(" ");
                        }
                    } else {

                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? " " : "─");
                        }
                        System.out.print(j % 2 == 0 ? "┌" : "┐");
                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? "─" : " ");
                        }
                    }
                }
                System.out.println();
            }

            // print line of numbers
            for (int j = 0; j < line.size(); j++) {

                String f = line.get(j);
                if (f == null) f = "";
                int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
                int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);

                // a number
                for (int k = 0; k < gap1; k++) {
                    System.out.print(" ");
                }
                System.out.print(f);
                for (int k = 0; k < gap2; k++) {
                    System.out.print(" ");
                }
            }
            System.out.println();

            perpiece /= 2;
        }
    }

    public String getText(IAVLNode node) {
        return "" + node.getKey();

    }

    public static void main(String[] args) {
        AVLTree tree = new printableTree();
//        int a = tree.insert(24, "twnenty four");
//        int b = tree.insert(8, "eight");
//        int b2 = tree.insert(8, "eight");
//        tree.insert(6, "six");
//        tree.insert(7, "seven");
//        tree.insert(12, "twelve");
//        tree.insert(30, "thirty");
//        tree.insert(5, "five");
//        tree.insert(36, "thirty six");
//        tree.insert(28, "twenty eight");
//        tree.insert(26, "twenty six");
//        tree.insert(10, "ten");
//        tree.insert(9, "nine");
        tree.insert(63,"");
        tree.insert(50,"");
        tree.insert(75,"");
        tree.insert(1,"");
        tree.insert(52,"");
        tree.insert(74,"");
        tree.insert(99,"");
        tree.insert(98,"");
        tree.insert(3,"");
        ((printableTree) tree).printTree();
        System.out.println("---------");
        System.out.println("split by = "+52);
        AVLTree[] split = tree.split(52);
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
        System.out.println(((AVLNode)treeSplit0.getRoot()).getSize());
        System.out.println(((AVLNode)treeSplit1.getRoot()).getSize());
//        System.out.println(Arrays.toString(tree.keysToArray()));
//        System.out.println(Arrays.toString(tree.infoToArray()));
//        System.out.println("min is "+tree.min());
//        System.out.println("max is "+tree.max());
//        System.out.println("-------------------------");
//        System.out.println(tree.delete(30));
//        System.out.println(tree.delete(26));
//        System.out.println(tree.delete(36));
//        System.out.println(tree.delete(7));
//        System.out.println(tree.delete(8));
//        System.out.println(tree.delete(5));
//        System.out.println(Arrays.toString(tree.keysToArray()));
//        System.out.println(Arrays.toString(tree.infoToArray()));
//        System.out.println("min is "+tree.min());
//        System.out.println("max is "+tree.max());
//        System.out.println("-------------------------");
////        ((printableTree) tree).printTree();
//        System.out.println("tree size = "+((AVLNode)tree.getRoot()).getSize());
//        System.out.println("left size = "+((AVLNode)tree.getRoot().getLeft()).getSize());
//        System.out.println("right size = "+((AVLNode)tree.getRoot().getRight()).getSize());
//        System.out.println("left left size = "+((AVLNode)tree.getRoot().getLeft().getLeft()).getSize());
//        System.out.println("left right size = "+((AVLNode)tree.getRoot().getLeft().getRight()).getSize());
//        System.out.println("right right size = "+((AVLNode)tree.getRoot().getRight().getRight()).getSize());

//        System.out.println(a);
//        System.out.println(b);
//        System.out.println(b2);
//        AVLTree[] splitted = (tree.split(8));

//        System.out.println(splitted[0].getRoot().getLeft().getKey());
//        System.out.println("large tree:");
//        System.out.println(splitted[0].getRoot().getKey());
//        System.out.println(splitted[0].getRoot().getLeft().getKey());
//        System.out.println(splitted[0].getRoot().getLeft().getLeft().getKey());
//        System.out.println(splitted[0].getRoot().getRight().getKey());
//        System.out.println(splitted[0].getRoot().getRight().getRight().getKey());
//        System.out.println("min = " + splitted[0].min.getKey());
//        System.out.println("max = " + splitted[0].max.getKey());
//        System.out.println("size = " + ((AVLNode)splitted[0].getRoot()).getSize());
//        System.out.println("height = " + splitted[0].getRoot().getHeight());

//        System.out.println("-------------------------");
//        System.out.println("large tree:");
//        System.out.println(splitted[1].getRoot().getKey());
//        System.out.println(splitted[1].getRoot().getLeft().getKey());
//        System.out.println(splitted[1].getRoot().getLeft().getLeft().getKey());
//        System.out.println(splitted[1].getRoot().getRight().getKey());
//        System.out.println(splitted[1].getRoot().getRight().getRight().getKey());
//        System.out.println("min = " + splitted[1].min.getKey());
//        System.out.println("max = " + splitted[1].max.getKey());
//        System.out.println("size = " + ((AVLNode)splitted[1].getRoot()).getSize());
//        System.out.println("height = " + splitted[1].getRoot().getHeight());

    }
}