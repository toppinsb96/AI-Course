# search.py
# ---------
# Licensing Information:  You are free to use or extend these projects for
# educational purposes provided that (1) you do not distribute or publish
# solutions, (2) you retain this notice, and (3) you provide clear
# attribution to UC Berkeley, including a link to
# http://inst.eecs.berkeley.edu/~cs188/pacman/pacman.html
#
# Attribution Information: The Pacman AI projects were developed at UC Berkeley.
# The core projects and autograders were primarily created by John DeNero
# (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# Student side autograding was added by Brad Miller, Nick Hay, and
# Pieter Abbeel (pabbeel@cs.berkeley.edu).


"""
In search.py, you will implement generic search algorithms which are called by
Pacman agents (in searchAgents.py).
"""

import util

class SearchProblem:
    """
    This class outlines the structure of a search pro blem, but doesn't implement
    any of the methods (in object-oriented terminology: an abstract class).

    You do not need to change anything in this class, ever.
    """

    def getStartState(self):
        """
        Returns the start state for the search problem.
        """
        util.raiseNotDefined()

    def isGoalState(self, state):
        """
          state: Search state

        Returns True if and only if the state is a valid goal state.
        """
        util.raiseNotDefined()

    def getSuccessors(self, state):
        """
          state: Search state

        For a given state, this should return a list of triples, (successor,
        action, stepCost), where 'successor' is a successor to the current
        state, 'action' is the action required to get there, and 'stepCost' is
        the incremental cost of expanding to that successor.
        """
        util.raiseNotDefined()

    def getCostOfActions(self, actions):
        """
         actions: A list of actions to take

        This method returns the total cost of a particular sequence of actions.
        The sequence must be composed of legal moves.
        """
        util.raiseNotDefined()


def tinyMazeSearch(problem):
    """
    Returns a sequence of moves that solves tinyMaze.  For any other maze, the
    sequence of moves will be incorrect, so only use this for tinyMaze.
    """
    from game import Directions
    s = Directions.SOUTH
    w = Directions.WEST
    return  [s, s, w, s, w, w, s, w]

def depthFirstSearch(problem):
    from game import Directions
    visitedStates = list()
    stk = util.Stack()

    startState = (problem.getStartState(), [])
    stk.push(startState)

    actions = list()

    while(not stk.isEmpty()):
        node = stk.pop()
        state = node[0]
        actions = node[1]

        if problem.isGoalState(state):
            return actions

        if state not in visitedStates:
            visitedStates.append(state)
            for i, j, k in problem.getSuccessors(state):
                stk.push((i, actions + [j]))

    return
    util.raiseNotDefined()


def breadthFirstSearch(problem):
    from game import Directions
    visitedStates = list()
    stk = util.Queue()

    startState = (problem.getStartState(), [])
    stk.push(startState)

    actions = list()

    while(not stk.isEmpty()):
        node = stk.pop()
        state = node[0]
        actions = node[1]

        if problem.isGoalState(state):
            return actions

        if state not in visitedStates:
            visitedStates.append(state)
            for i, j, k in problem.getSuccessors(state):
                stk.push((i, actions + [j]))

    return
    util.raiseNotDefined()

def uniformCostSearch(problem):
    from game import Directions

    q = util.PriorityQueue()
    visited = list()
    q.push((problem.getStartState(), []), 0)

    while(not q.isEmpty()):
        state, action = q.pop()
        if problem.isGoalState(state):
            return action

        if state not in visited:
            visited.append(state)
            for i, j, k in problem.getSuccessors(state):
                update = action + [j]
                q.push((i, update), problem.getCostOfActions(update))



def nullHeuristic(state, problem=None):
    """
    A heuristic function estimates the cost from the current state to the nearest
    goal in the provided SearchProblem.  This heuristic is trivial.
    """
    return 0


def aStarSearch(problem, heuristic=nullHeuristic):
    from game import Directions

    q = util.PriorityQueue()
    visited = list()
    q.push((problem.getStartState(), [], 0), heuristic(problem.getStartState(), problem))

    while(not q.isEmpty()):
        state, action, cost = q.pop()
        if problem.isGoalState(state):
            return action

        if state not in visited:
            visited.append(state)
            for i, j, k in problem.getSuccessors(state):
                update = action + [j]
                updatePriority = problem.getCostOfActions(update) + heuristic(i, problem)
                q.push((i, update, k), updatePriority)


# Abbreviations
bfs = breadthFirstSearch
dfs = depthFirstSearch
astar = aStarSearch
ucs = uniformCostSearch
