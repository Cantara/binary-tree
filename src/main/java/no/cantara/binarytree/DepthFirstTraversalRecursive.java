package no.cantara.binarytree;

/**
 * Recursive depth-first (DFS) traversal on a binary tree.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public final class DepthFirstTraversalRecursive implements DepthFirstTraversal {

    private final BinaryTree tree;

    public DepthFirstTraversalRecursive(BinaryTree tree) {
        this.tree = tree;
    }

    @Override
    public void traversePreOrder(NodeVisitor visitor) {
        traversePreOrder(tree.getRoot(), visitor);
    }

    /**
     * Traverses the tree in pre-order and calls the {@link NodeVisitor#visit(Node)} method on each
     * node.
     *
     * @param node    the node
     * @param visitor the visitor
     */
    public static void traversePreOrder(Node node, NodeVisitor visitor) {
        traversePreOrder(node, new TraversalContext<>(TraversalRange.OPEN), (ctx, n) -> visitor.visit(n));
    }

    @Override
    public void traversePostOrder(NodeVisitor visitor) {
        traversePostOrder(tree.getRoot(), visitor);
    }

    /**
     * Traverses the tree in post-order and calls the {@link NodeVisitor#visit(Node)} method on each
     * node.
     *
     * @param node    the node
     * @param visitor the visitor
     */
    public static void traversePostOrder(Node node, NodeVisitor visitor) {
        traversePostOrder(node, new TraversalContext<>(TraversalRange.OPEN), (ctx, n) -> visitor.visit(n));
    }

    @Override
    public void traverseInOrder(NodeVisitor visitor) {
        traverseInOrder(tree.getRoot(), visitor);
    }

    /**
     * Traverses the tree in-order and calls the {@link NodeVisitor#visit(Node)} method on each node.
     *
     * @param node    the node
     * @param visitor the visitor
     */
    public static void traverseInOrder(Node node, NodeVisitor visitor) {
        traverseInOrder(node, new TraversalContext<>(TraversalRange.OPEN), (ctx, n) -> visitor.visit(n));
    }

    @Override
    public void traverseReverseInOrder(NodeVisitor visitor) {
        traverseReverseInOrder(tree.getRoot(), visitor);
    }

    /**
     * Traverses the tree reverse in-order and calls the {@link NodeVisitor#visit(Node)} method on
     * each node.
     *
     * @param node    the node
     * @param visitor the visitor
     */
    public static void traverseReverseInOrder(Node node, NodeVisitor visitor) {
        traverseReverseInOrder(node, new TraversalContext<>(TraversalRange.OPEN), (ctx, n) -> visitor.visit(n));
    }

    @Override
    public <R> TraversalSummary<R> traversePreOrder(TraversalRange range, Visitor<R> visitor) {
        TraversalContext<R> ctx = traversePreOrder(tree.getRoot(), new TraversalContext<>(range), visitor);
        return new TraversalSummary<>(ctx.result);
    }

    public static <R> TraversalContext<R> traversePreOrder(Node node, TraversalContext<R> ctx, Visitor<R> visitor) {
        if (node == null) {
            return ctx;
        }
        if (ctx.shouldVisit(node)) {
            visitor.visit(ctx, node);
            if (ctx.stop) {
                return ctx;
            }
        }
        if (ctx.shouldTraverseLeft(node)) {
            traversePreOrder(node.left(), ctx, visitor);
            if (ctx.stop) {
                return ctx;
            }
        }
        if (ctx.shouldTraverseRight(node)) {
            traversePreOrder(node.right(), ctx, visitor);
        }
        return ctx;
    }

    @Override
    public <R> TraversalSummary<R> traversePostOrder(TraversalRange range, Visitor<R> visitor) {
        TraversalContext<R> ctx = traversePostOrder(tree.getRoot(), new TraversalContext<>(range), visitor);
        return new TraversalSummary<>(ctx.result);
    }

    public static <R> TraversalContext<R> traversePostOrder(Node node, TraversalContext<R> ctx, Visitor<R> visitor) {
        if (node == null) {
            return ctx;
        }
        if (ctx.shouldTraverseLeft(node)) {
            traversePostOrder(node.left(), ctx, visitor);
            if (ctx.stop) {
                return ctx;
            }
        }
        if (ctx.shouldTraverseRight(node)) {
            traversePostOrder(node.right(), ctx, visitor);
            if (ctx.stop) {
                return ctx;
            }
        }
        if (ctx.shouldVisit(node)) {
            visitor.visit(ctx, node);
        }
        return ctx;
    }

    @Override
    public <R> TraversalSummary<R> traverseInOrder(TraversalRange range, Visitor<R> visitor) {
        TraversalContext<R> ctx = traverseInOrder(tree.getRoot(), new TraversalContext<>(range), visitor);
        return new TraversalSummary<>(ctx.result);
    }

    public static <R> TraversalContext<R> traverseInOrder(Node node, TraversalContext<R> ctx, Visitor<R> visitor) {
        if (node == null) {
            return ctx;
        }
        if (ctx.shouldTraverseLeft(node)) {
            traverseInOrder(node.left(), ctx, visitor);
            if (ctx.stop) {
                return ctx;
            }
        }
        if (ctx.shouldVisit(node)) {
            visitor.visit(ctx, node);
            if (ctx.stop) {
                return ctx;
            }
        }
        if (ctx.shouldTraverseRight(node)) {
            traverseInOrder(node.right(), ctx, visitor);
        }
        return ctx;
    }

    @Override
    public <R> TraversalSummary<R> traverseReverseInOrder(TraversalRange range, Visitor<R> visitor) {
        TraversalContext<R> ctx = traverseReverseInOrder(tree.getRoot(), new TraversalContext<>(range), visitor);
        return new TraversalSummary<>(ctx.result);
    }

    public static <R> TraversalContext<R> traverseReverseInOrder(Node node, TraversalContext<R> ctx, Visitor<R> visitor) {
        if (node == null) {
            return ctx;
        }
        if (ctx.shouldTraverseRight(node)) {
            traverseReverseInOrder(node.right(), ctx, visitor);
            if (ctx.stop) {
                return ctx;
            }
        }
        if (ctx.shouldVisit(node)) {
            visitor.visit(ctx, node);
            if (ctx.stop) {
                return ctx;
            }
        }
        if (ctx.shouldTraverseLeft(node)) {
            traverseReverseInOrder(node.left(), ctx, visitor);
        }
        return ctx;
    }
}
