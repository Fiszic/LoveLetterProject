import java.util.*;

public class Player {
	private String name;
	private int coordx, coordy;
	private ArrayList<Card> hand;
	private int tokens;
	private boolean inGame, immunity;
	private ArrayList<Card> discard;
	public Player(String n/*, Card c, int x, int y*/) {
		hand = new ArrayList<>();
		name = n;
		inGame = true;
		tokens = 0;
		discard = new ArrayList<>();
		immunity = false;
		//coordx = x;
		//coordy=y;
		
	}
	/*public int getX() {
		return coordx;
	}
	public int getY() {
		return coordy;
	}*/
	public String getName() {
		return name;
	}
	public void drawCard(Card c) {
		hand.add(c);
		//System.out.println(hand.get(0).getName() + hand.get(1).getName());
	}
	public void addToken() {
		tokens++;
	}
	public void discardCard(int i) {
		Card c = hand.remove(i);
		if(c.getCardNumber() == 9) {
			lose();
		}
		discard.add(c);
	}
	public boolean isInGame() {
		return inGame;
	}
	public int getTokens() {
		return tokens;
	}
	public void lose() {
		inGame = false;
		discard.add(hand.get(0));
		immunityTrue();
	}
	public int getHandAmmount() {
		return hand.size();
	}
	public Card getHandCard(int i) {
		return hand.get(i);
	}
	public ArrayList<Card> getDiscard(){
		return discard;
	}
	public void addDiscardCardDebug(Card c) {
		discard.add(c);
	}
	public void immunityTrue() {
		immunity = true;
	}
	public void immunityFalse() {
		immunity = false;
	}
	public boolean immune() {
		return immunity;
	}
	public void swapHand(Card c) {
		hand.add(c);
		hand.remove(0);
	}
	public int getSpyUsedCount() {
		int count = 0;
		for(int i = 0; i<discard.size();i++) {
			if(discard.get(i).getCardNumber() == 0) {
				count ++;
			}
		}
		return count;
	}
	public void reset() {
		hand.clear();
		discard.clear();
		inGame = true;
		immunityFalse();
	}
	public void removeCard(int i) {
		hand.remove(i);
	}
}
