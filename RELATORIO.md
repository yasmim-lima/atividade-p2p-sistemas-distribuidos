# Atividade 01 - Unidade 2
## Sistemas Distribuídos

### Avaliação de desempenho de transferência de arquivos

**Aluno:** Yasmim de Andrade Lima  
**Matrícula:** 202000047913

---

# 1. Introdução

Este trabalho apresenta uma avaliação de desempenho de diferentes arquiteturas
de comunicação distribuída aplicadas à transferência de arquivos.

Foram implementadas e avaliadas quatro abordagens:

- Cliente-servidor sequencial;
- Cliente-servidor concorrente;
- Cliente-servidor com limite de clientes utilizando Thread Pool;
- Comunicação P2P.

Os experimentos foram realizados utilizando diferentes tamanhos de dados e
quantidades de clientes, permitindo observar o comportamento das diferentes
estratégias de comunicação.

---

# 2. Objetivo

O objetivo da atividade é avaliar o desempenho da transferência de arquivos
utilizando diferentes arquiteturas de sistemas distribuídos.

Para isso, foram desenvolvidas implementações em Java utilizando comunicação
TCP através de sockets.

Os tempos de transferência foram coletados e analisados considerando:

- tempo mínimo;
- tempo médio;
- tempo máximo.

---

# 3. Ambiente de desenvolvimento

Os experimentos foram realizados no seguinte ambiente:

- Linguagem: Java 21;
- Gerenciamento do projeto: Maven;
- IDE: IntelliJ IDEA Community Edition;
- Sistema operacional: Windows;
- Controle de versão: Git;
- Repositório: GitHub;
- Comunicação: TCP/IP através de sockets;
- Ambiente de execução dos testes: localhost.

Os tempos foram medidos utilizando o método `System.nanoTime()` e convertidos
para milissegundos.

---

# 4. Arquiteturas implementadas

## 4.1 Cliente-servidor sequencial

Na arquitetura sequencial, o servidor recebe uma conexão e realiza toda a
transferência antes de atender o próximo cliente.

Dessa forma, apenas um cliente é atendido por vez.

A implementação utiliza `ServerSocket` para aguardar conexões e `Socket` para
realizar a transferência dos dados.

---

## 4.2 Cliente-servidor concorrente

Na arquitetura concorrente, cada cliente conectado é atendido por uma nova
thread.

Após aceitar uma conexão, o servidor cria uma `Thread` específica para aquela
transferência.

Essa abordagem permite que múltiplos clientes sejam atendidos
simultaneamente.

---

## 4.3 Cliente-servidor com Thread Pool

Nesta abordagem foi utilizado um Thread Pool através da classe
`ExecutorService` e do método `Executors.newFixedThreadPool()`.

O servidor possui um limite de quatro threads para atendimento dos clientes.

Essa estratégia evita a criação ilimitada de threads e permite controlar a
quantidade de clientes processados simultaneamente.

---

## 4.4 P2P

Na implementação P2P, um peer pode atuar como servidor e disponibilizar os
dados para outro peer através de uma conexão TCP.

O peer cliente realiza a conexão diretamente com o peer que disponibiliza os
dados.

Para os experimentos realizados neste trabalho, os peers foram executados na
mesma máquina utilizando `localhost`.

---

# 5. Metodologia

Os experimentos foram realizados utilizando arquivos de dados gerados durante
a execução da aplicação.

Foram utilizados os tamanhos de:

- 5 MB;
- 50 MB.

Para as arquiteturas cliente-servidor, foram realizados testes utilizando
dois clientes simultaneamente.

Para a implementação P2P, foi realizado um teste utilizando um peer cliente.

Durante cada execução foram registrados os tempos individuais de transferência.

A partir desses valores foram calculados:

- menor tempo de transferência;
- tempo médio;
- maior tempo de transferência.

---

# 6. Resultados dos experimentos

## 6.1 Transferências de 5 MB

| Arquitetura | Clientes | Mínimo (ms) | Média (ms) | Máximo (ms) |
|---|---:|---:|---:|---:|
| Cliente-servidor sequencial | 2 | 37,66 | 45,79 | 53,91 |
| Cliente-servidor concorrente | 2 | 56,32 | 56,63 | 56,93 |
| Thread Pool | 2 | 63,15 | 65,39 | 67,62 |
| P2P | 1 | 59,89 | 59,89 | 59,89 |

