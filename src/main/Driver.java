package main;

import java.awt.*;
import java.io.*;
import javax.swing.*;

import battle.BattleView;
import entity.Player;
import map.*;
import status.*;

public class Driver {
	
    final static String MAP = "Map GUI";
    final static String BATTLE = "Battle GUI";
    int GUIstate;
    static JPanel cards;
    static Map map;
    static JFrame frame;
    static int fail;
    
	public static void main(String[] args) {
        // http://docs.oracle.com/javase/tutorial/uiswing/concurrency/initial.html
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                prepareGUI();
                startCheck();
            }
        });
    }
	
	public static void startCheck() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while(fail == 0) {
					while(!map.isBattle()) {
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					JPanel battlePane = new BattleView(map.getPlayer(), map.getVirtumon());
					cards.add(battlePane, BATTLE);
			        CardLayout cl = (CardLayout)(cards.getLayout());
			        cl.show(cards, BATTLE);
			        frame.pack();
			        //ini diganti dengan ngecek apakah battle beres
			        while(true);
				}
			}
        	
        }).start();
	}
	
	public static void prepareGUI() {
		//Create and set up the window.
		frame = new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //Create and set up the content pane.
        Driver demo = new Driver();
        demo.addComponentToPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
	}
	
	public void addComponentToPane(Container pane) {
		cards = new JPanel(new CardLayout());
		File filename = new File("map.txt");
		GridBagConstraints c = new GridBagConstraints();
		try {
			JPanel mapPane = new JPanel();
			mapPane.setLayout(new GridBagLayout());
			Player player = new Player("Solid",100);
			System.out.println("hahaha");
			map = new Map(filename, player);
			System.out.println("hihihi");
			c.gridx = 0;
			c.gridy = 0;
			mapPane.add(map, c);
			Status status = new Status(player);
			c.gridx = 1;
			c.gridy = 0;
			c.anchor = GridBagConstraints.FIRST_LINE_START;
			mapPane.add(status, c);
			mapPane.setVisible(true);
			cards.add(mapPane, MAP);
			fail = 0;
		} catch (IOException e) {
			JPanel error = new JPanel();
			JLabel err = new JLabel("Missing data file");
			error.add(err);
			error.setVisible(true);
			cards.add(error, MAP);
			fail = 1;
		}
		pane.add(cards);
	}
}