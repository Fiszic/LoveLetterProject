import java.awt.*;
import javax.swing.*;
public class DeckRunner extends JFrame{
	private static final int WIDTH = 1500;
	private static final int HEIGHT = 900;
	public DeckRunner(String title) {
		super(title);
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(new Deck());
		setVisible(true);
	}

}
