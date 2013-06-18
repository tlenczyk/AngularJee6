package pl.tls.service.util;

import java.util.Random;

/**
 *
 * @author tomek
 */
public class EnumUtil {

    public static <T extends Enum> T getRandom(Class<T> enumType) {
        int random = new Random().nextInt(enumType.getEnumConstants().length);
        return enumType.getEnumConstants()[random];
    }
}
