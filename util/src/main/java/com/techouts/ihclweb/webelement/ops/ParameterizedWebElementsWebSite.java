package com.techouts.ihclweb.webelement.ops;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.ihclweb.automation.consts.IHCLConstantsWeb;
import com.ihclweb.automation.util.exceptions.IHCLBusinessException;

/**
 * 
 * @author rajeevsai.t
 *
 */
public class ParameterizedWebElementsWebSite {
	
	private static final Logger LOG = Logger.getLogger(ParameterizedWebElementsWebSite.class.getName());
	private static final String SPAN_CONTAINS = "(//span[contains(.,'";
	private static final String TR_TD_CONTAINS ="//tr[td[contains(text(),'";
	private static final String OCCURENCE1 ="')])[1]";
	private static List<WebElement> elements = null;

	private ParameterizedWebElementsWebSite(){}
	
	public static  WebElement l1Category(String categoryTitle, WebDriver driver) {
		WebElement element;
		try {
			LOG.info("Locating category element");
			element = driver
					.findElement(By.xpath("//a[@title='" + categoryTitle + "']"));
			LOG.info("l1Category element found");
			WebElementOperationsWeb.park(5);

		} catch (Exception e) {
			LOG.error("l1Category element not found");
			throw new IHCLBusinessException("l1Category element not found", e);
		}
		return element;
	}
	
	public static  WebElement l2Category(String categoryTitle,String subCategoryMainTitle, WebDriver driver) {
		WebElement element;
		try {
			LOG.info("Locating l2Category element");
			element = driver.findElement(By.xpath("//a[@title='" + categoryTitle + "']/following-sibling::div//span/a[text()='" + subCategoryMainTitle + "']"));
			LOG.info("l2Category element found");

		} catch (Exception e) {
			LOG.error("l2Category element not found");
			throw new IHCLBusinessException("l2Category element not found", e);
		}
		return element;
	}
	
	public static  WebElement refinementsCategory(WebDriver driver,String refinementCategory) {
		WebElement element;
		try {
			LOG.info("Locating refinementsCategory element");
			element = driver.findElement(By.xpath("//div[@id='product-facet']/div/div/div/div/div[contains(@class,'filter-category-heading')]/span[contains(.,'"+ refinementCategory +"')]"));
			LOG.info("refinementsCategory element found");
			WebElementOperationsWeb.park(3);

		} catch (Exception e) {
			LOG.error("refinementsCategory element not found");
			throw new IHCLBusinessException("refinementsCategory element not found", e);
		}
		return element;
	}
	
	public static  WebElement refinementsCategoryFilter(WebDriver driver,String refinementCategoryFilter) {
		WebElement element;
		try {
			LOG.info("Locating refinementsCategoryFilter element");
			element = driver.findElement(By.xpath("//div[@id='product-facet']/descendant::div[@class='tabs_item']/ul/li/descendant::label[contains(text(),'"+ refinementCategoryFilter +"')]/preceding-sibling::input[@class='checkbox-sel']"));
			LOG.info("refinementsCategoryFilter element found");
			WebElementOperationsWeb.park(3);

		} catch (Exception e) {
			LOG.error("refinementsCategoryFilter element not found");
			throw new IHCLBusinessException("refinementsCategoryFilter element not found", e);
		}
		return element;
	}
	
	public static  WebElement categoryFilter(WebDriver driver,String categoryFilter) {
		WebElement element;
		try {
			LOG.info("Locating CategoryFilter element");
			element = driver.findElement(By.xpath("//div[@class='ex-checkbox']/input[@id='"+ categoryFilter +"']"));
			LOG.info("CategoryFilter element found");
			WebElementOperationsWeb.park(3);

		} catch (Exception e) {
			LOG.error("categoryFilter element not found");
			throw new IHCLBusinessException("categoryFilter element not found", e);
		}
		return element;
	}
	
