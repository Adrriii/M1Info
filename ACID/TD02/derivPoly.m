function [ coeffDeriv ] = derivPoly( coeff )
  coeffDeriv = coeff(:, 1:length(coeff) - 1);
end