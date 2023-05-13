import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class KnapsackRecursion {
	private static int n;
	private static int maxWeight;
	private static int weights[];
	private static int values[];
    // A utility function that returns
    // maximum of two integers
    static int max(int a, int b) { return (a > b) ? a : b; }
 
    // Returns the maximum value that
    // can be put in a knapsack of
    // capacity W
    static int knapSack(int W, int wt[], int val[], int n)
    {
        // Base Case
        if (n == 0 || W == 0)
            return 0;
 
        // If weight of the nth item is
        // more than Knapsack capacity W,
        // then this item cannot be included
        // in the optimal solution
        if (wt[n - 1] > W)
            return knapSack(W, wt, val, n - 1);
 
        // Return the maximum of two cases:
        // (1) nth item included
        // (2) not included
        else
            return max(val[n - 1]
                           + knapSack(W - wt[n - 1], wt,
                                      val, n - 1),
                       knapSack(W, wt, val, n - 1));
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

    // Driver code
    public static void main(String args[])
    {
    	readFromFile("velike.txt");
        final long pocetnoVrijeme = System.currentTimeMillis();
        int result = knapSack(maxWeight, weights, values, n);
        final long zavrsnoVrijeme = System.currentTimeMillis();
        System.out.println("Vrijednost pokupljenog je: "+result + ",vrijeme izvrsavanja je: "+(zavrsnoVrijeme-pocetnoVrijeme)); 
    }
}