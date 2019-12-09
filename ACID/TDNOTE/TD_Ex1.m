clear all
load ('data1_G5.mat')

close all
figure(1)
hold on

axis equal

scatter3(C1(:, 1), C1(:,2),C1(:, 3))
scatter3(C2(:, 1), C2(:,2),C2(:, 3), 'r')
hold off

# Exercice 1.1
mean(C1)
mean(C2)

# Exercice 1.2
C1Taille = size(C1,1)
C2Taille = size(C2,1)

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

function ret = classifyACI(test, W, label1, label2)
  testnorm = [ones(1, size(test, 2)); test];
  
  %side = W'*testnorm; % Ne marche pas ?? Can't multiply 1x3 * 4501x3 ...
  side = W'*testnorm'; % Totalement faux, mais ça laisse le reste du code tourner...

  
  for i=1:size(test,2)
    if side(i) >= 0
      ret(i) = label1;
    else
      ret(i) = label2;
    end
  end
end

function [W] = CalculACI (C1, C2)
  Sw = cov(C1)+cov(C2);
  m1 = mean(C1);
  m2 = mean(C2);
  W = inv(Sw)*(m1-m2)';
endfunction

# La consigne demande 100 itérations
rounds = 100;

maxsize = 0; % 0 pour enlever la limite
trainCoeff = 0.1;

P1 = C1Taille / (C1Taille + C2Taille)
P2 = C2Taille / (C1Taille + C2Taille)

# Stocker l'erreur de chaque itération
error1 = zeros(rounds,1);
error2 = zeros(rounds,1);

error = zeros(rounds,1);

errora1 = zeros(rounds,1);
errora2 = zeros(rounds,1);

errora = zeros(rounds,1);


for i=1:rounds
  # Appliquer la taille maximale d'échantillon, si demandé
  if(maxsize > 0 && maxsize < C1Taille)
    C1Round = C1(1:maxsize,:);
    C1RoundTaille = maxsize;
  else
    C1Round = C1;
    C1RoundTaille = C1Taille;
  endif
    
  if(maxsize > 0 && maxsize < C2Taille)
    C2Round = C2(1:maxsize,:);
    C2RoundTaille = maxsize;
  else
    C2Round = C2;
    C2RoundTaille = C2Taille;
  endif
  
  # Trouver des indices aléatoires pour nos échantillons
  
  C1Rand = randperm(size(C1Round,1));
  C2Rand = randperm(size(C2Round,1));
  
  # Affecter les valeurs des indices aux échantillons

  trainSizeC1 = abs(C1RoundTaille * trainCoeff);
  trainSizeC2 = abs(C2RoundTaille * trainCoeff);
    
  C1Train = C1Round(C1Rand(1:trainSizeC1),:);
  C1Test = C1Round(C1Rand(trainSizeC1+1:C1RoundTaille),:);
  
  C1TestLen = length(C1Test);
  
  C2Train = C2Round(C2Rand(1:trainSizeC2),:);
  C2Test = C2Round(C2Rand(trainSizeC2+1:C2RoundTaille),:);
  
  C2TestLen = length(C2Test);
  
  W = CalculACI(C1, C2);
  
  # Classifier (MAP)
  r1 = classify(C1Test, mean(C1Train), cov(C1Train), 1, mean(C2Train), cov(C2Train), 2, P1, P2);
  r2 = classify(C2Test, mean(C2Train), cov(C2Train), 2, mean(C1Train), cov(C1Train), 1, P1, P2);
  
  # Classifier (ACI)
  ra1 = classifyACI(C1Test, W, 1, 2);
  ra2 = classifyACI(C2Test, W, 2, 1);
  
  # Comparer avec les attentes pour avoir le taux d'erreur
  error1(i) = sum(r1 ~= 1) / length(r1) * 100 ;
  error2(i) = sum(r2 ~= 2) / length(r2) * 100 ;
  
  error(i) = (C1TestLen * error1(i) + C2TestLen * error2(i)) / (C1TestLen + C2TestLen);
  
  errora1(i) = sum(ra1 ~= 1) / length(ra1) * 100 ;
  errora2(i) = sum(ra2 ~= 2) / length(ra2) * 100 ;
  
  errora(i) = (C1TestLen * errora1(i) + C2TestLen * errora2(i)) / (C1TestLen + C2TestLen);
endfor

error1Mean = mean(error1)
error2Mean = mean(error2)
errorMean = mean(error)

figure('Name', 'Error MAP');
plot(1:rounds, error1, 'g')
hold on
plot(1:rounds, error2)
plot(1:rounds, error, 'k')
hold off
ylim([0 rounds])

errora1Mean = mean(errora1)
errora2Mean = mean(errora2)
erroraMean = mean(errora)

figure('Name', 'Error MAP');
plot(1:rounds, errora1, 'g')
hold on
plot(1:rounds, errora2)
plot(1:rounds, errora, 'k')
hold off
ylim([0 rounds])