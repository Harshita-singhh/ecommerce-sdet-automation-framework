package com.ecommerce.framework;

import org.testng.Assert;
import org.testng.annotations.Test;

public class FirstTest {

    @Test
    public void additionShouldEqualFour() {
        int actualSum = 2 + 2;
        Assert.assertEquals(actualSum, 4, "2 + 2 should equal 4");
    }
}
