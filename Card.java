import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;

public class Card  extends Deck{
	private BufferedImage CardImage;
	private String CardName;
	private int number, CardNumber;
	public Card(String cn, int cnum, BufferedImage CI, int num) {
		CardName = cn;
		CardNumber = cnum;
		CardImage = CI;
		number =num;
	}
	public int getNumber() {
		return number;
	}
	public BufferedImage getImage() {
		return CardImage;
	}
	public int getCardNumber(){
		return CardNumber;
	}
	public String getName(){
		return CardName;
	}
}
