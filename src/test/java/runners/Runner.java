package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(features = {"src/test/resources/features"}, // the path of the feature files

		glue = { "stepDefs", "hooks" }, // the path of the step definition files
		plugin = { "pretty:target/cucumber-pretty.txt",
				"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:", "json:target/cucumber.json",
				"rerun:target/rerun.txt" }, // to generate different types of reporting
		monochrome = true, // display the console output in a proper readable format
		tags="@PipelineCreation",
		dryRun = false, publish = true) // to check the mapping is proper between feature file and step definition file

public class Runner extends AbstractTestNGCucumberTests {

}
