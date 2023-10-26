package com.techelevator;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * This class is provided to you as a *suggested* class to start
 * your project. Feel free to refactor this code as you see fit.
 */
public class VendingMachineCLI extends Transaction {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT };
	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = " Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = {PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION};





	private PrintWriter logWriter;



	// this will print onto log
	public VendingMachineCLI() throws IOException {
		logWriter = new PrintWriter(new FileWriter("recordlog.txt", true ));
	}


	public void closeLogFile(){
		logWriter.close();
	}

	//this method is used to print out the date and time in a format
	private void logMessage(String message){
		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter =DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm:ss a");
		String formattedDateTime = dateTime.format(formatter);
		logWriter.println(formattedDateTime + " - " + message);

	}

	public static void main(String[] args) throws IOException {
		// created new object
		VendingMachineCLI cli = new VendingMachineCLI();
		cli.run();
	}
	private static Scanner userInput = new Scanner(System.in);

	public void run() {


		List<ProductInVendingMachine> products = new ArrayList<ProductInVendingMachine>();
		Transaction transaction = new Transaction();
		// Gets items with info from given file
		File inputFile = new File("main.csv");

		// started cart count at 1 so that it does not count zero as a purchase
		int cart = 1;


		try {
			Scanner fileScanner = new Scanner(inputFile);
			while(fileScanner.hasNextLine()) {
				// iterates over every  line
				String line = fileScanner.nextLine();
				//turn the strings from the inputFile in an array
				// splits strings from the comma
				String[] lineArr = line.split("\\,");
				             // this places slotId into array position 0
				String slot = lineArr[0];
				String name = lineArr[1];
												// returns a new double value that is initialized to the value corresponding
				BigDecimal price = BigDecimal.valueOf(Double.parseDouble(lineArr[2]));
				String type = lineArr[3];

				if(type.equals("Munchy")) {
					ProductInVendingMachine prod = new Munchy(slot, name, price, type, 5);
					products.add(prod);

				} else if(type.equals("Candy")){
					ProductInVendingMachine prod = new Candy(slot, name, price, type, 5);
					products.add(prod);
				}
				else if(type.equals("Drink")){
					ProductInVendingMachine prod = new Drink(slot, name, price, type, 5);
					products.add(prod);
				}
				else if(type.equals("Gum")){
					ProductInVendingMachine prod = new Gum(slot, name, price, type, 5);
					products.add(prod);
				}else{
					System.out.println("Entry invalid.");
				}
			}
		} catch(FileNotFoundException e) {

		}

		while (true) {
			System.out.println();
			System.out.println("(1) Display Vending Machine Items");
			System.out.println("(2) Purchase");
			System.out.println("(3) Exit");
			System.out.print("Please make a selection: ");
			String userChoice = userInput.nextLine();

			if (userChoice.equals("1")) {

				// display vending machine items

				for (ProductInVendingMachine prod : products) {
					// lists the product in this order onto the terminal
					System.out.println(prod.getSlotID() + ", " + prod.getName() + " $" + prod.getPrice() + " " + prod.getCategory() + " Quantity: " + prod.getQuantity());
				}

			} else if (userChoice.equals("2")) {
				while (true) {
					System.out.println();
					System.out.println();
					System.out.println("(1) Feed Money");
					System.out.println("(2) Select Product");
					System.out.println("(3) Finish Transaction");
					System.out.println("Please make a selection: ");

					Scanner userSecondChoice = new Scanner(System.in);
					String userNextChoice = userSecondChoice.nextLine();


					if (userNextChoice.equals("1")) {



						while (true) {
							System.out.println("Current Balance: $" + transaction.getBalance() + "\n");
							System.out.println("How much do you want to add? \n" + "(1) $1.00\n" + "(2) $5.00\n" + "(3) $10.00\n" + "(4) $20.00\n");


							Scanner moneyScanner = new Scanner(System.in);
							String moneyInput = moneyScanner.nextLine();
							// Money Selection
							if (moneyInput.equals("1")) {
								transaction.addBalance(BigDecimal.valueOf(1.00));

							} else if (moneyInput.equals("2")) {
								transaction.addBalance(BigDecimal.valueOf(5.00));

							} else if (moneyInput.equals("3")) {
								transaction.addBalance(BigDecimal.valueOf(10.00));

							} else if (moneyInput.equals("4")) {
								transaction.addBalance(BigDecimal.valueOf(20.00));
								;
							}
							System.out.println("Current Balance: $" + transaction.getBalance() + "\n");

							break;
						}
						logMessage("User added money. Current balance: $" + transaction.getBalance());

					} else if (userNextChoice.equals("2")) {
						System.out.println("Select Product: ");
						Scanner userProductChoice = new Scanner(System.in);
						String productChoice = userSecondChoice.nextLine();

						String found = "Invalid Entry";

						for (ProductInVendingMachine prod : products) {
							int balanceCheck = transaction.getBalance().compareTo(prod.getPrice());
							// if user input matches slotID AND balance is higher than price && product is in stock
							// then purchase is successful
							//OTHERWISE LINE 199
							if ((prod.getSlotID().equals(productChoice)) && (balanceCheck > 0) && (prod.getQuantity() >= 1)) {
								//every other item gets a discount
								if (cart % 2 == 0) {
									prod.lowerQuantity();
									//discount every other item by $1
									transaction.minusBalance(prod.getPrice().subtract(BigDecimal.ONE));
									found = prod.getMessage();
									//add count to cart located on line 39
									cart++;

									break;
								} else {
									prod.lowerQuantity();
									transaction.minusBalance(prod.getPrice());
									found = prod.getMessage();
									cart++;
									break;
								}
								// OTHERWISE
							} else if (((prod.getSlotID().equals(productChoice)) && balanceCheck < 0)) {
								found = "INSUFFICIENT FUNDS!\nPlease add more cash!";
								break;
							} else if (((prod.getSlotID().equals(productChoice)) && (prod.getQuantity() <= 0))) {
								found = "OUT OF STOCK!";
								break;
							}
						}
						System.out.println(found);
						System.out.println("Current Balance: $" + transaction.getBalance() + "\n");


						// If the product was found and purchased successfully
						if (!found.equals("Invalid Entry")) {
							logMessage("Purchased: " + found + "Current balance: $" + " " + transaction.getBalance());
						}
					} else if (userNextChoice.equals("3")) {

						int quarterCount = 0;
						int dimeCount = 0;
						int nickleCount = 0;

						BigDecimal quarterValue = BigDecimal.valueOf(0.25);
						BigDecimal dimeValue = BigDecimal.valueOf(0.10);
						BigDecimal nickelValue = BigDecimal.valueOf(0.05);
						BigDecimal lastBalance = transaction.getBalance();

						//This returns the the costumers change in coins
						while (lastBalance.compareTo(BigDecimal.ZERO) > 0) {
							// as long as the total balance and the value of a quarter are greater than zero
							while (lastBalance.compareTo(quarterValue) >= 0) {
								//subtract from a 0.25 from total balance
								lastBalance = lastBalance.subtract(BigDecimal.valueOf(0.25));
								// adds the count
								quarterCount++;
							}
							while (lastBalance.compareTo(dimeValue) >= 0) {
								// adds the count
								dimeCount++;
								lastBalance = lastBalance.subtract(BigDecimal.valueOf(0.10));
							}
							while (lastBalance.compareTo(nickelValue) >= 0) {
								// adds the count
								nickleCount++;
								lastBalance = lastBalance.subtract(BigDecimal.valueOf(0.05));
							}
						}
						lastBalance = BigDecimal.valueOf(0);

						System.out.println("Thanks for shopping!");
						System.out.println("Your change balance is: $" + transaction.getBalance() + "\n");
						String finalOutput = "Your change is " + quarterCount + " quarters, " + dimeCount + " dimes and " + nickleCount + " nickels.";
						System.out.println(finalOutput);
						//logged into recordLog
						logMessage("User finished transaction. Change balance: $" + transaction.getBalance());
						break;

					} else {
						System.out.println("Entry invalid.");
						logMessage("Invalid entry from the user.");
					}

				}

			} else if (userChoice.equals("3")) {

				System.out.println("Thanks for shopping!");
				logMessage("User exited the application.");
				break;

			} else {
				// if user input is invalid
				System.out.println("Entry invalid.");
				logMessage("Invalid entry from the user.");
			}

		}
		closeLogFile();
	}
	}
