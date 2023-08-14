package UsefulMethods;

import DEBUG.Debug;
import MainSelenium.SeleniumStarter;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

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
			abbreviation.append(Character.toLowerCase(s.charAt(1)));
		}
		return abbreviation.toString();
	}
	public String getFlagEmoji(String countryName) {
		HashMap<String, String> countryCodes = new HashMap<String, String>();
		countryCodes.put("Albania", "\uD83C\uDDE6\uD83C\uDDEB");
		countryCodes.put("Austria", "\uD83C\uDDE6\uD83C\uDDF9");
		countryCodes.put("Belgium", "\uD83C\uDDE7\uD83C\uDDEA");
		countryCodes.put("Bosnia & Herzegovina", "\uD83C\uDDE7\uD83C\uDDE6");
		countryCodes.put("Bulgaria", "\uD83C\uDDE7\uD83C\uDDEC");
		countryCodes.put("Croatia", "\uD83C\uDDED\uD83C\uDDF7");
		countryCodes.put("Cyprus", "\uD83C\uDDE8\uD83C\uDDFE");
		countryCodes.put("Czech Republic", "\uD83C\uDDE8\uD83C\uDDFF");
		countryCodes.put("Denmark", "\uD83C\uDDE9\uD83C\uDDF0");
		countryCodes.put("Estonia", "\uD83C\uDDEA\uD83C\uDDEA");
		countryCodes.put("Finland", "\uD83C\uDDEB\uD83C\uDDEE");
		countryCodes.put("France", "\uD83C\uDDEB\uD83C\uDDF7");
		countryCodes.put("Germany", "\uD83C\uDDE9\uD83C\uDDEA");
		countryCodes.put("Greece", "\uD83C\uDDEC\uD83C\uDDF7");
		countryCodes.put("Hungary", "\uD83C\uDDED\uD83C\uDDFA");
		countryCodes.put("Ireland", "\uD83C\uDDEE\uD83C\uDDEA");
		countryCodes.put("Israel", "\uD83C\uDDEE\uD83C\uDDF1");
		countryCodes.put("Italy", "\uD83C\uDDEE\uD83C\uDDF9");
		countryCodes.put("Jordan", "\uD83C\uDDEF\uD83C\uDDF4");
		countryCodes.put("Latvia", "\uD83C\uDDF1\uD83C\uDDFB");
		countryCodes.put("Lithuania", "\uD83C\uDDF1\uD83C\uDDF9");
		countryCodes.put("Luxembourg", "\uD83C\uDDF1\uD83C\uDDEC");
		countryCodes.put("Malta", "\uD83C\uDDF2\uD83C\uDDF9");
		countryCodes.put("Montenegro", "\uD83C\uDDF2\uD83C\uDDEB");
		countryCodes.put("Morocco", "\uD83C\uDDF2\uD83C\uDDE6");
		countryCodes.put("Netherlands", "\uD83C\uDDF3\uD83C\uDDF1");
		countryCodes.put("Norway", "\uD83C\uDDF3\uD83C\uDDF4");
		countryCodes.put("Poland", "\uD83C\uDDF5\uD83C\uDDF1");
		countryCodes.put("Portugal", "\uD83C\uDDF5\uD83C\uDDF9");
		countryCodes.put("Romania", "\uD83C\uDDF7\uD83C\uDDF4");
		countryCodes.put("Serbia", "\uD83C\uDDF7\uD83C\uDDEA");
		countryCodes.put("Slovakia", "\uD83C\uDDF8\uD83C\uDDF0");
		countryCodes.put("Slovenia", "\uD83C\uDDF8\uD83C\uDDFE");
		countryCodes.put("Spain", "\uD83C\uDDEA\uD83C\uDDF8");
		countryCodes.put("Sweden", "\uD83C\uDDF8\uD83C\uDDEA");
		countryCodes.put("Switzerland", "\uD83C\uDDE8\uD83C\uDDED");
		countryCodes.put("Turkey", "\uD83C\uDDF9\uD83C\uDDF7");
		countryCodes.put("United Kingdom", "\uD83C\uDDEC\uD83C\uDDE7");

		String countryCode = countryCodes.get(countryName);
		if (countryCode == null) {
			return null;
		}
		return countryCode;
	}
	public String numberToEmoji(int number) {
		String emoji = "";
		switch (number) {
			case 0:
				emoji = "0️⃣";
				break;
			case 1:
				emoji = "1️⃣";
				break;
			case 2:
				emoji = "2️⃣";
				break;
			case 3:
				emoji = "3️⃣";
				break;
			case 4:
				emoji = "4️⃣";
				break;
			case 5:
				emoji = "5️⃣";
				break;
			case 6:
				emoji = "6️⃣";
				break;
			case 7:
				emoji = "7️⃣";
				break;
			case 8:
				emoji = "8️⃣";
				break;
			case 9:
				emoji = "9️⃣";
				break;
			default:
				break;
		}
		return emoji;
	}
}
