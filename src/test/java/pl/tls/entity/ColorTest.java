/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.tls.entity;

import org.junit.Assert;
import org.junit.Test;

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
		Assert.assertNotNull(random);
	}
}
