import { permitsAny, permissions, permitsAll } from 'libs/permission';
import { usePage } from 'page';
import { useUser } from 'user';
import { FeatureToggle } from 'feature';
import { NavBarLink, PermittedNavBarLink } from './NavBarLink';

import styles from './NavBar.module.scss';

const BASE_SYS_MGMT_PERMISSIONS = [
    'EPILINKADMIN-SYSTEM',
    'VIEWELRACTIVITY-OBSERVATIONLABREPORT',
    'SRTADMIN-SYSTEM',
    'VIEWPHCRACTIVITY-CASEREPORTING',
    'IMPORTEXPORTADMIN-SYSTEM',
    'REPORTADMIN-SYSTEM',
    'ALERTADMIN-SYSTEM',
    'ADMINISTRATE-SYSTEM',
    'ADMINISTRATE-SECURITY',
];

const DEDUPE_FEATURE_SYS_MGMT_PERMISSIONS = [...BASE_SYS_MGMT_PERMISSIONS, 'MERGE-PATIENT'];

export const NavBar = () => {
    const {
        state: { user },
        logout,
    } = useUser();

    const { title } = usePage();

    return (
        <nav className={styles.navbar} aria-label="main menu">
            <table role="presentation" className={styles.nedssNavTable}>
                <tbody>
                    <tr>
                        <td>
                            <table role="presentation">
                                <tbody>
                                    <tr>
                                        <NavBarLink url="/nbs/HomePage.do?method=loadHomePage" name="Home" />
                                        <PermittedNavBarLink
                                            permission={permitsAny(
                                                permissions.morbidityReport.add,
                                                permissions.labReport.add,
                                                permissions.summaryReports.view,
                                                permissions.patient.search,
                                                permissions.place.manage,
                                                permissions.provider.manage,
                                                permissions.organization.manage
                                            )}
                                            url="/nbs/LoadNavbar.do?ContextAction=DataEntry"
                                            name="Data Entry"
                                            includeSeparator={true}
                                        />
                                        <PermittedNavBarLink
                                            permission={permitsAll(permissions.patient.merge)}
                                            url="/nbs/LoadNavbar1.do?ContextAction=MergePerson"
                                            name="Merge Patients"
                                            includeSeparator={true}
                                        />

                                        <PermittedNavBarLink
                                            permission={permitsAll(permissions.investigation.view)}
                                            url="/nbs/LoadNavbar.do?ContextAction=GlobalInvestigations&initLoad=true"
                                            name="Open Investigations"
                                            includeSeparator={true}
                                        />

                                        <PermittedNavBarLink
                                            permission={permitsAny(
                                                permissions.reports.template.view,
                                                permissions.reports.public.view,
                                                permissions.reports.private.view,
                                                permissions.reports.reportingFacility.view
                                            )}
                                            url="/nbs/nfc?ObjectType=7&amp;OperationType=116"
                                            name="Reports"
                                            includeSeparator={true}
                                        />

                                        <FeatureToggle
                                            guard={(features) => features?.deduplication?.enabled}
                                            fallback={
                                                <PermittedNavBarLink
                                                    permission={permitsAny(...BASE_SYS_MGMT_PERMISSIONS)}
                                                    url="/nbs/SystemAdmin.do"
                                                    name="System Management"
                                                    includeSeparator={true}
                                                />
                                            }
                                        >
                                            <PermittedNavBarLink
                                                permission={permitsAny(...DEDUPE_FEATURE_SYS_MGMT_PERMISSIONS)}
                                                url="/nbs/SystemAdmin.do"
                                                name="System Management"
                                                includeSeparator={true}
                                            />
                                        </FeatureToggle>
                                    </tr>
                                </tbody>
                            </table>
                        </td>
                        <td>
                            <table role="presentation" align="right">
                                <tbody>
                                    <tr>
                                        <td className={styles.navLink}>
                                            <a href={`/nbs/UserGuide.do?method=open`} target="_blank" rel="noreferrer">
                                                {' '}
                                                Help{' '}
                                            </a>
                                        </td>
                                        <td>
                                            <span> | </span>
                                        </td>
                                        <td className={styles.navLink}>
                                            <a href="/nbs/logout" onClick={logout}>
                                                Logout
                                            </a>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </td>
                        <td style={{ width: '105px' }}>&nbsp;</td>
                    </tr>
                </tbody>
            </table>
            <div className={styles.bottom}>
                <span className={styles.title}>{title}</span>
                <span className={styles.user}>
                    <span>User : {user?.name.display}</span>
                    <img title="NBS Logo" alt="NBS Logo" height="32" width="80" src="/images/nedssLogo.jpg" />
                </span>
            </div>
        </nav>
    );
};
