package socialNetwork.ui;

import java.awt.BorderLayout;
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
import java.awt.EventQueue;
import java.util.Date;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.Box.Filler;
import javax.swing.SwingUtilities;

import socialNetwork.Main;
import socialNetwork.src.Commentary;
import socialNetwork.src.Friends;
import socialNetwork.src.Message;
import socialNetwork.src.Status;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;


/**
 * Gère l'interface du programme.
 *
 */
public class Interface extends JFrame {

	static JPanel contentPane, panel, fPanel, me, him;
	private JTextField txtStatus, friendText;

	/**
	 * Démare l'interface.
	 */
	public Interface() {
		initInterface();
	}

	/**
	 * Affiche un commentaire. (Non fonctionnel)
	 * @param comment Le commentaire.
	 */
	public static void printCommentary(Commentary comment){
		/**/
	}

	/**
	 * Affiche un nouveau statuts.
	 * @param status Le status.
	 */
	public void himStatus(String status){
		JLabel label = new JLabel(status);
		him.add(label);
		panel.revalidate();
	}
	
	private final void initInterface() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//Zone de texte des status
		txtStatus = new JTextField();
		txtStatus.setText("Status");
		txtStatus.setBounds(12, 12, 674, 25);
		contentPane.add(txtStatus);
		txtStatus.setColumns(10);

		JPanel people = new JPanel();
		people.setBounds(12, 86, 491, 276);
		contentPane.add(people);
		people.setLayout(null);

		//Nos status
		final JPanel me = new JPanel();
		me.setBounds(12, 12, 286, 235);
		people.add(me);
		me.setLayout(new BoxLayout(me, BoxLayout.Y_AXIS));
		me.setAlignmentY(Component.TOP_ALIGNMENT);
		people.add(me);

		JLabel lblLabelNoir = new JLabel(Main.userName);
		me.add(lblLabelNoir);

		//Leur status
		JPanel him = new JPanel();
		him.setBounds(299, 12, 280, 235);
		people.add(him);
		him.setLayout(new BoxLayout(him, BoxLayout.Y_AXIS));
		him.setAlignmentY(Component.TOP_ALIGNMENT);
		people.add(him);

		JLabel lblNous = new JLabel("Amis");
		him.add(lblNous);

		//Boutton post
		JButton postButton = new JButton("Post");
		postButton.setBounds(12, 49, 117, 25);
		contentPane.add(postButton);
		postButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				/* Ajoute le statut */
				DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
				Date date = new Date();
				Status.createNewStatus(txtStatus.getText(), true);
				String status = "[" + dateFormat.format(date) +"]"+ System.getProperty("user.name")+"> " + txtStatus.getText();
				JLabel label = new JLabel(status);
				txtStatus.setText("");
				me.add(label);
				/* Et redessine */
				me.validate();
			}
		});
		
		//Bouton d'ajout d'amis
		JButton friendButton = new JButton("Add Friends");
		friendButton.setBounds(568, 49, 118, 25);
		contentPane.add(friendButton);

		friendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				fPanel = new JPanel();
				fPanel.setLayout(new BoxLayout(fPanel, BoxLayout.Y_AXIS));
				JFrame fFrame = new JFrame("Add Friend");
				fFrame.getContentPane().add(fPanel);

				friendText = new JTextField(10);
				friendText.setMaximumSize(new Dimension(Integer.MAX_VALUE, txtStatus.getMinimumSize().height));
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
							Friends.sendRequest(InetAddress.getByName(address));
						} catch (Exception e) {}

					}
				});
				answerButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						try {
							Friends.AcceptFriend("ysenel");
						} catch (Exception e) {}

					}
				});

				fFrame.pack();
				fFrame.setVisible(true);
			}
		});

		//Liste des amis
		JPanel listFriend = new JPanel();
		listFriend.setBounds(457, 86, 118, 259);
		contentPane.add(listFriend);

		//Bouton de choix public/privé
		javax.swing.ButtonGroup buttonGroup1;
		buttonGroup1 = new javax.swing.ButtonGroup();
		JRadioButton rdbtnPublic = new JRadioButton("Public");
		rdbtnPublic.setBounds(151, 50, 79, 23);
		contentPane.add(rdbtnPublic);
		JRadioButton rdbtnPrivate = new JRadioButton("Private");
		rdbtnPrivate.setBounds(243, 50, 89, 23);
		contentPane.add(rdbtnPrivate);
        buttonGroup1.add(rdbtnPublic);
        buttonGroup1.add(rdbtnPrivate);
        rdbtnPublic.setSelected(true);

		/* Le reste de l'interface */
		setTitle("Social Network");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}