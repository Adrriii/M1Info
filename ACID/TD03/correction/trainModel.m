function [model] = trainModel (Train)

model.mu = mean(Train);
model.var = cov(Train);

end