	public static  WebElement categoryFilterVerification(WebDriver driver,String refineBycategoryFilter) {
		WebElement element;
		try {
			LOG.info("Locating RefineBycategoryFilter element");
			element = driver.findElement(By.xpath("//div[@class='col-lg-11']/ul/li/label[contains(.,'"+ refineBycategoryFilter +"')]"));
			LOG.info("RefineBycategoryFilter element found");
			WebElementOperationsWeb.park(3);

		} catch (Exception e) {
			LOG.error("RefineBycategoryFilter element not found");
			throw new IHCLBusinessException("RefineBycategoryFilter element not found", e);
		}
		return element;
	}
	public static  WebElement sourcingProcessSelection(String value, WebDriver driver) {
		WebElement element;
		try {
			element = driver.findElement(By.xpath(TR_TD_CONTAINS+value+"')]]/td[1]"));
			WebElementOperationsWeb.park(2);

		} catch (Exception e) {
			throw new IHCLBusinessException("sourcingProcess element not found", e);
		}
		return element;
	}
	
	public static  WebElement supplierDiversityAnswerSelection(String value, WebDriver driver) {
		WebElement element;
		try {
			element = driver.findElement(By.xpath("//div[label[contains(text(),'"+value+"')]]/input[@type='checkbox']"));
			WebElementOperationsWeb.park(2);
		} catch (Exception e) {
			throw new IHCLBusinessException("supplierDiversityAnswerSelection checkbox not found", e);
		}
		return element;
	}
	public static  WebElement termsAndConditionAnswerSelection(String value, WebDriver driver) {
		WebElement element;
		try {
			element = driver.findElement(By.xpath("(//span[contains(text(),'"+value+"')])"));
			WebElementOperationsWeb.park(2);
		} catch (Exception e) {
			throw new IHCLBusinessException("termsAndConditionAnswerSelection value not found", e);
		}
		return element;
	}
	
	public static  WebElement ratingAnswerSelection(String value, WebDriver driver) {
		WebElement element;
		try {
			element = driver.findElement(By.xpath("//span[contains(text(),'"+value+"')]"));
			WebElementOperationsWeb.park(2);
		} catch (Exception e) {
			throw new IHCLBusinessException(" Rating AnswerSelection value not found", e);
		}
		return element;
	}
	
	public static WebElement beginTime(String value, WebDriver driver) {
		WebElement element;
		try {
			element = driver.findElement(By.xpath(TR_TD_CONTAINS + value + "')]]/td[9]"));
			WebElementOperationsWeb.park(2);
		} catch (Exception e) {
			throw new IHCLBusinessException(" Begin time for User Test", e);
		}
		return element;
	}

	public static WebElement userNotificationEmailTime(String value, WebDriver driver, int i) {
		WebElement element;
		try {
			element = driver.findElement(By.xpath(TR_TD_CONTAINS + value + "')]]/td[2])[" + i + "]"));
			WebElementOperationsWeb.park(2);
		} catch (Exception e) {
			throw new IHCLBusinessException("Suppliers " + i + "Notification and emails time is displayed", e);
		}
		return element;
	}
	
	public static  WebElement userEmails(String value, WebDriver driver, int i) {
		WebElement element;
		try {
			element = driver.findElement(By.xpath("(//tr[td[contains(text(),'"+value+"')]]/td[4])["+i+"]"));
			WebElementOperationsWeb.park(2);
		} catch (Exception e) {
			throw new IHCLBusinessException("Supplier "+i+ "email is displayed", e);
		}
		return element;
	}
	
	public static  WebElement workFlowPerformer(String value, WebDriver driver, int i) {
		WebElement element;
		try {
			element = driver.findElement(By.xpath("(//tr/td[contains(.,'"+value+"')])[10]"));
			WebElementOperationsWeb.park(2);
		} catch (Exception e) {
			throw new IHCLBusinessException("Supplier "+i+ "email is displayed", e);
		}
		return element;
	}
	
