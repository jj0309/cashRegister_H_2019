package packageTP2;

public class Client 
{

private String noCarteMembre;
private String nom;
private int nbPointsAcc;
private double soldeCarteCredit;

  public Client(String noCarteMembre, String nom, int nbPointsAcc, double soldeCarteCredit)
  {
	  this.noCarteMembre=noCarteMembre;
	  this.nbPointsAcc=nbPointsAcc;
	  this.nom = nom;
	  this.soldeCarteCredit = soldeCarteCredit;
  
  }

  public String getNoCarte()
  {
	  return noCarteMembre;
  }
  
  public String getNom()
  {
 	return nom;
  }
  
  public int getNbPointsAcc()
  {
	  return nbPointsAcc;
  }
  
  public void modifierPoints ( int nbPointsSupp )
  {
	  this.nbPointsAcc+= nbPointsSupp;
  }

 
  public boolean assezArgent ( Commande c, double montant ) // pour verifier si assez d'argent
  {
	  double total = c.calculerGrandTotal();
	  if ( montant  >= total )
	    return true;
	  else 
	    return false;
  }
  
  public int arrondirCent ( double montant ) // pour arrondir les cents selon 2 chiffre apres virgules
  {
	  double montantEnCentsAvecPoussieres = montant * 100;
	  int montantEnCents = ( int ) Math.round(montantEnCentsAvecPoussieres);
	  return montantEnCents;
  }
  
  public double paieCommandeComptant ( Commande c, double montant )
  {
	  double total = c.calculerGrandTotal(); // total de la commande
	  System.out.println(total);
	  double change = montant- total; // montant en comptant - total de commande
	  int cents=0;
	  System.out.println ( change);
	  //multiplie par 100 pour avoir cents 
	  /*
	  change = change*100;
	  System.out.println ( change);
	  //arrondit
	  cents = (int)Math.round(change);
	  */
	  cents = arrondirCent(change); // arrondie le retour de monnaie 2 chiffres apres virgule
	  System.out.println ( cents);
	  int reste = cents % 5;
	  System.out.println(reste);
	  if ( reste >=3)
		  cents = cents + ( 5-reste );
	  else
		  cents = cents - reste;
	  System.out.println(cents);
	  //remettre en dollars
	  change = cents/100.0;
	  System.out.println(change);
	  int nbPoints = c.calculerPointsBonis();
	  if ( change > 0) // et si on donne le montant exact?
	  {
	      modifierPoints(nbPoints);
	      c.devientPayee(true);
	  }

	  return change;
  }
  
  public boolean paieCommandeCredit ( Commande c )// paiement, on passe la commande en parametre
  {
  double total = c.calculerGrandTotal(); // on calcule le montant total
  System.out.println(total);
  if ( soldeCarteCredit + total <= 2000) // solde de cc + total peut pas depasser 2000?
  	{
	  int totalEnCents = arrondirCent(total);
	//remettre en dollars
	 
	  soldeCarteCredit += totalEnCents/100.0;
	  int nbPoints = c.calculerPointsBonis();
	  modifierPoints(nbPoints);
	  c.devientPayee(true);
	  return true; // cc paiement accepted
  	}
  else
	  return false; // cc declined
  }

public double getSoldeCarteCredit() {
	return soldeCarteCredit;
}

public void setSoldeCarteCredit(double soldeCarteCredit) {
	this.soldeCarteCredit = soldeCarteCredit;
}
  
}