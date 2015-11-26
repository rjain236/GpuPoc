package com.codefellas.analytics;

import com.amd.aparapi.Range;
import com.amd.aparapi.device.Device;
import com.amd.aparapi.device.OpenCLDevice;
import com.amd.aparapi.internal.kernel.*;
import com.amd.aparapi.opencl.OpenCL;
import com.amd.aparapi.opencl.OpenCL.Resource;

import java.io.*;

/**
 * Created by rjain236 on 26/11/15.
 */
public class MersenneTwister {
    @Resource("com/codefellas/analytics/cl/mersennetwister.cl") interface MersenneWithResource extends OpenCL<MersenneWithResource>{
        public MersenneWithResource mersennetwister_generator(//
                                          Range _range,//
                                          @GlobalReadWrite("numberRandVar") int numberRandVar,//
                                          @GlobalReadWrite("matrix_a") int[] matrix_a,//
                                          @GlobalReadWrite("mask_b") int[] mask_b,//
                                          @GlobalReadWrite("mask_c") int[] mask_c,//
                                          @GlobalReadWrite("seed") int[] seed,//
                                          @GlobalReadWrite("out") float[] out);
    }

    public static void main(String[] args) throws IOException {
        final int numberRandVar = 100;
        final Range range = Range.create(numberRandVar);

        final Device device = KernelManager.instance().bestDevice();

        if (device instanceof OpenCLDevice) {
            final OpenCLDevice openclDevice = (OpenCLDevice) device;
            final MersenneWithResource mersenne = openclDevice.bind(MersenneWithResource.class);

            int[] matrix_a= new int[100];
            int[] mask_b= new int[100];
            int[] mask_c= new int[100];
            int[] seed = new int[100];

            populateMersennetwisterParams(matrix_a,mask_b,mask_c,seed,numberRandVar);
            float[] out = new float[numberRandVar];

            mersenne.mersennetwister_generator(range, numberRandVar, matrix_a, mask_b, mask_c, seed, out);
            for (int i = 0; i < numberRandVar; i++) {
                System.out.println(out[i]);
            }
            mersenne.dispose();
        }
    }

    private static void populateMersennetwisterParams(int[] matrix_a, int[] mask_b, int[] mask_c, int[] seed, int numberRandVar) throws IOException {

        DataInputStream data_in = new DataInputStream(
                new BufferedInputStream(
                        new FileInputStream(new File("/Users/rjain236/Projects/FinTechGame/GpuPoc/hackathon/src/com/codefellas/data/MersenneTwister.dat"))));
        int count = 0;
        while(count<numberRandVar*4) {
            try {
                int t = data_in.readInt();//read 4 bytes
//                System.out.printf("%08X ",t);
                // change endianness "manually":
                t = (0x000000ff & (t>>24)) |
                        (0x0000ff00 & (t>> 8)) |
                        (0x00ff0000 & (t<< 8)) |
                        (0xff000000 & (t<<24));
//                System.out.printf("%08X", t);
                if(count%4==0)
                    matrix_a[count/4] = t;
                if(count%4==1)
                    mask_b[count/4] = t;
                if(count%4==2)
                    mask_c[count/4] = t;
                if(count%4==3)
                    seed[count/4] = 42;
                count++;
            }
            catch (java.io.EOFException eof) {
                break;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        data_in.close();
    }

}
