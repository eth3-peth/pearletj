package hk.zdl.crypto.pearlet.component.dashboard.ether;

import java.awt.Color;
import java.awt.Component;
import java.math.BigDecimal;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import org.json.JSONObject;
import org.web3j.utils.Convert;

import com.jthemedetecor.OsThemeDetector;

@SuppressWarnings("serial")
public class EtherValueCellRenderer extends DefaultTableCellRenderer {

	private static final OsThemeDetector otd = OsThemeDetector.getDetector();
	private static final Color my_cyan = new Color(0, 175, 175), my_green = new Color(175, 255, 175);
	private final String address;

	public EtherValueCellRenderer(String address) {
		this.address = address;
		setHorizontalAlignment(SwingConstants.RIGHT);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		boolean isDark = otd.isDark();
		setBackground(isDark ? darker(my_cyan) : my_cyan);
		if (!isSelected) {
			JSONObject tx = (JSONObject) value;
			if (tx.getString("to_address").equals(address)) {
				setBackground(isDark ? darker(my_green) : my_green);
			} else {
				setBackground(isDark ? darker(Color.pink) : Color.pink);
			}
		}
		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	}

	@Override
	protected void setValue(Object value) {
		JSONObject tx = (JSONObject) value;
		BigDecimal val = Convert.fromWei(new BigDecimal(tx.getString("value")),Convert.Unit.ETHER).stripTrailingZeros();
		setText(val.stripTrailingZeros().toString());
	}

	private static final Color darker(Color c) {
		float factor = 0.4f;
		return new Color(Math.max((int) (c.getRed() * factor), 0), Math.max((int) (c.getGreen() * factor), 0), Math.max((int) (c.getBlue() * factor), 0), c.getAlpha());
	}

}
