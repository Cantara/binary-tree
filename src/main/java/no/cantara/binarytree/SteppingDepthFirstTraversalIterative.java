package no.cantara.binarytree;

import java.util.Deque;

/**
 * Iterative depth-first (DFS) traversal on a binary tree.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public final class SteppingDepthFirstTraversalIterative implements DepthFirstTraversal {

  private final BinaryTree tree;

  public SteppingDepthFirstTraversalIterative(BinaryTree tree) {
    this.tree = tree;
  }

  @Override
  public void traversePreOrder(NodeVisitor visitor) {
    traversePreOrder(TraversalRange.OPEN, (ctx, node) -> visitor.visit(node));
  }

  @Override
  public void traversePostOrder(NodeVisitor visitor) {
    traversePostOrder(TraversalRange.OPEN, (ctx, node) -> visitor.visit(node));
  }

  @Override
  public void traverseInOrder(NodeVisitor visitor) {
    traverseInOrder(TraversalRange.OPEN, (ctx, node) -> visitor.visit(node));
  }

  @Override
  public void traverseReverseInOrder(NodeVisitor visitor) {
    traverseReverseInOrder(TraversalRange.OPEN, (ctx, node) -> visitor.visit(node));
  }

  @Override
  public <R> TraversalSummary<R> traversePreOrder(TraversalRange range, Visitor<R> visitor) {
    SteppingTraversalContext<R> ctx = traversePreOrder(new SteppingTraversalContext<>(range, tree.getRoot()));
    while(ctx.hasNext) {
      visitor.visit(ctx, ctx.next);
      traversePreOrder(ctx);
    }
    return new TraversalSummary<>(ctx.result);
  }

  <R> SteppingTraversalContext<R> traversePreOrder(TraversalContext<R> _ctx) {
    final SteppingTraversalContext<R> ctx = (SteppingTraversalContext<R>) _ctx;
    final Deque<Node> stack = ctx.stack;

    if (!ctx.initialized) {
      Node node = tree.getRoot();
      if (node == null) {
        return ctx;
      }
      stack.push(node);
      ctx.initialized = true;
    }

    while (!stack.isEmpty()) {
      ctx.node = stack.poll();
      ctx.hasNext = false;
      if (ctx.shouldVisit(ctx.node)) {
        ctx.hasNext = true;
        ctx.next = ctx.node;
      }
      if (ctx.shouldTraverseRight(ctx.node)) {
        if (ctx.node.right() != null) {
          stack.push(ctx.node.right());
        }
      }
      if (ctx.shouldTraverseLeft(ctx.node)) {
        if (ctx.node.left() != null) {
          stack.push(ctx.node.left());
        }
      }
      if (ctx.hasNext) {
        return ctx; // can be continued by calling this method with ctx
      }
    }
    ctx.hasNext = false;
    ctx.next = null;
    return ctx;
  }

  @Override
  public <R> TraversalSummary<R> traversePostOrder(TraversalRange range, Visitor<R> visitor) {
    SteppingTraversalContext<R> ctx = traversePostOrder(new SteppingTraversalContext<>(range, tree.getRoot()));
    while(ctx.hasNext) {
      visitor.visit(ctx, ctx.next);
      traversePostOrder(ctx);
    }
    return new TraversalSummary<>(ctx.result);
  }

  <R> SteppingTraversalContext<R> traversePostOrder(TraversalContext<R> _ctx) {
    final SteppingTraversalContext<R> ctx = (SteppingTraversalContext<R>) _ctx;
    final Deque<Node> stack = ctx.stack;

    while (!stack.isEmpty() || ctx.node != null) {
      if (ctx.node != null) {
        stack.push(ctx.node);
        if (ctx.shouldTraverseLeft(ctx.node)) {
          ctx.node = ctx.node.left();
        } else {
          ctx.node = null;
        }
      } else {
        Node topNode = stack.peek();
        if (ctx.shouldTraverseRight(topNode) && topNode.right() != null && !topNode.right().equals(ctx.lastVisitedNode)) {
          ctx.node = topNode.right();
        } else {
          ctx.hasNext = false;
          if (ctx.shouldVisit(topNode)) {
            ctx.hasNext = true;
            ctx.next = topNode;
          }
          ctx.lastVisitedNode = stack.poll();
          if (ctx.hasNext) {
            return ctx; // can be continued by calling this method with ctx
          }
        }
      }
    }
    ctx.hasNext = false;
    ctx.next = null;
    return ctx;
  }

  @Override
  public <R> TraversalSummary<R> traverseInOrder(TraversalRange range, Visitor<R> visitor) {
    SteppingTraversalContext<R> ctx = traverseInOrder(new SteppingTraversalContext<>(range, tree.getRoot()));
    while(ctx.hasNext) {
      visitor.visit(ctx, ctx.next);
      traverseInOrder(ctx);
    }
    return new TraversalSummary<>(ctx.result);
  }

  <R> SteppingTraversalContext<R> traverseInOrder(TraversalContext<R> _ctx) {
    final SteppingTraversalContext<R> ctx = (SteppingTraversalContext<R>) _ctx;
    final Deque<Node> stack = ctx.stack;

    while (!stack.isEmpty() || ctx.node != null) {
      if (ctx.node != null) {
        stack.push(ctx.node);
        if (ctx.shouldTraverseLeft(ctx.node)) {
          ctx.node = ctx.node.left();
        } else {
          ctx.node = null;
        }
      } else {
        ctx.node = stack.pop();
        ctx.hasNext = false;
        if (ctx.shouldVisit(ctx.node)) {
          ctx.hasNext = true;
          ctx.next = ctx.node;
        }
        if (ctx.shouldTraverseRight(ctx.node)) {
          ctx.node = ctx.node.right();
        } else {
          ctx.node = null;
        }
        if (ctx.hasNext) {
          return ctx; // can be continued by calling this method with ctx
        }
      }
    }
    ctx.hasNext = false;
    ctx.next = null;
    return ctx;
  }

  @Override
  public <R> TraversalSummary<R> traverseReverseInOrder(TraversalRange range, Visitor<R> visitor) {
    SteppingTraversalContext<R> ctx = traverseReverseInOrder(new SteppingTraversalContext<>(range, tree.getRoot()));
    while(ctx.hasNext) {
      visitor.visit(ctx, ctx.next);
      traverseReverseInOrder(ctx);
    }
    return new TraversalSummary<>(ctx.result);
  }

  <R> SteppingTraversalContext<R> traverseReverseInOrder(TraversalContext<R> _ctx) {
    final SteppingTraversalContext<R> ctx = (SteppingTraversalContext<R>) _ctx;
    final Deque<Node> stack = ctx.stack;

    while (!stack.isEmpty() || ctx.node != null) {
      if (ctx.node != null) {
        stack.push(ctx.node);
        if (ctx.shouldTraverseRight(ctx.node)) {
          ctx.node = ctx.node.right();
        } else {
          ctx.node = null;
        }
      } else {
        ctx.node = stack.pop();
        ctx.hasNext = false;
        if (ctx.shouldVisit(ctx.node)) {
          ctx.hasNext = true;
          ctx.next = ctx.node;
        }
        if (ctx.shouldTraverseLeft(ctx.node)) {
          ctx.node = ctx.node.left();
        } else {
          ctx.node = null;
        }
        if (ctx.hasNext) {
          return ctx; // can be continued by calling this method with ctx
        }
      }
    }
    ctx.hasNext = false;
    ctx.next = null;
    return ctx;
  }
}
