package MathQuest.Logic;

import java.util.ArrayList;

public class Character {

	public enum DamageType {
		CRUSHING, SLASHING, MAGICAL
	}

	private int level;
	private int maxExperience;
	private int currentExperience;
	private int maxHealth;
	private int currentHealth;
	private int vitality;
	private int strength;
	private int gold;
	private String name;
	private String imagePath;
	private double answeredCorrectly;
	private double answeredIncorrectly;
	private DamageType damageType;
	private Item equippedHelmet;
	private Item equippedMail;
	private Item equippedWeapon;
	private Item equippedBoots;
	private Item equippedGloves;
	private ArrayList<Item> inventory;

	public Character() {
		this.currentExperience = 0;
		this.maxExperience = 10;
		this.strength = 10;
		this.vitality = 10;
		this.gold = 0;
		this.currentHealth = this.vitality;
		this.maxHealth = this.vitality;
		this.level = 1;
		this.imagePath = "char2.jpg";
		this.name = "Hero#1";
		this.damageType = DamageType.SLASHING;
		this.answeredCorrectly = 0;
		this.answeredIncorrectly = 0;
		inventory =  new ArrayList<Item>();
	}

	//monster constructor
	public Character(int level, String name, String imagePath, DamageType damageType) {
		this.strength = 10 * level;
		this.gold = 5 * level;
		this.currentHealth = 10 * level;
		this.maxHealth = 10 * level;
		this.level = level;
		this.name = name;
		this.currentExperience = 0;
		this.maxExperience = 10 * level;
		this.imagePath = imagePath;
		this.damageType = damageType;
	}

	// test constructor
	public Character(int currentGold){
		this.currentExperience = 0;
		this.maxExperience = 10;
		this.strength = 10;
		this.vitality = 10;
		this.gold = currentGold;
		this.currentHealth = 1;
		this.maxHealth = this.vitality;
		this.level = 1;
		this.imagePath = "char2.jpg";
		this.name = "Hero#1";
		this.damageType = DamageType.SLASHING;
		this.answeredCorrectly = 0;
		this.answeredIncorrectly = 0;
	}

	public Character(Integer[] charStats, ArrayList<Item> items) {
		this.level = charStats[0];
		this.currentHealth = charStats[1];
		this.currentExperience = charStats[2];
		this.gold = charStats[3];
		this.vitality = 10 * level;
		this.maxHealth = vitality;
		this.strength = 10 + 2 * level;
		this.maxExperience = (int)(10 * Math.pow(2, level));
		this.imagePath = "char2.jpg";
		this.damageType = DamageType.SLASHING;
		this.name = "Hero#1";
		this.answeredCorrectly = 0;
		this.answeredIncorrectly = 0;
		this.inventory = items;
		//System.out.println("getting inventory size " + this.inventory.size());
		for (int i = 0 ; i < this.inventory.size();i++){
			Item el = this.inventory.get(i);
			//System.out.println(el.toString() + " " + el.isEquipped()+" "+i);
			if(el.isEquipped()){
				equip(el);
				i--;
				//System.out.println("equipped " + el.toString());		
			}
		}
	}
	/*switch(item.getSlot()){
	case "Weapons": setEquippedWeapon(item);
	case "Helmets": setEquippedHelmet(item);
	case "Armor":setEquippedMail(item);
	case "Gloves":setEquippedGloves(item);
	case "Boots":setEquippedBoots(item);
	}*/
	public int getMaxExperience(){
		return this.maxExperience;
	}

	public int getCurrentExperience(){
		return this.currentExperience;
	}

	public int getStrength(){
		return this.strength;
	}

	public int getVitality(){
		return this.vitality;
	}

	public void addStrength(int strength) {
		this.strength = this.strength + strength;
	}

	public void addVitality(int vitality){
		this.vitality = this.vitality + vitality;
	}

	public int getMaxHealth(){
		return this.maxHealth;
	}

