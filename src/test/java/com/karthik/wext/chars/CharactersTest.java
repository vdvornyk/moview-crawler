package com.karthik.wext.chars;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class CharactersTest {
	@Test
	public void utfCharTest() {
		String title = "Goryl Śnieżek w Barcelonie";
		System.out.println(title);

		int arr[] = new int[] { 1, 2 };
		System.out.println(arr.length);
		System.out.println(Arrays.copyOfRange(arr, 1, arr.length).length);

	}

	@Test
	public void divTest() {
		int c = 15;

		Assert.assertEquals(1, (int) Math.ceil(4 / 15.0));
		Assert.assertEquals(1, (int) Math.ceil(1 / 15.0));
		Assert.assertEquals(1, (int) Math.ceil(15 / 15.0));
		Assert.assertEquals(2, (int) Math.ceil(16 / 15.0));
		Assert.assertEquals(2, (int) Math.ceil(29 / 15.0));
		Assert.assertEquals(2, (int) Math.ceil(30 / 15.0));
	}
}
