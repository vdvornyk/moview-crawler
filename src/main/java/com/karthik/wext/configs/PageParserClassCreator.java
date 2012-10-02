package com.karthik.wext.configs;

import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtNewMethod;
import javassist.NotFoundException;

public class PageParserClassCreator {
	private final static String PACKAGE_NAME = PageParserClassCreator.class.getPackage().getName() + ".";

	public static Class createClass(PageParserMethodContainer methodContainer, String className) {

		try {
			ClassPool pool = ClassPool.getDefault();
			CtClass evalClass = pool.makeClass(PACKAGE_NAME + className);

			evalClass.setSuperclass(pool.get("com.karthik.wext.core.PageParserStrategy"));

			// Get the method from the Class byte code
			for (int i = 1; i < methodContainer.size(); i++) {
				// LogUtils.logger.info("id={}", className);
				evalClass.addMethod(CtNewMethod.make("public void " + methodContainer.getName(i) + "() { " + methodContainer.getBody(i) + " }", evalClass));
			}

			evalClass.writeFile();
			evalClass.detach();
			return evalClass.toClass();

		} catch (NotFoundException | IOException | CannotCompileException e1) {
			// TODO Auto-generated catch block

			e1.printStackTrace();
		}

		return null;

	}
}
