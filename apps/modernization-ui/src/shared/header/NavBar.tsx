import { useContext } from 'react';
import { useLocation } from 'react-router-dom';
import { Config } from '../../config';
import { UserContext } from '../../providers/UserContext';
import './NavBar.scss';

// eslint-disable-next-line no-undef
const NBS_URL = Config.nbsUrl;

export default function NavBar() {
    const { state, logout } = useContext(UserContext);
    const location = useLocation();
    const logoutClick = () => {
        logout();
    };
    return (
        <div className="nav-bar">
            <table role="presentation" className="nedssNavTable">
                <tbody>
                    <tr>
                        <td>
                            <table role="presentation" align="left">
                                <tbody>
                                    <tr>
                                        <td className="navLink">
                                            <a href={`${NBS_URL}/HomePage.do?method=loadHomePage`}>Home</a>
                                        </td>
                                        <td>
                                            {' '}
                                            <span> | </span>{' '}
                                        </td>

                                        <td className="navLink">
                                            <a href={`${NBS_URL}/LoadNavbar.do?ContextAction=DataEntry`}>Data Entry</a>
                                        </td>
                                        <td>
                                            <span> | </span>
                                        </td>

                                        <td className="navLink">
                                            <a href={`${NBS_URL}/LoadNavbar1.do?ContextAction=MergePerson`}>
                                                Merge Patients
                                            </a>
                                        </td>
                                        <td>
                                            <span> | </span>
                                        </td>

                                        <td className="navLink">
                                            <a
                                                href={`${NBS_URL}/LoadNavbar.do?ContextAction=GlobalInvestigations&initLoad=true`}>
                                                Open Investigations
                                            </a>
                                        </td>
                                        <td>
                                            <span> | </span>
                                        </td>

                                        <td className="navLink">
                                            <a href={`${NBS_URL}/nfc?ObjectType=7&amp;OperationType=116`}>Reports</a>
                                        </td>
                                        <td>
                                            <span> | </span>
                                        </td>

                                        <td className="navLink">
                                            <a href={`${NBS_URL}/SystemAdmin.do`}>System Management</a>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </td>
                        <td>
                            <table role="presentation" align="right">
                                <tbody>
                                    <tr>
                                        <td className="navLink">
                                            <a
                                                href={`${NBS_URL}/UserGuide.do?method=open`}
                                                target="_blank"
                                                rel="noreferrer">
                                                {' '}
                                                Help{' '}
                                            </a>
                                        </td>
                                        <td>
                                            <span> | </span>
                                        </td>
                                        <td className="navLink">
                                            <a onClick={logoutClick}>Logout</a>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </td>
                        <td style={{ width: '105px' }}>&nbsp;</td>
                    </tr>
                </tbody>
            </table>
        </div>
    );
}
