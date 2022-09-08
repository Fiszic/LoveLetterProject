import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;

public class Card  extends Deck{
	private BufferedImage CardImage;
	private String CardName;
	private int number;
	public Card(String cn, int n, BufferedImage CI) {
		CardName = cn;
		number = n;
		CardImage = CI;
	}
	public int getNumber() {
		return number;
	}
	public BufferedImage getImage() {
		return CardImage;
	}
}
