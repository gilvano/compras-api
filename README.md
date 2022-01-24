# Compras API

## API para compras de revendedores
API REST para compras de revendedores

- Cadastro de revendedor
- Login de revendedor
- Cadastro de compras do revendedor
- Listagem das compras do revendedor
- Retorno do valor acumulado de cashback

### Tecnologias utilizadas
- Kotlin
- Spring Boot Web, Data Mongo, Test, Validation, Security, OpenFeign
- JUnit
- Mockk
- MongoDB
- Docker
- WireMock

### Execução do projeto

Clonar o projeto
  
```git clone https://github.com/gilvano/compras-api.git```

Navegar até a pasta do projeto e executar o comando para subir os serviços do MongoDB e do WireMock

```docker compose up -d ```

Rodar o seguinte comando para subir a aplicação

```./gradlew bootRun```

Para rodar os testes basta executar o comando

```./gradlew test```

Para acessar a url com a documentação da API

 [localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)


### Utilização da API

#### Cadastro de revendedor

POST - http://localhost:8080/api/v1/resellers

#### Login de revendedor

POST - http://localhost:8080/login

#### Cadastro de compra

POST - http://localhost:8080/api/v1/purchases

#### Listagem das compras do revendedor

GET - http://localhost:8080/api/v1/purchases/all

#### Retorno do total do cashback acumulado do revendedor

GET - http://localhost:8080/api/v1/purchases/accumulated

