package it.arakne.dbing.magento.logger;

import it.arakne.dbing.magento.util.Config;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MagentoLogger {
	static private FileHandler fileTxt;
	static private SimpleFormatter formatterTxt;

	static public void setup(String logFile) throws IOException {
		// Create Logger
		Logger logger = Logger.getLogger("");
		Level level = Level.parse(Config.LOG_LEVEL_STRING);
//		Level level = Config.LOG_LEVEL;
		logger.setLevel(level);
		
		fileTxt = new FileHandler(logFile);

		// Create txt Formatter
		formatterTxt = new SimpleFormatter();
		fileTxt.setFormatter(formatterTxt);
		logger.addHandler(fileTxt);

	}
}