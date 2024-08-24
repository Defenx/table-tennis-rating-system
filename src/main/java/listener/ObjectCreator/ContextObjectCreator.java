package listener.ObjectCreator;

import jakarta.servlet.ServletContext;

import java.util.HashMap;
import java.util.Map;


public class ContextObjectCreator {
    private final Map<String, Object> services = new HashMap<>();

    public void addContextObjects(AppPart appPart) {
        services.putAll(appPart.addPart(services));
    }

    public void addObjectsToContext(ServletContext context) {
        services.forEach(context::setAttribute);
    }

    public void addAttribute(String key, Object object) {
        services.put(key, object);
    }
}
