For building Pyp, you need to declare the following properties in your settings.xml.

for example :
```
	  <profile>
            <id>my-plateform</id>
            <activation>
                <property>
                    <name>!alwaysActivated</name>
                </property>
            </activation>
            <properties>
                <smtpPrdServer>mySmtp</smtpPrdServer>
                <smtpPrdPort>25</smtpPrdPort>
                <mailDomainPrd>@gmail.com</mailDomainPrd>

                <confluencePrdUrl>http://wp-confluence/confluence</confluencePrdUrl>
                <confluencePrdUser>mandela</confluencePrdUser>
                <confluencePrdPassword>XXX_12355</confluencePrdPassword>
                <confluencePrdSpaceKey>swdev</confluencePrdSpaceKey>
                <confluencePrdPage>Destinataire equipe java</confluencePrdPage>
				
				<confluenceTestUrl>http://wd-confluence/confluence</confluenceTestUrl>
				<confluenceTestUser>mandela_dev</confluenceTestUser>
				<confluenceTestPassword>XXXX_4567</confluenceTestPassword>
				<confluenceTestSpaceKey>sandbox</confluenceTestSpaceKey>

            </properties>
        </profile>
```

TODO :
* Manage properly the absence of a confluence server (add alerts on gui or any sendMail simulation)
* when unblocking radio button is clcked, status passes to unblocked
  --> ideally, if its "quick" date is set to creation date, else to "today"
* Filter the last x days Brins OR highlight them with a specific color
* show detail in a popup to avoid double click
* simple export for confluence, with Name/Url for quick insert in "reunion plateforme" minutes
* Move tomcat-maven-plugin to super-pom