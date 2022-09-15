import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class Deck extends JPanel implements MouseListener{
	private ArrayList<BufferedImage> listofimage= new ArrayList<>();
	private ArrayList<Card> cards = new ArrayList<>();
	private ArrayList<Card> deck = new ArrayList<>();
	private BufferedImage cardback; 
	private BufferedImage spypng, gaurdpng, priestpng, baronpng, handmaidpng, princepng, chancellorpng, kingpng, countesspng,princesspng;
	private Card spy, gaurd, priest, baron, handmaid, prince, chancellor, king, countess, princess;
	private int tempcount;
	private boolean hasdrawn;
	private int count;
	public Deck() {
		
		
		listofimage = new ArrayList<BufferedImage>();
		cards = new ArrayList<Card>();
		deck = new ArrayList<Card>();
		hasdrawn = false;
		count = -1;
		try {
			cardback = ImageIO.read(Deck.class.getResource("CardBack.png"));
			spypng =ImageIO.read(Deck.class.getResource("Spy-0.png")) ;
			listofimage.add(spypng);
			gaurdpng = ImageIO.read(Deck.class.getResource("Gaurd-1.png"));
			listofimage.add(gaurdpng);
			priestpng = ImageIO.read(Deck.class.getResource("Priest-2.png"));
			listofimage.add(priestpng);
			baronpng = ImageIO.read(Deck.class.getResource("Baron-3.png"));
			listofimage.add(baronpng);
			handmaidpng = ImageIO.read(Deck.class.getResource("Handmaid-4.png"));
			listofimage.add(handmaidpng);
			princepng = ImageIO.read(Deck.class.getResource("Prince-5.png"));
			listofimage.add(princepng);
			chancellorpng = ImageIO.read(Deck.class.getResource("Chancellor-6.png"));
			listofimage.add(chancellorpng);
			kingpng = ImageIO.read(Deck.class.getResource("King-7.png"));
			listofimage.add(kingpng);
			countesspng = ImageIO.read(Deck.class.getResource("Chancellor-6.png"));
			listofimage.add(countesspng);
			princesspng = ImageIO.read(Deck.class.getResource("Princess-9.png"));
			listofimage.add(princesspng);
		}
		catch(Exception E) {
			System.out.println("Exception Error");
			E.printStackTrace();
			return;
		}
		spy = new Card("Spy", 0, 2);
		cards.add(spy);
		gaurd = new Card("Gaurd", 1 , 5);
		cards.add(gaurd);
		priest = new Card("Priest", 2 , 2);
		cards.add(priest);
		baron = new Card("Baron", 3 , 2);
		cards.add(baron);
		handmaid = new Card("Handmaid", 4, 2);
		cards.add(handmaid);		
		prince = new Card("Prince", 5, 2);
		cards.add(prince);
		chancellor = new Card("Chancellor", 6, 2);
		cards.add(chancellor);
		king = new Card("King", 7, 1);
		cards.add(king);
		countess = new Card("Countess", 8, 1);
		cards.add(countess);
		princess = new Card("Princess", 9, 1);
		cards.add(princess);
		for(int i = 0; i<cards.size();i++){
			int temp = cards.get(i).getNumber();
			for(int j = 0; j <temp; j++){
				deck.add(cards.get(i));
			}
		}
		for(int i = 0; i<3; i++) {
			p =new Player("Player" + i);
			players.add(p);
		}
		Collections.shuffle(deck);
		addMouseListener(this);
	}
	public void startGame() {
		gamestarted = true;
		
		deck.remove(0);
		
	}
	public void paint(Graphics g) {
		if(deck.size()>-0) {
			g.drawImage(cardback, 8*getWidth()/20, 2*getHeight()/20, 3*getWidth()/20, 5*getHeight()/20, null);
		}
		g.setColor(Color.RED);
		g.fillRect(13*getWidth()/20-10, 0, 3*getWidth()/20+20, 5*getHeight()/20+10);
		g.drawImage(cardback, 13*getWidth()/20, 0, 3*getWidth()/20, 5*getHeight()/20, null);
		if(hasdrawn && count<deck.size()) {
			g.drawImage(getImage(deck.get(count).getCardNumber()), 0, 0, 3*getWidth()/20, 5*getHeight()/20, null);
		}
		
	public BufferedImage getImage(int i) {
		return listofimage.get(i);
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
			if(x > 8*getWidth()/20&&  y >2*getHeight()/20&& x < 8*getWidth()/20+3*getWidth()/20 && y< 2*getHeight()/20+5*getHeight()/20) {
				count++;
				hasdrawn = true;
			}
		}
		repaint();
	}
}
