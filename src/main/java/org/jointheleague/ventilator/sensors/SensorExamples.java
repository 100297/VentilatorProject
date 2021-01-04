package org.jointheleague.ventilator.sensors;

import java.io.IOException;

import org.jointheleague.ventilator.sensors.lidar.VL53L0XDevice;
import org.jointheleague.ventilator.sensors.pressure.BME280Driver;

import com.pi4j.io.i2c.I2CBus;

public class SensorExamples {
	BME280Driver bme280;
	int previousDist;

	public SensorExamples() {
		bme280 = BME280Driver.getInstance(I2CBus.BUS_1, BME280Driver.I2C_ADDRESS_76);
		previousDist = 1; // TODO: Am I using previousDist right?
	}

	public void test() {
		System.out.println("lidar:       "+readLidar());
		System.out.println("humidity:    "+readHumidity());
		System.out.println("pressure:    "+readPressure());
		System.out.println("temperature: "+readTemperature());
	}

	/**
	 * Reads the LIDAR distance.
	 * Returns 0 if an error occurs.
	 * @return LIDAR distance in mm
	 */
	public int readLidar() {
		// Using Lidar
		VL53L0XDevice sensor = null;
		try {
			sensor = new VL53L0XDevice(0x29, 30);
			int mm = sensor.range();

			if (previousDist != mm) {
				System.out.println(String.format("Distance: %d mm", mm));
				return mm;
			}
			previousDist = mm;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	/**
	 * Gets value of pressure sensor.
	 * Returns 0 if an error occurs.
	 * @return (Pressure in Pa)*256
	 */
	public float readPressure() {
		try {
			bme280.open();
			float[] values = bme280.getSensorValues();
			return values[2];
		} catch (IOException e) {
		} finally {
			try {
				if (bme280 != null) bme280.close();
			} catch (IOException e) {}
		}
		return 0;
	}
	
	/**
	 * Gets value of temperature sensor.
	 * Returns 0 if an error occurs. 
	 * @return (Temperature in °C)*100
	 */
	public float readTemperature() {
		try {
			bme280.open();
			float[] values = bme280.getSensorValues();
			return values[0];
		} catch (IOException e) {
		} finally {
			try {
				if (bme280 != null) bme280.close();
			} catch (IOException e) {}
		}
		return 0;
	}

	/**
	 * Gets value of humidity sensor.
	 * Returns 0 if an error occurs.
	 * @return (Humidity in %RH)*1024 (e.g. relative humidity of 34% would be 34816)
	 */
	public float readHumidity() {
		try {
			bme280.open();
			float[] values = bme280.getSensorValues();
			return values[1];
		} catch (IOException e) {
		} finally {
			try {
				if (bme280 != null) bme280.close();
			} catch (IOException e) {}
		}

		return 0;
	}
}
