package com.karthik.wext.bytecode;

import java.io.IOException;

import javassist.CannotCompileException;
import javassist.NotFoundException;

import org.junit.Assert;
import org.junit.Test;

public class JavassistTest {
	@Test
	public void methodAbstractInjectionTest() {
		String className = "com.karthik.wext.bytecode.SomeClass";
		String methodName = "getString";
		String methodBody1 = "return \"" + SomeClass.CONTENT1 + "\";";
		String methodBody2 = "return \"" + SomeClass.CONTENT2 + "\";";
		String methodBody3 = "return \"" + SomeClass.CONTENT3 + "\";";

		try {

			SomeAbstractClass myClasss1 = MethodImplnjectorSuperclass.modifyByteCode("Site1", methodName, methodBody1);
			Assert.assertEquals(SomeClass.CONTENT1, myClasss1.getMessage());

			SomeAbstractClass myClasss2 = MethodImplnjectorSuperclass.modifyByteCode("Site2", methodName, methodBody2);
			Assert.assertEquals(SomeClass.CONTENT2, myClasss2.getMessage());

			SomeAbstractClass myClasss3 = MethodImplnjectorSuperclass.modifyByteCode("Site3", methodName, methodBody3);
			Assert.assertEquals(SomeClass.CONTENT3, myClasss3.getMessage());

		} catch (NotFoundException | CannotCompileException | IOException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
