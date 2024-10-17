const saveDataElementsConfiguration = async (data: { dataElements: any[]; belongingnessRatio: number | undefined }) => {
    try {
        const response = await fetch('http://localhost:8080/configurations/save-data-elements', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });
        if (response.ok) {
            const result = await response.json();
            console.log('Success:', result);
        } else {
            console.error('Failed to save configuration');
        }
    } catch (error) {
        console.error('Error:', error);
    }
};

export default saveDataElementsConfiguration;
