    package gov.cdc.nedss.util;

    // Basic node stored in unbalanced binary search trees
    // Note that this class is not accessible outside
    // of package util

    class BinaryNode
    {
        // Constructors
        BinaryNode( Long theElement )
        {
            this( theElement, null, null );
        }

        BinaryNode( Long theElement, BinaryNode lt, BinaryNode rt )
        {
            element  = theElement;
            left     = lt;
            right    = rt;
        }

        // Friendly data; accessible by other package routines
        Long element;      // The data in the node
        BinaryNode left;         // Left child
        BinaryNode right;        // Right child
    }
