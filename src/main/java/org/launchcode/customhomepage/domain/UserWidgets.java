package org.launchcode.customhomepage.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A UserWidgets.
 */
@Entity
@Table(name = "user_widgets")
public class UserWidgets implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    @OneToMany(mappedBy = "userwidgets")
    @JsonIgnoreProperties(value = { "userwidgets" }, allowSetters = true)
    private Set<Widget> widgets = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserWidgets id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserWidgets user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<Widget> getWidgets() {
        return this.widgets;
    }

    public void setWidgets(Set<Widget> widgets) {
        if (this.widgets != null) {
            this.widgets.forEach(i -> i.setUserwidgets(null));
        }
        if (widgets != null) {
            widgets.forEach(i -> i.setUserwidgets(this));
        }
        this.widgets = widgets;
    }

    public UserWidgets widgets(Set<Widget> widgets) {
        this.setWidgets(widgets);
        return this;
    }

    public UserWidgets addWidget(Widget widget) {
        this.widgets.add(widget);
        widget.setUserwidgets(this);
        return this;
    }

    public UserWidgets removeWidget(Widget widget) {
        this.widgets.remove(widget);
        widget.setUserwidgets(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserWidgets)) {
            return false;
        }
        return id != null && id.equals(((UserWidgets) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserWidgets{" +
            "id=" + getId() +
            "}";
    }
}
