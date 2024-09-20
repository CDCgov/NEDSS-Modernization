import styles from './extended-patient-nav.module.scss';

export const AddPatientExtendedNav = () => {
    return (
        <aside>
            <nav>
                <div className={styles.navTitle}>On this page</div>
                <div className={styles.navOptions}>
                    <a href="#section-Administrative">Administrative</a>
                    <a href="#section-Name">Name</a>
                    <a href="#section-Address">Address</a>
                    <a href="#section-PhoneAndEmail">Phone & email</a>
                    <a href="#identification">Identification</a>
                    <a href="#section-Race">Race</a>
                    <a href="#section-Ethnicity">Ethnicity</a>
                    <a href="#sexAndBirth">Sex & birth</a>
                    <a href="#section-Mortality">Mortality</a>
                    <a href="#section-General">General patient information</a>
                </div>
            </nav>
        </aside>
    );
};
