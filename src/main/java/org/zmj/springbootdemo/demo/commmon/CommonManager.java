package org.zmj.springbootdemo.demo.commmon;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CommonManager {
    @PersistenceContext
    public EntityManager em;
}
