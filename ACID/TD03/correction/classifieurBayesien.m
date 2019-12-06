close all;
clear all;
clc;

load('VTSaumonBar.mat')
sizeVTSaumon = size(VTSaumon, 1)
sizeVTBar= size(VTBar, 1)


%probas a priori
pBar=sizeVTBar/(sizeVTBar+sizeVTSaumon);
pSaumon=sizeVTSaumon/(sizeVTBar+sizeVTSaumon);


figure(1)
hist(VTSaumon);
hold on
hist(VTBar);

nbIter   = 100;
sizeTrain = 400;
errorBarML = zeros(nbIter,1);
errorSaumonML = zeros(nbIter, 1);
errorML = zeros(nbIter, 1);
errorBarMAP = zeros(nbIter,1);
errorSaumonMAP = zeros(nbIter, 1);
errorMAP = zeros(nbIter, 1);

for i=1:nbIter
    
        [TrainSaumon,TestSaumon] = extractTestAndTrain(VTSaumon, sizeTrain);
        [TrainBar, TestBar] = extractTestAndTrain(VTBar, sizeTrain);
        [modelSaumon] = trainModel(TrainSaumon);
        [modelBar] = trainModel(TrainBar);
       
        % ML (maximum de vraissemblance)
        ResBar    =    myClassify(TestBar,modelBar, modelSaumon, 0.5, 0.5, "B", "S");
        ResSaumon =    myClassify(TestSaumon,modelBar, modelSaumon, 0.5, 0.5, "B", "S");
        SaumonError = computeError(ResSaumon, "S");
        BarError = computeError(ResBar, "B");
        
        errorSaumonML(i) = SaumonError*100;
        errorBarML(i) = BarError*100;
        errorML(i) = (length(TestSaumon) * errorSaumonML(i) + length(TestBar) *  errorBarML(i) )/ (length(TestSaumon) + length(TestBar));
        
        % MAP (utilisation des proba a priori)
        ResBar    =    myClassify(TestBar,modelBar, modelSaumon, pBar, pSaumon, "B", "S");
        ResSaumon =    myClassify(TestSaumon,modelBar, modelSaumon, pBar, pSaumon, "B", "S");
        SaumonError = computeError(ResSaumon, "S");
        BarError = computeError(ResBar, "B");
        errorSaumonMAP(i) = SaumonError*100;
        errorBarMAP(i) = BarError*100;
        errorMAP(i) = (length(TestSaumon) * errorSaumonMAP(i) + length(TestBar) *  errorBarMAP(i) )/ (length(TestSaumon) + length(TestBar));
   
end;

MeanErrorBarML    = mean(errorBarML)
MeanErrorSaumonML = mean(errorSaumonML)
MeanErrorML = mean(errorML)

MeanErrorBarMAP    = mean(errorBarMAP)
MeanErrorSaumonMAP = mean(errorSaumonMAP)
MeanErrorMAP = mean(errorMAP)


figure('Name', 'ML');
plot(1:nbIter, errorBarML, 'g')
hold on
plot(1:nbIter, errorSaumonML)
plot(1:nbIter, errorML, 'k')
hold off
ylim([0 100])

figure('Name', 'MAP');
plot(1:nbIter, errorBarMAP, 'g')
hold on
plot(1:nbIter, errorSaumonMAP)
plot(1:nbIter, errorMAP, 'k')
hold off
ylim([0 100])

