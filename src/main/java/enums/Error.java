package enums;

import lombok.Getter;

@Getter
public enum Error {
    CAPITAL_LETTER_ERROR("Вводимые данные должны начинаться с заглавной буквы"),
    COUNT_OF_PARTICIPANT_ERROR("Число участников должно быть четным"),
    EMAIL_PATTERN_ERROR("Адрес электронной почты не соответствует формату"),
    EMAIL_REPEAT_ERROR("Этот адрес электронной почты уже зарегистрирован"),
    EMPTINESS_ERROR("Поле не может быть пустым"),
    IS_NUMERIC_ERROR("Значение этого поля должно быть числом"),
    LANGUAGE_ERROR("Разрешены только символы русского алфавита"),
    MAX_LENGTH_ERROR("Превышено максимальное количество символов"),
    MIN_LENGTH_ERROR("Количество символов ниже минимального значения"),
    NEGATIVE_VALUE_ERROR("Значение этого поля не может быть отрицательным"),
    SPACE_SYMBOLS_ERROR("Поле не должно содержать пробельных символов"),
    SPECIAL_CHARACTERS_ERROR("Поле должно содержать специальные символы");

    private String message;

    Error(String message) {
        this.message = message;
    }
}
