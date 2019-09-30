# n coefficients from x^n to x^0
function [ v ] = valeurPolynome (coeff, X)
  indexes = (length(coeff)-1:-1:0);
  v = arrayfun(@(x) sum(arrayfun(@(i) x^i * coeff(length(indexes)-i),indexes)),X);
end
