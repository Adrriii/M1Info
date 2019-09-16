
close all
epsilon = 0.001; % critere d'arret
pasMult = 0.95;

x = 0:0.1:6;
y = valeurPolynome([1 -11 41 -61 30],x);

yd = valeurPolynome(derivPoly([1 -11 41 -61 30]),x);

figure('Name', 'recherche du minimum sur un polynome')
hold on;
plot(x,y);
plot(x,yd);

x0=0
delta = 0.1;
xcurrent = x0 + delta;
xprec = x0;


plot(x0,valeurPolynome([1 -11 41 -61 30],x0),'ok','MarkerSize',20);
axis([0 6 -10 20]);


while(abs(xprec - xcurrent) > epsilon)
    
    plot(xcurrent,valeurPolynome([1 -11 41 -61 30],xcurrent),'ob');
    xprec = xcurrent;
    
    d = valeurPolynome(derivPoly([1 -11 41 -61 30]),xcurrent);
    xcurrent += -(d/abs(d)) * delta;
    
    delta *= pasMult;
    pause(0.001)
end
plot(xcurrent,valeurPolynome([1 -11 41 -61 30],xcurrent),'xr', 'MarkerSize',20);

% 3.1 : https://puu.sh/EhNMG/35e7a0ef6b.png

% 3.2 : https://puu.sh/EhNMR/8aea3d13da.png

% 3.3 : Cet algorithme risque de s'arrêter si la recherche prend trop de temps. 
% Dans ce cas, il faut augmenter pasMult avec 1 étant le maximum
% Attention, si le pas est trop haut, l'algorithme marchera toujours mais sera plus lent.