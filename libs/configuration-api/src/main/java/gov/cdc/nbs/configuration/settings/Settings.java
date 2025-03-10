package gov.cdc.nbs.configuration.settings;

import gov.cdc.nbs.configuration.settings.analytics.Analytics;
import gov.cdc.nbs.configuration.settings.session.Session;
import gov.cdc.nbs.configuration.settings.smarty.Smarty;

public record Settings(Smarty smarty, Analytics analytics, Session session) {
}
