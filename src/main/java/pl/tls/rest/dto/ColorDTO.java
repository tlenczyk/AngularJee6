/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.tls.rest.dto;

import pl.tls.entity.Color;

/**
 *
 * @author tomek
 */
public class ColorDTO {

    private String code;
    private Color name;

    public ColorDTO(String code, Color name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Color getName() {
        return name;
    }

    public void setName(Color name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ColorDTO{" + "code=" + code + ", name=" + name + '}';
    }
}
