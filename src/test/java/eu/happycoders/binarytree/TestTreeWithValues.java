package eu.happycoders.binarytree;

public class TestTreeWithValues implements BinaryTree {

  private static final Node ROOT;

  static {
    ROOT = new Node(3);
    ROOT.left(new Node(1));
    ROOT.left().left(new Node(13));
    ROOT.left().right(new Node(5));
    ROOT.left().right().left(new Node(6));
    ROOT.right(new Node(10));
    ROOT.right().left(new Node(11));
    ROOT.right().right(new Node(16));
    ROOT.right().right().left(new Node(15));
    ROOT.right().right().left().left(new Node(9));
    ROOT.right().right().left().right(new Node(4));
    ROOT.right().right().right(new Node(2));
  }

  static final Long[] PRE_ORDER_VALUES = {3L, 1L, 13L, 5L, 6L, 10L, 11L, 16L, 15L, 9L, 4L, 2L};
  static final Long[] POST_ORDER_VALUES = {13L, 6L, 5L, 1L, 11L, 9L, 4L, 15L, 2L, 16L, 10L, 3L};
  static final Long[] IN_ORDER_VALUES = {13L, 1L, 6L, 5L, 3L, 11L, 10L, 9L, 15L, 4L, 16L, 2L};
  static final Long[] REVERSE_IN_ORDER_VALUES = {2L, 16L, 4L, 15L, 9L, 10L, 11L, 3L, 5L, 6L, 1L, 13L};
  static final Long[] LEVEL_ORDER_VALUES = {3L, 1L, 10L, 13L, 5L, 11L, 16L, 6L, 15L, 2L, 9L, 4L};

  @Override
  public Node getRoot() {
    return ROOT;
  }
}
