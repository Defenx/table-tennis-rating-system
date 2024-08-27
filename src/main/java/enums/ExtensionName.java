package enums;

import lombok.Getter;

import java.util.Map;

@Getter
public enum ExtensionName {
    IS_RATING("Рейтинговый турнир", InputType.CHECKBOX, Map.of("checked", "")),
    TRAINING_SETS("Кол-во побед в тренировочных матчах", InputType.NUMBER, Map.of("min", "1", "max", "3")),
    PLAYOFF_SETS("Кол-во побед в плейофф матчах", InputType.NUMBER, Map.of("min", "1", "max", "20"));

    private final String displayName;
    private final InputType inputType;
    private final Map<String, String> htmlAttributes;

    ExtensionName(String displayName,
                  InputType inputType,
                  Map<String, String> htmlAttributes) {
        this.displayName = displayName;
        this.inputType = inputType;
        this.htmlAttributes = htmlAttributes;
    }

    public String getHtmlAttributesAsString() {
        var stringBuilder = new StringBuilder();
        var space = " ";
        stringBuilder.append(space);
        for (var entry : htmlAttributes.entrySet()) {
            var attributeName = entry.getKey();
            var value = entry.getValue();
            var attribute = "%s=\"%s\"".formatted(attributeName, value);
            stringBuilder.append(attribute).append(space);
        }
        return stringBuilder.toString();
    }
}
