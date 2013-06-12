package pl.tls.entity;

import java.util.Random;

/**
 *
 * @author Tomasz.Lenczyk
 */
public enum Color {

	WHITE,
	BLACK,
	RED,
	BLUE,
	SILVER;

	public static Color getRandom() {
		int random = new Random().nextInt(Color.values().length);
		return Color.values()[random];
	}
}
