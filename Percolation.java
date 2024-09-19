import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.HashSet;
import java.util.Set;

public class Percolation {
    private final int gridSize;
    private final int rowColSize;
    private final WeightedQuickUnionUF quickFind;
    private final Set<Integer> openedSites = new HashSet<>();

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Grid size must be greater than 0");
        }
        this.rowColSize = n;
        this.gridSize = n * n;
        this.quickFind = new WeightedQuickUnionUF(gridSize);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        proveIndex(row, col);
        int index = getIndex(row, col);
        if (openedSites.contains(index)) return;

        openedSites.add(index);

        // Connect to adjacent open sites
        if (row > 0 && isOpen(row - 1, col)) {
            quickFind.union(index, getIndex(row - 1, col));
        }
        if (row < rowColSize - 1 && isOpen(row + 1, col)) {
            quickFind.union(index, getIndex(row + 1, col));
        }
        if (col > 0 && isOpen(row, col - 1)) {
            quickFind.union(index, getIndex(row, col - 1));
        }
        if (col < rowColSize - 1 && isOpen(row, col + 1)) {
            quickFind.union(index, getIndex(row, col + 1));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        proveIndex(row, col);
        return openedSites.contains(getIndex(row, col));
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        proveIndex(row, col);
        int index = getIndex(row, col);
        for (int openSite : openedSites) {
            if (openSite < rowColSize && quickFind.connected(index, openSite)) {
                return true;
            }
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openedSites.size();
    }

    // does the system percolate?
    public boolean percolates() {
        for (int openSite : openedSites) {
            if (openSite < rowColSize) {
                for (int bottomSite : openedSites) {
                    if (bottomSite >= (rowColSize * (rowColSize - 1)) && quickFind.connected(openSite, bottomSite)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // unit testing (required)
    public static void main(String[] args) {
    }

    private int getIndex(int row, int col) {
        return row * rowColSize + col;
    }

    private void proveIndex(int row, int col) {
        if (row < 0 || row >= rowColSize || col < 0 || col >= rowColSize) {
            throw new IllegalArgumentException("out of bounds");
        }
    }
}
