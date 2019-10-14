data = load('VTSaumonBar.mat');

sizeVTSaumon = size(data.VTSaumon);
sizeVTBar = size(data.VTBar);


hold on;


hist(data.VTSaumon)
hist(data.VTBar)



hold off;