///////////////////////////////////
//Name: Thomas Sumardi
//DESCRIPTION : The Goal of this project is to create a wumpusgame.
//				however I did add some features that allows the user to
//				see the locations of the trap and the monster. I also
//				include the ability to fire. there are few other nice features that
//				I add into this project, which is related to the project description.for example,
//				the monster will move and eat the player if the player shoots at the wrong direction.
//

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;
import javax.swing.event.*;
import java.net.*;


public class wumpus extends JApplet implements ActionListener
{
	private JPanel array_panel;
	private URL heroimage, blankimage,fireimage,deathimage,shootimage,trapimage,flameimage;
	private Icon empty,hero,fire,death,shoot,trap,flames;
	private JLabel array_field[][];
	private int backgrd_field[][];	
	JLabel label,dumy_label1,dumy_label2,dumy_label3,dumy_label4,labelbottom;
	boolean wall,gameover,monster; 
	int heroposx,wumpusposx;
	int heroposy,wumpusposy;
	String textinput; 
	Container content; 
	JButton button1;
	JButton button2;
	JButton button3;
	JButton button4;
	JButton button5,button6,button7,button8;
	JButton newbutton1,newbutton2;


//Constructor where all the GUI components are defined///////////////////
//There are four part of the GUI the top is the description
//The bottom is the message, the left side is the movement keys and the right side is
//the shooting options.
	public wumpus()
	{
	
    	try{   
	    heroimage = new URL("http://www.angelfire.com/falcon/tsumardi/flash/javagames/wumpus/hero.gif");
	    blankimage = new URL("http://www.angelfire.com/falcon/tsumardi/flash/javagames/wumpus/shape8.gif");
	    shootimage = new URL("http://www.angelfire.com/falcon/tsumardi/flash/javagames/wumpus/shoot.gif");
	    trapimage = new URL("http://www.angelfire.com/falcon/tsumardi/flash/javagames/wumpus/trap.gif");
	    fireimage = new URL("http://www.angelfire.com/falcon/tsumardi/flash/javagames/wumpus/fire1.gif");
	    deathimage =  new URL("http://www.angelfire.com/falcon/tsumardi/flash/javagames/wumpus/death.gif");
	    flameimage =  new URL("http://www.angelfire.com/falcon/tsumardi/flash/javagames/wumpus/flames1.gif");
	    
		}
		catch(MalformedURLException e){
		}
	
		empty = new ImageIcon(blankimage);
		hero = new ImageIcon(heroimage);
		shoot =new ImageIcon(shootimage);
		trap = new ImageIcon(trapimage);
		fire = new ImageIcon(fireimage);
		death = new ImageIcon(deathimage);
		flames = new ImageIcon(flameimage);

		//content is the container of all the panels,which contains buttons,label and grid
		content = getContentPane();
		content.setLayout(new BorderLayout(5,5));
		
		//creating the left button panel
		JPanel buttons_panel = new JPanel();
		buttons_panel.setLayout(new GridLayout(0,3));
		
		//creating the right button panel
		JPanel buttons_panel2 = new JPanel();
		buttons_panel2.setLayout(new GridLayout(0,3));
    	
    	//creating the top panel
    	JPanel toppanel = new JPanel();
    	newbutton1 = new JButton("<html> CLICK" +"<FONT COLOR=RED>THIS" +
         "<FONT COLOR=GREEN> BUTTON"+"<FONT COLOR=BLUE> TO REVEAL ALL</html>");
        newbutton2 = new JButton("<html> CLICK"+ "<FONT COLOR=RED>THIS" +
         "<FONT COLOR=GREEN> BUTTON"+"<FONT COLOR=BLUE> TO CLOSED ALL</html>");         
        toppanel.setBorder(BorderFactory.createTitledBorder("CHEATING OPTIONS")); 
        
        //attaching the buttons into the panels
        toppanel.add(newbutton1);
        toppanel.add(newbutton2);     
        
        //This is the buttons on the leftside of the GUI
        // the keyoptions on the GUI
    	button1 = new JButton("UP");
		button2 = new JButton("LEFT");
		button3 = new JButton("DOWN");
		button4 = new JButton("RIGHT");
		dumy_label1  = new JLabel("");
		dumy_label2 = new JLabel("");
    	buttons_panel.setBorder(BorderFactory.createTitledBorder("MOVEMENT"));
    	buttons_panel.add(dumy_label1,0);
    	buttons_panel.add(button1,1);
    	buttons_panel.add(dumy_label2,2);
		buttons_panel.add(button2,3); 
		buttons_panel.add(button3,4);
		buttons_panel.add(button4,5);
		
		//this is the buttons on the rightside of the GUI
		//The shooting options on the GUI
		button5 = new JButton("shootup");
		button6 = new JButton("shootleft");
		button7 = new JButton("shoot down");
		button8 = new JButton("shoot right");
		dumy_label3  = new JLabel("");
		dumy_label4 = new JLabel("");
    	buttons_panel2.setBorder(BorderFactory.createTitledBorder("SHOOTING DIRECTION"));
    	buttons_panel2.add(dumy_label3,0);
    	buttons_panel2.add(button5,1);
    	buttons_panel2.add(dumy_label4,2);
		buttons_panel2.add(button6,3); 
		buttons_panel2.add(button7,4);
		buttons_panel2.add(button8,5);
		
		//the label on the bottom of the screen
		String statement = "";
		labelbottom = new JLabel(statement, JLabel.CENTER);
    	labelbottom.setBorder(BorderFactory.createTitledBorder("MESSAGE"));
    	
    	
    	//attaching all the components in to one container content
    	//content.add(label, BorderLayout.NORTH);
    	content.add(toppanel, BorderLayout.NORTH);
    	content.add(buttons_panel,BorderLayout.WEST);
		content.add(buttons_panel2,BorderLayout.EAST);		
		content.add(labelbottom, BorderLayout.SOUTH);
		
		
		//creating all the action listener for all the buttons on the GUI
		newbutton1.addActionListener(this);
		newbutton2.addActionListener(this);
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
		button5.addActionListener(this);
		button6.addActionListener(this);
		button7.addActionListener(this);
		button8.addActionListener(this);
		innerlayer();
		
}		


		
public void innerlayer()
{
	wall = false;
	gameover = false;
	array_field = new JLabel[6][6];
	backgrd_field = new int[6][6];
	
	//placing the pits,hero,monster and so on...
	//by using numerical values to represents all the characters on the
	//game.
	//0 : nothing is there, 1 : pit is there
	//2 : Wumpus is there,3 : hero is there
	
	int l = 0;
	for(int x = 0; x < 6; x ++){
	    for (int y = 0; y < 6; y++){
		// placing 6 pits in random distribution
		if((int)(Math.random()*6) == 0){
		    l= l +1;
		    if(l <= 6)
		    {
		    	//System.out.println("P");
		    	backgrd_field[x][y]=1;
		    }
		  
		}
		else{
		    backgrd_field[x][y]=0;
		}
		//System.out.println(backgrd_field[x][y]);
	    }
	}
	
	//placing the wumpus and the hero in the array
	heroposx = (int)(Math.random()*6);
	heroposy =  (int)(Math.random()*6);
	wumpusposx =  (int)(Math.random()*6);
	wumpusposy =  (int)(Math.random()*6);
	
	//check whether wumpus on the pit
	//making sure that the hero is not on the pit and not ontop of the monster.
	while((backgrd_field[heroposx][heroposy] == 1) || (backgrd_field[wumpusposx][wumpusposy] == 1) || 
		   ( (heroposx == wumpusposx) && (heroposy == wumpusposy)  ) )
	{
		heroposx = (int)(Math.random()*6);
		heroposy =  (int)(Math.random()*6);
		wumpusposx =  (int)(Math.random()*6);
		wumpusposy =  (int)(Math.random()*6);
	}
	backgrd_field[heroposx][heroposy] = 3;
	backgrd_field[wumpusposx][wumpusposy] = 2;
	
	//placing the hero,pits and monster on the gametable...
	//this is the outer layer for the player...
	//remember: the x-axis and the y-axis are inverted.
	array_panel = new JPanel();
	array_panel.setLayout(new GridLayout(6, 6, 1, 1));
	for(int x = 0; x < 6; x++)
	{
	    for(int y = 0; y < 6; y++)
	    {
			if(backgrd_field[x][y]==0)
			{
		   		array_field[x][y] = new JLabel(empty,SwingConstants.CENTER);//empty
		   		array_panel.add(array_field[x][y]);
			}
			else if(backgrd_field[x][y]==3)
			{
		    	array_field[x][y] = new JLabel(hero,SwingConstants.CENTER);//hero
		    	array_panel.add(array_field[x][y]);
			}
			else if(backgrd_field[x][y]==2)
			{
		    	array_field[x][y] = new JLabel(empty,SwingConstants.CENTER);//empty
		    	array_panel.add(array_field[x][y]);
			}
			else
			{
		    	array_field[x][y]= new JLabel(empty,SwingConstants.CENTER);//empty
		    	array_panel.add(array_field[x][y]);
			}
	    }
	 }
	 content.add(array_panel, BorderLayout.CENTER);
	 
	 //check if the hero near pit and print it on the screen in the 
	 //beginning of the game.
	 starting_check(heroposx,heroposy);
	 	
	}
	
