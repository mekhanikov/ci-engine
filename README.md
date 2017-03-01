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


**Configurations
- Modules configured here ModuleFacadeImpl (name, git url).
- How to build modules is here EnvironmentFacadeImpl (which execution list to apply to which Module and branch, 
what docker image to use)
- CIEngineListenerImpl is entry point to all events, has reactions on the (what to do in case some particular event). 
In general it is just listener, we can add any number of listeners.
