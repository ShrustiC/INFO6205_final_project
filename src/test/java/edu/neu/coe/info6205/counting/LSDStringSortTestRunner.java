package edu.neu.coe.info6205.counting;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:edu/neu/coe/info6205/counting/lsdsort/LSDStringSort.feature",
        glue = "classpath:edu.neu.coe.info6205.counting.LSDStringSortStepDefinition",
        plugin = "html:target/LSDStringSort-report", strict = true
)
public class LSDStringSortTestRunner {

}
