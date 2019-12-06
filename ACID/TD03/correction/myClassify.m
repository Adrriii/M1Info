function  Res  = myClassify(test, model1, model2, p1, p2, label1, label2)

    prob2  = mvnpdf(test,model1.mu, model1.var)*p1;
    prob1  = mvnpdf(test,model2.mu, model2.var)*p2;
    
    for i=1:size(test,1)
      if prob2(i) >= prob1(i)
        Res(i) = label1;
      else
        Res(i) = label2;
      end
    end
    
end

