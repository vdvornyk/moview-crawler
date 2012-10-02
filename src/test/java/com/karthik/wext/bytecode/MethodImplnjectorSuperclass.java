package com.karthik.wext.bytecode;

import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtNewMethod;
import javassist.NotFoundException;

public class MethodImplnjectorSuperclass {
	private final static String PACKAGE_NAME = "com.karthik.wext.bytecode.";

	public static SomeAbstractClass modifyByteCode(String siteId, String methodName, String methodBody) throws NotFoundException,
			CannotCompileException, IOException, InstantiationException, IllegalAccessException {

		ClassPool pool = ClassPool.getDefault();
		CtClass evalClass = pool.makeClass(PACKAGE_NAME + "Site_" + siteId);
		evalClass.setSuperclass(pool.get("com.karthik.wext.bytecode.SomeAbstractClass"));

		// Get the method from the Class byte code
		evalClass.addMethod(CtNewMethod.make("public String getMessage() { " + methodBody + " }", evalClass));

		evalClass.writeFile();

		evalClass.detach();
		/**
		 * Inserting the body
		 */

		return (SomeAbstractClass) evalClass.toClass().newInstance();

	}
}
