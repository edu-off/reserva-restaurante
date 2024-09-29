# Aplicação reserva-restaurante - Tech Challenge Pós Tech FIAP

Tech Challenge Pós Tech FIAP - Back end de aplicação que controla reservas de restaurantes

## **Tecnologias** 

* **Linguagem:** Java 17
* **Framework principal:** Spring boot 3.3.2
* **Banco de dados:** Mysql 9.0.1
* **Banco de dados em memória:** H2 Database 2.3.232
* **Framework de testes:** Junit 5
* **Relatório de testes:** Allure 2.29.0

## **Comandos importantes via makefile**

* Para build da aplicação:
```shell
make build
```

<br><br/>

* Para execução de testes unitários:
```shell
make unit-test
```

<br><br/>

* Para execução de testes integrados:
```shell
make integration-test
```

<br><br/>

* Para execução de testes de performance:
```shell
make performance-test
```

<br><br/>

* Para execução de testes de sistema:
```shell
make system-test
```

<br><br/>

* Para execução de todos os testes:
```shell
make test
```

<br><br/>

* Para subir container da aplicação:
```shell
make docker-start
```

<br><br/>

* Para exibir relatório de testes:
```shell
make relatorio-testes
```

<br><br/>

* Para efetuar build da aplicação e gerar relatório de testes:
```shell
make gerando-relatorio-testes
```
Obs.: Para todos comandos listados, o make deve estar instalado na máquina de quem o for executar. Já para somente os dois últimos comandos, o node js e o allure devem estar instalados. 

<br><br/>

## **Comandos importantes via terminal**

* Para execução de teste unitários:
```shell
mvn test
```

<br><br/>

* Para execução de teste integrados:
```shell
mvn test integration-test
```

<br><br/>

* Para execução de testes de performance:
```shell
mvn gatling:test -P performance-test
```
Obs.: A aplicação deve estar em execução.

<br><br/>

* Para execução de testes de sistema:
```shell
mvn test -P system-test
```
Obs.: A aplicação deve estar em execução.

<br><br/>

* Para exibição do relatório de testes:
```shell
allure serve target/allure-results
```
Obs.: O node js e o allure devem estar instalados e o build da aplicação estar concluído.

<br><br/>

* Para subir container da aplicação e servidor de banco de dados via docker compose:
```shell
docker-compose -f docker-compose.yml up -d
```