import java.io.*;
import java.util.*;
import java.awt.*;
import java.lang.*;

public class stock
{
    String individual;
    double s, m, e;

    public stock(String ind, double sma, double max, double ema)
    {
        individual = ind;
        s = sma;
        m = max;
        e = ema;
    }

    // ========================================================================
    //            Rule Formulas
    // ========================================================================
    public double smaRule(int n, double[] p)
    {
        double numerator = 0.0;
        for(double i : p)
            numerator += i;
        return numerator / n;
    }
    public double maxRule(int n, double[] p)
    {
        double max = 0.0;

        for(double i : p)
        {
            if(i > max)
                max = i;
        }
        return max;
    }
    public double emaRule(int n, double[] p)
    {
        double numerator = 0.0;
        double denominator = 0.0;
        double alpha = 2 / (n + 1);
        for(int i = 0; i < n; i++)
            numerator += Math.pow((1-alpha), i) * p[i];
        for(int i = 0; i < n; i++)
            denominator += Math.pow((1-alpha), i);
        return numerator / denominator;

    }
    // ========================================================================
    //            Genetic Algorithm Functions
    // ========================================================================
    public void evalFitness(ArrayList<stock> p)
    {

    }
    public void roullete()
    {

    }
    public void select()
    {

    }
    public void crossover()
    {

    }
    public void getMaxFit()
    {

    }

    // ========================================================================
    //            Run the Trading Sequence
    // ========================================================================
    public void startTrading(ArrayList<stock> p)
    {
        int count = 0;
        while (count <  200)
        {
            count++;
        }
    }


    // ========================================================================
    //     Main function for starting and setting up initial parameters
    // ========================================================================
    public static void main(String[] args)
    {
        ArrayList<stock> initialPop = new ArrayList();
    }
}
