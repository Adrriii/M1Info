function [ t ] = findRegLin( pdep, x, y, m )
  epsilon = 0.1;
  
  t0d = 1;
  t1d = 0.01;
  
  x_ = [ones(1,size(x,1)); x'];
  
  t = [pdep(1), pdep(2)];
  
  err0 = critereDer0(t,x_,y);
  err1 = critereDer1(t,x_,y);
  
  critprev = critere(t(1),t(2),x,y,m);
  critold = critprev;
    
  t(1) += -err0/abs(err0) * t0d;
  t(2) += -err1/abs(err1) * t1d;
    
  critnext = critere(t(1),t(2),x,y,m);

  p = plot(x,linear(t(1),t(2),x));
  while(critnext != critold)
    delete(p);
  
    err0 = critereDer0(t,x_,y);
    err1 = critereDer1(t,x_,y);
    
    t(1) += -err0/abs(err0) * t0d;
    t(2) += -err1/abs(err1) * t1d;
      
    p = plot(x,linear(t(1),t(2),x));
    pause(0.01);
    
    critold = critprev;
    critprev = critnext;
    critnext = critere(t(1),t(2),x,y,m);
  end
  critnext
end

function [ r ] = critere(t0, t1, x, y, m)
  r = (1 / m) * sum(power(t0 + t1.*x-y,2));
end

function [ y ] = critereDer0(t0, x, y)
  y = sum(2 .* (t0 * x .- y'));
end

function [ y ] = critereDer1(t1, x, y)
  y = sum(2 .* x(2, :) .* (t1 * x .- y'));
end

function [ y ] = linear(t0,t1,x)
  y = t1 * x + t0;
end
