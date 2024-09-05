import React, { createContext, useContext, useState } from 'react';

const DataElementsContext = createContext<DataElementsContextProps | undefined>(undefined);

type DataElementsContextProps = {
    dataElements: typeof DataElements | null;
    setDataElements: (dataElements: typeof DataElements) => void;
};

const DataElements = {
    lastName: '',
    secondLastName: '',
    firstName: '',
    middleName: '',
    secondMiddleName: '',
    suffix: '',
    currentSex: '',
    dateOfBirth: '',
    ssn: '',
    idType: '',
    idAssigningAuthority: '',
    idValue: '',
    streetAddress1: '',
    city: '',
    state: '',
    zipCode: '',
    telephone: ''
};

const DataElementsContextProvider: React.FC<{
    children: React.ReactNode;
}> = ({ children }) => {
    const [dataElements, setDataElements] = useState(DataElements);

    return (
        <DataElementsContext.Provider value={{ dataElements, setDataElements }}>
            {children}
        </DataElementsContext.Provider>
    );
};

export const useDataElementsContext = () => {
    const context = useContext(DataElementsContext);

    if (context === undefined) {
        throw new Error('useDataElementsContext must be used inside DataElementsContextProvider');
    }

    return context;
};

export default DataElementsContextProvider;
