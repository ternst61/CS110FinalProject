/**
Tommy Ernst
CS 110
War Game
*/

import java.util.ArrayList;
import javax.swing.*;
   
public class War
{
   private QueueReferenceBased playerOneHand = new QueueReferenceBased();
   private QueueReferenceBased playerTwoHand = new QueueReferenceBased();
   
   private ArrayList<Card> warCardsTotal = new ArrayList<Card>();
   
   private int INITIAL_HAND = 26;
   
   private Card playerOneCard;
   private Card playerTwoCard;
   
   
   public War()
   {
      Deck deck = new Deck();
      deck.shuffle();
      
      deal(deck);
   }
   
   /**
      deal method
      Each initial hand will have 26 cards, deck is shuffled and cards are enqueued one at a time.
      
      @param theDeck, the deck created in the constructor
   */
   
   public void deal(Deck theDeck)
   {
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
   
      //Card playerOneCard = playerOneDraw();
      //Card playerTwoCard = playerTwoDraw(); 
      
      if (playerOneCard.getRank() > playerTwoCard.getRank())
      {
         playerOneHand.enqueue(playerOneCard);
         playerOneHand.enqueue(playerTwoCard);
         
          //add cards from warCardsTotal, if any
         int totalSize = warCardsTotal.size();
         for (int i = 0; i < totalSize; i++)
         {
            playerOneHand.enqueue(warCardsTotal.remove(0));
         }
         
         
         System.out.println("Player One card: " + playerOneCard.toString());
         System.out.println("Player Two card: " + playerTwoCard.toString());
         System.out.println("Player One wins!\n");
         System.out.println("Player One has " + playerOneHand.getSize() + " cards.");
         System.out.println("Player Two has " + playerTwoHand.getSize() + " cards.\n");

         
        
         return "p1WIN";
      }
      
      else if (playerOneCard.getRank() < playerTwoCard.getRank())
      {
         playerTwoHand.enqueue(playerOneCard);
         playerTwoHand.enqueue(playerTwoCard);   
         
          //add cards from warCardsTotal, if any
         int totalSize = warCardsTotal.size();
         for (int i = 0; i < totalSize; i++)
         {
            playerTwoHand.enqueue(warCardsTotal.remove(0));
         }
         
         System.out.println("Player One card: " + playerOneCard.toString());
         System.out.println("Player Two card: " + playerTwoCard.toString());
         System.out.println("Player Two wins!\n");
         System.out.println("Player One has " + playerOneHand.getSize() + " cards.");
         System.out.println("Player Two has " + playerTwoHand.getSize() + " cards.\n");
         
         
         
         return "p2WIN";
      }
      
      else
      {
         System.out.println("Player One card: " + playerOneCard.toString());
         System.out.println("Player Two card: " + playerTwoCard.toString());
         System.out.println("War!");
         
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
      
      if (warRound == "p1WIN")
      {
         System.out.println("Player one wins!");
      }
      
      if (warRound == "p2WIN")
      {
         System.out.println("Player two wins!");
      }
      
   }
   
   /**
      playerOneDraw method
      
      @return the top card from player ones hand
   */
   
   public Card playerOneDraw()
   {
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
      return playerOneHand.getSize();
   }
      
   /**
      getPlayerTwoCardsLeft method
      @return number of cards player one has left
   */
   
   public int getPlayerTwoCardsLeft()
   {
      return playerTwoHand.getSize();
   }
      
   /** endGame method
   
      prints out statement showing who has won the game
   */
   public void endGame(String player)
   {
      if (player.equals("two"))
      {
         System.out.println("Player One has all the cards. Game over! Player One wins.");
      }
      
      if (player.equals("one"))
      {
         System.out.println("Player Two has all the cards. Game over! Player Two wins.");
      }
   }
         
  
  
  
  
  
  
  
  
      
}
      
      

      
      