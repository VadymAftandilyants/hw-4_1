package myprojects.automation.assignment4;


import myprojects.automation.assignment4.model.ProductData;
import myprojects.automation.assignment4.utils.Properties;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

/**
 * Contains main script actions that may be used in scripts.
 */
public class GeneralActions {
    private WebDriver driver;
    private WebDriverWait wait;


    public GeneralActions(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
    }

    /**
     * Logs in to Admin Panel.
     * @param login
     * @param password
     */
    public void login(String login, String password) {
        // TODO implement logging in to Admin Panel
        driver = new EventFiringWebDriver(driver);
        driver.get(Properties.getBaseAdminUrl());
        driver.findElement(By.id("email")).sendKeys(login);
        driver.findElement(By.id("passwd")).sendKeys(password);
        driver.findElement(By.name("submitLogin")).click();
//        throw new UnsupportedOperationException();
    }

    public void createProduct(ProductData newProduct) {
        // TODO implement product creation scenario

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='subtab-AdminCatalog']/a/span")));
        Actions act = new Actions(driver);
        WebElement element = driver.findElement(By.xpath("//*[@id='subtab-AdminCatalog']/a/span"));
        act.moveToElement(element).perform();
        driver.findElement(By.xpath("//*[@id='subtab-AdminProducts']/a")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='page-header-desc-configuration-add']/span")));
        driver.findElement(By.xpath("//*[@id='page-header-desc-configuration-add']/span")).click();

        String newName = newProduct.getName();
        driver.findElement(By.xpath("//*[@id='form_step1_name_1']")).sendKeys(newName);

        String newQty = newProduct.getQty().toString();
        driver.findElement(By.xpath("//*[@id='form_step1_qty_0_shortcut']")).sendKeys(newQty);

        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("window.scrollBy(0,200)");

        WebElement price = driver.findElement(By.xpath("//*[@id='form_step1_price_shortcut']"));
        act.doubleClick(price).perform();
        driver.findElement(By.xpath("//*[@id='form_step1_price_shortcut']")).sendKeys(Keys.DELETE);

        String newPrice = String.valueOf(newProduct.getPrice());
        driver.findElement(By.xpath("//*[@id='form_step1_price_shortcut']")).sendKeys(newPrice);

        driver.findElement(By.xpath("//*[@id='form']/div[4]/div[1]/div")).click();
//        Assert.assertEquals(driver.findElement(By.xpath("//*[@id='form']/div[4]/div[1]/div")).getText(),"");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("growl-message")));
        Assert.assertEquals(driver.findElement(By.className("growl-message")).getText(), "Настройки обновлены.");
        driver.findElement(By.className("growl-close")).click();

        driver.findElement(By.xpath("//*[@id='form']/div[4]/div[2]/div/button[1]/span")).click();


//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("growl-message")));
//        Assert.assertEquals(driver.findElement(By.className("growl-message")).getText(), "Настройки обновлены.");
//        driver.findElement(By.className("growl-close")).click();
//            WebElement popUpClose = driver.findElement(By.className("growl-close"));
//            JavascriptExecutor executor = (JavascriptExecutor) driver;
//            executor.executeScript("arguments[0].click()", popUpClose);

//        throw new UnsupportedOperationException();
    }

    public void checkOnSite(String name, String qty, String price ) {
        driver.get("http://prestashop-automation.qatestlab.com.ua/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='content']/section/a")));
        driver.findElement(By.xpath("//*[@id='content']/section/a")).click();

        driver.findElement(By.xpath("//*[@id='search_widget']/form/input[2]")).sendKeys(name);
        driver.findElement(By.xpath("//*[@id='search_widget']/form/button/i")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='js-product-list']/div[1]/article/div/div[1]/h1/a")));
        driver.findElement(By.linkText(name)).click();
        Assert.assertEquals(driver.findElement(By.linkText(name)).getText(), name);
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id='main']/div/div/div/div/div/span"))
                .getText().contains(price));
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id='product-details']/div/span"))
                .getText().contains(qty));
    }


    /**
     * Waits until page loader disappears from the page
     */
    public void waitForContentLoad() {
        // TODO implement generic method to wait until page content is loaded

         wait.until(ExpectedConditions.presenceOfElementLocated(By.id("main")));
        // ...
    }
}
