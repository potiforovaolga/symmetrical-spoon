from functools import reduce

class Table:
    def __get_init__(self, length = 100) -> None: # внутренняя функция конструктор
        self.weigth = 0
        self.length = length
        self.hashTable = dict.fromkeys(range(0, length)) # возвращает словарь, в котором ключи равны диапозону, который передали в аргументах  
    

    def __hash__(self, value):
        return reduce(lambda x, y: x + y, [str(ord(v)) for i, v in enumerate(value)]) # функция преобразование берем каждыйй симфол получаем длля него код юникода  и превращаем их в строку, потом складываем их.    

    def __get_hash__(self, value):
        return int(self.__hash__(value)) % self.length # находим остаток от деления, который является значением hash функции 

    def __change_hash_table__(self, newLenght):
        newHashTable = Table(newLenght) # получаем новую таблицу с нвой длиной
        for key, array in self.hashTable.items():
            if array == None:
                continue # если масив равен None  пропускаем и идем на новый виток цикла
            for value in array:
                newHashTable.insert(value) # значения вставляем в полученную новую hash таблицу
        self.hashTable = newHashTable.hashTable
        self.length = newLenght

    def insert(self, value):
        index = self.__get_hash__(value) # вычисляем hash функцию для вводимого значения
        if self.hashTable[index]:
            self.hashTable[index].append(value) 
        else:
            self.hashTable[index] = [value]
            self.weigth += 1 # если  масив по индексу существует - добавляем в него элемент, если нет, то создаем массив
        
        if self.length // 2 < self.weigth:
            self.__change_hash_table__(self.length * 2) # если таблица заполнена больше чем на половину, то увеличиваем её вдвое
    
    def remove(self, value):
        index = self.__get_hash__(value)
        if not self.hashTable[index] or value not in self.hashTable[index]: # если нет удаляемого значения в таблице  то выводим предупреждение
            print(f'значение{value} в таблице не найдено')
            return
        self.hashTable[index].remove(value)
        print(f'значение{value} успешно удалено')
    
    def search(self, value):
        index = self.__get_hash__(value)
        if not self.hashTable[index] or value not in self.hashTable[index]:
            print(f'значение{value} не найдено в таблице')
            return
        print(f'значение{value} находится в таблице')
        print(f'hash индекс: {index}')
        print(f'hash значение: {self.__hash__(value)}')
        

    def print_table(self): # функция которая выводит таблицу на печать
        for key, array in self.hashTable.items():
            if array == None or not array:
                print(key, ': пусто')
            else:
                print(key, ':', array)

table = Table()

while(True):
    data_raw = input("Пожалуйста введите значение(add, remove, search, print): ")

    if data_raw == "add":
        value = input("Пожалуйста введите значение для вставки в таблицу: ")
        table.insert(value)


    elif data_raw == "remove":
        value = input("Пожалуйста введите значение для удаления из таблицы: ")
        table.remove(value)


    elif data_raw == "search":
        value = input("Пожалуйста введите значение для поиска в таблице: ")
        table.search(value)


    elif data_raw == "print":
        table.print_table()

    elif data_raw == 'exit':
        break
