package clinica_juridica.backend.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static final String ISO_DATE_FORMAT = "yyyy-MM-dd";
    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ofPattern(ISO_DATE_FORMAT);

    private DateUtils() {
        // Private constructor to hide the implicit public one
    }

    public static String formatLocalDate(LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.format(ISO_FORMATTER);
    }

    public static LocalDate parseLocalDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        return LocalDate.parse(dateStr, ISO_FORMATTER);
    }

    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }
}
