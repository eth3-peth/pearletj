package hk.zdl.crypto.pearlet.component.account_settings.signum;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.greenrobot.eventbus.EventBus;

import hk.zdl.crypto.pearlet.component.event.AccountListUpdateEvent;
import hk.zdl.crypto.pearlet.misc.IndepandentWindows;
import hk.zdl.crypto.pearlet.persistence.MyDb;
import hk.zdl.crypto.pearlet.ui.UIUtil;
import hk.zdl.crypto.pearlet.util.CrptoNetworks;
import hk.zdl.crypto.pearlet.util.CryptoUtil;
import hk.zdl.crypto.pearlet.util.Util;

public class WatchSignumAccount {

	private static final Insets insets_5 = new Insets(5, 5, 5, 5);
	public static final void create_watch_account_dialog(Component c, CrptoNetworks nw) {
		var w = SwingUtilities.getWindowAncestor(c);
		var dialog = new JDialog(w, "Watch Account", Dialog.ModalityType.APPLICATION_MODAL);
		dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		IndepandentWindows.add(dialog);
		var panel = new JPanel(new GridBagLayout());
		panel.add(new JLabel(UIUtil.getStretchIcon("icon/" + "eyeglasses.svg", 64, 64)), new GridBagConstraints(0, 0, 1, 4, 0, 0, 17, 0, insets_5, 0, 0));
		var label_1 = new JLabel("Network:");
		var network_combobox = new JComboBox<>(new CrptoNetworks[] {nw});
		network_combobox.setEnabled(false);
		panel.add(label_1, new GridBagConstraints(1, 0, 1, 1, 0, 0, 17, 0, insets_5, 0, 0));
		panel.add(network_combobox, new GridBagConstraints(2, 0, 1, 1, 0, 0, 17, 0, insets_5, 0, 0));
		var text_field = new JTextField(30);
		panel.add(text_field, new GridBagConstraints(1, 1, 4, 3, 0, 0, 10, 1, new Insets(5, 5, 0, 5), 0, 0));
		var btn_1 = new JButton("OK");
		btn_1.addActionListener(e -> Util.submit(() -> {
			String text = text_field.getText().trim();

			boolean b = false;
			byte[] public_key, private_key = new byte[] {};
			try {
				public_key = CryptoUtil.getPublicKeyFromAddress(nw, text);
				if (public_key == null) {
					throw new Exception("Reed-Solomon address does not contain public key");
				}
				b = MyDb.insertAccount(nw, CryptoUtil.getAddress(nw, public_key),public_key, private_key);
			} catch (Exception x) {
				JOptionPane.showMessageDialog(dialog, x.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			if (b) {
				dialog.dispose();
				Util.submit(() -> EventBus.getDefault().post(new AccountListUpdateEvent(MyDb.getAccounts())));
			} else {
				JOptionPane.showMessageDialog(dialog, "Duplicate Entry!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}));
		var panel_1 = new JPanel(new BorderLayout());
		panel_1.add(panel, BorderLayout.CENTER);
		var panel_2 = new JPanel(new FlowLayout());
		panel_2.add(btn_1);
		panel_1.add(panel_2, BorderLayout.SOUTH);

		dialog.add(panel_1);
		dialog.pack();
		dialog.setResizable(false);
		dialog.setLocationRelativeTo(w);
		dialog.setVisible(true);
	}

}
