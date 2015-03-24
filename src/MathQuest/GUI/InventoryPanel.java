package MathQuest.GUI;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;

import MathQuest.Logic.Character;
import MathQuest.Logic.Item;
import MathQuest.Pages.Area;
import MathQuest.Pages.World;

import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.JButton;
import javax.swing.JComboBox;

public class InventoryPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private ImageIcon inventoryImage;

	public InventoryPanel(final Area frame, Character hero) {
		
		this.setBounds(683, 0, 341, 649);
		this.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
		setLayout(null);

		this.loadImages();

		JPanel inventoryStatsPanel = new JPanel();
		inventoryStatsPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		inventoryStatsPanel.setBounds(10, 227, 321, 330);
		add(inventoryStatsPanel);
		inventoryStatsPanel.setLayout(null);
		
		JComboBox<Item> helmetComboBox = new JComboBox<Item>();
		helmetComboBox.setBounds(149, 11, 28, 20);
		for(Item el : hero.getHeadItems()) 
			helmetComboBox.addItem(el);
		inventoryStatsPanel.add(helmetComboBox);
		
		JComboBox<Item> weaponComboBox = new JComboBox<Item>();
		weaponComboBox.setBounds(34, 80, 28, 20);
		for(Item el : hero.getWeaponItems())
			weaponComboBox.addItem(el);
		inventoryStatsPanel.add(weaponComboBox);
		
		JComboBox<Item> chestComboBox = new JComboBox<Item>();
		chestComboBox.setBounds(149, 115, 28, 20);
		for(Item el : hero.getChestItems())
			chestComboBox.addItem(el);
		inventoryStatsPanel.add(chestComboBox);
		
		JComboBox<Item> gloveComboBox = new JComboBox<Item>();
		gloveComboBox.setBounds(34, 238, 28, 20);
		for(Item el : hero.getGloveItems())
			gloveComboBox.addItem(el);
		inventoryStatsPanel.add(gloveComboBox);
		
		JComboBox<Item> bootsComboBox = new JComboBox<Item>();
		bootsComboBox.setBounds(264, 238, 28, 20);
		for(Item el : hero.getFeetItems()) 
			bootsComboBox.addItem(el);
		inventoryStatsPanel.add(bootsComboBox);
		
		JPanel characterPanel = new JPanel();
		characterPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		characterPanel.setBounds(10, 11, 321, 37);
		add(characterPanel);
		characterPanel.setLayout(null);

		JLabel characterLabel = new JLabel("Character Sheet");
		characterLabel.setBounds(49, 5, 219, 27);
		characterPanel.add(characterLabel);
		characterLabel.setHorizontalAlignment(SwingConstants.CENTER);
		characterLabel.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 24));

		JPanel characterStatsPanel = new JPanel();
		characterStatsPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		characterStatsPanel.setBounds(10, 58, 321, 110);
		add(characterStatsPanel);
		characterStatsPanel.setLayout(null);
		
		JLabel levelLabel = new JLabel("Level");
		levelLabel.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 11));
		levelLabel.setHorizontalAlignment(SwingConstants.CENTER);
		levelLabel.setVerticalAlignment(SwingConstants.TOP);
		levelLabel.setBorder(new LineBorder(Color.black));
		levelLabel.setBounds(10, 11, 68, 29);
		characterStatsPanel.add(levelLabel);
		
		JLabel experienceLabel = new JLabel("Experience");
		experienceLabel.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 11));
		experienceLabel.setHorizontalAlignment(SwingConstants.CENTER);
		experienceLabel.setVerticalAlignment(SwingConstants.TOP);
		experienceLabel.setBorder(new LineBorder(Color.black));
		experienceLabel.setBounds(88, 11, 108, 29);
		characterStatsPanel.add(experienceLabel);
		
		JLabel nextLevelLabel = new JLabel("Next Level");
		nextLevelLabel.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 11));
		nextLevelLabel.setHorizontalAlignment(SwingConstants.CENTER);
		nextLevelLabel.setVerticalAlignment(SwingConstants.TOP);
		nextLevelLabel.setBorder(new LineBorder(Color.black));
		nextLevelLabel.setBounds(206, 11, 105, 29);
		characterStatsPanel.add(nextLevelLabel);
		
		Integer level = hero.getLevel();
		JLabel actualLevel = new JLabel(level.toString());
		actualLevel.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 11));
		actualLevel.setHorizontalAlignment(SwingConstants.CENTER);
		actualLevel.setBounds(10, 26, 68, 14);
		characterStatsPanel.add(actualLevel);
		
		Integer experience = hero.getCurrentExperience();
		JLabel actualExperience = new JLabel(experience.toString());
		actualExperience.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 11));
		actualExperience.setHorizontalAlignment(SwingConstants.CENTER);
		actualExperience.setBounds(88, 26, 105, 14);
		characterStatsPanel.add(actualExperience);
		
		Integer nextLevel = hero.getMaxExperience();
		JLabel actualNextLevel = new JLabel(nextLevel.toString());
		actualNextLevel.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 11));
		actualNextLevel.setHorizontalAlignment(SwingConstants.CENTER);
		actualNextLevel.setBounds(206, 26, 105, 14);
		characterStatsPanel.add(actualNextLevel);
		
		JLabel strengthLabel = new JLabel("Strength");
		strengthLabel.setHorizontalAlignment(SwingConstants.CENTER);
		strengthLabel.setBounds(10, 78, 68, 14);
		characterStatsPanel.add(strengthLabel);
		
		JLabel damageLabel = new JLabel("Damage");
		damageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		damageLabel.setBounds(170, 78, 68, 14);
		characterStatsPanel.add(damageLabel);
		
		Integer actualStrength = hero.getStrength();
		JLabel actualStrengthLabel = new JLabel(actualStrength.toString());
		actualStrengthLabel.setHorizontalAlignment(SwingConstants.CENTER);
		actualStrengthLabel.setBounds(88, 78, 68, 14);
		characterStatsPanel.add(actualStrengthLabel);
		
		Integer maxDamage = (int)(Math.round(actualStrength * .2));
		Integer minDamage = (int)(Math.round(.75 * maxDamage));
		String damage = String.format("%s - %s", minDamage.toString(), maxDamage.toString());
		JLabel actualDamage = new JLabel(damage);
		actualDamage.setHorizontalAlignment(SwingConstants.CENTER);
		actualDamage.setBounds(243, 78, 68, 14);
		characterStatsPanel.add(actualDamage);
		
		JLabel lifeLabel = new JLabel("Life");
		lifeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lifeLabel.setBounds(10, 53, 68, 14);
		characterStatsPanel.add(lifeLabel);
		
		Integer currentLife = hero.getCurrentHealth();
		Integer maxLife = hero.getMaxHealth();
		String life = String.format("%s / %s", currentLife.toString(), maxLife.toString());
		JLabel actualLifeLabel = new JLabel(life);
		actualLifeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		actualLifeLabel.setBounds(88, 53, 68, 14);
		characterStatsPanel.add(actualLifeLabel);
		
		JLabel goldLabel = new JLabel("Gold");
		goldLabel.setHorizontalAlignment(SwingConstants.CENTER);
		goldLabel.setBounds(166, 53, 73, 14);
		characterStatsPanel.add(goldLabel);
		
		Integer actualGold = hero.getGold();
		JLabel actualGoldLabel = new JLabel(actualGold.toString());
		actualGoldLabel.setHorizontalAlignment(SwingConstants.CENTER);
		actualGoldLabel.setBounds(248, 53, 63, 14);
		characterStatsPanel.add(actualGoldLabel);
		
		JLabel statBorder = new JLabel();
		statBorder.setBounds(10, 51, 301, 43);
		statBorder.setBorder(new LineBorder(Color.black));
		characterStatsPanel.add(statBorder);

		JPanel inventoryPanel = new JPanel();
		inventoryPanel.setBounds(10, 179, 321, 37);
		add(inventoryPanel);
		inventoryPanel.setLayout(null);
		inventoryPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		JLabel inventoryLabel = new JLabel("Inventory");
		inventoryLabel.setBounds(49, 5, 219, 27);
		inventoryPanel.add(inventoryLabel);
		inventoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
		inventoryLabel.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 24));

		JLabel inventoryImage = new JLabel();
		inventoryImage.setBounds(0, 0, 321, 330);
		inventoryImage.setIcon(this.inventoryImage);
		inventoryStatsPanel.add(inventoryImage);
		
		JButton btnSave = new JButton("Save");
		btnSave.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 11));
		btnSave.setBounds(143, 568, 88, 70);
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

			}
		});
		add(btnSave);
		
		JButton btnClose = new JButton("Close");
		btnClose.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 11));
		btnClose.setBounds(241, 568, 88, 70);
		btnClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(frame instanceof World) {
					World world = (World)frame;
					world.loadJLabels();
				}
				setVisible(false);
			}
		});
		add(btnClose);
	}

	public void loadImages() {
		try {                
			this.inventoryImage = new ImageIcon(ImageIO.read(new File("inventory.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
