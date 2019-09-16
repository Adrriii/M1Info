function [] = animationDescenteSurface(pdep)
  
  hold on;
  
  X = -8 : 8;
  Y = -8 : 8;

  Y = Y';
  Z = func(X,Y);
  % Z = X.^2 - 3*X + 2 + Y.^2 + 7*Y + 12;

  surf(X,Y,Z);
  
  epsilon = 0.001; % critere d'arret
  pasMult = 1;

  delta = 0.1;
  pcurr = pdep + delta;
  pprec = pdep;


  plot3(pdep(1), pdep(2), func(pdep(1), pdep(2)), 'ob');
  % axis([-8 8 -8 8]);


  while(abs(pprec(1) - pcurr(1)) > epsilon && abs(pprec(2) - pcurr(2)) > epsilon)
      
      plot3(pcurr(1),pcurr(2),func(pcurr(1), pcurr(2)), 'ob');
      pprec = pcurr;
      
      xd = pcurr(1) * 2 - 3;
      yd = pcurr(2) * 2 + 7;
      
      pcurr(1) += -(xd/abs(xd)) * delta;
      pcurr(2) += -(yd/abs(yd)) * delta;
      
      delta *= pasMult;
      pause(0.001)
  end
  plot3(pcurr(1),pcurr(2),func(pcurr(1), pcurr(2)), 'ob');
  
  hold off;
end

function [ Z ] = func(X,Y)
  Z = (X-1).*(X-2)+(Y+3).*(Y+4);
end
