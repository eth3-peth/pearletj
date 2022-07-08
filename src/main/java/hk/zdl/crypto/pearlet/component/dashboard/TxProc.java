package hk.zdl.crypto.pearlet.component.dashboard;

import javax.swing.table.TableColumnModel;

import hk.zdl.crypto.pearlet.component.dashboard.rotura.RoturaAddressCellRenderer;
import hk.zdl.crypto.pearlet.component.dashboard.rotura.RoturaValueCellRenderer;
import hk.zdl.crypto.pearlet.component.dashboard.signum.InstantCellRenderer;
import hk.zdl.crypto.pearlet.component.dashboard.signum.SignumAddressCellRenderer;
import hk.zdl.crypto.pearlet.component.dashboard.signum.SignumTxTypeCellRenderer;
import hk.zdl.crypto.pearlet.component.dashboard.signum.SignumValueCellRenderer;
import hk.zdl.crypto.pearlet.component.dashboard.signum.TxIdCellrenderer;
import hk.zdl.crypto.pearlet.util.CrptoNetworks;

public class TxProc {

	public void update_column_model(CrptoNetworks network, TableColumnModel model, String address){
		switch (network) {
		case ROTURA:
			model.getColumn(0).setCellRenderer(new TxIdCellrenderer());
			model.getColumn(1).setCellRenderer(new InstantCellRenderer());
			model.getColumn(2).setCellRenderer(new SignumTxTypeCellRenderer());
			model.getColumn(3).setCellRenderer(new RoturaValueCellRenderer(address));
			model.getColumn(4).setCellRenderer(new RoturaAddressCellRenderer(address));
			break;
		case SIGNUM:
			model.getColumn(0).setCellRenderer(new TxIdCellrenderer());
			model.getColumn(1).setCellRenderer(new InstantCellRenderer());
			model.getColumn(2).setCellRenderer(new SignumTxTypeCellRenderer());
			model.getColumn(3).setCellRenderer(new SignumValueCellRenderer(address));
			model.getColumn(4).setCellRenderer(new SignumAddressCellRenderer(address));
			break;
		case WEB3J:
			break;
		default:
			break;

		}
	}
}