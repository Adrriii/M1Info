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

# La consigne demande 100 itérations
rounds = 100;
trainCoeff = 0.1;

trainSizeC1 = abs(C1Taille * trainCoeff);
trainSizeC2 = abs(C2Taille * trainCoeff);

P1 = C1Taille / (C1Taille + C2Taille)
P2 = C2Taille / (C1Taille + C2Taille)

# Stocker l'erreur de chaque itération
error1 = zeros(rounds,1);
error2 = zeros(rounds,1);

errorMAP = zeros(rounds,1);

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
  
  # Classifier
  r1 = classify(C1Test, mean(C1Train), cov(C1Train), 1, mean(C2Train), cov(C2Train), 2, P1, P2);
  r2 = classify(C2Test, mean(C2Train), cov(C2Train), 2, mean(C1Train), cov(C1Train), 1, P1, P2);
  
  # Comparer avec les attentes pour avoir le taux d'erreur
  error1(i) = sum(r1 ~= 1) / length(r1) * 100 ;
  error2(i) = sum(r2 ~= 2) / length(r2) * 100 ;
  
  errorMAP(i) = (C1TestLen * error1(i) + C2TestLen * error2(i)) / (C1TestLen + C2TestLen);
endfor

error1Mean = mean(error1)
error2Mean = mean(error2)
errorMAPMean = mean(errorMAP)

figure('Name', 'Error MAP');
plot(1:rounds, error1, 'g')
hold on
plot(1:rounds, error2)
plot(1:rounds, errorMAP, 'k')
hold off
ylim([0 100])