# Gerenciamento de Abastecimentos - README

Este projeto é uma API REST para gerenciar abastecimentos de veículos, construída com Spring Boot e utilizando o banco de dados H2 em memória.  Ele segue a arquitetura Clean Architecture, com camadas bem definidas para domínio, aplicação e infraestrutura.

## Pré-requisitos

* Java 17 ou superior instalado.  Verifique com `java -version`.
* Maven 3.9.9 instalado.  Verifique se você consegue executar `mvn -version`   no seu terminal.
* Um IDE Java (como IntelliJ IDEA ou Eclipse) - Recomendado, mas não obrigatório.
* Git (para clonar o repositório).


## Clonando o Repositório  

Se o código estiver em um repositório Git, clone-o usando o comando:

```bash
git clone https://github.com/MarceloMacedoDev/abastecimento-api.git
```
## **Navegue até o diretório do projeto:**
```bash
 cd fuelmanagement
```
## Inicializando o Banco de Dados H2
O aplicativo utiliza o banco de dados H2 em memória. O console H2 está habilitado e será iniciado automaticamente com a aplicação. Não é necessário nenhum passo adicional para configurar o banco de dados.

## Executando o Aplicativo
1. **Compile o projeto (se necessário):** Se você clonou o repositório, compile o projeto usando Maven:
```bash
mvn clean install
```
 

2. **Execute o aplicativo:** Execute o seguinte comando Maven:
```bash
mvn spring-boot:run
```
 

A aplicação iniciará na porta 8080. Você verá mensagens de log no console, incluindo as consultas SQL geradas pelo Spring Data JPA (devido a spring.jpa.show-sql=true).

## Acesso ao Console H2
O console H2 está disponível em: ***http://localhost:8080/h2-console***

- JDBC URL: jdbc:h2:mem:testdb
- Usuário: sa
- Senha: (deixe em branco)
## Testando a API
Após a execução do aplicativo, você pode testar os endpoints REST usando uma ferramenta como Postman, curl ou o navegador.

**Endpoints:**

- **GET /abastecimentos:** Retorna uma lista de todos os abastecimentos. Você pode adicionar um parâmetro de consulta placa para filtrar os resultados (ex: /abastecimentos?placa=AAA-1234).

- **POST /abastecimentos:** Adiciona um novo abastecimento. O corpo da requisição deve ser um JSON contendo os dados do abastecimento (placa, quilometragem, dataHora, valorTotal). Exemplo:
```json
{
  "placa": "AAA-1234",
  "quilometragem": 10000,
  "dataHora": "2024-10-27T10:00:00",
  "valorTotal": 100.00
}
```
**Validações:** A API realiza validações nos dados de entrada. Erros de validação serão retornados como respostas HTTP apropriadas (provavelmente com código 400 Bad Request e uma mensagem de erro descrevendo o problema).

- **DELETE /abastecimentos/{id}:** Remove um abastecimento específico pelo seu ID.
 

## Testes
O projeto inclui testes unitários e de integração. Você pode executá-los usando Maven  com **95% de coberture**:
```bash
mvn test
 ```

## Parando a Aplicação
Para parar a aplicação, pressione Ctrl+C no terminal onde ela está sendo executada.

## Arquitetura (Resumo)
O projeto utiliza a arquitetura Clean Architecture, separando as responsabilidades em camadas:

- **Camada de Domínio (Core):** Entidades e regras de negócio (***Fueling.java, FuelingValidationException.java***).
- **Camada de Aplicação:** Lógica de negócio e casos de uso (***FuelingService.java, FuelingServiceImpl.java***).
- **Camada de Infraestrutura:** Acesso a dados, mapeadores e DTOs (***FuelingRepository.java, FuelingMapper.java, FuelingDTO.java***).
- **Camada de Apresentação:** Controllers REST (***FuelingController.java***).
 

