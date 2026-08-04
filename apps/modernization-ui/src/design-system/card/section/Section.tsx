import classNames from 'classnames';
import { Card, CardProps } from 'design-system/card';

import styles from './Section.module.scss';

export const Section = (props: CardProps) => {
    return (
        <Card className={classNames(styles.cardContainer, classNames)} {...props} collapsible={true} level={4}>
            {props.children}
        </Card>
    );
};
