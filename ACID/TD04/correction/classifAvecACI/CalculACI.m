
function [W] = CalculACI (C1, C2)
  Sw = cov(C1)+cov(C2);
  m1 = mean(C1);
  m2 = mean(C2);
  W = inv(Sw)*(m1-m2)';

endfunction
