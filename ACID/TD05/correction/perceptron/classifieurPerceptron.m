clear all;
close all;
clc

load('donnees.mat')


%visualisation des echantillons 
figure(1)
axis equal
scatter(C1(:, 1), C1(:, 2));
hold on
scatter(C2(:, 1), C2(:, 2) ,'g');
hold off


nbIter   = 100;
sizeTrain = 100;
errorC1 = zeros(nbIter,1);
errorC2 = zeros(nbIter, 1);
error = zeros(nbIter, 1);

for i=1:nbIter
        [TrainC1,TestC1] = extractTestAndTrain(C1, sizeTrain);
        [TrainC2,TestC2] = extractTestAndTrain(C2, sizeTrain);
        W = [6; 1; -3];
        [W] = Perceptron(TrainC1', TrainC2',W);
              
        ResC1 = myClassify(TestC1',W, "1", "2");
        ResC2 = myClassify(TestC2',W, "1", "2");
        C1Error = computeError(ResC1, "1");
        C2Error = computeError(ResC2, "2");
        errorC1(i) = C1Error*100;
        errorC2(i) = C2Error*100;
        error(i) = (length(TestC1) * errorC1(i) + length(TestC2) *  errorC2(i) )/ (length(TestC1) + length(TestC2));
          
end;

MeanErrorC2    = mean(errorC2)
MeanErrorC1 = mean(errorC1)
MeanError = mean(error)

figure(2);

plot(1:nbIter, errorC1, 'g')
hold on
plot(1:nbIter, errorC2)
plot(1:nbIter, error, 'k')
plot(1:nbIter, MeanError*ones(size(error)), 'k-')
hold off
legend('Error for C1 class', 'Error for C2 class', 'Mean Error')
title('Errors')
xlabel('Iteration')
ylabel('Error in %')
ylim([0 30])

