package hk.zdl.crpto.pearlet.component;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.apache.commons.io.IOUtils;
import org.jdesktop.swingx.combobox.ListComboBoxModel;

import hk.zdl.crpto.pearlet.MyToolbar;
import hk.zdl.crpto.pearlet.misc.IndepandentWindows;

@SuppressWarnings("serial")
public class SettingsPanel extends JTabbedPane {

	public SettingsPanel() {
		addTab("Networks", initNetworkPanel());
		addTab("Accounts", initAccountPanel());
	}

	private static final Component initNetworkPanel() {
		var panel = new JPanel(new GridLayout(0, 1));
		List<String> nws = Arrays.asList();
		try {
			nws = IOUtils.readLines(SettingsPanel.class.getClassLoader().getResourceAsStream("networks.txt"), "UTF-8");
		} catch (IOException e) {
		}
		nws.stream().map(SettingsPanel::init_network_UI_components).forEach(panel::add);
		var panel_1 = new JPanel(new FlowLayout());
		panel_1.add(panel);
		return panel_1;
	}

	@SuppressWarnings("unchecked")
	private static final Component init_network_UI_components(String network_name) {
		var panel = new JPanel(new GridBagLayout());
		var label = new JLabel(network_name);
		label.setHorizontalTextPosition(SwingConstants.LEFT);
		panel.add(label, new GridBagConstraints(0, 0, 1, 1, 0, 0, 17, 0, new Insets(5, 0, 0, 0), 0, 0));
		var combo_box = new JComboBox<String>();
		combo_box.setEditable(true);
		combo_box.setPreferredSize(new Dimension(300, 20));
		panel.add(combo_box, new GridBagConstraints(0, 1, 3, 1, 0, 0, 10, 0, new Insets(5, 0, 5, 0), 0, 0));
		var btn = new JButton("Select Node");
		panel.add(btn, new GridBagConstraints(3, 1, 1, 1, 0, 0, 10, 0, new Insets(5, 5, 5, 5), 0, 0));
		List<String> nws = Arrays.asList();
		try {
			nws = IOUtils.readLines(SettingsPanel.class.getClassLoader().getResourceAsStream("network/" + network_name + ".txt"), "UTF-8");
		} catch (IOException e) {
		}
		combo_box.setModel(new ListComboBoxModel<String>(nws));
		if (network_name.equals("web3j")) {
			var opt_btn = new JButton("ID / Secret");
			panel.add(opt_btn, new GridBagConstraints(1, 0, 1, 1, 0, 0, 10, 0, new Insets(5, 5, 5, 5), 0, 0));
			opt_btn.addActionListener(e -> createWeb3jAuthDialog(panel));
		}
		return panel;
	}

	private static final void createWeb3jAuthDialog(Component c) {
		var dialog = new JDialog((Frame) SwingUtilities.getRoot(c), "Enter Project ID & Secret", true);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		IndepandentWindows.getInstance().add(dialog);
		var panel_1 = new JPanel(new GridBagLayout());
		try {
			panel_1.add(new JLabel(new MyStretchIcon(ImageIO.read(MyToolbar.class.getClassLoader().getResource("icon/" + "key_1.svg")), 64, 64)),
					new GridBagConstraints(0, 0, 1, 4, 0, 0, 17, 0, new Insets(0, 5, 5, 5), 0, 0));
		} catch (IOException e) {
		}
		panel_1.add(new JLabel("Project ID:"), new GridBagConstraints(1, 0, 1, 1, 0, 0, 17, 0, new Insets(0, 5, 5, 5), 0, 0));
		var id_field = new JTextField(30);
		panel_1.add(id_field, new GridBagConstraints(1, 1, 1, 1, 0, 0, 17, 0, new Insets(0, 5, 5, 5), 0, 0));
		panel_1.add(new JLabel("Project Secret:"), new GridBagConstraints(1, 2, 1, 1, 0, 0, 17, 0, new Insets(0, 0, 5, 0), 0, 0));
		var scret_field = new JPasswordField(30);
		panel_1.add(scret_field, new GridBagConstraints(1, 3, 1, 1, 0, 0, 17, 0, new Insets(0, 5, 5, 5), 0, 0));
		var btn_1 = new JButton("OK");
		btn_1.addActionListener(e -> {
			dialog.dispose();
		});
		panel_1.add(btn_1, new GridBagConstraints(0, 4, 2, 1, 0, 0, 10, 0, new Insets(5, 5, 10, 5), 0, 0));
		dialog.add(panel_1);
		dialog.pack();
		dialog.setResizable(false);
		dialog.setLocationByPlatform(true);
		dialog.setVisible(true);
	}

	private static final Component initAccountPanel() {
		var panel = new JPanel();
		return panel;
	}
}
