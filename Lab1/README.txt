Design Decision:

Design of code is incremental. You can find the updated feature in each increasing # of code as expected.
Assignment is based on original sample code provided by Instructor but with some modification as proceed
to advance expectations.

In 4th part of code (SockClient4 & SockServer4), InputStream of socket is wrapped around DataInputStream
to solve the problem of data marshalling.

In 5th part of code (SockClient5 & SockServer5), XML files are used to maintain the state of server.
[createXML,readXML,modifyXML,saveXML,isClientExist] methods are used to perform the calculation and
saveXML method is made ThreadSafe using Synchronised keyword to avoid write conflict in multithreaded
scenario.

In 6th part of code (SockClient6 & SockServer6), ThreadPool is introduced to increase the throughput of code.
Conditional Synchronised block per client is added to increase efficiency of code. For example,
if two threads are trying to write to same client, code should be synchronised and write operation is to be
perform for different clients code can execute in non-synchronised way. The design of code is based on these
conditions to increase throughput in synchronised environment.

-------------------------**********************************************-----------------------------------
Commands to Run the code:

1. java SockClient1 int1

	where int1 represents byte size integer in range of -128 to 127
	Example :
		I/P : java SockClient1 10
		O/P : Result is 10

2. java SockClient2 [int1 OR char]

	where int1 represents byte size integer in range of -128 to 127
		  char represents sum reset command 'r'
	Example :
		I/P : java SockClient2 10
		O/P : Result is 10

		I/P : java SockClient2 r
		O/P : Result is 0

3. java SockClient3 char [int1  int2]

	where int1 represent the client ID
		  int2 represents byte size integer in range of -128 to 127
		  char 't' represents running sum command and 'r' sum reset command
	Example :
		I/P : java SockClient3 t 10 10
		O/P : Result is 10

		I/P : java SockClient3 r 10
		O/P : Result is 0

4. java SockClient4 char [int1  int2]

	where int1 represent the client ID
		  int2 represents byte size integer in range of -2147483648 to +2147483647
		  char 't' represents running sum command and 'r' sum reset command
	Example :
		I/P : java SockClient4 t 1 12312312
		O/P : Result is 12312312

		I/P : java SockClient4 r 10
		O/P : Result is 0

5. java SockClient5 char [int1  int2]

	where int1 represent the client ID
		  int2 represents byte size integer in range of -2147483648 to +2147483647
		  char 't' represents running sum command and 'r' sum reset command
		  XML file generated is "totals.xml" in current folder
	Example :
		I/P : java  SockClient5 t 1 10
		O/P : Result is 10

		I/P : java SockClient5 r 2
		O/P : Client 2 Does Not Exist

6. java SockClient6 char [int1  int2]

	where int1 represent the client ID
		  int2 represents byte size integer in range of -2147483648 to +2147483647
		  char 't' represents running sum command and 'r' sum reset command
		  XML file generated is "totals.xml" in current folder
	Example :
		I/P1 : java  SockServer6 6000
		I/P2 : java  SockClient6 t 1 10
		I/P3 : java  SockClient6 t 1 20
		O/P2 : Result is 10
		O/P3 : Result is 30

		I/P : java SockClient4 r 10
		O/P : Result is 0
