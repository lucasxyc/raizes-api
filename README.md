# Rede Raízes do Nordeste - API REST

## Sobre o Projeto
API RESTful desenvolvida como Projeto Multidisciplinar para a trilha de Back-End da UNINTER (2026). O sistema gerencia as operações comerciais e de vendas de uma rede de lanchonetes, controlando a multicanalidade de requisições, o gerenciamento de estoque local por unidade, o programa de fidelidade conforme as diretrizes da LGPD e o processamento financeiro desacoplado associado a um Gateway de Pagamento simulado (Mock Gateway).

## Tecnologias Utilizadas
- **Linguagem Principal:** Java 21
- **Framework Base:** Spring Boot 3.x
- **Segurança e Autenticação:** Spring Security e Stateless JWT (Json Web Token)
- **Persistência de Dados:** Spring Data JPA e Hibernate
- **Banco de Dados Relacional:** MySQL
- **Controle de Versionamento de Banco:** Flyway
- **Utilidades e Validação:** Lombok e Jakarta Validation (Bean Validation)
- **Documentação de Endpoints:** Swagger UI (OpenAPI 3)

## Requisitos para Execução
- Java Development Kit (JDK) 21 instalado
- Maven 3.x (ou uso do wrapper nativo `./mvnw`)
- Instância ativa do MySQL Server

## Como Executar a Aplicação
1. Clone o repositório público:
   ```bash
   git clone https://github.com/lucasxyc/raizes-api
   ```

2. Abra o arquivo `src/main/resources/application.properties` e insira as credenciais de acesso do seu banco de dados local MySQL (propriedades `spring.datasource.url`, `username` e `password`).

3. Certifique-se de que a pasta das migrações do Flyway (`src/main/resources/db/migration`) esteja presente para que o versionamento seja executado na inicialização.

4. Execute a aplicação através de uma IDE ou via terminal na raiz do projeto utilizando o comando:
   ```bash
   ./mvnw spring-boot:run
   ```

## Mecanismo de Segurança e JWT
A API utiliza criptografia forte BCrypt para o armazenamento seguro de credenciais de usuários em banco. As rotas operacionais são protegidas por filtros interceptores baseados em tokens JWT Stateless. Para fins de homologação e facilitação da bateria de testes locais, os filtros de validação ativa de Token Bearer encontram-se sob a diretriz `.permitAll()` no arquivo `SecurityConfig.java`, permitindo requisições diretas via Postman.

## Documentação Swagger UI
Com a aplicação em execução local, a documentação interativa contendo os contratos de dados expostos, payloads JSON aceitos e verbos HTTP suportados pode ser acessada pelo navegador através da URL:
`http://localhost:8080/swagger-ui/index.html`

## Estrutura Arquitetural do Projeto
O projeto adota uma arquitetura em camadas plana para simplificar a rastreabilidade de responsabilidades e atender ao desacoplamento exigido pelos critérios avaliativos:

```text
br.com.raizes.raizesapi
├── config       -> Configurações globais do ecossistema e beans do sistema
├── controller   -> Exposição de endpoints HTTP e Handlers REST anotados com Swagger
├── dto          -> Records e classes imutáveis para contratos de entrada e saída de dados
├── entity       -> Entidades de domínio JPA mapeadas relacionalmente com chaves estrangeiras
├── exception    -> Controlador global interceptador de exceções para respostas unificadas
├── repository   -> Interfaces de acesso aos dados estendendo JpaRepository
├── service      -> Implementação das regras de negócio organizadas por domínio
└── security     -> Filtros de segurança e geração do mecanismo de tokens JWT
```

## Uso de Inteligência Artificial
Em conformidade com as boas práticas de integridade acadêmica, declara-se que ferramentas de Inteligência Artificial Generativa foram utilizadas exclusivamente como suporte na revisão gramatical, refinamento ortográfico de comentários de código, estruturação e formatação técnica da documentação deste projeto.

## Desenvolvedor
- **JOÃO LUCAS NOGUEIRA DA SILVA** / RU 4708093