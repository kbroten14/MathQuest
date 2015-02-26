package MathQuest;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import MathQuest.GUI.Blacksmith;
import MathQuest.GUI.CombatPanel;
import MathQuest.GUI.Inn;
import MathQuest.GUI.KillingFields;
import MathQuest.GUI.LoginPage;
import MathQuest.GUI.World;
import MathQuest.Logic.Character;

public class MathQuest
{
	private static final Dimension FRAME_DIMENSIONS = new Dimension(1024, 768);
	
	private static JFrame outerFrame;
	private static JPanel contentPane;
	private static String username;
	private static String password;
	private static Character hero;
	
	public MathQuest() {
		initializeMathQuest();
	}

	private void initializeMathQuest() {
		outerFrame = new JFrame("MathQuest");
		outerFrame.setSize(FRAME_DIMENSIONS);
		outerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		outerFrame.setLocationRelativeTo(null);
		outerFrame.setResizable(false);
		contentPane = new LoginPage();
		outerFrame.setContentPane(contentPane);
		outerFrame.setVisible(true);
	}

	public static Dimension getDimensions() {
		return FRAME_DIMENSIONS;
	}

	public static void switchToGameWorld() {
		contentPane = new World(hero);
		outerFrame.setContentPane(contentPane);
		contentPane.revalidate();
	}

	public static void switchToInn() {
		contentPane = new Inn(hero);
		outerFrame.setContentPane(contentPane);
		contentPane.revalidate();
	}

	public static void switchToBlacksmith() {
		contentPane = new Blacksmith(hero);
		outerFrame.setContentPane(contentPane);
		contentPane.revalidate();
	}

	public static void switchToKillingFields() {
		contentPane = new KillingFields(hero);
		outerFrame.setContentPane(contentPane);
		contentPane.revalidate();
	}

	public static void switchToInventory() {

	}

	public static void switchToOptions() {

	}

	public static void switchToCombat(Character creature) {
		contentPane = new CombatPanel(hero, creature);
		outerFrame.setContentPane(contentPane);
		contentPane.revalidate();
	}

	public static String getUsername() {
		return username;
	}
	
	public static void setUsername(String name) {
		username = name;
	}
	
	public static String getPassword() {
		return password;
	}
	
	public static void setPassword(String pass) {
		password = pass;
	}
	
	public static Character getCharacter() {
		return hero;
	}
	
	public static void setCharacter(Character character) {
		hero = character;
	}
	
	public static void disableComponents() {
		for(Component el : outerFrame.getComponents()) 
			el.setEnabled(false);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater (
				new Runnable() {
					@Override
					public void run() {
						new MathQuest();
					}
				}
				);	
	}
}
