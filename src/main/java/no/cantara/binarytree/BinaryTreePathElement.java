package no.cantara.binarytree;

public class BinaryTreePathElement {

    final boolean leftChildOfParent;
    final Node node;

    public BinaryTreePathElement(boolean leftChildOfParent, Node node) {
        this.leftChildOfParent = leftChildOfParent;
        this.node = node;
    }
}
