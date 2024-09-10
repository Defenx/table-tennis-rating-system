package service.tournament.running;

import entity.Extension;
import entity.Tournament;
import enums.ExtensionName;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ExtensionVariableTypeResolver {

    private final Map<ExtensionName, Function<String, ?>> conversionMap;

    public ExtensionVariableTypeResolver() {
        conversionMap = new HashMap<>();
        conversionMap.put(ExtensionName.TRAINING_SETS, Integer::parseInt);
        conversionMap.put(ExtensionName.PLAYOFF_SETS, Integer::parseInt);
        conversionMap.put(ExtensionName.IS_RATING, value -> value);
        conversionMap.put(ExtensionName.NUMBER_OF_PARTICIPANTS, Integer::parseInt);
        conversionMap.put(ExtensionName.AVERAGE_RATING, BigDecimal::new);
    }

    public <T> T getExtensionValue(ExtensionName extensionName, Tournament tournament) {
        String extensionValue = getExtension(extensionName, tournament);
        Function<String, ?> converter = conversionMap.get(extensionName);

        return (T) converter.apply(extensionValue);
    }

    public String getExtension(ExtensionName extensionName, Tournament tournament) {
        for (Extension extension : tournament.getExtensions()) {
            if (extension.getName().equals(extensionName)) {
                return extension.getValue();
            }
        }
        throw new IllegalArgumentException("Не удалось найти расширение с именем " + extensionName);
    }
}
