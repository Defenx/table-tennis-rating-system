package service.extension;

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
        conversionMap.put(ExtensionName.NUMBER_OF_TRAINING_MATCHES, Integer::parseInt);
        conversionMap.put(ExtensionName.VICTORIES_IN_TRAINING_MATCHES, Integer::parseInt);
        conversionMap.put(ExtensionName.VICTORIES_IN_PLAYOFF_MATCHES, Integer::parseInt);
        conversionMap.put(ExtensionName.IS_RATING, Function.identity());
        conversionMap.put(ExtensionName.NUMBER_OF_PARTICIPANTS, Integer::parseInt);
        conversionMap.put(ExtensionName.AVERAGE_RATING, BigDecimal::new);
    }

    @SuppressWarnings("unchecked")
    public <T> T getExtensionValue(ExtensionName extensionName, Tournament tournament) {
        String extensionValue = getExtension(extensionName, tournament);
        Function<String, ?> converter = conversionMap.get(extensionName);

        return (T) converter.apply(extensionValue);
    }

    public String getExtension(ExtensionName extensionName, Tournament tournament) {
        return tournament.getExtensions().stream()
                .filter(extension -> extension.getName().equals(extensionName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Can't find extension with name: " + extensionName))
                .getValue();
    }
}
