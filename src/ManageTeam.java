//import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

 class ManageTeam implements ActionListener{
	//private static final long serialVersionUID = 1L;
	private JPanel pan;
	private JButton but;
	private JTextField textBox;
	
	public ManageTeam() {
	}
	
	public JPanel getManagePan() {
		pan = new JPanel();
		pan.setLayout(null);
		
		textBox = new JTextField("", 10);
		textBox.setBounds(25, 20, 160, 20);
		pan.add(textBox);
		
		but = new JButton("Ajouter une équipe");
		but.addActionListener(this);
		but.setBounds(25, 50, 160, 20);
		pan.add(but);
		
		return pan;
	}
	
	/*public void paintComponent(Graphics g) {
		
	}*/
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JOptionPane.showMessageDialog(pan, "FEUUU");
	}
}
