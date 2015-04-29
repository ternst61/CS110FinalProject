/**  
   Tommy Ernst
   CS110
   War Game GUI
*/
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.event.*;
import java.awt.*;

public class WarGUI extends JFrame
{
   private War game; // the guts
   
   private JPanel topPanel;
   private JPanel player1Panel; 
   private JPanel player2Panel;  // break up regions
   private JPanel bottomPanel;
   
   private JButton playButton;   //play button
   
   private JLabel status;  // game status
   private JLabel title;   // static title
   
   private ImageIcon cardBack;           // card back image
   private ImageIcon p1Card;      // player one current card image
   private ImageIcon p2Card;      // player two current card image
   
   private JLabel player1Score;    //player one score
   private JLabel player2Score;    //player two score
   
   private JLabel player1Deck;     //player one deck
   private JLabel player2Deck;     //player two deck
   
   private JLabel player1Card;     //player one card
   private JLabel player2Card;     //player two card

   
   
   
   private final int WIDTH = 800;   //width and height for GUI
   private final int HEIGHT = 500;
   
   /**
      WarGUI constructor
      
      builds the GUI box, sets layout, creates new game of war
   */
   public WarGUI()
   {
      //set layout
      setLayout(new BorderLayout());
      
      // create game instance
      game = new War();
      
      // setup containers and components
      setSize(WIDTH, HEIGHT);
      setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      validate();
      
      //get card image for back of card
      try
      {
         cardBack = new ImageIcon(ImageIO.read(new File("cards/back.jpg")));  
      }
      catch(IOException e)
      {
         System.err.println("card not found");
      } 
      
      //set title bar
      setTitle("A game of WAR");  
      
      //call buildPanels method
      buildPanels();
      
      //so we can see everything
      setVisible(true);
      
      //to keep everything updated
      update();
     
   }
   
   /**
      buildPanels method
      
      this method builds the top, bottom, player one and player two panels
   */
   private void buildPanels()
   {
      //create deck labels
      player1Deck = new JLabel();
      player2Deck = new JLabel();
      
      //create card labels
      player1Card = new JLabel();
      player2Card = new JLabel();
      
      //create score labels
      player1Score = new JLabel();
      player1Score.setFont(new Font("ARIAL",Font.BOLD,24));
 
      player2Score = new JLabel();
      player2Score.setFont(new Font("ARIAL",Font.BOLD,24));

      
      
      //create player one panel and add elements
      player1Panel = new JPanel();
      player1Panel.setPreferredSize(new Dimension(400,450));
      
      
      player1Panel.add(player1Score);
      player1Panel.add(player1Deck);
      player1Panel.add(player1Card);
      player1Panel.setBackground(Color.green);
      
      //create player two panel and add elements
      player2Panel = new JPanel();
      player2Panel.setPreferredSize(new Dimension(400,450));

      player2Panel.add(player2Score);
      player2Panel.add(player2Deck);
      player2Panel.add(player2Card);
      player2Panel.setBackground(Color.green);
      
      //create play button, add listener
      playButton = new JButton("Start Game!");
      
      playButton.addActionListener(new PlayCardListener());
      
      //create bottom panel, add elements
      bottomPanel = new JPanel();
      bottomPanel.add(playButton);
      bottomPanel.setBackground(Color.blue);
      bottomPanel.setPreferredSize(new Dimension(800,50));
      
      //create top panel, add elements
      topPanel = new JPanel();
      
      status = new JLabel();
      status.setFont(new Font("ARIAL",Font.BOLD,24));
     
      topPanel.add(status);
      topPanel.setBackground(Color.orange);
      topPanel.setPreferredSize(new Dimension(800,50));

      //add each panel to the GUI
      add(player1Panel, BorderLayout.WEST);
      add(player2Panel, BorderLayout.EAST);
      add(bottomPanel, BorderLayout.SOUTH);
      add(topPanel, BorderLayout.NORTH);
      
      //keep game updated
      update();
   }
    
   /**
      PlayCardListener class implements ActionListener
      
      plays game through, each turn controlled by play button
      also adds card images each turn
   */
         
   
   private class PlayCardListener implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         playButton.setText("Next Hand");
         {
            //draw cards
            Card playerOneCard = game.playerOneDraw();
            Card playerTwoCard = game.playerTwoDraw();
            
            //set player one image
            p1Card = new ImageIcon(playerOneCard.getImageName());
            player1Card.setIcon(p1Card);
            
            //set player two image
            p2Card = new ImageIcon(playerTwoCard.getImageName());
            player2Card.setIcon(p2Card);
            
            //play a round!
            game.playRound(playerOneCard, playerTwoCard);
         }
         //keep scores updated
         update();     
         
      }
   }
   
   /**
      update method
      
      calls the other update methods
   */
   public void update()
   {
      updateCardImages();
      updateOutcome();
      updateScores();
   }
   
   /**
      updates the back of the card image
      image goes away when there are no cards left
   */
   public void updateCardImages()
   {
      //player one card backs
      if (game.getPlayerOneCardsLeft() != 0)
      {
         player1Deck.setIcon(cardBack);
      }
      else
         {
            player1Deck.setIcon(null);
         }
      
      //player two card backs
      if (game.getPlayerTwoCardsLeft() != 0)
         {
            player2Deck.setIcon(cardBack);
         }
      else
         {
            player2Deck.setIcon(null);
         }
   }
   
   /**
      updateOutcome method
      
      gets outcome for each turn from the War class
  */
   public void updateOutcome()
   {
      //get outcome
      String outcome = game.getOutcome();
      //set outcome status
      status.setText(outcome);
   }
   
   /**
      updateScores method
      
      updates the score for each player, from the War class
   */
   public void updateScores()
   {
      //get player one score 
      int play1Score = game.getPlayerOneCardsLeft();
      
      //set text
      player1Score.setText(String.valueOf(play1Score));
      
      //get player two score
      int play2Score = game.getPlayerTwoCardsLeft();
      
      //set text
      player2Score.setText(String.valueOf(play2Score));
      //player2Score.setText(String.valueOf(game.getPlayerTwoCardsLeft()));
   } 
   
   
   
   
   //run the GUI
   public static void main (String[] args)
   {
      WarGUI game = new WarGUI();
   }
   
}
