import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String args[]) throws InterruptedException, IOException {
		Scanner sc = new Scanner(System.in);
		
		char keepGameRunning = 'y';
		
		while (keepGameRunning == 'y') {
			//clear console here
			System.out.println();
			
			int playerCount = 0;
			String strInput;
			boolean validInput = false;
			
			while (!validInput) {
				System.out.print("Enter number of players: ");
				strInput = sc.nextLine();
				try {
					playerCount = Integer.parseInt(strInput);
					if (playerCount > 1 && playerCount <= 10) {
						validInput = true;
					} else {
						System.out.println("Error");
					}
				} catch (Exception e) {
					System.out.println("Error");
				}
			}
			
			PlayingTable game = new PlayingTable(playerCount);
			start(game);
			
			//let winner see that they won and clear console
			Thread.sleep(5000);
			System.out.println();
			
			//ask user if they want to play again
			validInput = false;
			strInput = "";
			while (!validInput) {
				System.out.print("Play again? y/n: ");
				strInput = sc.nextLine();
				try {
					keepGameRunning = strInput.charAt(0);
					if (keepGameRunning == 'y' || keepGameRunning == 'n') {
						validInput = true;
					} else {
						System.out.println("Error");
					}
				} catch (Exception e) {
					System.out.println("Error");
				}
			}			
		}
		
		//when game is not running and is over. Also, clear console
		System.out.println();
		System.out.println("Game over :(");
	}
	public static void start(PlayingTable game) throws InterruptedException {
		while (!game.isGameWon() && !game.allDecksAreEmpty()) {
			game.makeNextPlay();
		}
	}
}
