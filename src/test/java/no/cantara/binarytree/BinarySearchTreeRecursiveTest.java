package no.cantara.binarytree;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static no.cantara.binarytree.BinaryTreeAssert.assertThatTree;

class BinarySearchTreeRecursiveTest extends BinarySearchTreeTest {

  @Override
  protected BinarySearchTree createBST() {
    return new BinarySearchTreeRecursive(NodeFactory.defaultFactory());
  }

  @Test
  void deleteSpecificComplexTestThatIsEasyToReasonInDetailAbout() {
    SimpleBinaryTree tree = new SimpleBinaryTree(NodeFactory.defaultFactory());

    Node root = tree.insertRoot(25);

    // Left sub-tree of root
    // this matches https://www.happycoders.eu/algorithms/binary-search-tree-java/
    // Case C: Deleting a Node With Two Children from the recursive example
    Node node5 = tree.insertNode(5, root, SimpleBinaryTree.Side.LEFT);
    // left subtree of node 5
    Node node2 = tree.insertNode(2, node5, SimpleBinaryTree.Side.LEFT);
    Node node1 = tree.insertNode(1, node2, SimpleBinaryTree.Side.LEFT);
    Node node4 = tree.insertNode(4, node2, SimpleBinaryTree.Side.RIGHT);
    Node node3 = tree.insertNode(3, node4, SimpleBinaryTree.Side.LEFT);
    // right subtree of node 5
    Node node9 = tree.insertNode(9, node5, SimpleBinaryTree.Side.RIGHT);
    Node node6 = tree.insertNode(6, node9, SimpleBinaryTree.Side.LEFT);
    Node node8 = tree.insertNode(8, node6, SimpleBinaryTree.Side.RIGHT);
    Node node15 = tree.insertNode(15, node9, SimpleBinaryTree.Side.RIGHT);
    Node node13 = tree.insertNode(13, node15, SimpleBinaryTree.Side.LEFT);
    Node node16 = tree.insertNode(16, node15, SimpleBinaryTree.Side.RIGHT);

    // Right sub-tree of root
    Node node30 = tree.insertNode(30, root, SimpleBinaryTree.Side.RIGHT);
    Node node27 = tree.insertNode(27, node30, SimpleBinaryTree.Side.LEFT);
    Node node34 = tree.insertNode(34, node30, SimpleBinaryTree.Side.RIGHT);
    Node node28 = tree.insertNode(28, node27, SimpleBinaryTree.Side.RIGHT);

    List<Long> keysOrdered = List.of(1L, 2L, 3L, 4L, 5L, 6L, 8L, 9L, 13L, 15L, 16L, 25L, 27L, 28L, 30L, 34L);
    List<Long> keysRemaining = new ArrayList<>(keysOrdered);

    BinarySearchTreeRecursive bstr = new BinarySearchTreeRecursive(NodeFactory.defaultFactory());
    bstr.root = tree.getRoot();

    bstr.deleteNode(5L);

    keysRemaining.remove(5L);

    assertThatTree(bstr)
            .isValid()
            .hasKeysInGivenOrder(keysRemaining);

    assertSpecificTreeInvariants(bstr);
  }
}
