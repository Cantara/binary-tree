package eu.happycoders.binarytree.pojo;

import eu.happycoders.binarytree.NodeFactory;
import eu.happycoders.binarytree.NodeFactoryInitializer;
import eu.happycoders.binarytree.NodeFactoryProviderName;

import java.util.Collections;
import java.util.Map;

@NodeFactoryProviderName("pojo")
public class PojoNodeFactoryInitializer implements NodeFactoryInitializer {

    @Override
    public Map<String, String> defaultConfiguration() {
        return Collections.emptyMap();
    }

    @Override
    public NodeFactory configure(Map<String, String> configuration) {
        return new PojoNodeFactory();
    }
}
