package pl.tls.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author Tomasz.Lenczyk
 */
@Entity
@NamedQueries({
    @NamedQuery(name = Make.REMOVE_ALL, query = "DELETE FROM Make")
})
public class Make implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String REMOVE_ALL = "make.removeAllMakes";
    @Id
    @GeneratedValue
    private Long id;
    private String name;
//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "make")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "MAKE_ID")
    private List<Model> models = new ArrayList<>();

    public Make() {
    }

    public Make(String name) {
        this.name = name;
    }

    public Make(String name, List<Model> models) {
        this.name = name;
        this.models = models;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Model> getModels() {
        return models;
    }

    public void setModels(List<Model> models) {
        this.models = models;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.name);
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
        final Make other = (Make) obj;
        return true;
    }

    @Override
    public String toString() {
        return "Make{" + "id=" + id + ", name=" + name + '}';
    }
}
