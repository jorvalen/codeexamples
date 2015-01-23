#!/usr/bin/python

def calc24(first, numbers, fn):
    if not first:
        for n in numbers:
            first = n
            numbers.remove(n)
            if calc24(first, numbers, fn):
                return True
            numbers.append(n)

    for n in numbers:
        for f in fn:
            #divisionbyzero fml
            try:
                out = f(first, n)
            except:
                continue
            if len(numbers) <= 1:
                if out == 24:
                    return True
            else:
                numbers.remove(n)
                if calc24(out, numbers, fn):
                    return True
                numbers.append(n) 
    return False


func = []
func.append(lambda x, y: x + y)
func.append(lambda x, y: x / y)
func.append(lambda x, y: x * y)
func.append(lambda x, y: x - y)

nl = [2, 3, 4, 0]

print(calc24(None, nl, func))

