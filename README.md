spoke
=====

A program to calculate the required spoke length for a bicycle wheel, using measurements of the hub and rim.


# Installation instructions

This program has been written in Java, and can be compiled using the Apache Ant build system. Provided that
you have Ant installed, you can compile this program by running the command `ant` in your terminal.

The Java program is invoked by a shell script wrapper, which requires the Bash interpretter. Bash is
installed by default on all major Linux/UNIX distributions, and on Mac OSX. Microsoft Windows ports are also
available via Cygwin or MinGW.

**TODO**: Need an install script, to compile the program and copy `*.jar` file and shell script wrapper to
appropriate directories.


# Command Usage

To run the program, open a terminal and cd into the cloned workspace directory. Then, run the command:
`./spoke-length <path_to_measurement_data_file>`.

If you have masochistic tendencies, you can also run the Java interpretter directly from the command line,
but I suggest that you really **don't** want to do that; the shell script wrapper will save you a whole lot
of typing!


# Measurement Data File

This program calcuates the spoke length based on measurements of the hub and rim made by you, and stored in
a file which is read by the program. The file must be written in INI syntax, ie. `<name> = <value>` pairs,
where the equals sign, name and value may be padded with white space (excluding newlines). Anything between
a '#' or ';' character and the end of line is treated as a comment, and ignored. A whole line comment
consists of a line begining with a '#' or ';'.

The following measurements must be supplied in the data file:
- `hub.width`: the distance from the outside of one hub flange to the outside of the opposite flange.
- `flange.width`: the thickness of each flange; distance from outside to inside.
- `flange.diameter`: diameter of the circle of spoke holes in the hub flange centered around the axle.
- `hole.diameter`: diameter of each hole in the hub flange.
- `rim.diameter`: diameter of the rim; measured from the nipple seat on one side to the nipple seat on the
opposite side, accross where the wheel axle will be in the completed wheel.
- `spokes.count`: the number of spokes that will be used in the whole wheel.
- `crossings.count`: the number of spokes that each spoke crosses between the hub and the rim in a 
tangentially laced wheel. Set this to 0 for a radially spoked (0 cross) wheel.
