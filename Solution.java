import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solution {

	public static Map<Double, Integer> StockPieces;

	public static List<Double> rendus;

	public static void initStockePieces() {

		StockPieces = new HashMap<Double, Integer>();

		getStockPieces().put(5.0, 0);
		getStockPieces().put(2.0, 0);
		getStockPieces().put(1.0, 0);
		getStockPieces().put(0.5, 0);
		getStockPieces().put(0.2, 0);
		getStockPieces().put(0.1, 0);
		getStockPieces().put(0.05, 0);

	}

	public static Map<Double, Integer> getStockPieces() {
		return StockPieces;
	}

	public void setStockPieces(Map<Double, Integer> stockPieces) {
		StockPieces = stockPieces;
	}

	public static int getNbPieceOfType(List<Double> inputPieces, double typePiece) {
		int nbPieceOfType = 0;

		for (double piece : inputPieces) {
			if (piece == typePiece) {
				nbPieceOfType++;
			}
		}
		return nbPieceOfType;
	}
	
	public static List<Double> getTypeMonnaie(List<Double> inputPieces) {
		List<Double> listTypeMonnaie = new ArrayList<Double>();
		double previousPiece = 0;
		
		for (double piece : inputPieces) {
			if(previousPiece != piece) {
				listTypeMonnaie.add(piece);
			}
			previousPiece = piece;
		}
		return listTypeMonnaie;
	}

	public static int addInStock(List<Double> inputPieces) {
		int newStock;

		for (double piece : getTypeMonnaie(inputPieces)) {
			int nbPieceOfThisType = getNbPieceOfType(inputPieces, piece);
			if ((StockPieces.get(piece) + nbPieceOfThisType) < 20) {
				newStock = (StockPieces.get(piece) + nbPieceOfThisType);
				StockPieces.put(piece, newStock);
			} else {
				System.out.println("Veuillez inserer un nombre de piece de "+piece+"CHF moins important");
				return -1;
			}
		}
		return 0;
	}

	public static void removeInStock(Double piece) {
		if (StockPieces.get(piece) > 0) {
			int newStock = StockPieces.get(piece) - 1;
			StockPieces.put(piece, newStock);
		} else {
			System.out.println("La machine n'a pas assez de pieces de : " + piece);
		}
	}

	public static void removeInStock(List<Double> inputPieces) {
		for(double piece : inputPieces) {
			int newStock = StockPieces.get(piece) - 1;
			StockPieces.put(piece, newStock);
		}
	}

	public static double getSomme(List<Double> inputPieces) {
		double somme = 0;
		for (double piece : inputPieces) {
			somme = somme + piece;
		}
		return round(somme, 2);
	}

	public static List<Double> renduMonnnaie(List<Double> inputPieces, double montantAchat) {
		double montantInput = getSomme(inputPieces);

		if (montantInput < montantAchat) {
			System.out.println("Montant inséré insuffisant");
			return null;
		} else {
			if (addInStock(inputPieces) != -1) {
				return helper(montantAchat, inputPieces);
			}
		}
		return null;
	}

	private static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(Double.toString(value));
		bd = bd.setScale(places, RoundingMode.HALF_UP);

		return bd.doubleValue();
	}
	
	private static double getRest(Double typePiece, Double resteAPayer) {
		if ((resteAPayer - typePiece >= 0) 
				&& (StockPieces.get(typePiece) > 0)) 
		{
			resteAPayer = round((resteAPayer - typePiece), 2);
			removeInStock(typePiece);
			rendus.add(typePiece);
		return resteAPayer;
		}
		
		return resteAPayer;
	}

	private static List<Double> helper(double montantAchat, List<Double> inputPieces) {
		
		double aRendre = getSomme(inputPieces) - montantAchat;
		rendus = new ArrayList<Double>();

			for (Double typePiece : StockPieces.keySet()) {
				while (aRendre > 0 && aRendre >= typePiece && StockPieces.get(typePiece) > 0) {
				aRendre = getRest(typePiece, aRendre);
				}
			}
				
			if (aRendre > 0) {
				System.out.println("La machine n'a pas assez de monnaie");
				removeInStock(inputPieces);
				rendus = inputPieces;
			}


		return rendus;

	}

	public static void main(String[] args) throws IOException {

		initStockePieces();
		List<Double> inputPiece = new ArrayList<Double>();
		double montantAchat;
		
		/**
		 * Exemple 1: Montant inséré insuffisant
		 */
		
		inputPiece.add(0.10);
		montantAchat = 0.80;
		System.out.println(renduMonnnaie(inputPiece, montantAchat));

		/**
		 * Exemple 2: Machine n'ayan pas assez de monnaie
		 */
		inputPiece.clear();
		inputPiece.add(1.0);inputPiece.add(1.0);inputPiece.add(1.0);inputPiece.add(1.0);inputPiece.add(1.0);inputPiece.add(1.0);
		inputPiece.add(1.0);inputPiece.add(1.0);inputPiece.add(1.0);inputPiece.add(1.0);inputPiece.add(1.0);inputPiece.add(1.0);
		montantAchat = 1.30;
		System.out.println(
				"Montant de l'achat : " + montantAchat + "CHF, Montant inséré " + getSomme(inputPiece) + "CHF");
		System.out.println("Rendu en piéce de monnaies : "
				+ renduMonnnaie(inputPiece, montantAchat));
		
		/**
		 * Exemple 3: Montant Ok, rendu monnaie Ok
		 */
		inputPiece.clear();
		inputPiece.add(5.0);
		montantAchat = 1.10;
		System.out.println(
				"Montant de l'achat : " + montantAchat + "CHF, Montant inséré " + getSomme(inputPiece) + "CHF");
		System.out.println("Rendu en piéce de monnaies : "
				+ renduMonnnaie(inputPiece, montantAchat));

	}

}