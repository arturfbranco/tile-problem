# Problema dos triminós
#### Willian Weyh & Artur Branco

### Questões a serem respondidas:
#### a) Como você resolve esse problema, sabendo que deverá escolher a posição correta das peças para não deixar espaços descobertos? E como você resolve esse problema usando divisão e conquista?
Partimos do Objetivo de transformar um problema principal em problemas menores, sendo assim, dividimos um tabuleiro 
principal em quatro tabuleiros menores, entretando foi necessário adicionar em cada
quadrante um valor faltante para que todos ficassem igualmente dividos, isso também só se faz possível
pois contamos com um tabuleiro montado com uma potência de dois.

O programa recebe um valor n válido (tamanho do tabuleiro) e as coordenadas i e j da posição proibida.
A cada recursão do método fillMatrix(), a matriz é dividida em quatro sub-matrizes, e verifica-se em qual destas está localizada a posição a não ser preenchida. De forma a tornar possível dividir o problema original em 4 sub-problemas iguais, cada uma das sub-matrizes que não aquela com a posição proibida recebem artificialmente uma posição. Essas novas 3 posições são posicionadas no centro da matriz original, de forma que as 3 em conjunto formem um novo triminó. Esse processo continua até que a matriz seja reduzida a um tamanho de 2x2. Nesse ponto, o triminó é adicionado manualmente na matriz, respeitando a posição proibida.
Em seguida, os quadrantes preenchidos são mesclados novamente para formar a matriz final.
As peças faltantes "falsas" são preenchidas corretamente, deixando apenas a peça original faltante.

Essa abordagem divide e conquista permite preencher o tabuleiro de maneira eficiente, garantindo que a peça faltante esteja localizada corretamente na matriz final.

### Instruções para execução do programa Java (algoritmo final): 
A execução do programa é muito simples basta rodar a classe main pelas interfaces de inicialização de sua IDE ou rodar os seguintes comandos no terminal dentro da pasta src:
```
javac Main.java
java Main.class
```

### Instruções para execução do programa Typescript:

```
npx tsc index.ts
node index.js
```
Por o programa Typescript ter se tratado de uma PoC realizada para projetar a estrutura do algoritmo implementado em Java na sequência, este código trata-se apenas do algoritmo puro, sem atenção para os meios de comunicação com o usuário (input e output no terminal). Dessa forma, é exigido que o tamanho do lado da matriz, e as coordenadas i e j da posição proibida sejam informadas diretamente na linha 179 do arquivo index.ts.
