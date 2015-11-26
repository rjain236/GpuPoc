Purpose
=======
Standardize error handling and error messages.
If you don't give complete details we do a standardization on a best effort basis.
This means giving a standard error code where only error message is given etc. 

Helper classes to pretty print complete stack trace of exceptions. 

Assert class that does standard checks and creates validation exceptions. This also reduces number of lines of code from 6-7 to just a single line. 

FinException 
============
It's a runtime error:
1. throw in the function signature is therefore not required. 
2. Explicit checks must be made at the end points exposed to user e.g facade layer or struts action classes at a minimum.

ErrPrettyPrinter
================
1. use prettyPrintSimple to print message
2. use prettyPrint to get stacktrace as stringbuilder 
*(pass recursive for deep stack)
3. prettyPrintStr to get stacktrace as string 
*(pass recursive for deep stack)

FinAssert
=========
Does asserts like notNull and throws FinException. Less cluttered code for null checks etc

Usage
=====
For use in your project you need to create an enum class similar to SystemErrorMessage. It should implement the ErrorMessage Interface that guarantees that it contains both an error code and an error message. 

1. Error messages can be simple error messages like 
	"An error has occurred". 
2. Parameterized errors like 
	"No user by name {0} found in system {1}
   Use the setNumberedParameterMessage call for this and pass the parameter values as well. Example	
   ```
     FinException test009_23 = (new FinExceptionBuilder(MyExceptionClass.class)).setNumberedParameterMessage(ErrorMessageEnumTestData.NUMBER_ENUM_001.getErrorMsg(), oneItemList).build();
   ```
3. Named parameters like 	
	"No user by name ${:name} found in system ${:system}
	Use the setNamedParameterMessage call for this and pass the parameter values 
	```
	FinException test008_33 = (new FinExceptionBuilder(MyExceptionClass.class)).setNamedParameterMessage(ErrorMessageEnumTestData.NAMED_ENUM_001, oneItemMap).build();
	```

Error Pretty Printer Usage:

1. ErrPrettyPrint.prettyPrint(exception,true) gives complete stack trace
2. ErrPrettyPrint.prettyPrint(exception,false) gives top exception stack trace and root cause. 
3. ErrPrettyPrint.prettyPrintSimple(exception) prints only the error message. 

Assert usage

1. FinAssert.isNullOrEmptyText(str, "error message")
Have a look at this class to see how "static import" can make code look less ugly.
	
Testing customizations
======================
 * Extend FinExceptionTest for testing your own exceptions. Parameterized junit class so all you need to do is change the parameter to your own class. 
 * Extend SystemErrorMessageTest to do sanity test of your error enum like checking for duplicate error messages and codes. This again is a parameterized test case where you just need to change parameters to that of your own test enum.
* Extend FinExceptionBuilderTest  to ensure system is able to build your test exception class. Also a parameterized test case. This test class is also a reference point for behaviors of different combinations for the builder. 
  



