package com.onevizion.uitest.api;

import java.util.List;

import javax.annotation.Resource;

import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Component
public class SeleniumSettings {

    private ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();

    private ThreadLocal<String> url = new ThreadLocal<>();

    private ThreadLocal<UserProperties> userProperties = new ThreadLocal<>();

    private ThreadLocal<List<String>> windows = new ThreadLocal<>();

    private ThreadLocal<String> testUser = new ThreadLocal<>();

    private ThreadLocal<String> testStatus = new ThreadLocal<>();

    private ThreadLocal<String> testName = new ThreadLocal<>();

    private ThreadLocal<String> testLog = new ThreadLocal<>();

    private ThreadLocal<String> testFailScreenshot = new ThreadLocal<>();

    @Resource
    private Long defaultTimeout;

    @Resource
    private String uploadFilesPath;

    @Resource
    private String remoteAddress;

    @Resource
    private Boolean remoteWebDriver;

    @Resource
    private Boolean headlessMode;

    @Resource
    private Boolean codeCoverage;

    @Resource
    private String screenshotsPath;

    @Resource
    private String ciAddr;

    @Resource
    private String testPassword;

    @Resource
    private String testPasswordApiV3;

    @Resource
    private String browser;

    @Resource
    private String restApiCredential;

    @Resource
    private String restApiUrl;

    @Resource
    private String restApiVersion;

    @Resource
    private String serverUrl;

    public WebDriver getWebDriver() {
        return webDriver.get();
    }

    void setWebDriver(WebDriver webDriver) {
        this.webDriver.set(webDriver);
    }

    public String getUrl() {
        return url.get();
    }

    void setUrl(String url) {
        this.url.set(url);
    }

    public UserProperties getUserProperties() {
        return userProperties.get();
    }

    public void setUserProperties(UserProperties userProperties) {
        this.userProperties.set(userProperties);
    }

    public List<String> getWindows() {
        return windows.get();
    }

    void setWindows(List<String> windows) {
        this.windows.set(windows);
    }

    public String getTestUser() {
        return testUser.get();
    }

    void setTestUser(String testUser) {
        this.testUser.set(testUser);
    }

    public String getTestStatus() {
        return testStatus.get();
    }

    void setTestStatus(String testStatus) {
        this.testStatus.set(testStatus);
    }

    public String getTestName() {
        return testName.get();
    }

    void setTestName(String testName) {
        this.testName.set(testName);
    }

    void clearTestLog() {
        testLog.remove();
    }

    public String getTestLog() {
        return testLog.get();
    }

    void setTestLog(String testLog) {
        this.testLog.set(testLog);
    }

    void clearTestFailScreenshot() {
        testFailScreenshot.remove();
    }

    public String getTestFailScreenshot() {
        return testFailScreenshot.get();
    }

    void setTestFailScreenshot(String testFailScreenshot) {
        this.testFailScreenshot.set(testFailScreenshot);
    }

    public Long getDefaultTimeout() {
        return defaultTimeout;
    }

    public String getUploadFilesPath() {
        return uploadFilesPath;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public String getTestPassword() {
        return testPassword;
    }
 
    public String getTestPasswordApiV3() {
        return testPasswordApiV3;
    }

    public Boolean getRemoteWebDriver() {
        return remoteWebDriver;
    }

    public Boolean getHeadlessMode() {
        return headlessMode;
    }

    public Boolean getCodeCoverage() {
        return codeCoverage;
    }

    public String getScreenshotsPath() {
        return screenshotsPath;
    }

    public String getCiAddr() {
        return ciAddr;
    }

    public String getBrowser() {
        return browser;
    }

    public String getRestApiCredential() {
        return restApiCredential;
    }

    public String getRestApiUrl() {
        return restApiUrl;
    }

    public String getRestApiVersion() {
        return restApiVersion;
    }

    public String getServerUrl() {
        return serverUrl;
    }

}