	//method that shows everything to the screen..
	//so that the player doesn't feel cheated.
	public void show_result()
	{
		for(int x = 0; x < 6; x++)
		{
	    	for(int y = 0; y < 6; y++)
	    	{
				if(backgrd_field[x][y]==0)
				{
		   			array_field[x][y].setIcon(empty); 
				}
				else if(backgrd_field[x][y]==3)
				{
		    		array_field[x][y].setIcon(hero);
				}
				else if(backgrd_field[x][y]==2)
				{
		    		array_field[x][y].setIcon(death);
				}
				else
				{
		    		array_field[x][y].setIcon(trap);
				}		
	    	}
	 	}
	 content.add(array_panel, BorderLayout.CENTER);
	}
	
	//closed the result back so that the player can continue the game..
	//this method and above can be used to show and close the game.
	public void closed_result()
	{
		for(int x = 0; x < 6; x++)
		{
	    	for(int y = 0; y < 6; y++)
	    	{
				if(backgrd_field[x][y]==0)
				{
		   			array_field[x][y].setIcon(empty); 
				}
				else if(backgrd_field[x][y]==3)
				{
		    		array_field[x][y].setIcon(hero);
				}
				else if(backgrd_field[x][y]==2)
				{
		    		array_field[x][y].setIcon(empty);
				}
				else
				{
		   		 	array_field[x][y].setIcon(empty);
				}
	    	}
	 	}
	 content.add(array_panel, BorderLayout.CENTER); 
	}
	
