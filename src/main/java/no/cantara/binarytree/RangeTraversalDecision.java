package no.cantara.binarytree;

public interface RangeTraversalDecision {
    boolean shouldTraverseLeft(Node node);

    boolean shouldTraverseRight(Node node);

    boolean shouldVisit(Node node);
}
