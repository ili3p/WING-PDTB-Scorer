Scorer for PDTB relations
=====================================
Requires JDK 1.6 or higher.

Score a system's performance on Connective, Semantic Class, Arg1 Span and Arg2 Span against gold standard annotations.

Usage
------------------

`java -jar scorer.jar predictedFileName expectedFileName semanticLevel [errorFileName outputFileName]`

 - predictedFileName - path to the file being scored
 - expectedFileName - path to the file containing the gold standard annotations
 - semanticLevel - semantic type level used to evaluate the annotations (1, 2 or 3)

Optional:

 - errorFileName - path to a file to print error messages to. The default value is "errors_[timestamp].txt"
 - outputFileName - path to a file to print results  to. The default value is to print to console.
	

Format
------------------
 Both annotations must be in PDTB pipe-delimited format where every relation is represented
 on a single line and values are delimited by the pipe symbol.
 There must be 48 columns, but certain values may be blank. 
 
 
 The following lists the column values.
 For precise definitions of the terms used, please consult the [PDTB 2.0 annotation manual](http://www.seas.upenn.edu/~pdtb/PDTBAPI/pdtb-annotation-manual.pdf).

 - Col  0: Relation type (Explicit/Implicit/AltLex/EntRel/NoRel)
 - Col  1: Section number (0-24)
 - Col  2: File number (0-99)
 - Col  3: Connective/AltLex SpanList (only for Explicit and AltLex)
 - Col  4: Connective/AltLex GornAddressList (only for Explicit and AltLex)
 - Col  5: Connective/AltLex RawText (only for Explicit and AltLex)
 - Col  6: String position (only for Implicit, EntRel and NoRel) 
 - Col  7: Sentence number (only for Implicit, EntRel and NoRel)
 - Col  8: ConnHead (only for Explicit)
 - Col  9: Conn1 (only for Implicit)
 - Col 10: Conn2 (only for Implicit)
 - Col 11: 1st Semantic Class  corresponding to ConnHead, Conn1 or AltLex span (only for Explicit, Implicit and AltLex)
 - Col 12: 2nd Semantic Class  corresponding to ConnHead, Conn1 or AltLex span (only for Explicit, Implicit and AltLex)
 - Col 13: 1st Semantic Class corresponding to Conn2 (only for Implicit)
 - Col 14: 2nd Semantic Class corresponding to Conn2 (only for Implicit)
 - Col 15: Relation-level attribution: Source (only for Explicit, Implicit and AltLex)
 - Col 16: Relation-level attribution: Type (only for Explicit, Implicit and AltLex)
 - Col 17: Relation-level attribution: Polarity (only for Explicit, Implicit and AltLex)
 - Col 18: Relation-level attribution: Determinacy (only for Explicit, Implicit and AltLex)
 - Col 19: Relation-level attribution: SpanList (only for Explicit, Implicit and AltLex)
 - Col 20: Relation-level attribution: GornAddressList (only for Explicit, Implicit and AltLex)
 - Col 21: Relation-level attribution: RawText (only for Explicit, Implicit and AltLex)
 - Col 22: Arg1 SpanList
 - Col 23: Arg1 GornAddress
 - Col 24: Arg1 RawText
 - Col 25: Arg1 attribution: Source (only for Explicit, Implicit and AltLex)
 - Col 26: Arg1 attribution: Type (only for Explicit, Implicit and AltLex)
 - Col 27: Arg1 attribution: Polarity (only for Explicit, Implicit and AltLex)
 - Col 28: Arg1 attribution: Determinacy (only for Explicit, Implicit and AltLex)
 - Col 29: Arg1 attribution: SpanList (only for Explicit, Implicit and AltLex)
 - Col 30: Arg1 attribution: GornAddressList (only for Explicit, Implicit and AltLex)
 - Col 31: Arg1 attribution: RawText (only for Explicit, Implicit and AltLex)
 - Col 32: Arg2 SpanList
 - Col 33: Arg2 GornAddress
 - Col 34: Arg2 RawText
 - Col 35: Arg2 attribution: Source (only for Explicit, Implicit and AltLex)
 - Col 36: Arg2 attribution: Type (only for Explicit, Implicit and AltLex)
 - Col 37: Arg2 attribution: Polarity (only for Explicit, Implicit and AltLex)
 - Col 38: Arg2 attribution: Determinacy (only for Explicit, Implicit and AltLex)
 - Col 39: Arg2 attribution: SpanList (only for Explicit, Implicit and AltLex)
 - Col 40: Arg2 attribution: GornAddressList (only for Explicit, Implicit and AltLex)
 - Col 41: Arg2 attribution: RawText (only for Explicit, Implicit and AltLex)
 - Col 42: Sup1 SpanList (only for Explicit, Implicit and AltLex)
 - Col 43: Sup1 GornAddress (only for Explicit, Implicit and AltLex)
 - Col 44: Sup1 RawText (only for Explicit, Implicit and AltLex)
 - Col 45: Sup2 SpanList (only for Explicit, Implicit and AltLex)
 - Col 46: Sup2 GornAddress (only for Explicit, Implicit and AltLex)
 - Col 47: Sup2 RawText (only for Explicit, Implicit and AltLex)

Example relation:

`Explicit|18|70|262..265|1,0|But|||but|||Comparison.Contrast||||Wr|Comm|Null|Null||||9..258|0|From a helicopter a thousand feet above Oakland after the second-deadliest earthquake in U.S. history, a scene of devastation emerges: a freeway crumbled into a concrete sandwich, hoses pumping water into once-fashionable apartments, abandoned autos|Inh|Null|Null|Null||||266..354|1,1;1,2;1,3|this quake wasn't the big one, the replay of 1906 that has been feared for so many years|Inh|Null|Null|Null|||||||||`

Version
------------------
Version: 0.1
Last update: 11-Nov-2014

Copyright notice and statement of copying permission
------------------
Copyright (C) 2014 WING, NUS.                                                                     
                                                                                                  
This program is free software: you can redistribute it and/or modify it under the terms of the    
GNU General Public License as published by the Free Software Foundation, either version 3 of the  
License, or (at your option) any later version.                                                   
                                                                                                  
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without 
even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU     
General Public License for more details.                                                          
                                                                                                  
You should have received a copy of the GNU General Public License along with this program. If     
not, see http://www.gnu.org/licenses/.                                                            
                                                                                                  