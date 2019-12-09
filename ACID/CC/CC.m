clear all
load ('donnees.mat')

close all
figure(1)
hold on

axis equal

scatter3(C1(:, 1), C1(:,2),C1(:, 3))
scatter3(C2(:, 1), C2(:,2),C2(:, 3), 'r')
hold off

# 1.1

C1Taille = size(C1,1)
C1Dimension = size(C1,2)
C2Taille = size(C2,1)
C2Dimension = size(C2,2)

C1Moyenne = mean(C1)
C1Variance = var(C1)
C2Moyenne = mean(C2)
C2Variance = var(C2)

# 1.2 (Sans moindre carrés pour le moment)

function ret = classify(test, train1Mu, train1Cov, label1, train2Mu, train2Cov, label2, P1, P2)
  p2=mvnpdf(test,train1Mu, train1Cov) * P1;
  p1=mvnpdf(test,train2Mu, train2Cov) * P2;
  
  for i=1:size(test,1)
    if p1(i) <= p2(i)
      ret(i) = label1;
    else
      ret(i) = label2;
    end
  end
end

function ret = classifyPerceptron(test, W, label1, label2)
  testnorm = [ones(1, size(test, 2)); test];
  
  side = W'*testnorm ;
  
  for i=1:size(test,2)
    if side(i) >= 0
      ret(i) = label1;
    else
      ret(i) = label2;
    end
  end
end

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

# La consigne demande 100 itérations
rounds = 100;
trainCoeff = 0.1;

trainSizeC1 = abs(C1Taille * trainCoeff);
trainSizeC2 = abs(C2Taille * trainCoeff);

P1 = C1Taille / (C1Taille + C2Taille)
P2 = C2Taille / (C1Taille + C2Taille)

# Stocker l'erreur de chaque itération (MAP)
error1 = zeros(rounds,1);
error2 = zeros(rounds,1);

errorMAP = zeros(rounds,1);

# (Perceptron)
error1P = zeros(rounds,1);
error2P = zeros(rounds,1);

errorP = zeros(rounds,1);

for i=1:rounds
  # Trouver des indices aléatoires pour nos échantillons
  C1Rand = randperm(size(C1,1));
  C2Rand = randperm(size(C2,1));
  
  # Affecter les valeurs des indices aux échantillons
  C1Train = C1(C1Rand(1:trainSizeC1),:);
  C1Test = C1(C1Rand(trainSizeC1+1:C1Taille),:);
  
  C1TestLen = length(C1Test);
  
  C2Train = C2(C2Rand(1:trainSizeC2),:);
  C2Test = C2(C2Rand(trainSizeC2+1:C2Taille),:);
  
  C2TestLen = length(C2Test);
  
  % La droite est approximativement y = 1.5x - 10
  % j = -10 + (3/2)i
  % 0 = -10 + (3/2)i - j
  % 0*2 = -20 + 3i - 2j
  W = [-20; 3; -2; 1];
  [W] = Perceptron(C1Train', C2Train',W);
  
  # Classifier (MAP)
  r1 = classify(C1Test, mean(C1Train), cov(C1Train), 1, mean(C2Train), cov(C2Train), 2, P1, P2);
  r2 = classify(C2Test, mean(C2Train), cov(C2Train), 2, mean(C1Train), cov(C1Train), 1, P1, P2);
  
  rP1 = classifyPerceptron(C1Test, W, 1, 2);
  rP2 = classifyPerceptron(C2Test, W, 1, 2);
  
  # Comparer avec les attentes pour avoir le taux d'erreur
  error1(i) = sum(r1 ~= 1) / length(r1) * 100 ;
  error2(i) = sum(r2 ~= 2) / length(r2) * 100 ;
  errorP1(i) = sum(rP1 ~= 1) / length(rP1) * 100 ;
  errorP2(i) = sum(rP2 ~= 2) / length(rP2) * 100 ;
  
  errorMAP(i) = (C1TestLen * error1(i) + C2TestLen * error2(i)) / (C1TestLen + C2TestLen);
  errorP(i) = (C1TestLen * errorP1(i) + C2TestLen * errorP2(i)) / (C1TestLen + C2TestLen);
endfor

error1Mean = mean(error1)
error2Mean = mean(error2)
errorMAPMean = mean(errorMAP)

errorP1Mean = mean(errorP1)
errorP2Mean = mean(errorP2)
errorPMean = mean(errorP)

figure('Name', 'Error MAP');
plot(1:rounds, error1, 'g')
hold on
plot(1:rounds, error2)
plot(1:rounds, errorMAP, 'k')
hold off
ylim([0 100])

figure('Name', 'Error P');
plot(1:rounds, errorP1, 'g')
hold on
plot(1:rounds, errorP2)
plot(1:rounds, errorP, 'k')
hold off
ylim([0 100])