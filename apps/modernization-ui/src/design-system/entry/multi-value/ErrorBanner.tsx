import { Icon } from 'design-system/icon';
import styles from './error-banner.module.scss';

type Props = {
    errors: string[];
};
// Created to match the designs for RepeatingBlocks used within extended patient create.
// There are design conflicts between this banner and the AlertBanner.
// In the "near" future, this and the AlertBanner component need to be updated / possibly combined once design issues are resolved
export const ErrorBanner = ({ errors }: Props) => {
    return (
        <section className={styles.errorBanner}>
            <div>
                <Icon name={'error'} className={styles.icon} />
            </div>
            <div className={styles.content}>
                <div className={styles.errorHeading}>Please fix the following errors: </div>
                <ul>
                    {errors.map((e, i) => (
                        <li key={i}>{e}</li>
                    ))}
                </ul>
            </div>
        </section>
    );
};
