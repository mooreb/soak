package com.mooreb.soak;

import org.testng.annotations.Test;

public class SoakTest {

    @Test(groups="integration")
    public void runSoaker() {
        final Soak soak = new Soak();
        soak.soak();
    }
}
