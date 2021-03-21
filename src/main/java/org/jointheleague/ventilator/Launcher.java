package org.jointheleague.ventilator;

import org.jointheleague.test.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.jointheleague.ventilator.server.VentilatorController;

@SpringBootApplication
public class Launcher {
	public static boolean RUN_TEST = false; // Flag for if test run of stepper motor
	public static boolean CONNECTED_VENTILATOR = false; // Flag for if connected to actual ventilator
	public static final int LOCAL_PORT = 5001;

	public static void main(String[] args) {
		SpringApplication.run(Launcher.class, args);

		if (RUN_TEST) {
			Test test = new Test();
		} else {
			VentilatorController e = new VentilatorController(LOCAL_PORT);
			e.start();
		}
	}
}
