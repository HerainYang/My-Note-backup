import random
def fisherYates(list):
    list_copy = list.copy()
    for i in reversed(range(len(list_copy))):
        index = random.randint(0, i)
        temp = list_copy[i]
        list_copy[i] = list_copy[index]
        list_copy[index] = temp
    return list_copy

if __name__ == '__main__':
    list = [1,2,3,4,5,6,7,8]
    print(fisherYates(list))
