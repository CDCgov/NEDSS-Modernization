import { Button } from '@trussworks/react-uswds';
import React from 'react';
import { useNavigate } from 'react-router-dom';
import { PageBuilder } from '../../PageBuilder/PageBuilder';
import styles from './AddBusinessRule.module.scss';
import { Breadcrumb } from 'breadcrumb';
import { Heading } from 'components/heading';
// import { PageRuleControllerService } from '../../../generated';
// import { useAlert } from 'alert';
// import { authorization } from 'authorization';

const AddBusinessRule = () => {
    const navigate = useNavigate();
    // const { pageId } = useParams();
    // const { showAlert } = useAlert();

    const handleCancel = () => {
        navigate(-1);
    };

    return (
        <>
            <section className={styles.addBusinessRuleHeader}>
                <header>
                    <h2>Page builder</h2>
                </header>
            </section>
            <PageBuilder page="business-rules">
                <Breadcrumb start="../">business rules</Breadcrumb>
                <div className={styles.addRulesContainer}>
                    <div className={styles.addBusinessRuleTitle}>
                        <Heading level={3}>Add new business rule</Heading>
                    </div>
                </div>
                <div className={styles.footerButtonsContainer}>
                    <Button type="button" outline onClick={handleCancel}>
                        Cancel
                    </Button>
                    <Button type="submit" className="lbr" disabled>
                        Add to Library
                    </Button>
                </div>
            </PageBuilder>
        </>
    );
};

export default AddBusinessRule;
