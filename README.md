Factoring-Large-Binomial-Coefficients
=====================================

Problem Statement
-----------------
Factoring Large Binomial Coefficients The binomial coefficient (n choose m)
for two non-negative numbers n >= m >= 0is defined as 
(n choose m) = n!/(m!(n - m)!).Binomial coefficients can be quite large,
even for small n, but thanks to their structure can be factored with relative
ease. In this problem, you're asked to factor a binomial coefficient given
n and m. For instance, if n = 10 and m = 3, we have 
(10 choose 3) = 120 = 2^3 * 3 * 5 which you should output as 2 2 2 3 5.

Input
-----
From standard input, read lines with two integers n and m until a terminating
line that contains only zeros. It is guaranteed that 0 <= m <= n and
0 < n <= 1000000.
10 3
20 10
20 19
20 0
1009 1008
210 13
0 0

Output
------
For each input pair, output on a single line the prime factorization of
(n choose m) in sorted order,or output 1 if (n choose m) = 1.
2 2 2 3 5
2 2 11 13 17 19
2 2 5
1
1009
2 2 2 3 3 5 5 7 11 17 19 23 29 41 67 101 103 199