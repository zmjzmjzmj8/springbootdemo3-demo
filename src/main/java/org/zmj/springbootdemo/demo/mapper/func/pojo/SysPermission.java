package org.zmj.springbootdemo.demo.mapper.func.pojo;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Sys_Permission")
public class SysPermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
    @Column(name = "url")
    private String url;

    @Column(name = "pid")
    private Long pid;

    @Column(name = "method")
    private String method;

    @Column(name = "type")
    private String type;

    @Transient
    private List<SysPermission> children = new ArrayList<SysPermission>();

    public SysPermission() {
    }

    public SysPermission(String name, String description, String url, Long pid, String method) {
        this.name = name;
        this.description = description;
        this.url = url;
        this.pid = pid;
        this.method = method;
    }

    @ManyToMany(mappedBy = "permissions")
    @Transient
    private Set<SysRole> roles = new HashSet<SysRole>();

    public Set<SysRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<SysRole> roles) {
        this.roles = roles;
    }

    public List<SysPermission> getChildren() {
        return children;
    }

    public void setChildren(List<SysPermission> children) {
        this.children = children;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
