package com.Ian.attacks;

import com.Ian.utilities.Attack;
import com.Ian.utilities.BigRational;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * @author Ian Offerdahl
 */
public class ContinuedFractions
{


    public static Attack attack(Attack in)
    {
        BigInteger n = in.n;
        BigInteger e = in.e;
        BigInteger p = in.p;
        BigInteger q = in.q;
        BigInteger phi = (p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE)));
        BigInteger d = dFromEN(e,n);

        Attack out = new Attack(n,e,p,q,phi,d);
        return out;
    }

    public static BigInteger dFromEN(BigInteger e, BigInteger n)
    {
        ArrayList<BigInteger> a = new ArrayList<BigInteger>();
        BigRational active = new BigRational(e,n);
        int counter = 0;

        while(true)
        {
            try
            {
                BigInteger coefficient = BigRational.coefficient(active);
                BigRational remainder = BigRational.remainder(active);
                a.add(coefficient);

                System.out.println(counter + "  =  " + a.get(counter));

                BigRational con = converge(a);
                System.out.println(counter + "  +  " + con);

                if (test(con,e,n))
                {
                    System.out.println("d = " + con.den);
                    return con.den;
                }


                active = remainder.reciprocal();

                counter++;
            }
            catch(Exception ex)
            {
                System.out.println(ex.getMessage());
                break;
            }
        }

        System.out.println("Done");
        return BigInteger.ZERO;
    }

    private static boolean test(BigRational con, BigInteger e, BigInteger n)
    {
        BigInteger plain = new BigInteger("7");

        if (plain.modPow(e,n).modPow(con.den,n).equals(plain))
            return true;

        return false;
    }

    public static BigRational converge(ArrayList<BigInteger> input)
    {
        BigRational ret;
        BigRational temp = new BigRational(BigInteger.ONE,input.get(input.size()-1));
        ret = temp;

        for(int i = (input.size()-2); i >= 0; i--)
        {
            BigInteger currentCoeff = input.get(i);
            BigRational currentRational = new BigRational(currentCoeff,BigInteger.ONE);

            ret = ret.plus(currentRational);
            ret = ret.reciprocal();
        }
        return ret.reciprocal();
    }

    public static Attack validate(BigInteger p, BigInteger q, BigInteger e)
    {

        BigInteger n = p.multiply(q);
        BigDecimal rootN = new BigDecimal(n);
        rootN = BigDecimal.ONE.divide(rootN.pow(4)).divide(new BigDecimal(3));
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        BigInteger d = new BigInteger("0");

        if(e.gcd(phi).equals(BigInteger.ONE))
        {
            d = e.modInverse(phi);
            if (rootN.subtract(new BigDecimal(d)).signum() == 1);
                //possible candidate
            else
                return null;
        }
        else
            return null;

        if(q.multiply(new BigInteger("2")).subtract(p).signum() == 1)
            if(p.subtract(q).signum() == 1);
                //possible candidate
            else
                return null;
        else
            return null;

        Attack attack = new Attack(n,e,p,q,phi,d);
        return attack;
    }

}
