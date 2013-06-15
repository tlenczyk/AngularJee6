/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.tls.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Tomasz.Lenczyk
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "auction.removeAllAuctions", query = "DELETE FROM Auction")
})
public class Auction implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    @Temporal(TemporalType.DATE)
    private Date productionYear;
    @ManyToOne(cascade = CascadeType.REFRESH)
    private Make make;
    @ManyToOne(cascade = CascadeType.REFRESH)
    private Model model;
    @Enumerated(EnumType.STRING)
    private Color color;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;
    private Long milage;
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    public Auction() {
    }

    public Auction(String title, String description, Date productionYear, Make make, Model model, Color color, BigDecimal price, FuelType fuelType, Long milage, Date creationDate) {
        this.title = title;
        this.description = description;
        this.productionYear = productionYear;
        this.make = make;
        this.model = model;
        this.color = color;
        this.price = price;
        this.fuelType = fuelType;
        this.milage = milage;
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Date getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(Date productionYear) {
        this.productionYear = productionYear;
    }

    public Make getMake() {
        return make;
    }

    public void setMake(Make make) {
        this.make = make;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public Long getMilage() {
        return milage;
    }

    public void setMilage(Long milage) {
        this.milage = milage;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Auction other = (Auction) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.make, other.make)) {
            return false;
        }
        if (!Objects.equals(this.model, other.model)) {
            return false;
        }
        if (this.color != other.color) {
            return false;
        }
        if (!Objects.equals(this.price, other.price)) {
            return false;
        }
        if (this.fuelType != other.fuelType) {
            return false;
        }
        if (!Objects.equals(this.milage, other.milage)) {
            return false;
        }
        if (!Objects.equals(this.creationDate, other.creationDate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Auction{" + "id=" + id + ", title=" + title + ", description=" + description + ", productionYear=" + productionYear + ", make=" + make + ", model=" + model + ", color=" + color + ", price=" + price + ", fuelType=" + fuelType + ", milage=" + milage + ", creationDate=" + creationDate + '}';
    }
}
