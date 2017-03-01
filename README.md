**Run in development mode**

Run as java:
- WebApplication.java
- MasterrunnerConfiguration.java
- SourcesRepositoryApplication.java

**Open in browser webapp:**
- to see builds: [builds](http://localhost:8090/builds)
- to start release process: [selectmodules](http://localhost:8090/selectmodules)

**Open in browser master:**
- [swagger-ui](http://localhost:8081/swagger-ui.html)

**Open in browser SourcesRepository:**
- [swagger-ui](http://localhost:8082/swagger-ui.html)


**Configurations**
- **CIAgentFacadeImpl** upload by ssh and run builds.
- **CIEngineFacadeImpl** main facade with all workflow.
- **EnvironmentFacadeImpl** contains how to build modules is here  (which execution list to apply to which Module and branch, 
what docker image to use)
- **ModuleFacadeImpl** has Modules configurations  (name, git url).
- **NodeFacadeImpl** has list of nodes accesible to build.


- **CIEngineListenerImpl** is entry point to all events, has reactions on them (what to do in case some particular event). 
In general it is just listener, we can add any number of listeners.

Build lists lives in **agent** module:
- **MockReleaseList** - for tests
- **ReleaseList** - real build (clone sources, run maven and so on).
- **OnCommitList** 

