Jenkins
=======

Version details
---------------

  * Version: `2.46.3.2`
  * Mode:    WAR
  * Url:     https://jenkins1.prodwest.citrixsaassbe.net/jenkins/
  * Servlet container
      - Specification: 3.1
      - Name:          `jetty/9.2.z-SNAPSHOT`
  * Java
      - Home:           `/usr/safe/JAVA/jdk1.8.0_144/jre`
      - Vendor:           Oracle Corporation
      - Version:          1.8.0_144
      - Maximum memory:   16.00 GB (17179869184)
      - Allocated memory: 16.00 GB (17179869184)
      - Free memory:      11.26 GB (12086556224)
      - In-use memory:    4.74 GB (5093312960)
      - GC strategy:      G1
  * Java Runtime Specification
      - Name:    Java Platform API Specification
      - Vendor:  Oracle Corporation
      - Version: 1.8
  * JVM Specification
      - Name:    Java Virtual Machine Specification
      - Vendor:  Oracle Corporation
      - Version: 1.8
  * JVM Implementation
      - Name:    Java HotSpot(TM) 64-Bit Server VM
      - Vendor:  Oracle Corporation
      - Version: 25.144-b01
  * Operating system
      - Name:         Linux
      - Architecture: amd64
      - Version:      4.4.11-23.53.amzn1.x86_64
  * Process ID: 6 (0x6)
  * Process started: 2017-09-17 13:59:33.247+0000
  * Process uptime: 4 days 11 hr
  * JVM startup parameters:
      - Boot classpath: `/usr/safe/JAVA/jdk1.8.0_144/jre/lib/resources.jar:/usr/safe/JAVA/jdk1.8.0_144/jre/lib/rt.jar:/usr/safe/JAVA/jdk1.8.0_144/jre/lib/sunrsasign.jar:/usr/safe/JAVA/jdk1.8.0_144/jre/lib/jsse.jar:/usr/safe/JAVA/jdk1.8.0_144/jre/lib/jce.jar:/usr/safe/JAVA/jdk1.8.0_144/jre/lib/charsets.jar:/usr/safe/JAVA/jdk1.8.0_144/jre/lib/jfr.jar:/usr/safe/JAVA/jdk1.8.0_144/jre/classes`
      - Classpath: `/usr/share/jenkins/jenkins.war`
      - Library path: `/usr/java/packages/lib/amd64:/usr/lib64:/lib64:/lib:/usr/lib`
      - arg[0]: `-Duser.timezone=America/Los_Angeles`
      - arg[1]: `-Dhudson.model.ParametersAction.keepUndefinedParameters=true`
      - arg[2]: `-Dhudson.slaves.NodeProvisioner.MARGIN=50`
      - arg[3]: `-Dhudson.slaves.NodeProvisioner.MARGIN0=0.85`
      - arg[4]: `-Dhudson.model.DirectoryBrowserSupport.CSP=`
      - arg[5]: `-Djenkins.model.Jenkins.logStartupPerformance=true`
      - arg[6]: `-Dcom.cloudbees.opscenter.client.plugin.OperationsCenterCredentialsProvider.cache.disabled=true`
      - arg[7]: `-Dcom.cloudbees.opscenter.client.plugin.OperationsCenterCredentialsProvider.cache.disabled=true`
      - arg[8]: `-Djava.util.logging.config.file=/var/jenkins_home/data/log.properties`
      - arg[9]: `-Dhudson.tasks.MailSender.SEND_TO_UNKNOWN_USERS=true`
      - arg[10]: `-Dhudson.tasks.MailSender.SEND_TO_USERS_WITHOUT_READ=true`
      - arg[11]: `-Dhudson.TcpSlaveAgentListener.hostName=10.39.161.80`
      - arg[12]: `-XX:+AlwaysPreTouch`
      - arg[13]: `-Xloggc:/var/jenkins_home/gc/gc-%t.log`
      - arg[14]: `-XX:NumberOfGCLogFiles=5`
      - arg[15]: `-XX:+UseGCLogFileRotation`
      - arg[16]: `-XX:GCLogFileSize=20m`
      - arg[17]: `-XX:+PrintGC`
      - arg[18]: `-XX:+PrintGCDateStamps`
      - arg[19]: `-XX:+PrintGCDetails`
      - arg[20]: `-XX:+PrintHeapAtGC`
      - arg[21]: `-XX:+PrintGCCause`
      - arg[22]: `-XX:+PrintTenuringDistribution`
      - arg[23]: `-XX:+PrintReferenceGC`
      - arg[24]: `-XX:+PrintAdaptiveSizePolicy`
      - arg[25]: `-Djava.awt.headless=true`
      - arg[26]: `-XX:ReservedCodeCacheSize=384m`
      - arg[27]: `-XX:+UseCodeCacheFlushing`
      - arg[28]: `-XX:+UseG1GC`
      - arg[29]: `-XX:+UseStringDeduplication`
      - arg[30]: `-XX:+ExplicitGCInvokesConcurrent`
      - arg[31]: `-XX:+ParallelRefProcEnabled`
      - arg[32]: `-XX:+UnlockExperimentalVMOptions`
      - arg[33]: `-XX:G1NewSizePercent=20`
      - arg[34]: `-XX:+UnlockDiagnosticVMOptions`
      - arg[35]: `-XX:G1SummarizeRSetStatsPeriod=1`
      - arg[36]: `-Xmx16384m`
      - arg[37]: `-Xms16384m`