	public static WebElement amentMentNumber(String value, WebDriver driver) {
		WebElement element;
		try {
			element = driver.findElement(By.xpath(SPAN_CONTAINS + value + OCCURENCE1));
			WebElementOperationsWeb.park(3);
		} catch (Exception e) {
			LOG.error("Amendment element not found");
			throw new IHCLBusinessException("Amendment element not found", e);
		}
		return element;
		}

	
	public static WebElement srqProjectIDSelection(String value, WebDriver driver) {
		WebElement element;
		try {
			element = driver.findElement(By.xpath(TR_TD_CONTAINS+value+"')]]/td[1]"));
			WebElementOperationsWeb.park(2);
		} catch (Exception e) {
			throw new IHCLBusinessException("SRQ ProjectID element not found", e);
		}
		return element;
		}
	
	public static WebElement planId(String value, WebDriver driver) {
		WebElement element;
		try {
			LOG.info("Locating Plan Id element");
			element = driver.findElement(By.xpath(SPAN_CONTAINS + value + OCCURENCE1));
			LOG.info("Plan Id element found");
			WebElementOperationsWeb.park(3);

		} catch (Exception e) {
			LOG.error("Plan Id element not found");
			throw new IHCLBusinessException("Plan Id element not found", e);
		}
		return element;
	}
	
	public static WebElement srqProjectIDSelection2(String value, WebDriver driver) {
		WebElement element;
		try {
			element = driver.findElement(By.xpath("//tr[td[a[contains(text(),'" + value + "')]]]/td[1]"));
			WebElementOperationsWeb.park(2);
		} catch (Exception e) {
			throw new IHCLBusinessException("SRQ ProjectID element not found", e);
		}
		return element;
	}

	public static WebElement yearElement(String value, WebDriver driver) {
		WebElement element;
		try {
			element = driver.findElement(By.xpath("//li[@id='body_x_c__year_id_"+value+"'"+"]"));
			WebElementOperationsWeb.park(2);
		} catch (Exception e) {
			throw new IHCLBusinessException("Year element not found", e);
		}
		return element;
	}

	public static WebElement nextButton(int i, WebDriver driver) {
		WebElement element;
		try {
			element = driver.findElement(By.xpath("(//button[@aria-label='Next'])["+i+"]"));
			WebElementOperationsWeb.park(3);
			
		} catch (Exception e) {
			throw new IHCLBusinessException("Next button element not found", e);
		}
		return element;
	}
	
	public static WebElement previousButton(int i, WebDriver driver) {
		WebElement element;
		try {
			element = driver.findElement(By.xpath("(//button[@aria-label='Previous'])["+i+"]"));
			WebElementOperationsWeb.park(3);
			
		} catch (Exception e) {
			throw new IHCLBusinessException("Previous Button element not found", e);
		}
		return element;
	}
	
	public static WebElement createdCompaignLink(String value, WebDriver driver) {
		WebElement element;
		try {
			element = driver.findElement(By.xpath("//tr[td[button[@aria-label='"+value+" - Allegis Global Solutions, Inc.']]]/td[6]"));
			WebElementOperationsWeb.park(3);
			
		} catch (Exception e) {
			throw new IHCLBusinessException("Created Compaign element not found", e);
		}
		return element;
	}
	
	public static WebElement mainMenuBarText(WebDriver driver, int i)  {
		WebElement element;
		try {
			LOG.info("Locating MainMenuBarTitle elements");
			element = driver.findElement(By.xpath(".//nav/div[1]/div/ul/li["+i+"]/a"));
			WebElementOperationsWeb.webElementHighlighter(driver, element);
			LOG.info("Main Menu Bar text found on Home Page");

		} catch (Exception e) {
			LOG.error("Exception in Class Static_Page | Method MainMenuBarText");
			throw new IHCLBusinessException("Main Menu Bar text does not exist on Home Page", e);
		}
		return element;
	}
	
	public static  WebElement primaryFilterOpenAndClose(WebDriver driver,String primaryFilter) {
		WebElement element;
		try {
			LOG.info("Locating primaryFilterOpenAndClose element");
			element = driver.findElement(By.xpath("(//div[contains(@class,'filter-category-heading') and contains(text(),'"+ primaryFilter +"')])[2]"));
			LOG.info("primaryFilterOpenAndClose element found");
			WebElementOperationsWeb.park(3);

		} catch (Exception e) {
			LOG.error("primaryFilterOpenAndClose element not found");
			throw new IHCLBusinessException("primaryFilterOpenAndClose element not found", e);
		}
		return element;
	}
	
