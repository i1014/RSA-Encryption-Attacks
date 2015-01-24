package com.Ian.utilities;

import java.math.BigInteger;

/**
 * This class contains utilities that are utilized by multiple factoring methods.
 * @author Ian Offerdahl
 */
public class FactorUtilities
{
    //ZERO is a static BigInteger used for comparisons.
    private static BigInteger ZERO = new BigInteger("0");

    /**
     * This function returns the largest positive remainder of b and m.
     * @param b
     * @param m
     * @return BigInteger
     */
    public static BigInteger posMod(BigInteger b, BigInteger m) {
        BigInteger answer=b.mod(m);
        if(answer.compareTo(ZERO)<0)
            return answer;
        else
            return answer.add(m);
    }

}