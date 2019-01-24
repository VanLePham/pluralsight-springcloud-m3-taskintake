## pluralsight-springcloud-m3-taskintake
Web app to initiate a task

This app used to test the apps `pluralsight-springcloud-m3-task` and `pluralsight-springcloud-m3-tasksink`

It will use to Spring Cloud Stream Rabbitmq (see the `pom` and `application.properties` files for dependency and setup)

In the class `TaskProcessor`, we will create a Spring Cloud Task launcher `TaskLaunchRequest` passing in the task artifact url (here I use the maven repo of the `pluralsight-springcloud-m3-task`). Then we will create a message with type of `TaskLaunchRequest` and send it to the Rabbitmq

In the class `TaskController`, we will create a Rest Api POST with request body (Station id, licence plate, timestamp), and use the above `TaskProcessor` to publish that request to the message queue

## To test the whole flow:
**1.**
Clone the app `pluralsight-springcloud-m3-task` (see its README for details). Build the `pluralsight-springcloud-m3-task` as `Run As -> Maven Install`. Make sure it successfully built and the jar file is created and coppied to `/Users/<username>/.m2/repository/pluralsight/demo/pluralsight-springcloud-m3-task/0.0.1-SNAPSHOT/`
  
**2.** 
Clone the app `pluralsight-springcloud-m3-tasksink` (see its README for details). Run the `pluralsight-springcloud-m3-tasksink`. Make sure there is a `tasktopic.anonymous.tu-xxxxx....` queue in the Rabbit manager console. 

**3.** Clone and run this app

**4.** Open Postman and do a POST to http://localhost:8082/tasks with body as text `StationName,LicensePlate,2019-01-01T08:44:50`

**5.** Look at the app Console window to see the log msg. If you create a database for the `pluralsight-springcloud-m3-task`, open the database to see if the record is saved

## Notes: 
Make sure the settings 
`spring.cloud.stream.bindings.input.destination=tasktopic` in tasksink project

and 
`spring.cloud.stream.bindings.output.destination=tasktopic` in taskintake project

point to the same queue (in this case `tasktopic`)
