package de.leuphana.ardrone.dronesystem.domain.util;


/**
 * represents an auto incrementing counter
 * @author Florian Quadt
 *
 */
public final class Counter {
	private static long counter = 1;

	public synchronized static long get() {
		return counter++;
	}
	public synchronized static long getCounterWithoutIncreasing() {
		return counter;
	}

	public synchronized static void reset() {
		counter = 1;
		System.out.println("Command Counter resetted to 1");
	}
	
	private Counter()
	{
		
	}
}
