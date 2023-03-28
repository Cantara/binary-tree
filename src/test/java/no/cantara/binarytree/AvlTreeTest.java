package no.cantara.binarytree;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import static no.cantara.binarytree.BinaryTreeAssert.assertThatTree;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AvlTreeTest extends BinarySearchTreeTest {

    @Override
    protected BinarySearchTree createBST() {
        return new AvlTree(NodeFactory.defaultFactory());
    }

    @Override
    protected void assertSpecificTreeInvariants(BinarySearchTree tree) {
        validateAVLInvariant(tree.getRoot());
    }

    private void validateAVLInvariant(Node node) {
        if (node == null) return;

        int leftHeight = node.left() != null ? node.left().height() : -1;
        int rightHeight = node.right() != null ? node.right().height() : -1;

        validateHeight(node, leftHeight, rightHeight);
        validateBalanceFactor(node, leftHeight, rightHeight);

        // Validate AVL invariant for children (recursion)
        validateAVLInvariant(node.left());
        validateAVLInvariant(node.right());
    }

    private void validateHeight(Node node, int leftHeight, int rightHeight) {
        int expectedHeight = 1 + Math.max(leftHeight, rightHeight);
        if (node.height() != expectedHeight) {
            throw new AssertionError(
                    "Height of node %d is %d (expected: %d)"
                            .formatted(node.data(), node.height(), expectedHeight));
        }
    }

    private void validateBalanceFactor(Node node, int leftHeight, int rightHeight) {
        int bf = rightHeight - leftHeight;
        if (bf < -1 || bf > 1) {
            throw new AssertionError(
                    "Balance factor (bf) of node %d is %d (expected: -1 <= bf <= 1)"
                            .formatted(node.data(), bf));
        }
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
        AvlTree.updateHeight(node3);
        AvlTree.updateHeight(node4);
        AvlTree.updateHeight(node1);
        AvlTree.updateHeight(node2);
        // right subtree of node 5
        Node node9 = tree.insertNode(9, node5, SimpleBinaryTree.Side.RIGHT);
        Node node6 = tree.insertNode(6, node9, SimpleBinaryTree.Side.LEFT);
        Node node8 = tree.insertNode(8, node6, SimpleBinaryTree.Side.RIGHT);
        Node node15 = tree.insertNode(15, node9, SimpleBinaryTree.Side.RIGHT);
        Node node13 = tree.insertNode(13, node15, SimpleBinaryTree.Side.LEFT);
        Node node16 = tree.insertNode(16, node15, SimpleBinaryTree.Side.RIGHT);
        AvlTree.updateHeight(node16);
        AvlTree.updateHeight(node13);
        AvlTree.updateHeight(node15);
        AvlTree.updateHeight(node8);
        AvlTree.updateHeight(node6);
        AvlTree.updateHeight(node9);
        AvlTree.updateHeight(node5);

        // Right sub-tree of root
        Node node30 = tree.insertNode(30, root, SimpleBinaryTree.Side.RIGHT);
        Node node27 = tree.insertNode(27, node30, SimpleBinaryTree.Side.LEFT);
        Node node34 = tree.insertNode(34, node30, SimpleBinaryTree.Side.RIGHT);
        Node node28 = tree.insertNode(28, node27, SimpleBinaryTree.Side.RIGHT);
        AvlTree.updateHeight(node28);
        AvlTree.updateHeight(node34);
        AvlTree.updateHeight(node27);
        AvlTree.updateHeight(node30);

        AvlTree.updateHeight(root);

        List<Long> keysOrdered = List.of(1L, 2L, 3L, 4L, 5L, 6L, 8L, 9L, 13L, 15L, 16L, 25L, 27L, 28L, 30L, 34L);
        List<Long> keysRemaining = new ArrayList<>(keysOrdered);

        AvlTree avlTree = new AvlTree(NodeFactory.defaultFactory());
        avlTree.root = tree.getRoot();

        assertThatTree(avlTree)
                .isValid()
                .hasKeysInGivenOrder(keysRemaining);

        assertSpecificTreeInvariants(avlTree);

        // delete node 5
        Node deletedNode5 = avlTree.deleteNode(5L);
        assertEquals(node5, deletedNode5);

        keysRemaining.remove(5L);

        assertThatTree(avlTree)
                .isValid()
                .hasKeysInGivenOrder(keysRemaining);

        assertSpecificTreeInvariants(avlTree);

        // re-insert node 5
        Node newNode5 = avlTree.insertNode(5);
        keysRemaining.add(4, 5L);

        assertThatTree(avlTree)
                .isValid()
                .hasKeysInGivenOrder(keysRemaining);

        assertSpecificTreeInvariants(avlTree);
    }

    @Test
    public void checkRebalanceAfterInsertWorks() {
        List<Long> keys = List.of(112L, 597L, 471L, 593L, 878L, 476L, 782L);
        TreeSet<Long> keysOrdered = new TreeSet<>();
        AvlTree tree = new AvlTree(NodeFactory.defaultFactory());
        for (int i = 0; i < keys.size(); i++) {
            Long key = keys.get(i);
            if (key.equals(782L)) {
                // the next insert will break when re-balancing logic fails to propagate up the tree correctly
                TreePrinter.print(tree.getRoot());
                System.out.println();
                System.out.println();
            }
            tree.insertNode(key);
            keysOrdered.add(key);
        }
        TreePrinter.print(tree.getRoot());
        System.out.println();
        System.out.println();

        assertThatTree(tree)
                .isValid()
                .hasKeysInGivenOrder(keysOrdered.stream().toList());

        assertSpecificTreeInvariants(tree);
    }

    @Test
    public void deleteCase1() {
        List<Long> keys = List.of(1L, 0L, 3L, 2L);
        AvlTree tree = new AvlTree(NodeFactory.defaultFactory());
        TreeSet<Long> keysOrdered = new TreeSet<>();
        for (int i = 0; i < keys.size(); i++) {
            Long key = keys.get(i);
            tree.insertNode(key);
            keysOrdered.add(key);
        }
        TreePrinter.print(tree.getRoot());
        System.out.println();
        System.out.println();

        tree.deleteNode(0L);
        keysOrdered.remove(0L);

        TreePrinter.print(tree.getRoot());

        assertThatTree(tree)
                .isValid()
                .hasKeysInGivenOrder(keysOrdered.stream().toList());

        assertSpecificTreeInvariants(tree);
    }

    @Test
    public void deleteCase2() {
        List<Long> keys = List.of(3L, 1L, 5L, 0L, 2L, 4L, 6L, 7L);
        AvlTree tree = new AvlTree(NodeFactory.defaultFactory());
        TreeSet<Long> keysOrdered = new TreeSet<>();
        for (int i = 0; i < keys.size(); i++) {
            Long key = keys.get(i);
            tree.insertNode(key);
            keysOrdered.add(key);
        }
        TreePrinter.print(tree.getRoot());
        System.out.println();
        System.out.println();

        tree.deleteNode(3L);
        keysOrdered.remove(3L);

        TreePrinter.print(tree.getRoot());

        assertThatTree(tree)
                .isValid()
                .hasKeysInGivenOrder(keysOrdered.stream().toList());

        assertSpecificTreeInvariants(tree);
    }


    /*
     * Test NavigableMap features - using advanced traversals
     */

    @Test
    void navigableMap() {
        List<Long> keys = List.of(3L, 1L, 5L, 0L, 2L, 4L, 6L, 7L);
        AvlTree tree = new AvlTree(NodeFactory.defaultFactory());
        TreeSet<Long> keysOrdered = new TreeSet<>();
        for (Long key : keys) {
            tree.insertNode(key);
            keysOrdered.add(key);
        }
        TreePrinter.print(tree.getRoot());
        System.out.println();
        System.out.println();

        assertEquals(keysOrdered.stream().toList(), tree.entrySet().stream().map(Map.Entry::getKey).toList()); // Do not replace by keySet() - we mean to test entrySet here.
        assertEquals(keysOrdered.descendingSet().stream().toList(), tree.descendingMap().keySet().stream().toList());
        assertEquals(keysOrdered.stream().toList(), tree.keySet().stream().toList());
        assertEquals(keysOrdered.descendingSet().stream().toList(), tree.descendingMap().keySet().stream().toList());

        assertEquals(List.of(2L, 3L, 4L, 5L, 6L), tree.subMap(2L, true,6L, true).keySet().stream().toList());
        assertEquals(List.of(3L, 4L, 5L), tree.subMap(2L, false,6L, false).keySet().stream().toList());
        assertEquals(List.of(0L, 1L, 2L, 3L, 4L), tree.headMap(5L, false).keySet().stream().toList());
        assertEquals(List.of(0L, 1L, 2L, 3L, 4L, 5L), tree.headMap(5L, true).keySet().stream().toList());
        assertEquals(List.of(6L, 7L), tree.tailMap(5L, false).keySet().stream().toList());
        assertEquals(List.of(5L, 6L, 7L), tree.tailMap(5L, true).keySet().stream().toList());

        assertEquals(List.of(5L, 4L), tree.subMap(2L, 6L).descendingMap().subMap(4L, 10L).keySet().stream().toList());
    }
}
