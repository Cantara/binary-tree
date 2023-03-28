package no.cantara.binarytree;

public class DeletionContext {

    Node deletedNode;
    Node successorNode;
    Node parentOfSuccessorNode;

    final BinaryTreePath affectedPath = new BinaryTreePath();

    public Node deletedNode() {
        return deletedNode;
    }

    public DeletionContext deletedNode(Node deletedNode) {
        this.deletedNode = deletedNode;
        return this;
    }

    public Node parentOfSuccessorNode() {
        return parentOfSuccessorNode;
    }

    public DeletionContext parentOfSuccessorNode(Node parentOfSuccessorNode) {
        this.parentOfSuccessorNode = parentOfSuccessorNode;
        return this;
    }

    public Node successorNode() {
        return successorNode;
    }

    public DeletionContext successorNode(Node successorNode) {
        this.successorNode = successorNode;
        return this;
    }

    public BinaryTreePath affectedPath() {
        return affectedPath;
    }
}
