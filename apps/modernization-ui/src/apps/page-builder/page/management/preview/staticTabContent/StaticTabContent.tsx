import { ContactRecord } from './ContactRecord';
import { SupplementalInfo } from './SupplementalInfo';
import styles from './static-tab.module.scss';

type Props = {
    tab: 'contactRecord' | 'supplementalInfo';
};
export const StaticTabContent = ({ tab }: Props) => {
    return (
        <div className={styles.staticTab}>
            {tab === 'contactRecord' && <ContactRecord />}
            {tab === 'supplementalInfo' && <SupplementalInfo />}
        </div>
    );
};
