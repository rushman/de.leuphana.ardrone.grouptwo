package de.leuphana.ardrone.dronesystem.old;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;

public class KeyDispatcher implements KeyEventDispatcher {
	    @Override
	    public boolean dispatchKeyEvent(KeyEvent e) {
	        if (e.getID() == KeyEvent.KEY_PRESSED) {
	            System.out.println("tester");
	        } else if (e.getID() == KeyEvent.KEY_RELEASED) {
	            System.out.println("2test2");
	        } else if (e.getID() == KeyEvent.KEY_TYPED) {
	            System.out.println("3test3");
	        }
	        return false;
	    }
	}