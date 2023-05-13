import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;


public class DynamicAlgorithm {
	private  static Predmet[] predmeti;
	private static int[] pokupljeni_predmeti;
	private int ukupnaVrijednost;
	private static int n;
	private static int maxWeight;
	private static int weights[];
	private static int values[];

	
	private static class Predmet{
		public double tezina;
		public double vrijednost;
		
		public Predmet(double tezina, double vrijednost) {
			this.tezina = tezina;
			this.vrijednost = vrijednost;
		}
	}
	
	
    static int vrijednostPokupljenog(int W, int[] wt, int[] val, int n) { 
    	predmeti = new Predmet[n];
        int K[][] = new int[n + 1][W + 1]; 
        for(int j = 0; j<n; j++){
        	predmeti[j] = new Predmet(wt[j],val[j]); // svi predmeti 
        }
        
        pokupljeni_predmeti = new int[n];
        for(int i = 0; i<n; i++)
        	pokupljeni_predmeti[i] = 0;
  
        // Izgradnja tabele K[][], odozgo na gore
        for (int i = 0; i <= n; i++)  { 
            for (int w = 0; w <= W; w++)  { 
                if (i == 0 || w == 0) K[i][w] = 0; 
                else if (wt[i - 1] <= w) K[i][w] = Math.max(
                                val[i - 1] + K[i - 1][w - wt[i - 1]], K[i - 1][w]
                        ); 
                else K[i][w] = K[i - 1][w]; 
            } 
        } 
        
        //Da li je predmet pokupljen?
        int v = K[n][W];
        int w = W;
        for(int i = n-1; i> 0; i--){
        		if(K[i][w] > K[i-1][w] && K[i][w] <= v)
        		{
        			pokupljeni_predmeti[i] = 1;
        		    v -= val[i];
        		    w = wt[i-1];
        		}
        }
        
        return K[n][W]; 
    } 
  
    public static void readFromFile(String filename) {
   	 try {
   	      File myObj = new File(filename);
   	      Scanner myReader = new Scanner(myObj);
   	      int br = 0;
   	      String nw[] = myReader.nextLine().split(" ");
			  n = Integer.parseInt(nw[0]);
			  maxWeight = Integer.parseInt(nw[1]);
			  weights = new int[n];
			  values = new int[n];
   	      while (myReader.hasNextLine()) {
   	        String data[] = myReader.nextLine().split(" ");
   	        weights[br] = Integer.parseInt(data[0]);
   	        values[br] = Integer.parseInt(data[1]);
   	        br++;
   	      }
   	      myReader.close();
   	    } catch (FileNotFoundException e) {
   	      System.out.println("An error occurred.");
   	      e.printStackTrace();
   	    }
   }

    public static void main(String args[]) { 
        readFromFile("velike.txt");
        final long pocetnoVrijeme = System.currentTimeMillis();
        int result = vrijednostPokupljenog(maxWeight, weights, values, n);
        final long zavrsnoVrijeme = System.currentTimeMillis();
        System.out.println("Vrijednost pokupljenog je: "+result + ",vrijeme izvrsavanja je: "+(zavrsnoVrijeme-pocetnoVrijeme)); 
    } 
} 