package gov.nyc.doitt.nycopendataintegration.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class PropertyLoaderUtil {

	static {
		loadLog4jProperties();
	}
	private static Logger logger = Logger.getLogger(PropertyLoaderUtil.class);
	private static ResourceBundle applicationProperties = loadApplicationProperties();

	// Socrata Config
	public static final String DOMAIN = applicationProperties
			.getString("socrata.domain");
	public static final String USER = applicationProperties
			.getString("socrata.user");
	public static final String PASSWORD = applicationProperties
			.getString("socrata.password");
	public static final String APPTOKEN = applicationProperties
			.getString("socrata.apptoken");

	// Proxy Config
	public static final String PROXY_HOST = applicationProperties
			.getString("proxy.host");
	public static final String PROXY_PORT = applicationProperties
			.getString("proxy.port");
	public static final boolean PROXY_ENABLED = Boolean
			.parseBoolean(applicationProperties.getString("proxy.enabled"));

	// Excel Config

	public static final String DATASET_FILE = applicationProperties
			.getString("dataset.file");

	public static final String DATASET_LIST_SHEET = applicationProperties
			.getString("dataset.list.sheet");

	// public static int DATASET_LIST_HEADERS_ROW = Integer
	// .parseInt(applicationProperties
	// .getString("dataset.list.headers.row"));
	// public static int DATASET_LIST_VALUES_FIRST_ROW = Integer
	// .parseInt(applicationProperties
	// .getString("dataset.list.values.first.row"));

	public static final String COLUMN_DATASET_ID_HEADER = applicationProperties
			.getString("column.dataset.id.header");
	public static final String COLUMN_CATEGORY_HEADER = applicationProperties
			.getString("column.category.header");
	public static final String COLUMN_TYPE_HEADER = applicationProperties
			.getString("column.type.header");

	public static void init() {
	}

	private static void loadLog4jProperties() {
		String path = null;
		String APP_CONFIG_DIR = "src/main/resources";
		try {

			path = URLDecoder.decode(APP_CONFIG_DIR, "UTF-8");

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		File f = new File(path, "log4j.properties");
		Properties log4jProps = null;
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(f);
			log4jProps = new Properties();
			log4jProps.load(inputStream);
			PropertyConfigurator.configure(log4jProps);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static ResourceBundle loadApplicationProperties() {
		String path = null;
		String APP_CONFIG_DIR = "src/main/resources";
		try {
			path = URLDecoder.decode(APP_CONFIG_DIR, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		File f = new File(path, "NYCOpenDataIntegration.properties");
		ResourceBundle bundle = null;
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(f);
			bundle = new PropertyResourceBundle(inputStream);
			logger.info("Loaded external properties: " + f.getCanonicalPath());
		} catch (Exception e) {
			logger.error("Error loading external properties: ", e);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				logger.error("Error loading external properties: ", e);
			}
		}
		return bundle;
	}

}
