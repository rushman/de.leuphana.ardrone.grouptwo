package de.leuphana.ardrone.dronesystem.old;


/**
 * represents an auto incrementing counter
 * @author Florian Quadt
 *
 */
public final class CommandCounter {
	private static long counter = 1;

	public synchronized static long getCounter() {
		return counter++;
	}
	public synchronized static long getCounterWithoutIncreasing() {
		return counter;
	}

	public synchronized static void reset() {
		counter = 1;
		System.out.println("Command Counter resetted to 1");
	}
	
	private CommandCounter()
	{
		
	}
}
