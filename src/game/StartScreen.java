package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartScreen extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JFrame title_frame;
	private final Dimension start_dimension = new Dimension(520, 450);

	public StartScreen() {
		
		title_frame = new JFrame();
		title_frame.setTitle("CHECKERS");
		title_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		title_frame.setSize(start_dimension);
		title_frame.setLocationRelativeTo(null);
		title_frame.setLayout(new BorderLayout());
		
		setBackground(Color.black);
		setVisible(true);
		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
		
		JLabel title_text = new JLabel();
		title_text.setText("<HTML><div style='text-align: center;'>"
					+ "<p><font color='red'>"
						+ "CHECKERS"
					+ "</font></p><p></p>" 
					+ "<p><font color='white'>"
						+ "click to start"
					+ "</font></p>"
				+ "</div><HTML>");
		title_text.setFont(new Font("Sansserif", Font.PLAIN, 64));
		add(title_text);
		
		title_frame.add(this);
		title_frame.setVisible(true);
		
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				// remove title screen
				title_frame.dispose();
				
				// start game
				new CheckerFrame();
			}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}
		});
	}
}































