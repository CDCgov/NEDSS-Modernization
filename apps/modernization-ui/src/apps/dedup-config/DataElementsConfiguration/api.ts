const saveDataElementsConfiguration = async (data: { dataElements: any[]; belongingnessRatio: number | undefined }) => {
    console.log('Attempting to save configuration with data:', data);

    try {
        const response = await fetch('http://localhost:8080/api/configurations/config', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        console.log('Response status:', response.status);
        console.log('Response headers:', response.headers);

        if (response.ok) {
            const result = await response.json();
            console.log('Configuration saved successfully:', result);
        } else {
            const errorText = await response.text();
            console.error('Failed to save configuration. Status:', response.status, 'Error:', errorText);
        }
    } catch (error) {
        console.error('Network error or fetch failed:', error);
    }
};

export default saveDataElementsConfiguration;
