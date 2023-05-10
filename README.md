# Teste Santander

Primeiro você deve clonar o repositório e acessar o diretório complete e rodar o comando: mvn spring-boot:run

# Para criar um novo cliente envie um POST com os dados em JSON:

POST http://localhost:8080/clientes

JSON:

{
	"nome": "Claudia Silva",
	"planoExclusive": false,
	"saldo": 2000.00,
	"numeroDaConta": "1",
	"dataDeNascimento": "1983-12-24"
}



# Para sacar do cliente criado envie um POST e a quantidade em JSON:

POST http://localhost:8080/clientes/saque/1

JSON:

{
	"quantidade": 485
}



# Para fazer um depósito envie um POST e a quantidade em JSON:

POST http://localhost:8080/clientes/deposito/1

JSON: 

{
	"quantidade": 1000
}



# Para solicitar o histórico envie um GET com o ID do cliente:

GET http://localhost:8080/clientes/historico/1

# Para retornar os dados do cliente envie um GET com o ID do cliente:

GET http://localhost:8080/clientes/1

