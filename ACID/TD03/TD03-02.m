
close all;

data = load('VTSaumonBar.mat');

VTSaumon = data.VTSaumon;
VTBar = data.VTBar;

sizeVTSaumon = size(VTSaumon);
sizeVTBar = size(VTBar);


function [ C ] = myClassify ( subject , muBar, sigmaBar, muSaumon, sigmaSaumon ) 
  Ps = 2/3;
  Pb = 1/3;
  PxBar = mvnpdf(subject, muBar, sigmaBar);
  PxSaumon = mvnpdf(subject, muSaumon, sigmaSaumon);
  PBarx = PxBar .* Pb;
  PSaumonx = PxSaumon .* Ps;
  
  C = ["B","S"](1 + ( PBarx < PSaumonx ));
end
function [ E ] = computeError ( res, subject )
  E = nnz(~(res == subject)) / size(res)(2);
end

errB = [];
errS = [];

nbIter = 100;
sizeTrain = 400;
for i=1:nbIter
%% extraction de l’ensemble d’entrainement
TrainSaumonIndices = randperm(sizeVTSaumon, sizeTrain);
TrainBarIndices = randperm(sizeVTBar,sizeTrain);
TrainSaumon = VTSaumon(TrainSaumonIndices);
TrainBar = VTBar(TrainBarIndices);
%% entrainement
muSaumonTrain = mean(TrainSaumon);
sigmaSaumonTrain = var(TrainSaumon);
muBarTrain = mean(TrainBar);
sigmaBarTrain = var(TrainBar);
%% test - la fonction myClassify renvoie un vecteur de labels ("B" pour bar, "S" pour saumon)
TestBar = VTBar;
TestSaumon = VTSaumon;
ResBar = myClassify(TestBar, muBarTrain, sigmaBarTrain,muSaumonTrain, sigmaSaumonTrain);
ResSaumon = myClassify(TestSaumon, muBarTrain, sigmaBarTrain, muSaumonTrain, sigmaSaumonTrain);
%% récupération des erreurs
errB = [errB computeError(ResBar, "B")];
errS = [errS computeError(ResSaumon, "S")];
end;

ErrorBarMean = mean(errB)*100
ErrorSaumonMean = mean(errS)*100

hold on;
plot(errB);
plot(errS);
hold off;