package org.jointheleague.test;

import org.jointheleague.ventilator.stepper.StepperController;

public class Test {
	
	public Test() {
		simpleStepperTest();
	}
	
	void simpleStepperTest() {
		StepperController sc = new StepperController();
		while (1==1) {
		sc.forward(10, 5);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sc.backward(10, 5);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
}
