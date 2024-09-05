package servlet.util;


import jakarta.servlet.http.HttpServletRequest;

public class TooltipUtil {

    public static void setTooltips(HttpServletRequest req) {
        req.setAttribute("tooltipFirstName", "Имя должно состоять только из букв русского алфавита и начинаться с заглавной буквы");
        req.setAttribute("tooltipSurname", "Фамилия должна состоять только из букв русского алфавита и начинаться с заглавной буквы");
        req.setAttribute("tooltipEmail", "Email должен соответствовать формату, например: mail@example.com");
        req.setAttribute("tooltipPassword", "Пароль: 5-16 символов, минимум 1 специальный символ (!@#$%()^&*), без пробелов");
    }
}