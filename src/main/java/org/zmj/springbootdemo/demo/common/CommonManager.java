package org.zmj.springbootdemo.demo.common;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class CommonManager {
    @PersistenceContext
    public EntityManager em;
}
