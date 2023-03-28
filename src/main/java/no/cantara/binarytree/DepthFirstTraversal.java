package no.cantara.binarytree;

/**
 * Interface for depth-first (DFS) traversal on a binary tree.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public interface DepthFirstTraversal {

  /**
   * Traverses the tree in pre-order and calls the {@link NodeVisitor#visit(Node)} method on each
   * node.
   *
   * @param visitor the visitor
   */
  void traversePreOrder(NodeVisitor visitor);

  /**
   * Traverses the tree in post-order and calls the {@link NodeVisitor#visit(Node)} method on each
   * node.
   *
   * @param visitor the visitor
   */
  void traversePostOrder(NodeVisitor visitor);

  /**
   * Traverses the tree in-order and calls the {@link NodeVisitor#visit(Node)} method on each node.
   *
   * @param visitor the visitor
   */
  void traverseInOrder(NodeVisitor visitor);

  /**
   * Traverses the tree reverse in-order and calls the {@link NodeVisitor#visit(Node)} method on
   * each node.
   *
   * @param visitor the visitor
   */
  void traverseReverseInOrder(NodeVisitor visitor);

  /**
   * Traverses the tree in pre-order and calls the {@link NodeVisitor#visit(Node)} method on each
   * node.
   *
   * @param ctx the traversal context
   * @param visitor the visitor
   */
  <R> TraversalSummary<R> traversePreOrder(TraversalRange ctx, Visitor<R> visitor);

  /**
   * Traverses the tree in post-order and calls the {@link NodeVisitor#visit(Node)} method on each
   * node.
   *
   * @param ctx the traversal context
   * @param visitor the visitor
   */
  <R> TraversalSummary<R> traversePostOrder(TraversalRange ctx, Visitor<R> visitor);

  /**
   * Traverses the tree in-order and calls the {@link NodeVisitor#visit(Node)} method on each node.
   *
   * @param ctx the traversal context
   * @param visitor the visitor
   */
  <R> TraversalSummary<R> traverseInOrder(TraversalRange ctx, Visitor<R> visitor);

  /**
   * Traverses the tree reverse in-order and calls the {@link NodeVisitor#visit(Node)} method on
   * each node.
   *
   * @param ctx the traversal context
   * @param visitor the visitor
   */
  <R> TraversalSummary<R> traverseReverseInOrder(TraversalRange ctx, Visitor<R> visitor);
}