	public static void removeAllItemFromWishList(WebDriver driver, String testCaseName) {
		try {
			WebElement element = driver.findElement(By.xpath("//span[@class='count wish-count']"));
			if (WebElementOperationsWeb.isDisplayed(driver, element)) {
				LOG.info("Product available in Wishlist is " + element.getText());
				WebElementOperationsWeb.park(5);
				clickOnWishlist(driver, testCaseName);
				WebElementOperationsWeb.park(2);
				removingProductsFromWishlist(driver, testCaseName);
				WebElementOperationsWeb.waitForPageLoad(driver, 20);
			} else {
				LOG.info("Wishlist is empty ");
			}
		} catch (Exception e) {
			throw new IHCLBusinessException("Error occured while deleting products from Wishlist", e);
		}
	}
	
	public static void removingProductsFromWishlist(WebDriver driver, String testCaseName) {
		try {
			WebElement element = driver.findElement(By.xpath("(//h1[contains(text(),'Wishlist')])[2]"));
			if (WebElementOperationsWeb.isDisplayed(driver, element)) {
				clickOnRemoveButtonListInWishlist(driver);
				clickOnStartShopping(driver);
				WebElementOperationsWeb.waitForPageLoad(driver, 20);
			}
		} catch (Exception e) {
			WebElementOperationsWeb.park(4);
			clickOnWishlistType(driver, testCaseName);
			clickOnRemoveButtonListInWishlist(driver);
			clickOnStartShopping(driver);
		}
	}
	
	public static List<WebElement> wishlistRemoveButton(WebDriver driver) {
		try {
			elements = driver.findElements(By.xpath("//div[@class='remove-pro']"));
		} catch (Exception e) {
			throw new IHCLBusinessException("Error occured while identifying remove button in Wishlist", e);
		}
		return elements;
	}
	
	public static void clickOnRemoveButtonListInWishlist(WebDriver driver) {
        List<WebElement> list=wishlistRemoveButton(driver);
		try {
			int size=list.size();
			if (!list.isEmpty()){
				for (int i=1; i<=size;i++){
					WebElementOperationsWeb.click(driver, wishlistRemoveButton(driver).get(0));
					WebElementOperationsWeb.park(8);	
					LOG.info("Clicked On Remove button "+i);
					}
				}
				else{
					LOG.error("Unable to Remove products from Wishlist "+ list);
				}
		} catch (Exception e) {
			throw new IHCLBusinessException("Error occured while clicking On remove button in the Wishlist ");
		}
	}
	
	public static void productSelectSize(WebDriver driver) {
		try {
			List<WebElement> productSizeButtonList=driver.findElements(By.xpath("//*[@class='variant_size_ulClass']/li/button[@type='button']"));
			if (!productSizeButtonList.isEmpty()){
				for (int i=0; i<=productSizeButtonList.size()-1;i++){
					if (!(productSizeButtonList.get(i).getAttribute("class").contains("grey-background"))){
						WebElementOperationsWeb.executeJS(driver, IHCLConstantsWeb.JS_CLICK_SCRIPT,productSizeButtonList.get(i));
						LOG.info("Size selected "+i);
						WebElementOperationsWeb.park(5);
						break;
					}
				}
			}
			else{
				LOG.error("Unable to select size from becuse size is "+productSizeButtonList.size());
				}
		} catch (Exception e) {
			throw new IHCLBusinessException("Error occured while Selecting size",e);

		}
	}

	public static  WebElement selectingFilter(WebDriver driver,String filter) {
		WebElement element;
		try {
			LOG.info("Locating Filter element");
			element = driver.findElement(By.xpath("//input[@id='"+ filter +"']"));
			LOG.info("filter element found");
			WebElementOperationsWeb.park(3);

		} catch (Exception e) {
			LOG.error("filter element not found");
			throw new IHCLBusinessException("filter element not found", e);
		}
		return element;
	}
	
