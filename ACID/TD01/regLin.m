function [ y ] = regLin( x , y , m )
    t1 = regLin1(x, y, m);
    t0 = regLin0(x, y, m, t1);
    y = t1 * x + t0;
end