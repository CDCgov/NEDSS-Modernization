import { Header, Menu, NavDropDownButton, NavMenuButton, PrimaryNav, Search, Title } from '@trussworks/react-uswds';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './NavBar.scss';

export default function NavBar() {
    const navigate = useNavigate();

    const [expanded, setExpanded] = useState(false);
    const [isOpen, setIsOpen] = useState(false);
    const [searchValue, setSearchValue] = useState<string>('');
    const onClick = (): void => setExpanded((prvExpanded) => !prvExpanded);

    const testMenuItems = [
        <a href="#linkOne" key="one">
            Current link
        </a>,
        <a href="#linkTwo" key="two">
            Simple link Two
        </a>
    ];

    const testItemsMenu = [
        <>
            <NavDropDownButton
                menuId="testDropDownOne"
                onToggle={(): void => {
                    setIsOpen(!isOpen);
                }}
                isOpen={isOpen}
                label="Nav Label"
                isCurrent={true}
            />
            <Menu key="one" items={testMenuItems} isOpen={isOpen} id="testDropDownOne" />
        </>,
        <a href="#two" key="two" className="usa-nav__link">
            <span>Parent link</span>
        </a>,
        <a href="#three" key="three" className="usa-nav__link">
            <span>Parent link</span>
        </a>
    ];

    return (
        <>
            <div className="nav-bar bg-primary">
                <div className={`usa-overlay ${expanded ? 'is-visible' : ''}`}></div>
                <Header basic={true}>
                    <div className="usa-nav">
                        <div className="usa-navbar">
                            <Title className="title">NBS</Title>
                            <NavMenuButton onClick={onClick} label="Menu" />
                        </div>
                        <PrimaryNav items={testItemsMenu} mobileExpanded={expanded} onToggleMobileNav={onClick}>
                            <Search
                                size="small"
                                onChange={(e: any) => {
                                    setSearchValue(e.target.value);
                                }}
                                onSubmit={(e) => {
                                    e.preventDefault();
                                    searchValue &&
                                        navigate({
                                            pathname: '/search',
                                            search: `?q=${searchValue}`
                                        });
                                }}
                            />
                        </PrimaryNav>
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
