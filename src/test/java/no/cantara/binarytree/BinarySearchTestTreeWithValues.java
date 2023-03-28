package no.cantara.binarytree;

public class BinarySearchTestTreeWithValues implements BinaryTree {

  private static final Node ROOT;

  static {
    NodeFactory factory = NodeFactory.defaultFactory();
    AvlTree tree = new AvlTree(factory);
    tree.insertNode(3);
    tree.insertNode(1);
    tree.insertNode(13);
    tree.insertNode(5);
    tree.insertNode(6);
    tree.insertNode(10);
    tree.insertNode(11);
    tree.insertNode(16);
    tree.insertNode(15);
    tree.insertNode(9);
    tree.insertNode(4);
    tree.insertNode(2);
    ROOT = tree.getRoot();
    System.out.printf("   ::: BST sample tree :::%n");
    TreePrinter.print(ROOT);
  }

  static final long startKey = 5L;
  static final long endKey = 13L;

  static final Long[] PRE_ORDER_VALUES = {6L, 3L, 1L, 2L, 5L, 4L, 11L, 10L, 9L, 15L, 13L, 16L};
  static final Long[] POST_ORDER_VALUES = {2L, 1L, 4L, 5L, 3L, 9L, 10L, 13L, 16L, 15L, 11L, 6L};
  static final Long[] IN_ORDER_VALUES = {1L, 2L, 3L, 4L, 5L, 6L, 9L, 10L, 11L, 13L, 15L, 16L};
  static final Long[] IN_ORDER_VALUES_CLOSED_INCLUSIVE = {5L, 6L, 9L, 10L, 11L, 13L};
  static final Long[] IN_ORDER_VALUES_CLOSED_EXCLUSIVE = {6L, 9L, 10L, 11L};
  static final Long[] IN_ORDER_VALUES_OPEN_START_INCLUSIVE = {1L, 2L, 3L, 4L, 5L, 6L, 9L, 10L, 11L, 13L};
  static final Long[] IN_ORDER_VALUES_OPEN_START_EXCLUSIVE = {1L, 2L, 3L, 4L, 5L, 6L, 9L, 10L, 11L};
  static final Long[] IN_ORDER_VALUES_OPEN_END_INCLUSIVE = {5L, 6L, 9L, 10L, 11L, 13L, 15L, 16L};
  static final Long[] IN_ORDER_VALUES_OPEN_END_EXCLUSIVE = {6L, 9L, 10L, 11L, 13L, 15L, 16L};
  static final Long[] REVERSE_IN_ORDER_VALUES = {16L, 15L, 13L, 11L, 10L, 9L, 6L, 5L, 4L, 3L, 2L, 1L};

  @Override
  public Node getRoot() {
    return ROOT;
  }
}
