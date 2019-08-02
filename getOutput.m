function [output] = getOutput(input,target)
this = myNeuralNetworkFunction(createArray(input + 1),prepareTargets(target),input)
output = this{input}
end
