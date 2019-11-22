package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class VenMach {
	
	private static Scanner in = new Scanner(System.in);
	private static List<Products> productSelectionList = new ArrayList<>();
	private static String auditLine = "";

	private static BigDecimal currentMoney = new BigDecimal(0);
	private static BigDecimal addMoney = new BigDecimal(0);

	public static void displayProducts() {
		System.out.println("Slot Location Product Name " + "Price Type Stock");
		System.out.println(String.join("", Collections.nCopies(66, "=")));
		for (Products items : productSelectionList) {
			String Id = items.slotId();
			String p = items.price().toString();
			int q = items.quantity();
			String n = items.name();
			if (q == 0) {
				System.out.println(
						Id + generateSpace(Id) + n + generateSpace(n) + "$" + p + generateSpace(p) + "SOLD OUT!!!");
			} else {
				System.out.println(Id + generateSpace(Id) + n + generateSpace(n) + "$" + p + generateSpace(p) + q);
			}
		}
	}
	public static String generateSpace(String str) {
		int spaceLength = 20 - str.length();
		String space = "";
		for (int i = 1; i <= spaceLength; i++) {
			space += " ";
		}
		return space;
	}
	
	
	

	public static List<Products> createProductList() {
		try {
			File inputInventoryFile = new File("vendingmachine.csv");
			Scanner showMenu = new Scanner(inputInventoryFile);
			while (showMenu.hasNextLine()) {
				String inventoryLine = showMenu.nextLine();

				String[] inventoryArray = inventoryLine.split("\\|");
				BigDecimal price = new BigDecimal(inventoryArray[2]);

				if (inventoryArray[3].equals("Chip")) {
					Products item = new Chips(inventoryArray[1], price, inventoryArray[0], inventoryArray[3]);
					productSelectionList.add(item);
				} else if (inventoryArray[3].equals("Candy")) {
					Products item = new Candy(inventoryArray[1], price, inventoryArray[0], inventoryArray[3]);
					productSelectionList.add(item);
				} else if (inventoryArray[3].equals("Drink")) {
					Products item = new Drink(inventoryArray[1], price, inventoryArray[0], inventoryArray[3]);
					productSelectionList.add(item);
				} else if (inventoryArray[3].equals("Gum")) { // .equals
					Products item = new Gum(inventoryArray[1], price, inventoryArray[0], inventoryArray[3]);
					productSelectionList.add(item);
				}
			}
			showMenu.close();

		} catch (FileNotFoundException e) {
			System.out.println("This file was not found!");

		}
		return productSelectionList;
	}



	public static void feedMoney() {
		boolean takeMoreMoney = true;
		while (takeMoreMoney) {

			System.out.println("Feed Money: ");
			String moneyInput = in.nextLine();
			addMoney = new BigDecimal(moneyInput);
			if (moneyInput.equals("1") || moneyInput.equals("2") || moneyInput.equals("5") || moneyInput.equals("10")) {
				currentMoney = currentMoney.add(addMoney);
				System.out.println("Your current balance is $" + currentMoney);
				System.out.println("Feed more money (Y)es/(N)o? ");
				String moreMoney = in.nextLine();
				if (moreMoney.toLowerCase().equals("n")) {
					takeMoreMoney = false;
				}
				getDateAndTime("Feed Money", addMoney, currentMoney);
			} else {
				takeMoreMoney = false;
				System.out.println("Vending machine only accepts $1, $2, $5, or $10 bills");
			}
		}
	}
	
	
	

	public static void selectProduct() {
		System.out.println("Please make a selection: ");
		String productID = in.nextLine();

		boolean varExist = false;
		BigDecimal price = new BigDecimal("0");
		String name = "";
		String type = "";
		int newQuantity = 0;
		String slotId = "";
		String message = "";

		for (Products foodObject : productSelectionList) {
			if ((foodObject.slotId().equals(productID))) {
				varExist = true;
				name = foodObject.name();
				price = foodObject.price();
				type = foodObject.type();
				newQuantity = foodObject.updateQuantity();
				slotId = foodObject.slotId();
				message = foodObject.message();
			}
		}

		if (varExist) {
			if (price.compareTo(currentMoney) == -1) {
				if (newQuantity > 0) {
					currentMoney = currentMoney.subtract(price);
					System.out.println(name + ": $" + price);
					System.out.println(message);
					System.out.println("Your current balance is $" + currentMoney);
				} else {
					System.out.println("Out of Stock");
				}
			} else {
				System.out.println("Insufficient funds");
			}
		} else {
			System.out.println("Item does not Exist");
		}
		String nameId = name + " " + slotId;
		getDateAndTime(nameId, addMoney, currentMoney);
	}

	public static void finishTransaction() {
		int countQuarters = 0;
		int countDimes = 0;
		int countNickels = 0;
		BigDecimal auditMoney = currentMoney;

		while (currentMoney.compareTo(new BigDecimal("0.25")) == 1
				|| currentMoney.compareTo(new BigDecimal("0.25")) == 0) {
			currentMoney = currentMoney.subtract(new BigDecimal(0.25));
			countQuarters++;
		}
		while (currentMoney.compareTo(new BigDecimal("0.10")) == 1
				|| currentMoney.compareTo(new BigDecimal("0.10")) == 0) {
			currentMoney = currentMoney.subtract(new BigDecimal("0.10"));
			countDimes++;
		}
		while (currentMoney.compareTo(new BigDecimal("0.05")) == 0) {
			currentMoney = currentMoney.subtract(new BigDecimal("0.05"));
			countNickels++;
		}
		getDateAndTime("Give Change", auditMoney, currentMoney);
		System.out.println("Your total change is:\n" + countQuarters + " quarter(s)\n" + countDimes + " dime(s)\n"
				+ countNickels + " nickel(s).");
		System.out.println("Current balance is: $" + currentMoney);
		System.out.println("Thank you for your purchase!");
		
	}
	
	public static void getDateAndTime(String action, BigDecimal addMoney, BigDecimal currentMoney) {
		DateFormat auditDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss aa ");
		Date dateObject = new Date();
		auditLine = auditDate.format(dateObject) + " " + action + " " + addMoney + " " + currentMoney + "\n";

		try {
			getNewAuditFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void getNewAuditFile() throws IOException {

		try {
			File file = new File("log.txt");
			PrintWriter pw = null;

			if (file.exists()) {
				pw = new PrintWriter(new FileOutputStream(file.getAbsoluteFile(), true));

			} else {
				pw = new PrintWriter(file.getAbsoluteFile());

			}
			pw.append(auditLine);
			pw.flush();
			pw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
