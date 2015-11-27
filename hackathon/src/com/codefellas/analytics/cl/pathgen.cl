__kernel void pathgen( __global float *** assetvalues, __global float *** randomNumbers,  __global float * finalInitialStates, __global float * delT,
 __global float * fwdVol ,  __global float * drift,  __global uint * nDimensions,  __global uint  * nPaths,  __global uint * timeSteps){

   const size_t gid = get_global_id(0);

           for(uint j = 0;j< *nDimensions;j++) {
                   for (uint i = 0; i < *timeSteps;i++) {
                       if (i == 0) {
                           assetvalues[j][gid][i]=finalInitialStates[j];
                       }
                       else {
                        assetvalues[j][gid][i] = assetvalues[j][gid][i - 1] * exp((drift[i] - fwdVol[i] * fwdVol[i] / 2) * delT[i] + fwdVol[i] * sqrt(delT[i]) * randomNumbers[nDimensions][gid][i]);
                       }

                   }
               }
}