	//method that checks whether the player is close to the pit or the monster.
	//It is quite messy might need to fix it to make it simpler to read.
	public void starting_check(int herox,int heroy)
	{
				
		String text = "";
		boolean pit = false;
		monster = false;
		//checking the hero's surrounding, N,E,W and South for pits
		if ((backgrd_field[checkx(herox+1)][heroy] == 1) || (backgrd_field[checkx(herox-1)][heroy] == 1) ||
			(backgrd_field[herox][checky(heroy+1)] == 1) ||  (backgrd_field[herox][checky(heroy-1)] == 1))
		{
			pit = true;	
		}
		//checking the hero's surrounding, N,E,W and South for monster
		if ((backgrd_field[checkx(herox+1)][heroy] == 2) || (backgrd_field[checkx(herox-1)][heroy] == 2) ||
			(backgrd_field[herox][checky(heroy+1)] == 2) ||  (backgrd_field[herox][checky(heroy-1)] == 2))
		{
			monster = true;
		}
		//monster and the pit are closed to the player
		if (monster == true && pit == true)
		{
			text = "<html><FONT COLOR=RED> There's a foul stench and " + 
					"<html><FONT COLOR=RED> a breeze</html>";
			printgui(text);
		}
		//only the monster is close to the player					
		else if ( monster == true && pit == false )
		{
			text = "<html><FONT COLOR=RED> There's a foul stench</html>";
			printgui(text);
		}	
		//the pit is close to the player
		else if (monster == false && pit == true)
		{
			text = "<html><FONT COLOR=RED> There's a breeze</html>";
			printgui(text);
		}
		//both are not closed to the player..
		else
		{
			text = " ";
			printgui(text);
		}
		
	}
	
