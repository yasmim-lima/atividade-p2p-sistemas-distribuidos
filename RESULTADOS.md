# Resultados dos Experimentos

## Ambiente

- Linguagem: Java 21
- Sistema operacional: Windows
- Ambiente de execução: localhost
- Arquiteturas avaliadas:
    - Cliente-servidor sequencial
    - Cliente-servidor concorrente
    - Cliente-servidor com Thread Pool
    - P2P

## Experimentos com 5 MB

| Arquitetura | Clientes | Mínimo (ms) | Média (ms) | Máximo (ms) |
|---|---:|---:|---:|---:|
| Sequencial | 2 | 37,66 | 45,79 | 53,91 |
| Concorrente | 2 | 56,32 | 56,63 | 56,93 |
| Thread Pool | 2 | 63,15 | 65,39 | 67,62 |
| P2P | 1 | 59,89 | 59,89 | 59,89 |

## Experimentos com 50 MB

| Arquitetura | Clientes | Mínimo (ms) | Média (ms) | Máximo (ms) |
|---|---:|---:|---:|---:|
| Sequencial | 2 | 174,93 | 250,87 | 326,80 |
| Concorrente | 2 | 202,47 | 215,59 | 228,71 |
| Thread Pool | 2 | 204,69 | 208,50 | 212,31 |
| P2P | 1 | 177,64 | 177,64 | 177,64 |

## Observações

Os testes foram realizados utilizando a interface de rede local
(localhost). Portanto, os resultados representam o comportamento das
implementações em uma mesma máquina.

O teste P2P foi realizado com um cliente, enquanto as demais arquiteturas
foram avaliadas com dois clientes. Dessa forma, os resultados do P2P não
devem ser considerados uma comparação direta de desempenho com as demais
arquiteturas.

Os tempos foram obtidos utilizando `System.nanoTime()` e convertidos para
milissegundos.