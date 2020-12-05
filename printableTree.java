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

    public String getText(IAVLNode node){
        return "" + node.getKey();

    }

    public static void main(String[] args) {
        AVLTree tree = new printableTree();
        tree.insert(24, "");

        tree.insert(8, "");
        tree.insert(4, "");
        tree.insert(7, "");
        tree.insert(12, "");
        tree.insert(30, "");
        tree.insert(1, "");
        tree.insert(36, "");
        tree.insert(28, "");
        tree.insert(26, "");
        tree.insert(10, "");
        tree.insert(9, "");
        ((printableTree)tree).printTree();
        System.out.println("tree height = "+tree.getRoot().getHeight());
        System.out.println("tree size = "+((AVLNode)tree.getRoot()).getSize());
        System.out.println(Arrays.toString(tree.keysToArray()));
        System.out.println("-------------------");
        System.out.println("deleting node 9:");
        tree.delete(9);
        ((printableTree)tree).printTree();
        System.out.println("tree height = "+tree.getRoot().getHeight());
        System.out.println("tree size = "+((AVLNode)tree.getRoot()).getSize());
        System.out.println(Arrays.toString(tree.keysToArray()));
        System.out.println("-------------------");
        System.out.println("deleting node 24:");
        tree.delete(24);
        ((printableTree)tree).printTree();
        System.out.println("tree height = "+tree.getRoot().getHeight());
        System.out.println("tree size = "+((AVLNode)tree.getRoot()).getSize());
        System.out.println(Arrays.toString(tree.keysToArray()));
        System.out.println("-------------------");
        System.out.println("deleting node 10:");
        tree.delete(10);
        ((printableTree)tree).printTree();
        System.out.println("tree height = "+tree.getRoot().getHeight());
        System.out.println("tree size = "+((AVLNode)tree.getRoot()).getSize());
        System.out.println(Arrays.toString(tree.keysToArray()));
        System.out.println("-------------------");
        System.out.println("deleting node 8:");
        tree.delete(8);
        ((printableTree)tree).printTree();
        System.out.println("tree height = "+tree.getRoot().getHeight());
        System.out.println("tree size = "+((AVLNode)tree.getRoot()).getSize());
        System.out.println(Arrays.toString(tree.keysToArray()));
        System.out.println("-------------------");

//        tree.delete(8);
//        ((printableTree)tree).printTree();
//        System.out.println("tree height = "+tree.getRoot().getHeight());
//        System.out.println("tree size = "+((AVLNode)tree.getRoot()).getSize());
//        System.out.println(Arrays.toString(tree.keysToArray()));
//        System.out.println("-------------------");
//        tree.delete(4);
//        ((printableTree)tree).printTree();
//        System.out.println("tree height = "+tree.getRoot().getHeight());
//        System.out.println("tree size = "+((AVLNode)tree.getRoot()).getSize());
//        System.out.println(Arrays.toString(tree.keysToArray()));
//        System.out.println("-------------------");

    }
}
