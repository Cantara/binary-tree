package no.cantara.binarytree;

import java.util.Iterator;

import static java.lang.Math.max;

/**
 * An AVL tree implementation with <code>int</code> keys.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class AvlTree extends BinarySearchTreeIterative {

  public AvlTree(NodeFactory factory) {
    super(factory);
  }

  public AvlTree(NodeFactory factory, TraversalRange range, boolean traversalDirection, Node root) {
    super(factory, range, traversalDirection, root);
  }

  @Override
  public BinarySearchTreeIterative subTree(TraversalRange subRange, boolean traversalDirection) {
    return new AvlTree(factory, range.subRange(subRange), traversalDirection, root);
  }

  @Override
  public Node insertNode(long key) {
    InsertionContext ctx = new InsertionContext();
    Node node = super.insertNode(key, ctx);

    updateHeightsAndRebalancePath(ctx.affectedPath);
    return node;
  }

  private Node updateHeightsAndRebalancePath(BinaryTreePath affectedPath) {
    Node lastRebalanceResult = null;
    Node lastAffectedNode = null;

    Iterator<BinaryTreePathElement> reverseAffectedPathIterator = affectedPath.descendingIterator();
    BinaryTreePathElement next = null;
    if (reverseAffectedPathIterator.hasNext()) {
      next = reverseAffectedPathIterator.next();
      updateHeight(next.node);
      lastRebalanceResult = rebalance(next.node);
      lastAffectedNode = next.node;
    }
    while (reverseAffectedPathIterator.hasNext()) {
      BinaryTreePathElement child = next;
      next = reverseAffectedPathIterator.next();
      if (!child.node.equals(lastRebalanceResult)) {
        // rotate has happened - root of subtree changed
        if (child.leftChildOfParent) {
          next.node.left(lastRebalanceResult);
        } else {
          next.node.right(lastRebalanceResult);
        }
      }
      updateHeight(next.node);
      lastRebalanceResult = rebalance(next.node);
      lastAffectedNode = next.node;
    }

    if (root.equals(lastAffectedNode) && !root.equals(lastRebalanceResult)) {
      root = lastRebalanceResult;
    }

    return lastRebalanceResult;
  }

  @Override
  Node deleteNode(long key, DeletionContext ctx) {
    Node node = super.deleteNode(key, ctx);

    // Node is null if the tree doesn't contain the key
    if (node == null) {
      return null;
    }

    node.delete(); // allow node to delete any other internal state, e.g. relationships to other nodes that has nothing to do with this tree structure.

    if (root == null) {
      // the only node in tree was deleted
      return node;
    }

    return updateHeightsAndRebalancePath(ctx.affectedPath);
  }

  static void updateHeight(Node node) {
    int leftChildHeight = height(node.left());
    int rightChildHeight = height(node.right());
    node.height(max(leftChildHeight, rightChildHeight) + 1);
  }

  private static Node rebalance(Node node) {
    int balanceFactor = balanceFactor(node);

    // Left-heavy?
    if (balanceFactor < -1) {
      if (balanceFactor(node.left()) <= 0) {
        // Rotate right
        node = rotateRight(node);
      } else {
        // Rotate left-right
        node.left(rotateLeft(node.left()));
        node = rotateRight(node);
      }
    }

    // Right-heavy?
    if (balanceFactor > 1) {
      if (balanceFactor(node.right()) >= 0) {
        // Rotate left
        node = rotateLeft(node);
      } else {
        // Rotate right-left
        node.right(rotateRight(node.right()));
        node = rotateLeft(node);
      }
    }

    return node;
  }

  private static Node rotateRight(Node node) {
    Node leftChild = node.left();

    node.left(leftChild.right());
    leftChild.right(node);

    updateHeight(node);
    updateHeight(leftChild);

    return leftChild;
  }

  private static Node rotateLeft(Node node) {
    Node rightChild = node.right();

    node.right(rightChild.left());
    rightChild.left(node);

    updateHeight(node);
    updateHeight(rightChild);

    return rightChild;
  }

  private static int balanceFactor(Node node) {
    return height(node.right()) - height(node.left());
  }

  private static int height(Node node) {
    return node != null ? node.height() : -1;
  }

  @Override
  protected void appendNodeToString(Node node, StringBuilder builder) {
    builder
        .append(node.data())
        .append("[H=")
        .append(height(node))
        .append(", BF=")
        .append(balanceFactor(node))
        .append(']');
  }
}
