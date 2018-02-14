import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Main
{
    public static void main(String[] args) throws FileNotFoundException
    {
        Genetic stockMarket = new Genetic("HistoricalData/F.csv","HistoricalData/F.csv","HistoricalData/F.csv","HistoricalData/F.csv","HistoricalData/F.csv","2017-01-04");
        stockMarket.startTrading();
    }
}


/*
Company ford = new Company("HistoricalData/F.csv");
Individual rule = new Individual("s400&e010&m010");
boolean didWeBuy = rule.checkbuy(ford.getClosings(), ford.getDates(), "2017-01-04");

if(didWeBuy == true)
{
    ford.buy(ford.getDateClosing("2018-01-08"));
}
else
{
    ford.sell(ford.getDateClosing("2018-01-08"));
}

System.out.println(ford.getMoney());
*/
