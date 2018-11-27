import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    boolean[][] _grid;
    int _sideSize;
    int _openSitesCount = 0;
    int _lastPosition;
    WeightedQuickUnionUF _weightedQuickUnionUF;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new java.lang.IllegalArgumentException("Wrong amount of sites");

        _sideSize = n;
        _lastPosition = _sideSize * _sideSize + 1;

        _weightedQuickUnionUF = new WeightedQuickUnionUF(n * n + 2);

        _grid = new boolean[n][];
        for (int i = 0; i < n; i++) {
            _grid[i] = new boolean[n];
            for (int j = 0; j < n; j++) {
                _grid[i][j] = false;
            }
        }
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        checkArguments(row, col);

        if (isOpen(row, col))
            return;

        _openSitesCount++;
        _grid[row - 1][col - 1] = true;

        int pos1 = getPosition(row, col);

        // first row is always connected to the top
        if (row == 1)
            _weightedQuickUnionUF.union(0, pos1);

        // last row is always connected to the bottom
        if (row == _sideSize)
            _weightedQuickUnionUF.union(_lastPosition, pos1);

        // checking site above
        if (row - 1 > 0) {
            int upPos = getPosition(row - 1, col);

            if (_weightedQuickUnionUF.connected(0, upPos))
                _weightedQuickUnionUF.union(0, pos1);

            if (_weightedQuickUnionUF.connected(_lastPosition, upPos))
                _weightedQuickUnionUF.union(_lastPosition, pos1);

            if (isOpen(row - 1, col))
                _weightedQuickUnionUF.union(upPos, pos1);
        }

        // checking site below
        if (row + 1 <= _sideSize) {
            int downPos = getPosition(row + 1, col);

            if (_weightedQuickUnionUF.connected(0, downPos))
                _weightedQuickUnionUF.union(0, pos1);

            if (_weightedQuickUnionUF.connected(_lastPosition, downPos))
                _weightedQuickUnionUF.union(_lastPosition, pos1);

            if (isOpen(row + 1, col))
                _weightedQuickUnionUF.union(downPos, pos1);
        }

        // checking left site
        if (col - 1 > 0) {
            int leftPos = getPosition(row, col - 1);

            if (_weightedQuickUnionUF.connected(0, leftPos))
                _weightedQuickUnionUF.union(0, pos1);

            if (_weightedQuickUnionUF.connected(_lastPosition, leftPos))
                _weightedQuickUnionUF.union(_lastPosition, pos1);

            if (isOpen(row, col - 1))
                _weightedQuickUnionUF.union(leftPos, pos1);
        }

        // checking right site
        if (col + 1 <= _sideSize) {
            int rightPos = getPosition(row, col + 1);

            if (_weightedQuickUnionUF.connected(0, rightPos))
                _weightedQuickUnionUF.union(0, pos1);

            if (_weightedQuickUnionUF.connected(_lastPosition, rightPos))
                _weightedQuickUnionUF.union(_lastPosition, pos1);

            if (isOpen(row, col + 1))
                _weightedQuickUnionUF.union(rightPos, pos1);
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkArguments(row, col);
        return _grid[row - 1][col - 1];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        checkArguments(row, col);
        return _weightedQuickUnionUF.connected(0, (row - 1) * _sideSize + col);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return _openSitesCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return _weightedQuickUnionUF.connected(0, _lastPosition);
    }

    // test client (optional)
    public static void main(String[] args) {

    }

    private void checkArguments(int row, int col) {
        if (row < 1 || col < 1)
            throw new java.lang.IllegalArgumentException("Wrong arguments");
    }

    private int getPosition(int row, int col) {
        return (row - 1) * _sideSize + col;
    }
}
