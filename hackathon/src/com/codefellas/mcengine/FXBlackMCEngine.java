package com.codefellas.mcengine;

import com.amd.aparapi.Kernel;
import com.amd.aparapi.Range;
import com.amd.aparapi.device.Device;
import com.amd.aparapi.device.OpenCLDevice;
import com.amd.aparapi.internal.kernel.KernelManager;
import com.amd.aparapi.opencl.OpenCL;
import com.codefellas.common.math.random.RandomGenerator;
import com.codefellas.marketdata.MarketDataContainer;
import com.codefellas.payoffs.FxPayoff;
import junit.framework.Assert;
import org.threeten.bp.ZonedDateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 27/11/2015.
 */
public class FxBlackMcEngine extends FxMcEngine {

    MarketDataContainer marketDataContainer;

    public FxBlackMcEngine(int nDimensions, RandomGenerator randomGenerator
            , FxPayoff payoff, MarketDataContainer marketDataContainer) {
        super(marketDataContainer.getReferenceDate(), nDimensions, randomGenerator, payoff);
        this.marketDataContainer = marketDataContainer;
    }

//    @OpenCL.Resource("com/codefellas/analytics/cl/pathgen.cl") interface Pathgen extends OpenCL<Pathgen>{
//        public Pathgen pathgen(//
//                      Range _range,//
//                      @GlobalReadWrite("assetvalues") float[][][] assetvalues,
//                               @GlobalReadWrite("randomNumbers") float[][][] randomNumbers,
//                               @GlobalReadWrite("finalInitialStates") float[] finalInitialStates,
//                               @GlobalReadWrite("delT") float[] delT,
//                               @GlobalReadWrite("fwdVol") float[] fwdVol,
//                               @GlobalReadWrite("drift") float[] drift,
//                                @GlobalReadWrite("nDim") int[] nDim,
//                                @GlobalReadWrite("timeSt") int[] timeSt);
//
//    }



    @Override
    public float[][][] simulate(final int nPaths, float[] initialStates) throws Exception {
        final float fxRateToday = (float) marketDataContainer.getForwardFxRate(getReferenceDate(),payoff.getCurrencyPair()).doubleValue();
        if(initialStates.length ==1 && initialStates[0]==0 ) {
            //Assert.assertTrue("Only single assert simulation implemented", nDimensions ==1);
            initialStates[0] = fxRateToday;
        }else
            Assert.assertTrue("OIndependent dimensions and initial states must be of same size", nDimensions
                    ==initialStates.length);
        final float[] finalInitialStates = initialStates;
        final float[][][] randomNumbers = randomGenerator.getIndependentRandomVariables(nDimensions,nPaths,timeGrid.size());
        final float[][][] assetvalues = new float[nDimensions][nPaths][timeGrid.size()];
        final int timeSteps = timeGrid.size() -1;
        final float[] delT = new float[timeGrid.size()-1];
        final float[] fwdVol = new float[timeGrid.size()-1];
        final float[] drift = new float[timeGrid.size()-1];
        List<ZonedDateTime> dateGrid = new ArrayList<>(getDatesGrid());
        double prevFxRate = fxRateToday;
        for (int j = 1; j < timeGrid.size(); j++) {
            delT[j-1] = (float)(timeGrid.get(j)-timeGrid.get(j-1));
            fwdVol[j-1] = (float) marketDataContainer.getForwardVolatility(dateGrid.get(j - 1), dateGrid.get(j), payoff.getCurrencyPair());
            double nextFwd = marketDataContainer.getForwardFxRate(dateGrid.get(j),payoff.getCurrencyPair());
            drift[j-1] = (float) Math.log(nextFwd/prevFxRate);
            prevFxRate = nextFwd;
        }
        final int[] nDim = new int[1];
        final int[] timeSt = new int[1];
        final float[] result1d = new float[nDimensions*timeSteps*nPaths];
        final float[] random1d = new float[nDimensions*timeSteps*nPaths];
        int count =0;
        for(int i=0;i<nDimensions;i++){
            for (int j = 0; j < nPaths; j++) {
                for (int k = 0; k < timeSteps; k++) {
                    random1d[count] = randomNumbers[i][j][k];
                    count++;
                }
            }
        }

        nDim[0] = nDimensions;
        timeSt[0] = timeSteps;
//        final Device device = KernelManager.instance().bestDevice();
//        if (device instanceof OpenCLDevice) {
//            final OpenCLDevice openclDevice = (OpenCLDevice) device;
//
//            final Range range = Range.create(nPaths);
//
//            final Pathgen pathgen = openclDevice.bind(Pathgen.class);
//            pathgen.pathgen(range, assetvalues, randomNumbers, finalInitialStates, delT, fwdVol, drift,nDim,timeSt);
        Kernel kernel = new Kernel(){
            @Override public void run() {
            int gid = getGlobalId();

            for(int j = 0;j<nDimensions;j++) {
                for (int i = 0; i < timeSteps;) {
                    if (i == 0) {
//                        assetvalues[j][gid][i]=finalInitialStates[j];
                        result1d[i+timeSteps*(j+nDimensions*gid)] = finalInitialStates[j];
                        i++;
                        continue;
                    }
                    result1d[i+timeSteps*(j+nDimensions*gid)] = result1d[i-1+timeSteps*(j+nDimensions*gid)]
                            * exp((drift[i] - fwdVol[i] * fwdVol[i] / 2) * delT[i] + fwdVol[i] * sqrt(delT[i]) * random1d[i+timeSteps*(j+nDimensions*gid)]);//randomNumbers[nDimensions][gid][i])
                    i++;
                }
            }
            }
        };
        kernel.execute(Range.create(nPaths));
        for(int i=0;i<nPaths;i++){
            for (int j = 0; j < nDimensions; j++) {
                for (int k = 0; k < timeSteps; k++) {
                    assetvalues[j][i][k] = result1d[k+timeSteps*(j+nDimensions*i)];
                }
            }
        }

//            pathgen.dispose();
            return assetvalues;
//        }
//        return null;

    }

}
