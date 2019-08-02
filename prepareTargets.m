function [tempCell] = prepareTargets(target)
fid = fopen(target);
textData = textscan(fid, '%n%n',  'delimiter', ' ');
fclose(fid);
temp = textData{2};
tempCell = con2seq(temp');
end
