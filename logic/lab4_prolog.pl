%Alejandro Salmón
% A01201954

hobby(juan,kaggle).

hobby(luis,hack).

hobby(elena,tennis).

hobby(midori,videogame).

hobby(simon,sail).

hobby(simon,kaggle).

hobby(laura,hack).

hobby(hans,videogame).

 

compatible(P,N):-

    hobby(P,O),

    hobby(N,O).

 

road(placentia,genua).

road(placentia,ariminum).

road(genua,pisae).

road(ariminum,ancona).

road(ariminum,roma).

road(pisae,roma).

road(genua,roma).

road(ancona,castrum_truentinum).

road(castrum_truentinum,roma).

road(ancona,roma).

road(capua,roma).

road(brundisium,capua).

road(messana,capua).

road(rhegium,messana).

road(catina,rhegium).

road(syracusae,catina).

road(lilibeum,messana).

 

can_get_to(X,Y):-

    road(X,Y);

    road(X,Z),

    can_get_to(Z,Y).

%can_get_to(syracusae,roma)

 

size(X,Y,C):-

    road(X,Y),

    C is 1;

    road(X,Z),

    size(Z,Y,CC),

    C is CC+1 .

%size(syracusae,roma,C).

min2(A,B,Z):-
    A>B,
    Z is A;
    B>A,
    Z is B.

min(A,B,C,Z):-
    A>B,
    A>C,
    Z is A;
    B>A,
    B>C,
    Z is B;
    C > A,
    C > B,
    Z is C;
    A = B,
    B = C, 
    Z is A.





gcd(0,B,B):-
    !.
gcd(A,0,A):-
    !.
gcd(A,A,A):-
    !.
gcd(A,B,Z):-
    B>A,
    B2 is B - A,
    gcd(A,B2,Z).
gcd(A,B,Z):-
    B<A,
    A2 is A - B,
    gcd(A2,B,Z).
%gcd(36,12,Z).