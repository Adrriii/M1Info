function [ y ] = droite2DVD( x , vd , p)
    y = p(2) + x * (vd(2) / vd(1));
end