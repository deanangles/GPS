import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Tester extends JPanel {
	JFrame window = new JFrame("Fastest Path");
	Timer tmr = null;
	Random rnd = new Random();
	Graph map = new Graph("MapInformationXY.txt", 50, 25);
	Vertex fromV = null, toV = null;

	public Tester() {
		window.setBounds(50, 50, 500, 500);
		window.setAlwaysOnTop(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.getContentPane().add(this);

		JMenu changeCost = new JMenu("Change Cost");
		JMenuItem currCost = new JMenuItem("Current Cost: "+ (map.useDistCost ? "Distance Cost" : "Time Cost"));
		JMenuItem distCost = new JMenuItem("Change to Distance Cost");
		distCost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				map.useDistCost = true;
				currCost.setText("Current Cost: Distance Cost");
			}
		});
		JMenuItem timeCost = new JMenuItem("Change to Time Cost");
		timeCost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				map.useDistCost = false;
				currCost.setText("Current Cost: Time Cost");
			}
		});
		changeCost.add(currCost);
		changeCost.add(distCost);
		changeCost.add(timeCost);
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(changeCost);

		JMenu changeTitle = new JMenu("Change Title");
		JMenuItem currTitle = new JMenuItem("Current Title: "+(map.returnAddress ? "Addresses" : "Symbols"));
		JMenuItem toSymbol = new JMenuItem("Change to Symbols");
		toSymbol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				map.returnAddress = false;
				currTitle.setText("Current Title: Symbols");
			}
		});
		JMenuItem toAddress = new JMenuItem("Change to Addresses");
		toAddress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				map.returnAddress = true;
				currTitle.setText("Current Title: Addresses");
			}
		});
		changeTitle.add(currTitle);
		changeTitle.add(toSymbol);
		changeTitle.add(toAddress);
		menuBar.add(changeTitle);

		window.setJMenuBar(menuBar);
		window.setVisible(true);

		// ============================================================ Events
		tmr = new Timer(50, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
		});

		// ============================================================ Mouse Pressed
		addMouseListener(new MouseListener() {

			@Override
			public void mousePressed(MouseEvent e) {
				for (Vertex v : map.vertices().values()) {
					if (v.contains(e.getPoint())) {
						if (fromV == null) {
							v.state = Vertex.enuState.START;
							fromV = v;
						} else if (toV == null) {
							v.state = Vertex.enuState.END;
							toV = v;
							Path fastPath = Dijkstra.shortestPath(map, fromV, toV);
							if (map.returnAddress) {
								if (fastPath.totalCost == Integer.MAX_VALUE) {
									JOptionPane.showMessageDialog(window,
											"No Path From " + fromV.address + " to " + toV.address);
								} else {
									JOptionPane.showMessageDialog(window,
											"From: " + fromV.address + "\nTo: " + toV.address + "\nPath Taken: "
													+ fastPath.toStringAddress() + "\nPath Cost: " + fastPath.totalCost
													+ (map.useDistCost ? " miles" : " minutes"));
								}
							} else if (fastPath.totalCost == Integer.MAX_VALUE) {
								JOptionPane.showMessageDialog(window,
										"No Path From " + fromV.symbol + " to " + toV.symbol);
							} else {
								JOptionPane.showMessageDialog(window,
										"From: " + fromV.symbol + "\nTo: " + toV.symbol + "\nPath Taken: "
												+ fastPath.toStringSymbol() + "\nPath Cost: " + fastPath.totalCost
												+ (map.useDistCost ? " miles" : " minutes"));
							}
						} else {
							fromV.state = toV.state = Vertex.enuState.UNSELECTED;
							toV = null;
							v.state = Vertex.enuState.START;
							fromV = v;
						}
					}
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});

		// ============================================================ Mouse Moved,
		// Dragged
		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				window.setTitle("");
				for (Vertex v : map.vertices().values())
					if (v.contains(e.getPoint()))
						window.setTitle(v.address);
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				mouseMoved(e);
			}
		});

		// ============================================================ Key pressed
		window.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {

			}

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

		});

		tmr.start();
	}

	// ============================================================ Drawing
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		map.draw(g);
	}

	// ======================================================
	public static void main(String[] args) {
		new Tester();
	}
	// ======================================================
}
