package enums;

import entity.Extension;
import lombok.Getter;

@Getter
public enum ExtensionName {
    IS_RATING {
        @Override
        String buildExtensionValue(String extensionValue) {
            return extensionValue == null ? "нет" : "да";
        }
    },
    AVERAGE_RATING,
    TRAINING_SETS,
    PLAYOFF_SETS;

    public Extension buildExtension(String extensionValue) {
        return Extension.builder()
                .name(this)
                .value(buildExtensionValue(extensionValue))
                .build();
    }

    String buildExtensionValue(String extensionValue) {
        return extensionValue;
    }

}
