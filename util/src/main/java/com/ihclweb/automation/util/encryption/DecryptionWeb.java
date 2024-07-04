package com.ihclweb.automation.util.encryption;

import java.util.Objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import com.techouts.ihclweb.webelement.ops.WebElementOperationsWeb;

public class DecryptionWeb {

	//private WebDriver driver = null;
	//private String strToDecrypt = "0acd13f56871a77ade1958faa9a9b07ea4ed0a0522be91597a98987de211d226";
	
	/*public DecryptionWeb(WebDriver driver) {
		this.driver = Objects.requireNonNull(driver,
				"WebDriver cannot be null to perform actions");}*/

	public static String decryptusingweb(String strToDecrypt) {
		try {
			WebDriver driver1 = new ChromeDriver();
			driver1.get("https://md5decrypt.net/en/Sha256/#answer");
			WebElementOperationsWeb.park(5);
			WebElement element = driver1.findElement(By.xpath("//textarea[@id='hash_input']"));
			element.sendKeys(strToDecrypt);
			driver1.findElement(By.xpath("//input[@name='decrypt']")).click();
			WebElementOperationsWeb.park(3);
			String decryptedOTP = driver1.findElement(By.cssSelector("fieldset[id='answer'] b")).getText();
			System.out.println(decryptedOTP);
			//return decryptedOTP;
		} catch (Exception e) 
		{
			System.out.println("Decrypting is not working as Expected");
		}
		return null;
	}
}
