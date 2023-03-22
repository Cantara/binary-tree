package no.cantara.binarytree;

import no.cantara.binarytree.pojo.PojoNodeFactory;

public interface NodeFactory {

    Node createNode(long data);

    Node nilNode();

    static NodeFactory defaultFactory() {
        return new PojoNodeFactory();
    }
}
