import { permitsAny, Permitted } from 'libs/permission';
import { usePage } from 'page';
import { useUser } from 'user';

import styles from './NavBar.module.scss';

export const NavBar = () => {
    const {
        state: { user },
        logout
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
                                        <td className={styles.navLink}>
                                            <a href={`/nbs/HomePage.do?method=loadHomePage`}>Home</a>
                                        </td>
                                        <td>
                                            <span> | </span>
                                        </td>

                                        <td className={styles.navLink}>
                                            <a href={`/nbs/LoadNavbar.do?ContextAction=DataEntry`}>Data Entry</a>
                                        </td>
                                        <td>
                                            <span> | </span>
                                        </td>

                                        <td className={styles.navLink}>
                                            <a href={`/nbs/LoadNavbar1.do?ContextAction=MergePerson`}>Merge Patients</a>
                                        </td>
                                        <td>
                                            <span> | </span>
                                        </td>

                                        <td className={styles.navLink}>
                                            <a
                                                href={`/nbs/LoadNavbar.do?ContextAction=GlobalInvestigations&initLoad=true`}>
                                                Open Investigations
                                            </a>
                                        </td>
                                        <td>
                                            <span> | </span>
                                        </td>

                                        <td className={styles.navLink}>
                                            <a href={`/nbs/nfc?ObjectType=7&amp;OperationType=116`}>Reports</a>
                                        </td>

                                        <Permitted
                                            permission={permitsAny(
                                                'EPILINKADMIN-SYSTEM',
                                                'VIEWELRACTIVITY-OBSERVATIONLABREPORT',
                                                'SRTADMIN-SYSTEM',
                                                'VIEWPHCRACTIVITY-CASEREPORTING',
                                                'IMPORTEXPORTADMIN-SYSTEM',
                                                'REPORTADMIN-SYSTEM',
                                                'ALERTADMIN-SYSTEM',
                                                'ADMINISTRATE-SYSTEM',
                                                'ADMINISTRATE-SECURITY'
                                            )}>
                                            <td className={styles.navLink}>
                                                <span> | </span>
                                                <a href={`/nbs/SystemAdmin.do`}>System Management</a>
                                            </td>
                                        </Permitted>
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
