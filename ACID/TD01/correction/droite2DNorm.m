function [y] = droite2DNorm(x,n,p)
% calcul des points d'une droite étant donnes son vecteur normal n
% et un point de la droite p

vd(1) = n(2);
vd(2) = -n(1);

y = droite2DVd(x,vd,p);
end

