import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.util.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class Deck extends JPanel implements MouseListener{
	private ArrayList<BufferedImage> listofimage;
	private ArrayList<Card> cards;
	private ArrayList<Card> deck;
	private BufferedImage cardback, referenceCard, brokenSealCard, token; 
	private BufferedImage spypng, gaurdpng, priestpng, baronpng, handmaidpng, princepng, chancellorpng, kingpng, countesspng,princesspng;
	private Card spy, gaurd, priest, baron, handmaid, prince, chancellor, king, countess, princess, playedcard, gaurdCardSelect;
	private boolean hasdrawn, gamestarted, selectPlayer,  chancellorturn, princePlayerSelect, selectCard, showCard, gameEnd, playerSelected;
	private int count, chancellorDraw, chancellorRemove;
	private ArrayList<Player> players, winner;
	private Player p;
	private ArrayList<String> logmessages;
	
	public Deck() {
		gameEnd = false;
		winner = new ArrayList<>();
		logmessages = new ArrayList<>();
		players = new ArrayList<>();
		listofimage = new ArrayList<BufferedImage>();
		cards = new ArrayList<Card>();
		deck = new ArrayList<Card>();
		count = -1;
		try {
			token = ImageIO.read(Deck.class.getResource("/Images/LoveLetterToken.png"));
			brokenSealCard = ImageIO.read(Deck.class.getResource("/Images/BrokenSealCard .png"));
			referenceCard= ImageIO.read(Deck.class.getResource("/Images/ReferenceCard.png"));
			cardback = ImageIO.read(Deck.class.getResource("/Images/CardBack.png"));
			spypng =ImageIO.read(Deck.class.getResource("/Images/Spy-0.png")) ;
			listofimage.add(spypng);
			gaurdpng = ImageIO.read(Deck.class.getResource("/Images/Gaurd-1.png"));
			listofimage.add(gaurdpng);
			priestpng = ImageIO.read(Deck.class.getResource("/Images/Priest-2.png"));
			listofimage.add(priestpng);
			baronpng = ImageIO.read(Deck.class.getResource("/Images/Baron-3.png"));
			listofimage.add(baronpng);
			handmaidpng = ImageIO.read(Deck.class.getResource("/Images/Handmaid-4.png"));
			listofimage.add(handmaidpng);
			princepng = ImageIO.read(Deck.class.getResource("/Images/Prince-5.png"));
			listofimage.add(princepng);
			chancellorpng = ImageIO.read(Deck.class.getResource("/Images/Chancellor-6.png"));
			listofimage.add(chancellorpng);
			kingpng = ImageIO.read(Deck.class.getResource("/Images/King-7.png"));
			listofimage.add(kingpng);
			countesspng = ImageIO.read(Deck.class.getResource("/Images/Countess-8.png"));
			listofimage.add(countesspng);
			princesspng = ImageIO.read(Deck.class.getResource("/Images/Princess-9.png"));
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
		for(int i = 0; i<3; i++) {
			p =new Player("Player" + i);
			players.add(p);
		}
		Collections.shuffle(players);
		addMouseListener(this);
		startGame();
	}
	public void startGame() {
		for(int i = 0; i<cards.size();i++){
			int temp = cards.get(i).getNumber();
			for(int j = 0; j <temp; j++){
				deck.add(cards.get(i));
			}
		}
		for(int i = 0; i<players.size();i++) {
			players.get(i).reset();
		}
		playerSelected = false;
		showCard = false;
		selectCard = false;
		princePlayerSelect = false;
		selectPlayer = false;
		chancellorturn = false;
		chancellorDraw = 0;
		gamestarted = true;
		hasdrawn = false;
		Collections.shuffle(deck);
		deck.remove(0);
		System.out.println(players.size() + " " + deck.size());
		for(int i = 0; i<players.size(); i++) {
			players.get(i).drawCard(deck.remove(0));
		}
		repaint();
	}
	public void checkHandLegal() {
		Card hand0 = players.get(0).getHandCard(0);
		Card hand1 = players.get(0).getHandCard(1);
		if(hand0.equals(countess)&&(hand1.equals(king)||hand1.equals(prince))) {
			players.get(0).discardCard(0);
			logmessages.add(0, players.get(0).getName() + " forced to discard countess");
			repaint();
			nextTurn();
		}
		else if(hand1.equals(countess)&&(hand0.equals(king)||hand0.equals(prince))) {
			players.get(0).discardCard(1);
			logmessages.add(0, players.get(0).getName() + " forced to discard countess");
			nextTurn();
		}
	}
	public void nextTurn() {
		System.out.println(deck.size());
		int playersInGame= 0;
		for(int i = 0; i<players.size();i++) {
			if(players.get(i).isInGame()) {
				playersInGame++;
			}
		}
		if(playersInGame <=1||deck.size()<=0) {
			endRound(playersInGame);
			return;
		}
		playerSelected = false;
		showCard = false;
		selectCard = false;
		princePlayerSelect = false;
		selectPlayer = false;
		chancellorturn = false;
		chancellorDraw = 0;
		hasdrawn = false;
		players.add(players.remove(0));
		if(players.get(0).immune()) {
			players.get(0).immunityFalse();
		}
		if(!players.get(0).isInGame()) {
			nextTurn();
		}
		repaint();
	}
	public void endRound(int ingame) {
		if(ingame <= 1) {
			for(int i = 0; i<players.size();i++) {
				if(players.get(i).immune()) {
					players.get(i).addToken();
				}
			}
		}
		int spycount0 = players.get(0).getSpyUsedCount();
		int spycount1 = players.get(1).getSpyUsedCount();
		int spycount2 = players.get(2).getSpyUsedCount();
		if(spycount0>=spycount1&&spycount0>=spycount2) {
			players.get(0).addToken();
		}
		if(spycount1>=spycount0&&spycount1>=spycount2) {
			players.get(1).addToken();
		}
		if(spycount2>=spycount0&&spycount2>=spycount1) {
			players.get(1).addToken();
		}
		if(players.get(0).getTokens()==5||players.get(1).getTokens()==5||players.get(2).getTokens()==5) {
			gameEnd = true;
			if(players.get(0).getTokens()==5) {
				winner.add(players.get(0));
			}
			if(players.get(1).getTokens()==5) {
				winner.add(players.get(1));
			}
			if(players.get(2).getTokens()==5) {
				winner.add(players.get(2));
			}
			repaint();
			return;
		}
		startGame();
	}
	public void playCard() {
		if(playedcard.equals(baron)) {
			selectPlayer = true;
			logmessages.add(0, players.get(0).getName() + " used baron");
		}
		else if(playedcard.equals(priest)) {
			selectPlayer = true;
			logmessages.add(0, players.get(0).getName() + " used priest");
		}
		else if(playedcard.equals(king)) {
			selectPlayer = true;
			logmessages.add(0, players.get(0).getName() + " used king");
		}
		else if(playedcard.equals(gaurd)) {
			logmessages.add(0, players.get(0).getName() + " used gaurd");
			selectPlayer = true;
			selectCard = true;
		}
		else if(playedcard.equals(handmaid)) {
			logmessages.add(0, players.get(0).getName() + " used handmaid");
			players.get(0).immunityTrue();
			nextTurn();
			return;
		}
		else if(playedcard.equals(prince)) {
			logmessages.add(0, players.get(0).getName() + " used prince");
			princePlayerSelect = true;
		}
		else if(playedcard.equals(spy)) {
			logmessages.add(0, players.get(0).getName() + " used spy");
			nextTurn();
			return;
		}
		else if(playedcard.equals(countess)) {
			logmessages.add(0, players.get(0).getName() + " used countess");
			nextTurn();
			return;
		}
		else if(playedcard.equals(chancellor)) {
			chancellorturn = true;
			logmessages.add(0, players.get(0).getName() + " used chancellor");
		}
		repaint();
	}
	public void playerselection() {
		if(playedcard.equals(king)) {
			Card pl0card = players.get(0).getHandCard(0);
			Card plxcard = p.getHandCard(0);
			players.get(0).swapHand(plxcard);
			p.swapHand(pl0card);
			nextTurn();
		}
		else if(playedcard.equals(baron)) {
			showCard = true;
			if(players.get(0).getHandCard(0).getCardNumber()>p.getHandCard(0).getCardNumber()){
				p.lose();
				logmessages.add(0, p.getName() + " lost this round");
			}
			else if(players.get(0).getHandCard(0).getCardNumber()<p.getHandCard(0).getCardNumber()){
				players.get(0).lose();
				logmessages.add(0, players.get(0).getName() + " lost this round");
			}
			repaint();
		}
		else if(playedcard.equals(gaurd)) {
			showCard = true;
		}
		else if(playedcard.equals(priest)) {
			showCard = true;
		}
		repaint();
	}
	public void gaurd() {
		if(gaurdCardSelect.equals(p.getHandCard(0))) {
			p.lose();
			logmessages.add(0, p.getName() + " lost this round");
		}
	}
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(new Color(130, 0, 0));
		g.fillRect(0,0,getWidth(), getHeight());
		//deck
		if(deck.size()>-0) {
			g.drawImage(cardback, 8*getWidth()/20, 2*getHeight()/20, 3*getWidth()/20, 6*getHeight()/20, null);
		}
		g.setColor(Color.ORANGE);
		//player in players.get(1)
		ArrayList<Card> discard1 = players.get(1).getDiscard();
		g.fillRect(6*getWidth()/10-10, 0, 3*getWidth()/20+20, 6*getHeight()/20+10);
		g.drawImage(cardback, 6*getWidth()/10, 0, 3*getWidth()/20, 6*getHeight()/20, null);
		g.setColor(Color.BLACK);
		if(players.get(1).isInGame()) {
			g.drawImage(referenceCard, 0, 0, 3*getWidth()/20, 6*getHeight()/20, null);
			g.drawImage(cardback, 0, 6*getHeight()/20, 3*getWidth()/20, 6*getHeight()/20,null);
			for(int i = 0; i<discard1.size();i++) {
				g.drawImage(getImage(discard1.get(i).getCardNumber()), 0, 12*getHeight()/20+i*getHeight()/20, 3*getWidth()/20, 6*getHeight()/20, null);
			}
		}
		else {
			g.drawImage(brokenSealCard, 0, 0, 3*getWidth()/20, 6*getHeight()/20, null);
			for(int i = 0; i<discard1.size();i++) {
				g.drawImage(getImage(discard1.get(i).getCardNumber()), 0, 6*getHeight()/20+i*getHeight()/20, 3*getWidth()/20, 6*getHeight()/20, null);
			}
		}
		for(int i = 0; i<players.get(1).getTokens(); i++) {
			g.drawImage(token, 3*getWidth()/20, (i+1)*getHeight()/20, getWidth()/20, getHeight()/20, null);
		}
		g.setColor(Color.MAGENTA);
		if(playerSelected) {
			if(p.equals(players.get(0))) {
				g.setColor(Color.YELLOW);
			}
		}
		g.drawString(players.get(1).getName(), 3*getWidth()/20, getHeight()/20);
		g.setColor(Color.MAGENTA);
		//player in players.get(2)
		ArrayList<Card> discard2 = players.get(2).getDiscard();
		if(players.get(2).isInGame()) {
			g.drawImage(referenceCard, 17*getWidth()/20, 0,  3*getWidth()/20, 6*getHeight()/20, null);
			g.drawImage(cardback, 17*getWidth()/20, 6*getHeight()/20, 3*getWidth()/20, 6*getHeight()/20,null);
			for(int i = 0; i<discard2.size();i++) {
				g.drawImage(getImage(discard2.get(i).getCardNumber()), 17*getWidth()/20, 12*getHeight()/20+i*getHeight()/20, 3*getWidth()/20, 6*getHeight()/20, null);
			}
		}
		else {
			g.drawImage(brokenSealCard, 17*getWidth()/20, 0,  3*getWidth()/20, 6*getHeight()/20, null);
			for(int i = 0; i<discard2.size();i++) {
				g.drawImage(getImage(discard2.get(i).getCardNumber()), 17*getWidth()/20, 6*getHeight()/20+i*getHeight()/20, 3*getWidth()/20, 6*getHeight()/20, null);
			}
		}
		for(int i = 0; i<players.get(2).getTokens(); i++) {
			g.drawImage(token, 16*getWidth()/20, (i+1)*getHeight()/20, getWidth()/20, getHeight()/20, null);
		}
		if(playerSelected) {
			if(p.equals(players.get(0))) {
				g.setColor(Color.YELLOW);
			}
		}
		g.drawString(players.get(2).getName(), 16*getWidth()/20, getHeight()/20);
		g.setColor(Color.MAGENTA);
		
		//player in player(0), player's turn
		g.drawImage(referenceCard, 7*getWidth()/20, getHeight()-6 * getHeight()/20,  3*getWidth()/20, 6*getHeight()/20, null);
		for(int i  = 0; i< players.get(0).getHandAmmount();i++) {
			if(i<2) {
				g.drawImage(getImage(players.get(0).getHandCard(i).getCardNumber()), 10*getWidth()/20+i*(3*getWidth()/20), getHeight()-6 * getHeight()/20, 3*getWidth()/20, 6*getHeight()/20, null);
			}
			else {
				g.drawImage(getImage(players.get(0).getHandCard(i).getCardNumber()), 13*getWidth()/20, getHeight()-12 * getHeight()/20, 3*getWidth()/20, 6*getHeight()/20, null);
			}
		}
		g.drawString(players.get(0).getName(), 7*getWidth()/20, getHeight()-6 * getHeight()/20);
		for(int i = 0; i<players.get(0).getTokens(); i++) {
			g.drawImage(token, 16*getWidth()/20-(i+1)*getWidth()/20, getHeight()-7 * getHeight()/20, getWidth()/20, getHeight()/20, null);
		}
		ArrayList<Card> discard0 = players.get(0).getDiscard();
		for(int i = 0; i<discard0.size();i++) {
				g.drawImage(getImage(discard0.get(i).getCardNumber()), 4*getWidth()/20, 12*getHeight()/20+i*getHeight()/20, 3*getWidth()/20, 6*getHeight()/20, null);
		}
		g.drawString("Deck", 37*getWidth()/80, getHeight()/20);
		g.setColor(Color.RED);
		g.drawLine(4*getWidth()/20, 0, 4*getWidth()/20, getHeight());
		g.drawLine(16*getWidth()/20, 0, 16*getWidth()/20, getHeight());
		g.drawLine(4*getWidth()/20, 8*getHeight()/20, 16*getWidth()/20, 8*getHeight()/20);
		g.drawLine(11*getWidth()/20, 0, 11*getWidth()/20, 8*getHeight()/20);
		g.setColor(Color.CYAN);
		g.fillRect(4*getWidth()/20, 0, 4*getWidth()/20, 8*getHeight()/20);
		g.setColor(Color.BLACK);		
		g.drawRect(4*getWidth()/20, 0, 4*getWidth()/20, 8*getHeight()/20);
		if(selectCard) {
			g.drawRect(4*getWidth()/20, 0, 2*getWidth()/20, getHeight()/20);
			g.drawRect(4*getWidth()/20, getHeight()/20, 2*getWidth()/20, getHeight()/20);
			g.drawRect(4*getWidth()/20, 2*getHeight()/20, 2*getWidth()/20, getHeight()/20);
			g.drawRect(4*getWidth()/20, 3*getHeight()/20, 2*getWidth()/20, getHeight()/20);
			g.drawRect(4*getWidth()/20, 4*getHeight()/20, 2*getWidth()/20, getHeight()/20);
			g.drawRect(6*getWidth()/20, 0, 2*getWidth()/20, getHeight()/20);
			g.drawRect(6*getWidth()/20, getHeight()/20, 2*getWidth()/20, getHeight()/20);
			g.drawRect(6*getWidth()/20, 2*getHeight()/20, 2*getWidth()/20, getHeight()/20);
			g.drawRect(6*getWidth()/20, 3*getHeight()/20, 2*getWidth()/20, getHeight()/20);
			g.drawRect(6*getWidth()/20, 4*getHeight()/20, 2*getWidth()/20, getHeight()/20);
			g.drawString("Spy", 4*getWidth()/20, getHeight()/20);
			g.drawString("gaurd", 4*getWidth()/20, 2*getHeight()/20);
			g.drawString("priest", 4*getWidth()/20, 3*getHeight()/20);
			g.drawString("baron", 4*getWidth()/20, 4*getHeight()/20);
			g.drawString("handmaid", 4*getWidth()/20, 5*getHeight()/20);
			g.drawString("prince", 6*getWidth()/20, getHeight()/20);
			g.drawString("chancellor", 6*getWidth()/20, 2*getHeight()/20);
			g.drawString("king", 6*getWidth()/20, 3*getHeight()/20);
			g.drawString("countess", 6*getWidth()/20, 4*getHeight()/20);
			g.drawString("princess", 6*getWidth()/20, 5*getHeight()/20);
		}
		if(showCard&&!playedcard.equals(gaurd)) {
			g.drawImage(getImage(p.getHandCard(0).getCardNumber()), 4*getWidth()/20, 0, 3*getWidth()/20, 6*getHeight()/20, null);
		}
		if(!showCard &&!selectCard) {
			for(int i = 8; i>=0;i--) {
				if(i<logmessages.size()) {
					g.drawString(logmessages.get(i), 4*getWidth()/20, (8-i)*getHeight()/20);
				}
			}
		}
		if(gameEnd) {
			g.setColor(new Color(130, 0, 0));
			g.fillRect(0,0,getWidth(), getHeight());
			g.setColor(Color.BLACK);
			String temp = "";
			for(int i = 0; i<winner.size();i++) {
				if(i == 0) {
					temp+=winner.get(i).getName();
				}
				else {
					temp+=" and " + winner.get(i).getName();
				}
			}
			temp += " wins!!!";
			g.drawString(temp, 0, 0);
		}
		//deck
		//if(hasdrawn && count<deck.size()) {
			//g.drawImage(getImage(deck.get(count).getCardNumber()), 0, 0, 3*getWidth()/20, 6*getHeight()/20, null);
		//}
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
		if(e.getButton() == e.BUTTON1 && !gameEnd) {
			if(x > 8*getWidth()/20&&  y >2*getHeight()/20&& x < 8*getWidth()/20+3*getWidth()/20 && y< 2*getHeight()/20+5*getHeight()/20&&(!hasdrawn||chancellorturn)&&chancellorDraw<2&&!selectPlayer&&!selectCard&&!princePlayerSelect) {
				Card drawnCard = deck.remove(0);
				logmessages.add(0, players.get(0).getName() + " drew " + drawnCard.getName());
				players.get(0).drawCard(drawnCard);
				if(chancellorturn) {
					chancellorDraw++;
				}
				hasdrawn = true;
				repaint();
				if(!chancellorturn) {
					checkHandLegal();
				}
				System.out.println("deck size" + deck.size());
			}
			if(x>10*getWidth()/20&&y>getHeight()-6 * getHeight()/20&&x<13*getWidth()/20&&y<getHeight()&&hasdrawn&&!chancellorturn&&!selectPlayer&&!selectCard&&!princePlayerSelect) {
				if(!players.get(0).getHandCard(0).equals(princess)) {
					playedcard = players.get(0).getHandCard(0);
					players.get(0).discardCard(0);
					playCard();
				}
				
			}
			else if(x>13*getWidth()/20&&y>getHeight()-6 * getHeight()/20&&x<16*getWidth()/20&&y<getHeight()&&hasdrawn&&!chancellorturn&&!selectPlayer&&!selectCard&&!princePlayerSelect) {
				if(!players.get(0).getHandCard(1).equals(princess)) {
					playedcard = players.get(0).getHandCard(1);
					players.get(0).discardCard(1);
					playCard();
				}
				
			}
			if(selectPlayer) {
				if(x>3*getWidth()/20 && y>0&&x<4*getWidth()/20 && y<getHeight()/20&&!players.get(1).immune()&&players.get(1).isInGame()) {
					playerSelected = true;
					p = players.get(1);
					repaint();
					playerselection();
				}
				else if(x>16*getWidth()/20&&y>0&&x<17*getWidth()/20&&y<getHeight()/20&&!players.get(2).immune()&&players.get(1).isInGame()) {
					playerSelected = true;
					p = players.get(2);
					repaint();
					playerselection();
				}
				else if(players.get(1).immune() && players.get(2).immune()) {
					nextTurn();
				}
			}
			if(selectCard&&playerSelected) {
				if(x>4*getWidth()/20 && x<6*getWidth()/20&&y>0&&y<getHeight()/20) {
					gaurdCardSelect = spy;
					gaurd();
				}
				else if(x>4*getWidth()/20 && x<6*getWidth()/20&&y>getHeight()/20&&y<2*getHeight()/20) {
					gaurdCardSelect = gaurd;
					gaurd();
				}
				else if(x>4*getWidth()/20 && x<6*getWidth()/20&&y>2*getHeight()/20&&y<3*getHeight()/20) {
					gaurdCardSelect = priest;
					gaurd();
				}
				else if(x>4*getWidth()/20 && x<6*getWidth()/20&&y>3*getHeight()/20&&y<4*getHeight()/20) {
					gaurdCardSelect = baron;
					gaurd();
				}
				else if(x>4*getWidth()/20 && x<6*getWidth()/20&&y>4*getHeight()/20&&y<5*getHeight()/20) {
					gaurdCardSelect = handmaid;
					gaurd();
				}
				else if(x>6*getWidth()/20 && x<8*getWidth()/20&&y>0&&y<getHeight()/20) {
					gaurdCardSelect = prince;
					gaurd();
				}
				else if(x>6*getWidth()/20 && x<8*getWidth()/20&&y>getHeight()/20&&y<2*getHeight()/20) {
					gaurdCardSelect = chancellor;
					gaurd();
				}
				else if(x>6*getWidth()/20 && x<8*getWidth()/20&&y>2*getHeight()/20&&y<3*getHeight()/20) {
					gaurdCardSelect = king;
					gaurd();
				}
				else if(x>6*getWidth()/20 && x<8*getWidth()/20&&y>3*getHeight()/20&&y<4*getHeight()/20) {
					gaurdCardSelect = countess;
					gaurd();
				}
				else if(x>6*getWidth()/20 && x<8*getWidth()/20&&y>4*getHeight()/20&&y<5*getHeight()/20) {
					gaurdCardSelect = princess;
					gaurd();
				}
			}
			if(princePlayerSelect) {
				if(x>3*getWidth()/20 && y>0&&x<4*getWidth()/20 && y<getHeight()/20&&!players.get(1).immune()) {
					System.out.println("player0 " + players.get(0).getHandAmmount());
					players.get(0).discardCard(0);
					Card drawnCard = deck.remove(0);
					logmessages.add(0, players.get(0).getName() + " drew " + drawnCard.getName());
					players.get(0).drawCard(drawnCard);
					System.out.println("player0 " + players.get(1).getHandAmmount());
					repaint();
					nextTurn();
				}
				else if(x>16*getWidth()/20&&y>0&&x<17*getWidth()/20&&y<getHeight()/20&&!players.get(2).immune()) {
					System.out.println("player1 " + players.get(1).getHandAmmount());
					players.get(1).discardCard(0);
					Card drawnCard = deck.remove(0);
					logmessages.add(0, players.get(1).getName() + " drew " + drawnCard.getName());
					players.get(1).drawCard(drawnCard);
					System.out.println("player1 " + players.get(1).getHandAmmount());
					repaint();
					nextTurn();
				}
				else if(x>7*getWidth()/20&&x<8*getWidth()/20&&y< getHeight()-6 * getHeight()/20&& y>getHeight()-7 * getHeight()/20) {
					System.out.println("player2 " + players.get(1).getHandAmmount());
					players.get(2).discardCard(0);
					Card drawnCard = deck.remove(0);
					logmessages.add(0, players.get(2).getName() + " drew " + drawnCard.getName());
					players.get(2).drawCard(drawnCard);
					System.out.println("player2 " + players.get(1).getHandAmmount());
					repaint();
					nextTurn();
				}
			}
			if(chancellorDraw == 2 && players.get(0).getHandAmmount()>1) {
				if(x>10*getWidth()/20&&y>getHeight()-6 * getHeight()/20&&x<13*getWidth()/20&&y<getHeight()&&!players.get(0).getHandCard(0).equals(princess)) {
					deck.add(players.get(0).getHandCard(0));
					players.get(0).removeCard(0);
					repaint();
				}
				else if (x>13*getWidth()/20&&y>getHeight()-6 * getHeight()/20&&x<16*getWidth()/20&&y<getHeight()&&!players.get(0).getHandCard(1).equals(princess)) {
					deck.add(players.get(0).getHandCard(1));
					players.get(0).removeCard(1);
					repaint();
				}
				else if(x>13*getWidth()/20&& y>8 * getHeight()/20&& x<16*getWidth()/20&& y<14*getHeight()/20&&!players.get(0).getHandCard(2).equals(princess)) {
					deck.add(players.get(0).getHandCard(2));
					players.get(0).removeCard(2);
					repaint();
				}
				if(players.get(0).getHandAmmount()==1) {
					nextTurn();
				}
			}
			if(showCard) {
				if(x>4*getWidth()/20&& x<7*getWidth()/20&&y>0&&y<6*getHeight()/20) {
					nextTurn();
				}
			}
		}
	}
}
