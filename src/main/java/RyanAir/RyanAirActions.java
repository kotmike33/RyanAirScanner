package RyanAir;

import Config.ConfigProviderInterface;
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
import java.util.NoSuchElementException;

import static JFrame.RyanairScannerUI.updateConfig;

public class RyanAirActions extends SeleniumStarter{
	public static LocalDate today;
	public static String searchURL = "failed to load link :<";
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

	public void starter() throws IOException, InterruptedException {
		PageFactory.initElements(driver, RyanAirActions.class);
		UsefulMethods usefulMethods = new UsefulMethods();
		driver.get(ConfigVariables.URL);
		if (usefulMethods.isElementPresent(cookiesAgreeButton)) {
			cookiesAgreeButton.click();
		}
		if(ConfigVariables.TO_AIRPORT.length()<3){
			for (int i=2;i>-1;i++){
				try {
					LoadSearchResults(
							ConfigVariables.FROM_COUNTRY,
							ConfigVariables.FROM_AIRPORT,
							ConfigVariables.TO_COUNTRY,
							String.valueOf(i)
					);
					scanPricesFromCarousel();
				}catch (NoSuchElementException e){
					break;
				}
			}
		}else {
			LoadSearchResults(
					ConfigVariables.FROM_COUNTRY,
					ConfigVariables.FROM_AIRPORT,
					ConfigVariables.TO_COUNTRY,
					ConfigVariables.TO_AIRPORT
			);
			scanPricesFromCarousel();
		}
	}
	public void LoadSearchResults(String fromCountry, String fromAirport, String toCountry, String toAirport) throws IOException {
		if(!driver.getCurrentUrl().equals(ConfigVariables.URL)){
			driver.get(ConfigVariables.URL);
		}
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

		if(toAirport.length()<3){
			xpath = "//fsw-airport-item["+ toAirport +"]";
			updateConfig("toAirport", driver.findElement(By.xpath(xpath)).getText());
		}else {
			xpath = "//fsw-airports//span[contains(text(),'" + toAirport + "')]";
		}
		tempAirportInput = driver.findElement(By.xpath(xpath));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", tempAirportInput);
		tempAirportInput.click();

		closestFlightAvailable.click();
		closestFlightAvailable.click();
		passengersDoneButton.click();
		searchButton.click();
	}
	public void scanPricesFromCarousel() throws IOException, InterruptedException {
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(50));

		ExcelMethods excelMethods = new ExcelMethods();
		Debug debug = new Debug();

		String xpathUpperCarousel1 = "//flights-summary/div/div[1]//carousel/div/ul/li[";
		String xpathUpperCarousel2 = "]/carousel-item/button[not(contains(@class,'disabled'))]";

		String xpathLowerCarousel1 = "//flights-summary/div/div[2]//carousel/div/ul/li[";
		String xpathLowerCarousel2 = "]/carousel-item/button[not(contains(@class,'disabled'))]";

		Thread.sleep(1000);
		searchURL = driver.getCurrentUrl();
		debug.functionDebug("url found: " + searchURL);

		boolean whileController = true;
		int emptyTicketCounter=0;
		while (whileController || emptyTicketCounter<20) {
			for (int i = 0; i < 6; i++) {
				String tempUpperCarouselXpath = xpathUpperCarousel1 + i + xpathUpperCarousel2;
				try {
					WebElement flightCell = driver.findElement(By.xpath(tempUpperCarouselXpath));
					String flightPrice = driver.findElement(By.xpath(tempUpperCarouselXpath + "//ry-price/span[@class='price__integers carousel-date-price']")).getText();
					LocalDate flightDate = LocalDate.parse(flightCell.getAttribute("data-ref"));
					debug.functionDebug("DAYS SCANNED: " + ChronoUnit.DAYS.between(today, flightDate));
					if (ChronoUnit.DAYS.between(today, flightDate) > ConfigVariables.NUM_OF_DAYS_TO_BEE_SCANNED) {
						debug.testDebug("Days between dates: " + ChronoUnit.DAYS.between(today, flightDate));
						debug.testDebug("Exiting the price scanner");
						whileController = false;
						break;
					}
					debug.testDebug("Request for excel row 3 - " + flightPrice + " --- " + flightDate);
					excelMethods.putDataToExcel(flightPrice, flightDate, 3);
				} catch (Exception e) {
					debug.functionDebug("flightCell with ID = " + i + " from upper carousel is empty, skipping...");
					emptyTicketCounter++;
				}
				String tempLowerCarouselXpath = xpathLowerCarousel1 + i + xpathLowerCarousel2;
				try {
					WebElement flightCell = driver.findElement(By.xpath(tempLowerCarouselXpath));
					String flightPrice = driver.findElement(By.xpath(tempLowerCarouselXpath + "//ry-price/span[@class='price__integers carousel-date-price']")).getText();
					LocalDate flightDate = LocalDate.parse(flightCell.getAttribute("data-ref"));
					debug.testDebug("Request for excel row 4 - " + flightPrice + " --- " + flightDate);
					excelMethods.putDataToExcel(flightPrice, flightDate, 4);
				} catch (Exception e) {
					debug.functionDebug("flightCell with ID = " + i + " from lower carousel is empty, skipping...");
					emptyTicketCounter++;
				}
			}
			Actions actions = new Actions(driver);
			actions.moveToElement(upperCarouselNextButton).pause(Duration.ofMillis(1500)).click().perform();
			actions.moveToElement(lowerCarouselNextButton).pause(Duration.ofMillis(1500)).click().perform();
			Thread.sleep(1200);
		}
		excelMethods.reviewCollectedData();

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(4));
	}
}
