from collections import deque

weights = list()
values = list()
queue = deque()

def fillInputs():
    print("\n\nTHIS IS BEARDTH-FIRST SEARCH!!!!!!!!\n")
    print("Input a weight and value for an item.")
    print("When you are done enter an integer less than 0 into either weights or values")
    maxW = 40
    while(True):
        try:
            userIn = raw_input().split()
            #print(userIn)
            weights.append(int(userIn[0]))
            values.append(int(userIn[1]))
            if(int(weights[len(weights)-1]) < 0 or int(values[len(values)-1]) < 0):
                weights.pop()
                values.pop()
                break
        except ValueError:
            print("This is not a valid input (Please input an integer)")
    prePrint()
    BFS(len(weights), weights, values, maxW)

class node():
    def __init__(self):
        self.weight = weight
        self.profit = profit
        self.level = level

def prePrint():
    print("Here are the objects that BREADTH-FIRST SEARCH will choose from.")
    for i in range(len(weights)):
        print("Item #" + str(i + 1) + ":\tweight: " + str(weights[i]) + "\tvalue: " + str(values[i]))

def BFS(n, w, v, maxW):
    q = queue
    




def main():
    fillInputs()
main()
