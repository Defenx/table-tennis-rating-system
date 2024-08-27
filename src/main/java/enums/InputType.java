package enums;

public enum InputType {
    CHECKBOX("checkbox"),
    NUMBER("number");

    private final String name;

    InputType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
