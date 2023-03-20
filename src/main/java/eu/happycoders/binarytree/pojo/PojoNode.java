package eu.happycoders.binarytree.pojo;

import eu.happycoders.binarytree.Node;

/**
 * A node in a binary tree, containing an <code>long</code> data.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public class PojoNode implements Node {

  // also called "value" in a binary tree
  // also called "key" in a binary search tree
  private long data;

  private PojoNode left;
  private PojoNode right;
  private PojoNode parent; // used in SimpleBinaryTree + red-black tree

  private int height; // used in AVL tree
  private boolean color; // used in red-black tree

  /**
   * Constructs a new node with the given data.
   *
   * @param data the data to store in the node
   */
  PojoNode(long data) {
    this.data = data;
  }

  @Override
  public long data() {
    return data;
  }

  @Override
  public PojoNode data(long data) {
    this.data = data;
    return this;
  }

  @Override
  public PojoNode left() {
    return left;
  }

  @Override
  public PojoNode left(Node left) {
    this.left = (PojoNode) left;
    return this;
  }

  @Override
  public PojoNode right() {
    return right;
  }

  @Override
  public PojoNode right(Node right) {
    this.right = (PojoNode) right;
    return this;
  }

  @Override
  public PojoNode parent() {
    return parent;
  }

  @Override
  public PojoNode parent(Node parent) {
    this.parent = (PojoNode) parent;
    return this;
  }

  @Override
  public int height() {
    return height;
  }

  @Override
  public PojoNode height(int height) {
    this.height = height;
    return this;
  }

  @Override
  public boolean color() {
    return color;
  }

  @Override
  public PojoNode color(boolean color) {
    this.color = color;
    return this;
  }

  @Override
  public boolean isNil() {
    return false;
  }
}
