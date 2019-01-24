package pluralsight.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.task.launcher.TaskLaunchRequest;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Source.class)
public class TaskProcessor {

	@Autowired
	private Source source;	// Autowired to RabbitMq (source can be other kinds of messages depend on set up)

	public void publishRequest(String payload) {

		// Here we get the task from local maven repo
		//maven://[groupid]:[artifactid]:jar:[version]
		String taskArtifactUrl = "maven://pluralsight.demo:pluralsight-springcloud-m3-task:jar:0.0.1-SNAPSHOT";

		// Parse the payload (assume created with comma delimiter) to get a list of input
		List<String> inputList = new ArrayList<String>(Arrays.asList(payload.split(",")));

		// Create a TaskLaunchRequest with the task url and input
		TaskLaunchRequest request = new TaskLaunchRequest(taskArtifactUrl, inputList, null, null, "Application name");
		System.out.println("created task launch request ...");

		// Create a message with type of TaskLaunchRequest and send it to the source (Rabbitmq)
		GenericMessage<TaskLaunchRequest> message = new GenericMessage<>(request);
		this.source.output().send(message);
	}
}
