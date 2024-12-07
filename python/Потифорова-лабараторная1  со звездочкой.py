
import random
import numpy as np


print("Введите количество строк: ")
n=int(input())
print("Введите количество столбцов: ")
m=int(input())

def get_matrix(n, m):
    matrix = [] #объявляем пустой масив 
    for _ in range(n):
        vector = [] #объявляем пустой масив внутри цикла
        for _ in range(m):
            vector.append(random.randint(0, 100)) #заполняем вектор случайными значениями
        matrix.append(vector) #добавляем в матрицу вектор
    return matrix #возвращаем матрицу
my_matrix = get_matrix(n, m) #объявляем переменную
print(my_matrix)
print("Матрица размером", n, "на", m) #выводим на экран


# Транспонирование матрицы
transmatrix=[]
a=0
while(a<m): #объявляем цикл, который работает пока выполняется заданное условие
    b=0
    transvector=[]
    while(b<n):
        transvector.append(my_matrix[b][a]) #заполняем строки значениями столбцов
        b=b+1

    transmatrix.append(transvector) 
    a=a+1

print("транспонированная матрица")
print(transmatrix)



if n == m:
    determinant = np.linalg.det(my_matrix) #расчёт определителя (детерминанты) матрицы
    print('Определитель матрицы равен определителю транспонированной матрицы: ', determinant)
else:
    print('Матрица не квадратная, не возможно найти определитель')

