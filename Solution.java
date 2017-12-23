import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 
 */

/**
 * @author Narimene MEZINE
 *
 */
public class Solution {

	public static Map<Double, Integer> StockCoins;
	public static List<Double> rendus;
	public static List<Double> expectedCoin;


	/**
	 *  init the list of coins have to return
	 */
	public static void initListCoinsToReturn() {
		rendus = new ArrayList<Double>();
	}

	/**
	 * @return List Double : return a list of all monnaie type accepted
	 */
	public static List<Double> initExpectedCoinType()
	{
		expectedCoin = new ArrayList<Double>();
		expectedCoin.add(5.0);
		expectedCoin.add(2.0);
		expectedCoin.add(1.0);
		expectedCoin.add(0.5);
		expectedCoin.add(0.2);
		expectedCoin.add(0.1);
		expectedCoin.add(0.05);

		return expectedCoin;
	}

	/**
	 * init the stock
	 */
	public static void initStockCoins() {
		expectedCoin = initExpectedCoinType();
		StockCoins = new HashMap<Double, Integer>();

		for (double typeCoin : expectedCoin) {
			getStockCoins().put(typeCoin, 15);
		}
	}

	/**
	 * @return Map of Double and Integer : return all stock
	 */
	public static Map<Double, Integer> getStockCoins() {
		return StockCoins;
	}

	/**
	 * @param stockCoins
	 */
	public void setStockCoins(Map<Double, Integer> stockCoins) {
		StockCoins = stockCoins;
	}

	/**
	 * @param inputCoins
	 * @param typeCoin
	 * @return int : return nb of coin of different type
	 */
	public static int getNbCoinOfType(List<Double> inputCoins, double typeCoin) {
		int nbCoinOfType = 0;

		for (double coin : inputCoins) {
			if (coin == typeCoin) {
				nbCoinOfType++;
			}
		}
		return nbCoinOfType;
	}

	/**
	 * @param inputCoins
	 * @return List of Double : return a list of distinct monnaie type
	 */
	public static List<Double> getTypeMonnaie(List<Double> inputCoins) {
		List<Double> listTypeMonnaie = new ArrayList<Double>();

		for (double coin : inputCoins) {
			if(!listTypeMonnaie.contains(coin)) {
				listTypeMonnaie.add(coin);
			}
		}
		return listTypeMonnaie;
	}

	/**
	 * @param inputCoins
	 * @return int : 0 if OK, -1 if the stock of any CoinType is full (NOK)
	 */
	public static int addInStock(List<Double> inputCoins) {
		int newStock = 0;

		for (double coin : getTypeMonnaie(inputCoins)) {
			int nbCoinOfThisType = getNbCoinOfType(inputCoins, coin);
			if ((StockCoins.get(coin) + nbCoinOfThisType) < 20) {
				newStock = (StockCoins.get(coin) + nbCoinOfThisType);
				StockCoins.put(coin, newStock);
			} else {
				System.out.println("Veuillez inserer un nombre de coin de "+coin+"CHF moins important");
				return -1;
			}
		}
		return 0;
	}

	/**
	 * @param coin
	 * remove the coin from the stock
	 */
	public static void removeCoinFromStock(Double coin) {
		if (StockCoins.get(coin) > 0) {
			int newStock = StockCoins.get(coin) - 1;
			StockCoins.put(coin, newStock);
		} else {
			System.out.println("La machine n'a pas assez de pieces de : " + coin);
		}
	}

	/**
	 * @param inputCoins
	 * remove the list of coin from the stock
	 */
	public static void removeInStock(List<Double> inputCoins) {
		for(double coin : inputCoins) {
			int newStock = StockCoins.get(coin) - 1;
			StockCoins.put(coin, newStock);
		}
	}

	/**
	 * @param inputCoins
	 * @return the somme of input coins
	 */
	public static double getSomme(List<Double> inputCoins) {
		double somme = 0;
		for (double coin : inputCoins) {
			somme = somme + coin;
		}
		return round(somme, 2);
	}



	/**
	 * @param inputCoins
	 * @return boolean : true if all input coins are accepted, false if not
	 */
	public static boolean isValidCoins(List<Double> inputCoins) {

		for(double coin : inputCoins) {
			if(!expectedCoin.contains(coin)) {
				System.out.println("Merci d'inserer seulement les piéces accepté par la machine");
				return false;
			}
		}
		return true;
	}

	/**
	 * @param inputCoins
	 * @param montantAchat
	 * @return List of Double : return coins if all is ok, null if not
	 */
	public static List<Double> renduMonnnaie(List<Double> inputCoins, double montantAchat) {

		if(isValidCoins(inputCoins)) {
			double montantInput = getSomme(inputCoins);

			if (montantInput < montantAchat) {
				System.out.println("Montant inséré insuffisant");
				return null;
			} else {
				if (addInStock(inputCoins) != -1) {
					return helper(montantAchat, inputCoins);
				}
			}
		}
		return null;
	}

	/**
	 * @param value
	 * @param places
	 * @return double : return round of any number in param
	 */
	static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(Double.toString(value));
		bd = bd.setScale(places, RoundingMode.HALF_UP);

		return bd.doubleValue();
	}

	/**
	 * @param typeCoin
	 * @param resteAPayer
	 * @return double : return the rest have to return by the machine
	 */
	static double getRest(Double typeCoin, Double resteAPayer) {
		if ((resteAPayer - typeCoin >= 0) 
				&& (StockCoins.get(typeCoin) > 0)) 
		{
			resteAPayer = round((resteAPayer - typeCoin), 2);
			removeCoinFromStock(typeCoin);
			rendus.add(typeCoin);
			return resteAPayer;
		}

		return resteAPayer;
	}

	/**
	 * @param montantAchat
	 * @param inputCoins
	 * @return List<Double> : return list of coins have to return if the stock is ok, null if not
	 */
	static List<Double> helper(double montantAchat, List<Double> inputCoins) {
		initListCoinsToReturn();
		double aRendre = getSomme(inputCoins) - montantAchat;

		for (Double typeCoin : StockCoins.keySet()) {
			while (aRendre > 0 && aRendre >= typeCoin && StockCoins.get(typeCoin) > 0) {
				aRendre = getRest(typeCoin, aRendre);
			}
		}

		if (aRendre > 0) { //si apres avoir fait le tour dans mon stock et que je n'ai pas assez de monnaie pour rendre
			System.out.println("La machine n'a pas assez de monnaie");
			addInStock(rendus);//je remet en stock les pieces que je voulais lui rendre
			removeInStock(inputCoins); //j'enleve du stocks les pieces qu'il ma donné
			rendus = inputCoins; //je rend les pieces qu'il ma donné
		}


		return rendus;

	}

	public static void main(String[] args) throws IOException {
		Solution.initStockCoins();
		List<Double> 	inputCoin 		= new ArrayList<Double>();
		List<Double> 	returnCoins 	= new ArrayList<Double>();
		
		//TODO : remplire le montant de l'achat ainsi que la liste des pieces inseré
		double 			montantAchat 	= 1.20;
		inputCoin.add(2.0);

		System.out.println(
				"Montant de l'achat : " + montantAchat + "CHF, Montant inséré " + getSomme(inputCoin) + "CHF");
		returnCoins = renduMonnnaie(inputCoin, montantAchat);
		System.out.println("Monnaie rendu :"+ getSomme(returnCoins)+"CHF");
	}

}