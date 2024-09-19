
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
    int totNum;
    int totSum;
    int trials;
    int size;
    Percolation percolation;
    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        if (n<=0 || trials <=0){
            throw new IllegalArgumentException("out of bounds");
        }
        this.trials = trials;
        for (int i = 1; i <= trials; i++) {
            percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int x = StdRandom.uniform(1, n+1);
                int y = StdRandom.uniform(1, n+1);
                percolation.open(x,y);
            }
            totNum += percolation.numberOfOpenSites();
        }
        System.out.println(mean());
    }

    // sample mean of percolation threshold
    public double mean(){
        return (double) totNum / trials;
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return Math.sqrt((totSum / trials) - Math.pow(mean(), 2));
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow(){
        double marginOfError = 1.96 * stddev() / Math.sqrt(trials);
        return mean() - marginOfError;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh(){
        double marginOfError = 1.96 * stddev() / Math.sqrt(trials);
        return mean() + marginOfError;
    }

    // test client (see below)
    public static void main(String[] args){
        new PercolationStats(100,50);

    }

}