package eu.happycoders.binarytree;

import org.junit.jupiter.api.RepeatedTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

import static eu.happycoders.binarytree.BinaryTreeAssert.assertThatTree;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

abstract class BinarySearchTreeTest {

  private static final int TEST_TREE_MIN_SIZE = 1;
  private static final int TEST_TREE_MAX_SIZE = 1000;

  @RepeatedTest(100)
  void insertingKeysShouldCreateAValidBSTWithKeysInOrder() {
    List<Long> keysOrdered = createOrderedSequenceOfKeys();

    var tree = createBST();
    insertKeysInRandomOrder(tree, keysOrdered);

    assertThatTree(tree) //
        .isValid()
        .hasKeysInGivenOrder(keysOrdered);

    assertSpecificTreeInvariants(tree);
  }

  @RepeatedTest(100)
  void shouldThrowExceptionWhenInsertingExistingKey() {
    List<Long> keysOrdered = createOrderedSequenceOfKeys();

    var tree = createBST();
    insertKeysInRandomOrder(tree, keysOrdered);

    long randomKey = pickRandomKey(keysOrdered);
    assertThrows(IllegalArgumentException.class, () -> tree.insertNode(randomKey));
  }

  @RepeatedTest(100)
  void searchFindsAllKeys() {
    List<Long> keysOrdered = createOrderedSequenceOfKeys();

    var tree = createBST();
    insertKeysInRandomOrder(tree, keysOrdered);

    for (Long key : keysOrdered) {
      Node node = tree.searchNode(key);
      assertThat(node.data(), is(key));
    }
  }

  @RepeatedTest(100)
  void searchReturnsNullWhenKeyNotFound() {
    List<Long> keysOrdered = createOrderedSequenceOfKeys();

    var tree = createBST();
    insertKeysInRandomOrder(tree, keysOrdered);

    Long highestKey = keysOrdered.get(keysOrdered.size() - 1);
    assertThat(tree.searchNode(highestKey + 1), is(nullValue()));
  }

  @RepeatedTest(100)
  void deleteNodeShouldLeaveAValidBSTWithoutTheDeletedNode() {
    List<Long> keysOrdered = createOrderedSequenceOfKeys();

    var tree = createBST();
    insertKeysInRandomOrder(tree, keysOrdered);

    // Remove every key... and after each key check if the BST is valid
    List<Long> keysToDelete = shuffle(keysOrdered);
    List<Long> keysRemaining = new ArrayList<>(keysOrdered);

    for (Long keyToDelete : keysToDelete) {
      tree.deleteNode(keyToDelete);

      keysRemaining.remove(keyToDelete);

      assertThatTree(tree) //
          .isValid()
          .hasKeysInGivenOrder(keysRemaining);

      assertSpecificTreeInvariants(tree);
    }
  }

  @RepeatedTest(100)
  void deleteNotExistingKeyShouldNotChangeTheBST() {
    List<Long> keysOrdered = createOrderedSequenceOfKeys();
    Long highestKey = keysOrdered.get(keysOrdered.size() - 1);

    var tree = createBST();
    insertKeysInRandomOrder(tree, keysOrdered);

    tree.deleteNode(highestKey + 1);

    assertThatTree(tree) //
        .isValid()
        .hasKeysInGivenOrder(keysOrdered);

    assertSpecificTreeInvariants(tree);
  }

  /**
   * Override this in tests for specific trees, e.g. AVL trees or red-black trees.
   *
   * @param tree the tree
   */
  protected void assertSpecificTreeInvariants(BinarySearchTree tree) {}

  protected abstract BinarySearchTree createBST();

  private List<Long> createOrderedSequenceOfKeys() {
    int size = ThreadLocalRandom.current().nextInt(TEST_TREE_MIN_SIZE, TEST_TREE_MAX_SIZE);
    return LongStream.range(0, size).boxed().toList();
  }

  private void insertKeysInRandomOrder(BinarySearchTree tree, List<Long> keysOrdered) {
    List<Long> keys = shuffle(keysOrdered);
    for (Long key : keys) {
      tree.insertNode(key);
    }
  }

  private List<Long> shuffle(List<Long> keysOrdered) {
    List<Long> keys = new ArrayList<>(keysOrdered);
    Collections.shuffle(keys);
    return Collections.unmodifiableList(keys);
  }

  private long pickRandomKey(List<Long> keysOrdered) {
    int randomIndex = ThreadLocalRandom.current().nextInt(keysOrdered.size());
    return keysOrdered.get(randomIndex);
  }

  @RepeatedTest(100)
  void inOrderSuccessor() {
    List<Long> keysOrdered = createOrderedSequenceOfKeys();
    var tree = createBST();
    insertKeysInRandomOrder(tree, keysOrdered);

    for (int i=0; i < keysOrdered.size() - 1; i++) {
      Long current = keysOrdered.get(i);
      Long next = keysOrdered.get(i + 1);
      Node node = BinarySearchTree.inOrderSuccessor(tree.getRoot(), tree.searchNode(current));
      assertThat(node.data(), is(next));
    }
    {
      Long lastKey = keysOrdered.get(keysOrdered.size() - 1);
      Node node = BinarySearchTree.inOrderSuccessor(tree.getRoot(), tree.searchNode(lastKey));
      assertThat(node, nullValue());
    }
  }

  @RepeatedTest(100)
  void inOrderPredecessor() {
    List<Long> keysOrdered = createOrderedSequenceOfKeys();
    var tree = createBST();
    insertKeysInRandomOrder(tree, keysOrdered);

    {
      Long firstKey = keysOrdered.get(0);
      Node node = BinarySearchTree.inOrderPredecessor(tree.getRoot(), tree.searchNode(firstKey));
      assertThat(node, nullValue());
    }
    for (int i=1; i < keysOrdered.size(); i++) {
      Long current = keysOrdered.get(i);
      Long prev = keysOrdered.get(i - 1);
      Node node = BinarySearchTree.inOrderPredecessor(tree.getRoot(), tree.searchNode(current));
      assertThat(node.data(), is(prev));
    }
  }
}
