package com;

import com.maveric.core.cucumber.CucumberBaseTest;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = {"./src/test/resources/features/DemoBank.feature"},
        glue ={ "com.internetapp.stepDefs"}
)
public class CucumberRunner extends CucumberBaseTest {


}
