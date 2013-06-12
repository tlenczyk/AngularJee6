package pl.tls.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * @author Tomasz.Lenczyk
 */
@Entity
@NamedQueries({
    @NamedQuery(name = Model.REMOVE_ALL, query = "DELETE FROM Model")
})
public class Model implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String REMOVE_ALL = "model.removeAllModels";
    @Id
    @GeneratedValue
    private Long id;
    private String name;

//    @ManyToOne
//    @JoinColumn(name = "MARK_ID")
//    private Mark mark;
    public Model() {
    }

    public Model(String name) {
        this.name = name;
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

//    public Mark getMark() {
//        return mark;
//    }
//
//    public void setMark(Mark mark) {
//        this.mark = mark;
//    }
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
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
        final Model other = (Model) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
//        if (!Objects.equals(this.mark, other.mark)) {
//            return false;
//        }
        return true;
    }

    @Override
    public String toString() {
        return "Model{" + "id=" + id + ", name=" + name + '}';
    }
}
