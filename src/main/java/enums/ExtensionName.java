package enums;

public enum ExtensionName {
    //TODO:
//    добавить описание в конструкторе, посмотреть код
    IS_RATING("1"),
    NUMBER_OF_SET_IN_WORKOUT("2"),
    NUMBER_OF_SET_IN_PLAY_OFF("3"),
    NUMBER_OF_PLAYERS("4");
    private final String description;
    public String getDescription(){
        return description;
    }

    ExtensionName(String description) {
        this.description = description;
    }

}
