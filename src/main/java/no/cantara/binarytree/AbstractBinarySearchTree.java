package no.cantara.binarytree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Objects;

/**
 * Interface for various BST implementations.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public abstract class AbstractBinarySearchTree extends BaseBinaryTree implements BinarySearchTree, NavigableMap<Long, Node> {

    protected final NodeFactory factory;
    protected final TraversalRange range;
    protected final boolean traversalDirection;

    protected AbstractBinarySearchTree(NodeFactory factory, TraversalRange range, boolean traversalDirection, Node root) {
        this.factory = factory;
        this.range = range;
        this.traversalDirection = traversalDirection;
        this.root = root;
    }

    public abstract AbstractBinarySearchTree subTree(TraversalRange subRange, boolean traversalDirection);

    public TraversalRange range() {
        return range;
    }

    /**
     * Traversal direction, true is forward and false is reverse.
     *
     * @return the direction
     */
    public boolean traversalDirection() {
        return traversalDirection;
    }

    @Override
    public Entry<Long, Node> lowerEntry(Long key) {
        TraversalRange subRange = range.subRange(null, false, key, false);
        DepthFirstTraversal traversal = new DepthFirstTraversalIterative(this);
        TraversalSummary<Node> summary = traversal.traverseReverseInOrder(subRange, (ctx, node) -> ctx.result(node)
                .stopTraversal());
        if (summary.result == null) {
            return null;
        }
        return Map.entry(summary.result.data(), summary.result);
    }

    @Override
    public Long lowerKey(Long key) {
        Entry<Long, Node> entry = lowerEntry(key);
        if (entry == null) {
            return null;
        }
        return entry.getKey();
    }

    @Override
    public Entry<Long, Node> floorEntry(Long key) {
        TraversalRange subRange = range.subRange(null, false, key, true);
        DepthFirstTraversal traversal = new DepthFirstTraversalIterative(this);
        TraversalSummary<Node> summary = traversal.traverseReverseInOrder(subRange, (ctx, node) -> ctx.result(node)
                .stopTraversal());
        if (summary.result == null) {
            return null;
        }
        return Map.entry(summary.result.data(), summary.result);
    }

    @Override
    public Long floorKey(Long key) {
        Entry<Long, Node> entry = floorEntry(key);
        if (entry == null) {
            return null;
        }
        return entry.getKey();
    }

    @Override
    public Entry<Long, Node> ceilingEntry(Long key) {
        TraversalRange subRange = range.subRange(key, true, null, false);
        DepthFirstTraversal traversal = new DepthFirstTraversalIterative(this);
        TraversalSummary<Node> summary = traversal.traverseInOrder(subRange, (ctx, node) -> ctx.result(node)
                .stopTraversal());
        if (summary.result == null) {
            return null;
        }
        return Map.entry(summary.result.data(), summary.result);
    }

    @Override
    public Long ceilingKey(Long key) {
        Entry<Long, Node> entry = ceilingEntry(key);
        if (entry == null) {
            return null;
        }
        return entry.getKey();
    }

    @Override
    public Entry<Long, Node> higherEntry(Long key) {
        TraversalRange subRange = range.subRange(key, false, null, false);
        DepthFirstTraversal traversal = new DepthFirstTraversalIterative(this);
        TraversalSummary<Node> summary = traversal.traverseInOrder(subRange, (ctx, node) -> ctx.result(node)
                .stopTraversal());
        if (summary.result == null) {
            return null;
        }
        return Map.entry(summary.result.data(), summary.result);
    }

    @Override
    public Long higherKey(Long key) {
        Entry<Long, Node> entry = higherEntry(key);
        if (entry == null) {
            return null;
        }
        return entry.getKey();
    }

    @Override
    public Entry<Long, Node> firstEntry() {
        TraversalRange subRange = range.subRange(null, false, null, false);
        DepthFirstTraversal traversal = new DepthFirstTraversalIterative(this);
        TraversalSummary<Node> summary = traversal.traverseInOrder(subRange, (ctx, node) -> ctx.result(node)
                .stopTraversal());
        if (summary.result == null) {
            return null;
        }
        return Map.entry(summary.result.data(), summary.result);
    }

    @Override
    public Entry<Long, Node> lastEntry() {
        TraversalRange subRange = range.subRange(null, false, null, false);
        DepthFirstTraversal traversal = new DepthFirstTraversalIterative(this);
        TraversalSummary<Node> summary = traversal.traverseReverseInOrder(subRange, (ctx, node) -> ctx.result(node)
                .stopTraversal());
        if (summary.result == null) {
            return null;
        }
        return Map.entry(summary.result.data(), summary.result);
    }

    @Override
    public Entry<Long, Node> pollFirstEntry() {
        Entry<Long, Node> firstEntry = firstEntry();
        if (firstEntry == null) {
            return null;
        }
        deleteNode(firstEntry.getKey());
        return firstEntry;
    }

    @Override
    public Entry<Long, Node> pollLastEntry() {
        Entry<Long, Node> lastEntry = lastEntry();
        if (lastEntry == null) {
            return null;
        }
        deleteNode(lastEntry.getKey());
        return lastEntry;
    }

    @Override
    public AbstractBinarySearchTree descendingMap() {
        return subTree(range, !traversalDirection);
    }

    @Override
    public AbstractBinarySearchTree.BSTKeySet navigableKeySet() {
        return new BSTKeySet();
    }

    @Override
    public AbstractBinarySearchTree.BSTKeySet descendingKeySet() {
        return descendingMap()
                .navigableKeySet();
    }

    @Override
    public AbstractBinarySearchTree subMap(Long fromKey, boolean fromInclusive, Long toKey, boolean toInclusive) {
        return subTree(new TraversalRange(fromKey, fromInclusive, toKey, toInclusive), traversalDirection);
    }

    @Override
    public AbstractBinarySearchTree headMap(Long toKey, boolean inclusive) {
        return subTree(new TraversalRange(null, false, toKey, inclusive), traversalDirection);
    }

    @Override
    public AbstractBinarySearchTree tailMap(Long fromKey, boolean inclusive) {
        return subTree(new TraversalRange(fromKey, inclusive, null, false), traversalDirection);
    }

    @Override
    public AbstractBinarySearchTree subMap(Long fromKey, Long toKey) {
        return subMap(fromKey, true, toKey, false);
    }

    @Override
    public AbstractBinarySearchTree headMap(Long toKey) {
        return headMap(toKey, false);
    }

    @Override
    public AbstractBinarySearchTree tailMap(Long fromKey) {
        return tailMap(fromKey, true);
    }

    @Override
    public Comparator<? super Long> comparator() {
        return (Comparator<Long>) Long::compareTo;
    }

    @Override
    public Long firstKey() {
        Entry<Long, Node> entry = firstEntry();
        if (entry == null) {
            return null;
        }
        return entry.getKey();
    }

    @Override
    public Long lastKey() {
        Entry<Long, Node> entry = lastEntry();
        if (entry == null) {
            return null;
        }
        return entry.getKey();
    }

    @Override
    public AbstractBinarySearchTree.BSTKeySet keySet() {
        return new BSTKeySet();
    }

    @Override
    public Collection<Node> values() {
        DepthFirstTraversal traversal = new DepthFirstTraversalIterative(this);
        TraversalSummary<List<Node>> summary;
        if (traversalDirection) {
            summary = traversal.traverseInOrder(range, (ctx, node) -> {
                if (ctx.result == null) {
                    ctx.result(new ArrayList<>());
                }
                ctx.result.add(node);
            });
        } else {
            summary = traversal.traverseReverseInOrder(range, (ctx, node) -> {
                if (ctx.result == null) {
                    ctx.result(new ArrayList<>());
                }
                ctx.result.add(node);
            });
        }
        if (summary.result == null) {
            return Collections.emptyList();
        }
        return summary.result;
    }

    @Override
    public AbstractBinarySearchTree.BSTEntrySet entrySet() {
        return new BSTEntrySet();
    }

    @Override
    public int size() {
        DepthFirstTraversal traversal = new DepthFirstTraversalIterative(this);
        MutableInt size = new MutableInt();
        traversal.traverseInOrder(range, (ctx, node) -> size.value++);
        return size.value;
    }

    @Override
    public boolean isEmpty() {
        return getRoot() == null;
    }

    @Override
    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    @Override
    public boolean containsValue(Object value) {
        Objects.requireNonNull(value);
        if (!(value instanceof Node)) {
            return false;
        }
        DepthFirstTraversal dfs = new DepthFirstTraversalRecursive(this);
        TraversalSummary<Node> summary = dfs.traverseInOrder(range, (ctx, node) -> {
            if (Objects.equals(node, value)) {
                ctx.result(node).stopTraversal();
            }
        });
        return summary.result != null;
    }

    @Override
    public Node get(Object key) {
        long theKey;
        if (key instanceof Number number) {
            theKey = number.longValue();
        } else {
            return null;
        }
        Entry<Long, Node> found = ceilingEntry(theKey);
        if (found == null || found.getKey() != theKey) {
            return null;
        }
        return found.getValue();
    }

    @Override
    public Node put(Long key, Node value) {
        Node node = insertNode(key);
        return node;
    }

    @Override
    public Node remove(Object key) {
        // TODO check whether key is within map boundaries
        return deleteNode((Long) key);
    }

    @Override
    public void putAll(Map<? extends Long, ? extends Node> m) {
        for (Entry<? extends Long, ? extends Node> entry : m.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    public class BSTKeySet implements NavigableSet<Long> {
        @Override
        public int size() {
            return AbstractBinarySearchTree.this.size();
        }

        @Override
        public boolean isEmpty() {
            return AbstractBinarySearchTree.this.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Long lower(Long key) {
            return lowerKey(key);
        }

        @Override
        public Long floor(Long key) {
            return floorKey(key);
        }

        @Override
        public Long ceiling(Long key) {
            return ceilingKey(key);
        }

        @Override
        public Long higher(Long key) {
            return higherKey(key);
        }

        @Override
        public Long pollFirst() {
            Entry<Long, Node> entry = pollFirstEntry();
            if (entry == null) {
                return null;
            }
            return entry.getKey();
        }

        @Override
        public Long pollLast() {
            Entry<Long, Node> entry = pollLastEntry();
            if (entry == null) {
                return null;
            }
            return entry.getKey();
        }

        @Override
        public Iterator<Long> iterator() {
            final SteppingDepthFirstTraversalIterative traversal = new SteppingDepthFirstTraversalIterative(AbstractBinarySearchTree.this);
            if (traversalDirection) {
                final SteppingTraversalContext<Object> ctx = traversal.traverseInOrder(new SteppingTraversalContext<>(AbstractBinarySearchTree.this.range, root));
                return new Iterator<>() {
                    @Override
                    public boolean hasNext() {
                        return ctx.hasNext;
                    }

                    @Override
                    public Long next() {
                        Node next = ctx.next;
                        traversal.traverseInOrder(ctx); // pre-advance
                        return next.data();
                    }
                };
            } else {
                final SteppingTraversalContext<Object> ctx = traversal.traverseReverseInOrder(new SteppingTraversalContext<>(AbstractBinarySearchTree.this.range, root));
                return new Iterator<>() {
                    @Override
                    public boolean hasNext() {
                        return ctx.hasNext;
                    }

                    @Override
                    public Long next() {
                        Node next = ctx.next;
                        traversal.traverseReverseInOrder(ctx); // pre-advance
                        return next.data();
                    }
                };
            }
        }

        @Override
        public AbstractBinarySearchTree.BSTKeySet descendingSet() {
            return subTree(TraversalRange.OPEN, !traversalDirection)
                    .keySet();
        }

        @Override
        public Iterator<Long> descendingIterator() {
            return descendingSet()
                    .iterator();
        }

        @Override
        public AbstractBinarySearchTree.BSTKeySet subSet(Long fromElement, boolean fromInclusive, Long toElement, boolean toInclusive) {
            return subTree(new TraversalRange(fromElement, fromInclusive, toElement, toInclusive), traversalDirection)
                    .keySet();
        }

        @Override
        public AbstractBinarySearchTree.BSTKeySet headSet(Long toElement, boolean inclusive) {
            return subTree(new TraversalRange(null, false, toElement, inclusive), traversalDirection)
                    .keySet();
        }

        @Override
        public AbstractBinarySearchTree.BSTKeySet tailSet(Long fromElement, boolean inclusive) {
            return subTree(new TraversalRange(fromElement, inclusive, null, false), traversalDirection)
                    .keySet();
        }

        @Override
        public Comparator<? super Long> comparator() {
            return Long::compareTo;
        }

        @Override
        public AbstractBinarySearchTree.BSTKeySet subSet(Long fromElement, Long toElement) {
            return subTree(new TraversalRange(fromElement, true, toElement, false), traversalDirection)
                    .keySet();
        }

        @Override
        public AbstractBinarySearchTree.BSTKeySet headSet(Long toElement) {
            return subTree(new TraversalRange(null, false, toElement, false), traversalDirection)
                    .keySet();
        }

        @Override
        public AbstractBinarySearchTree.BSTKeySet tailSet(Long fromElement) {
            return subTree(new TraversalRange(fromElement, true, null, false), traversalDirection)
                    .keySet();
        }

        @Override
        public Long first() {
            return firstKey();
        }

        @Override
        public Long last() {
            return lastKey();
        }

        @Override
        public Object[] toArray() {
            throw new UnsupportedOperationException();
        }

        @Override
        public <T> T[] toArray(T[] a) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean add(Long aLong) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object o) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(Collection<? extends Long> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }
    }

    public class BSTEntrySet implements NavigableSet<Entry<Long, Node>> {

        BSTEntrySet() {
        }

        @Override
        public int size() {
            return AbstractBinarySearchTree.this.size();
        }

        @Override
        public boolean isEmpty() {
            return AbstractBinarySearchTree.this.isEmpty();
        }

        @Override
        public boolean contains(Object o) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Entry<Long, Node> lower(Entry<Long, Node> entry) {
            return lowerEntry(entry.getKey());
        }

        @Override
        public Entry<Long, Node> floor(Entry<Long, Node> entry) {
            return floorEntry(entry.getKey());
        }

        @Override
        public Entry<Long, Node> ceiling(Entry<Long, Node> entry) {
            return ceilingEntry(entry.getKey());
        }

        @Override
        public Entry<Long, Node> higher(Entry<Long, Node> entry) {
            return higherEntry(entry.getKey());
        }

        @Override
        public Entry<Long, Node> pollFirst() {
            return pollFirstEntry();
        }

        @Override
        public Entry<Long, Node> pollLast() {
            return pollLastEntry();
        }

        @Override
        public Iterator<Entry<Long, Node>> iterator() {
            final SteppingDepthFirstTraversalIterative traversal = new SteppingDepthFirstTraversalIterative(AbstractBinarySearchTree.this);
            if (traversalDirection) {
                final SteppingTraversalContext<Object> ctx = traversal.traverseInOrder(new SteppingTraversalContext<>(AbstractBinarySearchTree.this.range, root));
                return new Iterator<>() {
                    @Override
                    public boolean hasNext() {
                        return ctx.hasNext;
                    }

                    @Override
                    public Entry<Long, Node> next() {
                        Node next = ctx.next;
                        traversal.traverseInOrder(ctx); // pre-advance
                        return Map.entry(next.data(), next);
                    }
                };
            } else {
                final SteppingTraversalContext<Object> ctx = traversal.traverseReverseInOrder(new SteppingTraversalContext<>(AbstractBinarySearchTree.this.range, root));
                return new Iterator<>() {
                    @Override
                    public boolean hasNext() {
                        return ctx.hasNext;
                    }

                    @Override
                    public Entry<Long, Node> next() {
                        Node next = ctx.next;
                        traversal.traverseReverseInOrder(ctx); // pre-advance
                        return Map.entry(next.data(), next);
                    }
                };
            }
        }

        @Override
        public AbstractBinarySearchTree.BSTEntrySet descendingSet() {
            return subTree(range, !traversalDirection).entrySet();
        }

        @Override
        public Iterator<Entry<Long, Node>> descendingIterator() {
            return descendingSet().iterator();
        }

        @Override
        public AbstractBinarySearchTree.BSTEntrySet subSet(Entry<Long, Node> fromElement, boolean fromInclusive, Entry<Long, Node> toElement, boolean toInclusive) {
            return subTree(new TraversalRange(fromElement.getKey(), fromInclusive, toElement.getKey(), toInclusive), traversalDirection).entrySet();
        }

        @Override
        public AbstractBinarySearchTree.BSTEntrySet headSet(Entry<Long, Node> toElement, boolean inclusive) {
            return subTree(new TraversalRange(null, false, toElement.getKey(), inclusive), traversalDirection).entrySet();
        }

        @Override
        public AbstractBinarySearchTree.BSTEntrySet tailSet(Entry<Long, Node> fromElement, boolean inclusive) {
            return subTree(new TraversalRange(fromElement.getKey(), inclusive, null, false), traversalDirection).entrySet();
        }

        @Override
        public Comparator<? super Entry<Long, Node>> comparator() {
            return Entry.comparingByKey();
        }

        @Override
        public AbstractBinarySearchTree.BSTEntrySet subSet(Entry<Long, Node> fromElement, Entry<Long, Node> toElement) {
            return subTree(new TraversalRange(fromElement.getKey(), true, toElement.getKey(), false), traversalDirection).entrySet();
        }

        @Override
        public AbstractBinarySearchTree.BSTEntrySet headSet(Entry<Long, Node> toElement) {
            return subTree(new TraversalRange(null, false, toElement.getKey(), false), traversalDirection).entrySet();
        }

        @Override
        public AbstractBinarySearchTree.BSTEntrySet tailSet(Entry<Long, Node> fromElement) {
            return subTree(new TraversalRange(fromElement.getKey(), true, null, false), traversalDirection).entrySet();
        }

        @Override
        public Entry<Long, Node> first() {
            return firstEntry();
        }

        @Override
        public Entry<Long, Node> last() {
            return lastEntry();
        }

        @Override
        public Object[] toArray() {
            throw new UnsupportedOperationException();
        }

        @Override
        public <T> T[] toArray(T[] a) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean add(Entry<Long, Node> longNodeEntry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object o) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(Collection<? extends Entry<Long, Node>> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }
    }
}
