/*
 * Code is copied/modified from the answer by MightyPork here: https://stackoverflow.com/questions/4965335/how-to-print-binary-tree-diagram-in-java
 */
package no.cantara.binarytree;

import java.util.ArrayList;
import java.util.List;


/**
 * Binary tree printer.
 *
 * @author MightyPork
 */
public class TreePrinter
{
    /** Node that can be printed */
    public interface PrintableNode
    {
        /** Get left child */
        PrintableNode left();


        /** Get right child */
        PrintableNode right();


        /** Get text to be printed */
        String getText();
    }


    /**
     * Print a tree
     *
     * @param root
     *            tree root node
     */
    public static void print(PrintableNode root) {
        StringBuilder sb = new StringBuilder();
        print(root, sb);
        System.out.printf("%s", sb);
        System.out.flush();
    }
    
    public static void print(PrintableNode root, StringBuilder sb)
    {
        List<List<String>> lines = new ArrayList<>();

        List<PrintableNode> level = new ArrayList<>();
        List<PrintableNode> next = new ArrayList<>();

        level.add(root);
        int nn = 1;

        int widest = 0;

        while (nn != 0) {
            List<String> line = new ArrayList<>();

            nn = 0;

            for (PrintableNode n : level) {
                if (n == null) {
                    line.add(null);

                    next.add(null);
                    next.add(null);
                } else {
                    String aa = n.getText();
                    line.add(aa);
                    if (aa.length() > widest) widest = aa.length();

                    next.add(n.left());
                    next.add(n.right());

                    if (n.left() != null) nn++;
                    if (n.right() != null) nn++;
                }
            }

            if (widest % 2 == 1) widest++;

            lines.add(line);

            List<PrintableNode> tmp = level;
            level = next;
            next = tmp;
            next.clear();
        }

        int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);
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
                    sb.append(c);

                    // lines and spaces
                    if (line.get(j) == null) {
                        for (int k = 0; k < perpiece - 1; k++) {
                            sb.append(" ");
                        }
                    } else {

                        for (int k = 0; k < hpw; k++) {
                            sb.append(j % 2 == 0 ? " " : "─");
                        }
                        sb.append(j % 2 == 0 ? "┌" : "┐");
                        for (int k = 0; k < hpw; k++) {
                            sb.append(j % 2 == 0 ? "─" : " ");
                        }
                    }
                }
                sb.append("\n");
            }

            // print line of numbers
            for (int j = 0; j < line.size(); j++) {

                String f = line.get(j);
                if (f == null) f = "";
                int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
                int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);

                // a number
                for (int k = 0; k < gap1; k++) {
                    sb.append(" ");
                }
                sb.append(f);
                for (int k = 0; k < gap2; k++) {
                    sb.append(" ");
                }
            }
            sb.append("\n");

            perpiece /= 2;
        }
    }
}