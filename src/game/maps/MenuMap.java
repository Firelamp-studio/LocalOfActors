package game.maps;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import api.Map;
import api.Window;
import api.components.Sprite;
import api.utility.Vector;

public class MenuMap extends Map {
	
	public MenuMap() {
		setMapSize(new Dimension(1500, 900));
		
		Vector mapCenter = getMapCenter();

		Sprite background = new Sprite("mainmenu.jpg");
        addComponent(background, mapCenter, -10);
        
        
		JButton startGame = new JButton("New Game");
		startGame.setBounds(mapCenter.x - 100, 350, 200, 30);
		startGame.setFont(new Font("Arial", Font.BOLD, 20));
		getViewArea().add(startGame, 10);
		
		JPanel bottomMenu = new JPanel();
		bottomMenu.setLayout(new GridBagLayout());
		bottomMenu.setBorder(new EmptyBorder(10, 25, 10, 25));
		bottomMenu.setBackground(Color.getHSBColor(0.6f, 0.45f, 1));
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.ipadx = 5;
		gbc.ipady = 10;
		gbc.insets = new Insets(2, 0, 2, 0);
		
		getViewArea().add(bottomMenu, 1);
		
		JLabel text1 = new JLabel("Persone che entreranno durante la serata: ");
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		bottomMenu.add(text1, gbc);

		SpinnerNumberModel customersTotModel = new SpinnerNumberModel();
		customersTotModel.setMinimum(1);
		customersTotModel.setValue(100);
		JSpinner customersTotSpinner = new JSpinner(customersTotModel);
		customersTotSpinner.setPreferredSize(new Dimension(50, customersTotSpinner.getPreferredSize().height));
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		bottomMenu.add(customersTotSpinner, gbc);

		JLabel text2 = new JLabel("Massimo numero di persone che possono stare dentro al locale:");
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		bottomMenu.add(text2, gbc);

		SpinnerNumberModel maxLocalCustumersModel = new SpinnerNumberModel();
		maxLocalCustumersModel.setMinimum(1);
		maxLocalCustumersModel.setValue(30);
		JSpinner maxLocalCustumersSpinner = new JSpinner(maxLocalCustumersModel);
		maxLocalCustumersSpinner.setPreferredSize(new Dimension(50, customersTotSpinner.getPreferredSize().height));
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		bottomMenu.add(maxLocalCustumersSpinner, gbc);

		JLabel text3 = new JLabel("Incremento del rallentamento della velocità della serata:");
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		bottomMenu.add(text3, gbc);

		SpinnerNumberModel gameSpeedModel = new SpinnerNumberModel();
		gameSpeedModel.setMinimum(1);
		gameSpeedModel.setMaximum(20);
		gameSpeedModel.setValue(3);
		JSpinner gameSpeedSpinner = new JSpinner(gameSpeedModel);
		gameSpeedSpinner.setPreferredSize(new Dimension(50, customersTotSpinner.getPreferredSize().height));
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.EAST;
		bottomMenu.add(gameSpeedSpinner, gbc);

		JLabel text4 = new JLabel("Capienza in litri delle botti di vino:");
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.WEST;
		bottomMenu.add(text4, gbc);

		SpinnerNumberModel maxBarrelValueModel = new SpinnerNumberModel();
		maxBarrelValueModel.setMinimum(1);
		maxBarrelValueModel.setValue(100);
		JSpinner maxBarrelValueSpinner = new JSpinner(maxBarrelValueModel);
		maxBarrelValueSpinner.setPreferredSize(new Dimension(50, customersTotSpinner.getPreferredSize().height));
		gbc.gridx = 2;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.EAST;
		bottomMenu.add(maxBarrelValueSpinner, gbc);

		JLabel text5 = new JLabel("Qualità del rendering delle immagini in movimento:");
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.WEST;
		bottomMenu.add(text5, gbc);

		JComboBox<String> renderQuality = new JComboBox<>();
		gbc.ipady = 2;
		gbc.gridx = 2;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.EAST;
		bottomMenu.add(renderQuality, gbc);

		renderQuality.addItem("  Alto");
		renderQuality.addItem("  Basso");
		
		Dimension prefSize = bottomMenu.getPreferredSize();
		bottomMenu.setBounds(mapCenter.x - (int)prefSize.getWidth()/2, 500, (int)prefSize.getWidth(), (int)prefSize.getHeight());
		
		
		
		startGame.addActionListener((e) -> {
			JFrame currentFrame = (JFrame)JFrame.getFrames()[0];

			new Window(new BarMap((int)customersTotModel.getValue(), (int)maxLocalCustumersModel.getValue(), (int)gameSpeedModel.getValue(), (int)maxBarrelValueModel.getValue()*1000, renderQuality.getSelectedItem().toString()));
			currentFrame.dispose();
		});
	}
}
