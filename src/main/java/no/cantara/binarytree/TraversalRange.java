package no.cantara.binarytree;

public class TraversalRange implements RangeTraversalDecision {

    public static TraversalRange OPEN = new TraversalRange();

    final Long startKey;
    final boolean startKeyInclusive;
    final Long endKey;
    final boolean endKeyInclusive;
    final RangeTraversalDecision delegate;

    public TraversalRange() {
        this(null, true, null, true);
    }

    public TraversalRange(Long startKey, boolean startKeyInclusive, Long endKey, boolean endKeyInclusive) {
        this.startKey = startKey;
        this.startKeyInclusive = startKeyInclusive;
        this.endKey = endKey;
        this.endKeyInclusive = endKeyInclusive;
        if (startKey == null && endKey == null) {
            delegate = new OpenRange();
        } else if (startKey == null) {
            delegate = new OpenStartRange();
        } else if (endKey == null) {
            delegate = new OpenEndRange();
        } else {
            delegate = new ClosedRange();
        }
    }

    public TraversalRange subRange(TraversalRange subRange) {
        return subRange(subRange.startKey, subRange.startKeyInclusive, subRange.endKey, subRange.endKeyInclusive);
    }

    public TraversalRange subRange(Long subStartKey, boolean subStartKeyInclusive, Long subEndKey, boolean subEndKeyInclusive) {
        Long effectiveStartKey;
        boolean effectiveStartKeyInclusive;
        Long effectiveEndKey;
        boolean effectiveEndKeyInclusive;
        if (subStartKey == null) {
            effectiveStartKey = startKey;
            effectiveStartKeyInclusive = startKeyInclusive;
        } else {
            if (startKey == null) {
                effectiveStartKey = subStartKey;
                effectiveStartKeyInclusive = subStartKeyInclusive;
            } else {
                if (subStartKeyInclusive || !startKeyInclusive) {
                    // both are true or both are false or sub is true
                    if (startKey < subStartKey) {
                        effectiveStartKey = subStartKey;
                        effectiveStartKeyInclusive = subStartKeyInclusive;
                    } else {
                        effectiveStartKey = startKey;
                        effectiveStartKeyInclusive = startKeyInclusive;
                    }
                } else {
                    // sub is false and this is true
                    if (subStartKey < startKey) {
                        effectiveStartKey = startKey;
                        effectiveStartKeyInclusive = startKeyInclusive;
                    } else {
                        effectiveStartKey = subStartKey;
                        effectiveStartKeyInclusive = subStartKeyInclusive;
                    }
                }
            }
        }
        if (subEndKey == null) {
            effectiveEndKey = endKey;
            effectiveEndKeyInclusive = endKeyInclusive;
        } else {
            if (endKey == null) {
                effectiveEndKey = subEndKey;
                effectiveEndKeyInclusive = subEndKeyInclusive;
            } else {
                if (subEndKeyInclusive || !endKeyInclusive) {
                    // both are true or both are false or sub is true
                    if (subEndKey < endKey) {
                        effectiveEndKey = subEndKey;
                        effectiveEndKeyInclusive = subEndKeyInclusive;
                    } else {
                        effectiveEndKey = endKey;
                        effectiveEndKeyInclusive = endKeyInclusive;
                    }
                } else {
                    // sub is false and this is true
                    if (endKey < subEndKey) {
                        effectiveEndKey = endKey;
                        effectiveEndKeyInclusive = endKeyInclusive;
                    } else {
                        effectiveEndKey = subEndKey;
                        effectiveEndKeyInclusive = subEndKeyInclusive;
                    }
                }
            }
        }
        return new TraversalRange(effectiveStartKey, effectiveStartKeyInclusive, effectiveEndKey, effectiveEndKeyInclusive);
    }

    public Long startKey() {
        return startKey;
    }

    public boolean startKeyInclusive() {
        return startKeyInclusive;
    }

    public Long endKey() {
        return endKey;
    }

    public boolean endKeyInclusive() {
        return endKeyInclusive;
    }

    @Override
    public boolean shouldTraverseLeft(Node node) {
        return delegate.shouldTraverseLeft(node);
    }

    @Override
    public boolean shouldTraverseRight(Node node) {
        return delegate.shouldTraverseRight(node);
    }

    @Override
    public boolean shouldVisit(Node node) {
        return delegate.shouldVisit(node);
    }

    private class OpenRange implements RangeTraversalDecision {

        @Override
        public boolean shouldTraverseLeft(Node node) {
            return true;
        }

        @Override
        public boolean shouldTraverseRight(Node node) {
            return true;
        }

        @Override
        public boolean shouldVisit(Node node) {
            return true;
        }
    }

    private class OpenStartRange implements RangeTraversalDecision {

        final long visitUpperBound;
        final long traversalUpperBound;

        OpenStartRange() {
            this.visitUpperBound = endKeyInclusive ? endKey : endKey - 1;
            this.traversalUpperBound = endKeyInclusive ? endKey - 1 : endKey - 2;
        }

        @Override
        public boolean shouldTraverseLeft(Node node) {
            return true;
        }

        @Override
        public boolean shouldTraverseRight(Node node) {
            return node.data() <= traversalUpperBound;
        }

        @Override
        public boolean shouldVisit(Node node) {
            return node.data() <= visitUpperBound;
        }
    }

    private class OpenEndRange implements RangeTraversalDecision {

        final long visitLowerBound;
        final long traversalLowerBound;

        private OpenEndRange() {
            this.visitLowerBound = startKeyInclusive ? startKey : startKey + 1;
            this.traversalLowerBound = startKeyInclusive ? startKey + 1 : startKey + 2;
        }

        @Override
        public boolean shouldTraverseLeft(Node node) {
            return traversalLowerBound <= node.data();
        }

        @Override
        public boolean shouldTraverseRight(Node node) {
            return true;
        }

        @Override
        public boolean shouldVisit(Node node) {
            return visitLowerBound <= node.data();
        }
    }

    private class ClosedRange implements RangeTraversalDecision {

        final long visitLowerBound;
        final long traversalLowerBound;
        final long visitUpperBound;
        final long traversalUpperBound;

        private ClosedRange() {
            this.visitLowerBound = startKeyInclusive ? startKey : startKey + 1;
            this.traversalLowerBound = startKeyInclusive ? startKey + 1 : startKey + 2;
            this.visitUpperBound = endKeyInclusive ? endKey : endKey - 1;
            this.traversalUpperBound = endKeyInclusive ? endKey - 1 : endKey - 2;
        }

        @Override
        public boolean shouldTraverseLeft(Node node) {
            return traversalLowerBound <= node.data();
        }

        @Override
        public boolean shouldTraverseRight(Node node) {
            return node.data() <= traversalUpperBound;
        }

        @Override
        public boolean shouldVisit(Node node) {
            long data = node.data();
            return visitLowerBound <= data & data <= visitUpperBound;
        }
    }
}
