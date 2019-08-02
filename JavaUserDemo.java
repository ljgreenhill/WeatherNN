import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;

public class JavaUserDemo extends JFrame {

	JavaUser user;

	public JavaUserDemo() {

		setTitle("Weather Station");

		JPanel panel = new JPanel();

		panel.setLayout(new GridLayout(6, 3));

		JLabel welcomeLabel = new JLabel("Weather Station");
		JLabel dewpointLabel = new JLabel("Dewpoint");
		JLabel temperatureLabel = new JLabel("Temperature");
		JLabel pressureLabel = new JLabel("Pressure");
		JLabel humidityLabel = new JLabel("Humidity");
		JLabel blank1 = new JLabel(" ");
		JLabel blank2 = new JLabel(" ");
		JLabel blank3 = new JLabel(" ");
		JLabel blank4 = new JLabel(" ");

		JTextField tf = new JTextField(10);

		JRadioButton dCelciusButton = new JRadioButton("(Celsius)");
		JRadioButton dFarenheitButton = new JRadioButton("(Farenheit)");
		JRadioButton tCelciusButton = new JRadioButton("(Celsius)");
		JRadioButton tFarenheitButton = new JRadioButton("(Farenheit)");
		JRadioButton inchesButton = new JRadioButton("(Inches)");
		JRadioButton rawButton = new JRadioButton("(Raw)");
		JRadioButton percentButton = new JRadioButton("(%)");

		JButton submit = new JButton("Submit Request");
		submit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (tf.getText() != null) {
					if (dCelciusButton.isSelected()) {
						user.writeServer("USER:DC-" + tf.getText());
						System.out.println("Requesting DC from server");
					}
					if (dFarenheitButton.isSelected()) {
						user.writeServer("USER:DF-" + tf.getText());
						System.out.println("Requesting DF from server");
					}
					if (tCelciusButton.isSelected()) {
						user.writeServer("USER:TC-" + tf.getText());
						System.out.println("Requesting TC from server");
					}
					if (tFarenheitButton.isSelected()) {
						user.writeServer("USER:TF-" + tf.getText());
						System.out.println("Requesting TF from server");
					}
					if (inchesButton.isSelected()) {
						user.writeServer("USER:Pnches-" + tf.getText());
						System.out.println("Requesting PI from server");
					}
					if (rawButton.isSelected()) {
						user.writeServer("USER:P-" + tf.getText());
						System.out.println("Requesting P from server");
					}
					if (percentButton.isSelected()) {
						user.writeServer("USER:H-" + tf.getText());
						System.out.println("Requesting percent humidity from server");
					}

				}

				try {
					JOptionPane.showMessageDialog(panel, user.receiveServer());
				} catch (HeadlessException | IOException e2) {
					System.out.println("ERROR: Receive Server");
				}

				System.exit(0);

			}
		});

		ButtonGroup group = new ButtonGroup();

		group.add(inchesButton);
		group.add(rawButton);
		group.add(tCelciusButton);
		group.add(tFarenheitButton);
		group.add(dCelciusButton);
		group.add(dFarenheitButton);
		group.add(percentButton);

		panel.add(blank1);
		panel.add(welcomeLabel);
		panel.add(blank2);

		panel.add(temperatureLabel);
		panel.add(tCelciusButton);
		panel.add(tFarenheitButton);

		panel.add(pressureLabel);
		panel.add(rawButton);
		panel.add(inchesButton);

		panel.add(dewpointLabel);
		panel.add(dCelciusButton);
		panel.add(dFarenheitButton);

		panel.add(humidityLabel);
		panel.add(percentButton);
		panel.add(blank3);

		panel.add(tf);
		panel.add(blank4);
		panel.add(submit);

		panel.setVisible(true);

		add(panel);

		setVisible(true);
		pack();

		user = new JavaUser(); // service class object

	}

	public static void main(String[] args) {
		new JavaUserDemo();

	}
}

class JavaUser {
	private String b = "nothing";
	private int time;
	private String measurement;
	Socket soc = newSocket1();
	// Socket soc = new Socket("169.254.241.142", 12345);
	DataOutputStream dout = getOUT();

	public void writeServer(String request) {
		try {
			// Socket soc = new Socket("169.254.241.142", 12345);
			// DataOutputStream dout = new DataOutputStream(soc.getOutputStream());
			dout.writeUTF(request);

			
			// dout.flush();
			// dout.close();
			// soc.close();
		} catch (Exception e) {
			System.out.println("ERROR: Write to server");
			e.printStackTrace();
		}
	}

	public String receiveServer() throws UnknownHostException, IOException {

		InputStream in = null;
		byte[] buffer = new byte[157 * 10];
		// Socket soc = new Socket("169.254.241.142", 12345);
		in = soc.getInputStream();
		in.read(buffer);
		in.close();
		// soc.close();
		String b1 = new String(buffer);
		// setB(b);
		return b1;

	}

	public Socket newSocket1() {
		Socket soc = null;
		try {
			soc = new Socket("169.254.241.142", 12345);

		} catch (IOException e) {
			System.out.println("ERROR: Socket");
		}
		return soc;
	}

	public DataOutputStream getOUT() {
		DataOutputStream dout = null;
		try {
			dout = new DataOutputStream(soc.getOutputStream());
		} catch (IOException e) {
			System.out.println("ERROR:DataOutputStream");
		}
		return dout;
	}

	public String getB() {
		return b;
	}

	public void setB(String b2) {
		b = b2;
	}

	public JavaUser() {
		time = 0;
		measurement = " ";
	}

	public int getTime() {
		return time;
	}

	public void setTime(int t) {
		time = t;
	}

	public String getMeasurement() {
		return measurement;
	}

	public void setMinutes(String m) {
		measurement = m;
	}
}
