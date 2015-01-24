package com.Ian.utilities;

import java.math.BigInteger;

public class BigRational {

    public static BigRational NEG_ONE = new BigRational(-1,1);

    public BigInteger num;   // the numerator
    public BigInteger den;   // the denominator

    // create and initialize a new Rational object
    public BigRational(int numerator, int denominator) {
        // BigInteger constructor takes a string, not an int
        num = new BigInteger(numerator + "");
        den = new BigInteger(denominator + "");
        BigInteger g = num.gcd(den);
        num = num.divide(g);
        den = den.divide(g);
    }

    // create and initialize a new Rational object
    public BigRational(BigInteger numerator, BigInteger denominator) {
        BigInteger g = numerator.gcd(denominator);
        num = numerator.divide(g);
        den = denominator.divide(g);
    }

    // return string representation of (this)
    public String toString() {
        if (den.equals(BigInteger.ONE)) return num + "";
        else                            return num + "/" + den;
    }

    // return a * b
    public BigRational times(BigRational b) {
        BigRational a = this;
        BigInteger numerator   = a.num.multiply(b.num);
        BigInteger denominator = a.den.multiply(b.den);
        return new BigRational(numerator, denominator);
    }

    // return a + b
    public BigRational plus(BigRational b) {
        BigRational a = this;
        BigInteger numerator   = a.num.multiply(b.den).add(a.den.multiply(b.num));
        BigInteger denominator = a.den.multiply(b.den);
        return new BigRational(numerator, denominator);
    }

    // return 1 / a
    public BigRational reciprocal() { return new BigRational(den, num);  }

    // return a / b
    public BigRational divides(BigRational b) {
        BigRational a = this;
        return a.times(b.reciprocal());
    }

    // return 1 for 6/4
    public static BigInteger coefficient(BigRational a) throws ArithmeticException
    {
        BigRational temp = BigRational.reduce(a);
        BigInteger coefficient = temp.num.divide(temp.den);
        return coefficient;
    }

    public static BigRational reduce(BigRational a)
    {
        BigInteger gcd = (a.num.gcd(a.den));
        return new BigRational(a.num.divide(gcd),a.den.divide(gcd));
    }

    public static BigRational remainder(BigRational active)
    {
        BigInteger coefficient = BigRational.coefficient(active);
        BigRational coeff = new BigRational(coefficient,BigInteger.ONE);
        if (coefficient.equals(BigInteger.ZERO))
            return active;
        else return active.plus(coeff.times(BigRational.NEG_ONE));
    }
}