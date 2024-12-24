##1. Подготовка ClickHouse на хосте

#1. Установка ClickHouse на хосте:
     apt update
apt-get install -y apt-transport-https
apt-key adv --keyserver keyserver.ubuntu.com --recv-keys E0C56BD4
echo "deb [signed-by=/usr/share/keyrings/clickhouse.gpg] https://repo.clickhouse.tech/deb/stable/main/ bullseye main" | sudo tee /etc/apt/sources.list.d/clickhouse.list
apt-get install -y clickhouse-server clickhouse-client

#2. Запускаем ClickHouse-сервер:
   service clickhouse-server start
##2. Настройка Docker-контейнеров для ClickHouse

# Создание `docker-compose.yml`

# Создание конфигов в папке configs:
/configs/config.xml
/configs/users.xml

# Запуск:
docker-compose up -d

х## 3. Настройка локальной реплики ClickHouse
# Создание конфигов в папке clickhouse-server:
/clickhouse-server/config.xml
/clickhouse-server/users.xml
# Перезапускаем сервер ClickHouse:
   service clickhouse-server restart
clickhouse-client --host=localhost --port=9000    

## 4: Создание реплицируемой таблицы

Создаём таблицы на всех инстансах ClickHouse.
#На хосте:
   CREATE TABLE test_table (
       id UInt64,
       value String
   ) ENGINE = ReplicatedMergeTree('/clickhouse/tables/test_table', 'replica_1')
   ORDER BY id;

# В контейнере:
   CREATE TABLE test_table (
       id UInt64,
       value String
   ) ENGINE = ReplicatedMergeTree('/clickhouse/tables/test_table', 'replica_2')
   ORDER BY id;

#Создание общей таблицы
Создаём Distributed таблицу для объединения данных:
CREATE TABLE distributed_table AS test_table
ENGINE = Distributed('cluster_name', 'default', 'test_table', rand());

# Проверка репликации
Вставка данных на хосте:
   INSERT INTO test_table VALUES (1, 'value1'), (2, 'value2');
