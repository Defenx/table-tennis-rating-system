package enums;

import lombok.Getter;

@Getter
public enum InputType {
    CHECKBOX("checkbox"),
    NUMBER("number");

    private final String name;

    InputType(String name) {
        this.name = name;
    }
}
