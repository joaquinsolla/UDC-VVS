# Resolución dos tests
Autor: _Joaquín Solla Vázquez_

## Estrutura

Dentro de _src/test/java_ atópase o paquete _gal.udc.fic.nameserver_ que contén a súa vez 3 paquetes máis:
- _cliente_
- _servidor_
- _usuario_

 > (Cada paquete ten as clases test correspondentes ás clases en _src/java_).

## Funcionamento dos tests

Todos os tests están compostos da mesma forma: 
1. Créanse os obxectos necesarios.
2. Realízanse as operacións correspondentes.
3. Compróbanse os resultados nun _assertAll()_.

 > Algúns tests teñen varios _assertAll()_ xa que foi preciso facer operacións adicionais entre uns 
 > asserts e outros.

## Erros/anomalías atopados

Tras probar todo o código atopáronse varios erros ou anomalías no seu funcionamento. Lístase a contnuación 
todo o atopado:
- _Cliente_: Non se pode testear a clase ConfigurarDialog porque é privada, polo que é inaccesible dende os tests.
- _Usuario_: O método _autorizar()_ só devolve true consigo mesmo, é dicir, se o _id_ do Usuario a autorizar é igual ca o do Usuario que autoriza.
- _Grupo_: Un Grupo pode engadir como membro a outro Grupo, pero non pode autorizalo (os Grupos só autorizan Usuarios).
- _ServidorReal_: As excepcións _FileNotFoundException_ e _IOException_ son inaccesibles (o mesmo ca liña 59).
- _ServidorReal_: O método _obterMestre()_ sempre devolve null.
- _ProxyCache_: O método _obterMestre()_ sempre devolve null.
- _Log_: O método _obterMestre()_ sempre devolve null.
- _CadeaDeResponsabilidade_: Pódese resolver a chave dun mestre dunha CadeaDeResponsabilidade se o resultado de resolver á propia CadeaDeResponsabilidade é null.
- _Paquete Servidor_: Nalgunhas clases cando se chama ós métodos de obter nome cun nume nulo estes devolven un string "null" ou "null (Log)", mentres que noutras clases devólvese unha _NullPointerException_.

## Cobertura
#### Total:
| Clases | Métodos | Liñas |
| :---: | :---: | :---: |
| 70% (17/24) | 68% (56/82) | 63% (166/262) |

#### Por paquetes (dentro de _nameserver_):
| Subpaquete | Clases | Métodos | Liñas |
| :---:   | :---: | :---: | :---: |
| cliente | 57% (8/14) | 39% (16/41) | 51% (86/168) |
| servidor | 100% (6/6) | 100% (28/28) | 92% (61/66) |
| usuario | 100% (3/3) | 100% (12/12) | 100% (19/19) |

## Execución
#### Número de tests: 32 (Todos correctos)
#### Tempo total: 4min 16sec (aprox.)
