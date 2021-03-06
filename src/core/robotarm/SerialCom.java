package core.robotarm;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


/**
 * Title: Serial communicator
 * Description: Writes data and reads data on com-port.
 * Eventbased listener.
 * 
 * @author dannic, sajohan
 *
 */
public class SerialCom {

	
	private static RobotHandler robotHandler;
	private OutputStream out;
	
	/**
	 * SerialCom constructor
	 * @param RobotHandler
	 */
    public SerialCom(RobotHandler robotHandler)
    {
    	this.robotHandler = robotHandler;
    }
    
    /**
     * Writes a string to the serial COM-port
     * @param String 
     */
    public void writeString(String s){
    	byte[] data = s.getBytes();
    	try {
			out.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Connects to a COM-port using the inparameter portName
     * Baudrate is set to 57600 
     * @param String
     * @throws Exception
     */
    void connect ( String portName ) throws Exception
    {
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if ( portIdentifier.isCurrentlyOwned() )
        {
            System.out.println("Error: Port is currently in use");
        }
        else
        {
            CommPort commPort = portIdentifier.open(this.getClass().getName(),2000);
            
            if ( commPort instanceof SerialPort )
            {
                SerialPort serialPort = (SerialPort) commPort;
                serialPort.setSerialPortParams(57600,SerialPort.DATABITS_8,SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
                
                InputStream in = serialPort.getInputStream();
                out = serialPort.getOutputStream();
                               
                (new Thread(new SerialWriter(out))).start();
                
                serialPort.addEventListener(new SerialReader(in));
                serialPort.notifyOnDataAvailable(true);

            }
            else
            {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }
    }
    
    /**
     * Handles the input coming from the serial port. A new line character
     * is treated as the end of a block in this example. 
     */
    public static class SerialReader implements SerialPortEventListener 
    {
        private InputStream in;
        private byte[] buffer = new byte[1024];
        
        
        
        public SerialReader ( InputStream in )
        {
            this.in = in;
        }
        
        public void serialEvent(SerialPortEvent arg0) {
            int data;
          
            try
            {
                int len = 0;
                while ( ( data = in.read()) > -1 )
                {
                    if ( data == '\n' ) {
                        break;
                    }
                    buffer[len++] = (byte) data;
                }
                robotHandler.setInData(buffer);

                System.out.println("Read data: "+new String(buffer,0,len));
            }
            catch ( IOException e )
            {
                e.printStackTrace();
                System.exit(-1);
            }             
        }
        
        public byte[] getBuffer() {
			return buffer;
		}

    }

    /**
     * Writes to the serialport(using console..) No longer used. Use writeString() instead
     * @deprecated
     */
    public static class SerialWriter implements Runnable 
    {
        OutputStream out;
        
        public SerialWriter ( OutputStream out )
        {
            this.out = out;
        }
        
        public void run ()
        {
            try
            {                
                int c = 0;
                while ( ( c = System.in.read()) > -1 )
                {
                    this.out.write(c);
                }                
            }
            catch ( IOException e )
            {
                e.printStackTrace();
                System.exit(-1);
            }            
        }
    }
	
}
