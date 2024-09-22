# O Catálogo do Sábio

Case técnico desenvolvido para o processo seletivo da F1RST Digital Services

## I. Arquitetura de Solução e Arquitetura Técnica

**Tecnologias utilizadas:**
- Java 17
- Spring 3.3.3
- Maven
- Mongo DB
- Redis
- Swagger

**Solução implementada**

O projeto é uma API REST desenvolvida em Java utilizando o framework Spring Boot.
A API permite a visualização de livros e dos usuários cadastrados.
Também permite que o usuário visualize um livro e essa informação fica salva em uma lista de visualizados recentemente.

**Decisões de Design**

A aplicação segue o padrão de arquitetura em camadas, separando responsabilidades entre controladores (resources), serviços (services) e repositórios (repositories).  
Para a transferência de dados da API são utilizados DTOs (Data Transfer Objects), garantindo encapsulamento e redução do acoplamento.  
Com a ideia de melhorar a performance da aplicação foi implementado caching utilizando o Redis nas consultas mais frequentes e que os resultados sejam estáticos, o TTL foi definido em 1 hora.  
Foi utilizado o Swagger para facilitar a documentação e testes da API.

## II. Explicação sobre o Case Desenvolvido (Plano de Implementação)

A API foi implementada com foco na gestão de livros e visualização dos mesmos pelo usuário.

![Diagrama de classes](/imgs/uml-classes.png)
### Documentação da API

#### Retorna todos os livros (possui paginação)

```http
  GET /books
```

| Parâmetro   | Tipo       | Default | Descrição                                  |
| :---------- | :--------- | :------ |:-------------------------------------------|
| `page`      | `integer`  | 0       | Opcional. Página dos dados.                |
| `size`      | `integer`  | 100     | Opcional. Quantos dados trazer por página. |

Resposta de sucesso: 200

Contrato:

```json
[
  {
    "isbn": "string",
    "title": "string",
    "author": "string",
    "genres": [
      "string"
    ],
    "publisher": "string"
  }
]
```
#### Retorna um único livro

```http
  GET /books/{id}
```
| Parâmetro   | Tipo       | Default | Descrição                                                   |
| :---------- | :--------- | :------ |:------------------------------------------------------------|
| `id`        | `string`   | -       | **Obrigatório**. O ID do livro a ser buscado.               |
| `user`      | `string`   | -       | Opcional. Header param que pode receber o ID de um usuário. |

Caso seja fornecido o ID de um usuário no header e o usuário existir, o livro será adicionado à lista de visualizados desse usuário.

Resposta de sucesso: 200

```json
{
  "isbn": "string",
  "title": "string",
  "author": "string",
  "genres": [
    "string"
  ],
  "publisher": "string"
}
```

Resposta em caso de livro não encontrado: 404

```json
{
  "timestamp": 1726869619976,
  "status": 404,
  "error": "Object not found",
  "message": "No book found with given id",
  "path": "/books/{user}"
}
```

Resposta em caso de livro encontrado, mas usuário não encontrado: 404

```json
{
  "timestamp": 1726869619976,
  "status": 404,
  "error": "Object not found",
  "message": "No user found with given id",
  "path": "/books/{id}"
}
```

#### Retorna livros que compartilham o mesmo gênero

```http
  GET /books/genre/{genre}
```
| Parâmetro   | Tipo       | Default | Descrição                                        |
| :---------- | :--------- | :------ |:-------------------------------------------------|
| `genre`     | `string`   | -       | **Obrigatório**. O gênero que deve ser filtrado. |

É realizado um match exato do gênero. Não diferencia maiúsculas de minúsculas.

Resposta de sucesso: 200

```json
[
  {
    "isbn": "string",
    "title": "string",
    "author": "string",
    "genres": [
      "string"
    ],
    "publisher": "string"
  }
]
```

Resposta de nenhum encontrado: 200

```json
[]
```

#### Retorna livros que compartilham autor com nomes iguais ao buscado

```http
  GET /books/author/{author}
```
| Parâmetro   | Tipo       | Default | Descrição                                       |
| :---------- | :--------- | :------ |:------------------------------------------------|
| `author`    | `string`   | -       | **Obrigatório**. O autor que deve ser filtrado. |

É realizado um match pelo valor digitado de autores que possuem esse segmento de texto.
Não diferencia maiúsculas de minúsculas.

Resposta de sucesso: 200

```json
[
  {
    "isbn": "string",
    "title": "string",
    "author": "string",
    "genres": [
      "string"
    ],
    "publisher": "string"
  }
]
```

Resposta de nenhum encontrado: 200

```json
[]
```

#### Retorna um usuário

```http
  GET /users/{id}
```
| Parâmetro   | Tipo       | Default | Descrição                                       |
| :---------- | :--------- | :------ |:------------------------------------------------|
| `id`        | `string`   | -       | **Obrigatório**. O ID do usuário a ser buscado. |

Resposta de sucesso: 200

```json
{
  "id": "string",
  "name": "string",
  "lastSeen": [
    {
      "isbn": "string",
      "title": "string",
      "author": "string",
      "genres": [
        "string"
      ],
      "publisher": "string"
    }
  ]
}
```

Resposta em caso de usuário não encontrado: 404

```json
{
  "timestamp": 1726869619976,
  "status": 404,
  "error": "Object not found",
  "message": "No user found with given id",
  "path": "/users/{id}"
}
```

#### Casos de erro

Resposta de erro genérica: 500

```json
{
  "timestamp": 1726869619976,
  "status": 500,
  "error": "Unexpected error",
  "message": "An internal error occurred",
  "path": "/books"
}
```

Resposta de erro de validação: 400

```json
{
  "timestamp": 1726869619976,
  "status": 400,
  "error": "Method argument type mismatch",
  "message": "Failed to convert value of type 'java.lang.String' to required type 'int'; For input string: 10000a",
  "path": "/books"
}
```

Resposta de rota não encontrada: 404

```json
{
  "timestamp": 1726869619976,
  "status": 404,
  "error": "Resource not found",
  "message": "No static resource user.",
  "path": "/user"
}
```


## III. Melhorias e Considerações Finais

**Possíveis melhorias:**

- Implementar mecanismos de autenticação e autorização para garantir a segurança da API.
- Operações de CREATE, UPDATE e DELETE para livros e usuários com o intuito de possuir uma aplicação mais completa e não depender de dados gerados pela aplicação.
- Adicionar novas opções de filtros para consultas mais eficientes.
- Adição de testes de componente automatizados, que garantam a solução de ponta a ponta utilizando mocks.

**Considerações finais:**

Durante o desenvolvimento, alguns desafios foram encontrados, como a configuração do cache com Redis, integração com o MongoDB e a conteinerização da aplicação com o Docker.
No entanto, a aplicação foi implementada com sucesso, atendendo aos requisitos propostos.
A arquitetura em camadas e o uso de DTOs garantem a manutenibilidade e escalabilidade do sistema.