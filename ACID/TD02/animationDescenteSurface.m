function [] = animationDescenteSurface(pdep)
  
  hold on;
  
  X = -8 : 8;
  Y = -8 : 8;
  
  coeffsX = [2,3,2];
  coeffsY = [2,7,12];

  Y = Y';
  Z = func(coeffsX,coeffsY,X,Y);
  % Z = X.^2 - 3*X + 2 + Y.^2 + 7*Y + 12;

  surf(X,Y,Z);
  
  epsilon = 0.001; % critere d'arret
  pasMult = 1;

  delta = 0.1;
  pcurr = pdep + delta;
  pprec = pdep;

  plot3(pdep(1), pdep(2), func(coeffsX,coeffsY,pdep(1), pdep(2)), 'ok');
  % axis([-8 8 -8 8]);
  
  xd = valeurPolynome(derivPoly(coeffsX),pcurr(1)) * delta;
  yd = valeurPolynome(derivPoly(coeffsY),pcurr(2)) * delta;


  while(xd > epsilon && yd > epsilon)
      
      plot3(pcurr(1),pcurr(2),func(coeffsX,coeffsY,pdep(1), pdep(2)), 'ob');
      pprec = pcurr;
      
      xd = valeurPolynome(derivPoly(coeffsX),pcurr(1)) * delta;
      yd = valeurPolynome(derivPoly(coeffsY),pcurr(2)) * delta;
      
      pcurr(1) += -(xd/abs(xd)) * delta;
      pcurr(2) += -(yd/abs(yd)) * delta;
      
      pause(0.001)
      
  end
  plot3(pcurr(1),pcurr(2),func(coeffsX,coeffsY,pdep(1), pdep(2)), 'ox');
  pcurr(1)
  pcurr(2)
  
  hold off;
end

function [ Z ] = func(coeffX,coeffY,X,Y)
  Z = valeurPolynome(coeffX,X) + valeurPolynome(coeffY,Y);
end

function [ Z ] = funcder(coeffX, coeffY, X,Y)
  Z = valeurPolynome(derivPoly(coeffX),X) + valeurPolynome(derivPoly(coeffY),Y);
end
