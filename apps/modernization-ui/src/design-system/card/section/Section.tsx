import { Card, CardProps } from 'design-system/card';
import styles from './Section.module.scss';

export const SectionHeader = (props: CardProps) => {
    return (
        <Card className={styles.cardContainer} {...props} collapsible level={4}>
            {props.children}
        </Card>
    );
};
