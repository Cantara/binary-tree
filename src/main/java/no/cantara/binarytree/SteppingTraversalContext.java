package no.cantara.binarytree;

import java.util.ArrayDeque;
import java.util.Deque;

public class SteppingTraversalContext<R> extends TraversalContext<R> {

    final Deque<Node> stack = new ArrayDeque<>();
    Node node;
    Node lastVisitedNode;
    boolean hasNext;
    Node next;
    boolean initialized;

    public SteppingTraversalContext(TraversalRange range, Node root) {
        super(range);
        this.node = root;
    }
}
