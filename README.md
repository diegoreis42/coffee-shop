# Coffee Shop Backend

Este é o repositório do backend do Coffee Shop. Aqui estão as instruções para executar o projeto.

## Pré-requisitos

Certifique-se de ter as seguintes ferramentas instaladas em sua máquina:

- java 21
- docker


## Executando o projeto

1. execute: 

```bash
docker-compose up --build
```
prontinho :) sua API estará exposta em localhost:9999

Para rodar somente o banco de dados execute:

```bash
docker-compose -f docker-compose.dev.yml up --build 
```

