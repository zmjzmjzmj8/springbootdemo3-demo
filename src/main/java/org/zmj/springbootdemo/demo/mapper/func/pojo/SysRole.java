package org.zmj.springbootdemo.demo.mapper.func.pojo;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "Sys_Role")
public class SysRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;

    public SysRole() {
    }

    @Transient
    private Set<SysPermission> permissions = new HashSet<SysPermission>();

    @ManyToMany
    @JoinTable(name = "sys_permission_role", joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    public Set<SysPermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<SysPermission> permissions) {
        this.permissions = permissions;
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

}
