package no.cantara.binarytree;

/**
 * Interface for various BST implementations.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public interface BinarySearchTree extends BinaryTree {

    /**
     * Searches for a node with the given search key.
     *
     * @param key the search key
     * @return the node or <code>null</code> if no node with the given search key exists
     */
    Node searchNode(long key);

    /**
     * Inserts a node with the given key.
     *
     * @param key the key of the node to be inserted
     * @return the inserted node
     */
    Node insertNode(long key);

    /**
     * Deletes the node with the given key.
     *
     * @param key the key of the node to be deleted
     * @return the deleted node or <code>null</code> if no node with the given key exists
     */
    Node deleteNode(long key);

    static Node inOrderSuccessor(Node root, Node n) {
        if (n.right() != null) {
            return minValue(n.right());
        }

        Node succ = null;

        while (root != null) {
            if (n.data() < root.data()) {
                succ = root;
                root = root.left();
            } else if (n.data() > root.data()) {
                root = root.right();
            } else {
                break;
            }
        }
        return succ;
    }

    static Node minValue(Node node) {
        while (node.left() != null) {
            node = node.left();
        }
        return node;
    }

    static Node inOrderPredecessor(Node root, Node n) {
        if (n.left() != null) {
            return maxValue(n.left());
        }

        Node succ = null;

        while (root != null) {
            if (n.data() > root.data()) {
                succ = root;
                root = root.right();
            } else if (n.data() < root.data()) {
                root = root.left();
            } else {
                break;
            }
        }
        return succ;
    }

    static Node maxValue(Node node) {
        while (node.right() != null) {
            node = node.right();
        }
        return node;
    }
}
