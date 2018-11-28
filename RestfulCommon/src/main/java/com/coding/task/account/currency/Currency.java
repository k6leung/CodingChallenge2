package com.coding.task.account.currency;

import java.util.HashSet;
import java.util.Set;

//didn't choose enum because may not be mappable to redis or other nosql as Map keys
public class Currency {

	public static final String HKD = "HKD";
	
	public static final String USD = "USD";
	
	public static final Set<String> currencySet = new HashSet<String>();
	
	static {
		Currency.currencySet.add(Currency.HKD);
		Currency.currencySet.add(Currency.USD);
	}
	
}
