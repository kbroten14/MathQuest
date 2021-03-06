package MathQuest.GUI;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JCheckBox;

import MathQuest.MathQuest;


import MathQuest.Pages.Area;
import MathQuest.Pages.World;
import MathQuest.Logic.Character;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class OptionsMenu extends JPanel {

	private static final long serialVersionUID = 1L;

	public OptionsMenu(final Area frame) {
		
		this.setBackground(Color.LIGHT_GRAY);
		this.setBounds(343, 296, 338, 177);
		setLayout(null);	

		JLabel optionsLabel = new JLabel("Options");
		optionsLabel.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 12));
		optionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		optionsLabel.setBorder(new LineBorder(Color.BLACK));
		optionsLabel.setBounds(0, 0, 338, 22);
		this.add(optionsLabel);

		JPanel optionsBody = new JPanel();
		optionsBody.setBounds(0, 21, 338, 156);
		optionsBody.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null), new BevelBorder(BevelBorder.LOWERED, null, null, null, null)));
		this.add(optionsBody);
		optionsBody.setLayout(null);

		JLabel volumeLabel = new JLabel("Volume:");
		volumeLabel.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 11));
		volumeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		volumeLabel.setBounds(10, 11, 80, 22);
		optionsBody.add(volumeLabel);
		
		final JCheckBox checkboxMuteSound = new JCheckBox("Mute Sound");
		
		final JSlider volumeSlider = new JSlider();
		volumeSlider.setPaintTicks(true);
		volumeSlider.setSnapToTicks(true);
		volumeSlider.setValue((int)(MathQuest.getVolume() * 10));
		volumeSlider.setMinorTickSpacing(1);
		volumeSlider.setMaximum(10);
		volumeSlider.setBounds(100, 11, 228, 22);
		volumeSlider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				double volume = (double)volumeSlider.getValue()/10;
				MathQuest.setVolume(volume);
				if(volume < .1)
					checkboxMuteSound.setSelected(true);
				else
					checkboxMuteSound.setSelected(false);
			}	
		});
		optionsBody.add(volumeSlider);

		
		checkboxMuteSound.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 11));
		checkboxMuteSound.setBounds(100, 40, 118, 23);
		checkboxMuteSound.setSelected(MathQuest.isMuted);
		checkboxMuteSound.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(checkboxMuteSound.isSelected()) {
					volumeSlider.setValue(0);
					MathQuest.setVolume(0);
					volumeSlider.setEnabled(false);
				}
				else {
					volumeSlider.setValue(10);
					MathQuest.setVolume(1);
					volumeSlider.setEnabled(true);
				}
			}
		});
		optionsBody.add(checkboxMuteSound);

		JLabel lblReset = new JLabel("System:");
		lblReset.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 11));
		lblReset.setHorizontalAlignment(SwingConstants.CENTER);
		lblReset.setBounds(10, 91, 80, 14);
		optionsBody.add(lblReset);

		JButton btnDeleteCharacter = new JButton("<html><center>Delete<br/>Character</center></html>");
		btnDeleteCharacter.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 11));
		btnDeleteCharacter.setBounds(100, 80, 89, 35);
		btnDeleteCharacter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				MathQuest.setCharacter(new Character());
				InventoryPanel.setArmorImage(null);
				InventoryPanel.setFeetImage(null);
				InventoryPanel.setGloveImage(null);
				InventoryPanel.setHelmetImage(null);
				InventoryPanel.setWeaponImage(null);
				MathQuest.switchToGameWorld();
			}
		});
		optionsBody.add(btnDeleteCharacter);
		
		JButton btnchangepassword = new JButton("<html><center>Change<br/>Password</center></html>");
		btnchangepassword.setFont(new Font("Copperplate Gothic Light", Font.PLAIN, 11));
		btnchangepassword.setBounds(210, 80, 89, 35);
		btnchangepassword.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				MathQuest.switchToChangePassword();
			}
		});
		optionsBody.add(btnchangepassword);
		optionsBody.add(btnchangepassword);

		JButton btnClose = new JButton("<html><center>X</center></html>");
		btnClose.setBounds(300, 2, 36, 18);
		btnClose.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Area.toggleOptions();
				World world = (World)frame;
				world.loadJLabels();
				world.renderBackground();
			}
		});
		add(btnClose);
	}
}
