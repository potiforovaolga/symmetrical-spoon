
from scapy.all import *
from statistics import mean

# Создаем список для хранения размеров пакетов
packet_sizes = []

# Функция для анализа пакетов
def packet_analyzer(packet):
    global packet_sizes  # Объявляем, что будем использовать глобальную переменную packet_sizes

    # Проверяем, содержит ли пакет IP-заголовок
    if IP in packet:
        # Добавляем размер пакета в список packet_sizes
        packet_sizes.append(len(packet))

        # Вычисляем средний размер пакета
        average_size = mean(packet_sizes)

        # Если размер текущего пакета больше чем в два раза превышает средний размер, выводим информацию о нем
        if len(packet) > average_size * 2:
            print("Аномальный пакет обнаружен:")
            print("Размер пакета:", len(packet))
            print(packet.summary())  # Выводим краткую информацию о пакете

# Функция для захвата пакетов
def capture_packets():
    sniff(prn=packet_analyzer, filter="tcp and port 80", store=0)
    # sniff - функция библиотеки Scapy для захвата и анализа сетевых пакетов
    # prn - параметр, указывающий функцию, которая будет вызываться для каждого захваченного пакета
    # filter - фильтр для захвата только определенных типов пакетов (в данном случае TCP на порту 80)
    # store - указывает, нужно ли сохранять захваченные пакеты

# Функция для эмуляции сетевой аномалии
def emulate_anomaly():
    # Создаем поддельный (spoofed) пакет и отправляем его
    spoofed_packet = IP(dst="127.0.0.1") / TCP(dport=80, flags="S") / Raw(load="Hello, this is a spoofed packet")
    send(spoofed_packet, verbose=0)  # Отправляем пакет без вывода дополнительной информации
    print("Эмулирована сетевая аномалия")

# Проверяем, что скрипт запущен как основная программа (а не импортирован как модуль)
if __name__ == "__main__":
    capture_packets()  # Захватываем сетевые пакеты
    emulate_anomaly()  # Эмулируем сетевую аномалию


