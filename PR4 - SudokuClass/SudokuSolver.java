import java.util.*;

class Variable
{
    String name;
    ArrayList<Integer> domain;

    public Variable(String variable)
    {
        String name = variable;
        domain = new ArrayList<Integer>();
        if(name.equals("_")) { initializeDomain(); }
        else { domain.add(Integer.parseInt(name)); }
    }
    public void initializeDomain()
    {
        for(int i = 1; i <= 9; i++) { domain.add(i); }
    }
    public Boolean remove(int value)
    {
        Boolean remove = false;
        for(int i = 0; i < domain.size(); i++)
        {
            if(domain.get(i) == value)
            {
                domain.remove(i);
                return true;
            }
        }
        return remove;
    }
}
class Csp
{
    ArrayList<Variable> vars;
    String[] state;
    public Csp(String[] state)
    {
        this.state = state;
        vars = new ArrayList<Variable>();
        createVariables();
    }

    // TODO:Compare Variable to Variable
    public Boolean constraint(String a, String b)
    {
        return a != b;
    }

    public void createVariables()
    {
        for(int i = 0; i < 81; i++)
        {
            Variable v = new Variable(state[i]);
            vars.add(v);
        }
    }

    public void updateState()
    {
        int i = 0;
        for(Variable v : vars)
        {
            if(v.domain.size() == 1)
            {
                state[i] = "" + v.domain.get(0);
            }
            else
            {
                state[i] = "_";
            }
            i++;
        }
    }

}
class Arc
{
    int a;
    int[] b;
    public Arc(int a, int[] b)
    {
        this.a = a;
        this.b = b;
    }
}
//==============================================================================
//              Arc Consistency
//==============================================================================
public class SudokuSolver
{
    /*
    Indexes for states

    00  01  02      03  04  05      06  07  08
    09  10  11      12  13  14      15  16  17
    18  19  20      21  22  23      24  25  26

    27  28  29      30  31  32      33  34  35
    36  37  38      39  40  41      42  43  44
    45  46  47      48  49  50      51  52  53

    54  55  56      57  58  59      60  61  62
    63  64  65      66  67  68      69  70  71
    72  73  74      75  76  77      78  79  80
    */
    // The 9 quadrants
    int[] Q1 = {0,1,2,9,10,11,18,19,20};
    int[] Q2 = {3,4,5,12,13,14,21,22,23};
    int[] Q3 = {6,7,8,15,16,17,24,25,26};
    int[] Q4 = {27,28,29,36,37,38,45,46,47};
    int[] Q5 = {30,31,32,39,40,41,48,49,50};
    int[] Q6 = {33,34,35,42,43,44,51,52,53};
    int[] Q7 = {54,55,56,63,64,65,72,73,74};
    int[] Q8 = {57,58,59,66,67,68,75,76,77};
    int[] Q9 = {60,61,62,69,70,71,78,79,80};
    // The 9 Columns
    int[] C1 = {0,9,18,27,36,45,54,63,72};
    int[] C2 = {1,10,19,28,37,46,55,64,73};
    int[] C3 = {2,11,20,29,38,47,56,65,74};
    int[] C4 = {3,12,21,30,39,48,57,66,75};
    int[] C5 = {4,13,22,31,40,49,58,67,76};
    int[] C6 = {5,14,23,32,41,50,59,68,77};
    int[] C7 = {6,15,24,33,42,51,60,69,78};
    int[] C8 = {7,16,25,34,43,52,61,70,79};
    int[] C9 = {8,17,26,35,44,53,62,71,80};
    // The 9 Rows
    int[] R1 = {0,1,2,3,4,5,6,7,8};
    int[] R2 = {9,10,11,12,13,14,15,16,17};
    int[] R3 = {18,19,20,21,22,23,24,25,26};
    int[] R4 = {27,28,29,30,31,32,33,34,35};
    int[] R5 = {36,37,38,39,40,41,42,43,44};
    int[] R6 = {45,46,47,48,49,50,51,52,53};
    int[] R7 = {54,55,56,57,58,59,60,61,62};
    int[] R8 = {63,64,65,66,67,68,69,70,71};
    int[] R9 = {72,73,74,75,76,77,78,79,80};
    public ArrayList<Arc> createArcQueue()
    {
        ArrayList<Arc> arcQ = new ArrayList<Arc>();

        for(int i = 0; i < 81; i++)
        {
            Arc r = new Arc(i, findRow(i));
            Arc c = new Arc(i, findCol(i));
            Arc q = new Arc(i, findQuad(i));

            arcQ.add(r);
            arcQ.add(c);
            arcQ.add(q);
        }
        return arcQ;
    }
    public int[] findRow(int i)
    {
        int[] fail = new int[9];
        if(checkArray(R1, i) > -1) return R1;
        if(checkArray(R2, i) > -1) return R2;
        if(checkArray(R3, i) > -1) return R3;
        if(checkArray(R4, i) > -1) return R4;
        if(checkArray(R5, i) > -1) return R5;
        if(checkArray(R6, i) > -1) return R6;
        if(checkArray(R7, i) > -1) return R7;
        if(checkArray(R8, i) > -1) return R8;
        if(checkArray(R9, i) > -1) return R9;
        return fail;
    }
    public int[] findCol(int i)
    {
        int[] fail = new int[9];
        if(checkArray(C1, i) > -1) return C1;
        if(checkArray(C2, i) > -1) return C2;
        if(checkArray(C3, i) > -1) return C3;
        if(checkArray(C4, i) > -1) return C4;
        if(checkArray(C5, i) > -1) return C5;
        if(checkArray(C6, i) > -1) return C6;
        if(checkArray(C7, i) > -1) return C7;
        if(checkArray(C8, i) > -1) return C8;
        if(checkArray(C9, i) > -1) return C9;
        return fail;
    }
    public int[] findQuad(int i)
    {
        int[] fail = new int[9];
        if(checkArray(Q1, i) > -1) return Q1;
        if(checkArray(Q2, i) > -1) return Q2;
        if(checkArray(Q3, i) > -1) return Q3;
        if(checkArray(Q4, i) > -1) return Q4;
        if(checkArray(Q5, i) > -1) return Q5;
        if(checkArray(Q6, i) > -1) return Q6;
        if(checkArray(Q7, i) > -1) return Q7;
        if(checkArray(Q8, i) > -1) return Q8;
        if(checkArray(Q9, i) > -1) return Q9;
        return fail;
    }
    public int checkArray(int[] a, int index)
    {
        for(int i : a)
        {
            if(i == index)
                return i;
        }
        return -1;
    }
    public void arc(Csp c)
    {
        ArrayList<Arc> arcQ = createArcQueue();

        while(!arcQ.isEmpty())
        {
            Arc node = arcQ.remove(0);

            if(removeValues(node, c))
            {
                for(int x : node.b)
                {
                    int[] neighbor = {node.a};
                    Arc n = new Arc(x, neighbor);
                    arcQ.add(n);
                }
            }
        }

        //printVariableDomains(c);
    }
    public Boolean removeValues(Arc node, Csp c)
    {
        Boolean remove = false;
        //System.out.println("H");
        if(c.vars.get(node.a).domain.size() == 1)
        {
            return false;
        }
        for(int i : node.b)
        {
            if(c.vars.get(i).domain.size() == 1)
            {
                if(i == node.a)
                {
                    continue;
                }
                if(c.vars.get(node.a).remove(c.vars.get(i).domain.get(0)))
                {
                    remove = true;
                }
            }
        }
        return remove;
    }

