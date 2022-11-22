# BootCamp Backend Java - Projeto Integrador
<p>
    <img alt="GitHub top language" src="https://img.shields.io/github/languages/top/fyshinkae/meli-frescos">
    <img alt="Repository size" src="https://img.shields.io/github/repo-size/fyshinkae/meli-frescos">
    <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/fyshinkae/meli-frescos">
</p>

### Requisito 6 - Gabriel Viana Teixeira

#### Banco de dados
<img alt="Diagrama" src="https://github.com/fyshinkae/meli-frescos/blob/gabriel-viana-req6/docs/diagrama.png">

User Story Code: ml-us-6-06
User Story Name: ml-rating-products-01

COMO comprador
QUERO poder avaliar os produtos dos vendedores
PARA apontar os melhores produtos do marketplace 6

CENÁRIO 1: Considerando que os produtos estejam registrados em alguma warehouse
DADO QUE comprador esteja cadastrado
E o produto esteja cadastrado
E a nota do produto seja um número de 1 a 5
QUANDO o comprador adiciona uma nota a um produto
ENTÃO uma nota é adicionada ao produto

VALIDAÇÃO
▪ A nota do produto não pode ser nula.

| HTTP | Plantilla URI                                                               | Descripción                                                                      | US-code   |
|------|-----------------------------------------------------------------------------|----------------------------------------------------------------------------------|-----------|
| POST | /api/v1/fresh-products/ratings                                              | Cadastra um novo produto. Devolver o status "201 CREATED"                        | ml-us6-06 |
| GET  | /api/v1/fresh-products/ratings/ consumers                                   | Obtém a lista de avaliações associadas aos consumidores                          | ml-us6-06 |
| GET  | /api/v1/fresh-products/ratings/ consumers/{consumerId}                      | Obtém a lista de avaliações associadas ao consumidor do consumerId               | ml-us6-06 |
| GET  | /api/v1/fresh-products/ratings/ consumers/{consumerId}/products/{productId} | Obtém a avaliação do consumidor do consumerId em relação ao produto do productId | ml-us6-06 |
| GET  | /api/v1/fresh-products/ratings/ products/{productId}                        | Obtém a lista de avaliações associadas ao produto do productId                   | ml-us6-06 |
| GET  | /api/v1/fresh-products/ratings/ sellers                                     | Obtém a lista de avaliações associada a cada vendedor                            | ml-us6-06 |


### Objetivo
O objetivo deste projeto final é implementar uma API REST no âmbito do slogan e aplicar
os conteúdos trabalhados durante o BOOTCAMP MELI. (Git, Java, Spring, Banco de Dados,
Qualidade e Segurança).

* [Enunciado Base](https://drive.google.com/file/d/1Oha8lfWwiXB6cYHB32Ppi3cB3hYWKVvE/view?usp=sharing)

### Especificações de Requisitos

* [Requisito 1](https://drive.google.com/file/d/1FpDBHMdlxCwSTP6txExJIgOgcAG8ujTD/view?usp=sharing)
* [Requisito 2](https://drive.google.com/file/d/1oJgq7YcNL_KmGG-drmxEjrpRkfGsj5ft/view?usp=sharing)
* [Requisito 3](https://drive.google.com/file/d/1peHIPZG7TJ-83FOewkoL6YqQVwSUIPcr/view?usp=sharing)
* [Requisito 4](https://drive.google.com/file/d/1OC5XIy1PsnX8ulTfackc-a0w17pw2wyz/view?usp=sharing)
* [Requisito 5](https://drive.google.com/file/d/1eREsXg-O1IBD2SeKmRxlMHjyt8GsLYTs/view?usp=sharing)
* [Requisito 6](https://drive.google.com/file/d/1il0kj0iGrPnVuko06dqxelyTiHcrkB6c/view?usp=sharing)

---

## Endpoints

### Collection do Postman

O arquivo com a coleção de endpoints está [aqui](https://raw.githubusercontent.com/fyshinkae/meli-frescos/main/postman/desafio-frescos.postman_collection.json). Caso precise de instruções consulte a [documentação do Postman](https://learning.postman.com/docs/getting-started/importing-and-exporting-data/).


### Tabela de endpoints

| Endpoint                                 | Verbo |                         Função                                                  |
|:-----------------------------------------|:-----:|:-------------------------------------------------------:                        |
| /api/v1/fresh-products/inboundorder      |  POST | Insere uma ordem de entrada contendo uma lista de lotes                         |
| /api/v1/fresh-products/inboundorder      |  PUT  | Atualiza os lotes no estoque                                                    |
| /api/v1/fresh-products                   |  GET  | Lista todos os produtos (Visão do comprador)                                    |
| /api/v1/fresh-products/list              |  GET  | Lista todos os produtos aceitando um filtro de categoria (Visão do comprador)   |  
| /api/v1/fresh-products/agent/list        |  GET  | Lista todos os produtos, aceitando filtros (Visão do Representante)             |


---

### Equipe 7

- [Anderson Alves](https://github.com/andmalves)
- [Felipe Shinkae](https://github.com/fyshinkae)
- [Gabriel Viana](https://github.com/gabvteixeira)
- [Giovanna Eliz](https://github.com/giovannaelizs)
- [Matheus Ferreira (Theus)](https://github.com/matheusFerreira-meli)
- [Matheus Alves (Ma)](https://github.com/matheusaralves)


---
Feito com 💛 
