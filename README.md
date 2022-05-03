# IA-MummyMazeSolver
PROJETO – MUMMY MAZE SOLVER
1. Mummy Maze
O Mummy Maze é um jogo onde o herói é um caçador de tesouros. Enquanto busca por tesouros
perdidos, a personagem principal do jogo vai atravessando vários níveis onde tem de evitar ser
apanhado por inimigos e pisar armadilhas.

Em cada nível ou câmara está uma passagem para o nível ou câmara seguinte. Desta forma, o
objetivo do herói é deslocar-se para a zona de acesso ao próximo nível evitando quaisquer perigos
que possam ocorrer: ser morto por um inimigo ou cair numa armadilha. A zona de acesso ao nível
seguinte é a célula que tem uma escada adjacente (ver figura seguinte).

Este jogo é jogado por turnos, onde primeiro se move o Herói e em seguida se deslocam os
inimigos. Em cada turno, o Herói é sempre o primeiro a deslocar-se e os inimigos são sempre os
últimos, mesmo que aquele já tenha chegado à célula objetivo.

2. Elementos constituintes do jogo
Herói
O Herói consegue deslocar-se apenas uma casa de cada vez em cada turno. Isto é: em cada turno
o herói pode passar para a quadrícula imediatamente acima, abaixo, à esquerda ou à direita da
quadrícula onde se encontra, a não ser que haja uma parede a bloquear esse caminho. O Herói
pode ainda optar por não se movimentar durante o seu turno.
Múmia Branca
A múmia branca desloca-se até duas casas em cada turno. O objetivo da múmia é matar o Herói e
para isso vai tentando deslocar-se para a posição onde este está. A múmia branca primeiro procura
estar na mesma coluna onde se encontra o Herói e só depois é que tenta deslocar-se para a mesma
linha que este. Usando o exemplo anterior, caso o Herói não efetuasse nenhum movimento, a
movimentação da múmia seria duas células para a direita, de forma a ficar na mesma coluna que
o Herói (ver figura seguinte).

Caso o Herói não se volte a mover durante o seu turno, o movimento da múmia branca será o
seguinte:

Neste caso, a múmia branca desloca-se apenas uma casa: como já está na mesma coluna que o
Herói, tenta posicionar-se na mesma linha indo para cima; após o primeiro passo para cima, a
múmia branca encontra uma parede, o que a impede de se deslocar mais para cima.
Na situação ilustrada na figura abaixo, a múmia tenta deslocar-se primeiro para a esquerda mas
não consegue. Como ainda não realizou nenhum movimento, tenta de seguida aproximar-se pelas
linhas e desce uma casa. Como ainda lhe resta um movimento, tenta agora aproximar-se
novamente pelas colunas e move-se para a esquerda, matando o herói. Ou seja, a múmia tenta
sempre gastar os dois movimentos, dando sempre prioridade à aproximação pelas colunas.
Múmia Vermelha
Tal como a múmia branca, a múmia vermelha pode realizar até dois movimentos em cada turno.
A múmia vermelha procura primeiro estar na mesma linha que o herói e só depois é que se desloca
para a mesma coluna.
Escorpião
O escorpião desloca-se da mesma forma que a múmia branca, embora apenas se desloque uma
quadrícula por turno.
Chave
Sempre que o Herói passa pela quadrícula onde se encontra a chave, abre ou fecha uma porta. Se
a porta estiver fechada passa a estar aberta e vice-versa.

Porta
Quando está fechada, a porta funciona como uma parede: bloqueia os movimentos do Herói e
seus inimigos.
Armadilha
O Herói morre sempre que pisar uma armadilha. As armadilhas não têm efeito nos inimigos.
3. Como matar múmias
Em alguns níveis onde existam duas múmias pode ser necessário eliminar primeiro uma múmia
para que o nível possa ter solução. Caso o Herói consiga fazer com que duas múmias ocupem a
mesma casa, elas lutam entre si restando apenas uma múmia viva. A múmia morta desaparece do
nível.
4. Trabalho a Realizar
Pretende-se que seja desenvolvida uma aplicação que, recorrendo aos algoritmos de procura
implementados nas aulas práticas, seja capaz de jogar o jogo Mummy Maze. Para os algoritmos
de procura informados deverão ser formuladas pelo menos duas heurísticas distintas adequadas
ao problema. O programa deverá permitir ao utilizador escolher um determinado nível a resolver,
o algoritmo de procura e a heurística a utilizar (se aplicável).
Pretende-se que seja também realizado um estudo comparativo do desempenho dos vários
algoritmos de procura bem como das heurísticas que forem utilizadas na resolução dos problemas.
Nomeadamente, pretende-se estudar os seguintes aspetos:
▪ O desempenho dos algoritmos de procura não informados;
▪ O desempenho dos algoritmos de procura informados;
▪ O desempenho dos algoritmos de procura não informados versus o desempenho dos
algoritmos de procura informados;
▪ A qualidade das heurísticas utilizadas.

5. Representação dos estados
São muitos os níveis que constituem o jogo Mummy Maze. Neste projeto, o programa a
desenvolver deve estar preparado para jogar apenas com níveis com as dimensões iguais aos do
nível apresentado nas figuras anteriores: uma matriz de 6 x 6 células. No entanto, deverá ser
possível definir diferentes configurações para os níveis. Para isso, deverão ser utilizados ficheiros
de texto que permitam definir diferentes níveis: a posição inicial do Herói e dos inimigos, bem
como as paredes, chaves, portas, armadilhas e a posição do objetivo.
O conteúdo dos ficheiros de configuração deverá consistir numa matriz de 13 x 13 caracteres em
que cada carácter representa uma célula do nível, uma parede ou a saída. Os caracteres a utilizar
deverão ser os seguintes:
• '|' ou '–', para uma posição ocupada por uma parede;
• 'H', para o agente;
• '.', para uma posição vazia;
• 'S', para a localização da saída;
• 'M', para a múmia branca;
• 'V', para a múmia vermelha;
• 'A', para a armadilha;
• 'E', para o escorpião;
• 'C', para a chave;
• '=', para porta horizontal fechada;
• '_', para porta horizontal aberta;
• '”', para porta vertical fechada;
• “)”, para porta vertical aberta.


No sítio da cadeira, juntamente com o enunciado, está disponível um ficheiro denominado
niveis.zip onde se encontram alguns níveis a resolver já neste formato. Os estudantessão
encorajados a criar novos níveis e até a publicar os mesmos no fórum da unidade curricular
para que possam comparar diferentes soluções e heurísticas para o mesmo problema.
