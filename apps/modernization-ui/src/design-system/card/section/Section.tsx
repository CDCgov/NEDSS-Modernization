import { Card, CardProps } from 'design-system/card';
import styles from './Section.module.scss';
import classNames from 'classnames';

export const Section = (props: CardProps) => {
    return (
        <Card className={classNames(styles.cardContainer, classNames)} {...props} collapsible level={4}>
            {props.children}
        </Card>
    );
};
