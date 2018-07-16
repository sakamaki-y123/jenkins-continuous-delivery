# Study guide
- English 
  - https://www.cloudbees.com/sites/default/files/cje-study-guide-2017.pdf
- Japanese
  - http://kazuhito-m.github.io/tech/2017/02/07/cje-study-guide-2017-jp

---

## 1. Key CI/CD/Jenkins Concepts

- Continuous Delivery/Continuous Integration Concepts
  - Define continuous integration, continuous delivery, continuous deployment
  https://www.atlassian.com/continuous-delivery/ci-vs-ci-vs-cd
    - Continuous Integration
    ```
    Developers practicing continuous integration merge their changes back to the main branch as often as possible. The developer's changes are validated by creating a build and running automated tests against the build. By doing so, you avoid the integration hell that usually happens when people wait for release day to merge their changes into the release branch.

    Continuous integration puts a great emphasis on testing automation to check that the application is not broken whenever new commits are integrated into the main branch.
    ```
    - ContinuousDelivery
    ```
    Continuous delivery is an extension of continuous integration to make sure that you can release new changes to your customers quickly in a sustainable way. This means that on top of having automated your testing, you also have automated your release process and you can deploy your application at any point of time by clicking on a button.

    In theory, with continuous delivery, you can decide to release daily, weekly, fortnightly, or whatever suits your business requirements. However, if you truly want to get the benefits of continuous delivery, you should deploy to production as early as possible to make sure that you release small batches, that are easy to troubleshoot in case of a problem.
    ```
    - Continuous deployment
    ```
    Continuous deployment goes one step further than continuous delivery. With this practice, every change that passes all stages of your production pipeline is released to your customers. There's no human intervention, and only a failed test will prevent a new change to be deployed to production.

    Continuous deployment is an excellent way to accelerate the feedback loop with your customers and take pressure off the team as there isn't a Release Day anymore. Developers can focus on building software, and they see their work go live minutes after they've finished working on it.
    ```
  - Difference between CI and CD
    ```
    Continuous Delivery is sometimes confused with Continuous Deployment. Continuous Deployment means that every change goes through the pipeline and automatically gets put into production, resulting in many production deployments every day. Continuous Delivery just means that you are able to do frequent deployments but may choose not to do it, usually due to businesses preferring a slower rate of deployment. In order to do Continuous Deployment you must be doing Continuous Delivery.
    ```
  - Stages of CI and CD
    - Start with the commit stage which compiles and runs unit tests.
    - Then run longer tests/quality tools/ Ex: acceptance tests (given/when/then)
    - Finally, deploy
  - Continuous delivery versus continuous deployment
  https://www.aspetraining.com/resources/blog/continuous-delivery-vs-continuous-deployment
    - Delivery means the ability to deploy to production. Deployment means actually doing so
    
    ![Continuous delivery versus continuous deployment](https://www.aspetraining.com/sites/default/files/inline-images/puppet_continuous_diagram.gif)
    
- Jobs
  - What are jobs in Jenkins?  
  https://jenkins-le-guide-complet.github.io/html/sect-first-steps-first-job.html
    - Job/Project – Runnable tasks
  ```
  Build jobs are at the heart of the Jenkins build process. Simply put, you can think of a Jenkins build job as a particular task or step in your build process. This may involve simply compiling your source code and running your unit tests. Or you might want a build job to do other related tasks, such as running your integration tests, measuring code coverage or code quality metrics, generating technical documentation, or even deploying your application to a web server. A real project usually requires many separate but related build jobs.
  ```
  - Types of jobs 
  https://blog.knoldus.com/2017/02/09/jenkins-build-jobs/
    - Freestyle project
    - Maven project
    - Pipeline
    - Multi configuration
    - Multi branch
    - Long running
  - Scope of jobs
    - Not sure what this means – Maybe that there is a long running job type?
- Builds
  - What are builds in Jenkins?
  https://wiki.jenkins.io/display/JENKINS/Building+a+software+project
    - Build – Result of one run of a job/project
  - What are build steps, triggers, artifacts, and repositories?
    - Build step – a single operation withing a build
    - Triggers – something that starts a build (time, SCM polling, etc)
    - Artifact – output of a build
    - Repository – the SCM system where the code to be built lives
  - Build tools configuration
    - In Manage System, set up location of tools like the JDK, Ant and Maven
- Source Code Management
  - What are source code management systems and how are they used?
    - Use to track code
    - Client/server – one source of truth such as SVN.
    - Distributed version control – every developer has copy of repository, peer to peer,  such as Git.
  - Cloud-based SCMs
    - Ex: Git hub
  - Jenkins changelogs
    - List commits since last build
  - Incremental updates v clean check out
    - Incremental updates – faster
    - Clean check out – guarantees no extra or changed local files
  - Checking in code
    - Should be at least daily with CI
  - Infrastructure-as-Code
    - Storing everything needed to build your environment
  - Branch and Merge Strategies
    - Branch by release
    - Branch by feature – by user story
    - Branch by abstraction – one branch, but turn features on/off by release
    - Merge regularly
- Testing
  - Benefits of testing with Jenkins
    - Fast feedback!
  - Define unit test, smoke test, acceptance test, automated verification/functional
    - Unit test – test one class, often involves test doubles
    - Integration/functional test – test components together
    - Smoke test – sanity check to reject a release. Looking for major errors.
    - Acceptance test – user level test for feature
tests
- Notifications
  - Types of notifications in Jenkins
    - Failure, second failure, success, etc
    - Active/push – radiators/SMS vs passive/pull – rss/dashboard
    - RSS - /rssAll, /rssFailed and rssLatest
    - Radiator view plugin uses the entire screen
    - Extreme feedback – physical/audio devices
  - Importance of notifications
    - Fixing a build is high priority so need to know it is broken
    - Communicating the status to all parties
- Distributed Builds
  - What are distributed builds?
    - Running builds on a different machine than master
  - Functions of masters and agents
    - Master – basic Jenkins install
    - Slaves – just for running jobs
- Plugins
  - What are plugins?
    - Add functionality to core Jenkins
  - What is the plugin manager?
    - UI for uploading/managing plugins
- Jenkins Rest API
  - How to interact with it
    - Format: XML or JSON
    - Python and Ruby wrapper APIs
  - Why use it?
    - Programmatic access
- Security
  - Authentication versus authorization
    - Authentication – identify a user
    - Authorization – what user can do
  - Matrix security
    - Maps roles to permissions
    - Major categories: overall, slave, job, run, view and SCM
  - Definition of auditing, credentials, and other key security concepts
    - Auditing – logging user operations and changes
    - Credentials – username/password or the like for access
- Fingerprints
  - What are fingerprints?
    - MD5 checksum of files
    - UI says for jar files, but works for any type of file
  - How do fingerprints work?
    - The first time you run a job with a post build step to generate a fingerprint, a new left navigation option shows up to check a file’s fingerprint.
    - You can upload a file you have to see if any file Jenkins knows the fingerprint of matches.
- Artifacts
  - How to use artifacts in Jenkins
    - Download, put in Nexus, deploy, etc
  - Storing artifacts
    - Can archive
    - Can control discard policy
- Using 3rd party tools
  - How to use 3rd party tools
    - Setup in Manage System the location on disk or download from there
    - Ex: JDK, Maven, Git
    - Can install automatically or from file system
- Installation Wizard [new] ★
  - What is the Jenkins Installation Wizard?
  - How to use the Wizard?
  - Which configurations are covered by the Installation Wizard?

---

## 2. Jenkins usage (features and functionality)

- Jobs
  - Organizing jobs in Jenkins
  - Parameterized jobs
  - Usage of Freestyle/Pipeline/Matrix jobs
- Builds
  - Setting up build steps and triggers
  - Configuring build tools
  - Running scripts as part of build steps
- Source Code Management
  - Polling source code management
  - Creating hooks
  - Including version control tags and version information
- Testing
  - Testing for code coverage
  - Test reports in Jenkins
  - Displaying test results
  - Integrating with test automation tools
  - Breaking builds
- Notifications
  - Setup and usage
  - Email notifications, instant messaging
  - Alarming on notifications
- Distributed Builds
  - Setting up and running builds in parallel
  - Setting up and using SSH agents, JNLP agents, cloud agents
  - Monitoring nodes
- Plugins
  - Setting up and using Plugin Manager
  - Finding and configuring required plugins
- CI/CD
  - Using Pipeline (formerly known as “Workflow”)
  - Integrating automated deployment
  - Release management process
  - Pipeline stage behavior
- Jenkins Rest API
  - Using REST API t  - trigger jobs remotely, access job status, create/delete jobs
- Security
  - Setting up and using security realms
  - User database, project security, Matrix security
  - Setting up and using auditing
  - Setting up and using credentials
- Fingerprints
  - Fingerprinting jobs shared or copied between jobs
- Artifacts
  - Copying artifacts
  - Using artifacts in Jenkins
  - Artifact retention policy
- Alerts
  - Making basic updates t  - jobs and build scripts
  - Troubleshooting specific problems from build and test failure alerts

## 3. Building Continuous Delivery (CD) Pipelines
- Pipeline Concepts
  - Value stream mapping for CD pipelines
  - Why create a pipeline?
  - Gates within a CD pipeline
  - How t  - protect centralized pipelines when multiple groups use same tools
  - Definition of binary reuse, automated deployment, multiple environments
  - Elements of your ideal CI/CD pipeline - tools
  - Key concepts in building scripts (including security/password, environment
information, etc.)
- Upstream and downstream
  - Triggering jobs from other jobs
  - Setting up the Parameterized Trigger plugin
  - Upstream/downstream jobs
- Triggering
  - Triggering Jenkins on code changes
  - Difference between push and pull
  - When t  - use push vs pull
- Pipeline (formerly known as “Workflow”)
  - Benefits of Pipeline vs linked jobs
  - Functionalities offered by Pipeline
  - How t  - use Pipeline
  - Pipeline stage view [new]
- Folders
  - How t  - control access t  - items in Jenkins with folders
  - Referencing jobs in folders
- Parameters
  - Setting up test automation in Jenkins against an uploaded executable
  - Passing parameters between jobs
  - Identifying parameters and how t  - use them: file parameter, string parameter
  - Jenkins CLI parameters
- Promotions
  - Promotion of a job
  - Why promote jobs?
  - How t  - use the Promoted Builds plugin
- Notifications
  - How t  - radiate information on CD pipelines t  - teams
- Pipeline Multibranch and Repository Scanning [new]
  - Usage of Multibranch jobs
  - Scanning GitHub and BitBucket Organization
  - Scanning basic SCM repositories
- Pipeline Global Libraries [new]
  - How t  - share code across Pipelines
  - Usages of the Shared Libraries
  - Interaction with Folders and Repository scanning
  - Security and Groovy sandbox

## 4. CD-as-Code Best Practices
- Distributed builds architecture
- Fungible (replaceable) agents
- Master-agent connectors and protocol
- Tool installations on agents
- Cloud agents
- Traceability
- High availability