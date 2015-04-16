package MathQuest.Pages;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

import javazoom.jlgui.basicplayer.BasicPlayerException;
import MathQuest.MathQuest;
import MathQuest.Database.Database;
import MathQuest.GUI.CharacterPanel;
import MathQuest.GUI.LogPanel;
import MathQuest.GUI.OptionsPanel;
import MathQuest.Logic.Character;
import MathQuest.Logic.Character.DamageType;
import MathQuest.Logic.Equation;
import MathQuest.Logic.Equation.Sign;
import MathQuest.Logic.Equation.Digits;
import MathQuest.Logic.Equation.Terms;
import MathQuest.Logic.Item;
import MathQuest.Logic.Loot;

import java.awt.Font;

public class Combat extends Area {

	private static final long serialVersionUID = 1L;

	private CharacterPanel creaturePanel;
	private LogPanel combatLog;
	private JPanel combatOptions;
	private ImageIcon victoryIcon;
	private ImageIcon defeatIcon;
	private ImageIcon potionIcon;
	private ImageIcon levelUpIcon;
	private ImageIcon runAwayIcon;
	private ImageIcon attackIcon;
	private Character hero;
	private Character creature;
	private String creatureName;
	private Integer answer;
	private boolean isFirstToAct;

	public Combat(Character hero, Character creature) {

		super(hero, MathQuest.class.getResource("Files/combatMusic" + (RANDOM.nextInt(3) + 1) + ".mp3"));
		this.loadImages();
		this.hero = hero;
		this.creature = creature;
		this.creatureName = creature.getName();

		this.creaturePanel = loadMonsterPanel(this.creature);
		add(creaturePanel);

		this.combatOptions = new JPanel();
		this.loadCombatOptions();
		add(combatOptions);

		this.combatLog = new LogPanel("Combat Log");
		combatLog.addTextToScrollPane("You have entered combat!");
		add(combatLog);

		this.renderBackground();

		this.isFirstToAct = hero.getLevel() >= creature.getLevel();
		if(isFirstToAct)
			combatLog.addTextToScrollPane("It is your turn to act.");
		else
			combatLog.addTextToScrollPane("A(n) " + creature.getName() + " will attack first!");
	}

	private Boolean checkDamage(int damage) {
		return (damage > 0);
	}

	private void promptQuestion() {

		combatLog.addTextToScrollPane("You try to find your opponent's weakness.");

		String question;
		if(MathQuest.connectToDatabase){
			String[] equationSettings = Database.getFormulaFromCache(creature.getLevel());
			if(equationSettings == null)
				question = Equation.constructEquation(Sign.ADDITION, Digits.ONE, Terms.TWO);
			else
				question = Equation.constructEquation(equationSettings);
		}
		else
			question = Equation.constructEquation(Sign.ADDITION, Digits.ONE, Terms.TWO);

		this.answer = Equation.solveEquation(question);
		ArrayList<Integer> options = new ArrayList<Integer>();
		boolean correctAnswerAdded = false;

		for(int i = 0; i < 3; i++) {

			double random = Math.random();

			if(random <  .33 && !correctAnswerAdded || i == 2 && !correctAnswerAdded) {
				correctAnswerAdded = true;
				options.add(this.answer);
			}
			else {
				Integer wrongAnswer;
				do {
					wrongAnswer = Equation.generateWrongAnswer(question);
				}
				while(options.contains(wrongAnswer));
				options.add(wrongAnswer);
			}
		}

		this.reloadCombatOptions(options);
		combatLog.addTextToScrollPane("Solve: " + question);
	}


	private void setAttacker() {
		this.isFirstToAct = true;
	}
	
