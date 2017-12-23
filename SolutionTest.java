import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * 
 */

/**
 * @author Narimene MEZINE
 *
 */
public class SolutionTest {

	@Test
	public void initExpectedCoinType_TEST() {
		List<Double> ExpectedCoinTypeTest = Solution.initExpectedCoinType();
		
		List<Double> ExpectedCoinTypeList = new ArrayList<Double>();
		ExpectedCoinTypeList.add(5.0);
		ExpectedCoinTypeList.add(2.0);
		ExpectedCoinTypeList.add(1.0);
		ExpectedCoinTypeList.add(0.5);
		ExpectedCoinTypeList.add(0.2);
		ExpectedCoinTypeList.add(0.1);
		ExpectedCoinTypeList.add(0.05);
		
		
		assertTrue(ExpectedCoinTypeTest.equals(ExpectedCoinTypeList));
	}
	
	
	@Test
	public void initStockCoins_TEST() {
		Map<Double, Integer> StockCoins = new HashMap<Double, Integer>();
		Solution.initStockCoins();
		
		StockCoins.put(5.0, 15);
		StockCoins.put(2.0, 15);
		StockCoins.put(1.0, 15);
		StockCoins.put(0.5, 15);
		StockCoins.put(0.2, 15);
		StockCoins.put(0.1, 15);
		StockCoins.put(0.05, 15);
		
		assertTrue(StockCoins.equals(Solution.getStockCoins()));
	}
	
	@Test
	public void getNbCoinOfType_TEST() {
		List<Double> listCoins = new ArrayList<Double>();
		listCoins.add(5.0);
		listCoins.add(5.0);
		listCoins.add(1.0);
		listCoins.add(5.0);
		int nbCoin = Solution.getNbCoinOfType(listCoins, 5.0);
		
		assertTrue(nbCoin == 3);
	}
	
	@Test
	public void getTypeMonnaie_TEST(){
		List<Double> inputCoinsTest = new ArrayList<Double>();
		inputCoinsTest.add(0.05);
		inputCoinsTest.add(2.0);
		inputCoinsTest.add(1.0);
		inputCoinsTest.add(0.05);
		
		List<Double> inputCoins = new ArrayList<Double>();
		inputCoins.add(0.05);
		inputCoins.add(2.0);
		inputCoins.add(1.0);
		
		assertTrue(inputCoins.equals(Solution.getTypeMonnaie(inputCoinsTest)));
	}
	
	@Test
	public void addInStock_TEST() {
		List<Double> inputCoins = new ArrayList<Double>();
		inputCoins.add(0.05);
		inputCoins.add(2.0);
		inputCoins.add(1.0);
		inputCoins.add(1.0);
		
		Solution.initStockCoins();
		Solution.addInStock(inputCoins);
		
		Map<Double, Integer> StockCoinsTest = new HashMap<Double, Integer>();
		
		StockCoinsTest.put(5.0, 15);
		StockCoinsTest.put(2.0, 16);
		StockCoinsTest.put(1.0, 17);
		StockCoinsTest.put(0.5, 15);
		StockCoinsTest.put(0.2, 15);
		StockCoinsTest.put(0.1, 15);
		StockCoinsTest.put(0.05, 16);
		
		assertTrue(StockCoinsTest.equals(Solution.getStockCoins()));
	}
	
	@Test
	public void removeCoinFromStock_TEST() {
		double coin = 5.0;
		
		Solution.initStockCoins();
		Solution.removeCoinFromStock(coin);
		
		Map<Double, Integer> StockCoinsTest = new HashMap<Double, Integer>();
		
		StockCoinsTest.put(5.0, 14);
		StockCoinsTest.put(2.0, 15);
		StockCoinsTest.put(1.0, 15);
		StockCoinsTest.put(0.5, 15);
		StockCoinsTest.put(0.2, 15);
		StockCoinsTest.put(0.1, 15);
		StockCoinsTest.put(0.05, 15);
		
		assertTrue(StockCoinsTest.equals(Solution.getStockCoins()));
	}
	
	@Test
	public void removeInStock_TEST() {
		List<Double> coinsToRemove = new ArrayList<Double>();
		coinsToRemove.add(0.05);
		coinsToRemove.add(2.0);
		coinsToRemove.add(1.0);
		coinsToRemove.add(1.0);
		
		Solution.initStockCoins();
		Solution.removeInStock(coinsToRemove);
		
		Map<Double, Integer> StockCoinsTest = new HashMap<Double, Integer>();
		
		StockCoinsTest.put(5.0, 15);
		StockCoinsTest.put(2.0, 14);
		StockCoinsTest.put(1.0, 13);
		StockCoinsTest.put(0.5, 15);
		StockCoinsTest.put(0.2, 15);
		StockCoinsTest.put(0.1, 15);
		StockCoinsTest.put(0.05, 14);
		
		assertTrue(StockCoinsTest.equals(Solution.getStockCoins()));
	}
	
