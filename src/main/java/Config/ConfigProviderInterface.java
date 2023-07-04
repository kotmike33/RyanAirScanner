package Config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public interface ConfigProviderInterface {
	Config config = readConfig();

	static Config readConfig(){
		return ConfigFactory.systemProperties().hasPath("testAccounts")
				? ConfigFactory.load(ConfigFactory.systemProperties().getString("testAccounts"))
				: ConfigFactory.load("config.conf");
	}
}
