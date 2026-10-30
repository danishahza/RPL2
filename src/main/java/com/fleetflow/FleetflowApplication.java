package com.fleetflow;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.fleetflow.service.AuthService;
import com.fleetflow.ui.MainFrame;
import com.fleetflow.ui.SessionContext;

public final class FleetflowApplication {

	private FleetflowApplication() {
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(FleetflowApplication::jalankan);
	}

	private static void jalankan() {
		pakaiTampilanSistem();
		MainFrame frame = new MainFrame(new AuthService(), new SessionContext());
		frame.setVisible(true);
	}

	private static void pakaiTampilanSistem() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (ReflectiveOperationException | UnsupportedLookAndFeelException e) {
			// Tampilan bawaan Java tetap dipakai.
		}
	}

}
