import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Problem Statement: Factoring Large Binomial Coefficients The binomial
 * coefficient (n choose m) for two non-negative numbers n >= m >= 0 is defined
 * as (n choose m) = n!/(m!( n-m)!). Binomial coefficients can be quite large,
 * even for small n, but thanks to their structure can be factored with relative
 * ease. In this problem, you're asked to factor a binomial coefficient given n
 * and m. For instance, if n = 10 and m = 3, we have (10 choose 3) = 120 = 2^3 *
 * 3 * 5 which you should output as 2 2 2 3 5.
 * 
 * @author Tucker Noia - 12/15/2012
 * 
 */
public class Binom {

    static boolean[] primes;

    /**
     * Begin by creating the boolean array of prime numbers up until 1000000.
     * 
     * @param args
     *            - not used.
     */
    public static void main(String[] args) {
        Binom b = new Binom();
        primes = sieve(1000000);
        b.start();
    }

    /**
     * Scan standard input, assuming that the input is formatted according to
     * the problem statement: From standard input, read lines with two integers
     * n and m until a terminating line that contains only zeros. It is
     * guaranteed that 0 <= m <= n and 0 < n <= 1000000.
     *  10 3
     *  20 10 
     *  20 19 
     *  20 0
     *  1009 1008 
     *  210 13 
     *  0 0
     * From there, then output the prime factorization of
     * the binomial coefficient according to the specified output: For each
     * input pair, output on a single line the prime factorization of (n choose
     * m) in sorted order, or output 1 if (n choose m) = 1.
     *  2 2 2 3 5
     *  2 2 11 13 17 19
     *  2 2 5
     *  1
     *  1009 
     *  2 2 2 3 3 5 5 7 11 17 19 23 29 41 67 101 103 199
     */
    void start() {
        Scanner in = new Scanner(System.in);
        OUTER: while (true) {
            int i = in.nextInt(), j = in.nextInt();

            if (i == j && j == 0)
                break;

            if (i == 0 || j == 0 || j == i) {
                System.out.println("1");
                continue OUTER;
            }

            INNER: for (int c = 2; c <= i; c++) {
                // Ignore non-prime numbers.
                if (!primes[c])
                    continue INNER;
                    
                int over = overflow(baseRep(j, c), baseRep((i - j), c), c);
                // Print out the base according to how many overflows were 
                // calculated in the addition.
                for (int m = 0; m < over; m++) {
                    System.out.print(c + " ");
                }
            }
            System.out.println();
        }
        in.close();
    }

    /**
     * @param num
     *            - the number in which the base-ary representation will be
     *            found.
     * @param base
     *            - the base in which the number will be represented
     * @return An ArrayList<Integer> which contains the base-ary representation
     *         of the input.
     */
    static ArrayList<Integer> baseRep(int num, int base) {
        ArrayList<Integer> rep = new ArrayList<Integer>();
        while (num > 0) {
            rep.add(num % base);
            num /= base;
        }
        return rep;
    }

    /**
     * Kummer's theorem: Given two integers n >= m >= 0 and a prime number p,
     * the maximum integer k such that p^k divides the binomial coefficient (n
     * choose m) is equal to the number of carries when m is added to n - m in
     * base p. For instance, consider n = 10, m = 3, and p = 2. In base 2, we
     * have m = 3 = 11_2 and n - m = 10 - 3 = 7 = 111_2. Adding 11_2 and 111_2:
     *   11
     *  111
     * ----
     * 1010 
     * requires three carries. Similarly, in base 3, adding 3 =
     * 10_3 to 7 = 21_3 yields 10_3 + 21_3 = 101_3 with one carry.
     * 
     * @param first
     *            - ArrayList<Integer> which should store the base-ary
     *            representation of an integer.
     * @param second
     *            - ArrayList<Integer> which should store the base-ary
     *            representation of an integer.
     * @param base
     *            - the base in which the ArrayList<Integer> are represented
     * @return the number of times that the addition overflows when first is
     *         added to second.
     */
    static int overflow(ArrayList<Integer> first, ArrayList<Integer> second,
            int base) {
        int count = 0;
        int overflow = 0;
        int size = Math.min(first.size(), second.size());
        int i;
        // Calculate the overflow up until the size of the smallest ArrayList.
        for (i = 0; i < size; i++) {
            int a = first.get(i), b = second.get(i);
            a += (b + overflow);
            if (a >= base) {
                count++;
                overflow = 1;
            } else {
                overflow = 0;
            }
        }

        // If the size of the first array is larger than the second, check
        // to see if there will be any overflow past the length of second.
        if (first.size() > second.size()) {
            for (; i < first.size(); i++) {
                int a = first.get(i);
                a += overflow;
                if (a >= base) {
                    count++;
                    overflow = 1;
                } else {
                    overflow = 0;
                }

            }
        }

        // Check past the size of first if second's size is greater than
        // first's
        else if (first.size() < second.size()) {
            for (; i < second.size(); i++) {
                int a = second.get(i);
                a += overflow;
                if (a >= base) {
                    count++;
                    overflow = 1;
                } else {
                    overflow = 0;
                }
            }
        }
        return count;
    }

    /**
     * A sieve which sifts out numbers to where the only true elements in the
     * boolean array are prime numbers.
     * 
     * @param n
     *            - Highest number that will be checked to be prime.
     * @return An array which specifies if the position in the array is prime.
     */
    public static boolean[] sieve(int n) {
        boolean[] isPrime = new boolean[n + 1];

        Arrays.fill(isPrime, true);
        isPrime[0] = false;
        isPrime[1] = false;
        for (int i = 2; i * i <= n; i++) {
            // Continue if already checked.
            if (!isPrime[i])
                continue;
            // Check all possible multiples of the number up until n. All
            // multiples are guaranteed to be non-prime.
            for (int multiples = i * i; multiples <= n; multiples += i)
                isPrime[multiples] = false;
        }
        return isPrime;
    }

}
