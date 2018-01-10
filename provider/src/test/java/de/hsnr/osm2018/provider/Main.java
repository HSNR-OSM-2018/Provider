package de.hsnr.osm2018.provider;

import org.junit.Assert;
import org.junit.Test;

public class Main {

    @Test
    public void test() {
        TestClass testClass = new TestClass("Hello World");
        Assert.assertEquals(testClass.getMessage(), "Hello World");
    }
}