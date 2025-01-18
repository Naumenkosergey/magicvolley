package ru.magicvolley;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.magicvolley.enums.CoachType;
import ru.magicvolley.enums.Role;
import ru.magicvolley.security.service.UserDetailsImpl;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class Util {

    public static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("YYYY-MM-DD");
    public static String UNDEFINED_VALUE = "Не указано";

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
        return Objects.nonNull(value) && Objects.equals(value, UNDEFINED_VALUE) ? Integer.parseInt(value) : 0;
    }

    public static String getOrUndefinedIfNull(Integer value) {
        return isNonNullAndNotZeroValue(value) ? String.valueOf(value) : UNDEFINED_VALUE;
    }

    public static String getOrUndefinedIfNull(String value) {
        return Objects.nonNull(value) ? value : UNDEFINED_VALUE;
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
}
