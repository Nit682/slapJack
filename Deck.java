import java.util.ArrayList;

public class Deck {
	private ArrayList<Card> cardList;
	private int deckNumber;
	static ArrayList<Card> alreadyTakenCards;
	
	public Deck(int deckNumber, int initialDeckSize) {
		Deck.alreadyTakenCards = new ArrayList<Card>();
		
		this.deckNumber = deckNumber;
		cardList = new ArrayList<Card>();
		for (int i = 0; i < initialDeckSize; i++) {
			Card randCard = new Card();
			if (existsInAlreadyTakenCards(randCard) || existsInDeck(randCard)) {
				i--;
			} else {
				cardList.add(randCard);
				Deck.alreadyTakenCards.add(randCard);
			}
		}
		
	}
	
	private boolean existsInAlreadyTakenCards(Card c) {
		for (Card card : Deck.alreadyTakenCards) {
			if (card != null) {
				if (card.isEqualTo(c)) {
					return true;
				}
			}
		}
		return false;	
	}
	
	private boolean existsInDeck(Card c) {
		for (Card card : cardList) {
			if (card != null) {
				if (card.isEqualTo(c)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void printDeck() {
		for (Card card : cardList) {
			System.out.println(card);
		}
	}
	
	public Card drawCard() {
		return cardList.remove(cardList.size()-1);
	}
	
	public void addCardsToDeck(ArrayList<Card> list) {
		for (Card card : list) {
			cardList.add(0,card);
		}
	}
	
	public int getSizeOfDeck() {
		return cardList.size();
	}
	
	@Override
	public String toString() {
		return "Cards in " + deckNumber + ": " + cardList.size();
	}
}
