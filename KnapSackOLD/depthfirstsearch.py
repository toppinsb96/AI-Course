print("THIS IS DEPTH-FIRST SEARCH!!!!!!!!!!!")
print("Input a name and weight for an item.")
print("When you are done just press enter with a empty input")

names = list()
weights = list()

while(True):
    names.append(str(raw_input("Type a name: ")))
    if(names[len(names)-1] == ""):
        names.pop()
        break
    try:
        weights.append(int(raw_input("Type an integer number: ")))
    except ValueError:
        print("This is not a valid weight integer")
