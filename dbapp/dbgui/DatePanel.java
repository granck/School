package dbgui;

import java.awt.FlowLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import javax.swing.WindowConstants;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class DatePanel extends javax.swing.JPanel {
	private JLabel fromLabel;
	private JTextField day1;
	private JComboBox month;
	private JTextField year1;
	
	private String label; 

	/**
	* Auto-generated main method to display this 
	* JPanel inside a new JFrame.
	*/
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new DatePanel("To: "));
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	public DatePanel(String label) {
		super();
		this.label = label;
		initGUI();
	}
	
	private void initGUI() {
		try {
			{
				fromLabel = new JLabel();
				this.add(fromLabel);
				fromLabel.setText(label);
				fromLabel.setPreferredSize(new java.awt.Dimension(41, 26));
			}
			{
				day1 = new JTextField();
				this.add(day1);
				day1.setText("01");
				day1.setPreferredSize(new java.awt.Dimension(32, 23));
			}
			{
				ComboBoxModel monthModel = new DefaultComboBoxModel(
					new String[] {"JAN","FEB","MAR","APR","MAY","JUN", "JUL","AUG","SEP","OCT","NOV","DEC"});
				month = new JComboBox();
				this.add(month);
				month.setModel(monthModel);
			}
			{
				year1 = new JTextField();
				this.add(year1);
				year1.setText("2006");
				year1.setPreferredSize(new java.awt.Dimension(51, 23));
			}
			FlowLayout thisLayout = new FlowLayout();
			this.setPreferredSize(new java.awt.Dimension(254, 37));
			this.setLayout(thisLayout);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getDoM() {
		return day1.getText();
	}
	
	public String getMonth() {
		return month.getSelectedItem().toString(); 
	}
	
	public String getYear() {
		return year1.getText();
	}

	public String getDateString() { 
		String day = getDoM();
		if (day.length() == 1) day = "0" + day;
		return day + "-" + getMonth() + "-" + getYear();
	}
}
