package eu.happycoders.binarytree;

/**
 * A node in a binary tree, containing an <code>long</code> data.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public interface Node {

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
}
