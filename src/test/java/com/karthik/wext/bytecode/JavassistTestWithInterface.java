package com.karthik.wext.bytecode;

import java.io.IOException;

import javassist.CannotCompileException;
import javassist.NotFoundException;

import org.junit.Assert;
import org.junit.Test;

public class JavassistTestWithInterface {
	@Test
	public void doTest() {
		String className = "com.karthik.wext.bytecode.SomeClass";
		String methodName = "getString";
		String methodBody1 = "return \"" + SomeClass.CONTENT1 + "\";";
		String methodBody2 = "return \"" + SomeClass.CONTENT2 + "\";";
		String methodBody3 = "return \"" + SomeClass.CONTENT3 + "\";";

		try {

			SomeInterface myClasss1 = MethodImplnjectorInterface.modifyByteCode("Site1_interf", methodName, methodBody1);
			Assert.assertEquals(SomeClass.CONTENT1, myClasss1.getMessage());

			SomeInterface myClasss2 = MethodImplnjectorInterface.modifyByteCode("Site2_interf", methodName, methodBody2);
			Assert.assertEquals(SomeClass.CONTENT2, myClasss2.getMessage());

			SomeInterface myClasss3 = MethodImplnjectorInterface.modifyByteCode("Site3_interf", methodName, methodBody3);
			Assert.assertEquals(SomeClass.CONTENT3, myClasss3.getMessage());

		} catch (NotFoundException | CannotCompileException | IOException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
