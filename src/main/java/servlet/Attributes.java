package servlet;

public enum Attributes {

    TOURNAMENT_SESSION_ATTRIBUTE("tournament"),
    USER_DTO_SESSION_ATTRIBUTE("userDto");
    private final String name;

    Attributes(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
