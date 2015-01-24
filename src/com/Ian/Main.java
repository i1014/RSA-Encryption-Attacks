package com.Ian;

import com.Ian.attacks.ContinuedFractions;
import com.Ian.attacks.Pollard;
import com.Ian.utilities.Attack;

import java.io.*;
import java.math.BigInteger;
import java.util.Scanner;

/**
 * @author Ian Offerdahl
 * @version 1.0
 */
public class Main
{
    //These are the supported attack types:
    private static final String POLLARD = "pollard";
    private static final String FRACTION = "fraction";

    //Default attack method:
    private static final String DEFAULT = POLLARD;

    /**
     * Main function used to start the program, also handles input.
     * @param args
     */
    public static void main(String[] args)
    {
        int length = args.length;
        switch (length)
        {
            case 0: displayHelp();
                manualUserInput();
                break;
            case 1: if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("man") || args[0].equals("?"))
                displayHelp();
            else
                fileEntry(args);
                break;
            case 2: defaultMethodEntry(args);
                break;
            case 3: selectMethodEntry(args);
        }
    }

    public static void displayHelp()
    {
        System.out.println("Welcome to Ian Offerdahl's FactorLand");
        System.out.println("Currently supported attack methods");
        System.out.println("  1) (pollard)  Pollard's p-1 attack {Default}");
        System.out.println("  2) (fraction) Continued Fraction attack to find private key");
        System.out.println("Methods of entry");
        System.out.println("  1) Command line arguments using default attack method (ie: java -jar Factor.jar {N} {e})");
        System.out.println("  2) Command line arguments using specified attack method (ie: java -jar Factor.jar {Method} {N} {e})");
        System.out.println("  3) Programatic entry (ie: java -jar Factor.jar) and you will be prompted to enter N and e");
        System.out.println("  4) File entry (ie: java -jar Factor.jar {path to file}");
        System.out.println("File input format is essentially a list of space delimited input commands contained in a file. You could accomplish the same thing with a shell script.");
        System.out.println("{Method} {N} {e}");
    }

    public static void manualUserInput()
    {
        BigInteger N;
        BigInteger e;
        BigInteger p;
        BigInteger q;

        Scanner in = new Scanner(System.in);
        System.out.println("Method of attack");
        String method = in.next();
        if (method.equals(FRACTION))
        {
            System.out.println("P : ");
            p = new BigInteger(in.next());
            System.out.println("Q : ");
            q = new BigInteger(in.next());
            N = p.multiply(q);
        }
        else
        {
            System.out.println("N : ");
            N = new BigInteger(in.next());
        }
        System.out.println("e : ");
        e = new BigInteger(in.next());

        String[] input = {method,""+N,""+e};
        selectMethodEntry(input);
    }

    public static void fileEntry(String[] args)
    {
        //Assume it's a file.
        FileReader file = null;
        try
        {
            file = new FileReader(args[0]);
            BufferedReader reader = new BufferedReader(file);
            String line = "";
            while ((line = reader.readLine()) != null)
            {
                String[] input = line.split(" ");
                selectMethodEntry(input);
            }
            if (file != null)
            {
                file.close();
            }
        }
        catch (Exception ex)
        {
            System.out.println("File Error. Now Exiting.");
            return;
        }
    }

    public static void defaultMethodEntry(String[] args)
    {
        String[] input = {DEFAULT,args[0],args[1]};
        selectMethodEntry(input);
    }

    public static void selectMethodEntry(String[] args)
    {
        Attack attack;
        BigInteger N;
        BigInteger e;
        BigInteger p;
        BigInteger q;

        if(args[0].equals(POLLARD))
        {
            N = new BigInteger(args[1]);
            e = new BigInteger(args[2]);

            attack = new Attack(N,e);

            attack = Pollard.attack(attack);
        }
        else
        if(args[0].equals(FRACTION))
        {
            p = new BigInteger(args[1]);
            q = new BigInteger(args[2]);
            e = new BigInteger(args[2]);

            attack = ContinuedFractions.validate(p, q, e);
            if(attack != null)
                attack = ContinuedFractions.attack(attack);
            else
            {
                System.out.println("INVALID p,q,e. Now Exiting");
                return;
            }
        }
        else
        {
            System.out.println("INVALID METHOD. Now Exiting");
            return;
        }

        display(attack);
    }

    public static void display(Attack attack)
    {
        System.out.println("p   = " + attack.p);
        System.out.println("q   = " + attack.q);
        System.out.println("phi = " + attack.phi);
        System.out.println("d   = " + attack.d);
    }

}
