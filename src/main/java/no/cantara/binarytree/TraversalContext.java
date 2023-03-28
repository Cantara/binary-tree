package no.cantara.binarytree;

public class TraversalContext<R> implements RangeTraversalDecision {

    final TraversalRange range;
    boolean stop = false;
    R result;

    public TraversalContext(TraversalRange range) {
        this.range = range;
    }

    public TraversalRange range() {
        return range;
    }

    public R result() {
        return result;
    }

    public TraversalContext<R> result(R result) {
        this.result = result;
        return this;
    }

    public TraversalContext<R> stopTraversal() {
        stop = true;
        return this;
    }

    @Override
    public boolean shouldTraverseLeft(Node node) {
        return range.shouldTraverseLeft(node);
    }

    @Override
    public boolean shouldTraverseRight(Node node) {
        return range.shouldTraverseRight(node);
    }

    @Override
    public boolean shouldVisit(Node node) {
        return range.shouldVisit(node);
    }
}
