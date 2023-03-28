package no.cantara.binarytree;

public class InsertionContext {

    Node insertedNode;

    final BinaryTreePath affectedPath = new BinaryTreePath();

    public Node insertedNode() {
        return insertedNode;
    }

    public InsertionContext insertedNode(Node insertedNode) {
        this.insertedNode = insertedNode;
        return this;
    }

    public BinaryTreePath affectedPath() {
        return affectedPath;
    }
}
