module.exports = async () => {
    // All tests will create dates in the EST Timezone.  UTC-5
    process.env.TZ = 'EST';
};