	public static List<WebElement> colorVariantButtonList(WebDriver driver) {
		
		try {
			LOG.info("Locating color_variant_buttonlist list element");
			elements = driver.findElements(By.xpath("//ul[@class='variant-list']//button"));
			WebElementOperationsWeb.webElementsHighlighter(driver, elements);
			LOG.info("Product color_variant_buttonlist  found on PDP");
		} catch (Exception e) {
			throw new IHCLBusinessException("Color Variant Buttons are not visible", e);
		}
		return elements;
	}
	
	public static List<WebElement> sizeVariantButtonList(WebDriver driver) {
		try {
			LOG.info("Locating size variant button list element");
			WebElementOperationsWeb.park(.3);
			elements = driver.findElements(By.xpath("//*[@class='variant_size_ulClass']/li/button[@type='button']"));
			WebElementOperationsWeb.webElementsHighlighter(driver, elements);
			LOG.info("Product size variant button list found on PDP ");
		} catch (Exception e) {
			throw new IHCLBusinessException("Size Variant Buttons are not visible", e);
		}
		return elements;
	}
	
	public static void removeAllFromCart(WebDriver driver, String testCaseName) {
		WebElement cartCount = driver.findElement(By.xpath("//span[@class='qtyHeaderMinicart count bag-count']"));
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, cartCount)) {
				LOG.info("Product available in count is "+ cartCount.getAttribute("value"));
				WebElementOperationsWeb.park(5);
				clickOnMyBag(driver, testCaseName);
				WebElementOperationsWeb.park(2);
				clickOnRemoveButtonList(driver);
				clickOnContinueShopping(driver);
				WebElementOperationsWeb.waitForPageLoad(driver, 20);
			} else {
				LOG.info("Cart is empty");
			}
		} catch (Exception e) {
			throw new IHCLBusinessException("Error occured while Removing products from Count",e);

		}
	}
	
	public static List<WebElement> removeButton(WebDriver driver) {
		try {
			elements=driver.findElements(By.xpath(".//*[@id='removeFromCartID']"));
		} catch (Exception e) {
			throw new IHCLBusinessException("Error occured while identifying remove button",e);
		}
		return elements;
	}
	
	public static void clickOnRemoveButtonList(WebDriver driver) {
        List<WebElement> list=removeButton(driver);
		try {
			int size=list.size();
			if (!list.isEmpty()){
				for (int i=1; i<=size;i++){
					WebElementOperationsWeb.click(driver, removeButton(driver).get(0));
					WebElementOperationsWeb.park(8);	
					LOG.info("Clicked On Remove button "+i);
					}
				}
				else{
					LOG.error("Unable to Remove products from cart "+ list );
				}
		} catch (Exception e) {
			throw new IHCLBusinessException("Error occured while clicking On remove button in the cart ");
		}
	}
	
	
	public static void clickOnMyBag(WebDriver driver,String testCaseName) {
		WebElement element = driver.findElement(By.xpath("//a[@class='minicart bags bag bag-container']"));
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, element)) {
				WebElementOperationsWeb.executeJS(driver, IHCLConstantsWeb.JS_CLICK_SCRIPT, element);
				WebElementOperationsWeb.captureScreenShotOnPass(driver, testCaseName, "ClickedOnMyBag");
				WebElementOperationsWeb.park(5);
			}
			else {
				throw new IHCLBusinessException("Unable to find the MyBag at the header ["+ element +"]");
			}
		} catch (Exception e) {
			WebElementOperationsWeb.captureScreenShotOnFail(driver, testCaseName, "NotClickedOnMyBag");
			throw new IHCLBusinessException("Error occured while clicking on MyBag at the header["+element+"] ",e);
		}
	}
	
	public static void clickOnEOSSWishlistType(WebDriver driver,String testCaseName) {
		WebElement element = driver.findElement(By.xpath("//a[contains(text(),'EOSS Wishlist')]"));
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, element)) {
				WebElementOperationsWeb.jsClick(driver, element);
				WebElementOperationsWeb.captureScreenShotOnPass(driver, testCaseName, "ClickedOnEOSSWishlistType");
				WebElementOperationsWeb.park(5);
			}
			else {
				throw new IHCLBusinessException("Unable to find the EOSS Wishlist Type in the wishlist page ["+ element +"]");
			}
		} catch (Exception e) {
			WebElementOperationsWeb.captureScreenShotOnFail(driver, testCaseName, "NotClickedOnEOSSWishlist");
			throw new IHCLBusinessException("Error occured while clicking on EOSS Wishlist ["+element+"] ",e);
		}
	}
	
	public static void clickOnWishlist(WebDriver driver,String testCaseName) {
		WebElement element = driver.findElement(By.xpath("//a[@class='wishlist']"));
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, element)) {
				WebElementOperationsWeb.jsClick(driver, element);
				WebElementOperationsWeb.captureScreenShotOnPass(driver, testCaseName, "ClickedOnWishlist");
				WebElementOperationsWeb.park(5);
			}
			else {
				throw new IHCLBusinessException("Unable to find the Wishlist Symbol in the header ["+ element +"]");
			}
		} catch (Exception e) {
			WebElementOperationsWeb.captureScreenShotOnFail(driver, testCaseName, "NotClickedOnWishlist");
			throw new IHCLBusinessException("Error occured while clicking on Wishlist in the header["+element+"] ",e);
		}
	}
	
	public static void clickOnWishlistType(WebDriver driver,String testCaseName) {
		WebElement element = driver.findElement(By.xpath("//li[@class='nav-item'][2]"));
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, element)) {
				WebElementOperationsWeb.click(driver, element);
				WebElementOperationsWeb.captureScreenShotOnPass(driver, testCaseName, "ClickedOnWishlistType");
				WebElementOperationsWeb.park(5);
			}
			else {
				throw new IHCLBusinessException("Unable to find the Wishlist Type in the wishlist page ["+ element +"]");
			}
		} catch (Exception e) {
			WebElementOperationsWeb.captureScreenShotOnFail(driver, testCaseName, "NotClickedOnWishlistType");
			throw new IHCLBusinessException("Error occured while clicking on Wishlist type["+element+"] ",e);
		}
	}

	public static void clickOnStartShopping(WebDriver driver) {
		WebElement startShopping = driver.findElement(By.xpath("//button[@class='btn btn-default shopbtns']"));
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, startShopping)) {
				WebElementOperationsWeb.executeJS(driver, IHCLConstantsWeb.JS_CLICK_SCRIPT, startShopping);
				WebElementOperationsWeb.park(5);
			}
			else {
				throw new IHCLBusinessException("Unable to find the Start shopping ["+ startShopping +"]");
			}
		} catch (Exception e) {
			throw new IHCLBusinessException("Error occured while clicking on Start shopping ["+startShopping+"] ",e);
		}
	}
	
	public static void clickOnContinueShopping(WebDriver driver) {
		WebElement continueShopping =driver.findElement(By.xpath("//a[@class='btn-white']"));
		try {
			if (WebElementOperationsWeb.isDisplayed(driver, continueShopping)) {
				WebElementOperationsWeb.executeJS(driver, IHCLConstantsWeb.JS_CLICK_SCRIPT, continueShopping);
				WebElementOperationsWeb.park(5);
			}
			else {
				throw new IHCLBusinessException("Unable to find the ContinueShoppingButton In cart["+ continueShopping +"]");
			}
		} catch (Exception e) {
			throw new IHCLBusinessException("Error occured while clicking on ContinueShoppingButton in the Cart["+continueShopping+"] ",e);
		}
	}
	
	public static List<WebElement> productImage(WebDriver driver) {
		List<WebElement> elements = null;
		try {
			elements = driver.findElements(By.xpath("//div[@class='pro-img']//a[2]"));
			WebElementOperationsWeb.webElementsHighlighter(driver, elements);
			LOG.info("Product Image elements exist on the cart page");

		} catch (Exception e) {
			LOG.error("Product Image elements does not exist on the cart page");
			throw new IHCLBusinessException("Error occured while checking product Images in the cart", e);
		}
		return elements;
	}
	
	public static List<WebElement> productBrandName(WebDriver driver) {
		List<WebElement> elements = null;
		try {
			elements = driver.findElements(By.xpath("//div[@class='mat-name']/a"));
			WebElementOperationsWeb.webElementsHighlighter(driver, elements);
			LOG.info("Product Brand Name exist on the cart page");

		} catch (Exception e) {
			LOG.error("Product Brand Name does not exist on the cart page");
			throw new IHCLBusinessException("Error occured while validating Brand names in the cart", e);
		}
		return elements;
	}
	
	public static List<WebElement> productName(WebDriver driver) {
		List<WebElement> elements = null;
		try {
			elements = driver.findElements(By.xpath("//div[@class='pro-name']/a"));
			WebElementOperationsWeb.webElementsHighlighter(driver, elements);
			LOG.info("Product Name exist on the cart page");

		} catch (Exception e) {
			LOG.error("Product Name does not exist on the cart page");
			throw new IHCLBusinessException("Error occured while validating product Names in the cart page", e);
		}
		return elements;
	}
	
	public static List<WebElement> cartInfo(WebDriver driver) {
		List<WebElement> elements = null;
		try {
			elements = driver.findElements(By.xpath("//form[@id='updateCartForm0']//li"));
			WebElementOperationsWeb.webElementsHighlighter(driver, elements);
			LOG.info("Cart Product info elements exist on the cart page");

		} catch (Exception e) {
			LOG.error("Cart Product info elements does not exist on the cart page");
			throw new IHCLBusinessException("Error occured while validating cart Info", e);
		}
		return elements;
	}

	public static WebElement selectNetBanking(String bankName, WebDriver driver) {
		WebElement element;
		try {
			LOG.info("Locating netbanking element");
			element = driver.findElement(By.xpath("//label[contains(.,'" + bankName + "')]"));
			LOG.info("netbanking element found");
			WebElementOperationsWeb.park(3);
		} catch (Exception e) {
			LOG.error("netbanking element not found");
			throw new IHCLBusinessException("netbanking element not found", e);
		}
		return element;
	}
	
	public static WebElement selectWallet(String walletName, WebDriver driver) {
		WebElement element;
		try {
			LOG.info("Locating Wallet element");
			element = driver.findElement(By.xpath("(//input[@id='"+ walletName +"'])[1]"));
			LOG.info("Wallet element found");
			WebElementOperationsWeb.park(3);
			WebElementOperationsWeb.executeJS(driver, IHCLConstantsWeb.JS_CLICK_SCRIPT, element);

		} catch (Exception e) {
			LOG.error("Wallet element not found");
			throw new IHCLBusinessException("Wallet element not found", e);
		}
		return element;
	}
	public static List<WebElement> MaroonText( WebDriver driver) throws Exception {
		try {
			elements = driver.findElements(By.xpath("//*[@class='results_list']//li//a"));
			WebElementOperationsWeb.webElementsHighlighter(driver, elements);

		} catch (Exception e) {
			LOG.error("Maroon is not found on the home page");
			throw new IHCLBusinessException("Maroon is not found on the home page", e);
		}
		return elements;
	}
	
	public static WebElement searchCategory(String searchTerm, WebDriver driver) {
		WebElement element;
		try {
			element = driver.findElement(By.xpath("//h2[contains(.,'"+searchTerm+"')]"));
			WebElementOperationsWeb.park(3);

		} catch (Exception e) {
			throw new IHCLBusinessException("searchCategory element not found", e);
		}
		return element;
	}
	
	public static WebElement searchAutoSuggestion(String searchTerm, WebDriver driver) {
		WebElement element;
		try {
			element = driver.findElement(By.xpath("(//span[contains(.,'"+searchTerm+"')])[2]"));
			WebElementOperationsWeb.park(3);
		} catch (Exception e) {
			throw new IHCLBusinessException("multipleSearchTerm Autosuggestion not found", e);
		}
		return element;
	}
}