package enums;

public enum Attributes {

    TOURNAMENT_SESSION_ATTRIBUTE("tournament"),
    USER_SESSION_ATTRIBUTE("user");
    private final String name;

    Attributes(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name;
    }

    //TODO Нужен ли этот Enum?
}
