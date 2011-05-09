package de.leuphana.ardrone.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JSplitPane;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;

import javax.swing.JTextArea;
import java.awt.CardLayout;
import java.awt.Component;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import de.leuphana.ardrone.CommandDispatcher;
import de.leuphana.ardrone.Controller;
import de.leuphana.ardrone.KeyDispatcher;
import de.leuphana.ardrone.Util;
import de.leuphana.ardrone.listener.MessageListener;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JCheckBox;
import javax.swing.JTextPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.SpinnerModel;
import javax.swing.UIManager;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.SpinnerNumberModel;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JSlider;

public class DroneWindow implements MessageListener {

	private JFrame frame;
	private Controller controller;
	private JTextPane txtrOutput;
	private Style style;
	CommandDispatcher dispatcher;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DroneWindow window = new DroneWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DroneWindow() {
		frame = new JFrame();
		controller = new Controller(frame);
		initialize();
		KeyboardFocusManager manager = KeyboardFocusManager
				.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(controller);
		controller.addDroneControlListener(this);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				controller.land();
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame.setBounds(100, 100, 400, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel panel_main = new JPanel();
		frame.getContentPane().add(panel_main);
		panel_main.setLayout(new BoxLayout(panel_main, BoxLayout.Y_AXIS));
		JPanel panel_top = new JPanel();
		panel_main.add(panel_top);
		BoxLayout bl_panel_top = new BoxLayout(panel_top, BoxLayout.Y_AXIS);
		// panel_top.setPreferredSize(new Dimension(50, 50));
		panel_top.setLayout(bl_panel_top);

		JPanel panel_buttons = new JPanel();
		panel_buttons.setBackground(Color.WHITE);
		panel_buttons.setAlignmentY(Component.TOP_ALIGNMENT);
		panel_top.add(panel_buttons);
		GridBagLayout gbl_panel_buttons = new GridBagLayout();
		gbl_panel_buttons.columnWidths = new int[] { 40, 40, 40 };
		gbl_panel_buttons.rowHeights = new int[] { 41, 41, 41, 0, 0, 0, 0, 40 };
		gbl_panel_buttons.columnWeights = new double[] { 1.0, 1.0, 1.0 };
		gbl_panel_buttons.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
				1.0, 1.0, Double.MIN_VALUE };
		panel_buttons.setLayout(gbl_panel_buttons);

		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.start();
			}
		});
		GridBagConstraints gbc_btnStart = new GridBagConstraints();
		gbc_btnStart.fill = GridBagConstraints.BOTH;
		gbc_btnStart.insets = new Insets(0, 0, 5, 5);
		gbc_btnStart.gridx = 0;
		gbc_btnStart.gridy = 0;
		panel_buttons.add(btnStart, gbc_btnStart);

		JButton btnLand = new JButton("Land");
		btnLand.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.land();
			}
		});
		GridBagConstraints gbc_btnLand = new GridBagConstraints();
		gbc_btnLand.fill = GridBagConstraints.BOTH;
		gbc_btnLand.insets = new Insets(0, 0, 5, 5);
		gbc_btnLand.gridx = 1;
		gbc_btnLand.gridy = 0;
		panel_buttons.add(btnLand, gbc_btnLand);

		JButton btnReset = new JButton("reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.reset();
			}
		});
		GridBagConstraints gbc_btnReset = new GridBagConstraints();
		gbc_btnReset.fill = GridBagConstraints.BOTH;
		gbc_btnReset.insets = new Insets(0, 0, 5, 0);
		gbc_btnReset.gridx = 2;
		gbc_btnReset.gridy = 0;
		panel_buttons.add(btnReset, gbc_btnReset);

		JButton btnTiltLeft = new JButton("tilt left");
		GridBagConstraints gbc_btnTiltLeft = new GridBagConstraints();
		gbc_btnTiltLeft.fill = GridBagConstraints.BOTH;
		gbc_btnTiltLeft.insets = new Insets(0, 0, 5, 5);
		gbc_btnTiltLeft.gridx = 0;
		gbc_btnTiltLeft.gridy = 1;
		panel_buttons.add(btnTiltLeft, gbc_btnTiltLeft);

		btnTiltLeft.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent arg0) {
				// controller.tiltLeft(true);
				// dispatcher = new CommandDispatcher(controller,"tiltLeft");
				// dispatcher.start();
				CommandDispatcher.getNewDispatcher(controller, "tiltLeft")
						.start();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// dispatcher.setExecuting(false);
				// Util.sleepMillis(100);
				// controller.tiltLeft(false);
				CommandDispatcher.stopDispatcher("tiltLeft");
			}
		});

		JButton btnForward = new JButton("forward");
		btnForward.setIcon(new ImageIcon(DroneWindow.class
				.getResource("/images/arrow-up-icon_32.png")));
		btnForward.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				// controller.up(true);
				CommandDispatcher.getNewDispatcher(controller, "forward")
						.start();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// dispatcher.setExecuting(false);
				// controller.up(false);
				CommandDispatcher.stopDispatcher("forward");
			}
		});
		GridBagConstraints gbc_btnForward = new GridBagConstraints();
		gbc_btnForward.fill = GridBagConstraints.BOTH;
		gbc_btnForward.insets = new Insets(0, 0, 5, 5);
		gbc_btnForward.gridx = 1;
		gbc_btnForward.gridy = 1;
		panel_buttons.add(btnForward, gbc_btnForward);

		JButton btnTiltright = new JButton("tiltRight");
		GridBagConstraints gbc_btnTiltright = new GridBagConstraints();
		gbc_btnTiltright.fill = GridBagConstraints.BOTH;
		gbc_btnTiltright.insets = new Insets(0, 0, 5, 0);
		gbc_btnTiltright.gridx = 2;
		gbc_btnTiltright.gridy = 1;
		panel_buttons.add(btnTiltright, gbc_btnTiltright);
		btnTiltright.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				CommandDispatcher.getNewDispatcher(controller, "tiltRight")
						.start();
				// controller.tiltRight(true);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				CommandDispatcher.stopDispatcher("tiltRight");
				// controller.tiltRight(false);
			}
		});

		JButton btnLeft = new JButton("left");
		btnLeft.setBackground(UIManager.getColor("Button.background"));
		btnLeft.setIcon(new ImageIcon(DroneWindow.class
				.getResource("/images/arrow-left-icon_32.png")));
		GridBagConstraints gbc_btnLeft = new GridBagConstraints();
		gbc_btnLeft.fill = GridBagConstraints.BOTH;
		gbc_btnLeft.insets = new Insets(0, 0, 5, 5);
		gbc_btnLeft.gridx = 0;
		gbc_btnLeft.gridy = 2;
		panel_buttons.add(btnLeft, gbc_btnLeft);
		btnLeft.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				CommandDispatcher.getNewDispatcher(controller, "rotLeft")
						.start();
				// controller.rotLeft(true);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				CommandDispatcher.stopDispatcher("rotLeft");
				// controller.rotLeft(false);
			}
		});
		JButton btnBackward = new JButton("backward");
		btnBackward.setIcon(new ImageIcon(DroneWindow.class
				.getResource("/images/arrow-down-icon_32.png")));
		GridBagConstraints gbc_btnBackward = new GridBagConstraints();
		gbc_btnBackward.fill = GridBagConstraints.BOTH;
		gbc_btnBackward.insets = new Insets(0, 0, 5, 5);
		gbc_btnBackward.gridx = 1;
		gbc_btnBackward.gridy = 2;
		panel_buttons.add(btnBackward, gbc_btnBackward);
		btnBackward.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				CommandDispatcher.getNewDispatcher(controller, "backward")
						.start();
				// controller.down(true);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				CommandDispatcher.stopDispatcher("backward");
				// controller.down(false);
			}
		});

		JButton btnRight = new JButton("right");
		btnRight.setIcon(new ImageIcon(DroneWindow.class
				.getResource("/images/arrow-right-icon_32.png")));
		GridBagConstraints gbc_btnRight = new GridBagConstraints();
		gbc_btnRight.insets = new Insets(0, 0, 5, 0);
		gbc_btnRight.fill = GridBagConstraints.BOTH;
		gbc_btnRight.gridx = 2;
		gbc_btnRight.gridy = 2;
		panel_buttons.add(btnRight, gbc_btnRight);
		btnRight.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				CommandDispatcher.getNewDispatcher(controller, "rotRight")
						.start();
				// controller.rotRight(true);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				CommandDispatcher.stopDispatcher("rotRight");
				// controller.rotRight(false);
			}
		});

		JButton btnUp = new JButton("up");
		btnUp.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				CommandDispatcher.getNewDispatcher(controller, "up").start();
				// controller.rotRight(true);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				CommandDispatcher.stopDispatcher("up");
				// controller.rotRight(false);
			}
		});
		btnUp.setIcon(new ImageIcon(DroneWindow.class
				.getResource("/images/plus-icon.png")));
		GridBagConstraints gbc_btnUp = new GridBagConstraints();
		gbc_btnUp.fill = GridBagConstraints.BOTH;
		gbc_btnUp.insets = new Insets(0, 0, 5, 5);
		gbc_btnUp.gridx = 0;
		gbc_btnUp.gridy = 3;
		panel_buttons.add(btnUp, gbc_btnUp);

		JButton btnDown = new JButton("down");
		btnDown.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				CommandDispatcher.getNewDispatcher(controller, "down").start();
				// controller.rotRight(true);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				CommandDispatcher.stopDispatcher("down");
				// controller.rotRight(false);
			}
		});
		btnDown.setIcon(new ImageIcon(DroneWindow.class
				.getResource("/images/minus-icon.png")));
		GridBagConstraints gbc_btnDown = new GridBagConstraints();
		gbc_btnDown.fill = GridBagConstraints.BOTH;
		gbc_btnDown.insets = new Insets(0, 0, 5, 5);
		gbc_btnDown.gridx = 1;
		gbc_btnDown.gridy = 3;
		panel_buttons.add(btnDown, gbc_btnDown);
		final JCheckBox chckbxGamemode = new JCheckBox("gameMode");
		GridBagConstraints gbc_chckbxGamemode = new GridBagConstraints();
		gbc_chckbxGamemode.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxGamemode.gridx = 0;
		gbc_chckbxGamemode.gridy = 4;
		panel_buttons.add(chckbxGamemode, gbc_chckbxGamemode);
		chckbxGamemode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.setGameMode(chckbxGamemode.getModel().isSelected());

			}
		});

		final JCheckBox chckbxOutdoor = new JCheckBox("outdoor");
		GridBagConstraints gbc_chckbxOutdoor = new GridBagConstraints();
		gbc_chckbxOutdoor.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxOutdoor.gridx = 1;
		gbc_chckbxOutdoor.gridy = 4;
		panel_buttons.add(chckbxOutdoor, gbc_chckbxOutdoor);
		chckbxOutdoor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.setOutdoor(chckbxOutdoor.getModel().isSelected());

			}
		});

		final JCheckBox chckbxIndoorshell = new JCheckBox("indoorShell");
		GridBagConstraints gbc_chckbxIndoorshell = new GridBagConstraints();
		gbc_chckbxIndoorshell.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxIndoorshell.gridx = 2;
		gbc_chckbxIndoorshell.gridy = 4;
		panel_buttons.add(chckbxIndoorshell, gbc_chckbxIndoorshell);

		chckbxIndoorshell.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				controller.setIndoorShell(chckbxIndoorshell.getModel()
						.isSelected());

			}
		});
		
		DroneControlPanel droneControlPanel = new DroneControlPanel(controller.getDroneControl());
		GridBagConstraints gbc_droneControlPanel = new GridBagConstraints();
		gbc_droneControlPanel.fill = GridBagConstraints.HORIZONTAL;
		gbc_droneControlPanel.gridwidth = 3;
		gbc_droneControlPanel.insets = new Insets(0, 0, 5, 5);
		gbc_droneControlPanel.gridx = 0;
		gbc_droneControlPanel.gridy = 5;
		panel_buttons.add(droneControlPanel,gbc_droneControlPanel);

		JPanel panel_Info = new JPanel();
		panel_Info.setMinimumSize(new Dimension(100, 100));
		panel_Info.setPreferredSize(new Dimension(400, 500));
		panel_main.add(panel_Info);
		panel_Info.setLayout(new BoxLayout(panel_Info, BoxLayout.Y_AXIS));

		JTextArea txtrInput = new JTextArea();

		txtrInput.setAlignmentY(Component.TOP_ALIGNMENT);
		txtrInput.setColumns(1);
		txtrInput.setText("input");
		txtrInput.setMinimumSize(new Dimension(100, 100));
		txtrInput.setPreferredSize(new Dimension(100, 100));
		panel_Info.add(txtrInput);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(300, 300));
		panel_Info.add(scrollPane);

		txtrOutput = new JTextPane();
		scrollPane.setViewportView(txtrOutput);
		txtrOutput.setBackground(new Color(245, 245, 245));
		txtrOutput.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.RED,
				null));
		txtrOutput.setEditable(false);
		txtrOutput.setText("output");
		txtrOutput.setMinimumSize(new Dimension(300, 300));
		style = txtrOutput.addStyle("Red", null);
		StyleConstants.setForeground(style, Color.red);
		// txtrOutput.setPreferredSize(new Dimension(300, 300));
	}

	public void updateOutput(String message) {
		if (txtrOutput.getText() == null || txtrOutput.getText().isEmpty())
			txtrOutput.setText("");

		txtrOutput.setText(txtrOutput.getText() + message + "\n");
		// JTextArea textComp = new JTextArea();

		// Highlight the occurrences of the word "public"
		// highlight(txtrOutput, ".*:");
		marker(txtrOutput, "[^\n]*::");

	}

	public void clearOutput() {
		removeHighlights(txtrOutput);
		txtrOutput.setText("");
	}

	@Override
	public void commandSend(String commandName, String message, boolean success) {
		// System.out.println(commandName + " : " + message);
		updateOutput(commandName + " :: " + message);
	}

	public void marker(JTextPane textComp, String pattern) {
		Document doc = textComp.getDocument();
		StyledDocument styledDocument = textComp.getStyledDocument();
		String text;
		try {
			text = doc.getText(0, doc.getLength());
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(text);
			while (m.find()) {
				styledDocument.setCharacterAttributes(m.start(),
						m.end() - m.start(), style, true);
//				System.out.println(m.group());
				// hilite.addHighlight(m.start(), m.end(), myHighlightPainter);
			}
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Creates highlights around all occurrences of pattern in textComp
	public void highlight(JTextComponent textComp, String pattern) {
		// First remove all old highlights
		removeHighlights(textComp);

		try {
			Highlighter hilite = textComp.getHighlighter();
			Document doc = textComp.getDocument();
			String text = doc.getText(0, doc.getLength());
			// int pos = 0;

			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(text);
			while (m.find()) {
				System.out.println(m.group());
				hilite.addHighlight(m.start(), m.end(), myHighlightPainter);
			}
			// while ((pos = text.indexOf(pattern, pos)) >= 0) {
			// // Create highlighter using private painter and apply around
			// pattern
			// hilite.addHighlight(pos, pos+pattern.length(),
			// myHighlightPainter);
			// pos += pattern.length();
			// }
		} catch (BadLocationException e) {
		}
	}

	// Removes only our private highlights
	public void removeHighlights(JTextComponent textComp) {
		Highlighter hilite = textComp.getHighlighter();
		Highlighter.Highlight[] hilites = hilite.getHighlights();

		for (int i = 0; i < hilites.length; i++) {
			if (hilites[i].getPainter() instanceof MyHighlightPainter) {
				hilite.removeHighlight(hilites[i]);
			}
		}
	}

	// An instance of the private subclass of the default highlight painter
	Highlighter.HighlightPainter myHighlightPainter = new MyHighlightPainter(
			Color.red);

	// A private subclass of the default highlight painter
	class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter {
		public MyHighlightPainter(Color color) {
			super(color);
		}
	}

}
