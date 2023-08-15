import { Button } from '@trussworks/react-uswds';
import './EditPageHeader.scss';

type Page = {
    name?: string;
    description?: string;
};

type PageProps = {
    page: Page;
};

export const EditPageHeader = ({ page }: PageProps) => {
    return (
        <div className="edit-page-header">
            <div className="edit-page-header__left">
                <h2>{page.name}</h2>
                <h4>{page.description}</h4>
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
