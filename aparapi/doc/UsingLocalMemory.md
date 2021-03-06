#UsingLocalMemory
*How to make use of local memory in a Kernel Updated Feb 28, 2012 by frost.g...@gmail.com*
##How to make use of new local memory feature
By default all primitive arrays accessed by an Aparapi Kernel is considered global. If we look at the generated code using -Dcom.amd.aparapi.enableShowGeneratedOpenCL=true we will see that primitive arrays (such as int buf[]) are mapped to __global pointers (such as __global int *buf) in OpenCL.

Although this makes Aparapi easy to use (especially to Java developers who are unfamiliar to tiered memory hierarchies), it does limit the ability of the 'power developer' wanting to extract more performance from Aparapi on the GPU.

This [page](http://www.amd.com/us/products/technologies/stream-technology/opencl/pages/opencl-intro.aspx?cmpid=cp_article_2_2010) from AMD's website shows the different types of memory that OpenCL programmers can exploit.

Global memory buffers in Aparapi (primitive Java arrays) are stored in host memory and are copied to Global memory (the RAM of the GPU card).

Local memory is 'closer' to the compute devices and not copied from the host memory, it is just allocated for use on the device. The use of local memory on OpenCL can lead to much more performant code as the cost of fetching from local memory is much lower.

Local memory is shared by all work item's (kernel instances) executing in the same group. This is why the use of local memory was deferred until we had a satisfactory mechanism for specifying a required group size.

Aparapi only supports local arrays, not scalers.

##How to define a primitive array as "local"
We have two ways define a local buffer. Either we can decorate the variable name with a _$local$ suffix (yes it is a valid identifier n Java).

    final int[] buffer = new int[1024]; // this is global accessable to all work items.
    final int[] buffer_$local$ = new int[1024]; // this is a local buffer 1024 int's shared across all work item's in a group

    Kernel k = new Kernel(){
        public void run(){
             // access buffer
             // access buffer_$local$
             localBarrier(); // allows all writes to buffer_$local$ to be synchronized across all work items in this group
             // ....
        }
    }
Alternatively (if defining inside the derived Kernel class - cannot be used via anonymous inner class pattern above!) we can can use the @Local annotation.

    final int[] buffer = new int[1024]; // this is global accessable to all work items.

    Kernel k = new Kernel(){
        @Local int[] localBuffer = new int[1024]; // this is a local buffer 1024 int's shared across all work item's in a group
        public void run(){
             // access buffer
             // access localBuffer
             localBarrier(); // allows all writes to localBuffer to be synchronized across all work items in this group
             // ....
        }
    }
##How do I know how big to make my local buffer?
This is where the new Range class helps.

If we create a Range using:

    Range rangeWithUndefinedGroupSize = Range.create(1024);
The Aparapi will pick a suitable group size. Generally this will be the highest factor of global size <= 256. So for a global size which is a power of two (and greater or equal to256 ;) ) the group size will be 256.

Normally the size a local buffer will be some ratio of the group size.

So if we needed 4 ints per group we might use a sequence such as.

    final int[] buffer = new int[8192]; // this is global accessable to all work items.
    final Range range = Range.create(buffer.length); // let the runtime pick the group size

    Kernel k = new Kernel(){
        @Local int[] localBuffer = new int[range.getLocalSize(0)*4]; // this is a local buffer containing 4 ints per work item in the group
        public void run(){
             // access buffer
             // access localBuffer
             localBarrier(); // allows all writes to localBuffer to be synchronized across all work items in this group
             // ....
        }
    }
Alternatively you can of course specify your own group size when you create the Range.

    final int[] buffer = new int[8192]; // this is global accessable to all work items.
    final Range range = Range.create(buffer.length,16); // we requested a group size of 16

    Kernel k = new Kernel(){
        @Local int[] localBuffer = new int[range.getLocalSize(0)*4]; // this is a local buffer containing 4 ints per work item in the group = 64 ints
        public void run(){
             // access buffer
             // access localBuffer
             localBarrier(); // allows all writes to localBuffer to be synchronized across all work items in this group
             // ....
        }
    }
##Using barriers
As we mentioned above local memory buffers are shared by all work items/kernels executing in the same group. However, to read a value written by another workitem we need to insert a local barrier.

A common pattern involves having each work item copying a value from global memory in local memory.

    Kernel k = new Kernel(){
        @Local int[] localBuffer = new int[range.getLocalSize(0)];
        public void run(){

             localBuffer[getLocalId(0)] = globalBuffer[getGlobalId(0)];
             localBarrier(); // after this all kernels can see the data copied by other workitems in this group
             // use localBuffer[0..getLocalSize(0)]
        }
    }
Without the barrier above, there is no guarantee that other work items will see mutations to localBuffer from other work items.

Caution regarding barriers
Barriers can be dangerous. It is up to the developer to ensure that all kernels execute the same # of calls to localBarrier(). Be very careful with conditional code (or code containing loops!), to ensure that each kernel executes the same number of calls to localBarrier().

The following kernel will deadlock!

    Kernel kernel = new Kernel(){
        public void run(){
             if (getGlobalId(0)>10){
                // ...
                localBarrier();
                // ...
             }
        }
    }
We need to make sure that all kernel's in a group execute the localBarrier(). So the following will work.

    Kernel kernel = new Kernel(){
        public void run(){
             if (getGlobalId(0)>10){
                // ...
                localBarrier();
                // ...
             }else{
                localBarrier();
             }

        }
    }
Of course if we have multiple calls to localBarrier() in the 'if' side of the if..then then we must match in the 'else'.

    Kernel kernel = new Kernel(){
        public void run(){
             if (getGlobalId(0)>10){
                // ...
                localBarrier();
                // ...
                localBarrier();
                // ...
             }else{
                localBarrier();
                localBarrier();
             }

        }
    }
With loops we must make sure that each kernel processes any loop the sam e # of times.

So the following is fine.

    Kernel kernel = new Kernel(){
        public void run(){
             for (int i=0; i< 10; i++){
                // ...
                localBarrier();
                // ...
             }
        }
    }
However the following will deadlock

    Kernel kernel = new Kernel(){
        public void run(){
             for (int i=0; i< getLocalId(0); i++){
                // ...
                localBarrier();
                // ...
             }
        }
    }
As a testament to how well we emulate OpenCL in JTP mode, this will also deadlock your kernel in JTP mode ;) so be careful.

Performance impact in JTP mode
Of course Java itself does not support local memory in any form. So any time code using local memory falls back to JTP mode we must expect a considerable performance degradation (try the NBody local example in JTP mode).

We do honor localBarrier() using Java's barrier from the new concurrency utils. However, Java's memory model does not require the use of a barrier to observe array changes across threads. So these barriers are basically just an expense.

I would recommend using local memory and barriers only if I am 90% sure the code will run on the GPU.

##Can I see some code?
I added a version of NBody example which uses local memory, the source can be found here.

[http://code.google.com/p/aparapi/source/browse/trunk/examples/nbody/src/com/amd/aparapi/examples/nbody/Local.java](http://code.google.com/p/aparapi/source/browse/trunk/examples/nbody/src/com/amd/aparapi/examples/nbody/Local.java)