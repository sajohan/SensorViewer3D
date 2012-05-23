package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.vecmath.Point3d;

import core.model.SensorValues;


public class SensorReader {

	BufferedReader br;
	SensorValues sv;

	public SensorReader() {
		
	}

	public SensorValues readFile(File file) throws IOException {
		br = new BufferedReader(new FileReader(file));
//		sv = new SensorValues();
		// check that file is the right type
		
		float x, y, z,value;
		Point3d p;
		
		// get xyz and value, stop when no more lines from file
		String line = br.readLine();
		while (line != null) {
			String[] data = line.split(",");
			x = new Float(data[0]);
			y = new Float(data[1]);
			z = new Float(data[2]);
			value = new Float(data[3]);
			
			// Add to structure
			p = new Point3d(x,y,z);
//			sv.addValue(p, value);
			
			// Get next line
			line = br.readLine();
		}
		return sv;
	}
}
