package model;

public class Constants {

	// Image URI's
	public static final String addSensorIconURI = "./Icons/addsensoriconsmall.png";
	public static final String handIconURI = "./Icons/handiconsmall.png";
	public static final String cameraIconURI = "./Icons/cameraiconsmall.png";
	public static final String selectionIconURI = "./Icons/selecticonsmall.png";

	// Options Panel Strings
	//tool control buttons
	public static final String handbutton = "handbutton";
	public static final String addsensorbutton = "sensorbutton";
	public static final String camerabutton = "camerabutton";
	public static final String selectionbutton = "selectionbutton";
	public static final String scaleslider = "scaleslider";
	public static final String brightslider = "brightslider";
	//camera control buttons
	public static final String freeCam= "freeCam";
	public static final String cameraXLock= "cameraXLock";
	public static final String cameraXRLock= "cameraXRLock";
	public static final String cameraYLock = "cameraYLock";
	public static final String cameraYRLock = "cameraYRLock";
	public static final String cameraZLock = "cameraZLock";
	public static final String cameraZRLock = "cameraZRLock";

	public static final String handtooltip = "Hand Tool";
	public static final String addsensortooltip = "Sensor Creation Tool";
	public static final String selectiontooltip = "Selection Tool";
	public static final String cameratooltip = "Camera Options";

    
    public static final double ACCURACYVALUE = 0.0001;  //The smaller number the higher accuracy
    
    public static final int SCALE_MIN = -10;
	public static final int SCALE_MAX = 10;
	public static final int SCALE_INIT = 0; // initial scale

	public static final int BRIGHT_MIN = 1;
	public static final int BRIGHT_MAX = 10;
	public static final int BRIGHT_INIT = 5; // initial scale
	
	

	public static final int SCALE_SPEED = 20; // Speed at which the scale slider
												// will zoom
	/*
	 * Camera constants
	 */
	//lock camera on x or y or z axle
	public static final int CAM_LOCK_X = 1;
	public static final int CAM_LOCK_Y = 2;
	public static final int CAM_LOCK_Z = 3;
	public static final double CAM_DISTANCE = 4d;// camera distance from origo
}
