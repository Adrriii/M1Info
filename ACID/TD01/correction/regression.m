
% création des données

m = 40;
sizeNoise = 10;
x = rand(m,1).*50 + 5;
noise = rand(m,1) * sizeNoise;
pente = 0.8;
b =  20;
y = b + pente*x + noise;

% régression matlab

X = [ones(length(x),1), x];
droiteMatlab = regress(y, X)


% calcul direct

theta1 =  (m * sum(x.*y) - sum(x)*sum(y))/(m*sum(x.*x) - sum(x)*sum(x))
%theta0 = (sum(y) - theta1*sum(x))/m
% ou
theta0 = mean(y) - theta1*mean(x)

figure
hold on
plot(x, y, 'o')

xd = [0, 60];
yd = [droiteMatlab(1), (60*droiteMatlab(2)+ droiteMatlab (1))];
plot(xd , yd)

yd = [theta0, (60*theta1+ theta0)];
plot(xd , yd, 'xr--')
