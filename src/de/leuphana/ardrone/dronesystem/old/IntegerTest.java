package de.leuphana.ardrone.dronesystem.old;

public class IntegerTest {
	
	public static void main(String[] args) {
		float x = -0.8f;
//		System.out.println(Float.toHexString(x));
		System.out.println(Float.floatToIntBits(x));
		System.out.println(Float.floatToIntBits(-0.2f));
	}

}
