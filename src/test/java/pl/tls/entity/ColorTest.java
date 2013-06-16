/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.tls.entity;

import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

/**
 *
 * @author Tomasz.Lenczyk
 */
public class ColorTest {

    public ColorTest() {
    }

    //
    @Test
    public void testRandom() {
        Color random = Color.getRandom();
        assertThat(random, notNullValue());
    }
}
