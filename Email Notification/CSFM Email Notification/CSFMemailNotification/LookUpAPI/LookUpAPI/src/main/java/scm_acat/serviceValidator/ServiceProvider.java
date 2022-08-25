package scm_acat.serviceValidator;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ServiceProvider {


	public Properties readProp() {
		Properties prop = null;
		try {
			String Path = new File("src/main/java/scm_acat/serviceValidator/Config.properties").getAbsolutePath();
			FileInputStream fis = new FileInputStream(new File(Path));
			prop = new Properties();
			prop.load(fis);
			return prop;
		} catch (Exception e) {
			String Path = new File("Config.properties").getAbsolutePath();
			FileInputStream fis;
			try {
				fis = new FileInputStream(new File(Path));
				prop = new Properties();
				prop.load(fis);
				return prop;
			} catch (Exception e1) {
				e1.printStackTrace();
				return null;
			}

		}

	}


}
