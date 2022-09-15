
public class Player {
	private String name;
	private int coordx, coordy;
	private Card[] hand;
	private int tokens;
	public Player(String n/*, Card c, int x, int y*/) {
		hand = new Card[2];
		hand[0] = null;
		hand[1] = null;
		name = n;
		//coordx = x;
		//coordy=y;
		tokens = 0;
	}
	public int getX() {
		return coordx;
	}
	public int getY() {
		return coordy;
	}
	public void drawCard(Card c) {
		if(hand[0] == null) {
			hand[0] = c;
		}
		else if(hand[1] == null) {
			hand[1] = c;
		}
		else {
			System.out.println("Hand is full");
		}
	}
	public void addToken() {
		tokens++;
	}
	public void useCard(int i) {
		hand[i] = null;
	}
}
