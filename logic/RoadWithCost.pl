road(placentia,arminum, 1).
road(placentia,genua, 2).
road(arminum,ancona, 3).
road(ancona,castrumtrentium, 2).
road(castrumtrentium,roma, 1).
road(ancona,roma, 2).
road(arminum,roma, 1).
road(genua,pisae, 5).
road(pisae,roma, 1).
road(brundisium,capua, 2).
road(capua,roma, 3).
road(lilibeum,messana, 2).
road(syracusae,catina, 2).
road(catina,rhegium, 1).
road(catina,messana, 2).
road(messana,capua, 1).
road(a,b,9).
road(b,d,4).
road(b,c,1).
road(b,c,0).
road(c,d,2).
road(c,x,10).
road(x,b,3).
road(d,x,2).
road(d,b,1).

path(Origen,Destiny,Path, Cost) :-
  path_aux(Origen,Destiny,[Origen],Q,0,Cost),
  invert(Q,Path).

path_aux(Origen,Destiny,Path,[Destiny|Path],Acum, Cost) :-
  road(Origen,Destiny,LocalCost),
  Cost is Acum + LocalCost.

path_aux(Origen,Destiny,Visited,Path,Acum,Cost) :-
  road(Origen,NewOrigen,LocalCost),
  NewAcum is Acum + LocalCost,
  NewOrigen \== Destiny,
  \+member(NewOrigen,Visited),
  path_aux(NewOrigen,Destiny,[NewOrigen|Visited],Path,NewAcum,Cost).

dis_min( X, B, Z ) :-
  road( X, B, Z ),
  not((
        road( X, _, Other),
        Other < Z
     )).

invert([], []).

invert(Lst, X) :-
    invert_aux(Lst, [], X).

invert_aux([], R, R).

invert_aux([H|T], X, R):-
  invert_aux(T,[H|X], R).

member(X,[H|T]):-
    X == H;
    member(X,T).









