print("Задача 1.\n")

n = int(input("Введитие n: "))
m = int(input("Введите m: "))

if (n < m):
    for i in range(n, m):
        print(i)

print("Задача 2.\n")

n = int(input("Введитие n: "))

if (n >= 0):
    for i in range(n, n+10):
        print(i)

print("Задача 3.\n")

n= int(input("Введитие n: "))

if (n > 0):
    print("Число не равно или больше 0")
elif (n < 0):
    print("Число не равно или меньше 0")
else:
    print("Число не имеет знака")
input()