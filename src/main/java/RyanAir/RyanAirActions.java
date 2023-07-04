package RyanAir;

import Config.ConfigVariables;
import DEBUG.Debug;
import Excel.ExcelMethods;
import MainSelenium.SeleniumStarter;
import UsefulMethods.UsefulMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class RyanAirActions extends SeleniumStarter {
	public static LocalDate today;

	public RyanAirActions() {
		today = LocalDate.now();
	}
	@FindBy(xpath = "//button[contains(text(), 'agree')]")
	private static WebElement cookiesAgreeButton;
	@FindBy(id = "input-button__departure")
	private static WebElement departureInput;
	@FindBy(id = "input-button__destination")
	private static WebElement destinationInput;
	@FindBy(xpath = "//button/span[text() ='Search']")
	private static WebElement searchButton;
	@FindBy(xpath = "//div[contains(@class,'calendar')][contains(@class,'cell')][@data-type][not(contains(@class,'disabled'))]")
	private static WebElement closestFlightAvailable;
	@FindBy(xpath = "//button[contains(@class, 'passengers')]")
	private static WebElement passengersDoneButton;
	@FindBy(xpath = "//flights-summary/div/div[1]//button[contains(@class,'next')]/carousel-arrow")
	private static WebElement upperCarouselNextButton;
	@FindBy(xpath = "//flights-summary/div/div[2]//button[contains(@class,'next')]/carousel-arrow")
	private static WebElement lowerCarouselNextButton;
	public void starter() throws IOException {
		PageFactory.initElements(driver, RyanAirActions.class);
		UsefulMethods usefulMethods = new UsefulMethods();
		driver.get(ConfigVariables.URL);

		if (usefulMethods.isElementPresent(cookiesAgreeButton)) {
			cookiesAgreeButton.click();
		}

		LoadSearchResults(
				ConfigVariables.FROM_COUNTRY,
				ConfigVariables.FROM_AIRPORT,
				ConfigVariables.TO_COUNTRY,
				ConfigVariables.TO_AIRPORT
		);

		scanPricesFromCarousel();
	}
	public void LoadSearchResults(String fromCountry, String fromAirport, String toCountry, String toAirport) {
		WebElement tempAirportInput;
		String xpath;

		departureInput.click();
		xpath = "//fsw-airports//span[contains(text(),'" + fromCountry + "')]";
		tempAirportInput = driver.findElement(By.xpath(xpath));
		tempAirportInput.click();

		xpath = "//fsw-airports//span[contains(text(),'" + fromAirport + "')]";
		tempAirportInput = driver.findElement(By.xpath(xpath));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", tempAirportInput);
		tempAirportInput.click();

		xpath = "//fsw-airports//span[contains(text(),'" + toCountry + "')]";
		tempAirportInput = driver.findElement(By.xpath(xpath));
		tempAirportInput.click();

		xpath = "//fsw-airports//span[contains(text(),'" + toAirport + "')]";
		tempAirportInput = driver.findElement(By.xpath(xpath));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", tempAirportInput);
		tempAirportInput.click();

		closestFlightAvailable.click();
		closestFlightAvailable.click();
		passengersDoneButton.click();
		searchButton.click();
	}
	public void scanPricesFromCarousel() throws IOException {
		ExcelMethods excelMethods = new ExcelMethods();
		Debug debug = new Debug();

		String xpathUpperCarousel1 = "//flights-summary/div/div[1]//carousel/div/ul/li[";
		String xpathUpperCarousel2 = "]/carousel-item/button[not(contains(@class,'disabled'))]";

		String xpathLowerCarousel1 = "//flights-summary/div/div[2]//carousel/div/ul/li[";
		String xpathLowerCarousel2 = "]/carousel-item/button[not(contains(@class,'disabled'))]";

		boolean whileController = true;
		while (whileController) {
			for (int i = 0; i < 6; i++) {
				String tempUpperCarouselXpath = xpathUpperCarousel1 + i + xpathUpperCarousel2;
				try {
					WebElement flightCell = driver.findElement(By.xpath(tempUpperCarouselXpath));
					String flightPrice = driver.findElement(By.xpath(tempUpperCarouselXpath + "//ry-price/span[contains(@class,'integ')]")).getText();
					LocalDate flightDate = LocalDate.parse(flightCell.getAttribute("data-ref"));
					debug.testDebug("Request for excel row 3 - " + flightPrice + " --- " + flightDate);
					excelMethods.putDataToExcel(flightPrice, flightDate, 3);
				} catch (Exception e) {
					debug.functionDebug("flightCell with ID = " + i + " from upper carousel is empty, skipping...");
				}
				String tempLowerCarouselXpath = xpathLowerCarousel1 + i + xpathLowerCarousel2;
				try {
					WebElement flightCell = driver.findElement(By.xpath(tempLowerCarouselXpath));
					String flightPrice = driver.findElement(By.xpath(tempLowerCarouselXpath + "//ry-price/span[contains(@class,'integ')]")).getText();
					LocalDate flightDate = LocalDate.parse(flightCell.getAttribute("data-ref"));
					debug.testDebug("Request for excel row 4 - " + flightPrice + " --- " + flightDate);
					excelMethods.putDataToExcel(flightPrice, flightDate, 4);
					if (ChronoUnit.DAYS.between(today, flightDate) > ConfigVariables.NUM_OF_DAYS_TO_BEE_SCANNED) {
						debug.testDebug("Days between dates: " + ChronoUnit.DAYS.between(today, flightDate));
						debug.testDebug("Exiting the price scanner");
						whileController = false;
						break;
					}
				} catch (Exception e) {
					debug.functionDebug("flightCell with ID = " + i + " from lower carousel is empty, skipping...");
				}
			}
			Actions actions = new Actions(driver);
			actions.moveToElement(upperCarouselNextButton).pause(Duration.ofSeconds(1)).click().perform();
			actions.moveToElement(lowerCarouselNextButton).pause(Duration.ofSeconds(1)).click().perform();
		}
		excelMethods.reviewCollectedData();
	}
	public static void main(String[]args) throws IOException {
		RyanAirActions ryanAirActions = new RyanAirActions();
		ryanAirActions.starter();
	}
}
