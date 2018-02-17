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

def genChildren(node, items):
    _items = list(items)

    children = list()

    if(node == "0"):
        return _items

    if node in _items:
        _items.remove(node)
    else:
        for i in range(len(node)):
            _items.remove(node[i])

    while(len(_items) != 0):
        if node in items:
            _node = list()
            _node.append(node)
            _node.append(_items[0])
            _items.pop(0)
        else:
            _node = list(node)
            _node.append(_items[0])
            _items.pop(0)
        children.append(_node)
        _node = []

    return children



def bfs(items, n, W):
    maxP = 0
    q = list()
    visited = list()
    q.append("0")
    resultNode = list()

    while(len(q) != 0):
        node = q.pop(0)
        children = genChildren(node, items)
        if node not in visited:
            visited.append(node)
            for i in children:
                if(compareProfits(i, W, items) > maxP):
                    resultNode = i
                    maxP = compareProfits(i, W, items)
                q.append(i)
    print("resultNode: " + str(resultNode) + " Max Profit: " + str(maxP))

def main():
    #item: A Weight: 10 Value: 10
    items = list()
    n = int(raw_input())
    W = int(raw_input())
    for i in range(n):
        items.append(raw_input())
    bfs(items, n, W)

main()
