function [ y ] = droite2DNorm( x , n , p)
    y = p(2) + x * (n(2) / n(1));
end