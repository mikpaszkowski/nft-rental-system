package com.rentalSystem.xrpl.common.utils;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Function;

public abstract class OptionalUtils {

    public static <SOURCE, RESULT> RESULT mapOr(@Nullable SOURCE INPUT, Function<SOURCE, RESULT> MAPPER, RESULT defaultValue) {
        return Optional.ofNullable(INPUT)
                .map(MAPPER)
                .orElse(defaultValue);
    }
}
