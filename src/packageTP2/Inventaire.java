package packageTP2;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class Inventaire 
{
	 private static Hashtable<String, Produit> listeProduits = new Hashtable<String, Produit>();
	
	 public static void lectureXSSF()
	 {
		 try {
			InputStream input = new FileInputStream("Produits.xlsx");
			XSSFWorkbook classeur = (XSSFWorkbook) WorkbookFactory.create(input);
			XSSFSheet feuille = classeur.getSheetAt(0);
			int indexDeFeuille = 1; // premiere range sont es titres donc pas 0 mais 1
			while(feuille.getRow(indexDeFeuille) != null)
			{
				XSSFRow rangee = feuille.getRow(indexDeFeuille);
				XSSFCell celluleCodeProduit = rangee.getCell(0); // code de produit
				XSSFCell nomDeProduit = rangee.getCell(1); // le nom de produit
				XSSFCell quantiteEnStock = rangee.getCell(2);
				XSSFCell leCout = rangee.getCell(3);
				XSSFCell pointsDuProduit = rangee.getCell(4);
				Produit produit = new Produit((int)celluleCodeProduit.getNumericCellValue(),nomDeProduit.getStringCellValue(),
						(int)quantiteEnStock.getNumericCellValue(),(int)leCout.getNumericCellValue(),(int)pointsDuProduit.getNumericCellValue());
				ajouterProduit(produit);
				indexDeFeuille++;
			}
			
		} catch (Exception e) {e.printStackTrace();}
	 }
 


	 public static Hashtable<String, Produit> getListe()
	 {
		 return listeProduits;
	 }
	
	 public static Produit getProduit ( String  nom )
	 {
		 return listeProduits.get(nom);
	 }
	
	 public static void ajouterProduit ( Produit p)
	 {
		 listeProduits.put(p.getNom(), p);
	 }

 
  
}