	public void addMaxHealth(int health) {
		this.maxHealth = this.maxHealth + health; 
	}

	public int getCurrentHealth(){
		return this.currentHealth;
	}

	public int getLevel(){
		return this.level;
	}

	public String getName() {
		return this.name;
	}

	public int getGold(){
		return this.gold;
	}

	public void removeGold(int gold) {
		this.gold = this.gold - gold;
	}

	public void addGold(int gold){
		this.gold = this.gold + gold;
	}

	public DamageType getDamageType() {
		return this.damageType;
	}

	public void setDamageType(DamageType damageType) {
		this.damageType = damageType;
	}

	public void death() {
		this.currentHealth = this.maxHealth;
		this.gold = (int)(this.gold * .5);
		this.currentExperience = 0;
	}

	public void gainExperience(int experience){
		this.currentExperience = this.currentExperience + experience;
		if(currentExperience >= maxExperience){
			this.gainLevel();
		}
	}

	private void gainLevel() {
		this.level++;
		this.addStrength(10);
		this.addMaxHealth(10);
		this.currentHealth = this.maxHealth;
		this.currentExperience = this.currentExperience - this.maxExperience;
		this.maxExperience = this.maxExperience*2;
	}

	public int calculateDamage() {
		double maxDamage = this.strength * .2;
		double damage = this.strength * .2  - ((Math.random()/4) * maxDamage);
		return (int)Math.round(damage);
	}

	public void takeDamage(int damage){
		this.currentHealth = this.currentHealth - damage;
	}

	public Integer[] getStatus() {
		Integer[] stats = new Integer[4];	
		stats[0] = this.level;
		stats[1] = this.currentHealth;
		stats[2] = this.currentExperience;
		stats[3] = this.gold;
		return stats;
	}

	public String getImagePath() {
		return this.imagePath;
	}

	public int calculateCost(String roomType, int level){
		switch(roomType){
		case "Shower": return level*level*1;
		case "Meal": return level*level*3;
		case "Sleep":return level*level*5;
		default: return level*level*2;
		}
	}

	public boolean enoughGold(String roomType){
		if (calculateCost(roomType,this.level) > this.getGold())
			return false;
		else
			return true;
	}

	public void payForInn(String roomType){
		this.removeGold(calculateCost(roomType,this.level));
	}

	public int calculateIncrease (String roomType){

		int increase;

		if (roomType.equalsIgnoreCase("Shower"))
			increase = this.maxHealth*30/100;
		else if (roomType.equalsIgnoreCase("Meal"))
			increase = this.maxHealth*60/100;
		else if (roomType.equalsIgnoreCase("Sleep"))
			increase = this.maxHealth*90/100;
		else
			increase = 0;
		return increase;
	}

	public void healthRegain (String roomType){

		int increase = calculateIncrease(roomType);

		if (this.currentHealth + increase < this.maxHealth)
			this.currentHealth = this.currentHealth + increase;
		else
			this.currentHealth = this.maxHealth;
	}

	public double getQuestionAccuracy() {
		if(this.answeredCorrectly == 0 && this.answeredIncorrectly == 0)
			return 0;
		else 
			return this.answeredCorrectly/(this.answeredCorrectly + this.answeredIncorrectly);
	}

	public int getTotalQuestionsAnswered() {
		return (int)(this.answeredCorrectly + this.answeredIncorrectly);
	}

	public void incrementAnsweredCorrectly() {
		this.answeredCorrectly++;
	}

	public void incrementAnsweredIncorrectly() {
		this.answeredIncorrectly++;
	}

	public Item getEquippedHelmet() {
		return equippedHelmet;
	}

	public void setEquippedHelmet(Item equippedHelmet) {
		if (this.equippedHelmet != null){
			//System.out.println("equipped");
			this.equippedHelmet.disrobe();
		}
		equippedHelmet.equip();
		this.equippedHelmet = equippedHelmet;
		//System.out.println(this.equippedHelmet.isEquipped());
	}

