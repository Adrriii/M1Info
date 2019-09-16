

close all
epsilon = 0.001; % critere d'arret
attenuation = 3; % pas du gradient 1/log(attenuation)

x = -300:2:500;
y = parabole(x);

figure('Name', 'recherche du minimum sur une parabole')
hold on;
plot(x,y);

%x0 = randi(250,1)
x0=250
xcurrent = x0 - 3*x0;
xprec = x0;

plot(x0,parabole(x0),'ok','MarkerSize',20);


while(abs(xprec - xcurrent) > epsilon)
    
    plot(xcurrent,parabole(xcurrent),'ob');
   
    xprec  = xcurrent;
    nu = 0.1;
    attenuation = attenuation+1;
    xcurrent = xprec - nu*2*xprec;
    nu
    xcurrent
    pause(0.1)
end
plot(xcurrent,parabole(xcurrent),'xr', 'MarkerSize',20);


% Exercice 2

% 4. Comment évolue la variable nu au fur et à mesure des itérations ?

% Elle se rapproche de 0 plus ou moins rapidement selon l'atténuation

% 5. Testez en modifiant la variable nu par une valeur constante inférieur à 1, par exemple nu = 0.1.
% Que se passe-t-il si elle est fixée à 1 ? Est-ce spécifique à la fonction choisie ?

% La variable nu sert à déterminer quelle distance parcourir relativement à l'ensemble X, dans la direction
% souhaitée pour continuer la recherche. En restant à la valeur 1, la distance parcourue est constante, ce qui
% cause la stagnation de la recharche étant donné qu'au maximum 3 points seront atteints. 
% (Le point de départ, un point à "distance 1" du point de départ, puis un aller retour avec le point suivant)
% 
% La variable nu est atténuée pour préciser la recherche. En la mettant à 0.1 de manière constante, l'algorithme
% utilisera un pas faible.
