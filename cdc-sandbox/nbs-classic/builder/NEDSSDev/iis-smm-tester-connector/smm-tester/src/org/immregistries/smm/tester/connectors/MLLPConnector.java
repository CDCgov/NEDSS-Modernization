/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.immregistries.smm.tester.connectors;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.immregistries.smm.mover.HL7;

/**
 * Each time the "Submit Message" method is called, a message is sent over the MLLP protocol, and then it waits until it receives 
 * a matching ACK back.  
 * @author Josh Hull
 */
public class MLLPConnector extends Connector {
	/**
	 * This socket is kept open for this instance, until the "shutdown" method
	 * is called.
	 */
	private Socket mllpSocket;

	private int port;
	private InetAddress host;

	/**
	 * This Map is a sort of temporary holder since we don't actually know what
	 * order the ACKs will come back in. IT's possible they will come back out
	 * of order, and this map should help to create some amount of continuity.
	 */
	private Map<String, String> ackMap = new HashMap<String, String>();

	protected MLLPConnector(String label, String url, String type) {
		super(label, type);
		this.url = url;
	}

	public MLLPConnector(String label, String urlPlusPort) throws IOException {
		super(label, "MLLP");
		System.out.println("Creating connector for MLLP for url: " + urlPlusPort + " Label: " + label);
		URL aURL = new URL(urlPlusPort);
		this.port = aURL.getPort();
		this.url = aURL.getHost();
		this.host = InetAddress.getByName(this.url);
	}

	@Override
	public String submitMessage(String message, boolean debug) throws Exception {
		if (message == null || message.length() == 0) {
			return "Empty message.  Skipping.";
		}

		System.out.println("SUBMITTING message.  Length of message: "
				+ message.length());
		// check to make sure the connection is open
		// if not, open it again.

		if (this.mllpSocket == null || !this.mllpSocket.isConnected()
				|| !this.mllpSocket.isBound()) {
			this.mllpSocket = this.openConnection();
		}

		InputStream socketInputStream = this.mllpSocket.getInputStream();
		OutputStream socketOutputStream = this.mllpSocket.getOutputStream();
		// send the request over MLLP.
		// get message-control-id out of the ack message, and put it in the map.
		String controlId = HL7.readField(message, 10);
		System.out.println("message in has control id: " + controlId);
		boolean sent = this.sendAnMLLPMessage(message, socketOutputStream);

		// wait for a reponse.  
		byte[] byteBuffer = new byte[5000];
		// if you wait too long, figure out how to handle that problem, and
		// recover.
		// Step 1: set a timeout...
		System.out.println("Message sent.  now waiting for a response. ");

		int tries = 0;// get one hundred input streams

		while (this.ackMap.get(controlId) == null && tries++ <= 100) {
			System.out.println("reading socket input stream");
//			int bytesReady = socketInputStream.available();// the number of
//															// bytes available
//															// without blocking.
			
			//This will block the thread until something is received back from the server. 
			socketInputStream.read(byteBuffer);
			String responseString = new String(byteBuffer);
			System.out.println("Received a response[" + responseString + "]");
			// Will it always be the whole message? Could it be partial??? Not
			// sure.  Could be sure by reading for the MLLP start/stop.  This doesn't appear to be necessary at the moment.
			// In the future, this could be added and tested

			String[] lines = responseString.split("[\n\r]{1,2}");
			if (lines != null && lines.length > 0) {
				for (String line : lines) {
					System.out.println("Evaluating line: " + line);
					if (line.startsWith(HL7.MSA)) {
						System.out.println("Line starts with MSA.  Getting controlId");
						String ackControlId = HL7.readField(line, 2);
						System.out.println("Putting ACK into map with control ID: "+ ackControlId);
						this.ackMap.put(ackControlId, responseString);
						break;
					}
				}
			}
		}
		

		String ack = this.ackMap.remove(controlId);
		System.out.println("MLLP Ack Map size - " + this.ackMap.size());
		// Get message-control-id from sent message, and pull it from the map.
		return ack;

		// you're looking for a MCIR ACK.
		// if you get an HIE ack before then,
		// wait a bit longer to see if the MCIR one ends up coming eventually.
	}

	public boolean sendAnMLLPMessage(String message, OutputStream out)
			throws IOException {
		String mllpMsgOutgoing = this.buildMllpPacket(message);
		// Send the MLLP wrapped message to the server!
		out.write(mllpMsgOutgoing.getBytes());
		return true;
	}

	// static final char END_OF_BLOCK = '\u001c';
	// static final char START_OF_BLOCK = '\u000b';
	static final char RHAPSODY_MLLP_START = 0x0B;
	static final char RHAPSODY_MLLP_TAIL1 = 0x1C;
	static final char RHAPSODY_MLLP_TAIL2 = 0x0D;

	protected String buildMllpPacket(String message) {
		StringBuilder msg = new StringBuilder();
		msg.append(RHAPSODY_MLLP_START).append(message)
				.append(RHAPSODY_MLLP_TAIL1).append(RHAPSODY_MLLP_TAIL2);
		return msg.toString();
	}

	@Override
	public void shutdown() {
//		super.shutdown();
		if (this.mllpSocket != null) {
			System.out.println("closing MLLP socket for - " + this.label);
			this.closeConnection(this.mllpSocket);
//		} else {
//			System.out.println("closing MLLP socket for - " + this.label + ": already closed");
		}
	}

	public void closeConnection(Socket inSocket) {
		try {
			inSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Socket openConnection() throws IOException {
		System.out.println("Opening Connection for " + this.label);
		return new Socket(this.host, this.port);
	}

	@Override
	public String connectivityTest(String message) throws Exception {
		Socket testSocket = openConnection();
		if (testSocket != null && testSocket.isConnected()) {
			this.closeConnection(testSocket);
			return "MLLP Connection Test: SUCCESS";
		}
		return "MLLP Connection Test: FAIL";
	}

	@Override
	protected void setupFields(List<String> fields) {
	}

	@Override
	protected void makeScriptAdditions(StringBuilder sb) {
	}
}
