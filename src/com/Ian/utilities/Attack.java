package com.Ian.utilities;

import java.math.BigInteger;

/**
 * This object is used to take information to and from each attack method.
 * These are read only objects.
 * @author Ian Offerdahl
 */
public class Attack
{
    public final BigInteger n;
    public final BigInteger e;

    public final BigInteger p;
    public final BigInteger q;
    public final BigInteger phi;

    public final BigInteger d;

    /**
     * Constructor that takes in all possible parameters.
     * @param n
     * @param e
     * @param p
     * @param q
     * @param phi
     * @param d
     */
    public Attack(BigInteger n, BigInteger e, BigInteger p, BigInteger q, BigInteger phi, BigInteger d)
    {
        this.n = n;
        this.e = e;
        this.p = p;
        this.q = q;
        this.phi = phi;
        this.d = d;
    }

    /**
     * Constructor that only takes in n and e. Used for input.
     * @param n
     * @param e
     */
    public Attack(BigInteger n, BigInteger e)
    {
        this.n = n;
        this.e = e;
        this.p = new BigInteger("0");
        this.q = new BigInteger("0");
        this.phi = new BigInteger("0");
        this.d = new BigInteger("0");
    }

    /**
     * Zero variable constructor.
     */
    public Attack()
    {
        this.n = new BigInteger("0");
        this.e = new BigInteger("0");
        this.p = new BigInteger("0");
        this.q = new BigInteger("0");
        this.phi = new BigInteger("0");
        this.d = new BigInteger("0");
    }
}
