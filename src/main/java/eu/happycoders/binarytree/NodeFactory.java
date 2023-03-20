package eu.happycoders.binarytree;

import java.util.ServiceLoader;

public interface NodeFactory {

    Node createNode(long data);

    Node nilNode();

    static NodeFactory defaultFactory() {
        NodeFactoryInitializer factoryInitializer = load("pojo");
        return factoryInitializer.configure(factoryInitializer.defaultConfiguration());
    }

    static NodeFactoryInitializer load(String provider) {
        return ServiceLoader.load(NodeFactoryInitializer.class)
                .stream()
                .filter(p -> p.type().isAnnotationPresent(NodeFactoryProviderName.class))
                .filter(p -> p.type().getAnnotation(NodeFactoryProviderName.class).value().equals(provider))
                .map(ServiceLoader.Provider::get)
                .findFirst()
                .orElseThrow();
    }
}
