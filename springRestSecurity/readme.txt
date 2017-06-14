Para gerar um token faça uma requisição POST para http://localhost:8080/springRestSecurity/login com o seguinte JSON no body:
{

	"username":"admin",

	"password":"admin"

}
O token virá no header de nome "Token" da resposta.

Para acessar um serviço protegido (Ex: http://localhost:8080/springRestSecurity/autorizacao/getAll) é preciso passar no header de nome "Autorization" o texto "Bearer <token>", onde <token> deve ser substituído pelo token gerado. 