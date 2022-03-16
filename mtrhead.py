import sympy
import concurrent.futures


def tCalculaPrimo(data):
    primos = 0
    for i in range(len(data)):
        if sympy.isprime(data[i]):
            primos += 1
    return primos


def resolve_thread(data, nThread):
    ThreadsQtdd = nThread
    tamanholista = len(data)

    index = range(0, tamanholista + (tamanholista // ThreadsQtdd), tamanholista // ThreadsQtdd)

    print('nThread ', nThread, ' = ', index)

    for i in range(len(index) - 1):
        print(i, '=', index[i], ' até ', index[i+1])

    print("")
    primos = 0
    with concurrent.futures.ThreadPoolExecutor() as executor:
        futures = []
        for i in range(ThreadsQtdd):

            if ThreadsQtdd - 1 == i and index[i+1] < tamanholista:
                lastIndex = tamanholista
            else:
                lastIndex = index[i+1]

            futures.append(executor.submit(tCalculaPrimo, data=data[index[i]:lastIndex]))
        for future in concurrent.futures.as_completed(futures):
            # futures.append(future.result())
            primos += future.result()
    return primos

def split(a, n):
    k, m = divmod(len(a), n)
    return (a[i*k+min(i, m):(i+1)*k+min(i+1, m)] for i in range(n))
