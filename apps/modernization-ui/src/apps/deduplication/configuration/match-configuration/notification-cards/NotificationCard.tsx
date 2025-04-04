import { Heading } from 'components/heading';
import { ReactNode } from 'react';
import styles from './notification-card.module.scss';

type Props = {
    heading: ReactNode;
    body: ReactNode;
    buttons?: ReactNode;
};
export const NotificationCard = ({ heading, body, buttons }: Props) => {
    return (
        <section className={styles.notificationCard}>
            <div className={styles.card}>
                <Heading level={2}>{heading}</Heading>
                <div className={styles.cardBody}>
                    <p className={styles.description}>{body}</p>
                    {buttons && <div className={styles.buttonContainer}>{buttons}</div>}
                </div>
            </div>
        </section>
    );
};
