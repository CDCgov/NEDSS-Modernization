import { ButtonGroup, Button } from '@trussworks/react-uswds';
import { ValueSet } from 'apps/page-builder/generated';
import './ValuesetLibraryTableRowExpanded.scss';

type Props = {
    data: ValueSet;
};

const ValuesetLibraryTableRowExpanded = ({ data }: Props) => {
    return (
        <>
            <div className="wrapper">
                <div className="tabSection">
                    <div className="tabContainer">
                        <ButtonGroup type="default">
                            <Button type="reset" className="tab selected">
                                Value set details
                            </Button>
                            <Button type="reset" className="tab">
                                Value set concepts
                            </Button>
                        </ButtonGroup>
                    </div>
                </div>
                <div className="valuesetDetailsSection">
                    <div className="valuesetDetails">
                        <div className="detailContainer">
                            <dl>
                                <dt>Value set name</dt>
                                <dd>{data.valueSetNm}</dd>
                                <dt>Value set description</dt>
                                <dd>{data.codeSetDescTxt}</dd>
                            </dl>
                        </div>
                        <div className="detailContainer">
                            <dl>
                                <dt>Value set type</dt>
                                <dd>{data.valueSetTypeCd}</dd>
                            </dl>
                        </div>
                        <div className="detailContainer">
                            <dl>
                                <dt>Value set status</dt>
                                <dd>{data.valueSetStatusCd}</dd>
                            </dl>
                        </div>
                        <div className="detailContainer">
                            <dl>
                                <dt>Value set code</dt>
                                <dd>{data.valueSetCode}</dd>
                            </dl>
                        </div>
                    </div>
                </div>
                <div className="footer">
                    <Button type="button" unstyled className="edit">
                        Edit
                    </Button>
                </div>
            </div>
        </>
    );
};

export default ValuesetLibraryTableRowExpanded;
