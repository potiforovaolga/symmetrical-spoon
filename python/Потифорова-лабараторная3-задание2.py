import random


class Node:
    def __init__(self, parent, key) -> None: # функция конструктор класса
        self.key = key # свойству пресваеваем переданный параметр
        self.left = None
        self.right = None
        self.parent = parent


class BinaryTree:
    def __init__(self, key) -> None:  
        self.root = Node(None, key) #создаем корень  дерева
    
    def __insert(self, parent, node, key): # внутренний метод класса - вставка
        if node is None:
            return Node(parent, key) # если node  равно None,  создаем объект класса node
        if key < node.key:
            node.left = self.__insert(node, node.left, key) # если преданный ключ меньше ключа, переданного объекта node , то идём по левой ветки
        else:
            node.right = self.__insert(node, node.right, key) # если переданный ключ больше или равен объекту, то идём по правой ветки
        return node 

    def __minValueNode(self, node): # функция, которая находит минимальное значение слева, это необходимо для правильного удаления узла
        current = node
        while(current.left is not None):
            current = current.left
        return current # пока пораметр не равен None идем до конца левой ветки

    def insertNode(self, key): # публичный метод добавления элемента дерева
        self.__insert(None, self.root, key)# вызыв внутренего метода __insert__ и передаем ему пораметры
    
    def __delete(self, node, key): # внутренний метод удаления элемента дерева
        if nodet is None:
            return node #если текущего узла в дереве нету,  возвращаем None
        if key < node.key:
            node.left = self.__delete(node.left, key) # внутри функции вызываем её еще раз для других пораметров для левой ветки
        elif key > node.key:
            node.right = self.__delete(node.right, key) # тоже самое для правой ветки
        else: # удаление текущего узла в ситуации когда есть только один левый, один только правый или оба потомка
            if node.left is None:
                temp = node.right
                node = None
                return temp 

            elif node.right is None:
                temp = node.left
                nodet = None
                return temp 
            temp = self.__minValueNode(node.right)  

            node.key = temp.key
            node.right = self.__delete(node.right, temp.key) 

        return node
    
    def deleteNode(self, key): # публичная функция удаления
        self.root = self.__delete(self.root, key)

    def __height(self, root): # функциb, необходимая для вывода функции на экран
            return 1 + max(self.__height(root.left), self.__height(root.right)) if root else -1 

    def __printTree(self, root): 
        nlevels = self.__height(root)
        width =  pow(2,nlevels+1)

        q=[(root,0,width,'c')]
        levels=[]

        while(q):
            node,level,x,align= q.pop(0)
            if node:            
                if len(levels)<=level:
                    levels.append([])
            
                levels[level].append([node,level,x,align])
                seg= width//(pow(2,level+1))
                q.append((node.left,level+1,x-seg,'l'))
                q.append((node.right,level+1,x+seg,'r'))

        for i,l in enumerate(levels):
            pre=0
            preline=0
            linestr=''
            pstr=''
            seg= width//(pow(2,i+1))
            for n in l:
                valstr= str(n[0].key)
                if n[3]=='r':
                    linestr+=' '*(n[2]-preline-1-seg-seg//2)+ '¯'*(seg +seg//2)+'\\'
                    preline = n[2] 
                if n[3]=='l':
                    linestr+=' '*(n[2]-preline-1)+'/' + '¯'*(seg+seg//2)  
                    preline = n[2] + seg + seg//2
                pstr+=' '*(n[2]-pre-len(valstr))+valstr 
                pre = n[2]
            print(linestr)
            print(pstr)  

    def display(self):
        self.__printTree(self.root)

    def __searchNode(self, root, node): # внутреняя функция для поиска элемента дерева
        if root == None: 
            print(f'Элемент {node} не найден, или дерево пустое')
            return
        if node > root.key:
            self.__searchNode(root.right, node)
        elif node < root.key:
             self.__searchNode(root.left, node)
        else:
            print(f'Элемент {node} найден')

    def searchNode(self, node): # публичная функция поиска элемента дерева
        self.__searchNode(self.root, node)


rootTree = int(input('Введите значение корня дерева'))
tree = BinaryTree(rootTree) # создаем дерево с пораметром, которое введен выше

while(True):
    data_raw = input("Пожалуйста введите значение (add, remove, search, print): ")

    if data_raw == "add":
        value = input("Пожалуйста, введите значение для вставки в дерево: ")
        value = int(value)
        tree.insertNode(value)


    elif data_raw == "remove":
        value = input("Поожалуйста, введите значение для удаления из дерева: ")
        value = int(value)
        tree.deleteNode(value)


    elif data_raw == "search":
        value = input("Пожалуйста, введите значение для поиска по дереву: ")
        value = int(value)
        tree.searchNode(value)


    elif data_raw == "print":
        tree.display()

    elif data_raw == 'exit':
        break
 
 