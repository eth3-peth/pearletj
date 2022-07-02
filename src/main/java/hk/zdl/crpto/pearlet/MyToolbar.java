package hk.zdl.crpto.pearlet;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import org.json.JSONArray;
import org.json.JSONTokener;

import com.formdev.flatlaf.extras.FlatDesktop;
import com.formdev.flatlaf.extras.FlatDesktop.QuitResponse;

import hk.zdl.crpto.pearlet.component.MyStretchIcon;

@SuppressWarnings("serial")
public class MyToolbar extends JScrollPane {
	private final MainFrameSwitch mfs;
	private final JPanel panel = new JPanel(new GridLayout(0, 1));
	private final Map<String, JToggleButton> buttons = new TreeMap<>();

	public MyToolbar(MainFrameSwitch mfs) {
		this.mfs = mfs;
		init();
	}

	private void init() {
		var x = new JPanel(new BorderLayout());
		x.add(panel, BorderLayout.NORTH);
		x.add(new Container(), BorderLayout.CENTER);
		setViewportView(x);
		setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		init_buttons();
		set_callbacks();
	}

	private void init_buttons() {
		var btn_gp = new ButtonGroup();
		JSONArray jarr = new JSONArray(new JSONTokener(getClass().getClassLoader().getResourceAsStream("toolbar.json")));
		for (int i = 0; i < jarr.length(); i++) {
			String id = jarr.getJSONObject(i).getString("id");
			String text = jarr.getJSONObject(i).getString("text");
			String icon = jarr.getJSONObject(i).getString("icon");
			var btn = new JToggleButton(text, getIcon(icon));
			btn.setBorderPainted(false);
			btn.setFocusPainted(false);
			btn.setFocusable(true);
			btn.addActionListener((e) -> mfs.showComponent(buttons.entrySet().stream().filter(x -> x.getValue() == e.getSource()).findAny().get().getKey()));
			btn.setHorizontalAlignment(SwingConstants.LEFT);
			btn.setMultiClickThreshhold(300);
			buttons.put(id, btn);
			btn_gp.add(btn);
			panel.add(btn);
		}

	}

	private void set_callbacks() {
		FlatDesktop.setAboutHandler(() -> clickButton("about"));
		FlatDesktop.setPreferencesHandler(() -> clickButton("sets"));
		FlatDesktop.setQuitHandler(QuitResponse::performQuit);
	}

	public void clickButton(String str) {
		Optional.ofNullable(buttons.get(str)).ifPresent(JToggleButton::doClick);
	}

	public static final Icon getIcon(String str) {
		try {
			return new MyStretchIcon(ImageIO.read(MyToolbar.class.getClassLoader().getResource("toolbar/" + str)), 32, 32);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
