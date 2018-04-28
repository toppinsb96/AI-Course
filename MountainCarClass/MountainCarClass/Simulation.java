import java.awt.*;
import javax.swing.*;
import java.util.*;
class State
{
    int vel;
    int pos;

    public State(mcar c)
    {
        this.vel = (int)(100 * c.curr_vel());
        this.pos = (int)(100 * c.curr_pos());
    }

    // Helper functions to speed up mapping and equaling inside state creation
    // Modified versions of overrides from geeksforgeeks.org
    @Override
    public boolean equals(Object O)
    {
        if(O == this)
            return true;
        if(!(O instanceof State))
            return false;

        State s = (State)O;
        return pos == s.pos && vel == s.vel;
    }
    @Override
    public int hashCode()
    {
        // randomly selected odd number and randomly selected prime number
        int prime = 113;
        int result = 1001;
        result = prime*result + new Integer(pos).hashCode();
        result = prime*result + new Integer(vel).hashCode();
        return result;
    }
}
public class Simulation extends JPanel
{
    public static double[] generateOptions()
    {
        double[] options = new double[3];
        Random r = new Random();
        for(int i = 0; i<3; i++)
            options[i] = r.nextDouble();
        return options;
    }
    public static double[] getOptions(mcar c, Map<State, double[]> Q)
    {
        State s = new State(c);
        if(!Q.containsKey(s))
        {
            Q.put(s, generateOptions());
            return Q.get(s);
        }
        else
        {
            return Q.get(s);
        }
    }
    public static int getAction(double[] options)
    {
        int maxO = 0;
        for(int i=0; i<3;i++)
        {
            if(options[maxO] < options[i])
                maxO = i;
        }
        return maxO-1;
    }
    public static void main(String[] args)
    {
        //DO NOT CHANGE
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Montain Car Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.white);
        frame.setSize(300, 300);
        QuadraticCurve panel = new QuadraticCurve();
        frame.add(panel);
        frame.setVisible(true);
        //END OF DO NOT CHANGE

        int a, a2;
        double alpha, gamma;
        int maxEpisodes = 50000;
        int maxSteps = 1000;

        //Random ran = new Random();
        //gamma = ran.nextDouble();
        //alpha = ran.nextDouble();
        gamma = 0.5584288713093067;
        alpha = 0.9725137673705606;
        Map<State, double[]> Q = new HashMap<State, double[]>();
        for(int i = 0; i<maxEpisodes; i++)
        {
            mcar car = new mcar();
            double[] s = getOptions(car, Q);
            a = getAction(s);
            for(int j = 0; j<maxSteps; j++)
            {
                car.update_position_velocity(a);
                double r = car.reward();

                double[] s2 = getOptions(car, Q);
                a2 = getAction(s2);
                double wow = s[a+1] + alpha*(r + gamma*s2[a2+1] - s[a+1]);

                s[a+1] = wow;
                if(car.reached_goal())
                {
                    System.out.println("GOAL! at step: " + j + " on Episode: " + i + ": Return of the Mountain");
                    break;
                }
                s = s2;
                a = a2;
            }
        }

        mcar car = new mcar();
        car.set_curr_pos(0);
        car.set_curr_vel(0);
        double pos;
        double vel;
        int count = 0;

        while(!car.reached_goal())
        {
            double[] s = getOptions(car, Q);
            car.update_position_velocity(getAction(s));
            //car.print();

            pos = car.curr_pos();
            vel = car.curr_vel();
            System.out.println(pos);
            System.out.println(vel);

            panel.updateCircle(pos);

            try
            {
                Thread.sleep(50);
            }
            catch(InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }
            frame.repaint();
            count++;
        }
        System.out.println("Alpha: " + alpha + " Gamma: " + gamma);
        System.out.println("Count: "+ count);

    }
}


/*
Test Parameters for alpha and gamma


Alpha: 0.12508510622868363 Gamma: 0.369824172300117
Count: 87

Alpha: 0.9725137673705606 Gamma: 0.5584288713093067
Count: 78

Alpha: 0.33590857615027114 Gamma: 0.3719115652398116
Count: 89
*/
