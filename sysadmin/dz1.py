
from collections import Counter

# Чтение лог-файла
with open('access.log', 'r') as file:
    logs = file.readlines()

# 1. Топ 10 IP-адресов с которых идут запросы
ip_addresses = [line.split()[0] for line in logs]
top_ips = Counter(ip_addresses).most_common(10)

print("Топ 10 IP-адресов:")
for ip, count in top_ips:
    print(f"{ip}: {count}")

# 2. Подсчет количества методов: POST, GET, PUT, DELETE, PATCH
methods = [line.split()[5].strip('"') for line in logs]
method_count = Counter(methods)

print("\nКоличество методов:")
for method in ['GET', 'POST', 'PUT', 'DELETE', 'PATCH']:
    print(f"{method}: {method_count[method]}")

# 3. Количество операционных систем с которых выполняются запросы
user_agents = [line.split('"')[5] for line in logs]
os_counter = Counter()

for user_agent in user_agents:
    if "Windows" in user_agent:
        os_counter["Windows"] += 1
    elif "Mac OS" in user_agent:
        os_counter["MacOS"] += 1
    elif "Linux" in user_agent:
        os_counter["Linux"] += 1
    elif "Android" in user_agent:
        os_counter["Android"] += 1
    elif "iPhone" in user_agent or "iPad" in user_agent:
        os_counter["iOS"] += 1

print("\nКоличество операционных систем:")
for os_name, count in os_counter.most_common():
    print(f"{os_name}: {count}")

# 4. Самое популярное устройство по количеству сделанных запросов
device_counter = Counter()

for user_agent in user_agents:
    if "Mobile" in user_agent:
        device_counter["Mobile"] += 1
    else:
        device_counter["Desktop"] += 1

popular_device = device_counter.most_common(1)[0]
print(f"\nСамое популярное устройство: {popular_device[0]} ({popular_device[1]} запросов)")

# 5. Топ 5 популярных браузеров включая их версию
browser_counter = Counter()

for user_agent in user_agents:
    if "Chrome" in user_agent:
        browser_info = user_agent.split("Chrome/")[0] + "Chrome/" + user_agent.split("Chrome/")[1].split()[0]
        browser_counter[browser_info] += 1
    elif "Firefox" in user_agent:
        browser_info = user_agent.split("Firefox/")[0] + "Firefox/" + user_agent.split("Firefox/")[1].split()[0]
        browser_counter[browser_info] += 1
    elif "Safari" in user_agent and "Chrome" not in user_agent:
        browser_info = user_agent.split("Version/")[0] + "Version/" + user_agent.split("Version/")[1].split()[0]
        browser_counter[browser_info] += 1

print("\nТоп 5 популярных браузеров:")
for browser, count in browser_counter.most_common(5):
    print(f"{browser}: {count}")

