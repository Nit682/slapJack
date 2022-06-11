import java.util.ArrayList;

public class PlayingTable {
	private ArrayList<Card> table;
	private ArrayList<Deck> playerPool;
	private int turn;
	private boolean deckEmptySoonAfterSlap;
	private int numberOfCardsUsedInGame;
	
	
	public PlayingTable(int playerCount) {
		//create playerCount number of decks but using cards from same 52 size deck
		playerPool = new ArrayList<Deck>();
		for (int i = 0; i < playerCount; i++) {
			playerPool.add(new Deck(i + 1, 52/playerCount));
		}
		
		table = new ArrayList<Card>();
		turn = 0;
		deckEmptySoonAfterSlap = false;
		
		//for debugging, show all decks at first
		printAllDeckSizes();
		System.out.println();
		
		//integer division may leave out some cards from being used when distributing the cards
		//for example, 3 players results in 51 cards being used instead of 52
		numberOfCardsUsedInGame = (52/playerCount)*playerCount;
	}
	
	public boolean isOneOfTheDecksEmptySoonAfterSlap() {
		return this.deckEmptySoonAfterSlap;
	}
	
	private void printAllDeckSizes() {
		int deckNumber = 1;
		for (Deck d : playerPool) {
			if (d == null) {
				System.out.println("Cards in " + deckNumber + ": 0");
			} else {
				System.out.println(d);
			}
			deckNumber++;
		}
	}
	
	public void makeNextPlay() throws InterruptedException {
		
		Card cardDrawn;
		Deck currDeck = playerPool.get(turn);
		//check if one of the decks is empty due to running out then turn must be skipped
		if (currDeck == null || currDeck.getSizeOfDeck() == 0) {
			turn++;
			this.wrapTurnIfNeeded();
			return;
		}
		
		cardDrawn = playerPool.get(turn).drawCard();
		
		table.add(cardDrawn);
		System.out.println("Player " + (turn + 1) + " placed a " + cardDrawn);
		
		//thread delay maintains game pace
		//Thread.sleep(1000);
		
		if (isValidSlap()) {
			System.out.println();
			System.out.println("Slap Detected!");
			
			
			int indexOfDeckThatTakesSlapWinnings = (int) ( Math.random() * playerPool.size());
			//if there is a slap, randomly choose which deck takes the cards
			while (indexDoesBelongToEliminatedPlayer(indexOfDeckThatTakesSlapWinnings)) {
				indexOfDeckThatTakesSlapWinnings = (int) ( Math.random() * playerPool.size());
			}
			
			//defaults deck that takes cards for testing purposes
			//indexOfDeckThatTakesSlapWinnings = 0;
			
			
			playerPool.get(indexOfDeckThatTakesSlapWinnings).addCardsToDeck(table);
			
			//print size of every deck in the pool
			this.printAllDeckSizes();
			
			table.clear();
			System.out.println();
			
			/*
			 * check if one of the decks has gone empty right after the slap so that they are 
			 * removed from the game
			*/
			for (int i = 0; i < playerPool.size(); i++) {
				if (playerPool.get(i) == null) {
					continue;
				} else if (playerPool.get(i).getSizeOfDeck() == 0) {
					playerPool.set(i, null);
					i--;
				}
			}
		}
		turn++;
		wrapTurnIfNeeded();
	}
	
	private boolean indexDoesBelongToEliminatedPlayer(int index) {
		return playerPool.get(index) == null;
	}
	
	//game can only be won if there is a deck with all the cards used in the game
	public boolean isGameWon() {
		for (int i = 0; i < playerPool.size(); i++) {
			if (playerPool.get(i) != null) {
				if (playerPool.get(i).getSizeOfDeck() != 0) {
					if (playerPool.get(i).getSizeOfDeck() == this.numberOfCardsUsedInGame) {
						System.out.println();
						System.out.println("Player " + (i + 1) + " won!");
						return true;
					}
					return false;
				}
			}
		}
		return false;
	}
	
	private void wrapTurnIfNeeded() {
		if (turn == playerPool.size()) {
			turn = 0;
		}
	}
	
	private boolean checkDouble() {
		if (table.size() < 2) {
			return false;
		}
		Card lastCard = table.get(table.size()-1);
		Card secondToLastCard = table.get(table.size()-2);
		return lastCard.getNumber().equals(secondToLastCard.getNumber());
	}
	
	private boolean checkMarraige() {
		if (table.size() < 2) {
			return false;
		}
		Card lastCard = table.get(table.size()-1);
		Card secondToLastCard = table.get(table.size()-2);
		return (lastCard.getNumber().equals("K") && secondToLastCard.getNumber().equals("Q")) ||
			   (lastCard.getNumber().equals("Q") && secondToLastCard.getNumber().equals("K"));
	}
	
	private boolean checkSandwich() {
		if (table.size() < 3) {
			return false;
		}
		Card lastCard = table.get(table.size()-1);
		Card thirdToLastCard = table.get(table.size()-3);
		return lastCard.getNumber().equals(thirdToLastCard.getNumber());
	}
	
	private boolean isValidSlap() {
		return checkSandwich() || checkMarraige() || checkDouble();
	}
	
	public boolean allDecksAreEmpty() {
		for (Deck deck : playerPool) {
			if (deck != null) {
				if (deck.getSizeOfDeck() > 0) {
					return false;
				}
			}
		}
		return true;
	}
}
