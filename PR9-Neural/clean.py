import sys

count = 0;
avg = 0;
# 24.044258373205743
# 23.79929292929293
file = ""
for line in sys.stdin:
    row  = line.split(",")

    if(row[0] == "\n"):
        break
    if(row[1] == '1'):
        row[1] = 'S'
    else:
        row[1] = 'D'

    for i in range(len(row)-1):
        file += row[i] + ","
    file += row[len(row)-1]

print(file)
'''
    if(row[4] == "Age"):
        print("Why")
        #Do Nothing
    elif(row[4] == ''):
        count += 1
    else:
        avg += float(row[4])
        count += 1

print(avg/count)
'''
