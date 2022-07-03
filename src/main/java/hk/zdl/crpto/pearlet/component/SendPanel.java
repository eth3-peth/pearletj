package hk.zdl.crpto.pearlet.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.stream.Stream;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import hk.zdl.crpto.pearlet.MyToolbar;

@SuppressWarnings("serial")
public class SendPanel extends JPanel {

	private static final Dimension FIELD_DIMENSION = new Dimension(500, 20);

	public SendPanel() {
		super(new FlowLayout());
		var panel_1 = new JPanel(new GridBagLayout());
		add(panel_1);
		var label_1 = new JLabel("Account");
		panel_1.add(label_1, newGridConst(0, 0, 3, 17));
		var label_2 = new JLabel("Balance");
		panel_1.add(label_2, newGridConst(3, 0, 1, 10));
		var label_3 = new JLabel("Token");
		panel_1.add(label_3, newGridConst(4, 0, 1, 17));
		var acc_combo_box = new JComboBox<>();
		acc_combo_box.setPreferredSize(new Dimension(300, 20));
		panel_1.add(acc_combo_box, newGridConst(0, 1, 3));
		var balance_label = new JLabel("123456");
		balance_label.setPreferredSize(new Dimension(100, 20));
		balance_label.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_1.add(balance_label, newGridConst(3, 1, 1));
		var token_combo_box = new JComboBox<>();
		token_combo_box.setPreferredSize(new Dimension(100, 20));
		panel_1.add(token_combo_box, newGridConst(4, 1));

		var label_4 = new JLabel("Recipant");
		panel_1.add(label_4, newGridConst(0, 2, 3, 17));
		var rcv_field = new JTextField();
		rcv_field.setPreferredSize(FIELD_DIMENSION);
		panel_1.add(rcv_field, newGridConst(0, 3, 5));

		var label_5 = new JLabel("Amount");
		panel_1.add(label_5, newGridConst(0, 4, 3, 17));
		var amt_field = new JTextField();
		amt_field.setPreferredSize(FIELD_DIMENSION);
		panel_1.add(amt_field, newGridConst(0, 5, 5));

		var label_6 = new JLabel("Fee");
		panel_1.add(label_6, newGridConst(0, 6, 3, 17));
		var fee_field = new JTextField();
		var fee_panel = new JPanel(new BorderLayout());
		fee_panel.setPreferredSize(FIELD_DIMENSION);
		var fee_button_panel = new JPanel(new GridLayout(1, 0));
		var fee_btn_cheap = new JToggleButton("Cheap");
		var fee_btn_stand = new JToggleButton("Standard");
		var fee_btn_priot = new JToggleButton("Priority");
		var btn_gp_0 = new ButtonGroup();
		Stream.of(fee_btn_cheap, fee_btn_stand, fee_btn_priot).forEach(btn_gp_0::add);
		Stream.of(fee_btn_cheap, fee_btn_stand, fee_btn_priot).forEach(fee_button_panel::add);

		fee_panel.add(fee_field, BorderLayout.CENTER);
		fee_panel.add(fee_button_panel, BorderLayout.EAST);
		panel_1.add(fee_panel, newGridConst(0, 7, 5));

		var panel_2 = new JPanel(new BorderLayout());
		var panel_3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		var msg_chk_box = new JCheckBox("Add a Message");
		msg_chk_box.setPreferredSize(new Dimension(150, 30));
		panel_3.add(msg_chk_box);
		var msg_option_btn = new JButton("↓");
		var msg_option_popup = new JPopupMenu();
		var eny_msg_menu_item = new JCheckBoxMenuItem("Encrypt");
		msg_option_popup.add(eny_msg_menu_item);
		var eny_msg_sub_menu = new JMenu("Send as");
		msg_option_popup.add(eny_msg_sub_menu);
		var plain_text_option_menu_item = new JRadioButtonMenuItem("Plain text", true);
		eny_msg_sub_menu.add(plain_text_option_menu_item);
		var base64_option_menu_item = new JRadioButtonMenuItem("Base64", false);
		eny_msg_sub_menu.add(base64_option_menu_item);
		var btn_gp_1 = new ButtonGroup();
		Stream.of(plain_text_option_menu_item, base64_option_menu_item).forEach(btn_gp_1::add);

		msg_option_btn.addActionListener((e) -> msg_option_popup.show(msg_option_btn, 0, 0));
		panel_3.add(msg_option_btn);

		var label_7 = new JLabel("0/1000");
		label_7.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_2.add(label_7, BorderLayout.EAST);
		panel_2.add(panel_3, BorderLayout.CENTER);
		panel_2.setPreferredSize(new Dimension(500, 40));
		panel_1.add(panel_2, newGridConst(0, 8, 5, 17));

		var msg_area = new JTextArea();
		var msg_scr = new JScrollPane(msg_area);
		msg_scr.setPreferredSize(new Dimension(500, 200));
		panel_1.add(msg_scr, newGridConst(0, 9, 5));

		var label_8 = new JLabel("Enter PIN");
		panel_1.add(label_8, newGridConst(0, 10, 1, 17));
		var pw_field = new JPasswordField();
		pw_field.setPreferredSize(FIELD_DIMENSION);
		panel_1.add(pw_field, newGridConst(0, 11, 5));

		var send_btn = new JButton("Send", MyToolbar.getIcon("paper-plane-solid.svg"));
		send_btn.setFont(new Font("Arial Black", Font.PLAIN, 32));
		send_btn.setMultiClickThreshhold(300);
		panel_1.add(send_btn, new GridBagConstraints(0, 12, 5, 1, 0, 0, 10, 0, new Insets(5, 0, 5, 0), 0, 0));

		SwingUtilities.invokeLater(() -> {
			Stream.of(msg_option_btn, label_7, msg_scr).forEach(o -> o.setVisible(false));
			msg_chk_box.addActionListener((e) -> Stream.of(msg_option_btn, label_7, msg_scr).forEach(o -> o.setVisible(msg_chk_box.isSelected())));
			msg_area.addKeyListener(new KeyListener() {

				@Override
				public void keyTyped(KeyEvent e) {
				}

				@Override
				public void keyPressed(KeyEvent e) {
				}

				@Override
				public void keyReleased(KeyEvent e) {
					int i = msg_area.getText().getBytes().length;
					label_7.setText(i + "/" + "1000");
					label_7.setForeground(i > 1000 ? Color.red : Color.black);
				}
			});
		});

	}

	private static final GridBagConstraints newGridConst(int x, int y) {
		var a = new GridBagConstraints();
		a.gridx = x;
		a.gridy = y;
		return a;
	}

	private static final GridBagConstraints newGridConst(int x, int y, int width) {
		var a = new GridBagConstraints();
		a.gridx = x;
		a.gridy = y;
		a.gridwidth = width;
		return a;
	}

	private static final GridBagConstraints newGridConst(int x, int y, int width, int anchor) {
		var a = new GridBagConstraints();
		a.gridx = x;
		a.gridy = y;
		a.gridwidth = width;
		a.anchor = anchor;
		return a;
	}

}