package org.launchcode.customhomepage.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Widget.
 */
@Entity
@Table(name = "widget")
public class Widget implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "props")
    private String props;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "widgets" }, allowSetters = true)
    private UserWidgets userwidgets;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Widget id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public Widget type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProps() {
        return this.props;
    }

    public Widget props(String props) {
        this.setProps(props);
        return this;
    }

    public void setProps(String props) {
        this.props = props;
    }

    public UserWidgets getUserwidgets() {
        return this.userwidgets;
    }

    public void setUserwidgets(UserWidgets userWidgets) {
        this.userwidgets = userWidgets;
    }

    public Widget userwidgets(UserWidgets userWidgets) {
        this.setUserwidgets(userWidgets);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Widget)) {
            return false;
        }
        return id != null && id.equals(((Widget) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Widget{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", props='" + getProps() + "'" +
            "}";
    }
}
