import java.io.*;
import java.util.*;
import java.awt.*;
import java.lang.*;


public class gacircle {

		//intial parameter setup
		double X_location;
		double Y_location;
		double radius;
    double selection;//(optional) used for roullete wheel selection

    //this is used to create a new instance of gacircle******************************************************************************************
    public gacircle(double x, double y, double r, double s) {
    	X_location = x;
    	Y_location = y;
    	radius = r;
    	selection = s;

    }
    //end of gacircle creator******************************************************

    //=============================================================================
    //					Project Implementation
    //=============================================================================


		static void eval_fitness(ArrayList<gacircle> S, gacircle[] G)
		{
        double x1, x2, y1, y2, ra, d;
        for(int i = 0; i < S.size(); i++)
        {
            for(int j = 0; j < 5; j++)
            {
                x1 = S.get(i).X_location;
                y1 = S.get(i).Y_location;
                x2 = G[j].X_location;
                y2 = G[j].X_location;
                ra = G[j].radius;
                d = Math.hypot(x1-x2, y1-y2);
                double r = d - ra;
                if(r < 0)
                {
                    S.get(i).radius = 0;
                    break;
                }

                if(S.get(i).radius == 0)
                {
                    S.get(i).radius = r;
                }
                else
                {
                    if(S.get(i).radius > r)
                    {
                        S.get(i).radius = r;
                    }
                }
            }
        }
		}
		static void roulette(ArrayList<gacircle> S)
		{
				return;
		}
		static int select(ArrayList<gacircle> S)
		{
				return 0;
		}
		static gacircle crossover(gacircle a, gacircle b)
		{
				return a;
		}
		static void mutate(gacircle a)
		{

		}
		static int answersofar(ArrayList<gacircle> S, gacircle[] G)
		{
				return 0;
		}

	//=================================================================================
	//					Project Implementation
	//=================================================================================
  //an example code of how GA is run
  //Feel free to change this to do what you want.
  static int produce(ArrayList<gacircle> S, gacircle[] G, Splat pl){
    	int count = 0;
    	int current_generation = 0;
    	int generation = 0;
    	int current = -1;

    	while(count < 1000)  { //maximum iteration is set to 1000
  		    //evaluate the fitness of the population in S
  		  	eval_fitness(S, G);

  		    //create the roullete wheel for the population in S
  		  	roulette(S);
          /*
  		    //select the father and the mother (parents)
  		  	int f = select(S);
  		  	int m = select(S);

  		  	gacircle F;
  		  	gacircle M;

  		  	Random prob = new Random();
  		  	//int luck = prob.nextInt(100);
          int luck = 0;
  		  	if(luck < 80) //probability of crossover is set to 0.8
  				{
  				  	gacircle child1 = crossover(S.get(f),S.get(m)); //offspring 1
  				  	gacircle child2 = crossover(S.get(m),S.get(f)); //offsring 2
  				  	mutate(child1); //mutate the offspring 1
  				  	mutate(child2); //mutate the offspring 2
  				  	//Add the offspring to the population
  				  	S.add(child1);
  				  	S.add(child2);

  				  	current_generation++;

  				    //re-evaluate the fitness of the population again
  				    eval_fitness(S,G);
  				    //re-set the roulette wheel again
  				    roulette(S);
  		  	}

          */
  		    //get the best answer so far
  		  	int k = answersofar(S,G);

  		  	gacircle A = S.get(k);

  				//update the screen
  				pl.update(A);
  				try {
  			      Thread.sleep(20);                 //1000 milliseconds is one second.
  			  } catch(InterruptedException ex) {
  			  		Thread.currentThread().interrupt();
  			  }

  		  	if(k != -1 && k != current) {
  			  	 	current = k;
  			  	 	generation = current_generation;
  		  	}
  		    //System.out.println("answer's generation: " + generation + ", total generation: " + current_generation);
  		  	count++;
    	}
    	//return the index of the best solution at the end
    	return current;
  }
  public static void main(String[] args) {
  	Scanner scan = new Scanner(System.in);
  	double x = 0, y = 0, r = 0;

  	gacircle [] G = new gacircle[5];

  	Random  Xgen = new Random();
  	Random  Xdec = new Random();
  	Random  Ygen = new Random();
  	Random  Ydec = new Random();
  	Random  Rgen = new Random();
  	Random  Rdec = new Random();

    //create 5 random circle to within the rectangle (0,0),(0,10),(10,0), and (10,10)
  	for(int i = 0; i < 5; i++){
  		G[i] = new gacircle(Xgen.nextInt(10)+ Xdec.nextFloat(),Ygen.nextInt(10)+Ydec.nextFloat(),Rgen.nextInt(3)+Rdec.nextFloat(),0);
  	}
    //Array List of population
  	ArrayList<gacircle> pop = new ArrayList<gacircle>();

    //reading the initial population from the input file
  	while(scan.hasNext()) {
  		gacircle C = new gacircle(scan.nextDouble(),scan.nextDouble(),0,0);
  	    pop.add(C);
  	}
    //evaluating the fitness of the population
  	eval_fitness(pop,G);
    //create the slots for roullete wheel selection
  	roulette(pop);
    //splat object for drawing circle
  	Splat sp1 = new Splat();
    //create dummy gacircle
  	gacircle B = new gacircle(0,0,0,0);
    //draw the the 5 initial circle with the dummy circle
		sp1.run(G,B);

    //get the answer
    int k = produce(pop, G, sp1);

    if(k != -1) { //an answer was found
    	System.out.println("Disks set");
    	for(int i = 0; i < G.length; i++)
    		System.out.println("Disk #"+i+" : x-location:"+G[i].X_location+", y-location:"+G[i].Y_location+", radius:"+G[i].radius);

			gacircle A = pop.get(k);
			System.out.println("Solution:");
			System.out.println("x-location: "+A.X_location+",y-location: "+A.Y_location+", radius: "+A.radius);
			//Splat sp1 = new Splat();
			sp1.update(A);

		}
    else
    {//no answer was found
    		System.out.println("no answer");
    		for(int i = 0; i < pop.size(); i++)
    			  System.out.println(pop.get(i).X_location+" , " + pop.get(i).Y_location+" , " +pop.get(i).radius);
		}
  }
}
