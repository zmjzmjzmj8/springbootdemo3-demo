package org.zmj.springbootdemo.demo.mapper.test.pojo;


import javax.persistence.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Entity
@Table(name = "history")
public class History {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String qqid;
  private String myid;
  private String jitter;
  private String shimmer;
  private String hnr;
  private java.sql.Timestamp time;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getQqid() {
    return qqid;
  }

  public void setQqid(String qqid) {
    this.qqid = qqid;
  }

  public String getMyid() {
    return myid;
  }

  public void setMyid(String myid) {
    this.myid = myid;
  }

  public String getJitter() {
    return jitter;
  }

  public void setJitter(String jitter) {
    this.jitter = jitter;
  }

  public String getShimmer() {
    return shimmer;
  }

  public void setShimmer(String shimmer) {
    this.shimmer = shimmer;
  }

  public String getHnr() {
    return hnr;
  }

  public void setHnr(String hnr) {
    this.hnr = hnr;
  }

  public String getTime() {
    String tsStr = "";
    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      //方法一
      tsStr = sdf.format(time);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return tsStr;
  }

  public void setTime(java.sql.Timestamp time) {
    this.time = time;
  }
}
