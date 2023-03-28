package no.cantara.binarytree;

import org.junit.jupiter.api.RepeatedTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.LongStream;

import static no.cantara.binarytree.BinaryTreeAssert.assertThatTree;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

abstract class BinarySearchTreeTest {

  private static final int TEST_TREE_MIN_SIZE = 1;
  private static final int TEST_TREE_MAX_SIZE = 1000;

  private static final boolean KEEP_PRINTED_TREE_STATE_BEFORE_FAIL = false; // change this to false when tests fail to identify tree state before failure

  @RepeatedTest(100)
  void insertingKeysShouldCreateAValidBSTWithKeysInOrder() {
    List<Long> keysOrdered = createOrderedSequenceOfKeys();

    var tree = createBST();
    insertKeysInRandomOrder(tree, keysOrdered);

    assertThatTree(tree)
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

    StringBuilder sb = new StringBuilder();
    for (Long keyToDelete : keysToDelete) {
      sb.setLength(0);
      if (KEEP_PRINTED_TREE_STATE_BEFORE_FAIL) {
        TreePrinter.print(tree.getRoot(), sb);
      }

      tree.deleteNode(keyToDelete);

      keysRemaining.remove(keyToDelete);

      try {
        assertThatTree(tree)
                .isValid()
                .hasKeysInGivenOrder(keysRemaining);

        assertSpecificTreeInvariants(tree);
      } catch (Throwable t) {
        System.out.printf(" ::: GOOD STATE BEFORE DELETE :::%n");
        System.out.printf("%s", sb);
        System.out.println();
        System.out.printf("Key to delete: " + keyToDelete);
        System.out.printf(" ::: BAD STATE AFTER DELETE :::%n");
        TreePrinter.print(tree.getRoot());
        throw t;
      }
    }
  }

  @RepeatedTest(100)
  void deleteNotExistingKeyShouldNotChangeTheBST() {
    List<Long> keysOrdered = createOrderedSequenceOfKeys();
    Long highestKey = keysOrdered.get(keysOrdered.size() - 1);

    var tree = createBST();
    insertKeysInRandomOrder(tree, keysOrdered);

    tree.deleteNode(highestKey + 1);

    assertThatTree(tree)
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

  protected List<Long> createOrderedSequenceOfKeys() {
    int size = ThreadLocalRandom.current().nextInt(TEST_TREE_MIN_SIZE, TEST_TREE_MAX_SIZE);
    return LongStream.range(0, size).boxed().toList();
  }

  protected void insertKeysInRandomOrder(BinarySearchTree tree, List<Long> keysOrdered) {
    List<Long> keys = shuffle(keysOrdered);
    List<Long> insertedKeys = new ArrayList<>();
    TreeSet<Long> insertedKeysSorted = new TreeSet<>();
    StringBuilder sb = new StringBuilder();
    for (Long key : keys) {
      sb.setLength(0);
      if (KEEP_PRINTED_TREE_STATE_BEFORE_FAIL) {
        TreePrinter.print(tree.getRoot(), sb);
      }
      Node node = tree.insertNode(key);
      assertThat(key, is(node.data()));
      insertedKeys.add(key);
      insertedKeysSorted.add(key);

      if (KEEP_PRINTED_TREE_STATE_BEFORE_FAIL) {
        try {
          assertThatTree(tree)
                  .isValid()
                  .hasKeysInGivenOrder(insertedKeysSorted.stream().toList());

          assertSpecificTreeInvariants(tree);
        } catch (Throwable t) {
          System.out.printf(" ::: GOOD STATE BEFORE INSERT :::%n");
          System.out.printf("%s", sb);
          System.out.println();
          System.out.printf("Key to insert: " + key);
          System.out.printf(" ::: BAD STATE AFTER INSERT :::%n");
          TreePrinter.print(tree.getRoot());
          throw t;
        }
      }
    }
    assertThatTree(tree)
            .isValid()
            .hasKeysInGivenOrder(insertedKeysSorted.stream().toList());

    assertSpecificTreeInvariants(tree);
  }

  protected List<Long> shuffle(List<Long> keysOrdered) {
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
