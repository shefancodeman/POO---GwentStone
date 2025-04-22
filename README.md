///Tema 0 - POO

/Scopul temei este crearea unui joc de carti de 2 jucatori ce combina Hearthstone si Gwent, fara interfata fizica. Acesta este jucat prin comenzi
date in JSON, dupa restrictiile fiecarui cardgame. Comenzile eronate vor returna mesaje de eroare. Jocul poate fi jucat de mai multe ori la rand,
programul tinand cont de scor si de terminarea fiecarui joc.

/Tema contine clase pentru carduri comune (cards/Card) si fiecare carte legendara (legendaries/Ripper, Miraj, etc) extinde clasa Card, cu o implementare
identica pentru Hero (clasa heroes/Hero e extinsa pt fiecare erou). Fiecare din cele 2 clase are un factory pattern pentru a genera cartile
legendare si eroii diferiti (polimorfism, override). Pentru fiecare din acestea a trebuit sa creez o functie care sa le transforme in ObjectNodes, 
din moment ce nu se printau bine in JSON.

/Am creat componentele (in components) necesare fiecarui meci, 2 clase ce vor avea liste de carti (Hand pentru mana jucatorilor, Deck
pentru deck-ul acestora). In acestea am implementat functiile de cardDraw si playCard pentru a nu incarca comenzile
mai mult decat trebuie. Apoi am creat si Board-ul, o matrice 4 pe 5 umpluta cu mai multe clase Card - care incepe initial goala,
dar pe care fiecare jucator poate plasa carti in functie de ID-ul lui (1 si 2 au randuri separate pe care pot plasa carti).
Am creat si functii pentru plasarea cartilor dupa cerinte, dar si pentru inlaturarea lor. Clasele Deck si Hand sunt apoi adaugate
la o clasa Player, din moement ce fiecare player are propria mana si propriul deck. Diferentiem playerii prin id - 1 si 2. Avem
si un boolean pentru a verifica a cui tura este, si un contor de mana initializat la 1.

/Avand toate componentele gata, am creat clasa Match (matche/Match) pentru a putea juca meciurile. Aceasta va avea 2 playeri(fiecare player
are deck-ul primit in constructor si mana lui goala), seed-ul (dupa care amestecam) si si startingPlayer-ul (primeste id-ul primului jucator).
Se joaca pe un singur board (avem un new Board), tinem cont de cate ture s-au jucat cu turnCounter si avem si un int care va incepe ca 0 -
winner (poate fi 1 sau 2, unele functii nu pot fi apelate cand avem deja un castigator). Apoi am facut ca main-ul de la etapa 1 SoundScape,
o functie ce preia o comanda cu match si ruleaza comanda apelata, aceasta returnand output-ul (numai ca in Match, pentru a tine main-ul liber).

/MatchList este clasa ce ne initializeaza fiecare Macth cu starting player, seed, si deckurile posibile (preia informatia din GameInput).
Aceasta clasa are startMatches, ce initializeaza cate Match-uri noi ni se cere la input (le da seed-ul si deck-urile selectate), apoi ruleaza actiune
cu actiune si adauga la output (fiecare meci are acelasi ArrayNode output la input).

//Actiunile in sine sunt grupate pe cele 4 categorii (cum erau grupate in cerinta, am zis asa reduc marimea fiecarei clase). 
Fiecare primeste Match-ul, pentru a putea modifica deck-urile, hand-urile, hp-ul jucatorilor, etc; si commandInput, pentru a stii parametrii actiunii rulate. Majoritatea returneaza
un ObjectNode ce va fi adaugat la ArrayNode output. Breakdown la fiecare:
/Debug - getCardinHand - ne returneaza un array de carduri din mana jucatorului selectat - preluam id-ul si adaugam in output
- getPlayerDeck - identic, tot luam id-ul, transformam cartile in Array de ObjectNode si adaugam la output
- getPlayerHero - preluam id-ul, transformam eroul in ObjectNode, si adaugam la output
- getPlayerTurn - avem boolean-ul din Player class care sa ne zica a cui tura este
- getPlayerMana - din nout, avem mana counter in PlayerClass, trebuie doar sa verificam id-ul
- getCardsOnTable - array de ArrayNodeuri, traversam FIECARE pozitie de pe table si adaugam cartile ce apar
- getCardAtPosition - avem in Board deja functie pentru a luat card-ul de la pozitie, doar luam x si y din CommandInput
- getFrozenCardsOnTable - fiecare card are un boolean daca este frozen sau nu, este getCardsOnTable cu un extra if
/Stats - fiecare comanda din stats doar adauga in ObjectNode o variabila aflata in Match
/Play - aici am grupat toate comenzile ce nu tin de atacat
- endTurn - complicat din moment ce eu eram obisnuit cu Hearthstone, unde turele nu functioneaza asa. Este o ciorba de if-uri
dar, verificam cine incepe meciul. Daca tura acestuia s-a terminat, trecem la urmatorul jucator. Daca si tura lui
s-a incheiat, stim ca ambii au jucat si le crestem mana (PANA IN 10!!! UITASEM SA FAC ASTA), fiecare trage o carte.
La finalul turei unui jucator apela functia untap (in functie de id-ul oferit reseteaza boolean-ul de frozen si
ready to attack al fiecare carti DE PE RANDURILE AFERENTE ID-ului)
- placeCard - in functie de tura cui este in prezent (avem boolean in Player class), acel jucator plaseaza din mana pe tabla
o creatura (avem functii in mana si board pentru asta). Dupa numele creaturii, stim pe ce row sa o plasam.
- useHeroAbility - aici apelam si printam mesajul output-ul pentru abilitatile eroilor - fiecare clasa de erou se ocupa cu
situatile in care nu poate sa-si casteze abilitatea, in afara de mana, caz pentru care verificam in functie.
Daca totul este bine, mesajul va fi null, altfel vom face ObjectNode si v-om printa eroarea in JSON.

    - Attack - cea mai lunga dintre ele cred, aceasta se ocupa de atacul carte la carte, erou la erou, sau abilitatile legendarelor. Stiu ca este
               foarte repetitiva si putea fi simplificata, dar wizzard-ul nu se descurca si nu am timp sa o scriu de mana.
             - attackCard - blueprint-ul pentru celelalte, aceasta verifica daca cardul exista, daca poate ataca (non frozen, non exhausted),
                            se uita pe randurile relevante pentru taunts sau daca incercam sa atacam carti de pe randurile noastre. Fiecare din
                            cazurile de mai sus are un mesaj special de eroare, salvat in message. Daca message ramane null, inseamna ca nu am dat
                            peste situatile astea, si nu printam nimic (daca exista mesajul, il adaugam in ObjectNode si returnam).
             - useAttackHero - IDENTIC, dar nu facem verificarea pentru atacarea propriului erou, din moment ce cartea noastra ataca automat
                                eroul advers. Facem o verificare daca am castigat si setam variabila winner din Match, printam si mesajul relevant.
             - cardUseAbility - cea mai urata functie din moment ce nu am rezolvat cazurile speciale ale cartilor, dar fiind identice cu atacul (in afara
                                de Disciple, el are cazurile rezolvate in propria functie ability) am decis sa folosesc functia de attack, doar cu ObjectNode
                                putin modificat. La fel, avem mesajele de eroare, daca acestea sunt nule atuncti merge.
