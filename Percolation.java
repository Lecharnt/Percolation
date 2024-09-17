import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.ArrayList;
import java.util.List;

public class Percolation {
    int gridSize;
    int rowColSize;
    WeightedQuickUnionUF quickFind;
    List<gridObject> openedObjects = new ArrayList<>();
    List<gridObject> openedObjectsTop = new ArrayList<>();
    List<gridObject> openedObjectsBottom = new ArrayList<>();

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        rowColSize= n;
        gridSize = rowColSize*rowColSize;
        quickFind = new WeightedQuickUnionUF(gridSize);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        openedObjects.add(getIndex(row,col),new gridObject(row, col));
        //is it a corner
        if(isOpen(row,col)){
            return;
        }
        if(row == 0){
            openedObjectsTop.add(getIndex(row,col), new gridObject(row, col));
        }
        if(row == gridSize-1){}
        if(row>0 && isOpen(row-1,col)){
            quickFind.union(getIndex(row,col),getIndex(row-1,col));
        }
        if (row<rowColSize && isOpen(row+1,col)){
            quickFind.union(getIndex(row,col),getIndex(row+1,col));
        }
        if (col<rowColSize && isOpen(row,col+1)){
            quickFind.union(getIndex(row,col),getIndex(row,col+1));
        }
        if (col>0 && isOpen(row,col-1)){
            quickFind.union(getIndex(row,col),getIndex(row,col-1));
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        return openedObjects.contains(getIndex(row,col));
    }
    public int getIndex(int row, int col){
        return ((row+1) * rowColSize-(rowColSize-col+1)-1);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        for(int i = 0;i < openedObjectsTop.size(); i++){
            if(quickFind.connected(getIndex(row, col), openedObjectsTop.indexOf(openedObjectsTop.get(i) ))){
                return true;
            }
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return openedObjects.size();
    }

    // does the system percolate?
    public boolean percolates(){
        for(int i = 0;i < openedObjectsTop.size(); i++){
            for(int j = 0;j < openedObjectsBottom.size(); j++){
                if (quickFind.connected(openedObjectsTop.indexOf(openedObjectsTop.get(i)), openedObjectsBottom.indexOf(openedObjectsBottom.get(j)))){
                 return true;
                }
            }
        }
        return false;
    }

    // unit testing (required)
    public static void main(String[] args){}

}
