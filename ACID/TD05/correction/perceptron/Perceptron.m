
function [W] = Perceptron (C1, C2, W)
% recherche des échantillons mal placés relativement à la frontière W
% on voudrait C1 coté positif et C2 coté négatif
C1norm = [ones(1, size(C1, 2)); C1];
C2norm = [ones(1, size(C2, 2)); C2];
Y = [C1norm -C2norm];
Ym = MalPlaces (Y, W);

% minimisation du critère (somme des distances des mal placés à la frontière)
eta = 0.1;
s = sum(Ym, 2);
Wnext =  W + eta * s;
k = 1;


%while(size(Ym, 2) != 0) => Pb si pas séparable!
while (norm(W-Wnext) > 0.001)
W = Wnext;
Ym = MalPlaces(Y, W);
s = sum(Ym, 2);
k = k+1;
Wnext = W + eta/k * s; % diviser par k ralentit l'évolution pour forcer l'arrêt
endwhile


endfunction
