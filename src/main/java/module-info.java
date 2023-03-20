import eu.happycoders.binarytree.NodeFactoryInitializer;
import eu.happycoders.binarytree.pojo.PojoNodeFactoryInitializer;

module no.cantara.binarytree {

    exports eu.happycoders.binarytree;

    uses NodeFactoryInitializer;

    provides NodeFactoryInitializer with PojoNodeFactoryInitializer;
}