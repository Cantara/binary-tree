package no.cantara.binarytree;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Iterative depth-first (DFS) traversal on a binary tree.
 *
 * @author <a href="sven@happycoders.eu">Sven Woltmann</a>
 */
public final class DepthFirstTraversalIterative implements DepthFirstTraversal {

  private final BinaryTree tree;

  public DepthFirstTraversalIterative(BinaryTree tree) {
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
    TraversalContext<R> ctx = traversePreOrder(new TraversalContext<>(range), visitor);
    return new TraversalSummary<>(ctx.result);
  }

  <R> TraversalContext<R> traversePreOrder(TraversalContext<R> ctx, Visitor<R> visitor) {
    Node node = tree.getRoot();
    if (node == null) {
      return ctx;
    }

    // Not using a java.util.Stack here. See
    // https://www.happycoders.eu/java/queue-deque-stack-ultimate-guide/#Why_you_should_not_use_Stack
    Deque<Node> stack = new ArrayDeque<>();
    stack.push(node);

    while (!stack.isEmpty()) {
      node = stack.poll();
      if (ctx.shouldVisit(node)) {
        visitor.visit(ctx, node);
        if (ctx.stop) {
          return ctx;
        }
      }
      if (ctx.shouldTraverseRight(node)) {
        if (node.right() != null) {
          stack.push(node.right());
        }
      }
      if (ctx.shouldTraverseLeft(node)) {
        if (node.left() != null) {
          stack.push(node.left());
        }
      }
    }
    return ctx;
  }

  @Override
  public <R> TraversalSummary<R> traversePostOrder(TraversalRange range, Visitor<R> visitor) {
    TraversalContext<R> ctx = traversePostOrder(new TraversalContext<>(range), visitor);
    return new TraversalSummary<>(ctx.result);
  }

  <R> TraversalContext<R> traversePostOrder(TraversalContext<R> ctx, Visitor<R> visitor) {
    Node node = tree.getRoot();
    Node lastVisitedNode = null;

    // Not using a java.util.Stack here. See
    // https://www.happycoders.eu/java/queue-deque-stack-ultimate-guide/#Why_you_should_not_use_Stack
    Deque<Node> stack = new ArrayDeque<>();

    while (!stack.isEmpty() || node != null) {
      if (node != null) {
        stack.push(node);
        if (ctx.shouldTraverseLeft(node)) {
          node = node.left();
        } else {
          node = null;
        }
      } else {
        Node topNode = stack.peek();
        if (ctx.shouldTraverseRight(topNode) && topNode.right() != null && !topNode.right().equals(lastVisitedNode)) {
          node = topNode.right();
        } else {
          if (ctx.shouldVisit(topNode)) {
            visitor.visit(ctx, topNode);
            if (ctx.stop) {
              return ctx;
            }
          }
          lastVisitedNode = stack.poll();
        }
      }
    }
    return ctx;
  }

  @Override
  public <R> TraversalSummary<R> traverseInOrder(TraversalRange range, Visitor<R> visitor) {
    TraversalContext<R> ctx = traverseInOrder(new TraversalContext<>(range), visitor);
    return new TraversalSummary<>(ctx.result);
  }

  <R> TraversalContext<R> traverseInOrder(TraversalContext<R> ctx, Visitor<R> visitor) {
    Node node = tree.getRoot();

    // Not using a java.util.Stack here. See
    // https://www.happycoders.eu/java/queue-deque-stack-ultimate-guide/#Why_you_should_not_use_Stack
    Deque<Node> stack = new ArrayDeque<>();

    while (!stack.isEmpty() || node != null) {
      if (node != null) {
        stack.push(node);
        if (ctx.shouldTraverseLeft(node)) {
          node = node.left();
        } else {
          node = null;
        }
      } else {
        node = stack.pop();
        if (ctx.shouldVisit(node)) {
          visitor.visit(ctx, node);
          if (ctx.stop) {
            return ctx;
          }
        }
        if (ctx.shouldTraverseRight(node)) {
          node = node.right();
        } else {
          node = null;
        }
      }
    }
    return ctx;
  }

  @Override
  public <R> TraversalSummary<R> traverseReverseInOrder(TraversalRange range, Visitor<R> visitor) {
    TraversalContext<R> ctx = traverseReverseInOrder(new TraversalContext<>(range), visitor);
    return new TraversalSummary<>(ctx.result);
  }

  <R> TraversalContext<R> traverseReverseInOrder(TraversalContext<R> ctx, Visitor<R> visitor) {
    Node node = tree.getRoot();

    // Not using a java.util.Stack here. See
    // https://www.happycoders.eu/java/queue-deque-stack-ultimate-guide/#Why_you_should_not_use_Stack
    Deque<Node> stack = new ArrayDeque<>();

    while (!stack.isEmpty() || node != null) {
      if (node != null) {
        stack.push(node);
        if (ctx.shouldTraverseRight(node)) {
          node = node.right();
        } else {
          node = null;
        }
      } else {
        node = stack.pop();
        if (ctx.shouldVisit(node)) {
          visitor.visit(ctx, node);
          if (ctx.stop) {
            return ctx;
          }
        }
        if (ctx.shouldTraverseLeft(node)) {
          node = node.left();
        } else {
          node = null;
        }
      }
    }
    return ctx;
  }
}
