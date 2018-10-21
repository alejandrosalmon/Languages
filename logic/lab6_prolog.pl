 % Lab 3 - Prolog  Alejandro Salmon A01201954


% --------------DEPTH FIRST LIMITED dfls ----------%

% Based on = https://www.cs.unm.edu/~luger/ai-final/code/PROLOG.depth.html

dfls(Start,Goal,X):-
	path(Start,Goal,[Start|[]],X).

%if the current node is same as goal ... *looks at next nodes*  "Meh, whatever"
path(Goal,Goal, Visited,_):-
	print_reverse_list(Visited).
	
path(Node,Goal,Visited, X):-
	NewX is X -1,
	NewX >= 0,
	child(Node,Child),
	not_member(Child, Visited),
	visited(Child,Visited,New_Visited),
	path(Child,Goal,New_Visited,NewX), 
	!.

% append child to visited
visited(Child,Visited,[Child|Visited]).

print_reverse_list([Head|[]]):-
	write(Head).

print_reverse_list([Head|Rest]):-
	print_reverse_list(Rest),
	write(','),
	write(Head).

not_member(_,[]).
not_member(X,[H|T]):-
 	X \= H,
 	not_member(X,T).

child(5,2).
child(5,1).
child(2,3).
child(1,7).
child(1,9).
child(7,10).

% ---------------------QUICKSORT----------------%

quick_sort([Pivot|List], Sorted):-
	divide(List, Pivot, Left,Right),
	quick_sort(Left, LSorted),
	quick_sort(Right, RSorted),
	append_list(LSorted, [Pivot|RSorted], Sorted),
	%cuts
	!.

% If list is empty returns empty list
quick_sort([],[]).

%If  N <= Pivot, N goes into Left
divide([N|Rest], Pivot, [N|Left], Right):-
	N =< Pivot,
	divide(Rest, Pivot, Left,Right).

% If N >= Pivot, N goes into Right list
divide([N|Rest], Pivot, Left, [N|Right]):-
	N > Pivot,
	divide(Rest, Pivot, Left,Right).

%If the list to divide is empty, return empty listss
divide([], _, [],[]).

% classic append
append_list([N|Rest], List2, [N|Result]):-
	append_list(Rest, List2, Result).
append_list([], List2, List2).
