cd C:\Oracle\Middleware\wlserver_10.3\server\lib
java -cp weblogic.jar weblogic.Deployer -verbose -noexit -adminurl http://localhost:7001/ -username weblogic -password 12345678 -source c:/users/poof-/Desktop/SipService.ear -stage -upload -deploy -name sipservice -timeout 300