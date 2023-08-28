import { Button } from '@trussworks/react-uswds';
import './EditPageHeader.scss';
import { PageDetails } from 'apps/page-builder/generated/models/PageDetails';

type PageProps = {
    page: PageDetails;
};

export const EditPageHeader = ({ page }: PageProps) => {
    return (
        <div className="edit-page-header">
            <div className="edit-page-header__left">
                <h2>{page.Name}</h2>
                <h4>{page.pageDescription}</h4>
            </div>
            <div className="edit-page-header__right">
                <Button type="button" outline>
                    Save draft
                </Button>
                <Button type="button" outline>
                    Cancel
                </Button>
                <Button type="button">Submit</Button>
            </div>
        </div>
    );
};
