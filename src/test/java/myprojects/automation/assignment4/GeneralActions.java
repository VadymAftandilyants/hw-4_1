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

    private By catalog = By.xpath("//*[@id='subtab-AdminCatalog']/a/span");
    private By product = By.xpath("//*[@id='subtab-AdminProducts']/a");
    private By addProductBtn = By.xpath("//*[@id='page-header-desc-configuration-add']/span");
    private By productNameForm = By.xpath("//*[@id='form_step1_name_1']");
    private By productQtyForm = By.xpath("//*[@id='form_step1_qty_0_shortcut']");
    private By productPriceForm = By.xpath("//*[@id='form_step1_price_shortcut']");
    private By saveBtn = By.xpath("//*[@id='form']/div[4]/div[2]/div/button[1]/span");
    private By allProducts = By.xpath("//*[@id='content']/section/a");
    private By searchProductForm = By.xpath("//*[@id='search_widget']/form/input[2]");
    private By searchProductBtn = By.xpath("//*[@id='search_widget']/form/button/i");
    private By currentPrice = By.xpath("//*[@class='current-price']/span");
    private By productDetails = By.xpath("//*[@id='product-details']/div/span");


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
        try {
            driver = new EventFiringWebDriver(driver);
            driver.get(Properties.getBaseAdminUrl());
            driver.findElement(By.id("email")).sendKeys(login);
            driver.findElement(By.id("passwd")).sendKeys(password);
            driver.findElement(By.name("submitLogin")).click();
        } catch (Exception e) {
        throw new UnsupportedOperationException();
        }
    }

    public void createProduct(ProductData newProduct) {
        // TODO implement product creation scenario
        try {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(catalog));

        Actions act = new Actions(driver);
        WebElement element = driver.findElement(catalog);
        act.moveToElement(element).perform();
        wait.until(ExpectedConditions.visibilityOfElementLocated(product));
        driver.findElement(product).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(addProductBtn));
        driver.findElement(addProductBtn).click();

        String newName = newProduct.getName();
        driver.findElement(productNameForm).sendKeys(newName);

        String newQty = newProduct.getQty().toString();
        driver.findElement(productQtyForm).sendKeys(newQty);

        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("window.scrollBy(0,200)");

        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].value = '';", driver.findElement(productPriceForm));

        String newPrice = String.valueOf(newProduct.getPrice());
        driver.findElement(productPriceForm).sendKeys(newPrice);

        driver.findElement(By.className("switch-input")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("growl-message")));
        Assert.assertEquals(driver.findElement(By.className("growl-message")).getText(), "Настройки обновлены.");
        driver.findElement(By.className("growl-close")).click();

        driver.findElement(saveBtn).click();
    /**
     *   При проверке часто тест падает, несмотря на то, что есть явное ожидание всплывающего сообщения, такого же как
     *   и при активации продукта
     */
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("growl-message")));
//        Assert.assertEquals(driver.findElement(By.className("growl-message")).getText(), "Настройки обновлены.");
//        driver.findElement(By.className("growl-close")).click();
//            WebElement popUpClose = driver.findElement(By.className("growl-close"));
//            JavascriptExecutor executor = (JavascriptExecutor) driver;
//            executor.executeScript("arguments[0].click()", popUpClose);

        driver.get("http://prestashop-automation.qatestlab.com.ua/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(allProducts));
        driver.findElement(allProducts).click();

        driver.findElement(searchProductForm).sendKeys(newName);
        driver.findElement(searchProductBtn).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(newName)));
        driver.findElement(By.linkText(newName)).click();
        Assert.assertEquals(driver.findElement(By.linkText(newName)).getText(), newName);
        Assert.assertTrue(driver.findElement(currentPrice).getText().contains(newPrice));
        Assert.assertTrue(driver.findElement(productDetails).getText().contains(newQty));

        } catch (Exception e) {
            throw new UnsupportedOperationException();
        }

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
