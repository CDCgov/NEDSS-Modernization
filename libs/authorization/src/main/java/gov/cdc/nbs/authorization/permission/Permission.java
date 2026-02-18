package gov.cdc.nbs.authorization.permission;

/**
 * An authorization that grants an operation on an object.
 *
 * @param operation The granted action
 * @param object The resource the action can be preformed on
 */
public record Permission(String operation, String object) {}
