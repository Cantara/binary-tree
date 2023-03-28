package no.cantara.binarytree;

/**
 * Node visitor for binary tree traversal.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public interface Visitor<R> {

  void visit(TraversalContext<R> ctx, Node node);
}
