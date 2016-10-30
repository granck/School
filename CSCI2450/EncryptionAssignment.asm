CHAR BUFF[16] DUP ?

;loop while the end of the file has not been reached
{

BUFF = read.file("Filename"); psuedocode reading in part of text file(16 bytes of the file)

;loop continues for 16 bytes(since the key is 16 bytes) and XOR's each byte of BUFF with the corresponding byte of KEY
for(LENGTHOF(BUFF)
   XOR BUFF with KEY
;exiting for loop that writes the encrypted txt to a new file before repeating loop with next 16 bytes
   
 

   };end end of file loop


;BONUS POINTS if random function is "awesome"

;Note: key can be taken in as 16 byte array. D
