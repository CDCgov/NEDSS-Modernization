import { Card, CardProps } from 'design-system/card';
import styles from './Section.module.scss';

export const Section = (props: CardProps) => {
    return (
        <Card className={styles.cardContainer} {...props} collapsible level={4}>
            {props.children}
        </Card>
    );
};
