package mps.spoke;


/**
 *  Exception class that will be thrown to indicate if a map key is not associated with any value.
 */
public class NoSuchKeyException extends Exception
{
    private String undefinedKey;

        public 
    NoSuchKeyException (String undefinedKey)
    {
        this.undefinedKey = undefinedKey;
    }

    /**
     *  Print the undefined key on stderr.
     */
        public void
    printMissingKey ()
    {
        System.err.println (undefinedKey);
    }
}

// vim: ts=4 sw=4 et
