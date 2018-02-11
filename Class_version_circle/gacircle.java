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
		static double checkBoundaries(double x, double y, double radius)
		{
				if(Math.hypot(x-0, y-y) < radius) {
						radius = Math.hypot(0-x, y-y);
				}
				if(Math.hypot(x-x, y-0) < radius) {
						radius = Math.hypot(x-x, 0-y);
				}
				if(Math.hypot(x-10, y-y) < radius) {
						radius = Math.hypot(10-x, y-y);
				}
				if(Math.hypot(x-x, 10-y) < radius) {
						radius = Math.hypot(x-x, 10-y);
				}
				return radius;
		}
		static boolean checkPointOutside(double x, double y)
		{
				if(x > 10 || y > 10 || x < 0 || y < 0)
						return true;
				return false;
		}
		static void eval_fitness(ArrayList<gacircle> S, gacircle[] G) {
				double x1, y1, x2, y2, ra, r, d;
				int sel = 0;

				for(gacircle i : S) {
						i.radius = 0.0;
						i.selection = 0.0;
				}

				for(gacircle i : S) {
						for(gacircle j : G) {
								x1 = i.X_location;
								y1 = i.Y_location;
								x2 = j.X_location;
								y2 = j.Y_location;
								ra = j.radius;

								if(checkPointOutside(x1, y1))
										break;

								d = Math.hypot(x1-x2, y1-y2);
								r = d - ra;

								if(d < ra) {
										i.radius = 0;
										break;
								}
								if(j == G[0])
										i.radius = checkBoundaries(x1, y1, r);
								else {
										if(i.radius > r)
												i.radius = checkBoundaries(x1, y1, r);
								}
						}
				}
				// Find selection for each element. Could be better implemented?
				for(gacircle i : S) {
						for (gacircle j : S) {
								if(i.radius > j.radius)
										i.selection += 1;
						}
				}


		}
		static void roulette(ArrayList<gacircle> S)
		{
				// Create probability roulette based on selection
				double roul = 0.0;
				for(gacircle i : S)
						roul = roul + i.selection;
				for(gacircle i : S)
						i.selection = i.selection / roul;
		}

		static int select(ArrayList<gacircle> S)
		{
				double w = 0.0;
				int index = 0;

				for(gacircle i : S)
						w += i.selection;

				double chance = new Random().nextDouble() * w;
				for(gacircle i : S) {
						chance -= i.selection;
						if(chance <= 0.0) {
								return index;
						}
						index++;
				}
				return index;
		}

		static gacircle crossover(gacircle a, gacircle b)
		{
				gacircle child = new gacircle(a.X_location, b.Y_location, 0, 0);
				return child;
		}
		static gacircle mutate(gacircle a)
		{
				double xMut = new Random().nextDouble();
				double yMut = new Random().nextDouble();
				int s1 = new Random().nextInt(2);
				int s2 = new Random().nextInt(2);

				if(xMut < 0.3) {
						if(s1 > 0)
								a.X_location += new Random().nextDouble();
						else
								a.X_location -= new Random().nextDouble();
				}
				if(yMut < 0.3) {
						if(s2 > 0)
								a.Y_location += new Random().nextDouble();
						else
								a.Y_location -= new Random().nextDouble();
				}

				return a;
		}
		static int answersofar(ArrayList<gacircle> S, gacircle[] G)
		{
				double r = 0.0;
				int ans = -1;
				int i = 0;

				for(gacircle c : S) {
						if(c.radius > r) {
								ans = i;
								r = c.radius;
						}
						i++;
				}
				return ans;
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

    	while(count < 1500)  { //maximum iteration is set to 1000
  		    //evaluate the fitness of the population in S
  		  	eval_fitness(S, G);
  		    //create the roullete wheel for the population in S
  		  	roulette(S);
  		    //select the father and the mother (parents)
  		  	int f = select(S);
					int m = select(S);

  		  	Random prob = new Random();
  		  	int luck = prob.nextInt(100);
  		  	if(luck < 80) //probability of crossover is set to 0.8
  				{
  				  	gacircle child1 = crossover(S.get(f), S.get(m)); //offspring 1
  				  	gacircle child2 = crossover(S.get(m), S.get(f)); //offsring 2
  				  	child1 = mutate(child1); //mutate the offspring 1
  				  	child2 = mutate(child2); //mutate the offspring 2
  				  	//Add the offspring to the population
  				  	S.add(child1);
  				  	S.add(child2);
							//System.out.println(S.size());
  				  	current_generation++;

  				    //re-evaluate the fitness of the population again
  				    eval_fitness(S,G);
  				    //re-set the roulette wheel again
  				    roulette(S);
  		  	}
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
  		    System.out.println("answer's generation: " + generation + ", total generation: " + current_generation);
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
		//splat object for drawing circle
  	Splat sp1 = new Splat();
    //evaluating the fitness of the population
  	eval_fitness(pop,G);
    //create the slots for roullete wheel selection
  	roulette(pop);
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
