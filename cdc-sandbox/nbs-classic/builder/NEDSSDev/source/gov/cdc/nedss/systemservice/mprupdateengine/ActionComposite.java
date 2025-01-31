
package gov.cdc.nedss.systemservice.mprupdateengine;

/**
 *
 * <p>Title: ActionComposite</p>
 * <p>Description: An interface.</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public interface ActionComposite extends Action
{
  /**
   * Adds an action to the composite
   * @param action
   */
  public void add(Action action);
}
