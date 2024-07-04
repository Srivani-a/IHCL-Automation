package com.ihclweb.automation.util;

/**
 * 
 * @author rajeevsai.t
 *
 */
public final class Sheet {

	private Sheet() {}
	
	public static final class Credentials {

		public static final int ID = 0;
		public static final int TYPE = 1;
		public static final int NAME = 2;
		public static final int USERNAME = 3;
		public static final int PASSWORD = 4;
		
		private Credentials() {}
	}
	
	
	public static final class HomePage {

		public static final int SERIAL_NO = 0;
		public static final int TESTCASE_NAME = 1;
		public static final int PAGE_TITLE = 2;
		public static final int CITY = 3;
		public static final int STORE = 4;
		public static final int ORDER = 5;
		public static final int TRACKORDER_PAGE_TITLE = 6;
		public static final int STORELOCATOR_PAGE_TITLE = 7;
		public static final int CONTACTUS_PAGE_TITLE = 8;
		public static final int CART_PAGE_TITLE = 9;
		public static final int SEARCH_TERM = 10;
		public static final int DATEOF_BIRTH = 11;
		public static final int PRODUCT_CATEGORY = 12;
		
		private HomePage() {}
	}
	
	public static final class MyAccount{
		
		public static final int SERIAL_NO = 0;
		public static final int TEST_CASE_NAME = 1;
		public static final int GIFT_TYPE = 2;
		public static final int GIFT_CARD_NUMBER = 3;
		public static final int PIN = 4;
		public static final int NEW_PASSWORD = 5;
		public static final int CONFIRM_NEW_PASSWORD = 6;
		
		private MyAccount() {}
	}
	
	public static final class BookingData {
		public static final int SERIAL_NO = 0;
		public static final int TESTCASE_NAME = 1;
		public static final int HOTEL_NAME = 2;
		
		private BookingData() {}
	}
	
	public static final class CardData {
		public static final int SERIAL_NO = 0;
		public static final int CARD_NUMBER = 1;
		public static final int EXPIRY_MONTH = 2;
		public static final int EXPIRY_YEAR = 3;
		public static final int CVV = 4;
		
		private CardData() {}
	}
	
	public static final class GiftCard {
		public static final int SERIAL_NO = 0;
		public static final int AMOUNT = 1;
		public static final int QUANTITY = 2;
		public static final int ADDRESS = 3;
		public static final int PINCODE = 4;
		
		private GiftCard() {}
	}
}
