package no.cantara.binarytree;

class DepthFirstTraversalRecursiveTest extends DepthFirstTraversalTest {

  @Override
  DepthFirstTraversal getTraversal(BinaryTree tree) {
    return new DepthFirstTraversalRecursive(tree);
  }
}
