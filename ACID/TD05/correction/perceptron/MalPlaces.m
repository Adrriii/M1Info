%Les exemples sont les colonnes de Y
% W : paramètres de la frontière linéaire = vecteur colonne
function [Ym] = MalPlaces (Y, W)
ind = find(W'*Y< 0);
Ym = Y (:, ind);
%endfunction
