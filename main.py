from time import perf_counter_ns
import simples as sp
import mtrhead as mt
import numpy as np
import matplotlib.pyplot as plt



i = 0
sumSimple = 0
nSample = 2
nPrimoSimple = []
nPrimoThread = []
nThread = 3

with open("data.csv") as file:
    data = [line.strip() for line in file]

data = list(map(int, data))


print('\n\nanalise de %d valores\n\n'%(len(data)))



while i < nSample:
    start1 = perf_counter_ns()
    primo_sp = sp.resolve_simples(data)
    finish1 = perf_counter_ns()

    sumSimple += (finish1-start1)/1000000
    nPrimoSimple.append(primo_sp)
    i+=1



arThread = []

for j in range(1 , nThread + 1):

    sumThread = 0
    k = 0
    while k < nSample:

        start2 = perf_counter_ns()
        primo_mt = mt.resolve_thread(data, j)
        finish2 = perf_counter_ns()

        nPrimoThread.append(primo_mt)
        sumThread+= (finish2-start2)/1000000

        k+= 1

    arThread.append(sumThread / nSample)

print('Simple: %f ms' %(sumSimple / nSample))

for l in range(len(arThread)):
    print('Thread: %f ms' %arThread[l])


print('Array primo simples', nPrimoSimple)
print('Array primo thread', nPrimoThread)

horizontalLineThread = np.arange(1, nThread + 1, 1)

plt.title("THREAD")
plt.plot(horizontalLineThread, arThread, )

plt.xlabel("Threads")
plt.ylabel("Tempo (ms)")
plt.show()

