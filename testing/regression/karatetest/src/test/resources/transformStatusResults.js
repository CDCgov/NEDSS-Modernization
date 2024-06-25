function transformStatusResults(statusResults) {
    return statusResults.map(item => ({
        ID: item.ID,
        modifiedData: item.modifiedData,
        "NBS Response": item.finalMessage
    }));
}
