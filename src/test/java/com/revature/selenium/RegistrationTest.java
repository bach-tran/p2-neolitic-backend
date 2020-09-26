package com.revature.selenium;

import java.util.concurrent.TimeUnit;

import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class RegistrationTest {
	
	@Test
	public void registrationTest() {
		String os = System.getProperty("os.name").toLowerCase();
		
		if (os.indexOf("win") >= 0)
		{
			System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
		} else if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0 || os.indexOf("aix") > 0) {
			System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver_linux");
		}
		else {
			System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
		}
		

		WebDriver driver=new ChromeDriver();
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("http://ec2-18-220-126-199.us-east-2.compute.amazonaws.com:8085/login");
		//driver.get("http://localhost:4200/login");
		
		try {
			
			WebElement signUpButton = driver.findElement(By.id("signup"));
			signUpButton.click();
			WebElement fname = driver.findElement(By.id("fname"));
			WebElement lname = driver.findElement(By.id("lname"));
			WebElement username = driver.findElement(By.id("username"));
			WebElement password = driver.findElement(By.id("password"));
			WebElement cpassword = driver.findElement(By.id("cpassword"));
			WebElement submitButton = driver.findElement(By.id("submit"));

			fname.sendKeys("Selenium");
			lname.sendKeys("Test");
			username.sendKeys("stest");
			password.sendKeys("1234");
			cpassword.sendKeys("1234");
			submitButton.click();
			
			
		}
		catch (Exception e)
		{
			e.getStackTrace();
		}
		
		driver.close();

		}

}
