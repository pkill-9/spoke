package mps.spoke;

import java.io.*;
import java.util.*;


/**
 *  Class to parse wheel and hub measurements from a data file, represented in INI format.
 *  This class presents the INI format data as a mapping from String keys to String values.
 */
public class DataFile
{
    private Map <String, String> store;

    /**
     *  Parse key/value associations from the specified file.
     *
     *  @throws IOException if the file cannot be read for some reason.
     */
        public
    DataFile (String filename) throws IOException
    {
        store = new HashMap <String, String> ();

        FileInputStream stream = new FileInputStream (filename);
        DataInputStream in = new DataInputStream (stream);
        BufferedReader input = new BufferedReader (new InputStreamReader (in));

        parseDataFromFile (input);

        input.close ();
    }

    /**
     *  Steps through all the lines in a given file, and adds the key/value pairs to our mapping,
     *  ignoring comment lines.
     */
        public void
    parseDataFromFile (BufferedReader file) throws IOException
    {
        String line;

        while ((line = file.readLine ()) != null)
        {
            if (isCommentLine (line) || line.equals (""))
            {
                continue;
            }

            line = removeCommentsFromLine (line);
            addDataToMap (line);
        }
    }

    /**
     *  Returns true if the given string is an entire line comment.
     */
        private boolean
    isCommentLine (String line)
    {
        String compacted = line.trim ();

        if (compacted.startsWith ("#") || compacted.startsWith (";"))
            return true;

        return false;
    }

    /**
     *  Strip comments from the end of a line. A comment may occurr on the same line as a key/value
     *  pair, in which case everything on the line following the start of the comment is ignored.
     *
     *  @return the line with any trailing comment removed.
     */
        private String
    removeCommentsFromLine (String line)
    {
        StringBuilder stripped = new StringBuilder ();
        char c;

        for (int i = 0; i < line.length (); i ++)
        {
            c = line.charAt (i);

            // we will allow comment sentinal chars to be escaped using the backslash.
            if (c == '\\')
            {
                stripped.append (line.charAt (i + 1));
                i += 1;
            }
            else if ((c != '#') && (c != ';'))
            {
                stripped.append (c);
            }
            else
            {
                break;
            }
        }

        return stripped.toString ();
    }

    /**
     *  Extract the key and value from a line of an INI format file and add it to the mapping.
     */
        private void
    addDataToMap (String line)
    {
        String [] pair = line.split ("=");
        store.put (pair [0].trim (), pair [1].trim ());
    }

    /**
     *  Returns the value associated with the given key.
     *
     *  @throws NoSuchKeyException if the specified key does not exist in the data file.
     */
        public String
    getValueForKey (String key) throws NoSuchKeyException
    {
        // check that the specified key is in fact present.
        if (store.containsKey (key) != true)
            throw new NoSuchKeyException (key);

        return store.get (key);
    }
}

// vim: ts=4 sw=4 et
