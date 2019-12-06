function [ratio] = computeError(Res, expectedLabel)
    nbErrors = sum(Res ~= expectedLabel);
    ratio = nbErrors / length(Res);
end

