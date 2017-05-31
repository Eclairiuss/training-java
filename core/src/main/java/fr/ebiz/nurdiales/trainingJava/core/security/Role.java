package fr.ebiz.nurdiales.trainingJava.core.security;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Column(unique = true)
    @Size(min = 2, max = 20)
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
