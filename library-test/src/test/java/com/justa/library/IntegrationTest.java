package com.justa.library;

/**
 * this interface means the unit test is not general unit test, it can only be used for Integration testing
 * Build process will skip this kind of unit test in test phase but will add them in verification phase 

 * 
 * the package name and class name is used in pom.xml file, so please change it if they are changed 
 * @author justin.wu  
 *
 */
public interface IntegrationTest {}