	private void loadCombatOptions() {

		combatOptions.setBounds(587, 612, 269, 77);
		combatOptions.setLayout(null);
		combatOptions.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));

		JButton attackButton = new JButton(this.attackIcon);
		attackButton.setBounds(3, 3, 88, 70);
		attackButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!isFirstToAct) {
					monsterAttack();
					promptQuestion();
				}
				else {
				 	promptQuestion();		
				}
			}
		});
		combatOptions.add(attackButton);

		JButton runAwayButton = new JButton(this.runAwayIcon);
		runAwayButton.setBounds(177, 3, 88, 70);
		runAwayButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				MathQuest.switchToGameWorld();
			}
		});
		combatOptions.add(runAwayButton);

		JButton usePotionButton = new JButton(this.potionIcon);
		usePotionButton.setBounds(90, 3, 88, 70);		
		usePotionButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int potions = hero.getPotions();
				if(potions > 0) {
					if(!isFirstToAct) {
						monsterAttack();
					}
					int currentHealth = hero.getCurrentHealth();
					int maxHealth = hero.getMaxHealth();
					int potionHealth = (int)(maxHealth * .3);
					currentHealth = currentHealth + potionHealth;
					if(currentHealth > maxHealth)
						currentHealth = maxHealth;
					hero.setPotions(potions - 1);
					hero.setCurrentHealth(currentHealth);
					combatLog.addTextToScrollPane("You drank a potion and gained " + potionHealth + " health!");	
					monsterAttack();
				}
				else
					combatLog.addTextToScrollPane("You don't have any potions to use!");	
			}
		});
		combatOptions.add(usePotionButton);
	}

	private void reloadCombatOptions(final ArrayList<Integer> mathAnswers) {

		this.combatOptions.removeAll();

		if(null == mathAnswers) {
			loadCombatOptions();
		}
		else {

			JButton answerOne = new JButton(mathAnswers.get(0).toString());
			answerOne.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 11));
			answerOne.setBounds(3, 3, 88, 70);
			answerOne.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					attack(mathAnswers.get(0));
					reloadCombatOptions(null);
				}
			});
			combatOptions.add(answerOne);

			JButton answerTwo = new JButton(mathAnswers.get(1).toString());
			answerTwo.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 11));
			answerTwo.setBounds(177, 3, 88, 70);
			answerTwo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					attack(mathAnswers.get(1));
					reloadCombatOptions(null);
				}
			});
			combatOptions.add(answerTwo);

			JButton answerThree = new JButton(mathAnswers.get(2).toString());
			answerThree.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 11));
			answerThree.setBounds(90, 3, 88, 70);	
			answerThree.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					attack(mathAnswers.get(2));
					reloadCombatOptions(null);
				}
			});
			combatOptions.add(answerThree);
			combatOptions.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
		}
	}

	private void attack(Integer answer) {

		int damage = hero.calculateDamage();
		Boolean damageOutcome = checkDamage(damage);

		combatLog.addTextToScrollPane("You answered " + answer + ".");

		if(answer == this.answer) {
			combatLog.addTextToScrollPane("Correct! You strike your enemy with great power!");
			hero.incrementAnsweredCorrectly();
			damage = 2 * damage;
			damageOutcome = null;
		}
		else {
			combatLog.addTextToScrollPane("Good try, but the correct answer was " + this.answer + ".");
			hero.incrementAnsweredIncorrectly();
		}

		this.playAttackSound(hero, damageOutcome);

		String output = new String("You attack a " + this.creatureName + " for " + damage + " points of damage.");
		combatLog.addTextToScrollPane(output);

		creature.takeDamage(damage);
		this.reloadMonsterPanel();

		if(creature.getCurrentHealth() <= 0) {
			this.victory();
		}
		else {
			this.monsterAttack();
		}
	}

	private void monsterAttack() {

		int damage = creature.calculateDamage();
		Boolean damageOutcome = checkDamage(damage);
		
		this.playAttackSound(creature, damageOutcome);

		String output = "A(n) " + creature.getName() + " attacks YOU for " + damage + " points of damage.";
		combatLog.addTextToScrollPane(output);
		MathQuest.getCharacter().takeDamage(damage);
		this.reloadCharacterPanel();

		if(MathQuest.getCharacter().getCurrentHealth() <= 0) {
			this.defeat();
		}
		else if(!isFirstToAct) {
			setAttacker();
		}
		else {
			combatLog.addTextToScrollPane("It is your turn to act.");
		}
	}

	private CharacterPanel loadMonsterPanel(Character monster) {

		CharacterPanel monsterPanel = new CharacterPanel(this, monster, false, true);
		monsterPanel.setLayout(null);
		monsterPanel.setBounds(907, 6, 107, 144);
		return monsterPanel;
	}

	private void reloadMonsterPanel() {

		if(this.creaturePanel != null)
			this.remove(this.creaturePanel);
		this.creaturePanel = loadMonsterPanel(this.creature);
		this.add(creaturePanel);
		this.renderBackground();
	}

	public void playAttackSound(Character character, Boolean damageOutcome) {

		String combatSound;
		DamageType damageType = character.getDamageType();

		switch (damageType) {
		case CRUSHING:
			if (damageOutcome == null) {	
				combatSound = "/crushing2.mp3";
			}
			else if (damageOutcome == false) {
				combatSound = "/crushing0.mp3";
			}
			else {
				combatSound = "/crushing1.mp3";
			}
			break;
		case SLASHING:
			if (damageOutcome == null) {
				combatSound = "/slashing3.mp3";
			}
			else if (damageOutcome == false) {
				combatSound = "/slashing0.mp3";
			}
			else {
				combatSound = "/slashing" + (RANDOM.nextInt(2) + 1) + ".mp3";
			}
			break;
		case MAGICAL:
			if (damageOutcome == null){	
				combatSound = "/magic4.mp3";
			}
			else if (damageOutcome == false) {
				combatSound = "/magic0.mp3";
			}
			else {
				combatSound = "/magic" + (RANDOM.nextInt(3) + 1) + ".mp3";
			}
			break;
		default:
			throw new IllegalArgumentException();
		}
		
		combatSound = String.format("Files" + combatSound);
		
		if(character == hero) {
			try {
				soundPlayer.open(MathQuest.class.getResource(combatSound));
				soundPlayer.play();
				soundPlayer.setGain(MathQuest.getVolume());
			}
			catch(BasicPlayerException e) {
				e.printStackTrace();
			}	
		}
		else {
			try {
				effectPlayer.open(MathQuest.class.getResource(combatSound));
				effectPlayer.play();
				effectPlayer.setGain(MathQuest.getVolume());
			}
			catch(BasicPlayerException e) {
				e.printStackTrace();
			}	
		}

	}

	private void victory() {

		try {
			musicPlayer.open(MathQuest.class.getResource("Files/victory.mp3"));
			musicPlayer.play();
			musicPlayer.setGain(MathQuest.getVolume());
		}
		catch(BasicPlayerException e) {
			e.printStackTrace();
		}

		int experience = (int)(creature.getMaxExperience() * .5);
		int gold = creature.getGold();
		hero.addGold(gold);
		int level = hero.getLevel();
		hero.gainExperience(experience);

		String victoryString; 
		Item droppedItem = Loot.getLoot(this.creature.getLevel());

		if(null == droppedItem) {
			victoryString = String.format("<html>You are victorious! You check your<br/>enemy for gold and head back to<br/>town. You receive:<br/><Center><br/>%d XP<br/>%d Gold<br/></Center></html>", experience, gold);
		}
		else {
			victoryString = String.format("<html>You are victorious! You check your<br/>enemy for gold and head back to<br/>town. You receive:<br/><Center><br/>%d XP<br/>%d Gold<br/><font color='%s'>[%s]</font><br/></Center></html>", experience, gold, droppedItem.getColor(), droppedItem);
			hero.addToInventory(droppedItem);
		}

		JOptionPane.showMessageDialog(this, 
				new JLabel(victoryString, JLabel.CENTER), 
				"Victory", 
				JOptionPane.PLAIN_MESSAGE,
				victoryIcon);

		if (level != hero.getLevel()) {

			this.reloadCharacterPanel();
			victoryString = String.format("<html>Congratulations! You have gained a<br/>level! You are now level %d.</html>", hero.getLevel());

			try {
				effectPlayer.open(MathQuest.class.getResource("Files/levelUp.mp3"));
				effectPlayer.play();
				effectPlayer.setGain(MathQuest.getVolume());
			}
			catch(BasicPlayerException e) {
				e.printStackTrace();
			}

			JOptionPane.showMessageDialog(this, 
					new JLabel(victoryString, JLabel.CENTER), 
					"You Feel Stronger...", 
					JOptionPane.PLAIN_MESSAGE,
					levelUpIcon);
		}

		MathQuest.switchToGameWorld();
	}

	private void defeat() {

		try {
			musicPlayer.open(MathQuest.class.getResource("Files/defeat.mp3"));
			musicPlayer.play();
			musicPlayer.setGain(MathQuest.getVolume());
		}
		catch(BasicPlayerException e) {
			e.printStackTrace();
		}

		hero.death();

		JOptionPane.showMessageDialog(this, 
				new JLabel("<html>You have been defeated in battle!<br/>A good samaritan finds you and<br/>nurses you back to health. You<br/>have lost gold and experience!</html>", JLabel.CENTER), 
				"Defeat", 
				JOptionPane.PLAIN_MESSAGE,
				defeatIcon);

		MathQuest.switchToGameWorld();
	}

	@Override
	public OptionsPanel loadOptionsPanel() {
		return null;
	}

	@Override
	public void loadImages() {

		Integer pictureNumber = RANDOM.nextInt(8) + 1;        
		this.background = new ImageIcon(MathQuest.class.getResource("Files/combat" + pictureNumber + ".jpg"));
		this.victoryIcon = new ImageIcon(MathQuest.class.getResource("Files/victoryIcon.png"));
		this.defeatIcon = new ImageIcon(MathQuest.class.getResource("Files/defeatIcon.png"));
		this.potionIcon = new ImageIcon(MathQuest.class.getResource("Files/potion.png"));
		this.attackIcon = new ImageIcon(MathQuest.class.getResource("Files/attack.png"));
		this.runAwayIcon = new ImageIcon(MathQuest.class.getResource("Files/runAway.png"));
		this.levelUpIcon = new ImageIcon(MathQuest.class.getResource("Files/levelUp.png"));
	}
}
