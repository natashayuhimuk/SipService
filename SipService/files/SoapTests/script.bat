@echo off

cd C:/Program Files (x86)/SmartBear/SoapUI-5.4.0/bin

call testrunner.bat  -ehttp://localhost:7001/webservices/user -a -fC:\reports\ C:\Your_address\PracticeApr19\SipService\files\SoapTests\TestSoapUi\user-soapui-project.xml

call testrunner.bat -ehttp://localhost:7001/webservices/tariff -a -fC:\reports\ C:\Your_address\PracticeApr19\SipService\files\SoapTests\TestSoapUi\tariff-soapui-project.xml

call testrunner.bat -ehttp://localhost:7001/webservices/blacklist -a -fC:\reports\ C:\Your_address\PracticeApr19\SipService\files\SoapTests\TestSoapUi\blacklist-soapui-project.xml

call testrunner.bat -ehttp://localhost:7001/webservices/auth -a -fC:\reports\ C:\Your_address\PracticeApr19\SipService\files\SoapTests\TestSoapUi\auth-soapui-project.xml

