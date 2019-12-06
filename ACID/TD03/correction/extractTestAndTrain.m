function [Train,Test] = extractTestAndTrain(GT, sizeTrain)

    sizeGT= size(GT,1);
    
    Indices      = randperm(sizeGT);
    TrainIndices = Indices(1:sizeTrain);
    TestIndices  = Indices(sizeTrain+1:sizeGT);
        
    Train    = GT(TrainIndices,:);
    Test     = GT(TestIndices,:);
end

