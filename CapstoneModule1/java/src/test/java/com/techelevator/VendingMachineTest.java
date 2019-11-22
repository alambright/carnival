package com.techelevator;

import static org.junit.Assert.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;

public class VendingMachineTest {

	
	
	VendingMachine vm = new VendingMachine();
	
	@Test
	public void vendingMachineStockedCorrectlyAndQuantityUpdates() {
		List<Products> actualProductList = vm.readFile();
		// Assume we are reading from the provided test file.
		// Alternatively, open a scanner read the file here and 
		// see if there are 16 lines.
		actualProductList.get(0).updateQuantity();
		int actual = actualProductList.get(0).updateQuantity();
		
		assertEquals(4, actual);
		assertEquals(16, actualProductList.size());
	
	}
	
	@Test
	public void feedOneAndTenDollarsUpdatesCurrentMoneyCorrectly() {
		
		String oneDollarBillStr = "5";
		String tenDollarBillStr = "10";
		
		BigDecimal testOneDollar = new BigDecimal(oneDollarBillStr);
		BigDecimal tenDollarBill = new BigDecimal(tenDollarBillStr);

		//Simulate that a one dollar bill was fed followed by a 10 dollar bill.
		for (int i=0; i< 2; i++) {
			
//			public static void setCurrentMoney(BigDecimal updatedAmount) {

			if (i== 0) {
				vm.addToCurrentMoney(testOneDollar);
			}
			else {
				vm.addToCurrentMoney(tenDollarBill);
			}
		}
		
		BigDecimal expectedResult = new BigDecimal("15");
		
		assertEquals(expectedResult, VendingMachine.currentMoney);
		
	}
	@Test
	public void currentMoneyReturnsCorrectChangeCorrectly() {
		BigDecimal balanceToRetrunAsChange = new BigDecimal("1.40");
		double expected = balanceToRetrunAsChange.doubleValue();
		double actual = vm.getchange(balanceToRetrunAsChange);
		
		assertEquals(expected, actual, 0.00001);
		
		
	}
	
	@Test
	public void isLogFileCreated() throws IOException {
	
		assertEquals(true, vm.getNewAuditFile());
	}
	
	@Test
	public void auditLineContainsCorrectInfo() {		
		
		String actualInfo = vm.getDateAndTime("FEED MONEY", new BigDecimal("10"), new BigDecimal("8.95"));
		String expectedResult = " FEED MONEY $10.00 $8.95\n";
		
		assertEquals(expectedResult, actualInfo);
//	LogProducingService service = new LogProducingService();
//	service.writeSomeLogicStatements("A");
//	assertThat(StaticAppender.getEvents()).extracting("message").containsOnly("This message is in a separate thread");
	}
	
	@Test
	public void returnMachineBalanceToZero() {
		assertEquals(new BigDecimal("0"), vm.returnMachineBalanceToZero());
		
		
		
	}
	


}
