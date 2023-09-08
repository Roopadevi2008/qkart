package QKART_SANITY_LOGIN.Module1;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Home {
    RemoteWebDriver driver;
    String url = "https://crio-qkart-frontend-qa.vercel.app";

    public Home(RemoteWebDriver driver) {
        this.driver = driver;
    }

    public void navigateToHome() {
        if (!this.driver.getCurrentUrl().equals(this.url)) {
            this.driver.get(this.url);
        }
    }

    public Boolean PerformLogout() throws InterruptedException {
        try {
            // Find and click on the Logout Button
            WebElement logout_button = driver.findElement(By.className("MuiButton-text"));
            logout_button.click();

            // SLEEP_STMT_10: Wait for Logout to complete
            // Wait for Logout to Complete
            //Thread.sleep(3000);

            return true;
        } catch (Exception e) {
            // Error while logout
            return false;
        }
    }

    /*
     * Returns Boolean if searching for the given product name occurs without any
     * errors
     */
    public Boolean searchForProduct(String product) {
        WebElement searchBoxElement;
        try {
            
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 03: MILESTONE 1
            // Clear the contents of the search box and Enter the product name in the search
           searchBoxElement = driver.findElement(By.xpath("(//input[@name='search'])[1]"));
            searchBoxElement.clear();
            searchBoxElement.sendKeys(product);
           // searchBoxElement.clear();
           // Thread.sleep(3000);
        //    WebDriverWait wait = new WebDriverWait(driver,30);
        //     wait.until(ExpectedConditions.or(ExpectedConditions.textToBePresentInElementLocated(By.className("css-yg30ev6"), product),
        //     ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"root\"]/div/div[3]/div[1]/div[2]/div/h4"))));
            Thread.sleep(3000);
            return true;
        } catch (Exception e) {
            System.out.println("Error while searching for a product: " + e.getMessage());
            return false;
        }
    }

    /*
     * Returns Array of Web Elements that are search results and return the same
     */
    public List<WebElement> getSearchResults() {
        List<WebElement> searchResults = new ArrayList<WebElement>() {
        };
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 03: MILESTONE 1
            // Find all webelements corresponding to the card content section of each of
            // search results
            searchResults = driver.findElements(By.className("MuiCardContent-root"));
            return searchResults;
        } catch (Exception e) {
            System.out.println("There were no search results: " + e.getMessage());
            return searchResults;

        }
    }

    /*
     * Returns Boolean based on if the "No products found" text is displayed
     */
    public Boolean isNoResultFound() {
        Boolean status = false;
        //WebElement noProductFound;
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 03: MILESTONE 1
            // Check the presence of "No products found" text in the web page. Assign status
            // = true if the element is *displayed* else set status = false
            WebElement noProductFound = driver.findElement(By.tagName("h4"));
            if (noProductFound.getText().equalsIgnoreCase("No products found")) {
            status = true;
          } else {
            status = false;
             }
            
            return status;
        } catch (Exception e) {
            return status;
        }
    }

    /*
     * Return Boolean if add product to cart is successful
     */
    public Boolean addProductToCart(String productName) {
        try {
            /*
             * Iterate through each product on the page to find the WebElement corresponding
             * to the matching productName
             * 
             * Click on the "ADD TO CART" button for that element
             * 
             * Return true if these operations succeeds
             */
            WebElement addingToCart =
                    driver.findElement(By.cssSelector(".MuiCardActions-root>button"));
            List<WebElement> searchedProducts = driver.findElements(By.className("MuiCardContent-root"));   
            for (WebElement searchedProduct : searchedProducts) {
                WebElement searchProductName = searchedProduct.findElement(By.tagName("p"));
                if (searchProductName.getText().equalsIgnoreCase(productName)) {

                    addingToCart.click();
                    break;
                }


            }
            return true;            // WebElement addingToCart = driver.findElement(By.cssSelector(".MuiCardActions-root>button"));
    // System.out.println("Unable to find the given product");
           // return false;
        } catch (Exception e) {
            System.out.println("Exception while performing add to cart: " + e.getMessage());
            return false;
        }
    }

    /*
     * Return Boolean denoting the status of clicking on the checkout button
     */
    public Boolean clickCheckout() {
        Boolean status = false;
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 05: MILESTONE 4
            // Find and click on the the Checkout button
            WebElement clickCheckout = driver.findElement(By.cssSelector(".cart-footer>button"));
            clickCheckout.click();
            status = true;
            return status;
        } catch (Exception e) {
            System.out.println("Exception while clicking on Checkout: " + e.getMessage());
            return status;
        }
    }

    /*
     * Return Boolean denoting the status of change quantity of product in cart
     * operation
     */
    public Boolean changeProductQuantityinCart(String productName, int quantity) {
        Boolean status = false;
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 06: MILESTONE 5
            WebElement parentCartElement =
                    driver.findElement(By.xpath("//div[contains(@class,'cart MuiBox-root')]"));
            List<WebElement> cartElements = parentCartElement
                    .findElements(By.xpath("//div[@class='MuiBox-root css-1gjj37g']"));
            for (WebElement cartElement : cartElements) {
                WebElement cartProductName = cartElement.findElement(By.tagName("div"));
                if (cartProductName.getText().equals(productName)) {
                    WebElement currentProductQuantity =
                            cartElement.findElement(By.xpath("//div[contains(text(),'" + productName
                                    + "')]/following-sibling::div//div[@data-testid='item-qty']"));
                    int currentQuantity = Integer.valueOf(currentProductQuantity.getText());
                    WebElement increaseQuantityButton =
                            cartElement.findElement(By.xpath("//div[contains(text(),'" + productName
                                    + "')]/following-sibling::div//button[2]"));
                    WebElement decreaseQuantityButton =
                            cartElement.findElement(By.xpath("//div[contains(text(),'" + productName
                                    + "')]/following-sibling::div//button[1]"));
                    while (currentQuantity != quantity) {
                        if (currentQuantity < quantity) {
                            increaseQuantityButton.click();
                            Thread.sleep(1000);
                            currentQuantity = Integer.valueOf(currentProductQuantity.getText());
                            status = true;
                        } else if (currentQuantity > quantity) {
                            decreaseQuantityButton.click();
                            Thread.sleep(1000);
                            if (currentQuantity > 1) {
                                currentQuantity = Integer.valueOf(currentProductQuantity.getText());
                            } else if (currentQuantity == 1) {
                                currentQuantity = currentQuantity - 1;
                            }
                            status = true;
                        } else {
                            status = false;
                        }
                    }
                }
            }
            return status;
        } catch (Exception e) {
            if (quantity == 0)
                return true;
            System.out.println("exception occurred when updating cart: " + e.getMessage());
            return false;
        }
    }
    /*
     * Return Boolean denoting if the cart contains items as expected
     */
    public Boolean verifyCartContents(List<String> expectedCartContents) {
        Boolean status = false;
        try {
            // TODO: CRIO_TASK_MODULE_TEST_AUTOMATION - TEST CASE 07: MILESTONE 6

            // Get all the cart items as an array of webelements

            // Iterate through expectedCartContents and check if item with matching product
            // name is present in the cart
            List<WebElement> cartElements = driver.findElements(By.xpath(
                    "//div [contains(@class,'cart MuiBox-root')]//div[@class='MuiBox-root css-1gjj37g']/div[1]"));
                          
            for (WebElement cartElement : cartElements) {
                for (String expectedCartContent : expectedCartContents) {
                    if (cartElement.getText().equalsIgnoreCase(expectedCartContent)) {
                        status = true;
                        break;
                    }
                }
            }
                
            return status;

        } catch (Exception e) {
            System.out.println("Exception while verifying cart contents: " + e.getMessage());
            return false;
        }
    }
}
