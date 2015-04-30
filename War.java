/**
   Tommy Ernst
   CS 110
   War Game
*/

import java.util.ArrayList;
import javax.swing.*;
   
public class War
{
   private QueueReferenceBased playerOneHand = new QueueReferenceBased(); //queue for player one
   private QueueReferenceBased playerTwoHand = new QueueReferenceBased(); //queue for player two
   
   private ArrayList<Card> warCardsTotal = new ArrayList<Card>();         //array that holds all 
                                                                          //cards on the table
 
   private int INITIAL_HAND = 25;               //number of cards dealt to each player
   
   private Card playerOneCard;                  //player one card
   private Card playerTwoCard;                  //player two card
   
   private String outcome;                      //outcome of each turn for GUI
      
   /**
      War constructor
      
      creates a deck and shuffles it
   */
   public War()
   {
      // new deck, shuffle
      Deck deck = new Deck();
      deck.shuffle();
      
      deal(deck);
   }
   
   /**
      deal method
      Each initial hand has 26 cards, deck is shuffled, cards are enqueued one at a time.
      
      @param theDeck, the deck created in the constructor
   */
   
   public void deal(Deck theDeck)
   {
      //loop to deal cards
      for (int i = 0; i < INITIAL_HAND; i++)
      {
         playerOneHand.enqueue(theDeck.dealCard());
         playerTwoHand.enqueue(theDeck.dealCard());
      }
   }
   
   /**
      playRound method
      
      plays one round of the game
      
      @return p1WIN if player one wins
              p2WIN if player two wins
              WAR if cards are equal
   */
   
   public String playRound(Card playerOneCard, Card playerTwoCard)
   {
    
      //if player one wins
      if (playerOneCard.getRank() > playerTwoCard.getRank())
      {
         //add both cards to player one
         playerOneHand.enqueue(playerOneCard);
         playerOneHand.enqueue(playerTwoCard);
         
          //add cards from warCardsTotal, if any
         int totalSize = warCardsTotal.size();
         for (int i = 0; i < totalSize; i++)
         {
            playerOneHand.enqueue(warCardsTotal.remove(0));
         }
         
         //print out results for command window
         System.out.println("Player One card: " + playerOneCard.toString());
         System.out.println("Player Two card: " + playerTwoCard.toString());
         System.out.println("Player One wins!\n");
         
         System.out.println("Player One has " + playerOneHand.getSize() + " cards.");
         System.out.println("Player Two has " + playerTwoHand.getSize() + " cards.\n");
 
         //results for GUI
         outcome = ("Player One wins!");
         
        
         return "p1WIN";
      }
      
      //if player two wins
      else if (playerOneCard.getRank() < playerTwoCard.getRank())
      {
         //add both cards to player two's hand
         playerTwoHand.enqueue(playerOneCard);
         playerTwoHand.enqueue(playerTwoCard);   
         
          //add cards from warCardsTotal, if any
         int totalSize = warCardsTotal.size();
         for (int i = 0; i < totalSize; i++)
         {
            playerTwoHand.enqueue(warCardsTotal.remove(0));
         }
         
         //print out results for command window
         System.out.println("Player One card: " + playerOneCard.toString());
         System.out.println("Player Two card: " + playerTwoCard.toString());
         System.out.println("Player Two wins!\n");
         
         System.out.println("Player One has " + playerOneHand.getSize() + " cards.");
         System.out.println("Player Two has " + playerTwoHand.getSize() + " cards.\n");
         
         //results for GUI
         outcome = ("Player Two wins!");

         
         return "p2WIN";
      }
      
      //else it is a war!
      else
      {
         //results for command window
         System.out.println("Player One card: " + playerOneCard.toString());
         System.out.println("Player Two card: " + playerTwoCard.toString());
         System.out.println("War!");
         
         //results for GUI
         outcome = ("WAR! Each player puts down three cards...");
         
         //go to tieWar method
         tieWar(playerOneCard, playerTwoCard);
         
         return "WAR";
      }
   }
      
   
   
   /**
      tieWar method
      
      each player draws three cards, the third is turned up to determine winner
   */
   
