package listener.ObjectCreator.part;

import config.ConstantsConfig;
import listener.ObjectCreator.AppPart;

import java.util.Map;

public class ConfigProperties implements AppPart {

    @Override
    public Map<String, Object> addPart(Map<String, Object> services) {
        config.ConfigProperties configProperties = new config.ConfigProperties();
        ConstantsConfig constants = new ConstantsConfig(configProperties);

        return Map.of("constants", constants);
    }
}
