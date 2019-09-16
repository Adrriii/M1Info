
close all;

figure('Name', 'parabole et sa derivee') 
hold on;

axis auto;
X = -100: 100;
Y = parabole(X);
plot(X, Y)


dY = diff(Y)./diff(X);
length(X)
length(dY)
% que fait la fonction diff?

% Comparer la courbe suivante :
plot(X(:, 1:length(X)-1),dY,'r')

% Avec celle-ci
%Yd = 2*X;
%plot(X, Yd, 'g')

% et conclure

hold off

figure('Name', 'animation parabole')

% en un point de la parabole, on affiche la tangente (le coefficient directeur est la derivee)
% comprendre comment on obtient cet affichage
hold on;

plot(X,Y)

for i=-80:20:80
    
    plot(i,parabole(i),'or');
    
    tg =2*i*10+parabole(i);
    
    plot([i;i+10],[parabole(i);tg],'r')
    
    pause(3);
end




