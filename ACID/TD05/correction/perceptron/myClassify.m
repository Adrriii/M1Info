function  Res  = myClassify(test, W, label1, label2)
    testnorm = [ones(1, size(test, 2)); test];

    side = W'*testnorm ;
    
    for i=1:size(test,2)
      if side(i) >= 0
        Res(i) = label1;
      else
        Res(i) = label2;
      end
    end
 
    
end

