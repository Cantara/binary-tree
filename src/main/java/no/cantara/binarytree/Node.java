package no.cantara.binarytree;

/**
 * A node in a binary tree, containing an <code>long</code> data.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public interface Node extends TreePrinter.PrintableNode {

  boolean RED = false;
  boolean BLACK = true;

  long data();

  Node data(long data);

  Node left();

  Node left(Node left);

  Node right();

  Node right(Node right);

  Node parent();

  Node parent(Node parent);

  int height();

  Node height(int height);

  boolean color();

  Node color(boolean color);

  boolean isNil();

  /**
   * Called by delete operations to allow removal any additional internal state and/or to remove the node from its environment
   *
   * NOTE: This is currently only used by the iterative binary search tree variants, specifically AVLTree. The reason is that
   * the recursive variants currently re-use nodes to hold state from another node instead of deleting the node, this is not compatible with use of this delete method.
   * In other words, if you require internal node state that relies on this method being called, then use the AVLTree data-structure only.
   */
  void delete();

  /**
   * Copy all non-navigable state from source node to this node, leaving all navigable state in this node untouched.
   *
   * Non-navigable state is everything else than what can be manipulated through this interface. Navigable state is
   * data/key, left, right, parent, color, height and any other state needed by binary search tree implementations.
   *
   * Examples of non-navigable state are:
   *  - Binary blob payload stored in a specialized node
   *  - Other properties of the node that does not affect tree navigation state.
   *
   * @param source
   */
  void copyNonNavigableStateFrom(Node source);

  @Override
  default String getText() {
    return data() + ":" + height();
  }
}
