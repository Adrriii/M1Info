clear all
load ('data2_G5.mat')

%%%%%%%%%%%%% PERCECPTRON (correction TD5) %%%%%%%%%%%%%

% Les exemples sont les colonnes de Y
% W : paramètres de la frontière linéaire = vecteur colonne
function [Ym] = MalPlaces (Y, W)
  ind = find(W'*Y< 0);
  Ym = Y (:, ind);
endfunction

function [W] = Perceptron (C1, C2, W)
  % Recherche des échantillons mal placés relativement à la frontière W
  % on voudrait C1 côté positif et C2 côté négatif
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

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

function ret = classify(test, W, label1, label2)
  testnorm = [ones(1, size(test, 2)); test];
  
  W = W(1:3,:);
  side = W'*testnorm'; % Toujours le même problème ici.
  
  for i=1:size(test,2)
    if side(i) >= 0
      ret(i) = label1;
    else
      ret(i) = label2;
    end
  end
end

C1TestLen = size(Test1,1);
C2TestLen = size(Test2,1);
  
% La droite est approximativement y = 1.2x - 5
% j = -5 + (6/5)i
% 0 = -5 + (6/5)i - j
% 0*2 = -50 + 12i - 10j
W = [-50; 12; -10; 1];
[W] = Perceptron(Train1', Train2',W);
  
# Classifier (Perceptron)
r1 = classify(Test1, W, 1, 2);
r2 = classify(Test2, W, 2, 1);
  
# Comparer avec les attentes pour avoir l'erreur
error1 = sum(r1 ~= 1) / length(r1) * 100
error2 = sum(r2 ~= 2) / length(r2) * 100
  
error = (C1TestLen * error1 + C2TestLen * error2) / (C1TestLen + C2TestLen)