	//method that checks whether a person is on the wall based on 6X6 array size..
	//this method checks the x-axis only....
	//remember: the x-axis and the y-axis are inverted.
	public int checkx(int herox)
	{
		int hero_x = herox;
		if (herox == 6)
		{
			hero_x = herox -1;
			wall = true;
		}	
		else if (herox == -1)
		{
			hero_x = herox +1;
			wall = true;
		}	
		else
		{	
			hero_x = herox;
			wall = false;
		}
		
		return hero_x;
		
	}
	//this method is the same as above, it checks the y-axis.
	public int checky(int heroy)
	{
		int hero_y = heroy;
		if (heroy == 6)
		{
		 	hero_y = heroy -1;
		 	wall = true;
		}	 
		else if (heroy == -1)
		{
			hero_y = heroy +1;
			wall = true;
		}		
		else	
			hero_y = heroy;
			
		return hero_y;	
	}
	
	//printing the statement to the GUI.	 	
	public void printgui(String statement)
	{
		
    	labelbottom.setText(statement);
    	
	}
	
	//method that services the request from the mouse click on the any of the buttons.
	//please read carefully some if statements need to be simplify.
	public void actionPerformed(ActionEvent e)
  {
  
	textinput = "<html>		</html>";
	printgui(textinput);
	int arrow;
	Object src = e.getSource();
	//the user request to show the solution of this game
	if(src == newbutton1)
	{
		show_result();
	}
	
	//the user request to hide the solution of this game
	if (src == newbutton2)
	{
		closed_result();
	}
	//the UP button is pressed and the game is still on..
	// the if statement inside the big one checks whether the user stumble into a WALL,PIT,
	//wumpus or a blank space.
	if ((src == button1)&& (gameover == false))
		{
			//cheking the wall
			if ((backgrd_field[checky(heroposx-1)][heroposy] == 3) && (wall == true))
			{
				textinput = "<html><FONT COLOR=RED> WALL ON THE TOP!!</html>";
				printgui(textinput);
			///bump against the wall
			}
			//checking the Pit
			else if ((backgrd_field[heroposx-1][heroposy] == 1) )
			{
				heroposx = heroposx-1;
				backgrd_field[heroposx+1][heroposy] = 0;
				backgrd_field[heroposx][heroposy] = 1;
				array_field[heroposx][heroposy].setIcon(trap);//setIcon(death)	 
				array_field[heroposx+1][heroposy].setIcon(empty);//setIcon(empty)
				textinput = "<html><FONT COLOR=RED> GAME OVER YOU HAVE FALLEN INTO A PIT!!</html>";
				printgui(textinput);
				gameover = true;
				///pit is there
			}
			//checking the monster
			else if (backgrd_field[heroposx-1][heroposy] == 2)
			{
				//wumpus is there
				heroposx = heroposx-1;
				backgrd_field[heroposx+1][heroposy] = 0;
				backgrd_field[heroposx][heroposy] = 2;
				array_field[heroposx][heroposy].setIcon(death);	 //setIcon(death);	
				 array_field[heroposx+1][heroposy].setIcon(empty);//setIcon(empty)
				textinput = "<html><FONT COLOR=RED> GAME OVER YOU HAVE BEEN EATEN BY WUMPUS!!</html>";
				printgui(textinput);
				gameover = true;
				
			}
			//the blank space.
			else 
			{
				 heroposx = heroposx-1;
			     //blank space
				 backgrd_field[heroposx+1][heroposy] = 0;
				 backgrd_field[heroposx][heroposy] = 3;
				 array_field[heroposx][heroposy].setIcon(hero);//setIcon(hero)	 
				 array_field[heroposx+1][heroposy].setIcon(empty);//setIcon(empty)
				 starting_check(heroposx,heroposy);
				 //move
			}
			System.out.println("UP");
		}
	 //the LEFT button is pressed and the game is still on..
	// the if statement inside the big one checks whether the user stumble into a WALL,PIT,
	//wumpus or a blank space.	
	if ((src == button2)&& (gameover == false))
		{
			//cheking the wall
			if ((backgrd_field[heroposx][checky(heroposy-1)] == 3) && (wall == true))
			{
				System.out.println("waaaaaal");
				textinput = "<html><FONT COLOR=RED> WALL ON THE LEFT!!</html>";
				printgui(textinput);
			///bump against the wall
			}
			//checking the Pit
			else if ((backgrd_field[heroposx][heroposy-1] == 1) )
			{
				heroposy = heroposy-1;
				backgrd_field[heroposx][heroposy+1] = 0;
				backgrd_field[heroposx][heroposy] = 1;
				array_field[heroposx][heroposy].setIcon(trap);	 //setIcon(death)
				 array_field[heroposx][heroposy+1].setIcon(empty);//setIcon(empty)
				textinput = "<html><FONT COLOR=RED> GAME OVER YOU HAVE FALLEN INTO A PIT!!</html>";
				printgui(textinput);
				gameover = true;
				///pit is there
			}
			//checking the monster
			else if (backgrd_field[heroposx][heroposy-1] == 2)
			{
				//wumpus is there
				heroposy = heroposy-1;
				backgrd_field[heroposx][heroposy+1] = 0;
				backgrd_field[heroposx][heroposy] = 2;
				array_field[heroposx][heroposy].setIcon(death);	 //setIcon(death)
				 array_field[heroposx][heroposy+1].setIcon(empty);//setIcon(empty)
				textinput = "<html><FONT COLOR=RED> GAME OVER YOU HAVE BEEN EATEN BY WUMPUS!!</html>";
				printgui(textinput);
				gameover = true;
				
			}
			//the blank space.
			else 
			{
				 heroposy = heroposy-1;
				 System.out.println("x"+heroposx+"y"+heroposy );
			     //blank space
				 backgrd_field[heroposx][heroposy+1] = 0;
				 backgrd_field[heroposx][heroposy] = 3;
				 array_field[heroposx][heroposy].setIcon(hero);	 //setIcon(hero)
				 array_field[heroposx][heroposy+1].setIcon(empty);//setIcon(empty)
				 starting_check(heroposx,heroposy);
				 //move
			}
			System.out.println("left");
		}
	//the DOWN button is pressed and the game is still on..
	// the if statement inside the big one checks whether the user stumble into a WALL,PIT,
	//wumpus or a blank space.	
	// the function is quite similar to the one before.
	if ((src == button3)&& (gameover == false))
		{
		
			if ((backgrd_field[checky(heroposx+1)][heroposy] == 3) && (wall == true))
			{
				System.out.println("waaaaaal");
				textinput = "<html><FONT COLOR=RED> WALL ON THE BOTTOM!!</html>";
				printgui(textinput);
			///bump against the wall
			}
			else if ((backgrd_field[heroposx+1][heroposy] == 1) )
			{
				heroposx = heroposx+1;
				backgrd_field[heroposx-1][heroposy] = 0;
				backgrd_field[heroposx][heroposy] = 1;
				array_field[heroposx][heroposy].setIcon(trap);	 //setIcon(death)
				 array_field[heroposx-1][heroposy].setIcon(empty);//setIcon(empty)
				textinput = "<html><FONT COLOR=RED> GAME OVER YOU HAVE FALLEN INTO A PIT!!</html>";
				printgui(textinput);
				gameover = true;
				///pit is there
			}
			else if (backgrd_field[heroposx+1][heroposy] == 2)
			{
				//wumpus is there
				heroposx = heroposx+1;
				backgrd_field[heroposx-1][heroposy] = 0;
				backgrd_field[heroposx][heroposy] = 2;
				array_field[heroposx][heroposy].setIcon(death);	// setIcon(death)
				 array_field[heroposx-1][heroposy].setIcon(empty);//setIcon(empty)
				textinput = "<html><FONT COLOR=RED> GAME OVER YOU HAVE BEEN EATEN BY WUMPUS!!</html>";
				printgui(textinput);
				gameover = true;
				
			}
			else 
			{
				 heroposx = heroposx+1;
				 System.out.println("x"+heroposx+"y"+heroposy );
			     //blank space
				 backgrd_field[heroposx-1][heroposy] = 0;
				 backgrd_field[heroposx][heroposy] = 3;
				 array_field[heroposx][heroposy].setIcon(hero);	 //setIcon(hero)
				 array_field[heroposx-1][heroposy].setIcon(empty);//setIcon(empty)
				 starting_check(heroposx,heroposy);
				 //move
			}
			System.out.println("down");
		}
	//the RIGHT button is pressed and the game is still on..
	// the if statement inside the big one checks whether the user stumble into a WALL,PIT,
	//wumpus or a blank space.	
	// the function is quite similar to the one before.	
	if ((src == button4) && (gameover == false))
		{
		
			if ((backgrd_field[heroposx][checky(heroposy+1)] == 3) && (wall == true))
			{
				System.out.println("waaaaaal");
				textinput = "<html><FONT COLOR=RED> WALL ON THE RIGHT!!</html>";
				printgui(textinput);
			///bump against the wall
			}
			else if ((backgrd_field[heroposx][heroposy+1] == 1) )
			{
				heroposy = heroposy+1;
				backgrd_field[heroposx][heroposy-1] = 0;
				backgrd_field[heroposx][heroposy] = 1;
				array_field[heroposx][heroposy].setIcon(trap);	 //setIcon(death)
				 array_field[heroposx][heroposy-1].setIcon(empty);//setIcon(empty)
				textinput = "<html><FONT COLOR=RED> GAME OVER YOU HAVE FALLEN INTO A PIT!!</html>";
				printgui(textinput);
				gameover = true;
				///pit is there
			}
			else if (backgrd_field[heroposx][heroposy+1] == 2)
			{
				//wumpus is there
				heroposy = heroposy+1;
				backgrd_field[heroposx][heroposy-1] = 0;
				backgrd_field[heroposx][heroposy] = 2;
				array_field[heroposx][heroposy].setIcon(death);	 //setIcon(death)
				 array_field[heroposx][heroposy-1].setIcon(empty);//setIcon(empty)
				textinput = "<html><FONT COLOR=RED> GAME OVER YOU HAVE BEEN EATEN BY WUMPUS!!</html>";
				printgui(textinput);
				gameover = true;
				
			}
			else 
			{
				 heroposy = heroposy+1;
				 System.out.println("x"+heroposx+"y"+heroposy );
			     //blank space
				 backgrd_field[heroposx][heroposy-1] = 0;
				 backgrd_field[heroposx][heroposy] = 3;
				 array_field[heroposx][heroposy].setIcon(hero);	 //setIcon(hero)
				 array_field[heroposx][heroposy-1].setIcon(empty);//setIcon(empty)
				 starting_check(heroposx,heroposy);
				 //move
			}
			System.out.println("right");
		}
	////////////////////////////////SHOOOTING METHODS BELOW/////////////////////////////
	
	//the shoot top button when the game is still on
	// the user can shoot in any direction while leaving a mark but the user doesnt damage
	//anything except the monster.
	// the function is quite similar to the one before.		
	if ((src == button5) && (gameover == false))
		{
			System.out.println("shootup");
			//the player shoots at the wall
			if ((backgrd_field[checky(heroposx-1)][heroposy] == 3) && (wall == true))
			{
				System.out.println("waaaaaal");
				array_field[heroposx][heroposy].setIcon(shoot); 
				starting_check(heroposx,heroposy);
				textinput = "<html><FONT COLOR=RED> YOU SHOOT THE TOP WALL!!</html>";
				printgui(textinput);
			///bump against the wall
			}
			//the player shoots at the pit
			else if ((backgrd_field[heroposx-1][heroposy] == 1) )
			{	
				arrow = heroposx-1;
				array_field[heroposx][heroposy].setIcon(shoot); 
				array_field[arrow][heroposy].setIcon(fire); 
				starting_check(heroposx,heroposy);
				textinput = "<html><FONT COLOR=RED> MISS!!</html>";
				printgui(textinput);
				///pit is there
			}
			//the player shoots at the monster
			else if (backgrd_field[heroposx-1][heroposy] == 2)
			{
				//wumpus is there
				arrow = heroposx-1;
				array_field[heroposx][heroposy].setIcon(shoot); 
				array_field[arrow][heroposy].setIcon(flames);	// setIcon(flames)
				textinput = "<html><FONT COLOR=RED> YOU HAVE WON THE GAME!!</html>";
				printgui(textinput);
				gameover = true;
				
			}
			//the player shoots at the blank space
			else 
			{
				 arrow = heroposx-1;
				 System.out.println("x"+heroposx+"y"+heroposy );
			     //blank space
			     array_field[heroposx][heroposy].setIcon(shoot); 
				 array_field[arrow][heroposy].setIcon(fire); //setIcon(fire)
				 starting_check(heroposx,heroposy);
				 textinput = "<html><FONT COLOR=RED> MISS!!</html>";
				 printgui(textinput);	 
				 //move
			}
			//after the user miss the shot.
			wumpusrevenge();
			
		}
		
		
	//the shoot left button when the game is still on
	// the user can shoot in any direction while leaving a mark but the user doesnt damage
	//anything except the monster.
	//the implementation is similar to the previous one.	
	if ((src == button6) && (gameover == false))
		{
			System.out.println("shootleft");
			//the player shoots at the wall
			if ((backgrd_field[heroposx][checky(heroposy-1)] == 3) && (wall == true))
			{
				System.out.println("waaaaaal");
				array_field[heroposx][heroposy].setIcon(shoot); 
				starting_check(heroposx,heroposy);
				textinput = "<html><FONT COLOR=RED> YOU SHOOT THE LEFT WALL!!</html>";
				printgui(textinput);
			///bump against the wall
			}
			//the player shoots at the pit
			else if ((backgrd_field[heroposx][heroposy-1] == 1) )
			{	 
			
				arrow = heroposy-1;
			 	array_field[heroposx][heroposy].setIcon(shoot);
			 	array_field[heroposx][arrow].setIcon(fire); 
				starting_check(heroposx,heroposy);
				textinput = "<html><FONT COLOR=RED> MISS!!</html>";
				printgui(textinput);
				///pit is there
			}
			//the player shoots at the monster
			else if (backgrd_field[heroposx][heroposy-1] == 2)
			{
				//wumpus is there
				arrow = heroposy-1;
				array_field[heroposx][heroposy].setIcon(shoot); 
				array_field[heroposx][arrow].setIcon(flames);	 //setIcon(flames)
				textinput = "<html><FONT COLOR=RED> YOU HAVE WON THE GAME!!</html>";
				printgui(textinput);
				gameover = true;
				
			}
			//the player shoots at the blank space
			else 
			{
				 arrow = heroposy-1;
				 System.out.println("x"+heroposx+"y"+heroposy );
			     //blank space
			     array_field[heroposx][heroposy].setIcon(shoot); 
				 array_field[heroposx][arrow].setIcon(fire); //setIcon(fire)
				 starting_check(heroposx,heroposy);
				 textinput = "<html><FONT COLOR=RED> MISS!!</html>";
				 printgui(textinput);	 
				 //move
			}
			//after the user miss the shot.
			wumpusrevenge();
			
		}
	//the shoot down button when the game is still on
	// the user can shoot in any direction while leaving a mark but the user doesnt damage
	//anything except the monster.
	//the implementation is similar to the previous one.		
	if ((src == button7) && (gameover == false))
		{
			
			System.out.println("shootdown");
			//the player shoots at the wall
			if ((backgrd_field[checky(heroposx+1)][heroposy] == 3) && (wall == true))
			{
				System.out.println("waaaaaal");
				array_field[heroposx][heroposy].setIcon(shoot); 
				starting_check(heroposx,heroposy);
				textinput = "<html><FONT COLOR=RED> YOU SHOOT THE BOTTOM WALL!!</html>";
				printgui(textinput);
			///bump against the wall
			}
			//the player shoots at the pit
			else if ((backgrd_field[heroposx+1][heroposy] == 1) )
			{	 
				arrow = heroposx+1;
				array_field[heroposx][heroposy].setIcon(shoot); 
				array_field[arrow][heroposy].setIcon(fire);
				starting_check(heroposx,heroposy);
				textinput = "<html><FONT COLOR=RED> MISS!!</html>";
				printgui(textinput);
				///pit is there
			}
			//the player shoots at the monster
			else if (backgrd_field[heroposx+1][heroposy] == 2)
			{
				//wumpus is there
				arrow = heroposx+1;
				array_field[heroposx][heroposy].setIcon(shoot); 
				array_field[arrow][heroposy].setIcon(flames) ;	//setIcon(flames) 
				textinput = "<html><FONT COLOR=RED> YOU HAVE WON THE GAME!!</html>";
				printgui(textinput);
				gameover = true;
				
			}
			//the player shoots at the blank spot
			else 
			{
				 arrow = heroposx+1;
				 System.out.println("x"+heroposx+"y"+heroposy );
			     //blank space
			     array_field[heroposx][heroposy].setIcon(shoot); 
				 array_field[arrow][heroposy].setIcon(fire);//setIcon(fire)
				 starting_check(heroposx,heroposy);
				 textinput = "<html><FONT COLOR=RED> MISS!!</html>";
				 printgui(textinput);	 
				 //move
			}
			//the monster eat the player
			wumpusrevenge();
		}
		//the shoot right button when the game is still on
	// the user can shoot in any direction while leaving a mark but the user doesnt damage
	//anything except the monster.
	//the implementation is similar to the previous one.	
	if ((src == button8) && (gameover == false))
		{
			//the player shoots at the wall
			if ((backgrd_field[heroposx][checky(heroposy+1)] == 3) && (wall == true))
			{
				System.out.println("waaaaaal");
				array_field[heroposx][heroposy].setIcon(shoot); 
				starting_check(heroposx,heroposy);
				textinput = "<html><FONT COLOR=RED> YOU SHOOT THE RIGHT WALL!!</html>";
				printgui(textinput);
			///bump against the wall
			}
			//the player shoots at the pit
			else if ((backgrd_field[heroposx][heroposy+1] == 1) )
			{	
				arrow = heroposy+1;
				array_field[heroposx][heroposy].setIcon(shoot); 
				array_field[heroposx][arrow].setIcon(fire);
				starting_check(heroposx,heroposy); 
				textinput = "<html><FONT COLOR=RED> MISS!!</html>";
				printgui(textinput);
				///pit is there
			}
			//the player shoots at the monster
			else if (backgrd_field[heroposx][heroposy+1] == 2)
			{
				//wumpus is there
				arrow = heroposy+1;
				array_field[heroposx][heroposy].setIcon(shoot);
				array_field[heroposx][arrow].setIcon(flames);	// setIcon(flames)
				textinput = "<html><FONT COLOR=RED> YOU HAVE WON THE GAME!!</html>";
				printgui(textinput);
				gameover = true;
				
			}
			//the player shoots at the blank space
			else 
			{
				 arrow = heroposy+1;
				 System.out.println("x"+heroposx+"y"+heroposy );
			     //blank space
			     array_field[heroposx][heroposy].setIcon(shoot);
				 array_field[heroposx][arrow].setIcon(fire);//setIcon(fire)
				 starting_check(heroposx,heroposy);
				 textinput = "<html><FONT COLOR=RED> MISS!!</html>";
				 printgui(textinput);	 
				 //move
			}
			// The monster eat the player 
			wumpusrevenge();
			System.out.println("shootright");
		}
		
	//content.add(array_panel, BorderLayout.CENTER);
	if (gameover == true)
	{
		printgui("<html>"+textinput+" PRESS "+ "<FONT COLOR=RED>REFRESH(BROWSER) </FONT>" +" TO PLAY AGAIN</html>");
	}
	monster = false;
	wall = false;	
  }
  
  
  //the monster will move and eat the player if the player fails to kill the monster
  //shooting at the wrong direction will kill the player
  public void wumpusrevenge()
	{
		
		if(monster == true)
		{	
			//blank space
			backgrd_field[heroposx][heroposy] = 2;
			backgrd_field[wumpusposx][wumpusposy] = 0;
			array_field[heroposx][heroposy].setIcon(death);	 //setIcon(death) //wumpusrev
			array_field[wumpusposx][wumpusposy].setIcon(empty);//setIcon(empty)
			textinput = "<html><FONT COLOR=RED> WUMPUS :YUMMMMMY....GAME OVER YOU LOSE!!</html>";
			gameover = true;
		}
		else
		{
		}
	}	
	
	//simple function that calls the constructor and starts the GUI.
	//
	public void init()
	{
		new wumpus();
	}
	

}
