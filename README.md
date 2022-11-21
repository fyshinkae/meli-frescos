# BootCamp Backend Java - Projeto Integrador
<p>
    <img alt="GitHub top language" src="https://img.shields.io/github/languages/top/fyshinkae/meli-frescos">
    <img alt="Repository size" src="https://img.shields.io/github/repo-size/fyshinkae/meli-frescos">
    <img alt="GitHub last commit" src="https://img.shields.io/github/last-commit/fyshinkae/meli-frescos">
</p>


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
* [Requisito 6](https://drive.google.com/file/d/1uT8A3F2_9_euNHiGwANR2Md7DLk8N9Mg/view?usp=sharing)

---

## Endpoints

### Collection do Postman

O arquivo com a coleção de endpoints está [aqui](https://raw.githubusercontent.com/fyshinkae/meli-frescos/main/postman/desafio-frescos.postman_collection.json). Caso precise de instruções consulte a [documentação do Postman](https://learning.postman.com/docs/getting-started/importing-and-exporting-data/).

---

O arquivo com a coleção de endpoints do requisito 6 está [aqui](https://raw.githubusercontent.com/fyshinkae/meli-frescos/matheus-ferreira-req6/postman/desafio-frescos.postman_collection.json)

---

### Diagrama de entidade e relacionamento
[aqui](mercado-frescos.drawio)
[aqui](diagrama.png)
---

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
