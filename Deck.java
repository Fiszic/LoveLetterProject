import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class Deck extends JPanel implements MouseListener{
	private ArrayList<Card> cards = new ArrayList<>();
	private ArrayList<Card> deck = new ArrayList<>();
	private BufferedImage cardback; 
	private Card spy, gaurd, priest, baron, handmaid, prince, chancellor, king, countess, princess;
	private boolean hasdrawn;
	private int count;
	public Deck() {
		hasdrawn = false;
		count = -1;
		try {
			cardback = ImageIO.read(Deck.class.getResource("CardBack.png"));
			spy = new Card("Spy", 0, ImageIO.read(Deck.class.getResource("Spy-0.png")), 2);
			cards.add(spy);
			gaurd = new Card("Gaurd", 1 , ImageIO.read(Deck.class.getResource("Gaurd-1.png")), 5);
			cards.add(gaurd);
			priest = new Card("Priest", 2 , ImageIO.read(Deck.class.getResource("Priest-2.png")), 2);
			cards.add(priest);
			baron = new Card("Baron", 3 , ImageIO.read(Deck.class.getResource("Baron-3.png")), 2);
			cards.add(baron);
			handmaid = new Card("Handmaid", 4, ImageIO.read(Deck.class.getResource("Handmaid-4.png")), 2);
			cards.add(handmaid);
			prince = new Card("Prince", 5, ImageIO.read(Deck.class.getResource("Prince-5.png")), 2);
			cards.add(prince);
			chancellor = new Card("Chancellor", 6, ImageIO.read(Deck.class.getResource("Chancellor-6.png")), 2);
			cards.add(chancellor);
			king = new Card("King", 7, ImageIO.read(Deck.class.getResource("King-7.png")), 1);
			cards.add(king);
			countess = new Card("Countess", 8, ImageIO.read(Deck.class.getResource("Chancellor-6.png")), 1);
			cards.add(countess);
			princess = new Card("Princess", 9, ImageIO.read(Deck.class.getResource("Princess-9.png")), 1);
			cards.add(princess);
			
		}
		catch(Exception E) {
			System.out.println("Exception Error");
			E.printStackTrace();
			return;
		}
		for(int i = 0; i<cards.size();i++){
			int temp = cards.get(i).getNumber();
			for(int j = 0; j <temp; j++){
				deck.add(cards.get(i));
			}
		}
		Collections.shuffle(deck);
		addMouseListener(this);
	}
	public void paint(Graphics g) {
		g.drawImage(cardback, 0, getHeight()/5, 2*getWidth()/5, 3*getHeight()/5, null);
		if(hasdrawn && count<deck.size()) {
			g.drawImage(deck.get(count).getImage(), 3*getWidth()/5, getHeight()/5, 2*getWidth()/5, 3*getHeight()/5, null);
		}
	}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		System.out.println("loc is (" + x + "," + y + ")");
		if(e.getButton() == e.BUTTON1) {
			if(x >0 &&  y >getHeight()/5 && x < 2*getWidth()/5 && y< 3*getHeight()/5) {
				count++;
				hasdrawn = true;
			}
		}
		repaint();
	}
}
