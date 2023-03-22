package no.cantara.binarytree;

/**
 * An extension of the recursive binary search tree implementation that also set's <code>Node.parent
 * </code> when inserting or deleting nodes.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class BinarySearchTreeRecursiveWithParent extends BinarySearchTreeRecursive {

  public BinarySearchTreeRecursiveWithParent(NodeFactory factory) {
    super(factory);
  }

  @Override
  Node insertNode(long key, Node node) {
    node = super.insertNode(key, node);

    // Set parents
    if (node.left() != null) {
      node.left().parent(node);
    }
    if (node.right() != null) {
      node.right().parent(node);
    }

    return node;
  }

  @Override
  Node deleteNode(long key, Node node) {
    Node newNode = super.deleteNode(key, node);

    // Set parents
    if (newNode != null && node != null && (newNode.equals(node.right()) || newNode.equals(node.left()))) {
      newNode.parent(node.parent());
    }

    return newNode;
  }
}
