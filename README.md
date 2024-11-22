
# Url-Shortener-EventoRocketseat

Este projeto é um encurtador de URLs construído com Java e utilizando os serviços da AWS, como **Lambda**, **S3** e **API Gateway**.

A ideia deste projeto é abstrair a preocupação com a infraestrutura, deixando que a AWS cuide de toda a execução e armazenamento dos dados.

## Tecnologias Utilizadas
- **Java 17** para desenvolvimento da aplicação. ![Java](https://img.shields.io/badge/Java-17-red)
- **AWS Lambda** para execução das funções de encurtamento de URLs. ![AWS Lambda](https://img.shields.io/badge/AWS_Lambda-orange)
- **Amazon S3** para armazenamento das URLs originais.  ![S3](https://img.shields.io/badge/S3-blue)
- **API Gateway** para criar as APIs de acesso à aplicação.

## Como Funciona

Este projeto permite que você envie uma URL original e receba uma versão encurtada dessa URL. O encurtador usa o **API Gateway** para receber as solicitações, e o **Lambda** para processar e retornar a URL encurtada. As URLs originais são armazenadas no **S3**, garantindo que os dados sejam persistidos de forma segura e escalável.

### Fluxo:
1. O usuário envia uma URL para a API.
2. A função Lambda processa a URL e cria um identificador único.
3. A URL original é armazenada no **S3** e associada ao identificador.
4. A URL encurtada é retornada ao usuário.
