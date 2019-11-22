package com.techelevator;

import java.math.BigDecimal;

public class Drink implements Products {
	
	private String name;
	private BigDecimal price;
	private String slotId;
	private String type;
	private int quantity =5;
	private int updateQuantity=5;
	

	

	public Drink(String name, BigDecimal price, String slotId, String type) {
		super();
		this.name = name;
		this.price = price;
		this.slotId = slotId;
		this.type = type;
	}

	
	
	@Override
	public String name() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public BigDecimal price() {
		// TODO Auto-generated method stub
		return price;
	}

	@Override
	public String slotId() {
		// TODO Auto-generated method stub
		return slotId;
	}

	@Override
	public int quantity() {
		
		return updateQuantity;
	}

	@Override
	public String type() {
		// TODO Auto-generated method stub
		return type;
	}

	@Override
	public String message() {
		// TODO Auto-generated method stub
		return "Glug, glug, Yum!";
	}

	@Override
	public int updateQuantity() {
		int newQuantity = 0;
		if (updateQuantity > 0) {
			newQuantity = updateQuantity--;
		} else {
			newQuantity = 0;
		} return newQuantity;
	}

}
