package eu.happycoders.binarytree;

import eu.happycoders.binarytree.pojo.PojoNodeFactory;

public interface NodeFactory {

    Node createNode(long data);

    Node nilNode();

    static NodeFactory defaultFactory() {
        return new PojoNodeFactory();
    }
}
