package socialNetwork.ui;

import java.awt.GridLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.Box.Filler;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import socialNetwork.src.Commentary;
import socialNetwork.src.Friends;
import socialNetwork.src.Message;
import socialNetwork.src.Status;

public class Interface extends JFrame {

	static JPanel panel, fPanel, me, him;
	static JTextField postText, friendText;

	public Interface() {
		initInterface();
	}

	public void himStatus(String status){
		JLabel label = new JLabel(status);
		him.add(label);
		panel.revalidate();
	}

	public static void printCommentary(Commentary comment){
		/**/
	}

	public final void initInterface() {
		JLabel label;

		panel = new JPanel();
		getContentPane().add(panel);
		/* Afficher d'abord la zone de post, puis les gens */
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		/* Une zone de texte et un bouton pour poster */
		postText = new JTextField(40);
		postText.setMaximumSize(new Dimension(Integer.MAX_VALUE, postText.getMinimumSize().height));
		panel.add(postText);
		JButton postButton = new JButton("Post");
		JButton friendButton = new JButton("Add Friend");
		panel.getRootPane().setDefaultButton(postButton);

		postButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				/* Ajoute le statut */
				DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
				Date date = new Date();
				Status.createNewStatus(postText.getText(), true);
				String status = "[" + dateFormat.format(date) +"]"+ System.getProperty("user.name")+"> " + postText.getText();
				JLabel label = new JLabel(status);
				postText.setText("");
				me.add(label);
				/* Et redessine */
				panel.validate();
			}
		});

		friendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				fPanel = new JPanel();
				fPanel.setLayout(new BoxLayout(fPanel, BoxLayout.Y_AXIS));
				JFrame fFrame = new JFrame("Add Friend");
				fFrame.getContentPane().add(fPanel);

				friendText = new JTextField(10);
				friendText.setMaximumSize(new Dimension(Integer.MAX_VALUE, postText.getMinimumSize().height));
				fPanel.add(friendText);

				JButton addButton = new JButton("Add");
				JButton answerButton = new JButton("Ok");
				fPanel.add(addButton);
				fPanel.getRootPane().setDefaultButton(addButton);
				fPanel.add(answerButton);

				addButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						String address = friendText.getText();
						try {
							Message.friendsRequest(InetAddress.getByName("localhost"));
						} catch (Exception e) {}

					}
				});
				answerButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						try {
							Friends.AcceptFriend("amiviell");
						} catch (Exception e) {}

					}
				});

				fFrame.pack();
				fFrame.setVisible(true);
			}
		});

		postButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		friendButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(postButton);
		panel.add(friendButton);

		/* Un panel horizontal pour les gens */
		JPanel people = new JPanel();
		panel.add(people);
		people.setAlignmentX(Component.LEFT_ALIGNMENT);
		/* Les personnes sont affichées de gauche à droite */
		people.setLayout(new BoxLayout(people, BoxLayout.X_AXIS));

		/* Moi */
		me = new JPanel();
		me.setBorder(new LineBorder(Color.black));
		/* Mes commentaires sont affichés de haut en bas */
		me.setLayout(new BoxLayout(me, BoxLayout.Y_AXIS));
		me.setAlignmentY(Component.TOP_ALIGNMENT);
		people.add(me);

		label = new JLabel("Label Noir status");
		me.add(label);

		/* Une petite séparation entre moi et lui */
		people.add(Box.createRigidArea(new Dimension(5,0)));

		/* Un ami */
		him = new JPanel();
		him.setBorder(new LineBorder(Color.black));
		him.setLayout(new BoxLayout(him, BoxLayout.Y_AXIS));
		him.setAlignmentY(Component.TOP_ALIGNMENT);
		people.add(him);

		label = new JLabel("Nous status");
		him.add(label);
		/* De la place pour les autres */
		people.add(Box.createHorizontalGlue());

		/* Le reste de l'interface */
		setTitle("Social");
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
