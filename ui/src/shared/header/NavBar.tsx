import { Header, NavMenuButton, PrimaryNav, Title } from '@trussworks/react-uswds';
import { useState } from 'react';
import { Link, useLocation } from 'react-router-dom';
import './NavBar.scss';

export default function NavBar() {
    const [expanded, setExpanded] = useState(false);
    const onClick = (): void => setExpanded((prvExpanded) => !prvExpanded);
    const { pathname } = useLocation();

    const navItems = [
        <>
            <Link to={'/'} className={`usa-nav-item ${pathname === '/' && 'active'}`}>
                Home
            </Link>
        </>,
        <>
            <Link to={'/'} className={`usa-nav-item ${pathname === '/my-reports' && 'active'}`}>
                My Reports
            </Link>
        </>,
        <>
            <Link to={'/'} className={`usa-nav-item ${pathname === '/system-management' && 'active'}`}>
                System Management
            </Link>
        </>
    ];

    return (
        <>
            <div className="nav-bar bg-white border-bottom border-base-light">
                <div className={`usa-overlay ${expanded ? 'is-visible' : ''}`}></div>
                <Header basic={true}>
                    <div className="usa-nav-container height-10">
                        <div className="usa-navbar width-full">
                            <Title className="title">NBS</Title>
                            <NavMenuButton onClick={onClick} label="Menu" />
                        </div>
                        <PrimaryNav
                            className="height-10"
                            items={navItems}
                            mobileExpanded={expanded}
                            onToggleMobileNav={onClick}
                        />
                    </div>
                </Header>
            </div>
        </>
    );

    // return (
    //     <>
    //         <Header className="bg-primary">
    //             <div className="usa-nav-container text-white">
    //                 <div className="usa-navbar">
    //                     <Title>NBS NEW</Title>
    //                 </div>
    //             </div>{' '}
    //         </Header>
    //     </>
    // );
}
