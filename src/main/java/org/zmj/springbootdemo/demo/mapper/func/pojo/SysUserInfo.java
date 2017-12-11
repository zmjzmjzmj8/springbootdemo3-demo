package org.zmj.springbootdemo.demo.mapper.func.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="sys_userinfo")
public class SysUserInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long usrId;
  @Column(name = "nickname")
  private String nickname;
  @Column(name = "avatar_Key")
  private String avatar_Key;
  @Column(name = "signature")
  private String signature;
  @Column(name = "area")
  private String area;
  @Column(name = "region")
  private String region;
  @Column(name = "sex")
  private String sex;
  @Column(name = "birthday")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
  private Date birthday;


  public long getUsrId() {
    return usrId;
  }

  public void setUsrId(long usr_Id) {
    this.usrId = usr_Id;
  }


  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }


  public String getAvatar_Key() {
    return avatar_Key;
  }

  public void setAvatar_Key(String avatar_Key) {
    this.avatar_Key = avatar_Key;
  }


  public String getSignature() {
    return signature;
  }

  public void setSignature(String signature) {
    this.signature = signature;
  }


  public String getArea() {
    return area;
  }

  public void setArea(String area) {
    this.area = area;
  }

  public String getRegion() {
    return region;
  }

  public void setRegion(String region) {
    this.region = region;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
  public Date getBirthday() {
    return birthday;
  }

  public void setBirthday(Date birthday) {
    this.birthday = birthday;
  }
}
