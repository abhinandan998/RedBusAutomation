package org.Abhinandan_Project;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class RedBus_Automation {

    public static void main(String[] args) throws InterruptedException {


        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.redbus.in/");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        By sourceLocator = By.xpath("(//div[contains(@class,'srcDestWrapper')])[1]");
        WebElement source = wait.until(ExpectedConditions.elementToBeClickable(sourceLocator));
        source.click();
        By searchSuggestionBox = By.xpath("//div[contains(@class,'searchSuggestionWrapper')]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchSuggestionBox));

        selectLocation(driver, wait, "Mumbai");
        selectToLocation(driver, wait, "Pune");

        By SearchButtonLocator = By.xpath("//button[contains(@class,'primaryButton')]");
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(SearchButtonLocator));
        searchButton.click();


        By PrimoButtonLocator = By.xpath("//div[contains(text(),'Primo Bus')]");
        WebElement primoButton = wait.until(ExpectedConditions.elementToBeClickable(PrimoButtonLocator));
        primoButton.click();
        Thread.sleep(4000);

        By ACBusButtonLocator = By.xpath("(//div[contains(text(),'AC')])[1]");
        WebElement ACBusButton = wait.until(ExpectedConditions.elementToBeClickable(ACBusButtonLocator));
        ACBusButton.click();


        By subTitleLocator = By.xpath("//div[contains(@class,'busesFoundText')]");

        WebElement subTitle = null;
        if (wait.until(ExpectedConditions.textToBePresentInElementLocated(subTitleLocator, "buses"))) {
            subTitle = wait.until(ExpectedConditions.elementToBeClickable(subTitleLocator));
        }
        System.out.println(subTitle.getText());

        By tuppleWrapperLocator = By.xpath("//li[contains(@class,'tupleWrapper')]");
        By BusesNameLocator = By.xpath(".//div[contains(@class,'travelsName')]");

        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tuppleWrapperLocator));
        //scroll
        JavascriptExecutor js = (JavascriptExecutor)driver;




        while (true)//lazy loading
        {
            List<WebElement> rowList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tuppleWrapperLocator));
            List<WebElement> endOfList = driver.findElements(By.xpath("//span[contains(text(),'End of list')]"));

            if(!(endOfList.isEmpty()))
            {
                break; //exit condition
            }
            js.executeScript("arguments[0].scrollIntoView({behavior:'smooth'})", rowList.get(rowList.size()-3));
        }
        List<WebElement> rowList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tuppleWrapperLocator));
        for(WebElement row: rowList)
        {
            String busName = row.findElement(BusesNameLocator).getText();
            System.out.println(busName);
        }
        System.out.println("Total Number of Buses Loaded: "+rowList.size());

        }



    public static void selectToLocation(WebDriver driver, WebDriverWait wait, String location) {

        WebElement toTectBox = driver.switchTo().activeElement();
        toTectBox.sendKeys(location);
        By tosearchCategoryLocator = By.xpath("//div[contains(@class,'searchCategory')]");
        List<WebElement> tosearchList = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(tosearchCategoryLocator, 1));

        WebElement toLocation = tosearchList.get(0);
        By toLocationNameLocator = By.xpath(".//div[contains(@class,'listHeader')]");
        List<WebElement> toLocationList =  toLocation.findElements(toLocationNameLocator);
        for(WebElement toLoc: toLocationList)
        {
            if(toLoc.getText().equalsIgnoreCase(location))
            {
                toLoc.click();
                break;
            }
        }

    }

    public static void selectLocation(WebDriver driver, WebDriverWait wait, String location)
    {
        WebElement searchTextBoxElement = driver.switchTo().activeElement();
        searchTextBoxElement.sendKeys(location);

        By searchCategoryLocator = By.xpath("//div[contains(@class,'searchCategory')]");
        List<WebElement> searchList = wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(searchCategoryLocator, 1));
        System.out.println(searchList.size());

        WebElement searchResult = searchList.get(0);

        //chaining of web element
        By locationNameLocator = By.xpath(".//div[contains(@class,'listHeader')]");
        List<WebElement> locationList =  searchResult.findElements(locationNameLocator);

        for(WebElement loc: locationList)
        {
            String lName = loc.getText();

            if(lName.equalsIgnoreCase(location))
            {
                loc.click();
                break;
            }
        }

    }
}
