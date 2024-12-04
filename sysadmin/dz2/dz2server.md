
#Для запуска сервера 
C:\Users\Пользователь\pathtodirectory\ serverTest.exe


▎# Проверка на наличие зомби-процессо

Get-Process | Where-Object { $_.Responding -eq $false }


▎# Количество открытых файловых дескрипторов

Get-Process | Select-Object -Property Id, ProcessName, Handles


▎# Многократные запросы

for ($i=1; $i -le 15; $i++) { Invoke-WebRequest -Uri "http://localhost:8080/bugs" }


