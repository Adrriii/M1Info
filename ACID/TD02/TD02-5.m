m = 40;
sizeNoise = 10;
x = rand(m,1).*50 + 5;
noise = rand(m,1) * sizeNoise;
pente = 0.8;
c =  20;
y = c + pente*x + noise;

pdep = [1 5];

hold on;
scatter(x,y);
axis([min(x) max(x) min(y) max(y)]);
t = findRegLin(pdep,x,y,m);
plot(x,linear(t(1),t(2),x));
hold off