import pytest
from selenium import webdriver


@pytest.fixture(scope="session", autouse=True)
def setup(request):
    browser_name = request.config.getoption("--browser")
    opt = request.config.getoption("--options_headless")
    if browser_name == 'edge':
        if opt == 'true':
            options = webdriver.EdgeOptions()
            options.use_chromium = True
            options.add_argument("start-maximized")
            # options.add_argument("headless")
            options.add_argument("disable-gpu")
            options.add_experimental_option("prefs", {
                "download.default_directory": r"C:\Test",
                "download.prompt_for_download": False,
                "download.directory_upgrade": True
            })
            driver = webdriver.Edge(options=options)
        else:
            driver = webdriver.Edge()
        print("Launching Edge browser.........")
    elif browser_name == 'firefox':
        driver = webdriver.Firefox()
        driver.maximize_window()
        print("Launching firefox browser.........")
    else:
        options = webdriver.ChromeOptions()
        options.add_argument("start-maximized")
        options.add_argument('--ignore-certificate-errors')
        options.add_argument('--ignore-ssl-errors')
        driver = webdriver.Chrome(options=options)
        print("Launching chrome browser.........")
    # request.cls.driver = driver

    return driver


def pytest_addoption(parser):  # This will get the value from CLI /hooks
    parser.addoption("--browser", action="store", default="edge")
    parser.addoption("--options_headless", action="store", default="true")
    parser.addoption("--navigation", action="store", default="homepage")
    parser.addoption("--template", action="store", default="Generic V2 Investigation")
    parser.addoption("--condition", action="store", default="Anthrax")


@pytest.fixture
def params(request):
    params = {'navigate': request.config.getoption('--navigation')}
    if params['navigate'] is None:
        pytest.skip()
    return params


@pytest.fixture
def test_parameters(pytestconfig):
    template = pytestconfig.getoption("--template")
    condition = pytestconfig.getoption("--condition")
    return {"condition": condition, "templateName": template}
