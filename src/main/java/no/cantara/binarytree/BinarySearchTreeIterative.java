package no.cantara.binarytree;

/**
 * An iterative binary search tree implementation with <code>int</code> keys.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class BinarySearchTreeIterative extends AbstractBinarySearchTree {

  public BinarySearchTreeIterative(NodeFactory factory) {
    super(factory, TraversalRange.OPEN, true, null);
  }

  public BinarySearchTreeIterative(NodeFactory factory, TraversalRange range, boolean traversalDirection, Node root) {
    super(factory, range, traversalDirection, root);
  }

  @Override
  public BinarySearchTreeIterative subTree(TraversalRange subRange, boolean traversalDirection) {
    return new BinarySearchTreeIterative(factory, range.subRange(subRange), traversalDirection, root);
  }

  @Override
  public Node searchNode(long key) {
    Node node = root;
    while (node != null) {
      if (key == node.data()) {
        return node;
      } else if (key < node.data()) {
        node = node.left();
      } else {
        node = node.right();
      }
    }

    return null;
  }

  @Override
  public Node insertNode(long key) {
    return insertNode(key, new InsertionContext());
  }

  public Node insertNode(long key, InsertionContext ctx) {
    Node newNode = factory.createNode(key);
    ctx.insertedNode(newNode);

    if (root == null) {
      root = newNode;
      ctx.affectedPath.add(true, newNode);
      return newNode;
    }

    ctx.affectedPath.add(true, root);
    Node node = root;
    while (true) {
      // Traverse the tree to the left or right depending on the key
      if (key < node.data()) {
        if (node.left() != null) {
          // Left sub-tree exists --> follow
          node = node.left();
          ctx.affectedPath.add(true, node);
        } else {
          // Left sub-tree does not exist --> insert new node as left child
          node.left(newNode);
          ctx.affectedPath.add(true, newNode);
          return newNode;
        }
      } else if (key > node.data()) {
        if (node.right() != null) {
          // Right sub-tree exists --> follow
          node = node.right();
          ctx.affectedPath.add(false, node);
        } else {
          // Right sub-tree does not exist --> insert new node as right child
          node.right(newNode);
          ctx.affectedPath.add(false, newNode);
          return newNode;
        }
      } else {
        throw new IllegalArgumentException("BST already contains a node with key " + key);
      }
    }
  }

  @Override
  @SuppressWarnings("squid:S2259") // parent won't be null as it's used only if node != root
  public Node deleteNode(long key) {
    DeletionContext ctx = new DeletionContext();
    deleteNode(key, ctx);
    return ctx.deletedNode;
  }

  Node deleteNode(long key, DeletionContext ctx) {
    Node node = root;
    Node parent = null;

    // Find the node to be deleted
    boolean nodeIsLeftChildOfParent = true; // ignored (so the initial value does not matter) in the case where parent == null
    while (node != null && node.data() != key) {
      // Traverse the tree to the left or right depending on the key
      parent = node;
      ctx.affectedPath.add(nodeIsLeftChildOfParent, node);
      if (key < node.data()) {
        node = node.left();
        nodeIsLeftChildOfParent = true;
      } else {
        node = node.right();
        nodeIsLeftChildOfParent = false;
      }
    }

    // Node not found?
    if (node == null) {
      return null;
    }

    // At this point, "node" is the node to be deleted
    ctx.deletedNode(node);

    // Node has at most one child --> replace node by its single child
    if (node.left() == null || node.right() == null) {
      deleteNodeWithZeroOrOneChild(key, node, parent);
    }

    // Node has two children
    else {
      Node movedUpNode = deleteNodeWithTwoChildren(node, parent, nodeIsLeftChildOfParent, ctx);
      if (parent == null) {
        root = movedUpNode;
      }
    }

    return node;
  }

  private void deleteNodeWithZeroOrOneChild(long key, Node node, Node parent) {
    Node singleChild = node.left() != null ? node.left() : node.right();

    if (node.equals(root)) {
      root = singleChild;
    } else if (key < parent.data()) {
      parent.left(singleChild);
    } else {
      parent.right(singleChild);
    }
  }

  static Node deleteNodeWithTwoChildren(Node node, Node parent, boolean nodeIsLeftChildOfParent, DeletionContext ctx) {
    // TODO determine whether it's best to use successor or predecessor which depends on which will keep the tree most balanced

    // Find minimum node of right subtree ("inorder successor" of current node)
    BinaryTreePath pathToSuccessor = new BinaryTreePath();
    Node inOrderSuccessor = node.right();
    Node inOrderSuccessorParent = node;
    boolean inOrderSuccessorParentIsLeftChildOfParent = false;
    while (inOrderSuccessor.left() != null) {
      inOrderSuccessorParent = inOrderSuccessor;
      inOrderSuccessor = inOrderSuccessor.left();
      pathToSuccessor.add(inOrderSuccessorParentIsLeftChildOfParent, inOrderSuccessorParent);
      inOrderSuccessorParentIsLeftChildOfParent = true;
    }
    ctx.parentOfSuccessorNode(inOrderSuccessorParent);
    ctx.successorNode(inOrderSuccessor);

    ctx.affectedPath.add(nodeIsLeftChildOfParent, inOrderSuccessor); // moved up to take the place of the deleted node
    for (BinaryTreePathElement pathElement : pathToSuccessor) {
      ctx.affectedPath.add(pathElement.leftChildOfParent, pathElement.node);
    }

    // Replace node with inorder successor

    if (parent != null) {
      if (nodeIsLeftChildOfParent) {
        parent.left(inOrderSuccessor);
      } else {
        parent.right(inOrderSuccessor);
      }
    }

    // Case a) Inorder successor is the deleted node's right child
    if (inOrderSuccessor.equals(node.right())) {
      // --> Replace deleted node with inOrderSuccessor directly
      inOrderSuccessor.left(node.left());
    }

    // Case b) Inorder successor is further down, meaning, it's a left child
    else {
      // --> Replace inorder successor's parent's left child
      //     with inorder successor's right child
      inOrderSuccessorParent.left(inOrderSuccessor.right());
      inOrderSuccessor.left(node.left());
      inOrderSuccessor.right(node.right());
    }

    return inOrderSuccessor;
  }
}
