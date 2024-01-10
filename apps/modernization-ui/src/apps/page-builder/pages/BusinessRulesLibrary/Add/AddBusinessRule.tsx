import { Button } from '@trussworks/react-uswds';
import React from 'react';
import { useNavigate } from 'react-router-dom';
import { PageBuilder } from '../../PageBuilder/PageBuilder';
import styles from './AddBusinessRule.module.scss';
import { Breadcrumb } from 'breadcrumb';
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
        <PageBuilder page="business-rules">
            <section className={styles.addBusinessRuleHeader}>
                <header>
                    <h2>Business Rules</h2>
                </header>
            </section>
            <Breadcrumb start="../">business rules</Breadcrumb>
            <div className={styles.addRulesContainer}>
                <h3>Add new business rule</h3>
                <div></div>
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
    );
};

export default AddBusinessRule;
