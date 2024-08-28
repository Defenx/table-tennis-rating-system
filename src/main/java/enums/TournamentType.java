package enums;

public enum TournamentType {
    SINGLE_PLAYER("Одиночный"),
    TEAM("Командный");

    private final String displayName;

    TournamentType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
