// TP2 SUPER CHEAP AUTO
// NOM:KA-SON CHAU
// DATE: 24/04/2019
// PROGRAMME DE TRANSACTION QUI LIT ET ÉCRIT DANS FICHIER EXCEL
package packageTP2;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.xml.transform.Source;

import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.util.SystemOutLogger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.Set;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class leFrame extends JFrame {
	private JTextField textFieldNumCarte;
	private JTable tableProduit;
	private JRadioButton rdbtnPaieComptant;
	private JRadioButton rdbtnPaieCredit;
	private JLabel lblMontantDonne;
	private JTextArea textAreaMDonne;
	private JLabel lblMontantRemis;
	private JTextArea textAreaMRemis;
	private JButton btnPayez;
	private JButton btnAnnulerCommande;
	private JButton btnAchet;
	private JButton btnTerminer;
	private DefaultTableModel modele;
	private JComboBox comboBoxARTICLE;
	private JTextArea textAreaStock;
	private JTextArea textAreaPrix;
	private JTextArea textAreaNomClient;
	private JTextArea textArea_PointBonus;
	private static DecimalFormat df = new DecimalFormat("0.00$"); // pour format d'argent avec decimals
	private static DecimalFormat df1 = new DecimalFormat("#");
	private Commande laCommande;
	private double totalApresTaxe = 0.0;
	private boolean numCarteEntré = false;
	private ButtonGroup buttonGroupPaiementMode;
	private NouveauClient dialogue;
	private boolean nouveauClient = false;
	private int numClient = 0;
	private JMenuItem mntmFinDeSession;
	private JMenuItem mntmNouveauClient;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					leFrame frame = new leFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public leFrame() {
		this.setTitle("SUPER CHEAP AUTO"); // le titre
		setBounds(100, 100, 652, 869);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		modele = new DefaultTableModel();
		
		JPanel panel_Membre = new JPanel();
		panel_Membre.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_Membre.setPreferredSize(new Dimension(600, 180));
		getContentPane().add(panel_Membre);
		panel_Membre.setLayout(null);
		panel_Membre.setBackground(Color.GREEN);
		
		JLabel lblNumCarte = new JLabel("Num\u00E9ro de la carte membre:");
		lblNumCarte.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNumCarte.setBounds(10, 21, 269, 23);
		panel_Membre.add(lblNumCarte);
		
		JLabel lblNomClient = new JLabel("Nom du client:");
		lblNomClient.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNomClient.setBounds(10, 71, 235, 34);
		panel_Membre.add(lblNomClient);
		
		JLabel lblPointBonus = new JLabel("Nombre de points bonis \u00E0 ce jour:");
		lblPointBonus.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblPointBonus.setBounds(10, 116, 302, 40);
		panel_Membre.add(lblPointBonus);
		
		textFieldNumCarte = new JTextField();
		textFieldNumCarte.setBounds(289, 25, 301, 20);
		panel_Membre.add(textFieldNumCarte);
		textFieldNumCarte.setColumns(10);
		
		textAreaNomClient = new JTextArea();
		textAreaNomClient.setBounds(291, 79, 299, 22);
		textAreaNomClient.setEditable(false);
		panel_Membre.add(textAreaNomClient);
		
		textArea_PointBonus = new JTextArea();
		textArea_PointBonus.setBounds(322, 127, 268, 22);
		textArea_PointBonus.setEditable(false);
		panel_Membre.add(textArea_PointBonus);
		
		JPanel panel_Commande = new JPanel();
		panel_Commande.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_Commande.setPreferredSize(new Dimension(450, 180));
		getContentPane().add(panel_Commande);
		panel_Commande.setLayout(null);
		panel_Commande.setBackground(Color.ORANGE);
		
		JLabel lblCOMMANDE = new JLabel("COMMANDE");
		lblCOMMANDE.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblCOMMANDE.setHorizontalAlignment(SwingConstants.CENTER);
		lblCOMMANDE.setBounds(10, 11, 150, 29);
		panel_Commande.add(lblCOMMANDE);
		
		JLabel lblArticle = new JLabel("Article");
		lblArticle.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblArticle.setBounds(41, 51, 61, 14);
		panel_Commande.add(lblArticle);
		
		comboBoxARTICLE = new JComboBox();
		comboBoxARTICLE.setBounds(152, 50, 277, 20);
		panel_Commande.add(comboBoxARTICLE);
		
		JLabel lblPrixUnitaire = new JLabel("Prix unitaire:");
		lblPrixUnitaire.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPrixUnitaire.setBounds(41, 94, 104, 14);
		panel_Commande.add(lblPrixUnitaire);
		
		textAreaPrix = new JTextArea();
		textAreaPrix.setBounds(41, 119, 104, 22);
		textAreaPrix.setEditable(false);
		panel_Commande.add(textAreaPrix);
		
		JLabel lblSTOCK = new JLabel("Quantit\u00E9 en stock:");
		lblSTOCK.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSTOCK.setBounds(223, 96, 142, 14);
		panel_Commande.add(lblSTOCK);
		
		textAreaStock = new JTextArea();
		textAreaStock.setBounds(223, 119, 142, 22);
		textAreaStock.setEditable(false);
		panel_Commande.add(textAreaStock);
		
		JPanel panel_Button = new JPanel();
		panel_Button.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_Button.setPreferredSize(new Dimension(150, 180));
		getContentPane().add(panel_Button);
		panel_Button.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		btnAchet = new JButton("Achat");
		btnAchet.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnAchet.setPreferredSize(new Dimension(140, 80));
		panel_Button.add(btnAchet);
		
		btnTerminer = new JButton("Terminer");
		btnTerminer.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnTerminer.setPreferredSize(new Dimension(140, 85));
		panel_Button.add(btnTerminer);
		
		JPanel panel_Paiement = new JPanel();
		panel_Paiement.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_Paiement.setPreferredSize(new Dimension(600, 425));
		getContentPane().add(panel_Paiement);
		panel_Paiement.setBackground(Color.RED);
		panel_Paiement.setLayout(null);
		modele.addColumn("Produit");
		modele.addColumn("Quantité");
		modele.addColumn("Prix");
		
		rdbtnPaieComptant = new JRadioButton("Paiement comptant");
		rdbtnPaieComptant.setFont(new Font("Tahoma", Font.BOLD, 11));
		rdbtnPaieComptant.setForeground(new Color(255, 255, 255));
		rdbtnPaieComptant.setBackground(new Color(255, 102, 51));
		rdbtnPaieComptant.setBounds(10, 251, 142, 23);
		panel_Paiement.add(rdbtnPaieComptant);
		
		rdbtnPaieCredit = new JRadioButton("Paiement credit");
		rdbtnPaieCredit.setFont(new Font("Tahoma", Font.BOLD, 11));
		rdbtnPaieCredit.setForeground(new Color(255, 255, 255));
		rdbtnPaieCredit.setBackground(new Color(255, 102, 51));
		rdbtnPaieCredit.setBounds(215, 251, 127, 23);
		panel_Paiement.add(rdbtnPaieCredit);
		
		lblMontantDonne = new JLabel("Montant donn\u00E9: ");
		lblMontantDonne.setForeground(new Color(255, 255, 255));
		lblMontantDonne.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblMontantDonne.setBounds(10, 335, 108, 14);
		panel_Paiement.add(lblMontantDonne);
		
		textAreaMDonne = new JTextArea();
		textAreaMDonne.setBounds(299, 331, 100, 22);
		textAreaMDonne.setEditable(false);
		panel_Paiement.add(textAreaMDonne);
		
		lblMontantRemis = new JLabel("Montant remis: ");
		lblMontantRemis.setForeground(new Color(255, 255, 255));
		lblMontantRemis.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblMontantRemis.setBounds(10, 376, 108, 14);
		panel_Paiement.add(lblMontantRemis);
		
		textAreaMRemis = new JTextArea();
		textAreaMRemis.setBounds(299, 372, 100, 22);
		textAreaMRemis.setEditable(false);
		panel_Paiement.add(textAreaMRemis);
		
		btnPayez = new JButton("Payez");
		btnPayez.setBounds(409, 223, 181, 116);
		panel_Paiement.add(btnPayez);
		
		btnAnnulerCommande = new JButton("<html>Annuler Commande/<br/>Nouvelle commande</html>");
		btnAnnulerCommande.setPreferredSize(new Dimension(61, 23));
		btnAnnulerCommande.setBounds(409, 350, 181, 64);
		panel_Paiement.add(btnAnnulerCommande);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 580, 189);
		panel_Paiement.add(scrollPane);
		
		tableProduit = new JTable(modele);
		tableProduit.setModel(modele);
		tableProduit.setEnabled(false);
		tableProduit.getTableHeader().setReorderingAllowed(false); // pour ne pas permetre de re-ordonner
		tableProduit.getTableHeader().setResizingAllowed(false);
		scrollPane.setViewportView(tableProduit);

		EnsembleClients.lectureDeXSSF();
		Inventaire.lectureXSSF();
		
		Ecouteur ecouteur = new Ecouteur();
		btnAchet.addActionListener(ecouteur);
		btnTerminer.addActionListener(ecouteur);
		btnAnnulerCommande.addActionListener(ecouteur);
		btnPayez.addActionListener(ecouteur);
		
		EcouteurComboBox ecB = new EcouteurComboBox();
		comboBoxARTICLE.addActionListener(ecB);
		
		Set<String> cle =  Inventaire.getListe().keySet();
		
		for(String str:cle) // pour ajouter les produit dans le combobox
		{
			comboBoxARTICLE.addItem(Inventaire.getProduit(str).getNom());
		}
		comboBoxARTICLE.setSelectedIndex(0); // pour que le item selected est huile de moteur bradley par default
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnOption = new JMenu("Option");
		menuBar.add(mnOption);
		
		mntmNouveauClient = new JMenuItem("Nouveau client");
		mnOption.add(mntmNouveauClient);
		
		mntmFinDeSession = new JMenuItem("Fin de session");
		mnOption.add(mntmFinDeSession);
		mntmFinDeSession.addActionListener(ecouteur);
		
		mntmNouveauClient.addActionListener(ecouteur);
		
		EcKey ecouteurKey = new EcKey();
		textFieldNumCarte.addKeyListener(ecouteurKey);
		
		buttonGroupPaiementMode = new ButtonGroup(); // pour grouper les radio buttons de mode de paiement
		buttonGroupPaiementMode.add(rdbtnPaieComptant);
		buttonGroupPaiementMode.add(rdbtnPaieCredit);
		
		dialogue = new NouveauClient(this); // le dialogue pour nouveau client avec le frame principale en parametre
		
		
	}
	
	

	public void setNumeroClient(int nouveauNumero) // pour que le dialogue nouveau client puisse passer les informations a ce frame
	{
		numClient = nouveauNumero;
		textFieldNumCarte.setText(String.valueOf(numClient));
	}

	private class Ecouteur implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			if(numCarteEntré) // pour que si on a pas validé le numero de client, on peut pas avoir access au autres boutons
			{
				if(e.getSource() == btnAchet)
				{
					String nomProduit = comboBoxARTICLE.getSelectedItem().toString(); // pour obtenir le nom du produit sélectioné dans le combo box
					if(Inventaire.getProduit(nomProduit).getQteStock()>=1) // pour voir si on a le produit en stock
					{
						String prixProduit = String.valueOf(Inventaire.getProduit(nomProduit).getPrix()); // pour obtenir le prix
						modele.addRow(new Object [] {nomProduit,"1",prixProduit}); // on ajoute le produit à la table
						Inventaire.getProduit(nomProduit).modifierQteStock(1); // -1 au produit
						textAreaStock.setText(String.valueOf(Inventaire.getProduit(nomProduit).getQteStock())); // update visuel de la quantite
					}
				}
				else if(e.getSource() == btnTerminer) // le boutton terminer
				{
					double totalAvantTaxe = 0.0;
					String totalAvantTaxeString="";
					String totalApresTaxeString="";
					for(int index = 0;index<modele.getRowCount();index++) // boucle pour avoir le total avant taxe de commande
					{
						laCommande.ajouterItem(new Item((String) (modele.getValueAt(index, 0)),1,laCommande.getNumero()));
					}
					totalAvantTaxe = laCommande.calculerSousTotal(); // calculer le sous-total
					totalApresTaxe = laCommande.calculerGrandTotal(); // pour calculer le total
					totalAvantTaxeString = df.format(totalAvantTaxe); // formatage 0.00$
					totalApresTaxeString = df.format(totalApresTaxe);
					modele.addRow(new Object [] {""});
					modele.addRow(new Object [] {"Total avant taxe: "+totalAvantTaxeString,"Grand Total: "+ totalApresTaxeString}); // affichage du total dans la table
					btnAchet.setEnabled(false); // on bloque les bouttons acheter et terminer pour ne pas êtres capable d'ajouter d'autre item après avoir cliqué sur terminé
					btnTerminer.setEnabled(false);
				}
				else if(e.getSource() == btnPayez)
				{
					int pointsBonus = laCommande.calculerPointsBonis(); // pour calculer les points bonus
					if(rdbtnPaieCredit.isSelected())
					{
						// boolean qui contient si true ou false selon si la transaction est valide/dépasse pas le montant de 2000 sur le solde (par methode paieCommandeCredit())
						boolean PaiementCredit = EnsembleClients.getClient(textFieldNumCarte.getText()).paieCommandeCredit(laCommande);
						if(PaiementCredit)// vérification de si on peut payer par crédit
						{
							laCommande.devientPayee(true); // la commande est payé
						}	
						else 
							JOptionPane.showMessageDialog(null,"Impossible de payer par crédit (solde invalide)");// message de paiement par credit invalide
					}
					else if (rdbtnPaieComptant.isSelected())
					{
						// paiement de la commande et retour de change
						Double retourDeMonnaie = EnsembleClients.getClient(textFieldNumCarte.getText()).paieCommandeComptant(laCommande, Double.parseDouble(textAreaMDonne.getText()));
						if(retourDeMonnaie<0) // si on a pas assez d'argent
						{
							laCommande.devientPayee(false);
						}
						else
							laCommande.devientPayee(true);
						if(laCommande.estPayee()) // si la commande est payé, on affiche le montant de retour
						{
							textAreaMRemis.setText(df.format(retourDeMonnaie));// affiche le change dans le champ texte d'argent remis
							
						}
						else // si on refuse le paiement
						{
							JOptionPane.showMessageDialog(null,"Montant invalide");
							textAreaMRemis.setText(df.format(retourDeMonnaie));
						}
					}
					if(laCommande.estPayee()) // on bloque le bouton pour payer si le paiement est fait
					{
						btnPayez.setEnabled(false);
						EnsembleClients.getClient(textFieldNumCarte.getText()).modifierPoints(pointsBonus); // update des points dans hashtable
						textArea_PointBonus.setText(String.valueOf(EnsembleClients.getClient(textFieldNumCarte.getText()).getNbPointsAcc())); // changement du text area points
					}
				}
				else if(e.getSource() == btnAnnulerCommande) // annule/ nouvelle commande
				{
					// on remet les contenu approprié à ce qui était au depart (début du programme)
					textFieldNumCarte.setText("");
					textAreaNomClient.setText("");
					textArea_PointBonus.setText("");
					textAreaMDonne.setText("");
					textAreaMRemis.setText("");
					numCarteEntré=false;
					if(laCommande.estPayee()==false) // si on annule la commande (non payé)
					{
						for(int index=0;index<tableProduit.getRowCount()-2;index++) // getrow-2 pour pas prendre les lignes de affichage total et grand total
						{
							String nomDuProduit = (String) modele.getValueAt(index, 0); 
							Inventaire.getProduit(nomDuProduit).modifierQteStock(-1);// on remet en stock de inventaire les produits non vendu
						}
					}
					double valeurDeDouble;
					String valeurQuantite;
					valeurDeDouble = Inventaire.getProduit(((String)comboBoxARTICLE.getSelectedItem())).getQteStock();
					valeurQuantite = df1.format(valeurDeDouble);
					btnAchet.setEnabled(true);
					btnTerminer.setEnabled(true);
					btnPayez.setEnabled(true);
					textAreaStock.setText(valeurQuantite);
					modele.setRowCount(0);
					laCommande = new Commande(textFieldNumCarte.toString());
				}
			}
			if(e.getSource() == mntmNouveauClient) // le jmenu pour avoir un nouveau client
			{
				dialogue.setLocationRelativeTo(leFrame.this);
				dialogue.setVisible(true);
			}
			else if (e.getSource() == mntmFinDeSession) // enregistrement sur excel en fin de session
			{
				 try { 
						InputStream input = new FileInputStream("Produits.xlsx");
						XSSFWorkbook classeur = (XSSFWorkbook) WorkbookFactory.create(input);
						XSSFSheet feuille = classeur.getSheetAt(0);
						int indexDeFeuille = 1; // premiere range sont es titres donc pas 0 mais 1
						for(String key:Inventaire.getListe().keySet())
						{
							// on change les valeurs de cellules
							XSSFRow rangee = feuille.getRow(indexDeFeuille);
							XSSFCell celluleCodeProduit = rangee.getCell(0); // code de produit
							celluleCodeProduit.setCellValue(Inventaire.getProduit(key).getCode());
							XSSFCell nomDeProduit = rangee.getCell(1); // le nom de produit
							nomDeProduit.setCellValue(Inventaire.getProduit(key).getNom());
							XSSFCell quantiteEnStock = rangee.getCell(2);
							quantiteEnStock.setCellValue(Inventaire.getProduit(key).getQteStock());
							XSSFCell leCout = rangee.getCell(3);
							leCout.setCellValue(Inventaire.getProduit(key).getPrix());
							XSSFCell pointsDuProduit = rangee.getCell(4);
							pointsDuProduit.setCellValue(Inventaire.getProduit(key).getPoints());;
							indexDeFeuille++;
						}
						OutputStream output = new FileOutputStream("Produits.xlsx");// ecriture dans le fichier produit
						classeur.write(output);
						output.close();
						input.close();
						
					} catch (Exception e1) {e1.printStackTrace();}
				 
				 try {
						InputStream input = new FileInputStream("Clients.xlsx");
						XSSFWorkbook classeur = (XSSFWorkbook) WorkbookFactory.create(input);
						XSSFSheet feuille = classeur.getSheetAt(0);
						int index = 1; // index de rangé
						for(String key:EnsembleClients.getListe().keySet())
						{
							XSSFRow rangee = feuille.getRow(index);
							XSSFCell celluleNumCarte = rangee.getCell(0);
							celluleNumCarte.setCellValue(EnsembleClients.getClient(key).getNoCarte());
							XSSFCell celluleNom = rangee.getCell(1);
							celluleNom.setCellValue(EnsembleClients.getClient(key).getNom());
							XSSFCell cellulePts = rangee.getCell(2);
							cellulePts.setCellValue(EnsembleClients.getClient(key).getNbPointsAcc());
							XSSFCell celluleCCSolder = rangee.getCell(3);
							celluleCCSolder.setCellValue(EnsembleClients.getClient(key).getSoldeCarteCredit());
							index++;
						}
						OutputStream output = new FileOutputStream("Clients.xlsx");// ecriture dans le fichier clients
						classeur.write(output);
						output.close();
						input.close();
						
					} catch (Exception e2) {e2.printStackTrace();}
				 
				 System.exit(0); // fin du programme
			}
		}
		
	}
	
	private class EcouteurComboBox implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() instanceof JComboBox) // pour ne pas avoir besoin d'un autre ecouteur (fonctione car on 
			{									// 												a juste un combobox)
				// get le prix de l'item selectioné
				double valeurDeDouble = Inventaire.getProduit(((String)comboBoxARTICLE.getSelectedItem())).getPrix();
				// formatage pour $
				String valeurDeText = df.format(valeurDeDouble);
				// set la valeur du text area avec la string formaté
				textAreaPrix.setText(valeurDeText);
				valeurDeDouble = Inventaire.getProduit(((String)comboBoxARTICLE.getSelectedItem())).getQteStock();
				valeurDeText = df1.format(valeurDeDouble);
				textAreaStock.setText(valeurDeText);
			}
			
		}
		
	}
	
	private class EcKey implements KeyListener
	{

		@Override
		public void keyTyped(KeyEvent e) {}
		@Override
		public void keyPressed(KeyEvent e)  // pour quand on press sur enter dans la zone de numéro de client
		{
			if(e.getKeyCode() == KeyEvent.VK_ENTER) // quand le boutton enter est pressed (lier au champ de numéro de carte)
			{
				if(EnsembleClients.getListe().containsKey(textFieldNumCarte.getText())) // pour vérifier si notre hashtable contient la clé (numéro de la carte)
				{
					numCarteEntré = true; // on peut maintenant faire une transaction(access au autre bouttons)
					// affichage du nom de client
					textAreaNomClient.setText(EnsembleClients.getClient(textFieldNumCarte.getText()).getNom());
					// affichage des points bonus du client
					textArea_PointBonus.setText(Integer.toString(EnsembleClients.getClient(textFieldNumCarte.getText()).getNbPointsAcc()));
					laCommande = new Commande(textFieldNumCarte.getText()); // nouvelle commande
					textAreaMDonne.setEditable(true); // on peut maintenant écrire le montant payé du client
				}
				else
				{
					JOptionPane.showMessageDialog(null, "numero de carte membre invalide ou n'existe pas");// pop up pour dire que la carte est pas valide
					textFieldNumCarte.setText("");// vide le contenu du textfield du numero de carte
				}
			}
		}
		
		@Override
		public void keyReleased(KeyEvent e) {}
		
	}
}
