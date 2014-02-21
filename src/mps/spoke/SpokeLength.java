package mps.spoke;

import java.io.*;


/**
 *  A java program to calculate the length of spoke required to build a wheel to the measurements
 *  contained in an INI format data file, given as a param to this program. The file must contain
 *  measurements for:
 *      - hub.width -- distance from flange to flange, outside.
 *      - flange.width
 *      - flange.diameter -- diameter of the hole circle on the hub flange.
 *      - rim.diameter -- diameter of the nipple seats on the rim.
 *      - hole.diameter -- diameter of each spoke hole in the flange.
 *      - spokes.count
 *      - crossings.count
 *
 *  In the case that a hub has two different flange diameters, you may prepare two data files, and
 *  calculate the spoke lengths for each side separately.
 */
public class SpokeLength
{
    private DataFile specs;

    /**
     *  Initialise the spoke length calculator given the path to the INI file that contains the hub and
     *  rim specs.
     */
        public
    SpokeLength (String specPath)
    {
        try
        {
            specs = new DataFile (specPath);
        }
        catch (IOException e)
        {
            System.err.println ("Could not access data file:");
            e.printStackTrace ();
        }
    }

    /**
     *  Program to calculate spoke lengths given rim and hub specs stored in an INI formate data
     *  file. This program should be given a single parameter, being the path to that data file.
     */
        public static void
    main (String [] args)
    {
        SpokeLength calculator;
        double length;

        if (args.length != 1)
        {
            System.err.println ("SpokeLength: Invalid command line parameters.");
            printUsage ();
            return;
        }

        try
        {
            calculator = new SpokeLength (args [0]);
            calculator.printInputDataSummary ();
            length = calculator.calculateLength ();
            System.out.println ("Spoke Length: " + length);
        }
        catch (NoSuchKeyException nsk)
        {
            System.err.println ("Incomplete rim/hub data. You need to supply the" +
                    "following measurement:");
            nsk.printMissingKey ();
        }
    }

    /**
     *  Print a summary of the rim and hub specs on stdout.
     */
        private void
    printInputDataSummary () throws NoSuchKeyException
    {
        System.out.println ("Summary of hub specs:");
        System.out.println ("==================================================");
        System.out.println ("axle length b/w flanges:\t" + specs.getValueForKey ("hub.width") + " mm.");
        System.out.println ("flange diameter:\t\t" + specs.getValueForKey ("flange.diameter") + " mm.");
        System.out.println ("flange width:\t\t\t" + specs.getValueForKey ("flange.width") + " mm.");
        System.out.println ("Spoke hole diameter:\t\t" + specs.getValueForKey ("hole.diameter") + " mm.");
        System.out.println ("==================================================\n");

        System.out.println ("Summary of rim specs:");
        System.out.println ("==================================================");
        System.out.println ("Diameter of spoke nipples:\t" + specs.getValueForKey ("rim.diameter") +
                " mm.");
        System.out.println ("==================================================\n");

        System.out.println ("Lacing configuration:");
        System.out.println ("==================================================");
        System.out.println ("Spoke count:\t\t\t" + specs.getValueForKey ("spokes.count"));
        System.out.println ("Crossings:\t\t\t" + specs.getValueForKey ("crossings.count") + " X");
        System.out.println ("==================================================\n");
    }

    /**
     *  Returns the spoke length required for the wheel given the specs in the file specified to the
     *  class constructor.
     */
        private double
    calculateLength () throws NoSuchKeyException
    {
        double halfHubWidth = getHubWidth () / 2;
        double flangeRadius = getRadius ("flange.diameter");
        double rimRadius = getRadius ("rim.diameter");
        double holeRadius = getRadius ("hole.diameter");

        double spokes = Double.valueOf (specs.getValueForKey ("spokes.count")).doubleValue ();
        double crossings = Double.valueOf (specs.getValueForKey ("crossings.count")).doubleValue ();

        double alpha = 2 * Math.PI * (crossings / spokes);
        double length = squaredLength (halfHubWidth, flangeRadius, rimRadius, alpha);

        return Math.sqrt (length) - holeRadius;
    }

        private double
    getHubWidth () throws NoSuchKeyException
    {
        double flangeToFlange = Double.valueOf (specs.getValueForKey ("hub.width")).doubleValue ();
        double flangeWidth = Double.valueOf (specs.getValueForKey ("flange.width")).doubleValue ();
        return flangeToFlange - flangeWidth;
    }

        private double
    squaredLength (double halfHubWidth, double flangeRadius, double rimRadius, double alpha)
    {
        double hypotenuse = halfHubWidth * halfHubWidth + flangeRadius * flangeRadius + rimRadius * rimRadius;
        return hypotenuse - (2 * flangeRadius * rimRadius * Math.cos (alpha));
    }

    /**
     *  Fetches a value from the spec file corresponding to the key, and halves it. This is useful for
     *  converting diameters to radii.
     */
        private double
    getRadius (String key) throws NoSuchKeyException
    {
        double value = Double.valueOf (specs.getValueForKey (key)).doubleValue ();
        return value / 2;
    }

    /**
     *  Print brief usage instructions on stderr.
     */
        private static void
    printUsage ()
    {
        System.err.println ("SpokeLength: USAGE:\n" +
                "SpokeLength <filename>\n\n" +
                "Where filename is a path to an INI format file containing the rim and hub " +
                "measurements.");
    }
}

// vim: ts=4 sw=4 et
