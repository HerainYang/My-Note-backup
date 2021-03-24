def multiply(x, y):
    n = min(len(str(x)), len(str(y)))
    if x == 0 or y == 0:
        return 0
    if x == 1:
        return y
    if y == 1:
        return x
    if n == 1:
        return x*y
    x1 = int(x/pow(2, int(n/2)))
    x0 = x - x1*pow(2, int(n/2))

    y1 = int(y/pow(2, int(n/2)))
    y0 = y - y1*pow(2, int(n/2))

    print(n)
    print(x1, x0, y1, y0)

    first_term = multiply(x1, y1)
    other_term = multiply(x1, y0) + multiply(x0, y1)
    last_term = multiply(x0, y0)

    print('f', first_term)
    print(other_term)
    print(last_term)

    print(n)

    return first_term*pow(2, 2*int(n/2))+other_term*pow(2, int(n/2))+last_term

if __name__ == '__main__':
    print(multiply(1234, 1234))