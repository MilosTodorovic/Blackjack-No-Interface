package blackjack;

import domaci6.Deck;
import domaci6.Card;
import highlow.TextIO;

public class Blackjack {
   
    /** Let the user play one game of Blackjack, with the computer as dealer.
     * @return true if the user wins the game, false if the user loses
     */
    static boolean playBlackjack() {
        Deck deck;                // A deck of cards. A new deck for each game
        BlackjackHand dealerHand; // The dealer's hand
        BlackjackHand playerHand;   // The user's hand
        char playerAction;
                
        deck = new Deck();
        dealerHand = new BlackjackHand();
        playerHand = new BlackjackHand();
        
        /* Shuffle the deck then deal 2 cards to each player */
        
        deck.shuffle();
        dealerHand.addCard(deck.dealCard());
        dealerHand.addCard(deck.dealCard());
        playerHand.addCard(deck.dealCard());
        playerHand.addCard(deck.dealCard());
        
        System.out.println("");
        System.out.println("");
        
        /* Check if one of the players has Blackjack (two cards totaling 21).
            The player with Blackjack wins the game. Dealer wins ties.
        */
        //
        
        if (dealerHand.getBlackjackValue() == 21) {
            System.out.println("Dealer has the " + dealerHand.getCard(0) 
                                    + " and the " + dealerHand.getCard(1) + ".");
            System.out.println("Player has the " + playerHand.getCard(0) 
                                    + " and the " + playerHand.getCard(1) + ".");
            System.out.println("");
            System.out.println("Dealer has blackjack. Dealer wins.");
            
            return false;
        }
        
        if (playerHand.getBlackjackValue() == 21) {
            System.out.println("Dealer has the " + dealerHand.getCard(0) 
                                    + " and the " + dealerHand.getCard(1) + ".");
            System.out.println("Player has the " + playerHand.getCard(0) 
                                    + " and the " + playerHand.getCard(1) + ".");
            System.out.println("");
            System.out.println("You have Blackjack. You win.");
            
            return true;
        }
        
        /* If neither player has Blackjack, play the game. First the player
            gets a chance to draw cards (i.e. to "Hit"). The while loop ends
            when the player chooses to "Stand". If the player goes over 21,
            the player looses immediately.
        */
        
        while (true) {
             
            /* Display the player's cards, and let the player decide to Hit ot Stand */
            
            System.out.println("");
            System.out.println("");
            System.out.println("Your cards are: ");
            for (int i = 0; i < playerHand.getCardCount(); i++) 
                System.out.println("   " + playerHand.getCard(i));
            System.out.println("Your total is " + playerHand.getBlackjackValue());
            System.out.println("");
            System.out.println("Dealer is showing the " + dealerHand.getCard(0));
            System.out.println("");
            System.out.println("Hit (H) or Stand (S)?");
            do {
                playerAction = Character.toUpperCase(TextIO.getlnChar());
                if (playerAction != 'H' && playerAction != 'S')
                    System.out.println("Please respomd with H or S");
            } while (playerAction != 'H' && playerAction != 'S');
            
            /* If the player Hits, the player gets a card. If the player 
                Stands the loop ends (and it's the dealer's turn to draw cards)
            */
            if (playerAction == 'S') {
                // Loop ends; player is dont taking cards
                break;
            }
            else {  // Player's action is 'H'. Give the player a card
                    // If the player goes over 21, the player looses.
                Card newCard = deck.dealCard();
                playerHand.addCard(newCard);
                System.out.println("");
                System.out.println("User hits");
                System.out.println("Your card is the " + newCard);
                System.out.println("Your total is now " + playerHand.getBlackjackValue());
                if (playerHand.getBlackjackValue() > 21) {
                    System.out.println("");
                    System.out.println("You busted by going over 21. You lose");
                    System.out.println("Dealer's other card was the " + dealerHand.getCard(1));
                    
                    return false;
                }
            }
        }   // End while loop
        
        /* If we get to this point, the player has stod with 21 or less. Now it's
            the dealer's chance to draw. Dealer draws cards until the dealer's 
            total is > 16. If dealer goes over 21, the dealer loses.
        */
        
        System.out.println("");
        System.out.println("User stand");
        System.out.println("Dealer's cards are");
        System.out.println("   " + dealerHand.getCard(0));
        System.out.println("   " + dealerHand.getCard(1));
        while (dealerHand.getBlackjackValue() <= 16) {
            Card newCard = deck.dealCard();
            System.out.println("Dealer hits and gets the " + newCard);
            dealerHand.addCard(newCard);
            if (dealerHand.getBlackjackValue() > 21) {
                System.out.println("");
                System.out.println("Dealer busted by going over 21. You win");
                return true;
            }
        }
        System.out.println("Dealer's total is " + dealerHand.getBlackjackValue());
        
        /* If we get to this point both players have either 21 or less. We
            can determine the winner by comparing the values of their hands  */
        System.out.println("");
        if (dealerHand.getBlackjackValue() == playerHand.getBlackjackValue()) {
            System.out.println("Dealer wins on a tie. You lose");
            return false;
        }
        else if (dealerHand.getBlackjackValue() > playerHand.getBlackjackValue()) {
            System.out.println("Dealer wins, " + dealerHand.getBlackjackValue() 
                                    + " to " + playerHand.getBlackjackValue() + ".");
            return false;
        }
        else {
            System.out.println("You win " + playerHand.getBlackjackValue() 
                                    + " to " + dealerHand.getBlackjackValue() + ".");
            return true;
        }
    }   // End playBlackjak
    
     public static void main(String[] args) {
         
         int money;        // Amount of money the user has
         int bet;          // Amount player bets on the game
         boolean playerWins; // Did the user win the game?
         
         System.out.println("Welcome to the game of blackjack");
         System.out.println("");
         
         money = 100;   // Player starts with a $100
         
         while(true) {
             System.out.println("You have " + money + " dollars");
             do {
                 System.out.println("How much do you want to bet on this hand?  (enter 0 to end)");
                 System.out.println("?");
                 bet = TextIO.getlnInt();
                 if (bet < 0 || bet > money) 
                     System.out.println("Your bet must be between 0 and " + money + ".");
             } while (bet < 0 || bet > money);
             if (bet == 0)
                 break;
             playerWins = playBlackjack();
             if (playerWins) 
                 money = money + bet;
             else 
                 money = money - bet;
             System.out.println("");
             if (money == 0) {
                 System.out.println("Looks like you'we ran out of money. GG");
                 break;
             }
         }
         
         System.out.println("");
         System.out.println("You left with $" + money + ".");
    } // End main
    
}
