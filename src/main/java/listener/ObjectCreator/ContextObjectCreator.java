package listener.ObjectCreator;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ContextObjectCreator {
    private final Map<String, Object> services = new HashMap<>();

    public void addConfiguration(AppPart appPart){
        services.putAll(appPart.getAppParts(services));
    }
}