/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.tls.rest.pojo;

/**
 *
 * @author Tomasz.Lenczyk
 */
public class Category {

	private String code;
	private String name;

	public Category(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}
}
