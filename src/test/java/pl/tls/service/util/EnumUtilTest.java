package pl.tls.service.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.is;
import org.junit.Test;
import pl.tls.entity.Color;

/**
 *
 * @author tomek
 */
public class EnumUtilTest {

    @Test
    public void shouldReturnRandomColor() {
        Color random = EnumUtil.getRandom(Color.class);
        assertThat(random, is(notNullValue()));
    }
}
