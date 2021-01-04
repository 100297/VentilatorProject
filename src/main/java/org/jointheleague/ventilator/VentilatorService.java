package org.jointheleague.ventilator;

import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import org.java_websocket.WebSocket;
import org.jointheleague.ventilator.sensors.SensorExamples;
import java.io.IOException;
import java.util.Random;

public class VentilatorService {
    public static void vs_getAll(JSONObject message, WebSocket conn, long reqnum) throws ProtocolException {
		JSONObject response = new JSONObject();
		response.put("request", reqnum);
		response.put("status", 200);
		response.put("timestamp", System.currentTimeMillis() / 1000L);
		
		JSONObject data = new JSONObject(); // "data" property of response

		try {
			SensorExamples se = new SensorExamples();
			
			float hum = se.readHumidity();
			float pre = se.readPressure();
			float tem = se.readTemperature();

			if (hum == 0 || pre == 0 || tem == 0) { // If getting error values
				throw new Exception();
			} else {
				data.put("humidity", se.readHumidity());
				data.put("pressure", se.readPressure());
				data.put("temperature", se.readTemperature());
			}
		} catch (Exception e) {
			throw new ProtocolException("Unable to read sensors", 500);
		}

		response.put("data", data); // Add "data" property to response

		// Sends response
		conn.send(response.toString());

		// Logs
		System.out.println("Responded to message with HTTP 200:");
		System.out.println(response.toString());
		System.out.println("End\n");
    }
}