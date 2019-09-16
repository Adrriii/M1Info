close all;

% Échauffement

% figure('Name', 'parabole et sa derivee') 
% hold on;

% axis auto;
% X = -100: 100;
% Y = parabole(X);
% plot(X, Y)

% dY = diff(Y)./diff(X);
% length(X)
% length(dY)
% que fait la fonction diff?
% la fonction diff retourne une liste avec, pour chaque interval entre deux valeurs,
% la différence entre ces deux là. Par exemple : 
% si dans Y à l'index 20 on a la valeur 100, et à l'index 21 la valeur 250
% on aura la valeur 150 à l'index 20 du retour de la fonction diff(Y)

% Comparer la courbe suivante :
% plot(X(:, 1:length(X)-1),dY,'r')

% Avec celle-ci
% Yd = 2*X;
% plot(X, Yd, 'g')

% et conclure
% Ces deux droites sont identiques.

% hold off

% Exercice 1
figure('Name', 'Polynome + dérivée') 
hold on;

coeff = [ 2 200 1000 ];
X = -100 : 100;
Y = valeurPolynome(coeff,X);
Yd = valeurPolynome(derivPoly(coeff),X);

plot(X, Y);
plot(X, Yd);

hold off;

% figure('Name', 'animation parabole')

% en un point de la parabole, on affiche la tangente (le coefficient directeur est la derivee)
% comprendre comment on obtient cet affichage
% hold on;

% plot(X,Y)

% for i=-80:20:80
    
%     plot(i,parabole(i),'or');
    
%     tg =2*i*10+parabole(i);
    
%     plot([i;i+10],[parabole(i);tg],'r')
    
%     pause(3);
% end




