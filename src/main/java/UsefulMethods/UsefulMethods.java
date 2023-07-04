package UsefulMethods;

import DEBUG.Debug;
import MainSelenium.SeleniumStarter;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.io.IOException;

public class UsefulMethods extends SeleniumStarter {
	public boolean isElementPresent(WebElement element) throws IOException {
		Debug debug = new Debug();
		try{
			String temp = element.getTagName();
			debug.functionDebug("isExists: YES ");
			debug.functionDebug(debugWebElement(element));
			return true;
		}catch (Exception e){
			debug.functionDebug("isExists: NO ");
			return false;
		}
	}
	public String debugWebElement(WebElement element) {
		String tagName = "";
		String text = "";
		String value = "";
		String name = "";
		String className = "";
		String location = "";
		String isEnabled = "";
		String isDisplayed = "";

		try {
			if (element != null) {
				tagName = element.getTagName();
				text = element.getText();
				value = element.getAttribute("value");
				name = element.getAttribute("name");
				className = element.getAttribute("class");
				location = element.getLocation().toString();
				isEnabled = Boolean.toString(element.isEnabled());
				isDisplayed = Boolean.toString(element.isDisplayed());
			}
		} catch (Exception e) {
			System.out.println("Exception while debugging web element: " + e.getMessage());
		}

		String debugInfo = String.format(
				"Element details:\n" +
						"	Tag name: %s\n" +
						"	Text: %s\n" +
						"	Value: %s\n" +
						"	Name: %s\n" +
						"	Class name: %s\n" +
						"	Location: %s\n" +
						"	Enabled: %s\n" +
						"	Displayed: %s\n",
				tagName, text, value, name, className, location, isEnabled, isDisplayed
		);
		return debugInfo;
	}
	public String getAbbreviation(String... strings) {
		StringBuilder abbreviation = new StringBuilder();
		for (String s : strings) {
			abbreviation.append(Character.toUpperCase(s.charAt(0)));
		}
		return abbreviation.toString();
	}
}
