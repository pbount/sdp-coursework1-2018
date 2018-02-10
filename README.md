### sdp-coursework1-2018
#Assignment One
###Simple Machine Language (SML)
####Software Design and Programming and Software and Programming III
_Spring Term 2018_

The  sample  code  mentioned  in  the  text  can  be  found  on
the  module  repository. This coursework can be completed in
Java, Kotlin, or Scala only.
The aim of this assignment is to give you practice with 
subclasses,  modifying existing code, and the use of 
reflection.

#####The problem
You will write an interpreter for a simple machine language —
SML. The general form of a machine language instruction is

`label instruction register-list`

where:
* **label**: is the label for the line. Other instructions 
might “jump” to that label. 
* **instruction** : Is the actual instruction.
In SML, there are instructions for adding, multiplying and 
so on, for storing and retrieving integers, and for 
conditionally branching to other labels (like an if state-
ment). 
* **register-list**: is the list of registers that the instruction manipulates.
Registers are simple,  integer,  storage areas in computer memory,  much like vari-
ables.  In SML, there are 32 registers, numbered 0, 1, . . . , 31.

SML has the following instructions: