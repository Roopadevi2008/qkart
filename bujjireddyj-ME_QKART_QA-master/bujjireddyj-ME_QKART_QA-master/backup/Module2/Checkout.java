package QKART_SANITY_LOGIN.Module1;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Checkout {
    RemoteWebDriver driver;
    String url = "https://crio-qkart-frontend-qa.vercel.app/checkout";

    public Checkout(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public void navigateToCheckout() {
        if (!this.driver.getCurrentUrl().equals(this.url)) {
            this.driver.get(this.url);
        }
    }

    /*
     * Return Boolean denoting the status of adding a new address
     */
    public Boolean addNewAddress(String addresString) {
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 05: MILESTONE 4
            /*
             * Click on the "Add new address" button, enter the addressString in the address
             * text box and click on the "ADD" button to save the address
             */
            WebElement addnewButton =
                    driver.findElement(By.id("add-new-btn"));
            addnewButton.click();
            WebElement addressTextbox = driver.findElement(
                    By.className("MuiOutlinedInput-input"));
                            addressTextbox.sendKeys(addresString);
            WebElement clickonAdd =
                    driver.findElement(By.className("MuiButton-contained"));
            clickonAdd.click();

                    return true;
        } catch (Exception e) {
            System.out.println("Exception occurred while entering address: " + e.getMessage());
            return false;

        }
    }

    /*
     * Return Boolean denoting the status of selecting an available address
     */
    public Boolean selectAddress(String addressToSelect) {
        //boolean status = false;
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 05: MILESTONE 4
            /*
             * Iterate through all the address boxes to find the address box with matching
             * text, addressToSelect and click on it
             */
            List<WebElement> addresses =
                    driver.findElements(By.xpath("//div[contains(@class,'address-item')]//p"));
            for (WebElement address : addresses) {
                if (address.getText().equals(addressToSelect))
                    address.click();
                break;
            }
                
                    //System.out.println("Unable to find the given address");
         return true;
        
        } catch (Exception e) {
            System.out.println("Exception Occurred while selecting the given address: " + e.getMessage());
            return false;
        }

    }

    /*
     * Return Boolean denoting the status of place order action
     */
    public Boolean placeOrder() {
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 05: MILESTONE 4
            // Find the "PLACE ORDER" button and click on it
            WebElement placeOrder=driver.findElement(By.xpath("//button[contains(@type,'button')][2]"));
            placeOrder.click();
               
            return true;

        } catch (Exception e) {
            System.out.println("Exception while clicking on PLACE ORDER: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting if the insufficient balance message is displayed
     */
    public Boolean verifyInsufficientBalanceMessage() {
        boolean status=false;
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 08: MILESTONE 7
            WebElement placeOderButton = driver.findElement(By.xpath(" //button[contains(@type,'button')][2]"));
                  placeOderButton.click();
            WebElement insufficiantBalanceMessage = driver.findElement(By.xpath("//div[contains(@id,'notistack-snackbar')][1]"));
            if (insufficiantBalanceMessage.getText().trim().equalsIgnoreCase(
                    "You do not have enough balance in your wallet for this purchase")) {
                status = true;
            
            }
            return status;       
            
        } catch (Exception e) {
            System.out.println("Exception while verifying insufficient balance message: " + e.getMessage());
            return false;
        }
    }
}
