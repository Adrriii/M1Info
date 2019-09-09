function [ t0 ] = regLin0(x , y , m , t1 )
    t0 = (sum(y) - t1 * sum(x)) / m
end