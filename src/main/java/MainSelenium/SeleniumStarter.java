package MainSelenium;

import Config.ConfigVariables;
import DEBUG.Debug;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.io.IOException;
import java.time.Duration;


abstract public class SeleniumStarter {
	protected static ChromeDriver driver;

	@BeforeClass
	public static void setUp() throws IOException {
		System.setProperty("webdriver.chrome.driver", "D:/JDK/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("disable-infobars");
		options.addArguments("--remote-allow-origins=*");
		options.addArguments("--profile-directory=" + ConfigVariables.USER_PROFILE);
		options.addArguments("--user-data-dir=" + ConfigVariables.USER_PROFILES_DIR);

		driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
		Debug debug = new Debug();
		debug.cleanLogs();
		debug.systemDebug("Session started.");
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(4));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
	}

	@AfterClass
	public static void tearDown() throws IOException, InterruptedException {
		Debug debug = new Debug();
		debug.systemDebug("Quiting");
		//driver.quit();
	}
}
