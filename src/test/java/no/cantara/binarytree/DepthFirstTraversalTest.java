package no.cantara.binarytree;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.List;

import static no.cantara.binarytree.TestTree.emptyTree;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;

public abstract class DepthFirstTraversalTest {

  @Test
  void traversePreOrder_sampleTree_traversesTreeInPreOrder() {
    TestNodeVisitor visitor = new TestNodeVisitor();
    getTraversal(new TestTreeWithValues()).traversePreOrder(visitor);
    assertThat(visitor.getDataList(), contains(TestTreeWithValues.PRE_ORDER_VALUES));
  }

  @Test
  void traversePreOrder_emptyTree_traversesNoElement() {
    TestNodeVisitor visitor = new TestNodeVisitor();
    getTraversal(emptyTree()).traversePreOrder(visitor);
    assertThat(visitor.getDataList(), is(empty()));
  }

  @Test
  void traversePostOrder_sampleTree_traversesTreeInPostOrder() {
    TestNodeVisitor visitor = new TestNodeVisitor();
    getTraversal(new TestTreeWithValues()).traversePostOrder(visitor);
    assertThat(visitor.getDataList(), contains(TestTreeWithValues.POST_ORDER_VALUES));
  }

  @Test
  void traversePostOrder_emptyTree_traversesNoElement() {
    TestNodeVisitor visitor = new TestNodeVisitor();
    getTraversal(emptyTree()).traversePostOrder(visitor);
    assertThat(visitor.getDataList(), is(empty()));
  }

  @Test
  void traverseInOrder_sampleTree_traversesTreeInOrder() {
    TestNodeVisitor visitor = new TestNodeVisitor();
    getTraversal(new TestTreeWithValues()).traverseInOrder(visitor);
    assertThat(visitor.getDataList(), contains(TestTreeWithValues.IN_ORDER_VALUES));
  }

  @Test
  void traverseInOrder_emptyTree_traversesNoElement() {
    TestNodeVisitor visitor = new TestNodeVisitor();
    getTraversal(emptyTree()).traverseInOrder(visitor);
    assertThat(visitor.getDataList(), is(empty()));
  }

  @Test
  void traverseReverseInOrder_sampleTree_traversesTreeInReverseOrder() {
    TestNodeVisitor visitor = new TestNodeVisitor();
    getTraversal(new TestTreeWithValues()).traverseReverseInOrder(visitor);
    assertThat(visitor.getDataList(), contains(TestTreeWithValues.REVERSE_IN_ORDER_VALUES));
  }

  @Test
  void traverseReverseInOrder_emptyTree_traversesNoElement() {
    TestNodeVisitor visitor = new TestNodeVisitor();
    getTraversal(emptyTree()).traverseReverseInOrder(visitor);
    assertThat(visitor.getDataList(), is(empty()));
  }

  @Test
  void traversePreOrder_sampleBST_traversesTreeInPreOrder() {
    TestNodeVisitor visitor = new TestNodeVisitor();
    getTraversal(new BinarySearchTestTreeWithValues()).traversePreOrder(visitor);
    assertThat(visitor.getDataList(), contains(BinarySearchTestTreeWithValues.PRE_ORDER_VALUES));
  }

  @Test
  void traversePostOrder_sampleBST_traversesTreeInPostOrder() {
    TestNodeVisitor visitor = new TestNodeVisitor();
    getTraversal(new BinarySearchTestTreeWithValues()).traversePostOrder(visitor);
    assertThat(visitor.getDataList(), contains(BinarySearchTestTreeWithValues.POST_ORDER_VALUES));
  }

  @Test
  void traverseInOrder_sampleBST_traversesTreeInOrder() {
    TestNodeVisitor visitor = new TestNodeVisitor();
    getTraversal(new BinarySearchTestTreeWithValues()).traverseInOrder(visitor);
    assertThat(visitor.getDataList(), contains(BinarySearchTestTreeWithValues.IN_ORDER_VALUES));
  }

  @Test
  void traverseInOrder_sampleBST_traversesTreeInOrderClosedInclusive() {
    TestVisitor visitor = new TestVisitor();
    TraversalSummary<List<Long>> summary = getTraversal(new BinarySearchTestTreeWithValues()).traverseInOrder(new TraversalRange(BinarySearchTestTreeWithValues.startKey, true, BinarySearchTestTreeWithValues.endKey, true), visitor);
    assertThat(visitor.getDataList(), contains(BinarySearchTestTreeWithValues.IN_ORDER_VALUES_CLOSED_INCLUSIVE));
  }

