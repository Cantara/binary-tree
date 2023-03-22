package no.cantara.binarytree.pojo;

import no.cantara.binarytree.Node;
import no.cantara.binarytree.NodeFactory;

public class PojoNodeFactory implements NodeFactory {

    public PojoNodeFactory() {
    }

    @Override
    public PojoNode createNode(long data) {
        return new PojoNode(data);
    }

    @Override
    public PojoNode nilNode() {
        return new PojoNilNode();
    }

    private static class PojoNilNode extends PojoNode {
        private PojoNilNode() {
            super(0);
            this.color(Node.BLACK);
        }

        @Override
        public boolean isNil() {
            return true;
        }
    }
}
