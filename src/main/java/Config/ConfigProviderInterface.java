package Config;

import com.typesafe.config.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public interface ConfigProviderInterface {
	Config config = readConfig();

	static Config readConfig() {
		return ConfigFactory.systemProperties().hasPath("testAccounts")
				? ConfigFactory.load(ConfigFactory.systemProperties().getString("testAccounts"))
				: ConfigFactory.load("config.conf");
	}
}