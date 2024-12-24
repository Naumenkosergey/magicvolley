package ru.magicvolley;

import lombok.experimental.UtilityClass;
import ru.magicvolley.enums.CoachType;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class Util {

    public static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("YYYY-MM-DD");

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

    public static boolean getOrDefaultIfNull(Boolean value, Boolean defaultFalue) {
        return Objects.nonNull(value) ? value : defaultFalue;
    }
}
