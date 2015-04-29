/**
   Tommy Ernst
   CS110
   War
*/

public class WarDriver
{
   public static void main(String [] args)
   {
      War newGame = new War();
      
      while (true)
      {
         Card playerOneCard = newGame.playerOneDraw();
         Card playerTwoCard = newGame.playerTwoDraw();
      
         newGame.playRound(playerOneCard, playerTwoCard);
      }
   }
   
}