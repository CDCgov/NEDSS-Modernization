/*
 * Created on Jan 5, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package gov.cdc.nedss.ldf.importer;

/**
 * @author xzheng
 *
 * This is base importer implementation.
 */
abstract class AbstractBaseImporter implements Importer {

	/**
	 * The source for the importer.
	 */
	protected String source = null;
	protected String sourceLoction = null;

	/** Initialize the importer.
	 * @param source The source for the importer.
	 */
	public void init(String source) {
		this.source = source;
		this.sourceLoction = extractSourceLocation();
	}

	/* (non-Javadoc)
	 * @see gov.cdc.nedss.ldf.helper.importer.Importer#getSource()
	 */
	public String getSource() {
		return source;
	}

	public String getSourceLocation() {
		if (sourceLoction == null)
			sourceLoction = extractSourceLocation();
		return sourceLoction;
	}

	abstract protected String extractSourceLocation();
}
