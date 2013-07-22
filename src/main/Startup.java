package main;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jme3.system.JmeContext;

import testingarea.HelloPhysics;

import main.game.Core;
import main.game.server.ServerStartup;

public class Startup extends JPanel {

	public Startup() {
		
		final ServerStartup app = new ServerStartup(true);

		final JFrame f = new JFrame("Launcher");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);

		final Startup launcher = this;
		launcher.setLayout(new GridLayout(3, 1));

		JButton launch_main = new JButton("Launch game");
		launch_main.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						Core app = new Core();
						app.start();
						f.dispose();
					}
				}).start();
			}
		});
		launcher.add(launch_main);

		JButton launch_physics = new JButton("Launch physics test");
		launch_physics.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						HelloPhysics app = new HelloPhysics();
						app.start();
						f.dispose();
					}
				}).start();
			}
		});
		launcher.add(launch_physics);

		JButton launch_server = new JButton("Launch server");
		launch_server.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						app.start(JmeContext.Type.Headless);
						//f.dispose();
					}
				}).start();
			}
		});
		launcher.add(launch_server);

		JButton quit_server = new JButton("Quit server");
		quit_server.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						app.stop();
						//f.dispose();
					}
				}).start();
			}
		});
		launcher.add(quit_server);

		JButton exit = new JButton("Exit");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						f.dispose();
					}
				}).start();
			}
		});
		launcher.add(exit);

		f.add(launcher);
		//f.setUndecorated(true);   
		/* I'm thinking of something like an undecorated window with a cool 
		 * background and awesome design, kinda like the Skyrim launcher
		 */
		f.pack();

		f.setVisible(true);

	}

	public static void main(String[] args) {
		new Startup();
	}

}
