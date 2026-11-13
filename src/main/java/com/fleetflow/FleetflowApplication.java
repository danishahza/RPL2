package com.fleetflow;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.fleetflow.controller.AuthController;
import com.fleetflow.controller.MasterDataController;
import com.fleetflow.ui.MainFrame;
import com.fleetflow.ui.SessionContext;

@SpringBootApplication
public class FleetflowApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(FleetflowApplication.class, args);
		context.registerShutdownHook();
		SwingUtilities.invokeLater(() -> jalankan(context));
	}

	private static void jalankan(ConfigurableApplicationContext context) {
		pakaiTampilanSistem();
		MainFrame frame = new MainFrame(context.getBean(AuthController.class),
				context.getBean(MasterDataController.class), new SessionContext());
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
