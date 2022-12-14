package hk.zdl.crypto.pearlet.tx_history_query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import hk.zdl.crypto.pearlet.component.event.AccountChangeEvent;
import hk.zdl.crypto.pearlet.component.event.TxHistoryEvent;
import hk.zdl.crypto.pearlet.util.CrptoNetworks;

public class TxHistoryQueryExecutor {

	private final List<MyThread> threads = Collections.synchronizedList(new ArrayList<>());
	private final EtherTxQuery eth_tx_q = new EtherTxQuery();

	public TxHistoryQueryExecutor() {
		EventBus.getDefault().register(this);
	}

	@SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	@Subscribe(threadMode = ThreadMode.ASYNC)
	public synchronized void onMessage(AccountChangeEvent e) {
		if(e.account==null)
			return;
		EventBus.getDefault().post(new TxHistoryEvent(e.network, TxHistoryEvent.Type.START, null));
		for (MyThread t : threads) {
			try {
				t.stop();
			} catch (Throwable x) {
			}
		}
		threads.clear();
		MyThread t = new MyThread(e.network, e.account);
		t.start();
		threads.add(t);
	}

	private final class MyThread extends Thread {
		CrptoNetworks network;
		String account;

		public MyThread(CrptoNetworks network, String account) {
			this.network = network;
			this.account = account;
			setPriority(MIN_PRIORITY);
			setDaemon(true);
		}

		@Override
		public void run() {
			boolean is_finished = false;
			if ("null".equals(String.valueOf(account))) {
				is_finished = true;
			} else
				try {
					switch (network) {
					case ROTURA:
					case SIGNUM:
						new SignumTxHistoryQuery(network).queryTxHistory(account);
						break;
					case WEB3J:
						eth_tx_q.queryTxHistory(account);
						break;
					default:
						break;

					}
					is_finished = true;
				} catch (InterruptedException e) {
				} catch (Exception e) {
					Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
					is_finished = true;
				}
			if (is_finished) {
				send_finish_msg();
			}
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		private void send_finish_msg() {
			EventBus.getDefault().post(new TxHistoryEvent(network, TxHistoryEvent.Type.FINISH, null));
		}

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("MyThread [network=").append(network).append(", account=").append(account).append("]");
			return builder.toString();
		}

	}
}
