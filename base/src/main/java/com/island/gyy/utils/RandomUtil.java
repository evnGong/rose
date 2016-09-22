package com.island.gyy.utils;

import java.util.Random;

public class RandomUtil {
	
	public static int getRandomNumber(int n) {
		return new Random(System.currentTimeMillis()).nextInt(n);
	}
}
