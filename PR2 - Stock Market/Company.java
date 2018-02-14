import java.io.*;
import java.util.*;
import java.lang.*;

public class Company
{
    ArrayList<String> closingData;
    ArrayList<String> datesData;
    CSVReader company;
    int stocksOwned;
    double money;
    double gainAccount;


    public Company(String path) throws FileNotFoundException
    {
        company = new CSVReader(path);
        closingData = company.getClosings();
        datesData = company.getDates();
        stocksOwned = 0;

        // Company Starts at $20,000
        money = 20000.00;
        gainAccount = 0.0;
    }
    public void trade(Boolean b, int day)
    {
        double preMoney = money;
        double preGain = gainAccount;
        if(b == true)
        {
            buy(day);
        }
        else
        {
            sell(day);
        }

    }
    public void resetCost()
    {
        money = 20000.00;
    }
    public void buy(int day)
    {
        double amount = Double.parseDouble(closingData.get(day));
        stocksOwned++;
        money -= amount;
        updateAccounts();
    }
    public void sell(int day)
    {
        double amount = Double.parseDouble(closingData.get(day));
        while(stocksOwned > 0)
        {
            money += amount;
            stocksOwned--;
        }
        updateAccounts();
    }
    public double getMoney()
    {
        return money;
    }
    public ArrayList<String> getClosings()
    {
        return closingData;
    }
    public ArrayList<String> getDates()
    {
        return datesData;
    }
    public double getDateClosing(int day)
    {
        return Double.parseDouble(closingData.get(day));
    }

    public void updateAccounts()
    {

        if(money > 20000.00)
        {
            gainAccount = money - 20000.00;
            money = 20000.00;
        }
        else if(money <= 20000.00 && gainAccount > 0)
        {
            money += gainAccount;
            if(money > 20000.00)
            {
                gainAccount = money - 20000.00;
            }
        }
    }
}