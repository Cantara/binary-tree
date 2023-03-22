package no.cantara.binarytree;

class BinarySearchTreeRecursiveTest extends BinarySearchTreeTest {

  @Override
  protected BinarySearchTree createBST() {
    return new BinarySearchTreeRecursive(NodeFactory.defaultFactory());
  }
}
