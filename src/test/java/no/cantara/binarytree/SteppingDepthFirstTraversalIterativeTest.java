package no.cantara.binarytree;

class SteppingDepthFirstTraversalIterativeTest extends DepthFirstTraversalTest {

  @Override
  DepthFirstTraversal getTraversal(BinaryTree tree) {
    return new SteppingDepthFirstTraversalIterative(tree);
  }
}
