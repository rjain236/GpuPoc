/*
Copyright (c) 2010-2011, Advanced Micro Devices, Inc.
All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the
following conditions are met:

Redistributions of source code must retain the above copyright notice, this list of conditions and the following
disclaimer. 

Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following
disclaimer in the documentation and/or other materials provided with the distribution. 

Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products
derived from this software without specific prior written permission. 

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE 
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

If you use the software (in whole or in part), you shall adhere to all applicable U.S., European, and other export
laws, including but not limited to the U.S. Export Administration Regulations ("EAR"), (15 C.F.R. Sections 730 through
774), and E.U. Council Regulation (EC) No 1334/2000 of 22 June 2000.  Further, pursuant to Section 740.6 of the EAR,
you hereby certify that, except pursuant to a license granted by the United States Department of Commerce Bureau of 
Industry and Security or as otherwise permitted pursuant to a License Exception under the U.S. Export Administration 
Regulations ("EAR"), you will not (1) export, re-export or release to a national of a country in Country Groups D:1,
E:1 or E:2 any restricted technology, software, or source code you receive hereunder, or (2) export to Country Groups
D:1, E:1 or E:2 the direct product of such technology or software, if such foreign produced direct product is subject
to national security controls as identified on the Commerce Control List (currently found in Supplement 1 to Part 774
of EAR).  For the most current Country Group listings, or for additional information about the EAR or your obligations
under those regulations, please refer to the U.S. Bureau of Industry and Security's website at http://www.bis.doc.gov/. 

*/

package com.amd.aparapi.sample.add;

import com.amd.aparapi.Kernel;
import com.amd.aparapi.Range;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class Main{

      final float x;
      float y;

      public Main(float x, float y){
         this.x = x;
         this.y = y;
      }

      public float add(){
         return (float)(Math.cos(Math.sin(x)) + Math.sin(Math.cos(y)));
      }

   public static void main(String[] _args) {

      final int size = 1000000;

      final float[] a = new float[size];
      final float[] b = new float[size];
      final Main[] p = new Main[size];

      for (int i = 0; i < size; i++) {
         a[i] = (float) (Math.random() * 100);
         b[i] = (float) (Math.random() * 100);
         p[i] = new Main(a[i],b[i]);
      }

      final float[] sum = new float[size];

      Kernel kernel = new Kernel(){
         @Override public void run() {
            int gid = getGlobalId();
            sum[gid] =(float)((float)cos((float) sin(a[gid])) + (float)sin((float) cos(b[gid])));
         }
      };

      kernel.execute(Range.create(1));
      System.out.println("Device = " + kernel.getTargetDevice().getShortDescription());

      final long startTime = System.nanoTime();
      kernel.execute(Range.create(size));
      final long endTime = System.nanoTime();
      System.out.println(endTime - startTime);

      final long startTime1 = System.nanoTime();
      for(int i =0; i<size;i++)
         sum[i] =(float)((float)Math.cos((float) Math.sin(a[i])) + (float)Math.sin((float) Math.cos(b[i])));

      final long endTime1 = System.nanoTime();
      System.out.println(endTime1-startTime1);

//
//      for (int i = 0; i < size; i++) {
//         System.out.printf("%6.2f + %6.2f = %8.2f\n", a[i], b[i], sum[i]);
//      }

      kernel.dispose();
   }

}
