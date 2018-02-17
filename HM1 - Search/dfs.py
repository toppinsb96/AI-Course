def compareProfits(node, maxW, items):
    W = 0
    P = 0

    if node in items:
        n = node.split()
        W = int(n[1])
        P = int(n[2])

        if(maxW < W): return 0
        else: return P

    for i in range(len(node)):
        n = node[i].split()
        W += int(n[1])
        P += int(n[2])

    if(W > maxW): return 0
    else: return P
def loopAround(q, items):
    if(len(items) <= q):
        return q - len(items)
    else:
        return q

def genChild(items, q):
    iResult = list()

    for i in range(len(q)):
        index = loopAround(q[i], items)
        iResult.append(items[index])
    return iResult


def dfs(items, n, W):
    maxP = 0
    q = list()
    q.append(None)
    visited = list()
    nextNode = 0

    for amount in range(n):
        node = q.pop(0)

        for i in range(n):
            q.append(i + nextNode)
            if node not in visited:
                visited.append(node)
            nodes = genChild(items, q)
            if(compareProfits(nodes, W, items) > maxP):
                resultNode = nodes
                maxP = compareProfits(nodes, W, items)
        for i in range(n-1):
            q.pop()
        nextNode += 1

    print("resultNode: " + str(resultNode) + " Max Profit: " + str(maxP))

def main():
    #item: A Weight: 10 Value: 10
    items = list()
    n = int(raw_input())
    W = int(raw_input())
    for i in range(n):
        items.append(raw_input())
    dfs(items, n, W)

main()
