package ru.magicvolley;

import lombok.experimental.UtilityClass;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

@UtilityClass
public class Util {

    public static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("YYYY-MM-DD");

    public static <T> Stream<T> getSaveStream(Collection<T> collections) {

        return Objects.isNull(collections)
                ? Stream.empty()
                : collections.stream();
    }
}