	@Test
	public void getSomme_TEST() {
		List<Double> inputCoins = new ArrayList<Double>();
		inputCoins.add(0.05);
		inputCoins.add(2.0);
		inputCoins.add(1.0);
		inputCoins.add(1.0);
		
		assertTrue(4.05 == Solution.getSomme(inputCoins));
	}
	
	@Test
	public void isValidCoins_TEST() {
		List<Double> coinsToTest = new ArrayList<Double>();
		coinsToTest.add(0.30);
		boolean isValidTest = Solution.isValidCoins(coinsToTest);
		
		assertTrue(!isValidTest);
	}
	
	@Test
	public void renduMonnnaie_TEST() {
		Solution.initStockCoins();
		List<Double> 	inputCoin 		= new ArrayList<Double>();
		List<Double> 	returnCoins 	= new ArrayList<Double>();
		List<Double> 	returnCoinsTest 	= new ArrayList<Double>();
		double 			montantAchat 	= 0;
		
		/**
		 * Test 1: Montant inséré insuffisant
		 */
		
		inputCoin.add(0.10);
		montantAchat = 0.80;
		System.out.println("***************************** TEST 1 ***************************** MONTANT INSUFISSANT");
		assertTrue(Solution.renduMonnnaie(inputCoin, montantAchat) == null);
		
		/**
		 * Test 2: Machine n'ayan pas assez de monnaie
		 */
		inputCoin.clear();
		inputCoin.add(1.0);inputCoin.add(1.0);inputCoin.add(0.5);inputCoin.add(1.0);inputCoin.add(1.0);inputCoin.add(1.0);
		inputCoin.add(1.0);inputCoin.add(1.0);inputCoin.add(1.0);inputCoin.add(1.0);inputCoin.add(1.0);inputCoin.add(1.0);
		montantAchat = 1.30;
		
		System.out.println("***************************** TEST 2 ***************************** STOCK DE PIECES FULL");
		System.out.println(
				"Montant de l'achat : " + montantAchat + "CHF, Montant inséré " + Solution.getSomme(inputCoin) + "CHF");
		assertTrue(Solution.renduMonnnaie(inputCoin, montantAchat) == null);
		
		/**
		 * Test 3: Rendu de monnaie OK
		 */
		inputCoin.clear();
		inputCoin.add(2.0);
		montantAchat = 1.20;
		
		System.out.println("***************************** TEST 3 ***************************** RENDU OK");
		returnCoinsTest = Solution.renduMonnnaie(inputCoin, montantAchat);
		returnCoins.add(0.5);
		returnCoins.add(0.2);
		returnCoins.add(0.1);
		System.out.println(
				"Montant de l'achat : " + montantAchat + "CHF, Montant inséré " + Solution.getSomme(inputCoin) + "CHF");
		assertTrue(returnCoins.equals(returnCoins));
		System.out.println("Monnaie rendu :"+Solution.getSomme(returnCoins)+"CHF");
		
		/**
		 * Test 4: Piece pas accepté par la machine
		 */
		inputCoin.clear();
		inputCoin.add(1.0);
		inputCoin.add(0.3);
		montantAchat = 1.20;
		System.out.println("***************************** TEST 4 ***************************** MONNAIE PAS ACCEPTE");
		assertTrue(Solution.renduMonnnaie(inputCoin, montantAchat) == null);
		
	}
	
	@Test
	public void round_TEST() {
		double roundTest = Solution.round(23.122017, 2);
		double round = 23.12;
		
		assertTrue(roundTest == round);
	}
	
	@Test
	public void getRest_TEST() {
		Solution.initStockCoins();
		Solution.initListCoinsToReturn();
		double restTest = Solution.getRest(1.0, 2.0);
		assert(restTest == 1.0);
	}
	
	@Test
	public void helper_TEST() {
		Solution.initStockCoins();
		List<Double> 	inputCoins 		= new ArrayList<Double>();
		inputCoins.add(1.0);
		inputCoins.add(0.3);
		inputCoins.add(0.2);
		List<Double> 	returnCoinsTest = Solution.helper(1.30, inputCoins);
		
		assertTrue(Solution.getSomme(returnCoinsTest) == 0.2);
	}
	
	}
