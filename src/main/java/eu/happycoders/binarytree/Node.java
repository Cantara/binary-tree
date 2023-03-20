package eu.happycoders.binarytree;

/**
 * A node in a binary tree, containing an <code>int</code> data.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class Node {

  // also called "value" in a binary tree
  // also called "key" in a binary search tree
  private long data;

  private Node left;
  private Node right;
  private Node parent; // used in SimpleBinaryTree + red-black tree

  private int height; // used in AVL tree
  private boolean color; // used in red-black tree

  /**
   * Constructs a new node with the given data.
   *
   * @param data the data to store in the node
   */
  public Node(long data) {
    this.data = data;
  }

  public long data() {
    return data;
  }

  public Node data(long data) {
    this.data = data;
    return this;
  }

  public Node left() {
    return left;
  }

  public Node left(Node left) {
    this.left = left;
    return this;
  }

  public Node right() {
    return right;
  }

  public Node right(Node right) {
    this.right = right;
    return this;
  }

  public Node parent() {
    return parent;
  }

  public Node parent(Node parent) {
    this.parent = parent;
    return this;
  }

  public int height() {
    return height;
  }

  public Node height(int height) {
    this.height = height;
    return this;
  }

  public boolean color() {
    return color;
  }

  public Node color(boolean color) {
    this.color = color;
    return this;
  }
}
