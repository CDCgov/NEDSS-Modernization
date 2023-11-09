import { Link } from 'react-router-dom';

type NavigationEntryProps = {
    label: string;
    href: string;
    useNav?: boolean;
};
export const NavigationEntry = ({ href, label, useNav = false }: NavigationEntryProps) => {
    return (
        <>
            {useNav ? (
                <Link to={href}>{label}</Link>
            ) : (
                <a href={href}>
                    <p>{label}</p>
                </a>
            )}
        </>
    );
};
