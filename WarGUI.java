/**  
   Tommy Ernst
   CS110
   War Game GUI
*/


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
      

      //set title bar
      setTitle("A game of WAR");  
      
      //call createPanels method
      createPanels();
      
      //so we can see everything
      setVisible(true);
      
      //to keep everything updated
      update();
     
   }
   
   /**
      createPanels method
      
      this method builds the top, bottom, player one and player two panels
   */
   private void createPanels()
   {
      //create deck labels
      player1Deck = new JLabel();
      player1Deck.setText("Player One");
      player1Deck.setFont(new Font("AVENIR",Font.BOLD,24));
      player1Deck.setVerticalTextPosition(JLabel.BOTTOM);
      player1Deck.setHorizontalTextPosition(JLabel.CENTER);
      
      player2Deck = new JLabel();
      player2Deck.setText("Player Two");
      player2Deck.setFont(new Font("AVENIR",Font.BOLD,24));
      player2Deck.setVerticalTextPosition(JLabel.BOTTOM);
      player2Deck.setHorizontalTextPosition(JLabel.CENTER);
      
      //create card labels
      player1Card = new JLabel();
      player2Card = new JLabel();
      
      
      //create player one panel and add elements
      player1Panel = new JPanel();
      player1Panel.setPreferredSize(new Dimension(400,450));
      
      player1Panel.add(player1Deck);
      player1Panel.add(player1Card);
      player1Panel.setBackground(Color.green);
      
      //create player two panel and add elements
      player2Panel = new JPanel();
      player2Panel.setPreferredSize(new Dimension(400,450));

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
      status.setFont(new Font("AVENIR",Font.BOLD,24));
     
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
      updateCardBacks();
      updateOutcome();
      updateScores();
   }
   
   /**
      updates the back of the card image
      image goes away when there are no cards left
   */
   public void updateCardBacks()
   {
      cardBack = new ImageIcon("cards/back.jpg");
      
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
      player1Card.setText(String.valueOf(play1Score)+" cards");
      player1Card.setFont(new Font("AVENIR",Font.BOLD,24));

      player1Card.setVerticalTextPosition(JLabel.BOTTOM);
      player1Card.setHorizontalTextPosition(JLabel.CENTER);

      
      //get player two score
      int play2Score = game.getPlayerTwoCardsLeft();
      
      //set text
      player2Card.setText(String.valueOf(play2Score)+" cards");
      player2Card.setVerticalTextPosition(JLabel.BOTTOM);
      player2Card.setHorizontalTextPosition(JLabel.CENTER);
      player2Card.setFont(new Font("AVENIR",Font.BOLD,24));

   } 
   
   
   
   
   //run the GUI
   public static void main (String[] args)
   {
      WarGUI game = new WarGUI();
   }
   
}
