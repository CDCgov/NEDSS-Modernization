import { Card } from 'design-system/card/Card';
import styles from './SystemManagementInfoCard.module.scss';

type LinkItem = { text: string; href: string };

type Props = {
    id: string;
    title: string;
    filter: string;
    links: LinkItem[];
};

export const SystemManagementInfoCard = ({ id, title, filter, links }: Props) => {
    const normalizedFilter = filter.trim().toLowerCase();
    const filteredLinks = links.filter((item) => item.text.toLowerCase().includes(normalizedFilter));

    if (filteredLinks.length === 0) return null;

    return (
        <Card id={id} title={title} level={2} collapsible className={styles.card}>
            <div className={styles.sectionContent}>
                {filteredLinks.map((link) => (
                    <a key={link.href} href={link.href}>
                        {link.text}
                    </a>
                ))}
            </div>
        </Card>
    );
};
