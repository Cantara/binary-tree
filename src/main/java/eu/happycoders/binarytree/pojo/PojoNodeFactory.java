package eu.happycoders.binarytree.pojo;

import eu.happycoders.binarytree.Node;
import eu.happycoders.binarytree.NodeFactory;

class PojoNodeFactory implements NodeFactory {

    PojoNodeFactory() {
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
