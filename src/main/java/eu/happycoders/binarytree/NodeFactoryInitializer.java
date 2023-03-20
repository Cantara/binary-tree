package eu.happycoders.binarytree;

import java.util.Map;


public interface NodeFactoryInitializer {

    /**
     * Provides a default configuration suited for this factory provider.
     *
     * @return the provider default configuration
     */
    Map<String, String> defaultConfiguration();

    /**
     * Creates a new <code>NodeFactory</code> using the given configuration.
     *
     * @param configuration the configuration to use
     * @return the newly created and configured <code>NodeFactory</code>
     */
    NodeFactory configure(Map<String, String> configuration);
}
