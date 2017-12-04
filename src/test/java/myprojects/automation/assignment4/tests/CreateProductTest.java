package myprojects.automation.assignment4.tests;

import myprojects.automation.assignment4.BaseTest;
import myprojects.automation.assignment4.model.ProductData;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class CreateProductTest extends BaseTest {

    @DataProvider
    public Object[][] getLoginData() {
        return new String[][]{
                {"webinar.test@gmail.com", "Xcg7299bnSmMuRLp9ITw"}
        };
    }

    @Test(dataProvider = "getLoginData")
    public void login(String login, String password) {
        // TODO implement test for product creation
        actions.login(login, password);
    }
    @Test(dependsOnMethods = "login")
    public void createNP() {
        actions.createProduct(ProductData.generate());
    }

//     TODO implement logic to check product visibility on website

    @Test(dataProviderClass = ProductData.class)
    public void checkOnSite(String name, String qty, String price) {
        actions.checkOnSite(name, qty, price);

}
}
