
# Делаем файл 'serverTest' исполняемым
chmod +x serverTest
# Команда chmod изменяет права доступа к файлу.
# '-x' добавляет разрешение на выполнение для файла 'serverTest',
# что позволяет запускать его как программу или скрипт.

# Запускаем сервер
./serverTest
# './' указывает, что мы запускаем исполняемый файл из текущей директории.
# Эта команда инициирует выполнение сервера, который находится в файле 'serverTest'.
   
#Проверяем запущенные процессы и смотрим их PID   
 ps -h  
    804 tty1     Ss+    0:00 /sbin/agetty -o -p -- \u --noclear tty1 linux
 172848 pts/0    Ss     0:00 -bash
 174889 pts/0    R+     0:00 ./serverTest

#Запускаем панику
curl http://localhost:8080/bugs

Recovered from panic: Random panic occurred!

#Запускаем просмотр системных вызовов
strace -o logs.txt -p 2529 -s 512 -f

#В новом окне терминала выполняем спам сервера 
curl http://localhost:8080/bugs

#В окне терминала  с запущенным strace видим следующие изменения 
 strace -o name.txt -p 2529 -s 512 -fыполняем 
# выполняем проверку
ps -h
и видим зомби процессы, которые появились в списке активных процессов
   2521 pts/0    Ss     0:00 -bash
   2529 pts/0    Sl+    0:00 ./serverTest
   2540 pts/1    Ss     0:00 -bash
   2561 pts/1    S+     0:00 strace -o name.txt -p 2529 -s 512 -f
   2574 pts/2    Ss     0:00 -bash
   2613 pts/0    S+     0:00 ./serverTest
   2616 pts/0    S+     0:00 ./serverTest
   2618 pts/0    S+     0:00 ./serverTest
   2621 pts/2    R+     0:00 ps -h






Пока всё, но продолжаю смотреть