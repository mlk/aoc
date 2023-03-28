# part1.opl

OPL as far as I can tell does not support complex data structures(1), including multidimensional arrays. 
But it does have a built-in database. I thought it would be cool to use this as the datastore.

The search only supports text fields, so I had to loop over the entire datastore each step. The application also
crashed when the file exceeded 500ish kb. I'm fairly sure the crash could be worked around, but it was always a bit of a
stupid solution, so in Attempt 2 I faked a multidimensional array by multiplying one of the dimensions. 

1) There is direct memory access but that looks lik work.