
function [V] = CalculACP (Echantillon)
        M = cov(Echantillon);
        [VP, Lambda] = eig (M);
        % trier les valeurs propres de la plus grande a la plus petite
        [SortedLambda, indices] = sort(diag(Lambda), 'descend');
        % renvoyer les vecteurs propres dans le même ordre
        V = VP(:, indices);
endfunction



% on appelle d la dimension réduite 
% pour obtenir la matrice de projection il faut sélectionner 
% les d 1ers vecteurs du résultat de cette fonction avec :
% W = V(:, 1:d);