function [ t1 ] = regLin1( x , y, m )
    t1 = ((m * sum(x.*y)) - sum(x)*sum(y)) / (m * sum(power(x,2)) - power(sum(x),2))
end