    public void printVariableDomains(Csp c)
    {
        int index = 0;
        for(Variable i : c.vars)
        {
            System.out.print("#" + index + " ");
            for(int j : i.domain)
            {
                System.out.print(""+ j + " ");
            }
            System.out.println();
            index++;
        }
    }
//==============================================================================
//                  DFS
//==============================================================================
    public String[] backtrackingSearch(Csp c)
    {
        c.updateState();
        printVariableDomains(c);
        return recursiveBacktracking(c.state, c);
    }
    public String[] recursiveBacktracking(String[] state, Csp c)
    {
        String[] assignment = new String[81];
        System.arraycopy(state, 0, assignment, 0, 81);

        String[] fail = {"FAILURE!"};
        if(checkAssignment(assignment, 1))
            return assignment;

        int v = findUnassignedValue(assignment);
        //printState(assignment);
        for(int value : c.vars.get(v).domain)
        {
            assignment[v] = "" + value;
            if(checkAssignment(assignment,2))
            {
                String[] result = recursiveBacktracking(assignment, c);
                if(!result[0].equals("FAILURE!"))
                {
                    return result;
                }
            }
        }
        return fail;
    }
    public Boolean checkAssignment(String[] assignment, int d)
    {
        int i = 0;
        for(String s : assignment)
        {
            if(s.equals("_") && d == 1)
                return false;

            for(int r : findRow(i))
            {
                if(i == r){}
                else if(s.equals(assignment[r]) && !assignment[r].equals("_"))
                {
                    return false;
                }

            }
            for(int c : findCol(i))
            {
                if(i == c){}
                else if(s.equals(assignment[c]) && !assignment[c].equals("_"))
                {
                    return false;
                }

            }
            for(int q : findQuad(i))
            {
                if(i == q){}
                else if(s.equals(assignment[q]) && !assignment[q].equals("_"))
                {
                    return false;
                }
            }
            i++;
        }

        return true;
    }
    public int findUnassignedValue(String[] s)
    {
        for(int i = 0; i<81; i++)
        {
            if(s[i].equals("_"))
                return i;
        }
        // FAIL condition
        return -1;
    }
    public void printState(String[] state)
    {
        for(String s : state)
        {
            System.out.print(s);
        }
        System.out.println();
    }


    public String solve(String State)
    {
        System.out.println(State);
        String[] S = State.split("");

        Csp c = new Csp(S);
        arc(c);
        String[] s = backtrackingSearch(c);
        String result = "";
        for(String var : s)
        {
            result += var;
        }


        return result;
    }
}
/*
Indexes for states

00  01  02      03  04  05      06  07  08
09  10  11      12  13  14      15  16  17
18  19  20      21  22  23      24  25  26

27  28  29      30  31  32      33  34  35
36  37  38      39  40  41      42  43  44
45  46  47      48  49  50      51  52  53

54  55  56      57  58  59      60  61  62
63  64  65      66  67  68      69  70  71
72  73  74      75  76  77      78  79  80
*/
/*


6______4___5__2__7729_____3_9__4___1____6____4___8__7_3_____1652__4__8___5______4

2_3_____7_4__72_5___13__2___3___6__4_9__3__2_8__5___7___2__37___6_79__1_4_____5_9

5_3_1_8__1____5____7___321___7____5_2_19_76_4_3____7___294___6____3____7__5_7_4_9

*/
