/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.tls.rest.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Tomasz.Lenczyk
 */
public class AddAuctionRequest {

    private String title;
    private String description;
    private String productionYear;
    private Long makeId;
    private Long modelId;
    private String color;
    private String fuel;
    private Long milage;
    private BigDecimal price;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(String productionYear) {
        this.productionYear = productionYear;
    }

    public Long getMakeId() {
        return makeId;
    }

    public void setMakeId(Long makeId) {
        this.makeId = makeId;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public Long getMilage() {
        return milage;
    }

    public void setMilage(Long milage) {
        this.milage = milage;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "AddAuctionRequest{" + "title=" + title + ", description=" + description + ", productionYear=" + productionYear + ", makeId=" + makeId + ", modelId=" + modelId + ", color=" + color + ", fuel=" + fuel + ", milage=" + milage + ", price=" + price + '}';
    }
}
