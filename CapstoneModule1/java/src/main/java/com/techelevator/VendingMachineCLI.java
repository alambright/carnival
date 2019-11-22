package com.techelevator;


import java.io.FileNotFoundException;


import com.techelevator.view.Menu;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items>";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase>";
	private static final String MAIN_MENU_OPTION_END = "Exit>";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE,
			MAIN_MENU_OPTION_END };
	private static final String FEED_MONEY = "Feed Money";
	private static final String SELECT_PRODUCT = "Select Product";
	private static final String FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = { FEED_MONEY, SELECT_PRODUCT, FINISH_TRANSACTION };

	private Menu menu;
	//789123git private static VendingMachine vendingMachine;

	VendingMachineCLI(Menu menu){//, VendingMachine vendingMachine) {
		this.menu = menu;
		//VendingMachineCLI.vendingMachine = vendingMachine;
	}

	public void run() throws FileNotFoundException {
		VendingMachine.readFile();
		Boolean bool = true;
		while (bool) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				
				VendingMachine.displayProducts();

			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				boolean askAgain = true;
				while (askAgain) {
					String purchaseChoice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

					if (purchaseChoice.equals(FEED_MONEY)) {
						VendingMachine.feedMoney();

					} else if (purchaseChoice.equals(SELECT_PRODUCT)) {
						VendingMachine.displayProducts();
						VendingMachine.selectProduct();

					} else if (purchaseChoice.equals(FINISH_TRANSACTION)) {
						VendingMachine.finishTransaction();
						askAgain = false;
					}
				}

			} else if (choice.equals(MAIN_MENU_OPTION_END)) {
				bool = false;
			}

		} 
	}

	public static void main(String[] args) throws FileNotFoundException {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);//, vendingMachine);
		cli.run();
	}
	
	
	
}
