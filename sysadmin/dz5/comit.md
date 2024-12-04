
#1. Запуск  Minikube :
minikube start

  - Используется образ gcr.io/k8s-minikube/storage-provisioner:v5
* Включенные дополнения: storage-provisioner, default-storageclass
* Готово! kubectl настроен для использования кластера "minikube" и "default" пространства имён по умолчанию

#2. применяем манифесты для создания подов:
kubectl apply -f pod1.yaml
kubectl apply -f pod2.yaml

pod/pod1 created
pod/pod2 created

#3. проверка статуса подов
kubectl get pods

NAME   READY   STATUS    RESTARTS   AGE
pod1   1/1     Running   0          9m38s
pod2   1/1     Running   0          8m48s

#4. Добавление метки к подам,чтобы они соответствовали селектору сервиса. 
kubectl label pod pod1 app=my-app
kubectl label pod pod2 app=my-app

pod/pod1 labeled
pod/pod2 labeled

#5. Применяем манифест сервиса:
kubectl apply -f service.yaml

service/my-service created

#6. Проверка созданногой сервиса
kubectl get services

NAME         TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)   AGE
kubernetes   ClusterIP   10.96.0.1       <none>        443/TCP   26m
my-service   ClusterIP   10.101.60.166   <none>        80/TCP    21s

#7. Тестирование взаимодействие между подами. Например, использование kubectl exec для выполнения команд внутри одного из подов и проверить доступ к другому поду через его имя:
kubectl exec -it pod1 -- curl http://pod2:80

curl: (6) Could not resolve host: pod2
command terminated with exit code 6
PS D:\kub