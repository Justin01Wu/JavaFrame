package com.justa.springboot.service;

import static org.junit.Assert.*;

import org.junit.Test;

public class ComplianceServiceTest {

    private ComplianceService cs = new ComplianceService("Rita Wang,Yan Wu");

    @Test
    public void isCompliantUw() {
        boolean result = cs.isCompliantUw("Rita Wang");
        assertTrue(result);
    }

    @Test
    public void isNotCompliantUw() {
        boolean result= cs.isCompliantUw("Justin Wu");
        assertFalse(result);
    }

    @Test(expected = IllegalStateException.class)
    public void initFailure() {
        new ComplianceService(null);
    }

}
