package Game.Maps;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.EmptyBorder;

import API.Element;
import API.Map;
import API.Window;
import API.Components.Sprite;
import API.Layouts.CenterGridLayout;
import API.Utility.Vector;

public class MenuMap extends Map {
	
	public MenuMap() {
		setMapSize(new Dimension(1500, 900));
		
		Vector mapCenter = getMapCenter();
		
		Element background = new Element();
        background.setSprite("mainmenu.jpg");
        addElement(background, mapCenter, -10);
        
        
		JButton startGame = new JButton("New Game");
		startGame.setBounds(mapCenter.x - 100, 350, 200, 30);
		startGame.setFont(new Font("Arial", Font.BOLD, 20));
		getViewArea().add(startGame, 10);
		
		JPanel bottomMenu = new JPanel();
		bottomMenu.setLayout(new GridBagLayout());
		bottomMenu.setBorder(new EmptyBorder(10, 25, 10, 25));
		bottomMenu.setBackground(Color.getHSBColor(0.6f, 0.45f, 1));
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.ipadx = 10;
		gbc.ipady = 10;
		
		getViewArea().add(bottomMenu, 1);
		
		JLabel text1 = new JLabel("Persone che entreranno durante la serata: ");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		bottomMenu.add(text1, gbc);
		
		JSpinner custumersTot = new JSpinner();
		custumersTot.setValue(100);
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		bottomMenu.add(custumersTot, gbc);
		
		JLabel text2 = new JLabel("Massimo numero di persone che possono stare dentro al locale:");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		bottomMenu.add(text2, gbc);
		
		JSpinner maxLocalCustumers = new JSpinner();
		maxLocalCustumers.setValue(20);
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		bottomMenu.add(maxLocalCustumers, gbc);
		
		
		Dimension prefSize = bottomMenu.getPreferredSize();
		bottomMenu.setBounds(mapCenter.x - (int)prefSize.getWidth()/2, 500, (int)prefSize.getWidth(), (int)prefSize.getHeight());
		
		
		
		startGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame currentFrame = (JFrame)JFrame.getFrames()[0];
				new Window(new BarMap((int)custumersTot.getValue(), (int)maxLocalCustumers.getValue()));
				currentFrame.dispose();
			}
		});
	}
}
