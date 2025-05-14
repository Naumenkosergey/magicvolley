package ru.magicvolley;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.magicvolley.enums.CoachType;
import ru.magicvolley.enums.Role;
import ru.magicvolley.security.service.UserDetailsImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class Util {

    public static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static String UNDEFINED_VALUE = "Не указано";
    public static String EMPTY_VALUE = "";

    public static <T> Stream<T> getSaveStream(Collection<T> collections) {

        return Objects.isNull(collections)
                ? Stream.empty()
                : collections.stream();
    }

    public static String getLike(String str) {
        return "%" + str + "%";
    }

    public static String getLike(CoachType... types) {
        String type = Arrays.stream(types).map(CoachType::name).collect(Collectors.joining(";"));
        return "%" + type + "%";
    }

    public static boolean getOrDefaultIfNull(Boolean value, Boolean defaultValue) {
        return Objects.nonNull(value) ? value : defaultValue;
    }

    public static Integer getOrDefaultIfNull(String value) {
        return StringUtils.isNotBlank(value) && !Objects.equals(value, UNDEFINED_VALUE) ? Integer.parseInt(value) : 0;
    }

    public static String getOrUndefinedIfNull(Integer value) {
        return isNonNullAndNotZeroValue(value) ? String.valueOf(value) : UNDEFINED_VALUE;
    }

    public static String getOrNullIfNull(Integer value) {
        return isNonNullAndNotZeroValue(value) ? String.valueOf(value) : null;
    }

    public static String getOrUndefinedIfNull(String value) {
        return StringUtils.isNotBlank(value) ? value : UNDEFINED_VALUE;
    }

    public static String getOrUndefinedIfNullForLimitationString(String value) {
        return StringUtils.isNotBlank(value) && !Objects.equals(value, UNDEFINED_VALUE) ? getStringDateConcat(LocalDate.parse(value)) : UNDEFINED_VALUE;
    }

    public static String getOrUndefinedIfNullForLimitation(String value) {
        return StringUtils.isNotBlank(value) && !Objects.equals(value, UNDEFINED_VALUE) ? LocalDate.parse(value).format(DATE_FORMAT) : UNDEFINED_VALUE;
    }

    private Boolean isNonNullAndNotZeroValue(Integer value) {
        return Objects.nonNull(value) && value != 0;
    }


    public Boolean isAdminCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsImpl userDetailsImpl) {
            return userDetailsImpl.getAuthorities().stream().anyMatch(r -> r.toString().equals(Role.ADMIN.name()));
        }
        return Boolean.FALSE;
    }

    public UUID getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsImpl userDetailsImpl) {
            return userDetailsImpl.getId();
        } else {
            throw new AuthenticationException("Наавторизованный пользователь") {
            };
        }
    }

    public static String trim(String string, char ch) {
        return trim(string, ch, ch);
    }

    public static String trim(String string, char leadingChar, char trailingChar) {
        return string.replaceAll("^[" + leadingChar + "]+|[" + trailingChar + "]+$", "");
    }

    public static String addNotExistChar(String telephone, char c) {
        return "+" + trim(telephone, '+');
    }

    public static String getOrUndefinedIfNull(LocalDate birthday) {
        return Objects.nonNull(birthday) ? birthday.format(DATE_FORMAT) : EMPTY_VALUE;
    }
    
    public static String getNumberMonth(LocalDate date) {
        return switch (date.getMonth()) {
            case JANUARY -> "01";
            case FEBRUARY -> "02";
            case MARCH -> "03";
            case APRIL -> "04";
            case MAY -> "05";
            case JUNE -> "06";
            case JULY -> "07";
            case AUGUST -> "08";
            case SEPTEMBER -> "09";
            case OCTOBER -> "10";
            case NOVEMBER -> "11";
            case DECEMBER -> "12";
        };
    }

    public static String getNumberDay(LocalDate date) {
        return switch (date.getDayOfMonth()) {
            case 1 -> "01";
            case 2 -> "02";
            case 3 -> "03";
            case 4 -> "04";
            case 5 -> "05";
            case 6 -> "06";
            case 7 -> "07";
            case 8 -> "08";
            case 9 -> "09";
            case 10 -> "10";
            case 11 -> "11";
            case 12 -> "12";
            case 13 -> "13";
            case 14 -> "14";
            case 15 -> "15";
            case 16 -> "16";
            case 17 -> "17";
            case 18 -> "18";
            case 19 -> "19";
            case 20 -> "20";
            case 21 -> "21";
            case 22 -> "22";
            case 23 -> "23";
            case 24 -> "24";
            case 25 -> "25";
            case 26 -> "26";
            case 27 -> "27";
            case 28 -> "28";
            case 29 -> "29";
            case 30 -> "30";
            case 31 -> "31";
            default -> throw new IllegalStateException("Unexpected value: " + date.getDayOfMonth());
        };
    }

    public String getStringDateConcat(LocalDate date) {
        return getNumberDay(date) + "." + getNumberMonth(date);
    }
}
