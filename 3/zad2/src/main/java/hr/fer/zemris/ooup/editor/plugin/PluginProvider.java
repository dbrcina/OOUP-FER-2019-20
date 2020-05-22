package hr.fer.zemris.ooup.editor.plugin;

import java.util.Iterator;
import java.util.ServiceLoader;

public class PluginProvider {

    private static PluginProvider provider;
    private final ServiceLoader<Plugin> serviceLoader;

    private PluginProvider() {
        serviceLoader = ServiceLoader.load(Plugin.class);
    }

    public static PluginProvider getInstance() {
        if (provider == null) {
            provider = new PluginProvider();
        }
        return provider;
    }

    public Iterator<Plugin> getPlugins() {
        return serviceLoader.iterator();
    }

}
