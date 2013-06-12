/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.tls.rest.dto;

import pl.tls.entity.Color;
import pl.tls.entity.FuelType;

/**
 *
 * @author tomek
 */
public class FuelTypeDTO {

    private String code;
    private FuelType name;

    public FuelTypeDTO(String code, FuelType name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public FuelType getName() {
        return name;
    }

    public void setName(FuelType name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "FuelTypeDTO{" + "code=" + code + ", name=" + name + '}';
    }
}
