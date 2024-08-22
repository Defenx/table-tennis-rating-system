package enums;


public enum ConfigSection {
    PATHS("paths"),
    ERRORS("errors"),
    PAGES("pages");

    private final String value;

    ConfigSection(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}