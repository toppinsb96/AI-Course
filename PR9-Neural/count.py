import sys

DEATH = 0;
SURVIVE = 0;

for line in sys.stdin:
    l = line.split()

    for i in l:
        if(i == 'D'):
            DEATH += 1
        if(i == 'S'):
            SURVIVE += 1


print("Death: " + str(DEATH) + " | " + "Survive: " + str(SURVIVE))
