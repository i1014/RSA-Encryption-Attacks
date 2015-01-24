package com.Ian.attacks;

import com.Ian.utilities.Attack;
import com.Ian.utilities.FactorUtilities;

import java.math.BigInteger;
import java.util.Random;

/**
 * @author Ian Offerdahl
 */
public class Pollard
{
    // The variable ONE is used for comparisons and simple math.
    private static BigInteger ONE = new BigInteger("1");

    /**
     * This method carries out the pollard attack rather than just factoring.
     * @param in
     * @return
     */
    public static Attack attack(Attack in)
    {
        BigInteger p = pMinusOneFactorization(in.n);
        BigInteger q = in.n.divide(p);

        BigInteger phi = (p.subtract(ONE).multiply(q.subtract(ONE)));

        BigInteger d = in.e.modInverse(phi);

        Attack out = new Attack(in.n,in.e,p,q,phi,d);
        return out;
    }

    /**
     * This method uses Pollard's p-1 factorization to factor n;
     * @param n
     * @return BigInteger
     * @throws IllegalArgumentException
     */
    public static BigInteger pMinusOneFactorization(BigInteger n) throws IllegalArgumentException
    {
        Random rand = new Random();
        BigInteger B = ONE;
        BigInteger residue = FactorUtilities.posMod(BigInteger.valueOf(rand.nextInt()), n);
        BigInteger temp = residue.subtract(ONE);
        BigInteger gcd = temp.gcd(n);

        while (true)
        {
            while (gcd.equals(ONE))
            {
                B = B.add(ONE);
                residue = residue.modPow(B,n);
                temp = residue.subtract(ONE);
                gcd = temp.gcd(n);
            }
            if (gcd.equals(n))
            {
                B = BigInteger.valueOf(1);
                residue = FactorUtilities.posMod(BigInteger.valueOf(rand.nextInt()), n);
                temp=residue.subtract(ONE);
                gcd=temp.gcd(n);
            } else return gcd;
        }
    }


}

