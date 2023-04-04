import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

abstract class AbstractClass {
    static Properties prop = new Properties();
    private static InputStream configFile;
    private static String password;
    private static String login;
    private static String baseUrl;
    private static String token;
    final static String xAuthToken="X-Auth-Token";

    @BeforeAll
    static void initTest() throws IOException {
        configFile = new FileInputStream("src/test/resources/my.properties");
        prop.load(configFile);
        baseUrl = prop.getProperty("url");
        password = prop.getProperty("password");
        login = prop.getProperty("login");

    }

    @AfterEach
    public void cleanUpEach() {

    }

    @AfterAll
    public static void cleanUp() {

    }

    public static String getBaseUrl() {
        return baseUrl;
    }
    public static String getLogin() {
        return login;
    }
    public static String getPassword() {
        return password;
    }
    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        AbstractClass.token = token;
    }

}
