% Alejandro Salmon Felix Diaz
% A01201954

% find positives in list
any_positive([C]):-
    C > 0.
any_positive([H|T]):-
    H > 0,
    true;
    any_positive(T).

% substitute elements in list
substitute(_,_,[],[]).
substitute(A,B,[H|T],[H|X]):-
    H \= A,
    substitute(A,B,T,X).
substitute(A,B,[A|T],[B|X]):-
    substitute(A,B,T,X).

% is member of list
miembro(X,[H|T]):-
    X == H;
    miembro(X,T).


% eliminate duplicates from list
eliminate_duplicates([],[]).
eliminate_duplicates([H|T],X):-
	miembro(H,T),
	eliminate_duplicates(T,X).
eliminate_duplicates([H|T],[H|X]):-
	not(miembro(H,T)),
	eliminate_duplicates(T,X).

% set inteersection
intersect([],_,[]):-
	!.
intersect([H|T], L, X) :-
    miembro(H, L),
    !,
    X = [H|R],
    intersect(T, L, R).	
intersect([_|T], L, R) :-
    intersect(T, L, R).

%invert list
invert(List,Inverted):-
	invierte(List,Inverted,[]).
invierte([],Inverted,Inverted).
invierte([H|T],Inverted,Acc):-
	invierte(T,Inverted,[H|Acc]).


%less than
less_than(_,[],[]).
less_than(X,[H|T],[H|R]):-
	H < X,
	less_than(X,T,R).
less_than(X,[_|T],R):-
	less_than(X,T,R).

%more than or equal
more_than(_,[],[]).
more_than(X,[H|T],[H|R]):-
	H >= X,
	more_than(X,T,R).
more_than(X,[_|T],R):-
	more_than(X,T,R).

% rotate list n number of times

% we use split/4 append/3 y length/2

len([],0).
len([_|T],X):-
	len(T,X2),
	X is X2 + 1.

appen([],List,List).
appen(List,[],List).
appen([H|T],List,[H|R]):-
	appen(T,List,R).


split([],_,[],[]).
split(List,0,List,[]).
split([H|T],1,[H],T).
split([H|T],N,[H|R1],R2):-	
	N > 1,
	N2 is N - 1,
	split(T,N2,R1,R2).


rotate(List,N,R):-
	N < 0,
	len(List,X),
	N2 is X + N,
	rotate(List,N2, R).
rotate(List,N,R):-
	split(List,N,W,Z),
	appen(Z,W,R).


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

road(placentia,genua).
road(genua,placentia).
road(placentia,ariminum).
road(ariminum,placentia).
road(genua,pisae).
road(pisae,genua).
road(ariminum,ancona).
road(ancona,ariminum).
road(ariminum,roma).
road(roma,ariminum).
road(pisae,roma).
road(roma,pisae).
road(genua,roma).
road(roma,genua).
road(ancona,castrum_truentinum).
road(castrum_truentinum,ancona).
road(castrum_truentinum,roma).
road(roma,castrum_truentinum).
road(ancona,roma).
road(ancona,roma).
road(capua,roma).
road(roma,capua).
road(brundisium,capua).
road(capua,brundisium).
road(messana,capua).
road(capua,messana).
road(rhegium,messana).
road(messana,rhegium).
road(catina,rhegium).
road(rhegium,catina).
road(syracusae,catina).
road(catina,syracusae).
road(lilibeum,messana).
road(messana,lilibeum).
 

member(X,[X|_]).
member(X,[_|T]):-
	member(X,T).

empty_stack([]).

member_stack(E,S):- 
	member(E,S).

stack(E,S,[E|S]).

pop([H|S],H,S).

%depth
go(Start,Goal):-
	empty_stack(Empty_been_list),
	stack(Start,Empty_been_list,Been_list),
	path(Start,Goal,Been_list).

%path(Goal,Goal,R,R).
path(Goal,Goal,Been_list):-
	reverse_print_stack(Been_list).

path(State,Goal,Been_list):-
	road(State,Next),
	not(member_stack(Next,Been_list)),
	stack(Next,Been_list,New_been_list),
	path(Next,Goal,New_been_list), !.

%to print
reverse_print_stack(S):-
	empty_stack(S).
reverse_print_stack(S):-
	stack(E,Rest,S),
	reverse_print_stack(Rest),
	write(E), nl.



