package pl.tls.entity;

import java.util.Arrays;

/**
 *
 * @author Tomasz.Lenczyk
 */
public enum Marks {

	RENAULT(new Mark("Renault", Arrays.asList(new Model("Megane"), new Model("Scenic"), new Model("Clio")))),
	PEUGEOT(new Mark("Peugeot", Arrays.asList(new Model("206"), new Model("207"), new Model("307"), new Model("407")))),
	CITROEN(new Mark("Citroen", Arrays.asList(new Model("C3"), new Model("C4"), new Model("C5"), new Model("C3 Picasso"), new Model("C4 Picasso"))));
	private Mark mark;

	private Marks(Mark mark) {
		this.mark = mark;
	}

	public Mark getMark() {
		return mark;
	}
}
