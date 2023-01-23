import { useEffect, useState } from 'react';
import { TableComponent } from '../../components/Table/Table';
import { Button, Grid, Icon } from '@trussworks/react-uswds';
import { HorizontalTable } from '../../components/Table/HorizontalTable';

export const Demographics = () => {
    const [tableBody, setTableBody] = useState<any>([]);
    const [nameTableBody, setNameTableBody] = useState<any>([]);
    const [currentPage, setCurrentPage] = useState<number>(1);

    useEffect(() => {
        const tempArr = [];
        for (let i = 0; i < 3; i++) {
            tempArr.push({
                id: i + 1,
                checkbox: true,
                tableDetails: [
                    {
                        id: 1,
                        title: '07/27/2022'
                    },
                    {
                        id: 2,
                        title: 'Legal'
                    },
                    {
                        id: 3,
                        title: 'Dr.'
                    },
                    {
                        id: 4,
                        title: 'Smith, Johnny'
                    },
                    {
                        id: 5,
                        title: ''
                    },
                    {
                        id: 6,
                        title: 'Smith, Johnny'
                    },
                    {
                        id: 7,
                        title: (
                            <Button type="button" unstyled>
                                <Icon.MoreHoriz />
                            </Button>
                        )
                    }
                ]
            });
        }
        setTableBody([
            {
                id: 1,
                checkbox: true,
                tableDetails: [
                    { id: 1, title: `11/19/2022` },
                    {
                        id: 2,
                        title: 'This patient is currently waiting for a call from an investigator to get investigation started.'
                    },
                    {
                        id: 3,
                        title: (
                            <Button type="button" unstyled>
                                <Icon.MoreHoriz />
                            </Button>
                        )
                    }
                ]
            }
        ]);
        setNameTableBody(tempArr);
    }, []);

    return (
        <>
            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <TableComponent
                    isPagination={true}
                    buttons={
                        <div className="grid-row">
                            <Button type="button" className="grid-row">
                                <Icon.Add className="margin-right-05" />
                                Add comment
                            </Button>
                        </div>
                    }
                    tableHeader={'Administrative'}
                    tableHead={[
                        { name: 'As of', sortable: true },
                        { name: 'General comment', sortable: true },
                        { name: 'Actions', sortable: true }
                    ]}
                    tableBody={tableBody}
                    currentPage={currentPage}
                    handleNext={(e) => setCurrentPage(e)}
                />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <TableComponent
                    isPagination={true}
                    buttons={
                        <div className="grid-row">
                            <Button type="button" className="grid-row">
                                <Icon.Add className="margin-right-05" />
                                Add name
                            </Button>
                        </div>
                    }
                    tableHeader={'Name'}
                    tableHead={[
                        { name: 'As of', sortable: true },
                        { name: 'Type', sortable: true },
                        { name: 'Prefix', sortable: true },
                        { name: 'Name ( last, first middle )', sortable: true },
                        { name: 'Suffix', sortable: true },
                        { name: 'Degree', sortable: true },
                        { name: 'Actions', sortable: true }
                    ]}
                    tableBody={nameTableBody}
                    currentPage={currentPage}
                    handleNext={(e) => setCurrentPage(e)}
                />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <TableComponent
                    isPagination={true}
                    buttons={
                        <div className="grid-row">
                            <Button type="button" className="grid-row">
                                <Icon.Add className="margin-right-05" />
                                Add address
                            </Button>
                        </div>
                    }
                    tableHeader={'Address'}
                    tableHead={[
                        { name: 'As of', sortable: true },
                        { name: 'Type', sortable: true },
                        { name: 'Address', sortable: true },
                        { name: 'City', sortable: true },
                        { name: 'State', sortable: true },
                        { name: 'Zip', sortable: true },
                        { name: 'Actions', sortable: true }
                    ]}
                    tableBody={[
                        {
                            id: 1,
                            checkbox: true,
                            tableDetails: [
                                {
                                    id: 1,
                                    title: '07/27/2022'
                                },
                                {
                                    id: 2,
                                    title: 'House / Home'
                                },
                                {
                                    id: 3,
                                    title: '123 Main St.'
                                },
                                {
                                    id: 4,
                                    title: 'Atlanta'
                                },
                                {
                                    id: 5,
                                    title: 'Georgia'
                                },
                                {
                                    id: 6,
                                    title: '30024'
                                },
                                {
                                    id: 7,
                                    title: (
                                        <Button type="button" unstyled>
                                            <Icon.MoreHoriz />
                                        </Button>
                                    )
                                }
                            ]
                        }
                    ]}
                    currentPage={currentPage}
                    handleNext={(e) => setCurrentPage(e)}
                />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <TableComponent
                    isPagination={true}
                    buttons={
                        <div className="grid-row">
                            <Button type="button" className="grid-row">
                                <Icon.Add className="margin-right-05" />
                                Add phone & email
                            </Button>
                        </div>
                    }
                    tableHeader={'Phone & email'}
                    tableHead={[
                        { name: 'As of', sortable: true },
                        { name: 'Type', sortable: true },
                        { name: 'Phone number', sortable: true },
                        { name: 'Email address', sortable: true },
                        { name: 'Actions', sortable: true }
                    ]}
                    tableBody={[
                        {
                            id: 1,
                            checkbox: true,
                            tableDetails: [
                                {
                                    id: 1,
                                    title: '07/27/2022'
                                },
                                {
                                    id: 2,
                                    title: 'Cellular phone / Mobile contact'
                                },
                                {
                                    id: 3,
                                    title: '555-555-5555'
                                },
                                {
                                    id: 4,
                                    title: 'sjohn@helloworld.com'
                                },
                                {
                                    id: 5,
                                    title: (
                                        <Button type="button" unstyled>
                                            <Icon.MoreHoriz />
                                        </Button>
                                    )
                                }
                            ]
                        }
                    ]}
                    currentPage={currentPage}
                    handleNext={(e) => setCurrentPage(e)}
                />
            </div>

            <div className="margin-top-6 margin-bottom-2 flex-row common-card">
                <TableComponent
                    isPagination={true}
                    buttons={
                        <div className="grid-row">
                            <Button type="button" className="grid-row">
                                <Icon.Add className="margin-right-05" />
                                Add identification
                            </Button>
                        </div>
                    }
                    tableHeader={'Indentification'}
                    tableHead={[
                        { name: 'As of', sortable: true },
                        { name: 'Type', sortable: true },
                        { name: 'Authority', sortable: true },
                        { name: 'Value', sortable: true },
                        { name: 'Actions', sortable: true }
                    ]}
                    tableBody={[
                        {
                            id: 1,
                            checkbox: true,
                            tableDetails: [
                                {
                                    id: 1,
                                    title: '07/27/2022'
                                },
                                {
                                    id: 2,
                                    title: 'Account number'
                                },
                                {
                                    id: 3,
                                    title: 'GA'
                                },
                                {
                                    id: 4,
                                    title: '3453453533'
                                },
                                {
                                    id: 5,
                                    title: (
                                        <Button type="button" unstyled>
                                            <Icon.MoreHoriz />
                                        </Button>
                                    )
                                }
                            ]
                        }
                    ]}
                    currentPage={currentPage}
                    handleNext={(e) => setCurrentPage(e)}
                />
            </div>

            <Grid row gap className="margin-auto">
                <Grid col={6}>
                    <Grid row>
                        <Grid col={12} className="margin-top-3 margin-bottom-2">
                            <HorizontalTable
                                tableHeader="Race"
                                tableData={[
                                    { title: 'As of:', text: '09/19/2020' },
                                    { title: 'Race:', text: 'White' },
                                    { title: 'Detailed race:', text: '' }
                                ]}
                            />
                        </Grid>

                        <Grid col={12} className="margin-top-3 margin-bottom-2">
                            <HorizontalTable
                                tableHeader="Ethnicity"
                                tableData={[
                                    { title: 'As of:', text: '09/19/2020' },
                                    { title: 'Ethnicity::', text: 'Not Hispanic or Latino' },
                                    { title: 'Spanish origin:', text: '' },
                                    { title: 'Reasons unknown:', text: '' }
                                ]}
                            />
                        </Grid>

                        <Grid col={12} className="margin-top-3 margin-bottom-2">
                            <HorizontalTable
                                tableHeader="Ethnicity"
                                tableData={[
                                    { title: 'As of:', text: '09/19/2020' },
                                    { title: 'Marital status::', text: 'Married' },
                                    { title: 'Mother’s maiden name:', text: '' },
                                    { title: 'Number of adults in residence:', text: '' },
                                    { title: 'Number of children in residence:', text: '' },
                                    { title: 'Primary occupation:', text: '' },
                                    { title: 'Highest level of education:', text: '' },
                                    { title: 'Primary language:', text: '' },
                                    { title: 'Speaks english:', text: '' },
                                    { title: 'State HIV case ID:', text: '' }
                                ]}
                            />
                        </Grid>
                    </Grid>
                </Grid>
                <Grid col={6}>
                    <Grid row>
                        <Grid col={12} className="margin-top-3 margin-bottom-2">
                            <HorizontalTable
                                tableHeader="Mortality"
                                tableData={[
                                    { title: 'As of:', text: '09/19/2020' },
                                    { title: 'Is the patient deceased::', text: 'No' },
                                    { title: 'Date of death:', text: '' },
                                    { title: 'City of death:', text: '' },
                                    { title: 'State of death:', text: '' },
                                    { title: 'County of death:', text: '' },
                                    { title: 'Country of death:', text: '' }
                                ]}
                            />
                        </Grid>

                        <Grid col={12} className="margin-top-3 margin-bottom-2">
                            <HorizontalTable
                                tableHeader="Sex & Birth"
                                tableData={[
                                    { title: 'As of:', text: '09/19/2020' },
                                    { title: 'Date of death:', text: '01/07/1972' },
                                    { title: 'Current age:', text: '50 Years' },
                                    { title: 'Current sex:', text: 'Male' },
                                    { title: 'Unknown reason:', text: '' },
                                    { title: 'Trasngender information:', text: '' },
                                    { title: 'Additional gender:', text: '' },
                                    { title: 'Birth sex:', text: '' },
                                    { title: 'Multiple birth:', text: '' },
                                    { title: 'Birth order:', text: '' },
                                    { title: 'Birth city:', text: '' },
                                    { title: 'Birth state:', text: '' },
                                    { title: 'Birth county:', text: '' },
                                    { title: 'Birth country:', text: '' }
                                ]}
                            />
                        </Grid>
                    </Grid>
                </Grid>
            </Grid>
        </>
    );
};
