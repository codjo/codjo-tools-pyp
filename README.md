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

Deployment :
* mvn --batch-mode codjo:switch-to-parent-release
* mvn release:prepare
* mvn release:perform -Darguments="-Dprocess=integration -Dserver=integration" -Dprocess=integration -Dserver=integration
* mvn --batch-mode codjo:switch-to-parent-snapshot

TODO :
* Edit page correction : move save button to the upper right
* add a statistic panel (nb brin/years) + filters ?
* when unblocking radio button is clcked, status passes to unblocked
  --> ideally, if its "quick" date is set to creation date, else to "today"
* show detail in a popup to avoid double click
* Move tomcat-maven-plugin to super-pom
* Manage automatic backup of repository ?
* Manage properly the absence of a confluence server (add alerts on gui or any sendMail simulation)

DONE :
* Manage PypRepository.xsd
* simple export for confluence, with Name/Url for quick insert in "reunion plateforme" minutes
* close WikiExport window with ESC button
* add an icon for wiki export
* Filter the last x days Brins OR highlight them with a specific color
   * filter should take creation date and Unblocking date
   * add "current" brins regardless of the creation date
   * summary should also be updated according to filter value
   * ergonomy :
          ** add more filters (current year / current month )
          ** move filter to the Left above "Summary" block.
   * when coming from edit page, filter is always "All brin"
* Edit page correction : bug link "Ajout"
