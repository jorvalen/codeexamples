#!/usr/bin/python

#import math
import sys

def sqsum(x, limit):
    aux = {}
    while (x > 0):
        #y = math.floor(math.sqrt(x))
        y = int( x **(1/2) )
        if y > limit:
            y = limit
        if y not in aux:
            aux[y] = 0
        aux[y] = aux[y] + 1
        x = x - y ** 2
    print(str(aux))
    return aux

def sqrecsum(x):
    minaux = {}
    mauxterms = None
    limit = int( x **(1/2) )
    while(limit > 0):
        aux = sqsum (x, limit)
        m = 0 
        for value in aux.values():
            m = m + value
        if mauxterms is None or m < mauxterms:
            mauxterms = m
            minaux = aux 
        limit = limit - 1
    print(str(minaux))
            
            
        
        


if __name__ ==  '__main__':
    if len(sys.argv) > 1:
        #sqsum(int(sys.argv[1]), int(sys.argv[2])  )
        sqrecsum(int(sys.argv[1])  )