Important configuration
---------------

  * Security realm: `com.cloudbees.opscenter.security.OperationsCenterSecurityRealm`
  * Authorization strategy: `hudson.security.ProjectMatrixAuthorizationStrategy`
  * CSRF Protection: false
  * Initialization Milestone: Completed initialization

Active Plugins
--------------

  * ace-editor:1.1 'JavaScript GUI Lib: ACE Editor bundle plugin'
  * allure-jenkins-plugin:2.15 'Allure Jenkins Plugin'
  * analysis-core:1.86 'Static Analysis Utilities'
  * AnchorChain:1.0 'AnchorChain'
  * android-emulator:2.15 'Android Emulator Plugin'
  * ansible:0.6.2 'Jenkins Ansible plugin'
  * ansicolor:0.5.0 'AnsiColor'
  * ant:1.5 'Ant Plugin'
  * antisamy-markup-formatter:1.5 'OWASP Markup Formatter Plugin'
  * appdynamics-dashboard:1.0.7 'AppDynamics Dashboard Plugin for Jenkins'
  * artifactory:2.9.1 'Jenkins Artifactory Plugin'
  * async-http-client:1.7.24.1 'Async Http Client'
  * audit-trail:2.2 'Audit Trail'
  * authentication-tokens:1.3 'Authentication Tokens API Plugin'
  * aws-beanstalk-publisher-plugin:1.7.2 'AWS Elastic Beanstalk Publisher Plugin'
  * aws-credentials:1.16 'CloudBees Amazon Web Services Credentials Plugin'
  * aws-java-sdk:1.11.68 'Amazon Web Services SDK'
  * aws-lambda:0.5.10 'AWS Lambda Plugin'
  * blueocean:1.0.1 'Blue Ocean'
  * blueocean-autofavorite:0.6 'blueocean-autofavorite'
  * blueocean-commons:1.0.1 'Common API for Blue Ocean'
  * blueocean-config:1.0.1 'Config API for Blue Ocean'
  * blueocean-dashboard:1.0.1 'Dashboard for Blue Ocean'
  * blueocean-display-url:1.5.1 'BlueOcean Display URL plugin'
  * blueocean-events:1.0.1 'Events API for Blue Ocean'
  * blueocean-git-pipeline:1.0.1 'Git Pipeline for Blue Ocean'
  * blueocean-github-pipeline:1.0.1 'GitHub Pipeline for Blue Ocean'
  * blueocean-i18n:1.0.1 'i18n for Blue Ocean'
  * blueocean-jwt:1.0.1 'JWT for Blue Ocean'
  * blueocean-personalization:1.0.1 'Personalization for Blue Ocean'
  * blueocean-pipeline-api-impl:1.0.1 'Pipeline REST API for Blue Ocean'
  * blueocean-pipeline-editor:0.2.0 'Blue Ocean Pipeline Editor'
  * blueocean-rest:1.0.1 'REST API for Blue Ocean'
  * blueocean-rest-impl:1.0.1 'REST Implementation for Blue Ocean'
  * blueocean-web:1.0.1 'Web for Blue Ocean'
  * bouncycastle-api:2.16.1 'bouncycastle API Plugin'
  * branch-api:2.0.9 'Branch API Plugin'
  * build-flow-plugin:0.20 'Build Flow plugin'
  * build-metrics:1.3 'build-metrics'
  * build-monitor-plugin:1.11+build.201701152243 'Build Monitor View'
  * build-name-setter:1.6.5 'build-name-setter'
  * build-pipeline-plugin:1.5.6 'Build Pipeline Plugin'
  * build-timeout:1.18 'Jenkins build timeout plugin'
  * build-user-vars-plugin:1.5 'Jenkins user build vars plugin'
  * build-with-parameters:1.3 'Build With Parameters'
  * built-on-column:1.1 'built-on-column'
  * checkstyle:3.48 'Checkstyle Plug-in'
  * claim:2.9 'Jenkins Claim Plugin'
  * clone-workspace-scm:0.6 'Jenkins Clone Workspace SCM Plug-in'
  * cloudbees-aborted-builds:1.9 'CloudBees Restart Aborted Builds Plugin'
  * cloudbees-assurance:2.46.2.3 'Beekeeper Upgrade Assistant Plugin'
  * cloudbees-aws-cli:1.5.7 'CloudBees Amazon AWS CLI Plugin'
  * cloudbees-bitbucket-branch-source:2.1.2 'Bitbucket Branch Source Plugin'
  * cloudbees-credentials:3.3 'CloudBees Credentials Plugin'
  * cloudbees-even-scheduler:3.7 'CloudBees Even Scheduler Plugin'
  * cloudbees-folder:6.0.4 'Folders Plugin'
  * cloudbees-folders-plus:3.1 'CloudBees Folders Plus Plugin'
  * cloudbees-jsync-archiver:5.5 'CloudBees Fast Archiving Plugin'
  * cloudbees-label-throttling-plugin:3.4 'CloudBees Label Throttling Plugin'
  * cloudbees-license:9.11 'CloudBees License Manager'
  * cloudbees-long-running-build:1.9 'CloudBees Long-Running Build Plugin'
  * cloudbees-monitoring:2.5 'CloudBees Monitoring Plugin'
  * cloudbees-nodes-plus:1.14 'CloudBees Nodes Plus Plugin'
  * cloudbees-plugin-usage:1.6 'CloudBees Plugin Usage Plugin'
  * cloudbees-quiet-start:1.2 'CloudBees Quiet Start Plugin'
  * cloudbees-request-filter:1.2 'CloudBees Request Filter Plugin'
  * cloudbees-secure-copy:3.9 'CloudBees Secure Copy Plugin'
  * cloudbees-ssh-slaves:1.7 'CloudBees SSH Build Agents Plugin'
  * cloudbees-support:3.9 'CloudBees Support Plugin'
  * cloudbees-template:4.29 'CloudBees Template Plugin'
  * cloudbees-workflow-template:2.6 'CloudBees Pipeline: Templates Plugin'
  * cloudbees-workflow-ui:2.1 'CloudBees Pipeline Stage View Extensions'
  * clover:4.7.1 'Jenkins Clover plugin'
  * cobertura:1.10 'Jenkins Cobertura Plugin'
  * conditional-buildstep:1.3.5 'Conditional BuildStep'
  * config-file-provider:2.15.7 'Config File Provider Plugin'
  * configurationslicing:1.45 'Configuration Slicing plugin'
  * copyartifact:1.38.1 'Copy Artifact Plugin'
  * coverity:1.9.1 'Coverity plugin'
  * credentials:2.1.13 'Credentials Plugin'
  * credentials-binding:1.11 'Credentials Binding Plugin'
  * crowd2:1.8 'Crowd 2 Integration'
  * cucumber-testresult-plugin:0.9.7 'Cucumber json test reporting.'
  * cvs:2.13 'Jenkins CVS Plug-in'
  * dashboard-view:2.9.10 'Dashboard View'
  * delivery-pipeline-plugin:1.0.1 'Delivery Pipeline Plugin'
  * deployed-on-column:1.7 'Deployed On Column Plugin'
  * deployer-framework:1.1 'Deployer Framework Plugin'
  * display-url-api:1.1.1 'Display URL API'
  * docker-asg-plugin:1.0.22 'Docker ASG plugin'
  * docker-build-publish:1.3.2 'CloudBees Docker Build and Publish plugin'
  * docker-build-step:1.42 'docker-build-step'
  * docker-commons:1.6 'Docker Commons Plugin'
  * docker-custom-build-environment:1.6.5 'CloudBees Docker Custom Build Environment Plugin'
  * docker-traceability:1.2 'CloudBees Docker Traceability'
  * docker-workflow:1.11 'Docker Pipeline'
  * dockerhub-notification:2.2.0 'CloudBees Docker Hub/Registry Notification'
  * durable-task:1.13 'Durable Task Plugin'
  * dynamicparameter:0.2.0 'Jenkins Dynamic Parameter Plug-in'
  * ec2:2.68 'Amazon EC2 plugin'
  * email-ext:2.57.2 'Email Extension Plugin'
  * emailext-template:1.0 'Email Extension Template Plugin'
  * envinject:2.0 'Environment Injector Plugin'
  * extensible-choice-parameter:1.4.0 'Extensible Choice Parameter plugin'
  * external-monitor-job:1.7 'External Monitor Job Type Plugin'
  * ez-templates:1.2.5 'EZ Templates'
  * favorite:2.3.0 'Favorite'
  * findbugs:4.70 'FindBugs Plug-in'
  * fortify360:3.81 'Fortify 360 Plugin'
  * git:3.3.0 'Jenkins Git plugin'
  * git-client:2.4.6 'Jenkins Git client plugin'
  * git-parameter:0.8.0 'Git Parameter Plug-In'
  * git-server:1.7 'Jenkins GIT server Plugin'
  * git-validated-merge:3.20 'CloudBees Git Validated Merge Plugin'
  * github:1.26.2 'GitHub plugin'
  * github-api:1.85 'GitHub API Plugin'
  * github-branch-source:2.0.5 'GitHub Branch Source Plugin'
  * global-build-stats:1.4 'Hudson global-build-stats plugin'
  * global-post-script:1.1.3 'Global Post Script Plugin'
  * gradle:1.26 'Gradle Plugin'
  * groovy:2.0 'Groovy'
  * groovy-postbuild:2.3.1 'Groovy Postbuild'
  * handlebars:1.1.1 'JavaScript GUI Lib: Handlebars bundle plugin'
  * hidden-parameter:0.0.4 'Hidden Parameter plugin'
  * htmlpublisher:1.13 'HTML Publisher plugin'
  * http_request:1.8.19 'HTTP Request Plugin'
  * icon-shim:2.0.3 'Icon Shim Plugin'
  * infradna-backup:3.34 'CloudBees Back-up Plugin'
  * ivy:1.27.1 'Ivy Plugin'
  * jackson2-api:2.7.3 'Jackson 2 API Plugin'
  * jacoco:2.2.1 'Jenkins JaCoCo plugin'
  * javadoc:1.4 'Javadoc Plugin'
  * jdepend:1.2.4 'Jenkins JDepend Plugin'
  * jenkins-cloudformation-plugin:1.2 'jenkins-cloudformation-plugin'
  * jenkins-multijob-plugin:1.24 'Jenkins Multijob plugin'
  * job-dsl:1.63 'Job DSL'
  * jobConfigHistory:2.16 'Jenkins Job Configuration History Plugin'
  * jobgenerator:1.22 'Job Generator'
  * join:1.21 'Join plugin'
  * jquery:1.11.2-0 'Jenkins jQuery plugin'
  * jquery-detached:1.2.1 'JavaScript GUI Lib: jQuery bundles (jQuery and jQuery UI) plugin'
  * jquery-ui:1.0.2 'Jenkins jQuery UI plugin'
  * junit:1.20 'JUnit Plugin'
  * label-linked-jobs:5.1.2 'Label Linked Jobs Plugin'
  * ldap:1.15 'LDAP Plugin'
  * lockable-resources:2.0 'Lockable Resources plugin'
  * m2release:0.14.0 'Jenkins Maven Release Plug-in Plug-in'
  * mailer:1.20 'Jenkins Mailer Plugin'
  * mapdb-api:1.0.9.0 'MapDB API Plugin'
  * mask-passwords:2.10.1 'Mask Passwords Plugin'
  * matrix-auth:1.7 'Matrix Authorization Strategy Plugin'
  * matrix-project:1.8 'Matrix Project Plugin'
  * maven-deployment-linker:1.5.1 'Maven Deployment Linker'
  * maven-plugin:2.14 'Maven Integration plugin'
  * mercurial:1.59 'Jenkins Mercurial plugin'
  * metrics:3.1.2.9 'Metrics Plugin'
  * momentjs:1.1.1 'JavaScript GUI Lib: Moment.js bundle plugin'
  * monitoring:1.65.1 'Monitoring'
  * multiple-scms:0.6 'Jenkins Multiple SCMs plugin'
  * naginator:1.17.2 'Naginator'
  * nectar-license:8.6 'CloudBees Jenkins Enterprise License Entitlement Check'
  * nectar-rbac:5.16 'CloudBees Role-Based Access Control Plugin'
  * nested-view:1.14 'Nested View Plugin'
  * next-build-number:1.4 'Next Build Number Plugin'
  * node-iterator-api:1.5 'Node Iterator API Plugin'
  * nodejs:0.2.2 'NodeJS Plugin'
  * nodelabelparameter:1.7.2 'Node and Label parameter plugin'
  * nunit:0.20 'Jenkins NUnit plugin'
  * operations-center-agent:2.46.0.3 'Operations Center Agent'
  * operations-center-analytics-config:2.46.0.2 'Operations Center Analytics Configuration'
  * operations-center-analytics-reporter:2.46.0.2 'Operations Center Analytics Reporter'
  * operations-center-client:2.46.0.4 'Operations Center Client Plugin'
  * operations-center-cloud:2.46.0.2 'Operations Center Cloud'
  * operations-center-context:2.46.0.6 'Operations Center Context'
  * operations-center-openid-cse:1.8.110 'Operations Center OpenID Cluster Session Extension'
  * p4:1.5.1 'P4 Plugin'
  * pam-auth:1.3 'PAM Authentication plugin'
  * parameter-separator:1.0 'Parameter Separator Plugin'
  * Parameterized-Remote-Trigger:2.2.2 'Parameterized Remote Trigger Plugin'
  * parameterized-trigger:2.32 'Jenkins Parameterized Trigger plugin'
  * perforce:1.3.36 'Perforce Plugin'
  * performance:3.0 'Performance Plugin'
  * persistent-parameter:1.1 'Persistent Parameter Plugin'
  * pipeline-aggregator-view:1.7 'Pipeline Aggregator'
  * pipeline-aws:1.9 'Pipeline: AWS Steps'
  * pipeline-build-step:2.5 'Pipeline: Build Step'
  * pipeline-graph-analysis:1.3 'Pipeline Graph Analysis Plugin'
  * pipeline-input-step:2.7 'Pipeline: Input Step'
  * pipeline-maven:2.2.0 'Pipeline Maven Integration Plugin'
  * pipeline-milestone-step:1.3.1 'Pipeline: Milestone Step'
  * pipeline-model-api:1.1.4 'Pipeline: Model API'
  * pipeline-model-declarative-agent:1.1.1 'Pipeline: Declarative Agent API'
  * pipeline-model-definition:1.1.4 'Pipeline: Model Definition'
  * pipeline-model-extensions:1.1.4 'Pipeline: Declarative Extension Points API'
  * pipeline-multibranch-defaults:1.1 'Pipeline: Multibranch with defaults'
  * pipeline-npm:0.9.1 'Pipeline NPM Integration Plugin'
  * pipeline-rest-api:2.6 'Pipeline: REST API Plugin'
  * pipeline-stage-step:2.2 'Pipeline: Stage Step'
  * pipeline-stage-tags-metadata:1.1.4 'Pipeline: Stage Tags Metadata'
  * pipeline-stage-view:2.6 'Pipeline: Stage View Plugin'
  * pipeline-utility-steps:1.3.0 'Pipeline Utility Steps'
  * plain-credentials:1.4 'Plain Credentials Plugin'
  * plugin-usage-plugin:0.4-SNAPSHOT (private-09/17/2016 16:21-ec2-user) 'Plugin Usage - Plugin'
  * pmd:3.47 'PMD Plug-in'
  * port-allocator:1.8 'Jenkins Port Allocator Plug-in'
  * post-completed-build-dynamodb-result:1.0.3 'post-completed-build-dynamodb-result'
  * post-completed-build-result:1.1 'post-completed-build-result'
  * postbuildscript:0.17 'Jenkins Post-Build Script Plug-in'
  * PrioritySorter:3.5.0 'Jenkins Priority Sorter Plugin'
  * project-stats-plugin:0.4 'Project statistics Plugin'
  * promoted-builds:2.28.1 'Jenkins promoted builds plugin'
  * pubsub-light:1.7 'Jenkins Pub-Sub "light" Bus'
  * quality-gates:2.5 'Quality Gates Plugin'
  * rebuild:1.25 'Rebuilder'
  * resource-disposer:0.6 'Resource Disposer Plugin'
  * run-condition:1.0 'Run Condition Plugin'
  * s3:0.10.12 'Jenkins S3 publisher plugin'
  * scm-api:2.1.1 'SCM API Plugin'
  * script-security:1.27 'Script Security Plugin'
  * scriptler:2.9 'Scriptler'
  * seleniumhq:0.4 'Hudson Seleniumhq plugin'
  * sidebar-link:1.7 'Sidebar Link'
  * simple-parameterized-builds-report:1.5 'Simple Parameterized Builds Report Plugin'
  * simple-theme-plugin:0.3 'Simple Theme Plugin'
  * skip-plugin:4.0 'CloudBees Skip Next Build Plugin'
  * slack:2.2 'Slack Notification Plugin'
  * sonar:2.6.1 'SonarQube Scanner for Jenkins'
  * sse-gateway:1.15 'Server Sent Events (SSE) Gateway Plugin'
  * ssh-agent:1.15 'SSH Agent Plugin'
  * ssh-credentials:1.13 'SSH Credentials Plugin'
  * ssh-slaves:1.17 'Jenkins SSH Slaves plugin'
  * stash-pullrequest-builder:1.7.0 'Stash Pullrequest Builder Plugin'
  * stashNotifier:1.11.6 'Stash Notifier'
  * structs:1.6 'Structs Plugin'
  * subversion:2.7.2 'Jenkins Subversion Plug-in'
  * support-core:2.41 'Support Core Plugin'
  * template-project:1.5.2 'Template Project plugin'
  * testng-plugin:1.14 'TestNG Results Plugin'
  * timestamper:1.8.8 'Timestamper'
  * token-macro:2.1 'Token Macro Plugin'
  * translation:1.15 'Jenkins Translation Assistance plugin'
  * unique-id:2.1.3 'Unique ID Library Plugin'
  * uno-choice:1.4 'Active Choices Plug-in'
  * validating-string-parameter:2.3 'Validating String Parameter Plugin'
  * variant:1.1 'Variant Plugin'
  * view-job-filters:1.27 'View Job Filters'
  * windows-slaves:1.3.1 'Windows Slaves Plugin'
  * workflow-aggregator:2.5 'Pipeline'
  * workflow-api:2.13 'Pipeline: API'
  * workflow-basic-steps:2.4 'Pipeline: Basic Steps'
  * workflow-cps:2.30 'Pipeline: Groovy'
  * workflow-cps-checkpoint:2.4 'CloudBees Pipeline: Groovy Checkpoint Plugin'
  * workflow-cps-global-lib:2.8 'Pipeline: Shared Groovy Libraries'
  * workflow-durable-task-step:2.11 'Pipeline: Nodes and Processes'
  * workflow-job:2.11 'Pipeline: Job'
  * workflow-multibranch:2.14 'Pipeline: Multibranch'
  * workflow-remote-loader:1.4 'Jenkins Pipeline Remote Loader Plugin'
  * workflow-scm-step:2.5 'Pipeline: SCM Step'
  * workflow-step-api:2.9 'Pipeline: Step API'
  * workflow-support:2.14 'Pipeline: Supporting APIs'
  * ws-cleanup:0.33 'Jenkins Workspace Cleanup Plugin'
  * xunit:1.102 'xUnit plugin'
  * xvfb:1.1.3 'Jenkins Xvfb plugin'
  * zentimestamp:4.2 'Jenkins Zentimestamp plugin'

Packaging details
-----------------

#UNKNOWN#

CloudBees Product Description
-----------------------------

 * Product Distribution: rolling 
 * Product Id: cje 
 * Product Name: CloudBees Jenkins Enterprise 
 * Product Version: 2.46.3.2 
