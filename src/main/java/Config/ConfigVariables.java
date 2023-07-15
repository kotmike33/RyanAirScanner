package Config;

public interface ConfigVariables extends ConfigProviderInterface{
	String URL = ConfigProviderInterface.readConfig().getString("url");
	String USER_PROFILES_DIR = ConfigProviderInterface.readConfig().getString("userProfileDir");
	String USER_PROFILE = ConfigProviderInterface.readConfig().getString("userProfile");
	String FROM_AIRPORT =ConfigProviderInterface.readConfig().getString("fromAirport");
	String FROM_COUNTRY =ConfigProviderInterface.readConfig().getString("fromCountry");
	String TO_AIRPORT =ConfigProviderInterface.readConfig().getString("toAirport");
	String TO_COUNTRY =ConfigProviderInterface.readConfig().getString("toCountry");
	int DESIRED_DAYS_NUM_FROM = ConfigProviderInterface.readConfig().getInt("desiredDaysNumFrom");
	int DESIRED_DAYS_NUM_TO = ConfigProviderInterface.readConfig().getInt("desiredDaysNumTo");
	int NUM_OF_DAYS_TO_BEE_SCANNED = ConfigProviderInterface.readConfig().getInt("numOfDaysToBeScanned");
	int SCANNING_FREQUENCY_DAYS = ConfigProviderInterface.readConfig().getInt("scanningFrequencyDays");
	String TELEGRAM_BOT_TOKEN = ConfigProviderInterface.readConfig().getString("telegramBotToken");
	String CHANNEL_CHAT_ID = ConfigProviderInterface.readConfig().getString("chatID");
	String PROJECT_PATH = ConfigProviderInterface.readConfig().getString("projectPath");
}
