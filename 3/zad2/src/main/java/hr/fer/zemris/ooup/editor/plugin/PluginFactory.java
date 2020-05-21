package hr.fer.zemris.ooup.editor.plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class PluginFactory {

    public static Plugin newInstance(String pluginKind, String name)
            throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException {
        Class<Plugin> clazz = (Class<Plugin>) Class.forName(
                "hr.fer.zemris.ooup.editor.plugin." + pluginKind);
        Constructor<Plugin> constructor = clazz.getConstructor(String.class);
        return constructor.newInstance(name);
    }

}
