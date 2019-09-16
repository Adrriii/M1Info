function [ coeffDeriv ] = derivPoly( coeff )
  indexes = (length(coeff)-1:-1:0);
  coeff = coeff .* indexes;
  coeffDeriv = coeff(:, 1:length(coeff) - 1);
end