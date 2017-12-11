package org.zmj.springbootdemo.demo.mapper.func.pojo;


import javax.persistence.*;

@Entity
@Table(name="sys_role_user")
public class Sys_role_user {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private Long user_id;
  private Long role_id;

  @Transient
  private SysUser sysUser;

  public Sys_role_user() {
  }

  public Sys_role_user(Long user_id, Long role_id) {
    this.user_id = user_id;
    this.role_id = role_id;
  }

  public SysUser getSysUser() {
    return sysUser;
  }

  public void setSysUser(SysUser sysUser) {
    this.sysUser = sysUser;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUser_id() {
    return user_id;
  }

  public void setUser_id(Long user_id) {
    this.user_id = user_id;
  }

  public Long getRole_id() {
    return role_id;
  }

  public void setRole_id(Long role_id) {
    this.role_id = role_id;
  }
}