	public Item getEquippedMail() {
		return equippedMail;
	}

	public void setEquippedMail(Item equippedMail) {
		if (this.equippedMail != null)
			this.equippedMail.disrobe();
		equippedMail.equip();
		this.equippedMail = equippedMail;
	}

	public Item getEquippedWeapon() {
		return equippedWeapon;
	}

	public void setEquippedWeapon(Item equippedWeapon) {
		if (this.equippedWeapon != null)
			this.equippedWeapon.disrobe();
		equippedWeapon.equip();
		this.equippedWeapon = equippedWeapon;
	}

	public Item getEquippedBoots() {
		return equippedBoots;
	}

	public void setEquippedBoots(Item equippedBoots) {
		if (this.equippedBoots != null)
			this.equippedBoots.disrobe();
		equippedBoots.equip();
		this.equippedBoots = equippedBoots;
	}

	public Item getEquippedGloves() {
		return equippedGloves;
	}

	public void setEquippedGloves(Item equippedGloves) {
		if (this.equippedGloves != null)
			this.equippedGloves.disrobe();
		equippedGloves.equip();
		this.equippedGloves = equippedGloves;
	}
	//get the inventories without equipped items
	public ArrayList<Item> getInventory() {
		return this.inventory;
	}
	//get the inventories including equipped items
	public ArrayList<Item> getAllInventory(){
		ArrayList<Item> items = new ArrayList<Item>();
		items.addAll(this.inventory);
		if(null != this.equippedBoots)
			items.add(this.equippedBoots);
		if(null != this.equippedGloves){
			items.add(this.equippedGloves);
		//System.out.println("added"+items.size());
		}
		if(null != this.equippedHelmet)
			items.add(this.equippedHelmet);
		if(null != this.equippedMail)
			items.add(this.equippedMail);
		if(null != this.equippedWeapon)
			items.add(this.equippedWeapon);
		return items;
	}
	public void addToInventory(Item item) {
		this.inventory.add(item);
	}

	public void removeFromInventory(Item item) {
		this.inventory.remove(item);
	}

	private void removeItemStats(Item item) {
		this.strength -= item.str;
		this.vitality -= item.vit;
		this.currentHealth -= item.vit;
		this.maxHealth -= item.vit;
	}

	private void addItemStats(Item item) {
		this.strength += item.str;
		this.vitality += item.vit;
		this.currentHealth += item.vit;
		this.maxHealth += item.vit;
	}

	public void equip(Object item) {
		if(null == item) {

		}
		else {
			Item equippable = (Item)item;
			String slot = equippable.getSlot();
			if(slot.equals("Helmets")) {
				if(this.equippedHelmet != null){
					this.inventory.add(this.equippedHelmet);
					this.removeItemStats(this.equippedHelmet);
				}
				this.setEquippedHelmet(equippable);
				this.addItemStats(equippable);
			}
			else if(slot.equals("Armor")) {
				if(this.equippedMail != null){
					this.inventory.add(this.equippedMail);
					this.removeItemStats(this.equippedMail);
				}
				this.setEquippedMail(equippable);
				this.addItemStats(equippable);
			}
			else if(slot.equals("Boots")) {
				if(this.equippedBoots != null){
					this.inventory.add(this.equippedBoots);
					this.removeItemStats(this.equippedBoots);
				}
				this.setEquippedBoots(equippable);
				this.addItemStats(equippable);
			}
			else if(slot.equals("Gloves")) {
				if(this.equippedGloves != null){
					this.inventory.add(this.equippedGloves);
					this.removeItemStats(this.equippedGloves);
				}
				this.setEquippedGloves(equippable);
				this.addItemStats(equippable);
			}
			else {
				if(this.equippedWeapon != null){
					this.inventory.add(this.equippedWeapon);
					this.removeItemStats(this.equippedWeapon);
				}
				this.setEquippedWeapon(equippable);
				this.addItemStats(equippable);
			}
			this.inventory.remove(equippable);
		}
	}
}