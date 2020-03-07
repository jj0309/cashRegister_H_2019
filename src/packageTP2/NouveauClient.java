package packageTP2;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JButton;

public class NouveauClient extends JDialog {

	JButton btnOK;
	JButton btnAnnuler;
	JTextArea textAreaNomClient;
	JTextArea textAreaNumMembre;
	private int numCarte;
	private String nomClient;
	private leFrame framePrincipale;

	public NouveauClient(leFrame frame) {
		
		this.framePrincipale = frame;
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		this.setTitle("Inscription d'un nouveau client"); // le titre
		
		JLabel lblNom = new JLabel("Nom du client: ");
		lblNom.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNom.setBounds(34, 48, 161, 14);
		getContentPane().add(lblNom);
		
		JLabel lblNumCarte = new JLabel("Numero de carte membre: ");
		lblNumCarte.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNumCarte.setBounds(34, 140, 251, 14);
		getContentPane().add(lblNumCarte);
		
		textAreaNomClient = new JTextArea();
		textAreaNomClient.setBorder(new LineBorder(new Color(0, 0, 0)));
		textAreaNomClient.setBounds(205, 46, 219, 22);
		getContentPane().add(textAreaNomClient);
		
		textAreaNumMembre = new JTextArea();
		textAreaNumMembre.setBorder(new LineBorder(new Color(0, 0, 0)));
		textAreaNumMembre.setBounds(295, 138, 129, 22);
		getContentPane().add(textAreaNumMembre);
		
		btnOK = new JButton("OK");
		btnOK.setBounds(58, 187, 89, 63);
		getContentPane().add(btnOK);
		
		btnAnnuler = new JButton("Annuler");
		btnAnnuler.setBounds(224, 187, 89, 63);
		getContentPane().add(btnAnnuler);
		
		Ecouteur ec = new Ecouteur(); // ecouteur pour les 2 buttons
		
		btnOK.addActionListener(ec); // ajout des ecouteurs
		btnAnnuler.addActionListener(ec);
		
		textAreaNumMembre.setEditable(false);
		textAreaNumMembre.setText((String.valueOf(ThreadLocalRandom.current().nextInt(100000, 900000)))); // pour obtenir un 6 number digit

	}



	private class Ecouteur implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btnOK) // si on clic sur ok
			{
				numCarte = Integer.parseInt(textAreaNumMembre.getText());
				nomClient = textAreaNomClient.getText();
				// ajouter un if condition pour si info invalide
				EnsembleClients.ajouterClient(new Client(String.valueOf(numCarte),nomClient,0,0.0)); // on ajoute le client dans la hashtable
				//framePrincipale.setNouveauClient(true);
				framePrincipale.setNumeroClient(numCarte); // set le numero de client dans le frame principale
				NouveauClient.this.dispose(); // on ferme le dialogue
			}
			else//si on click sur cancel
				NouveauClient.this.dispose(); // pour fermer le dialogue sans fermer l'application
		}
		
	}
}
