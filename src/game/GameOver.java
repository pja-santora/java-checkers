package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameOver extends JPanel {

private static final long serialVersionUID = 1L;
	
	JFrame title_frame;

	public GameOver(String winner) {
		
		title_frame = new JFrame();
		title_frame.setTitle("CHECKERS");
		title_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		title_frame.setSize(700, 450);
		title_frame.setLocationRelativeTo(null);
		title_frame.setLayout(new BorderLayout());
		
		setBackground(Color.black);
		setVisible(true);
		setLayout(new GridBagLayout());
		setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
		
		JLabel title_text = new JLabel();
		title_text.setText("<HTML><div style='text-align: center;'>"
					+ "<p><font color='red'>"
						+ "GAME OVER! : " + winner + " WINS!"
					+ "</font></p><p></p>" 
					+ "<p><font color='white'>"
						+ "click to start new game"
					+ "</font></p>"
				+ "</div><HTML>");
		title_text.setFont(new Font("Sansserif", Font.PLAIN, 42));
		add(title_text);
		
		title_frame.add(this);
		title_frame.setVisible(true);
		
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				title_frame.dispose();
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
