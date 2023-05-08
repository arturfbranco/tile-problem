# Problema dos triminós
#### Willian Weyh & Artur Branco

### Questões a serem respondidas:
#### a) Como você resolve esse problema, sabendo que deverá escolher a posição correta das peças para não deixar espaços descobertos? E como você resolve esse problema usando divisão e conquista?
Partimos do Objetivo de transformar um problema principal em problemas menores, sendo assim, dividimos um tabuleiro 
principal em quatro tabuleiros menores, entretando foi necessário adicionar em cada
quadrante um valor faltante para que todos ficassem igualmente dividos, isso também só se faz possível
pois contamos com um tabuleiro montado com uma potência de dois.

O programa recebe um valor n válido (tamanho do tabuleiro) e as coordenadas i e j da peça faltante.
A cada recursão do método fillMatrix(), a matriz é dividida em quatro quadrantes, e verifica-se em qual quadrante está localizada a peça faltante. A recursão é chamada novamente para preencher os outros quadrantes, passando a peça faltante para eles.
Esse processo continua até que a matriz seja reduzida a um tamanho de 2x2. Nesse ponto, a peça faltante é colocada manualmente na matriz, mantendo os outros valores preenchidos com números sequenciais.
Em seguida, os quadrantes preenchidos são mesclados novamente para formar a matriz final.
As peças faltantes "falsas" são preenchidas corretamente, deixando apenas a peça original faltante.

Essa abordagem divide e conquista permite preencher o tabuleiro de maneira eficiente, garantindo que a peça faltante esteja localizada corretamente na matriz final.

### Instruções para execução: 
A execução do programa é muito simples basta rodar a classe main pelas interfaces de inicialização de sua IDE ou rodar os seguintes comandos no terminal dentro da pasta src:
```
javac Main.java
java Main.class
```
