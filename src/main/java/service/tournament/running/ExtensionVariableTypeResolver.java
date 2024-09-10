package service.tournament.running;

import entity.Extension;
import entity.Tournament;
import enums.ExtensionName;

import java.math.BigDecimal;

public class ExtensionVariableTypeResolver {

    public int getTrainingSets(Tournament tournament) {
        return Integer.parseInt(getExtension(ExtensionName.TRAINING_SETS,tournament));
    }

    public int getPlayOffSets(Tournament tournament) {
        return Integer.parseInt(getExtension(ExtensionName.PLAYOFF_SETS,tournament));
    }

    public String getIsRatingValue(Tournament tournament) {
        return getExtension(ExtensionName.IS_RATING,tournament);
    }

    public int getNumberOfParticipants(Tournament tournament) {
        return Integer.parseInt(getExtension(ExtensionName.NUMBER_OF_PARTICIPANTS,tournament));
    }

    public BigDecimal getAverageRating(Tournament tournament) {
        return new BigDecimal(getExtension(ExtensionName.AVERAGE_RATING,tournament));
    }

    /**
     * Получаем значение value класса Extension по его названию.
     *
     * @param extensionName
     * @param tournament
     * @return Возвращает строковое представление значения по названию ExtensionName.
     * <p>
     * В ином случае возвращаем строку с ошибкой, которой в принципе не должно быть,
     * и она указывает на то что ошибка в базе или при создании турнира возникла ошибка
     * при создании Extension
     */
    public String getExtension(ExtensionName extensionName, Tournament tournament) {
        for (Extension extension : tournament.getExtensions()) {
            if (extension.getName().equals(extensionName)) {
                return extension.getValue();
            }
        }
        return  "Не удалось найти расширение с именем " + extensionName.toString();
    }
}
