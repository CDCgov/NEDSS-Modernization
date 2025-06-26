import { Card } from 'design-system/card/Card';
import styles from './SystemManagementInfoCard.module.scss';
import { useNavigate } from 'react-router';
import React from 'react';

type LinkItem = { text: string; href: string };

type Props = {
    id: string;
    title: string;
    filter: string;
    links: LinkItem[];
    useNavigation?: boolean;
};

export const SystemManagementInfoCard = ({ id, title, filter, links, useNavigation = false }: Props) => {
    const normalizedFilter = filter.trim().toLowerCase();
    const filteredLinks = links.filter((item) => item.text.toLowerCase().includes(normalizedFilter));
    const navigate = useNavigate();

    if (filteredLinks.length === 0) return null;

    const handleClick = (href: string, e: React.MouseEvent<HTMLAnchorElement>) => {
        if (useNavigation) {
            e.preventDefault();
            navigate(href);
        }
    };

    return (
        <Card id={id} title={title} level={2} collapsible className={styles.card}>
            <div className={styles.sectionContent}>
                {filteredLinks.map((link) => (
                    <a key={link.href} href={link.href} onClick={(e) => handleClick(link.href, e)}>
                        {link.text}
                    </a>
                ))}
            </div>
        </Card>
    );
};
