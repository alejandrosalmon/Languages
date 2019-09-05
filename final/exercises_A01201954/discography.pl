
% album(song,album).

album(another_one_bites_the_dust,the_game).
album(arboria,flash_gordon).


% year(album, year).

year(flash_gordon, 1980).
year(the_game, 1980).
year(prueba,1980).
year(esta_no, 1890).


get_songs(Album,Songs):-
	album(Songs,Album),
	get_songs(Album,Songs).


%song_same_year(X,Y).

song_same_year(X,Y):-
	year(A,Z),
	year(B,Z),
	album(X,A),
	album(Y,B),
	song_same_year(X,Y).


all_songs_from(Y,A):-
	year(Album, Y),
	get_songs(Album,NewA),
	all_songs_from(Y,NewA).



appen([],List,List).
appen(List,[],List).
appen([H|T],List,[H|R]):-
	appen(T,List,R).