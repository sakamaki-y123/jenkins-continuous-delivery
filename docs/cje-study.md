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
  - Continuous delivery versus continuous deployment
  https://www.aspetraining.com/resources/blog/continuous-delivery-vs-continuous-deployment
  ![Continuous delivery versus continuous deployment](https://www.aspetraining.com/sites/default/files/inline-images/puppet_continuous_diagram.gif)

- Jobs
  - What are jobs in Jenkins?
  - Types of jobs
  - Scope of jobs
- Builds
  - What are builds in Jenkins?
  - What are build steps, triggers, artifacts, and repositories?
  - Build tools configuration
- Source Code Management
  - What are source code management systems and how are they used?
  - Cloud-based SCMs
  - Jenkins changelogs
  - Incremental updates v clean check out
  - Checking in code
  - Infrastructure-as-Code
  - Branch and Merge Strategies
- Testing
  - Benefits of testing with Jenkins
  - Define unit test, smoke test, acceptance test, automated verification/functional
tests
- Notifications
  - Types of notifications in Jenkins
  - Importance of notifications
- Distributed Builds
  - What are distributed builds?
  - Functions of masters and agents
- Plugins
  - What are plugins?
  - What is the plugin manager?
- Jenkins Rest API

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