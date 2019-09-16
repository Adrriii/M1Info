
close all
epsilon = 0.001; % critere d'arret
attenuation = 3; % pas du gradient 1/log(attenuation)

x = 0:0.1:6;
y = valeurPolynome([1 -11 41 -61 30],x);

figure('Name', 'recherche du minimum sur une parabole')
hold on;
plot(x,y);

x0=5
xcurrent = 6;
xprec = x0;

plot(x0,valeurPolynome([1 -11 41 -61 30],x0),'ok','MarkerSize',20);
axis([0 6 -10 20]);


while(abs(xprec - xcurrent) > epsilon)
    
    plot(xcurrent,valeurPolynome([1 -11 41 -61 30],xcurrent),'ob');
   
    xprec  = xcurrent;
    nu = 0.1;
    attenuation = attenuation+1;
    xcurrent = xprec - nu*2*xprec;
    nu
    xcurrent
    pause(0.05)
end
plot(xcurrent,valeurPolynome([1 -11 41 -61 30],xcurrent),'xr', 'MarkerSize',20);

% 3.1 : 

% 3.2 : 

% 3.3 : 