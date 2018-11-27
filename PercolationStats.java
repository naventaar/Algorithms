/* *****************************************************************************
 *  Name:    Alan Turing
 *  NetID:   aturing
 *  Precept: P00
 *
 *  Description:  Prints 'Hello, World' to the terminal window.
 *                By tradition, this is everyone's first program.
 *                Prof. Brian Kernighan initiated this tradition in 1974.
 *
 **************************************************************************** */

public class PercolationStats {

    private double[] _fractions;
    private int _gridSize;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0)
            throw new java.lang.IllegalArgumentException("Wrong amount of sites");

        if (trials <= 0)
            throw new java.lang.IllegalArgumentException("Wrong amount of trials");

        _fractions = new double[trials];
        _gridSize = n;

        for (int i = 0; i < trials; i++) {

            Percolation perc = new Percolation(n);

            int counter = 0;
            while (true) {
                int row = edu.princeton.cs.algs4.StdRandom.uniform(0, n);
                int col = edu.princeton.cs.algs4.StdRandom.uniform(0, n);

                if (!perc.isOpen(row + 1, col + 1)) {

                    perc.open(row + 1, col + 1);

                    boolean percolates = perc.percolates();

                    counter++;

                    if (percolates)
                        break;
                }
            }

            _fractions[i] = (1.0 * counter) / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        double mean = 0.0;
        for (int i = 0; i < _fractions.length; i++) {
            mean += _fractions[i];
        }

        return mean / _fractions.length;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {

        double mean = mean();
        double devSqr = 0.0;
        for (int i = 0; i < _fractions.length; i++) {
            double value = _fractions[i] - mean;

            devSqr += value * value;
        }

        return Math.sqrt(devSqr / (_fractions.length - 1));
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(_fractions.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(_fractions.length);
    }

    // test client (described below)
    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);
        int count = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, count);

        System.out.println(String.format("mean = |%10f|", stats.mean()));
        System.out.println(String.format("stddev = |%10f|", stats.stddev()));
        System.out.println(String.format("95 confidence interval = [%f, %f]", stats.confidenceLo(),
                                         stats.confidenceHi()));
    }
}
