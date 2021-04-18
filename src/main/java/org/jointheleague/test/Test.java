package org.jointheleague.test;

import org.jointheleague.ventilator.stepper.StepperController;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.jointheleague.ventilator.BreathController;
import org.jointheleague.ventilator.PatientProfile;
import org.jointheleague.ventilator.PositionCheck;
import org.jointheleague.ventilator.sensors.SensorReader;

// TODO what does this do
public class Test {
	public static double startTime = System.currentTimeMillis();
	public Test() {
		//simpleStepperTest();
		comprehensiveStepperTest();
	}
	
	private void comprehensiveStepperTest() {
		// TODO Auto-generated method stub
		PositionCheck pc = new PositionCheck(new SensorReader());
		StepperController sc = new StepperController();
		pc.moveToTop(sc);
		PatientProfile testP = new PatientProfile(16,(double)64,(double)120,(double)20.6,"female","COVID-19");
		BreathController bc = new BreathController(testP);
		double[] lidarNumbers = new double[10];
		Timer t = new Timer();
		TimerTask tt = new TimerTask2(lidarNumbers);
		t.scheduleAtFixedRate(tt, new Date(System.currentTimeMillis()), 500);
		while (1==1) {
			bc.breathe();
			//use calculateBreathRate() here whenever you need to display or use it?
		}
		
		
	}

	void simpleStepperTest() {
		StepperController sc = new StepperController();
		while (true) {
			sc.forward(10, 5);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			sc.backward(10, 5);
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class TimerTask2 extends TimerTask{
	double[] lidNums;
	SensorReader sr = new SensorReader();
	
	public TimerTask2(double[] ln) {
		lidNums = new double[ln.length];
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		for(int i = 1; i < lidNums.length; i++) {
			lidNums[i-1]=lidNums[i];
		}
		lidNums[lidNums.length-1]= sr.readLidar();
		
	}
	
}
