package com.udacity.jwdnd.course1.cloudstorage.models;


import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "ROLES")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    private String role;

    public Role() {
    }

    @ManyToMany(mappedBy = "roles")
//    @JoinTable(name = "USER_ROLES", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<User> users = new HashSet<>();;

    public Role(String role) {
        this.role = role;
    }

    public Integer getId() {
        return roleId;
    }

    public void setId(Integer id) {
        this.roleId = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", role='" + role + '\'' +
                '}';
    }
}
