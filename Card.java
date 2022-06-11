
public class Card {
	private String number;
	private String suite;
	
	public Card() {
		String[] numbers = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
		String[] suites = {"Heart","Spade","Clover","Diamond"};
		int numbersRandIndex = (int) (Math.random()*numbers.length);
		int suitesRandIndex = (int) (Math.random()*suites.length);
		this.number = numbers[numbersRandIndex];
		this.suite = suites[suitesRandIndex];
	}
	
	public String getNumber() {
		return number;
	}
	
	public String getSuite() {
		return suite;
	}
	
	public int getNumericalNumber() {
		if (number.equals("A")) {
			return 14;
		}
		if (number.equals("K")) {
			return 13;
		}
		if (number.equals("Q")) {
			return 12;
		}
		if (number.equals("J")) {
			return 11;
		}
		return Integer.parseInt(number);
	}
	
	public int compareTo(Card other) {
		int otherNumerical = other.getNumericalNumber();
		int thisNumerical = this.getNumericalNumber();
		if (otherNumerical > thisNumerical) {
			return -1;
		} else if (otherNumerical == thisNumerical) {
			return 0;
		}
		return 1;
	}
	
	public boolean isEqualTo(Card other) {
		return compareTo(other) == 0 && other.getSuite().equals(suite);
	}
	
	@Override
	public String toString() {
		return number + " of " + suite;
	}
}
