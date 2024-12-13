package demo.wrappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.NumberFormat;
import java.time.Duration;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */

    public static void searchProduct(WebDriver driver, By locator, String product) {
        System.out.println("sending keys");
        try {
            // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            // wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

            WebElement inputBox = driver.findElement(locator);
            inputBox.click();
            inputBox.clear();
            inputBox.sendKeys(product);
            inputBox.sendKeys(Keys.ENTER);

        } catch (Exception e) {
            System.out.println("Exception occurred!" + e.getMessage());

        }

    }

    public static void clickElement(WebDriver driver, By locator) {
        System.out.println("Clicking on element");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(locator));

            WebElement clickableElement = driver.findElement(locator);
            clickableElement.click();

        } catch (Exception e) {
            System.out.println("Exception occurred!" + e.getMessage());

        }
    }

    public static Boolean searchStarRatingAndPrintCount(WebDriver driver, By locator, double starRating) {

        int washingMachineCount = 0;
        Boolean success = false;
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            List<WebElement> starRatingElements = driver.findElements(locator);
            for (WebElement rating : starRatingElements) {
                if (Double.parseDouble(rating.getText()) <= starRating) {
                    washingMachineCount++;
                }
                System.out.println("Count of washing machines with rating less than or equal to " + starRating + ":"
                        + washingMachineCount);
                success = true;
            }

        } catch (Exception e) {
            System.out.println("Exception occurred!" + e.getMessage());
            e.printStackTrace();
            success = false;
        }

        return success;
    }

    public static Boolean printTitleAndDiscount(WebDriver driver, By locator, int discount) {
        Boolean success = false;
        try {
            Thread.sleep(3000);
            List<WebElement> productList = driver.findElements(locator);
            HashMap<String, String> TitlediscountMap = new HashMap<>();
            for (WebElement product : productList) {
                String discountPercent = product.findElement(By.xpath("//div[@class= 'UkUFwK']/span")).getText();
                Thread.sleep(3000);
                System.out.println("Printing discount percent " + discountPercent);
                int discountValue = Integer.parseInt(discountPercent.replaceAll("[^\\d]", ""));
                if (discountValue > discount) {
                    String title = product.findElement(By.xpath(".//div[@class = 'KzDlHZ']")).getText();
                    TitlediscountMap.put(discountPercent, title);
                    System.out.println("Discount map " + TitlediscountMap);

                }
            }

            System.out.println("Checking success status first time " + success);

            for (Map.Entry<String, String> Titlediscounts : TitlediscountMap.entrySet()) {
                System.out.println("Iphone discount percentage is:" + Titlediscounts.getKey() + "and its title is:"
                        + Titlediscounts.getValue());

            }
            success = true;
            System.out.println("Checking success status second time " + success);

        } catch (Exception e) {
            System.out.println("Exception occurred!");
            e.printStackTrace();
            success = false;

        }

        return success;

    }

    public static Boolean printTitleAndImageUrl(WebDriver driver, By locator) {

        Boolean success;
        try {
            List<WebElement> userReviewElements = driver.findElements(locator);
            Set<Integer> userReviewSet = new HashSet<>();
            for (WebElement userReview : userReviewElements) {
                int userReviews = Integer.parseInt(userReview.getText().replaceAll("[^\\d]", ""));
                userReviewSet.add(userReviews);
            }

            List<Integer> userReviewCountList = new ArrayList<>(userReviewSet);
            Collections.sort(userReviewCountList, Collections.reverseOrder());
            System.out.println(userReviewCountList);
            NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
            LinkedHashMap<String, String> productDetailsMap = new LinkedHashMap<>();
            for (int i = 0; i < 5; i++) {
                String formattedUserReviewCount = "(" + numberFormat.format(userReviewCountList.get(i)) + ")";
                String productTitle = driver.findElement(By.xpath("//div[@class = 'slAVV4']//span[contains(text() , '"
                        + formattedUserReviewCount + "')]/../../a[@class = 'wjcEIp']")).getText();
                String productImageUrl = driver
                        .findElement(By.xpath("//div[@class = 'slAVV4']//span[contains(text() , '"
                                + formattedUserReviewCount + "')]/../..//img[@class = 'DByuf4']"))
                        .getAttribute("src");
                String highestReviewCountAndProductTitle = String.valueOf(
                        (i + 1) + "highest review count" + formattedUserReviewCount + "Title is" + productTitle);
                productDetailsMap.put(highestReviewCountAndProductTitle, productImageUrl);
            }

            // print title and image url of coffee mug
            for (Map.Entry<String, String> productDetails : productDetailsMap.entrySet()) {

                System.out.println(productDetails.getKey() + "and product image url is" + productDetails.getValue());
            }
            success = true;

        } catch (Exception e) {
            System.out.println("Exception occurred!");
            e.printStackTrace();
            success = false;

        }

        return success;
    }

}