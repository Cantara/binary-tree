package no.cantara.binarytree;

/**
 * A recursive binary search tree implementation with <code>int</code> keys.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class BinarySearchTreeRecursive extends BaseBinaryTree implements BinarySearchTree {

  private final NodeFactory factory;

  public BinarySearchTreeRecursive(NodeFactory factory) {
    this.factory = factory;
  }

  @Override
  public Node searchNode(long key) {
    return searchNode(key, root);
  }

  private Node searchNode(long key, Node node) {
    if (node == null) {
      return null;
    }

    if (key == node.data()) {
      return node;
    } else if (key < node.data()) {
      return searchNode(key, node.left());
    } else {
      return searchNode(key, node.right());
    }
  }

  @Override
  public Node insertNode(long key) {
    if (root == null) {
      root = factory.createNode(key);
      return root;
    }
    InsertionContext ctx = new InsertionContext();
    insertNode(key, root, ctx);
    return ctx.insertedNode;
  }

  Node insertNode(long key, Node node, InsertionContext ctx) {
    // No node at current position --> store new node at current position
    if (node == null) {
      node = factory.createNode(key);
      ctx.insertedNode(node);
    }

    // Otherwise, traverse the tree to the left or right depending on the key
    else if (key < node.data()) {
      node.left(insertNode(key, node.left(), ctx));
    } else if (key > node.data()) {
      node.right(insertNode(key, node.right(), ctx));
    } else {
      throw new IllegalArgumentException("BST already contains a node with key " + key);
    }

    return node;
  }

  @Override
  public Node deleteNode(long key) {
    DeletionContext ctx = new DeletionContext();
    root = deleteNode(key, root, null, true, ctx);
    return ctx.deletedNode;
  }

  Node deleteNode(long key, Node node, Node parent, boolean nodeIsLeftChildOfParent, DeletionContext ctx) {
    // No node at current position --> go up the recursion
    if (node == null) {
      return null;
    }

    // Traverse the tree to the left or right depending on the key
    if (key < node.data()) {
      node.left(deleteNode(key, node.left(), node, true, ctx));
    } else if (key > node.data()) {
      node.right(deleteNode(key, node.right(), node, false, ctx));
    }

    // At this point, "node" is the node to be deleted

    // Node has no children --> just delete it
    else if (node.left() == null && node.right() == null) {
      ctx.deletedNode(node);
      node = null;
    }

    // Node has only one child --> replace node by its single child
    else if (node.left() == null) {
      ctx.deletedNode(node);
      node = node.right();
    } else if (node.right() == null) {
      ctx.deletedNode(node);
      node = node.left();
    }

    // Node has two children
    else {
      ctx.deletedNode(node);
      deleteNodeWithTwoChildren(node);
    }

    return node;
  }

  private void deleteNodeWithTwoChildren(Node node) {
    // Find minimum node of right subtree ("inorder successor" of current node)
    Node inOrderSuccessor = findMinimum(node.right());

    // Copy inorder successor's data to current node
    node.data(inOrderSuccessor.data());
    node.copyNonNavigableStateFrom(inOrderSuccessor);

    // Delete inorder successor recursively
    node.right(deleteNode(inOrderSuccessor.data(), node.right(), node, !inOrderSuccessor.equals(node.right()), new DeletionContext()));
  }

  private Node findMinimum(Node node) {
    while (node.left() != null) {
      node = node.left();
    }
    return node;
  }
}