   public void tieWar(Card playerOneCard, Card playerTwoCard)
   {
   
   //draw two cards, both remain face down
      Card pOneHiddenCard1 = playerOneDraw();
      Card pTwoHiddenCard1 = playerTwoDraw();
      
      Card pOneHiddenCard2 = playerOneDraw();
      Card pTwoHiddenCard2 = playerTwoDraw();
   
   //add all cards on the table to the war total  
      warCardsTotal.add(playerOneCard);
      warCardsTotal.add(playerTwoCard);
      warCardsTotal.add(pOneHiddenCard1);
      warCardsTotal.add(pTwoHiddenCard1);
      warCardsTotal.add(pOneHiddenCard2);
      warCardsTotal.add(pTwoHiddenCard2);
   
   //draw a third card for another round  
      Card playerOneWarCard = playerOneDraw();
      Card playerTwoWarCard = playerTwoDraw();
      
      String warRound = playRound(playerOneWarCard, playerTwoWarCard);
      
      //if player one wins
      if (warRound == "p1WIN")
      {
         //results for command window
         System.out.println("WAR!\nPlayer one wins!\n");
         //results for GUI
         outcome = ("WAR! Player One wins!");
      }
      
      //if player two wins
      if (warRound == "p2WIN")
      {
         //results for command window
         System.out.println("WAR!\nPlayer two wins!\n");
         //results for GUI
         outcome = ("WAR! Player Two wins!");

      }
      
   }
   
   /**
      getOutcome method
      
      @return the outcome of each hand
   */
   
   public String getOutcome()
   {
      return outcome;
   }
   
   /**
      playerOneDraw method
      
      @return the top card from player ones hand
   */
   
   public Card playerOneDraw()
   {
      //if hand is empty, end game
      if (playerOneHand.isEmpty())
      {
         endGame("one");
      }
      return (Card)playerOneHand.dequeue();
   }
   
    /**
      playerTwoDraw method
      
      @return the top card from player twos hand
   */
   
   public Card playerTwoDraw()
   {
      //if hand is empty, end game
      if (playerTwoHand.isEmpty())
      {
         endGame("two");
      }
      return (Card)playerTwoHand.dequeue();
   }
   
   /**
      getPlayerOneCardsLeft method
      @return number of cards player one has left
   */
   
   public int getPlayerOneCardsLeft()
   {
      //if hand is empty, end game
      if (playerOneHand.isEmpty())
      {
         endGame("two");
      }
      return playerOneHand.getSize();
   }
      
   /**
      getPlayerTwoCardsLeft method
      @return number of cards player one has left
   */
   
   public int getPlayerTwoCardsLeft()
   {
      //if hand is empty, end game
      if (playerTwoHand.isEmpty())
      {
         endGame("two");
      }
      return playerTwoHand.getSize();
   }
   
   /**
      getCurrentPlayerOneCard
      @return the card in play for player one
   */
   
   public Card getCurrentPlayerOneCard()
   {
      return playerOneCard;
   }
   
   /**
      getCurrentPlayerTwoCard
      @return the card in play for player two
   */
   
   public Card getCurrentPlayerTwoCard()
   {
      return playerTwoCard;
   }
   
    /**
      getPlayerOneImage
      @return the image in play for player two
   */
   
   public String getPlayerOneImage()
   {
      return playerOneCard.getImageName();
   }
   
   /**
      getPlayerTwoImage
      @return the image in play for player two
   */
   
   public String getPlayerTwoImage()
   {
      return playerTwoCard.getImageName();
   }
      
      
   /** endGame method
   
      prints out statement showing who has won the game
   */
   public void endGame(String player)
   {
      //if player one wins (player two has zero cards)
      if (player.equals("two"))
      {
         //results for command window
         System.out.println("Player One has all the cards. Game over! Player One wins.");
         //results for GUI
         outcome = ("Player One has all the cards. Game over! Player One wins.");
      }
      
      //if player two wins (player one has zero cards)
      if (player.equals("one"))
      {
         //results for command window
         System.out.println("Player Two has all the cards. Game over! Player Two wins.");
         //results for GUI
         outcome = ("Player Two has all the cards. Game over! Player Two wins.");
      }
   } 
      
}      