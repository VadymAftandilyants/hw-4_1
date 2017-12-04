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
        log("Logging into admin panel");
        actions.login(login, password);
    }

//     TODO implement logic to check product visibility on website

    @Test(dependsOnMethods = "login")
    public void createNP() {
        log("Creating new product");
        actions.createProduct(ProductData.generate());
    }

}

