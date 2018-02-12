public class individual
{
    String rule;
    int s, m, e;

    public individual()
    {
        rule = "";
        s = m = e = 0;
    }
    public individual(String rule)
    {
        rule = rule;
        s = m = e = 0;
    }
    public individual(String rule, int s, int m, int e)
    {
        rule = rule;
        this.s = s;
        this.m = m;
        this.e = e;
    }
    public String getRule()
    {
        return rule;
    }
    public String getS()
    {
        return s;
    }
    public String getM()
    {
        return m;
    }
    public String getE()
    {
        return e;
    }
}
