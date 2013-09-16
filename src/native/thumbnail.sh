#!/bin/bash

input=$1
output=$2
if [ $# -ne 2 ]
then
        echo 'ERROR: Wrong number of parameters! You must pass: inputFile outputFile'
        exit -1
else
        size=$(identify $input | awk '{print $3}')
        #echo 'size: ' $size
        width=$(echo $size | awk -F'x' '{print $1}')
        height=$(echo $size | awk -F'x' '{print $2}')
        #echo 'w: '$width' h: '$height
        if [ $width -lt $height ]
        then
                min=$width
        else
                min=$height
        fi
        #echo 'min: '$min
        dx=$(((width-min)/2))
        dy=$(((height-min)/2))
        #echo 'dx: '$dx' dy: '$dy
        command='convert '$input' -crop '$min'x'$min'+'$dx'+'$dy' -scale 128x128 '$output
        #echo 'command: '$command
        $command
        exit $?       
fi