---

## 6.2 Transferências de 50 MB

| Arquitetura | Clientes | Mínimo (ms) | Média (ms) | Máximo (ms) |
|---|---:|---:|---:|---:|
| Cliente-servidor sequencial | 2 | 174,93 | 250,87 | 326,80 |
| Cliente-servidor concorrente | 2 | 202,47 | 215,59 | 228,71 |
| Thread Pool | 2 | 204,69 | 208,50 | 212,31 |
| P2P | 1 | 177,64 | 177,64 | 177,64 |

---

# 7. Análise dos resultados

Nos experimentos realizados com 5 MB, a arquitetura cliente-servidor
sequencial apresentou média de 45,79 ms para dois clientes. A arquitetura
concorrente apresentou média de 56,63 ms, enquanto o Thread Pool apresentou
média de 65,39 ms.

Com 50 MB, o comportamento apresentou diferenças mais significativas. A
arquitetura sequencial apresentou média de 250,87 ms, enquanto a arquitetura
concorrente apresentou média de 215,59 ms e o Thread Pool apresentou média de
208,50 ms.

Os resultados indicam que, para transferências maiores e com múltiplos
clientes, as abordagens concorrente e Thread Pool podem apresentar vantagens
por permitirem que diferentes conexões sejam processadas simultaneamente.

No entanto, os resultados obtidos não representam uma comparação absoluta
entre as arquiteturas, pois os testes foram realizados em uma única máquina,
utilizando `localhost`, e as condições de execução podem variar entre as
execuções.

Além disso, o teste P2P foi realizado com apenas um cliente, enquanto os
testes das demais arquiteturas foram realizados com dois clientes. Portanto,
os valores do P2P devem ser analisados como uma demonstração do funcionamento
da arquitetura, e não como uma comparação direta de desempenho com os demais
modelos.

---

# 8. Limitações

Uma das principais limitações dos experimentos foi a utilização de uma única
máquina para executar os processos.

Como as conexões foram realizadas através de `localhost`, não foi possível
avaliar fatores relacionados a uma rede física, como latência, largura de
banda, interferência e congestionamento de rede.

Outra limitação foi a diferença na quantidade de clientes utilizada no teste
P2P em relação às arquiteturas cliente-servidor.

Além disso, os experimentos representam execuções pontuais e podem apresentar
variações devido às condições de utilização do computador durante cada teste.

---

# 9. Conclusão

A atividade permitiu implementar e comparar diferentes estratégias de
comunicação utilizadas em sistemas distribuídos.

A implementação sequencial apresentou uma estrutura mais simples, atendendo
um cliente por vez. A abordagem concorrente permitiu o atendimento simultâneo
de múltiplos clientes através da criação de threads.

O uso de Thread Pool permitiu limitar a quantidade de threads utilizadas pelo
servidor, proporcionando maior controle sobre os recursos empregados no
processamento das conexões.

A implementação P2P demonstrou a possibilidade de realizar a transferência
diretamente entre peers através de uma conexão TCP.

Os experimentos demonstraram que o desempenho depende tanto do tamanho dos
dados quanto da estratégia utilizada para atender os clientes. Em especial,
os resultados de 50 MB mostraram melhor desempenho médio das abordagens
concorrente e Thread Pool em relação à abordagem sequencial para dois
clientes.

Apesar das limitações do ambiente experimental, o trabalho permitiu observar
na prática as diferenças entre processamento sequencial, concorrência,
controle de threads e comunicação entre peers.

---

# 10. Tecnologias utilizadas

- Java 21
- Maven
- TCP/IP
- Java Sockets
- Threads
- ExecutorService
- Thread Pool
- Git
- GitHub
- IntelliJ IDEA

---

# 11. Repositório

# 11. Repositório

O código-fonte, os resultados e a documentação da atividade estão disponíveis no repositório GitHub:

https://github.com/yasmim-lima/atividade-p2p-sistemas-distribuidos.git