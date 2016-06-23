package com.test.test;

import org.junit.Test;

import com.test.factory.Operation;
import com.test.factory.OperationFactory;

public class TestDesignPattern {
	
	@Test
	public void testFactoryPattern(){
		Operation operation = OperationFactory.createOperate("+");
		operation.setNumberA(1);
		operation.setNumberB(2);
		System.out.println(operation.getResult());
	}
}


