function [ y ] = droite2DVD( x , vd , p)
  a = vd(2) / vd(1);
  b = p(2) - a * p(1);
  y = a*x + b;
end