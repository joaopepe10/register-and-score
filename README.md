# Serasa Experian - Registro e Score API

API REST para gerenciamento de pessoas com cálculo de score e busca automática de endereço via CEP.

## 🚀 Tecnologias
- **Java 21**
- **Spring Boot 3.3.5**
- **H2 Database** (Banco de dados em memória)
- **MapStruct** (Mapeamento de objetos)
- **Feign Client** (Integração com ViaCEP)
- **Spring Security & JWT** (Autenticação e Autorização)
- **JUnit 5 & AssertJ** (Testes Unitários)


## 📖 Documentação (Swagger)
A documentação interativa dos endpoints pode ser acessada em:
`http://localhost:8080/swagger-ui.html` ou acesse  `src/main/resources/swagger.yaml`


## 📬 Postman Collection
Para facilitar o teste manual dos fluxos de **Registro**, **Login** e **Gerenciamento de Pessoas**, disponibilizei uma collection completa.

**Como usar:**
1. Importe o arquivo `Serasa Experian API.postman_collection.json` no seu Postman.
2. Utilize o endpoint de **Register** para criar seu usuário (`ADMIN` ou `USER`).
3. Utilize o endpoint de **Login** para obter seu token JWT.
4. A collection já está configurada para usar a variável `{{baseUrl}}`.

---
Desenvolvido como parte do Desafio Serasa Experian.
