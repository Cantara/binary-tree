package no.cantara.binarytree;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class BinaryTreePath implements Iterable<BinaryTreePathElement> {
    final Deque<BinaryTreePathElement> path = new LinkedList<>();

    void add(boolean leftChildOfParent, Node node) {
        path.addLast(new BinaryTreePathElement(leftChildOfParent, node));
    }

    public Iterator<BinaryTreePathElement> iterator() {
        return path.iterator();
    }

    public Iterator<BinaryTreePathElement> descendingIterator() {
        return path.descendingIterator();
    }
}
