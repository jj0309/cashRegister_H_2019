package packageTP2;
import java.util.*;
public class Commande 
{
private int numero;
private String noCarteMembreClient;
private Vector<Item> items;
private boolean estPayee;
private static int compteur;

  public Commande(String noCarte)
  {
	  this.numero=compteur++;
	  this.noCarteMembreClient = noCarte;
	  items = new Vector<Item>();
  }

  public boolean estPayee() {
	return estPayee;
}

public void devientPayee(boolean estPayee) {
	this.estPayee = estPayee;
}

public int getNumero()
{
	return numero;
}
public String getNoCarteMembreClient()
{
	return noCarteMembreClient;
}
public Vector<Item> getItems()
{
	return items;
}
public void ajouterItem ( Item i) // ajout de item dans la commande (vecteur de commande)
{
	items.add(i);
}
  
  public double calculerSousTotal () // calculer total avant tax
  {
  double total = 0;
  for ( int i = 0; i < items.size(); i++ )
    {
    Item item = items.get(i);
    int qte = item.getQte();
    Produit p = Inventaire.getProduit(item.getNomProduit());
    double prix = p.getPrix();
    total += qte * prix;
    }

  return total;
  }
  
  public double calculerTPS ()
  {
    double total = calculerSousTotal();
    return total*0.05;
  }
    //À partir du 1er janvier 2013, la taxe de vente du Québec est harmonisée avec le régime de la TPS. 
    //Ainsi, la TVQ est calculée sur le prix excluant la TPS. Pour contrer les pertes de revenu encourues 
    //par le gouvernement québécois suite à cette modification, le taux de la TVQ a été porté à 9,975 %. 
    //Pour les consommateurs, la modification n'a aucun impact en terme de montant à débourser, 
    //puisque le taux combiné est identique à celui de 2012, soit 14,975 %
  
  public double calculerTVQ()
  {
    double total = calculerSousTotal();
    return total * 0.09975;
  }
  
  public double calculerGrandTotal() // grand total avec tax
  {
	  double total = calculerSousTotal() + calculerTPS() + calculerTVQ();
	  
	  return total;
  }
  
  public int calculerPointsBonis() // pour calcul de points
  {
	  int total = 0;
	  for ( int i = 0; i < items.size(); i++ )
	    {
	    Item item = items.get(i);
	    int qte = item.getQte();
	    Produit p = Inventaire.getProduit(item.getNomProduit());
	    int points = p.getPoints();
	    total += qte * points;
	    }
	
	  return total;
  }
 
 
}