package org.raman.hibernate.org.raman.hibernate.domain;


import com.sun.org.apache.bcel.internal.generic.LNEG;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "users")
@Entity
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(name = "name", nullable = false, unique=true)
    private String name;

    @Column(name = "description", nullable = true)
    //@Type(type = "nstring")
    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + " Description="+description+" ]";
    }
}