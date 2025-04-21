```markdown
### Autor: Fábio Delgado

Olá! Seja bem vindo ;)

## Índice
1. [SpringBootApp](#SpringBootApp)
2. [Projeto e Conteúdo](#Projeto-e-Conteudo)
3. [Swagger](#Swagger)
4. [JWT](#JWT)
5. [SQLite](#SQLite)
6. [SMTP](#SMTP)
7. [Publicação](#Publicação)
8. [Suporte](#Suporte)

## SpringBootApp

Este repositório contém um exemplo de APIs REST desenvolvida em JAVA utilizando o framework Spring Boot com SQLite para armazenamento de dados.

### Pre-requisitos

JDK 1.8 +
Maven 3.0 +

### Como executar essa aplicação?

- Faça o download do zip ou clone o repositório Git.
- Descompacte o arquivo zip (caso tenha baixado o .zip)
- Abra o diretório Prompt de Comando e Altere (cd) para a pasta que contém pom.xml
- Abra o Visual Studio Code ou execute o comando via prompt `code .`
- Pressione `F5` para executar a aplicação.

A aplicação deverá estar disponível em seu navegador no endereço: 

http://localhost:8080/swagger-ui.html

http://localhost:8080/login.html


![swagger](/img/swagger_v2.png)

### Extensões recomendadas para desenvolvimento no VSCODE

- Java Extension Pack from Microsoft
- Spring Boot Extension Pack from Pivotal
- Spring Boot Dashboard from Microsoft
- Lombok Annotations Support for VS Code from Gabriel Basilio Brito
- Java Code Generators from Sohibe

#### Configurando o Java no Visual Studio Code

* Pressione `Ctrl`+`Virgula`.
* Procure por "java.home".  
**Caso você esteja usando a versão mais recente do vscode:**  
* Digite no seu java path(**Não se esqueça de colocar no caminho em:** `" "`).*  
**Caso você esteja em uma versão mais antiga do vscode:**  
* Clique no lápis ao lado da linha que começa com "java.home".
* Clique em "Copy to settings".
* Digite no seu java path(**Não se esqueça de colocar no caminho em:** `" "`).*
* Press `Ctrl`+`S`  
**Se você não sabe o seu caminho java, digite** `which java` **no seu terminal (no Windows, observe o formato correto, e.g:** `"java.home": "C:/Program Files/Java/jdk-11.0.2"`)

## Projeto e Conteúdo

O Spring Boot é um projeto da Spring que veio para facilitar o processo de configuração e publicação de nossas aplicações. A intenção é ter o seu projeto rodando o mais rápido possível e sem complicação. Ele consegue isso favorecendo a convenção sobre a configuração. Basta que você diga pra ele quais módulos deseja utilizar (WEB, Template, Persistência, Segurança, etc.) que ele vai reconhecer e configurar.

### Entendendo a estrutura de projeto

![ini](/img/ini.png)

## Swagger

O Swagger é uma aplicação open source que auxilia os desenvolvedores a definir, criar, documentar e consumir APIs REST;
É composto de um arquivo de configuração, que pode ser definido em YAML ou JSON;
Fornece ferramentas para: auxiliar na definição do arquivo de configuração (Swagger Editor), interagir com API através das definições do arquivo de configuração (Swagger UI) e gerar templates de código a partir do arquivo de configuração (Swagger Codegen).

Fonte: https://swagger.io/resources/webinars/getting-started-with-swagger/

> A maneira mais fácil de instalar é usar o Maven:

`pom.xml`
```xml
		<dependency>
		    <groupId>io.springfox</groupId>
		    <artifactId>springfox-swagger2</artifactId>
		    <version>2.9.2</version>
		</dependency>
		<dependency>
		    <groupId>io.springfox</groupId>
		    <artifactId>springfox-swagger-ui</artifactId>
		    <version>2.9.2</version>
		</dependency>
```
> Exemplo de implementação para testes

```java
@EnableSwagger2
@RestController
@RequestMapping(value="/api")
public class MainController {
 
    @GetMapping(value="/ola")
    public String getMethodName(){
        return "Olá mundo";
    }
```

## JWT
O JWT (JSON Web Token) nada mais é que um padrão (RFC-7519) de mercado que define como transmitir e armazenar objetos JSON de forma simples, compacta e segura entre diferentes aplicações, muito utilizado para validar serviços em Web Services pois os dados contidos no token gerado podem ser validados a qualquer momento, uma vez que ele é assinado digitalmente.

JSON Web Tokens (JWT) é um padrão stateless porque o servidor autorizador não precisa manter nenhum estado; o próprio token é suficiente para verificar a autorização de um portador de token.

Os JWTs são assinados usando um algoritmo de assinatura digital (por exemplo, RSA) que não pode ser forjado. Por isso, qualquer pessoa que confie no certificado do assinante pode confiar com segurança que o JWT é autêntico. Não há necessidade de um servidor consultar o servidor emissor de token para confirmar sua autenticidade.

Fonte: https://jwt.io/introduction/

`pom.xml`
```xml

		<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt</artifactId>
			<version>0.9.1</version>
		</dependency>
```

`Encode`
```java
try {
    Algorithm algorithm = Algorithm.HMAC256("secret");
    String token = JWT.create()
        .withIssuer("auth0")
        .sign(algorithm);
} catch (JWTCreationException exception){
    //Invalid Signing configuration / Couldn't convert Claims.
}
```

`Verify a Token`
```java
String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXUyJ9.eyJpc3MiOiJhdXRoMCJ9.AbIJTDMFc7yUa5MhvcP03nJPyCPzZtQcGEp-zWfOkEE";
try {
    Algorithm algorithm = Algorithm.HMAC256("secret");
    JWTVerifier verifier = JWT.require(algorithm)
        .withIssuer("auth0")
        .build(); //Reusable verifier instance
    DecodedJWT jwt = verifier.verify(token);
} catch (JWTVerificationException exception){
    //Invalid signature/claims
}
```

## SQLite

SQLite é um banco de dados relacional leve e independente que armazena dados diretamente em um arquivo no sistema de arquivos. Ele é amplamente utilizado em aplicações que requerem um banco de dados embutido, sem necessidade de configuração complexa de servidores de banco de dados.

No seu projeto, o SQLite está sendo utilizado para armazenar dados. O código foi adaptado para usar SQLite em vez de SQL Server.

> Dependência no `pom.xml` para SQLite:

```xml
	<dependency>
		<groupId>org.xerial</groupId>
		<artifactId>sqlite-jdbc</artifactId>
		<version>3.34.0</version>
	</dependency>
```

> Exemplo de configuração para SQLite no `application.properties`:

```properties
spring.datasource.url=jdbc:sqlite:mydatabase.db
spring.datasource.driverClassName=org.sqlite.JDBC
spring.datasource.platform=sqlite
spring.datasource.initialize=true
```

> Exemplo de implementação para acesso ao banco de dados SQLite:

```java
@Repository
public class ClienteRepository {

    private static final String SQL_FIND_ALL = "SELECT * FROM Clientes;";

    private static final BeanPropertyRowMapper<Cliente> ROW_MAPPER = new BeanPropertyRowMapper<>(Cliente.class);

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    public Iterable<Cliente> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL, ROW_MAPPER);
    }
}
```

## Publicação

Para publicar a aplicação, você pode optar por hospedar em servidores como Heroku, AWS ou DigitalOcean, configurando o banco de dados SQLite conforme a necessidade.

## Suporte

Por favor, entre em contato conosco via [Email]
```