  @Test
  void traverseInOrder_sampleBST_traversesTreeInOrderClosedExclusive() {
    TestVisitor visitor = new TestVisitor();
    TraversalSummary<List<Long>> summary = getTraversal(new BinarySearchTestTreeWithValues()).traverseInOrder(new TraversalRange(BinarySearchTestTreeWithValues.startKey, false, BinarySearchTestTreeWithValues.endKey, false), visitor);
    assertThat(visitor.getDataList(), contains(BinarySearchTestTreeWithValues.IN_ORDER_VALUES_CLOSED_EXCLUSIVE));
  }

  @Test
  void traverseInOrder_sampleBST_traversesTreeInOrderOpenStartInclusive() {
    TestVisitor visitor = new TestVisitor();
    TraversalSummary<List<Long>> summary = getTraversal(new BinarySearchTestTreeWithValues()).traverseInOrder(new TraversalRange(null, false, BinarySearchTestTreeWithValues.endKey, true), visitor);
    assertThat(visitor.getDataList(), contains(BinarySearchTestTreeWithValues.IN_ORDER_VALUES_OPEN_START_INCLUSIVE));
  }

  @Test
  void traverseInOrder_sampleBST_traversesTreeInOrderOpenStartExclusive() {
    TestVisitor visitor = new TestVisitor();
    TraversalSummary<List<Long>> summary = getTraversal(new BinarySearchTestTreeWithValues()).traverseInOrder(new TraversalRange(null, false, BinarySearchTestTreeWithValues.endKey, false), visitor);
    assertThat(visitor.getDataList(), contains(BinarySearchTestTreeWithValues.IN_ORDER_VALUES_OPEN_START_EXCLUSIVE));
  }

  @Test
  void traverseInOrder_sampleBST_traversesTreeInOrderOpenEndInclusive() {
    TestVisitor visitor = new TestVisitor();
    TraversalSummary<List<Long>> summary = getTraversal(new BinarySearchTestTreeWithValues()).traverseInOrder(new TraversalRange(BinarySearchTestTreeWithValues.startKey, true, null, false), visitor);
    assertThat(visitor.getDataList(), contains(BinarySearchTestTreeWithValues.IN_ORDER_VALUES_OPEN_END_INCLUSIVE));
  }

  @Test
  void traverseInOrder_sampleBST_traversesTreeInOrderOpenEndExclusive() {
    TestVisitor visitor = new TestVisitor();
    TraversalSummary<List<Long>> summary = getTraversal(new BinarySearchTestTreeWithValues()).traverseInOrder(new TraversalRange(BinarySearchTestTreeWithValues.startKey, false, null, false), visitor);
    assertThat(visitor.getDataList(), contains(BinarySearchTestTreeWithValues.IN_ORDER_VALUES_OPEN_END_EXCLUSIVE));
  }

  @Test
  void traverseReverseInOrder_sampleBST_traversesTreeInReverseOrder() {
    TestNodeVisitor visitor = new TestNodeVisitor();
    getTraversal(new BinarySearchTestTreeWithValues()).traverseReverseInOrder(visitor);
    assertThat(visitor.getDataList(), contains(BinarySearchTestTreeWithValues.REVERSE_IN_ORDER_VALUES));
  }

    @Test
    void traverseInOrder_sampleBST_traversesTreeInOrderClosedExclusiveFindNode() {
        Visitor<Long> visitor = (ctx, node) -> {
            if (node.data() == 10L) {
                ctx.result(node.data())
                        .stopTraversal();
            }
        };
        TraversalSummary<Long> ctx = getTraversal(new BinarySearchTestTreeWithValues()).traverseInOrder(new TraversalRange(BinarySearchTestTreeWithValues.startKey, false, BinarySearchTestTreeWithValues.endKey, false), visitor);
        assertThat(ctx.result, is(10L));
    }

    @Test
    void traverseInOrder_sampleBST_traversesTreeInOrderClosedExclusiveCannotFindNodeOutsideRange() {
        Visitor<Long> visitor = (ctx, node) -> {
            if (node.data() == 13L) {
                ctx.result(node.data())
                        .stopTraversal();
            }
        };
        TraversalSummary<Long> ctx = getTraversal(new BinarySearchTestTreeWithValues()).traverseInOrder(new TraversalRange(BinarySearchTestTreeWithValues.startKey, false, BinarySearchTestTreeWithValues.endKey, false), visitor);
        assertThat(ctx.result, Matchers.nullValue());
    }

    abstract DepthFirstTraversal getTraversal(BinaryTree tree);
}
