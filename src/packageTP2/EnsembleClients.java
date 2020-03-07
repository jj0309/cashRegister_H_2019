package packageTP2;
import java.util.*;

import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.InputStream; 

public class EnsembleClients 
{
private static Hashtable<String, Client> listeClients = new Hashtable<String,Client>();

 


	public static void lectureDeXSSF()
	{
		try {
			InputStream input = new FileInputStream("Clients.xlsx");
			XSSFWorkbook classeur = (XSSFWorkbook) WorkbookFactory.create(input);
			XSSFSheet feuille = classeur.getSheetAt(0);
			int index = 1;
			while(feuille.getRow(index) != null)
			{
				XSSFRow rangee = feuille.getRow(index);
				XSSFCell celluleNumCarte = rangee.getCell(0);
				XSSFCell celluleNom = rangee.getCell(1);
				XSSFCell cellulePts = rangee.getCell(2);
				XSSFCell celluleCCSolder = rangee.getCell(3);
				Client client = new Client(Integer.toString(((int)celluleNumCarte.getNumericCellValue())),
						celluleNom.getStringCellValue(),(int)cellulePts.getNumericCellValue(),celluleCCSolder.getNumericCellValue());
				ajouterClient(client);
				index++;
			}
		} catch (Exception e) {e.printStackTrace();}
	}
	
	
  public static Hashtable<String, Client> getListe()
  {
	  return listeClients;
  }

  public static Client getClient ( String noCarteMembre )
  {
	  return listeClients.get(noCarteMembre);
  }

  public static void ajouterClient ( Client c)
  {
	  listeClients.put(c.getNoCarte(), c);
  }
  
  

}