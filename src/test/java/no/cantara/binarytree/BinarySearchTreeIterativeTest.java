package no.cantara.binarytree;

class BinarySearchTreeIterativeTest extends BinarySearchTreeTest {

  @Override
  protected BinarySearchTree createBST() {
    return new BinarySearchTreeIterative(NodeFactory.defaultFactory());
  }
}
